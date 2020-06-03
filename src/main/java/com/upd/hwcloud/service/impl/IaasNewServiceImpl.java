package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.dao.IaasNewMapper;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IIaasNewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.service.IOperateRecordService;
import com.upd.hwcloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * IaaS 表 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
@Service
public class IaasNewServiceImpl extends ServiceImpl<IaasNewMapper, IaasNew> implements IIaasNewService {

    @Autowired
    private IOperateRecordService operateRecordService;
    @Autowired
    private IaasNewMapper iaasMapper;
    @Autowired
    private IFilesService filesService;

    @Autowired
    private IUserService userService;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(User user, String id, Integer result, String remark) {
        IaasNew iaas = this.getById(id);
        if (result.equals(1)) { // 上线
            iaas.setStatus(ReviewStatus.ONLINE.getCode());
            iaas.updateById();

            operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "上线", remark);
        } else { // 下线
            iaas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            iaas.updateById();

            operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "下线", remark);
        }
    }

    @Override
    public IPage<IaasNew> getPage(IPage<IaasNew> page, User user, Integer status, String name, String subType) {
        return iaasMapper.getPage(page, user, status, name, subType);
    }

    @Override
    public IaasNew getDetail(User user, String id) {
        IaasNew iaas = this.getById(id);
        if (iaas != null) {
            User creator = userService.findUserByIdcard(iaas.getCreator());
            iaas.setUser(creator);
            List<Files> filesList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, id));
            iaas.setFileList(filesList);
        }
        return iaas;
    }
    @Override
    public List<Map<String, Object>> getLabelWithCount() {
        List<IaasNew> subpageList = iaasMapper.getLabel(null, 1);
        Map<String, Set<String>> labelMap = new HashMap<>();
        for (IaasNew subpage : subpageList) {
            String serviceId = subpage.getId();
            String label = subpage.getTagDesc();
            if (labelMap.get(serviceId) == null) {
                Set<String> collect = Arrays.stream(label.split("[;；]")).collect(Collectors.toSet());
                labelMap.put(serviceId, collect);
            } else {
                Set<String> collect = Arrays.stream(label.split("[;；]")).collect(Collectors.toSet());
                labelMap.get(serviceId).addAll(collect);
            }
        }

        Set<String> labelSet = subpageList.stream().map(IaasNew::getTagDesc).collect(Collectors.toSet());
        Set<String> label = labelSet.stream().flatMap(s -> Arrays.stream(s.split("[;；]")))
                .collect(Collectors.toSet());

        Map<String, Integer> count = new HashMap<>();
        for (String s : label) {
            for (Map.Entry<String, Set<String>> entry : labelMap.entrySet()) {
                if (entry.getValue().contains(s)) {
                    if (count.get(s) == null) {
                        count.put(s, 1);
                    } else {
                        count.put(s, count.get(s) + 1);
                    }
                }
            }
        }
        ArrayList<Map.Entry<String, Integer>> collect = new ArrayList<>(count.entrySet());
        collect.sort(Comparator.comparing(Map.Entry::getValue));
        Collections.reverse(collect);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Integer> e : collect) {
            Map<String, Object> m = new HashMap<>(2);
            m.put("label", e.getKey());
            m.put("count", e.getValue());
            result.add(m);
        }

        return result;
    }
    @Override
    public List<IaasNew> getOneClickPage(String label,String keyword) {
        return iaasMapper.getPageByCondition(label,null, 1, keyword);
    }
    @Override
    public List<IaasNew> getFrontPage() {
        return iaasMapper.getPageByCondition(null,1, null, null);
    }
    @Transactional
    @Override
    public Boolean serviceSort(String id, String ope) {
        IaasNew entity = baseMapper.selectById(id);
        IaasNew change = null;
        if ("down".equals(ope)) {
            List<IaasNew> nexts = baseMapper.selectPage(new Page<IaasNew>(1, 1), new QueryWrapper<IaasNew>().eq("status",entity.getStatus()).gt("sort", entity.getSort()).orderByAsc("sort")).getRecords();
            if (nexts!=null&&nexts.size()==1){
                change = nexts.get(0);
            }
        }else if ("up".equals(ope)){
            List<IaasNew> pres =  baseMapper.selectPage(new Page<IaasNew>(1, 1), new QueryWrapper<IaasNew>().eq("status",entity.getStatus()).lt("sort", entity.getSort()).orderByDesc("sort")).getRecords();
            if (pres!=null&&pres.size()==1){
                change = pres.get(0);
            }
        }
        if (change!=null){
            Long sort = change.getSort();
            change.setSort(entity.getSort());
            entity.setSort(sort);
            baseMapper.updateById(entity);
            baseMapper.updateById(change);
        }
        return true;
    }
}
