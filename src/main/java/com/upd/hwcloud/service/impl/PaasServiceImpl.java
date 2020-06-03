package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.contains.ServiceStatus;
import com.upd.hwcloud.bean.dto.OnLineServiceDto;
import com.upd.hwcloud.bean.dto.apig.ServiceReturnBean;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.dao.PaasMapper;
import com.upd.hwcloud.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * DaaS 表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
@Service
public class PaasServiceImpl extends ServiceImpl<PaasMapper, Paas> implements IPaasService {

    @Autowired
    private IOperateRecordService operateRecordService;
    @Autowired
    private PaasMapper paasMapper;
    @Autowired
    private IDictService dictService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IFilesService filesService;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(User user, String id, Integer result, String remark) {
        Paas paas = this.getById(id);
        if (result.equals(1)) { // 上线
            paas.setStatus(ReviewStatus.ONLINE.getCode());
            paas.updateById();
            List<OperateRecord> operateRecords =
                    operateRecordService.list(new QueryWrapper<OperateRecord>()
                            .eq("TARGET_ID", id).eq("result","上线"));
            if(operateRecords == null || operateRecords.isEmpty()) {
                operateRecordService.insertLatestOnline(id,user.getIdcard(),"上/下线","上线",remark);
            } else {
                operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "上线", remark);
            }
        } else { // 下线
            paas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            paas.updateById();

            operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "下线", remark);
        }
    }

    @Override
    public Map<String, Object> getFrontData() {
        // 置顶大图类型
        LambdaQueryWrapper<Paas> qw = new QueryWrapper<Paas>().lambda();
        qw.eq(Paas::getStatus, ReviewStatus.ONLINE.getCode());
                //.orderByAsc(Paas::getSort)
                //.orderByDesc(Paas::getModifiedTime)
                //.orderByAsc(Paas::getBuildStatus);
        qw.eq(Paas::getHome, "1");
        List<Paas> mainType = this.list(qw.clone().eq(Paas::getTop, 1).last("order by instr('0,2,1', BUILD_STATUS),SORT,MODIFIED_TIME desc"));

        // 查子类,并进行分组
        List<Map<String, Object>> subType = new ArrayList<>();
        List<Dict> paasType = dictService.getChildByValue("paasType");
        paasType.forEach(type -> {
            // 按照子类类型查询非重点显示应用
            List<Paas> list = this.list(qw.clone().eq(Paas::getSubType, type.getId()).last("order by instr('0,2,1', BUILD_STATUS),SORT,MODIFIED_TIME desc"));
            if (list == null) {
                list = new ArrayList<>();
            }
            Map<String, Object> map = new HashMap<>(2);
            map.put("typeId", type.getId());
            map.put("name", type.getName());
            // 显示分类总数,和更多页面总数保持一致
            map.put("totalCount", list.size());
            // 分类列表,需要去除重点显示应用
            map.put("list", list.stream().filter(it -> !"1".equals(it.getTop())).collect(Collectors.toList()));
            subType.add(map);
        });

        Map<String, Object> res = new HashMap<>();
        res.put("mainType", mainType);
        res.put("subType", subType);
        return res;
    }

    @Override
    public IPage<Paas> getPage(IPage<Paas> page, User user, Integer status, String name, String subType) {
        return paasMapper.getPage(page, user, status, name, subType);
    }

    @Override
    public Paas getDetail(User user, String id) {
        Paas paas = this.getById(id);
        if (paas != null) {
            User creator = userService.findUserByIdcard(paas.getCreator());
            paas.setUser(creator);
            List<Files> filesList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, id));
            paas.setFileList(filesList);
        }
        return paas;
    }

    @Override
    public Set<String> getLabel(String typeId) {
        List<Paas> subpageList = paasMapper.getLabel(typeId, 1, null);
        Set<String> labelSet = subpageList.stream().map(Paas::getTagDesc).collect(Collectors.toSet());

        Set<String> label = labelSet.stream().flatMap(s -> Arrays.stream(s.split("[;；]")))
                .collect(Collectors.toSet());
        return label;
    }

    @Override
    public List<Map<String, Object>> getLabelWithCount(String typeId) {
        List<Paas> subpageList = paasMapper.getLabel(typeId, null, 1);
        Map<String, Set<String>> labelMap = new HashMap<>();
        for (Paas subpage : subpageList) {
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

        Set<String> labelSet = subpageList.stream().map(Paas::getTagDesc).collect(Collectors.toSet());
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
    public List<Paas> getOneClickPage(String typeId, String label, String keyword) {
        return paasMapper.getPageByCondition(typeId, label,null, null, keyword,1);
    }

    @Override
    public List<Paas> search(String keyword) {
        return paasMapper.getPageByCondition(null, null,null, null, keyword,null);
    }

    @Override
    public void savePaas(Paas paas) {
        paas.setId(UUIDUtil.getUUID());
        paas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        paas.insert();
        filesService.refFiles(paas.getFileList(),paas.getId());
    }

    @Override
    public void updatePaas(Paas paas) {
        this.updateById(paas);
        filesService.refFiles(paas.getFileList(),paas.getId());
    }

    @Override
    public IPage<Paas> getMorePage(IPage<Paas> page, String typeId, String keyword) {
        return paasMapper.getPageByCondition(page, typeId, null,1, null, keyword,null);
    }

    @Override
    public List<OnLineServiceDto> getOnlineService() {
        return paasMapper.getOnlineService();
    }
    @Transactional
    @Override
    public Boolean serviceSort(String id, String ope) {
        Paas entity = baseMapper.selectById(id);
        Paas change = null;
        QueryWrapper<Paas> wrapper = new QueryWrapper<Paas>().eq("status",entity.getStatus()).eq("SUB_TYPE",entity.getSubType());
        if ("down".equals(ope)) {
            wrapper.gt("sort", entity.getSort()).orderByAsc("sort");
        }else if ("up".equals(ope)){
            wrapper.lt("sort", entity.getSort()).orderByDesc("sort");
        }
        List<Paas> pres =  baseMapper.selectPage(new Page<Paas>(1, 1),wrapper).getRecords();
        if (pres!=null&&pres.size()==1) {
            change = pres.get(0);
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

    @Override
    public boolean servicePublish2PaaS(ServicePublish servicePublish,ServiceReturnBean returnBean) {
        System.out.println("服务应用申请发布信息--------------"+servicePublish.toString());
        if(servicePublish == null){
            return false;
        }
        Paas paas = null;
        try {
            paas = new Paas();
            paas.setId(UUIDUtil.getUUID());
            //申请人信息
            User user = userService.findUserByIdcard(servicePublish.getCreator());
            paas.setUser(user);
            //服务发布信息
            paas.setName(servicePublish.getServiceName());
            paas.setShortName(servicePublish.getServiceName());
            List<Dict> dctlist = dictService.list(new QueryWrapper<Dict>().lambda().eq(Dict::getName,servicePublish.getCategory()));
            String id = "";
            if(dctlist.size()>0){
                id = dctlist.get(0).getId();
            }
            paas.setCreator(servicePublish.getCreator());
            paas.setSubType(id);
            paas.setServiceGuid(returnBean.getServiceId());
            paas.setAreaName(servicePublish.getAreaName());
            paas.setPoliceCategory(servicePublish.getPoliceCategory());
            paas.setImage(servicePublish.getLogoUrl());
            paas.setDescription(servicePublish.getRemark());
            paas.setTagDesc(servicePublish.getTag());//标签
            paas.setHome(ServiceStatus.CAN_SHOW_HOME.getCode());//首页展示
            paas.setCanApplication(ServiceStatus.CANNOT_APPLY.getCode());//是否能申请
            paas.setSort(1000l);
            paas.setStatus(2l);//已上线
            paas.setBuildStatus(0l);
            paas.setFileList(servicePublish.getFileList());
            System.out.println("生成的pass信息--------"+paas);
            this.save(paas);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
