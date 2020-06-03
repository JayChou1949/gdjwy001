package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import com.upd.hwcloud.bean.dto.RedPoint;
import com.upd.hwcloud.bean.entity.Dict;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.dao.DictMapper;
import com.upd.hwcloud.dao.PaasMapper;
import com.upd.hwcloud.service.IDictService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-10-18
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private PaasMapper paasMapper;

    @Override
    public List<Dict> getTree() {
        // 查询所有字段并缓存
        List<Dict> allDict = this.list(new QueryWrapper<Dict>().orderByAsc("SORT"));
        Map<String, Dict> dictMap = new HashMap<>(allDict.size());
        for (Dict dict : allDict) {
            dictMap.put(dict.getId(), dict);
        }
        // 组合节点
        List<Dict> root = new ArrayList<>(); //第一级
        for (Dict dict : allDict) {
            if (dict.getLvl() == 0) {
                root.add(dict);
            } else {
                Dict p = dictMap.get(dict.getPid());
                if (p != null) {
                    p.getChildren().add(dict);
                }
            }
        }
        return root;
    }

    @Override
    public boolean isBasePaaSService(String id) {
        String name = paasMapper.getCategoryNameById(id);
        return StringUtils.equals(name,"基础服务");
    }

    @Override
    public Dict add(Dict dict) {
        if (exists(dict)) {
            throw new BaseException("该字典已存在!");
        }

        Integer defSortNum = 1;
        String pid = dict.getPid();
        if (pid != null && !"".equals(pid)) {
            Dict parentDict = dictMapper.selectById(pid);
            dict.setLvl(parentDict.getLvl() + 1);
            dict.setpValue(parentDict.getValue());

            Integer maxSort = dictMapper.getMaxSort(pid);
            if (maxSort != null) {
                defSortNum = maxSort + 1;
            }
        } else {
            dict.setLvl(0);
        }
        // 默认排序
        if (dict.getSort() == null){
            dict.setSort(defSortNum);
        }
        dict.insert();
        return dict;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Dict edit(Dict dict) {
        // 查询修改后的值是否重复
        List<Dict> dicts = dictMapper.selectList(new QueryWrapper<Dict>().lambda()
                .eq(Dict::getValue, dict.getValue()).isNull(Dict::getPid));
        for (Dict d : dicts) {
            if (!dict.getId().equals(d.getId())) {
                throw new BaseException("该字典已存在!");
            }
        }

        Dict oldDict = dictMapper.selectById(dict.getId());
        oldDict.setName(dict.getName());
        oldDict.setRemark(dict.getRemark());
        oldDict.setValue(dict.getValue());
        oldDict.setSort(dict.getSort());
        oldDict.updateById();
        // 修改子集
        dictMapper.updateSubDictValue(dict.getValue(), dict.getId());
        return dict;
    }

    @Override
    public List<Dict> getChildByValue(String value) {
        Dict parent = this.getOne(new QueryWrapper<Dict>().eq("LVL", 0).eq("VALUE", value));
        if (parent == null) {
            return new ArrayList<>();
        }
        // 查询所有字段并缓存
        List<Dict> allDict = this.list(new QueryWrapper<Dict>().orderByAsc("SORT"));
        Map<String, Dict> dictMap = new HashMap<>(allDict.size());
        for (Dict dict : allDict) {
            dictMap.put(dict.getId(), dict);
        }
        // 组合节点
        for (Dict dict : allDict) {
            if (parent.getId().equals(dict.getId())) {
                parent = dict;
            }
            if (dict.getLvl() != 0) {
                Dict p = dictMap.get(dict.getPid());
                if (p != null) {
                    p.getChildren().add(dict);
                }
            }
        }
        return parent.getChildren();
    }

    @Override
    public RedPoint getRedPoint() {
        RedPoint rp = new RedPoint();
        List<Dict> redPoint = getChildByValue("redPoint");
        redPoint.forEach(p -> {
            if ("during".equals(p.getName())) {
                rp.setDuring(Integer.parseInt(p.getValue()));
            } else if ("unit".equals(p.getName())) {
                rp.setUnit(Integer.parseInt(p.getValue()));
            }
        });
        return rp;
    }

    @Override
    public List<Dict> getChildByName(String name) {
        Dict parent = this.getOne(new QueryWrapper<Dict>().lambda().eq(Dict::getLvl, 0).eq(Dict::getName, name));
        if (parent == null) {
            return new ArrayList<>();
        }
        List<Dict> list = this.list(new QueryWrapper<Dict>().lambda().eq(Dict::getPid, parent.getId()).orderByAsc(Dict::getSort));
        return list;
    }

    /**
     * 判断字典是否存在
     */
    private boolean exists(Dict dict){
        List<Dict> dicts = dictMapper.selectList(new QueryWrapper<Dict>().lambda()
                .eq(Dict::getValue, dict.getValue()).isNull(Dict::getPid));
        if (dicts != null && dicts.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 返回自身和所有子节点的（name：value）Map
     * @param dictId
     * @return
     */
    @Override
    public Map<String,String> selectDeepMap(String dictId){
        Set<Dict> dictSet = Sets.newHashSet();
        findVmChildDict(dictSet,dictId);

        Map<String,String> resultMap = dictSet.stream().collect(Collectors.toMap((key->key.getName()),(value->value.getValue())));

        return resultMap;
    }


    private Set<Dict> findVmChildDict(Set<Dict> dictSet,String dictId){

        Dict dict = dictMapper.selectOne(new QueryWrapper<Dict>().lambda().eq(Dict::getId,dictId));
        if(dict != null){
            dictSet.add(dict);
        }

        List<Dict> dictList = dictMapper.selectList(new QueryWrapper<Dict>().lambda().eq(Dict::getPid,dictId));

        dictList.forEach(item->
                findVmChildDict(dictSet,item.getId()));
        return dictSet;
    }


}
