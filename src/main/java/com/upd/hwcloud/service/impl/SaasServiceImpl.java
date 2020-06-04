package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.contains.ServiceStatus;
import com.upd.hwcloud.bean.entity.Dict;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.OperateRecord;
import com.upd.hwcloud.bean.entity.Paas;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.vo.workbench.KeyAndValueVO;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.dao.SaasMapper;
import com.upd.hwcloud.service.IDictService;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IOperateRecordService;
import com.upd.hwcloud.service.ISaasService;
import com.upd.hwcloud.service.IUserService;
import com.upd.hwcloud.service.workbench.impl.CommonHandler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * SaaS 表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
@Service
public class SaasServiceImpl extends ServiceImpl<SaasMapper, Saas> implements ISaasService {

    @Autowired
    private SaasMapper saasMapper;
    @Autowired
    private IDictService dictService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IOperateRecordService operateRecordService;

    @Autowired
    private IFilesService filesService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(User user, String id, Integer result, String remark) {
        Saas saas = this.getById(id);
        if (result.equals(1)) { // 上线
            saas.setStatus(ReviewStatus.ONLINE.getCode());
            saas.updateById();
            List<OperateRecord> operateRecords =
                    operateRecordService.list(new QueryWrapper<OperateRecord>()
                            .eq("TARGET_ID", id).eq("result","上线"));
            if(operateRecords == null || operateRecords.isEmpty()) {
                operateRecordService.insertLatestOnline(id,user.getIdcard(),"上/下线","上线",remark);
            } else {
                operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "上线", remark);
            }
        } else { // 下线
            saas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            saas.updateById();

            operateRecordService.insertRecord(id, user.getIdcard(), "上/下线", "下线", remark);
        }
    }

    @Override
    public  List<Saas>  getServiceFrontData() {


        List<Saas> saasService = this.list(new QueryWrapper<Saas>().lambda().eq(Saas::getServiceFlag,1).eq(Saas::getHome,1).eq(Saas::getStatus,2).orderByAsc(Saas::getSort).orderByDesc(Saas::getName));
        /*Map<String,List<Saas>> res = Maps.newHashMap();
        List<Saas> all = saasMapper.getOnlineList(1);
        if(all != null){
            List<Saas> hot = all.parallelStream().sorted(Comparator.comparing(Saas::getSort,Comparator.reverseOrder())).limit(4).collect(Collectors.toList());
            List<String> hotIds = hot.parallelStream().map(Saas::getId).collect(Collectors.toList());
            res.put("hotService",hot);

            List allSubHot = all.parallelStream().filter(saas->!hotIds.contains(saas.getId())).collect(Collectors.toList());
            res.put("service",allSubHot);
        }*/

        return saasService;
    }

    /**
     * Saas应用
     * @return
     */
    @Override
    public List<Map<String, Object>> getApplicationFrontData(String projectName) {
        List<Saas> all = saasMapper.getOnlineList(0);
        List<Map<String, Object>> subType = Lists.newArrayList();
       // List<Map<String,Object>> areaPoliceType = Lists.newArrayList();
        if(all!=null){
            List<Dict> saasType = dictService.getChildByValue("saasType");
            saasType.forEach(type -> {
                List<Saas> list;
                //List<String> areaPoliceList = new ArrayList<>();
                if("通用应用".equals(type.getName())){
                    list = all.parallelStream().filter(saas -> Objects.equals(type.getId(), saas.getSubType()))
                            //重点应用降序后按排序值升序
                            .sorted(Comparator.comparing(Saas::getTop,Comparator.reverseOrder()).thenComparing(Saas::getSort))
                           // .sorted(Comparator.comparingLong(Saas::getSort))
                            .collect(Collectors.toList());
                    if (list == null) {
                        list = Lists.newArrayList();
                    }
                    Map<String, Object> map = Maps.newHashMap();
                    map.put("typeId", type.getId());
                    map.put("name", type.getName());
                    // 显示分类总数,和更多页面总数保持一致
                    map.put("totalCount", list.size());
                    map.put("list", list.size() > 15 ? list.subList(0, 15) : list);
                    subType.add(map);
                }else if("专项应用".equals(type.getName())){
                    if(StringUtils.isNotBlank(projectName)){
                        list = all.parallelStream().filter(saas ->StringUtils.equals(type.getId(),saas.getSubType()) && StringUtils.equals(saas.getProject(),projectName))
                                .sorted(Comparator.comparing(Saas::getSort)).collect(Collectors.toList());
                        if(list == null){
                            list = Lists.newArrayList();
                        }
                        Map<String, Object> map = Maps.newHashMap();
                        map.put("typeId", type.getId());
                        map.put("name", type.getName());
                        map.put("projectName",projectName);
                        // 显示分类总数,和更多页面总数保持一致
                        map.put("totalCount", list.size());
                        map.put("list", list.size() > 15 ? list.subList(0, 15) : list);
                        subType.add(map);
                    }
                }else if("疫情专区".equals(type.getName())){
                    list = all.parallelStream().filter(saas -> Objects.equals(type.getId(),saas.getSubType()))
                            .sorted(Comparator.comparing(Saas::getSort)).collect(Collectors.toList());
                    if (list == null) {
                        list = Lists.newArrayList();
                    }
                    Map<String, Object> map = Maps.newHashMap();
                    map.put("typeId", type.getId());
                    map.put("name", type.getName());
                    // 显示分类总数,和更多页面总数保持一致
                    map.put("totalCount", list.size());
                    map.put("list", list.size() > 15 ? list.subList(0, 15) : list);
                    subType.add(map);
                }else {
                    if(!"政府机构应用".equals(type.getName())){
                        list = all.parallelStream().filter(saas -> Objects.equals(type.getId(), saas.getSubType()))
                                .sorted(Comparator.comparing(Saas::getViewCount,Comparator.reverseOrder()).thenComparing(Saas::getSort))
                                .collect(Collectors.toList());

                        List<Saas> mostView;
                        Map<String,Long> resMap;
                        List<KeyAndValueVO> keyAndValueVOList = Lists.newArrayList();

                        if(list.size()>6){
                            mostView = list.subList(0,6);
                        }else {
                            mostView = list;
                        }
                        if("警种应用".equals(type.getName())){
                             resMap =  list.parallelStream().filter(saas -> saas.getPoliceCategory() != null &&(saas.getAreaName() == null || "省厅".equals(saas.getAreaName())) ).collect(Collectors.groupingBy(Saas::getPoliceCategory,Collectors.counting()));
                             keyAndValueVOList = groupHandler(resMap);
                             if(keyAndValueVOList.size()>5){
                                 keyAndValueVOList = keyAndValueVOList.subList(0,5);
                             }
                           // areaPoliceList = list.parallelStream().filter(saas->saas.getPoliceCategory()!=null&&("省厅".equals(saas.getAreaName())|| saas.getAreaName()==null) ).map(Saas::getPoliceCategory).distinct().collect(Collectors.toList());

                        }else if("地市应用".equals(type.getName())){
                            resMap =  list.parallelStream().filter(saas -> saas.getAreaName() !=null && !"省厅".equals(saas.getAreaName())).collect(Collectors.groupingBy(Saas::getAreaName,Collectors.counting()));
                            keyAndValueVOList = groupHandler(resMap);
                           // areaPoliceList = list.parallelStream().filter(saas->saas.getAreaName()!=null && !"省厅".equals(saas.getAreaName())).map(Saas::getAreaName).distinct().collect(Collectors.toList());
                        }
                        Map<String,Object> map = Maps.newHashMap();
                        map.put("mostView",mostView);
                        map.put("typeId", type.getId());
                        map.put("name", type.getName());
                        // 显示分类总数,和更多页面总数保持一致
                        map.put("totalCount", list.size());
                        map.put("list",keyAndValueVOList);
                        subType.add(map);

                    }
                }
            });
        }

        List<Saas> pilot = all.parallelStream().filter(saas -> saas.getPilotApp()==1).sorted(Comparator.comparing(Saas::getTop,Comparator.reverseOrder()).thenComparing(Saas::getSort)).collect(Collectors.toList());
        if (pilot == null) {
            pilot = Lists.newArrayList();
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("typeId", "");
        map.put("name", "试点应用");
        // 显示分类总数,和更多页面总数保持一致
        map.put("totalCount", pilot.size());
        map.put("list", pilot.size() > 15 ? pilot.subList(0, 15) : pilot);
        subType.add(map);
        return subType;
    }

    /**
     * Map转首页警种和地区应用VO
     * @return
     */
    private List<KeyAndValueVO> groupHandler(Map<String,Long> map){
        List<KeyAndValueVO> keyAndValueVOList = Lists.newArrayList();
        map.forEach((k,v)->{
          KeyAndValueVO keyAndValueVO = new KeyAndValueVO(k,v);
          keyAndValueVOList.add(keyAndValueVO);
          });
        List<KeyAndValueVO>  sortResult = keyAndValueVOList.parallelStream().sorted(Comparator.comparing(KeyAndValueVO::getValue,Comparator.reverseOrder())).collect(Collectors.toList());
        return sortResult;

    }

    @Override
    public Map<String, Object> getFrontData(Integer serviceFlag) {
        List<Saas> all = saasMapper.getOnlineList(serviceFlag);
        List<Map<String, Object>> subType = Lists.newArrayList();
        List<Saas> hot = Lists.newArrayList();
        if (all != null) {
            // 热门应用(重点显示的前4个)
            hot = all.parallelStream().filter(saas -> Objects.equals(saas.getTop(), "1"))
                   .sorted(Comparator.comparingLong(Saas::getSort))
                    .limit(4).collect(Collectors.toList());
            Set<String> hotIdSet = hot.stream().map(Saas::getId).collect(Collectors.toSet());
            // 查子类,并进行分组
            List<Dict> saasType = dictService.getChildByValue("saasType");
            saasType.forEach(type -> {
                // 按照子类类型过滤应用(通用应用按照排序字段排序)
                List<Saas> list;
                if ("通用应用".equals(type.getName())) {
                    list = all.parallelStream().filter(saas -> Objects.equals(type.getId(), saas.getSubType()))
                            //.sorted(Comparator.comparingLong(Saas::getSort))
                            .collect(Collectors.toList());
                } else {
                    list = all.parallelStream().filter(saas -> Objects.equals(type.getId(), saas.getSubType()))
                            .collect(Collectors.toList());
                }
                if (list == null) {
                    list = Lists.newArrayList();
                }
                Map<String, Object> map = Maps.newHashMap();
                map.put("typeId", type.getId());
                map.put("name", type.getName());
                // 显示分类总数,和更多页面总数保持一致
                map.put("totalCount", list.size());
                // 分类列表,需要去除热门应用
                list = list.stream().filter(it -> !hotIdSet.contains(it.getId())).collect(Collectors.toList());
                map.put("list", list.size() > 15 ? list.subList(0, 15) : list);
                subType.add(map);
            });
        }

        Map<String, Object> res = Maps.newHashMap();
        res.put("mainType", hot);
        res.put("subType", subType);
        return res;
    }

    @Override
    public Set<String> getLabel(String typeId) {
        List<Saas> saasSubpageList = saasMapper.getLabel(typeId,  null);
        Set<String> labelSet = saasSubpageList.stream().map(Saas::getTagDesc).collect(Collectors.toSet());

        Set<String> label = labelSet.stream().flatMap(s -> Arrays.stream(s.split("[;；]")))
                .collect(Collectors.toSet());
        return label;
    }

    @Override
    public List<Map<String, Object>> getLabelWithCount(String typeId) {
        List<Saas> saasSubpageList = saasMapper.getLabel(typeId,  1);
        Map<String, Set<String>> labelMap = new HashMap<>();
        for (Saas subpage : saasSubpageList) {
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

        Set<String> labelSet = saasSubpageList.stream().map(Saas::getTagDesc).collect(Collectors.toSet());
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
    public IPage<Saas> getOneClickPage(IPage<Saas> page, String typeId, String label, String keyword) {
        return saasMapper.getPageByCondition(page, typeId, label, 1, keyword, null, null);
    }

    @Override
    public IPage<Saas> getMorePage(IPage<Saas> page, String typeId, String keyword, String areaName, String policeCategory) {
        return saasMapper.getPageByCondition(page, typeId, null, null, keyword, areaName, policeCategory);
    }

    public IPage<Saas> getServiceMorePage(IPage<Saas> page, String keyword) {
        return saasMapper.getServicePageByCondition(page,keyword);
    }


    @Override
    public List<Saas> saasList(String keyword) {
        return saasMapper.getApplicationApplyPageByCondition(keyword);
    }

    @Override
    public void updateViewCountById(String id) {
        saasMapper.updateViewCountById(id);
    }

    @Override
    public List<Saas> getAppName(String creator) {
        return saasMapper.getAppName(creator);
    }

    @Override
    public Saas getDetailWithSubTypeName(String serviceId) {
        return saasMapper.getDetailWithSubTypeName(serviceId);
    }

    @Override
    public IPage<Saas> getPage(IPage<Saas> page, User user, Integer status, String name, String subType,Integer serviceFlag,Integer pilotApp) {
        return saasMapper.getPage(page, user, status, name, subType,serviceFlag,pilotApp);
    }

    @Override
    public IPage<Saas> getNewPage(IPage<Saas> page, QueryVO queryVO) {
        Map<String,Object> param = Maps.newHashMap();
        param.put("area",queryVO.getArea());
        param.put("police", queryVO.getPoliceCategory());
        param.put("serviceName",CommonHandler.dealNameforQuery(queryVO.getServiceName()));
        param.put("category",queryVO.getCategory());
        param.put("projectName",queryVO.getProjectName());
        param.put("pilot",queryVO.getPilot());
        return saasMapper.getNewPage(page,param);
    }

    @Override
    public Saas getDetail(User user, String id) {
        Saas saas = this.getById(id);
        if (saas != null) {
            User creator = userService.findUserByIdcard(saas.getCreator());
            saas.setUser(creator);
        }
        return saas;
    }
    @Transactional
    @Override
    public Boolean serviceSort(String id, String ope) {
        Saas entity = baseMapper.selectById(id);
        Saas change = null;
        QueryWrapper<Saas> wrapper = new QueryWrapper<Saas>().eq("status",entity.getStatus()).eq("SUB_TYPE",entity.getSubType());
        if ("down".equals(ope)) {
            wrapper.gt("sort", entity.getSort()).orderByAsc("sort");
        }else if ("up".equals(ope)){
            wrapper.lt("sort", entity.getSort()).orderByDesc("sort");
        }
        List<Saas> pres =  baseMapper.selectPage(new Page<Saas>(1, 1),wrapper).getRecords();
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
    public boolean servicePublish2SaaS(ServicePublish servicePublish,String serviceGuid) {
        System.out.println("服务发布申请信息------"+servicePublish.toString());
        if(servicePublish == null){
            return false;
        }
        Saas saas = null;
        try{
            saas = new Saas();
            saas.setId(UUIDUtil.getUUID());
            //申请人信息
            User user = userService.findUserByIdcard(servicePublish.getCreator());
            saas.setUser(user);
            //服务发布信息
            saas.setName(servicePublish.getServiceName());
            saas.setShortName(servicePublish.getServiceName());
            List<Dict> dctlist = dictService.list(new QueryWrapper<Dict>().lambda().eq(Dict::getName,servicePublish.getCategory()));
            String id = "";
            if(dctlist.size()>0){
                id = dctlist.get(0).getId();
            }
            saas.setSubType(id);
            saas.setAreaName(servicePublish.getAreaName());
            saas.setPoliceCategory(servicePublish.getPoliceCategory());
            saas.setImage(servicePublish.getLogoUrl());
            saas.setDescription(servicePublish.getRemark());
            saas.setTagDesc(servicePublish.getTag());//标签
            //saas首页不展示，可申请
            saas.setHome(ServiceStatus.NOT_SHOW_HOME.getCode());//不首页展示
            saas.setCanApplication(ServiceStatus.CAN_APPLY.getCode());//是否能申请
            saas.setSort(1000l);
            saas.setServiceGuid(serviceGuid); //发布成功后返回的服务ID
            saas.setServiceFlag(1); //类型为服务
            saas.setCreator(servicePublish.getCreator()); //设置服务创始人
            saas.setStatus(2l);//已上线
            saas.setBuildStatus(0l);
            System.out.println("生成的saas服务信息---------"+saas);
            this.save(saas);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 应用用户数
     * @param id
     * @return
     */
    @Override
    public Integer userCountOfSaasApplication(String id) {
        return saasMapper.userOfSaasApplication(id);
    }

    /**
     * 应用地市数
     * @param id
     * @return
     */
    @Override
    public Integer areaCountOfSaasApplication(String id) {
        return saasMapper.areaCountOfSaasApplication(id);
    }

    /**
     * 应用警种数
     * @param id
     * @return
     */
    @Override
    public Integer policeCountOfSaasApplication(String id) {
        return saasMapper.policeCountOfSaasApplication(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void hotfix() {
        List<Saas> saasList = this.list(new QueryWrapper<Saas>().lambda().isNotNull(Saas::getImage));
        for(Saas p : saasList){
            Files f = filesService.getOne(new QueryWrapper<Files>().lambda().eq(Files::getUrl,p.getImage()));
            if(f != null){
                p.setRealImage(f.getRealURL());
            }
        }
        if(CollectionUtils.isNotEmpty(saasList)){
            this.updateBatchById(saasList,100);
        }
    }
}
