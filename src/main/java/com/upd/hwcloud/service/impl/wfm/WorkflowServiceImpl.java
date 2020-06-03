package com.upd.hwcloud.service.impl.wfm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ModelName;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ServiceWorkFlow;
import com.upd.hwcloud.bean.entity.wfm.Workflow;
import com.upd.hwcloud.bean.entity.wfm.Workflowmodel;
import com.upd.hwcloud.bean.vo.KeyAndValueStr;
import com.upd.hwcloud.bean.vo.wfm.WorkFlowVo;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.dao.wfm.WorkflowMapper;
import com.upd.hwcloud.service.application.IServiceWorkFlowService;
import com.upd.hwcloud.service.wfm.IWorkflowService;
import com.upd.hwcloud.service.wfm.IWorkflowmodelService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-04-10
 */
@Service
public class WorkflowServiceImpl extends ServiceImpl<WorkflowMapper, Workflow> implements IWorkflowService {

    private static final List<String> SPECIAL_DEFAULT_PROCESS = Arrays.asList("ZYSB", "ZCSAAS", "SAAS", "PUBLISH", "SAAS_POWER_RECOVER","RECOVER");

    private static final Logger log = LoggerFactory.getLogger(WorkflowServiceImpl.class);

    @Autowired
    private IWorkflowmodelService workflowmodelService;

    @Autowired
    private WorkflowMapper workflowMapper;

    @Autowired
    private IServiceWorkFlowService serviceWorkFlowService;

    @Transactional
    @Override
    public Workflow saveFlow(User user, Workflow flow) {
        flow.setCreator(user.getName());
        String flowId = UUIDUtil.getUUID();
        flow.setId(flowId);

        //如果是新增默认流程
        if (flow.getDefaultProcess()!=null&&!"".equals(flow.getDefaultProcess())){

            dealOldDefault(flow.getDefaultProcess(),flow.getArea(),flow.getPoliceCategory(),flowId);
        }
        this.save(flow);
        List<Workflowmodel> models = new ArrayList<>();
        //反馈
        Workflowmodel feedback = new Workflowmodel(flowId,ModelName.FEEDBACK.getName(),null);
        feedback.setVersion(flow.getVersion());
        models.add(feedback);
        //业务办理
        Workflowmodel carry = flow.getCarry();
        String carryId = UUIDUtil.getUUID();
        carry.setId(carryId);
        carry.setModelname(ModelName.CARRY.getName());
        carry.setNextmodelids(feedback.getId());
        carry.setWorkflowid(flowId);
        carry.setVersion(flow.getVersion());
        models.add(carry);
        //科信审批
        Workflowmodel approve =  flow.getApprove();
        String approveId = UUIDUtil.getUUID();
        approve.setId(approveId);
        approve.setWorkflowid(flowId);
        approve.setModelname(ModelName.APPROVE.getName());
        approve.setVersion(flow.getVersion());
        approve.setNextmodelids(carryId);
        models.add(approve);
        //服务台复核
        Workflowmodel recheck = flow.getRecheck();
        String recheckId = UUIDUtil.getUUID();
        recheck.setId(recheckId);
        recheck.setModelname(ModelName.RECHECK.getName());
        recheck.setWorkflowid(flowId);
        recheck.setNextmodelids(approveId);
        recheck.setVersion(flow.getVersion());
        models.add(recheck);
        //本单位审批
        Workflowmodel depApprove = new Workflowmodel(flowId,ModelName.DEP_APPROVE.getName(),recheckId);
        depApprove.setVersion(flow.getVersion());
        models.add(depApprove);
        //申请
        Workflowmodel apply = new Workflowmodel(flowId,ModelName.APPLY.getName(),depApprove.getId());
        apply.setVersion(0);
        apply.setIsstart(0);
        models.add(apply);
        workflowmodelService.saveBatch( models);
        return flow;
    }


    /**
     * 地区唯一性确认(服务配置流程用)
     * @param workFlowIds
     * @return
     */
    @Override
    public boolean distinctAreaCheck(List<String> workFlowIds) {
        //获取对应流程集合
        List<Workflow> workflowList = this.list(new QueryWrapper<Workflow>().lambda().in(Workflow::getId,workFlowIds));
        //地市去重后的数据
        long area = workflowList.stream().filter(item->StringUtils.isBlank(item.getPoliceCategory())).map(Workflow::getArea).distinct().count();
        log.debug("area num -> {}",area);
        long police = workflowList.stream().map(Workflow::getPoliceCategory).filter(item->StringUtils.isNotBlank(item)).distinct().count();
        log.debug("police num ->{}",police);
        return area + police == workFlowIds.size()?true:false;
    }

    /**
     * IPDS订单选择流程（s->saasService）
     * @param type
     * @param area
     * @param serviceId
     * @return
     */
    @Override
    public Workflow chooseWorkFlow(ResourceType type, String area,String policeCategory,String serviceId) {

        //默认省厅流程
        if(StringUtils.isBlank(area)){
            area =  "省厅";
        }

        //如果ServiceId不为空=>说明是Iaas或Paas服务，服务可配置单独流程
        if(serviceId != null){
            log.info("==IaasOrPaas==");
            return IaasOrPaasHandle(type,area,policeCategory,serviceId);
        }else {
            //Daas或SaasService => 只有地市或省厅默认流程
            log.info("DaasOrSaasService");
            return DaasOrSaasServiceHandle(type,area,policeCategory);
        }
    }

    /**
     * Iaas或Paas服务处理
     * @param type
     * @param area
     * @param serviceId
     * @return
     */
    private Workflow IaasOrPaasHandle(ResourceType type, String area,String policeCategory,String serviceId){
        if(StringUtils.equals(area,"省厅")){
            log.info("====IP省厅==");
            if(StringUtils.isBlank(policeCategory)){
                log.debug("进入省厅PaaS IaaS处理流程");
                return  handleProvinceOfPaasOrIaas(type,serviceId);
            }else {
                log.debug("进入警种PaaS IaaS处理流程");
                return handlerPoliceOfPaasOrIaas(type,policeCategory,serviceId);
            }
        }else {
            if(StringUtils.isBlank(area)){
                throw  new BaseException("地区名为空,请确认订单信息");
            }
            log.info("===IP地市:{}===",area);
            return handleAreaOfPaasOrIaas(type,serviceId,area);
        }
    }

    /**
     * Daas或SaasService服务处理
     * @param type
     * @param area
     * @return
     */
    private Workflow DaasOrSaasServiceHandle(ResourceType type,String area,String policeCategory){
        if(StringUtils.equals(area,"省厅")){
            if(StringUtils.isNotBlank(policeCategory)){
                log.debug("DaaS SaaS服务 获取默认警种流程");
                Workflow defaultPoliceFlow =  getDefaultPoliceFlow(type,policeCategory);
                if(defaultPoliceFlow == null){
                    log.debug("未配置警种默认流程，使用省厅默认流程");
                    return getDefaultProvinceFlow(type);
                }
                log.debug("配置为警种默认流程");
                return defaultPoliceFlow;
            }
            return  getDefaultProvinceFlow(type);
        }else {
            if(StringUtils.isBlank(area)){
                throw  new BaseException("地区名为空,请确认订单信息");
            }
            Workflow areaDefault = getDefaultAreaFlow(type,area);
            if(areaDefault == null) {
                return getDefaultProvinceFlow(type);
            }
            return areaDefault;
        }
    }





    /**
     * 省厅流程处理
     * @param type  资源类型
     * @param serviceId 资源ID
     * @return
     */
    private Workflow handleProvinceOfPaasOrIaas(ResourceType type,String serviceId){

        //查询服务是否有配置流程
        List<ServiceWorkFlow> result = serviceWorkFlowService.list(new QueryWrapper<ServiceWorkFlow>().lambda().eq(ServiceWorkFlow::getServiceId,serviceId));
        //有
        if(CollectionUtils.isNotEmpty(result)){
            //获取流程ID集合
            List<String> workflowIds = result.stream().map(ServiceWorkFlow::getWorkFlowId).collect(Collectors.toList());
            //判断是否有省厅流程
            Workflow workflow = this.getOne(new QueryWrapper<Workflow>().lambda().in(Workflow::getId,workflowIds).eq(Workflow::getArea,"省厅"));
            log.info("workflow -> {}",workflow);
            if(workflow == null){
                //无 则使用省厅该资源默认流程
                return getDefaultProvinceFlow(type);
            }
            log.info("===服务配置省厅流程===");
            return workflow;
        }else {
            //服务没有配置流程 直接使用省厅该类资源的默认流程
            return getDefaultProvinceFlow(type);
        }

    }

    /**
     * PaaS IaaS警种流程处理
     * @param type 资源类型
     * @param policeCategory 警种
     * @param serviceId 服务Id
     * @return workflow
     */
    private Workflow handlerPoliceOfPaasOrIaas(ResourceType type,String policeCategory,String serviceId){
        //查询服务是否有配置流程
        List<ServiceWorkFlow> result = serviceWorkFlowService.list(new QueryWrapper<ServiceWorkFlow>().lambda().eq(ServiceWorkFlow::getServiceId,serviceId));
        //有
        if(CollectionUtils.isNotEmpty(result)) {
            //获取流程ID集合
            List<String> workflowIds = result.stream().map(ServiceWorkFlow::getWorkFlowId).collect(Collectors.toList());
            //判断是否有警种流程
            Workflow workflow = this.getOne(new QueryWrapper<Workflow>().lambda().in(Workflow::getId, workflowIds).eq(Workflow::getPoliceCategory, policeCategory));
            log.info("workflow ->{}",workflow);
            if (workflow == null) {
                log.info("服务:{} 未配置警种:{}流程",serviceId,policeCategory);
                //无
                //获取该警种默认流程
                Workflow defaultPoliceFlow  = getDefaultPoliceFlow(type,policeCategory);
                if(defaultPoliceFlow != null){
                    log.info("使用警种:{}默认流程 ->{}",policeCategory,defaultPoliceFlow);
                    return defaultPoliceFlow;
                }
                //无警种默认流程 返回省厅流程
                log.info("未配置{}警种{}默认流程，使用省厅默认流程",policeCategory,type.toString());
                return getDefaultProvinceFlow(type);
            }
            log.info("使用服务配置流程：{}",workflow);
            return workflow;
        }else {
            log.info("未配置{}警种{}默认流程，使用省厅默认流程",policeCategory,type.toString());
            return getDefaultProvinceFlow(type);
        }
    }

    /**
     * 地市流程处理
     * @param type 资源类型
     * @param serviceId 服务ID
     * @param area 地区
     * @return
     */
    private Workflow handleAreaOfPaasOrIaas(ResourceType type,String serviceId,String area){

        //查询该服务是否有配置流程
        List<ServiceWorkFlow> result = serviceWorkFlowService.list(new QueryWrapper<ServiceWorkFlow>().lambda().eq(ServiceWorkFlow::getServiceId,serviceId));
        //有
        if(CollectionUtils.isNotEmpty(result)){
            //获取流程ID集合
            List<String> workflowIds = result.stream().map(ServiceWorkFlow::getWorkFlowId).collect(Collectors.toList());
            //配置流程集合
            List<Workflow> workflowList = this.list(new QueryWrapper<Workflow>().lambda().in(Workflow::getId,workflowIds));
            //是否存在属于该地市的流程
            Optional<Workflow> optionalAreaflow = workflowList.stream().filter(wf->StringUtils.equals(wf.getArea(),area)).findFirst();

            //如果存在，返回
            if(optionalAreaflow.isPresent()){
                log.info("===服务配置地市流程===");
                return optionalAreaflow.get();
            }else {
                //获取该类资源地市默认流程
                Workflow areaDefault = getDefaultAreaFlow(type,area);
                if(areaDefault == null){
                    //该服务是否存在省厅流程(警种为空)
                    Optional<Workflow> optionalProvince = workflowList.stream().filter(wf->StringUtils.equals(wf.getArea(),"省厅")&&StringUtils.isBlank(wf.getPoliceCategory())).findFirst();
                    if(optionalProvince.isPresent()){
                        log.info("====服务省厅流程===");
                        return optionalProvince.get();
                    }else {
                        return getDefaultProvinceFlow(type);
                    }
                }
                return  areaDefault;
            }
        }else {
            //获取地市默认流程
            Workflow defaultArea = getDefaultAreaFlow(type,area);
            if(defaultArea != null){
                return defaultArea;
            }
            //无地市默认流程-》使用省厅流程
            return getDefaultProvinceFlow(type);
        }

    }

    /**
     * 获取省厅默认流程
     * @param type 资源类型
     * @return
     */
    private Workflow getDefaultProvinceFlow(ResourceType type){
        log.info("===获取省厅默认 {}===",type.toString());
        Workflow defaultFlow = this.getOne(new QueryWrapper<Workflow>().lambda().eq(Workflow::getFlowStatus,0).eq(Workflow::getDefaultProcess,type.toString()).eq(Workflow::getArea,"省厅").isNull(Workflow::getPoliceCategory));
        return defaultFlow;
    }

    private Workflow getDefaultAreaFlow(ResourceType type,String area){
        log.info("===获取地市默认:{} ,{}===",area,type.toString());
        Workflow defaultFlow = this.getOne(new QueryWrapper<Workflow>().lambda().eq(Workflow::getFlowStatus,0).eq(Workflow::getDefaultProcess,type.toString()).eq(Workflow::getArea,area).isNull(Workflow::getPoliceCategory));
        return defaultFlow;
    }

    private Workflow getDefaultPoliceFlow(ResourceType type,String policeCategory){
        log.info("获取{}警种 {} 默认流程 ->",policeCategory,type.toString());
        Workflow defaultFlow  = this.getOne(new QueryWrapper<Workflow>().lambda().eq(Workflow::getFlowStatus,0).eq(Workflow::getDefaultProcess,type.toString()).eq(Workflow::getPoliceCategory,policeCategory).eq(Workflow::getArea,"省厅"));
        return defaultFlow;
    }



    @Override
    public Map<String, Boolean> checkZysbAndRecover(String idCard) {
        Map<String,Boolean> status = Maps.newHashMap();
        List<KeyAndValueStr>  list = workflowMapper.getHandlersMap();
        list.forEach(kv->{
            if(kv.getValue()==null){
                status.put(kv.getKey(),false);
            }else {
                if("ZYSB".equals(kv.getKey())){
                    if(kv.getValue().contains(idCard)){
                        status.put(kv.getKey(),true);
                    }else {
                        status.put(kv.getKey(),false);
                    }
                }
                if("SAAS_POWER_RECOVER".equals(kv.getKey())){
                    if(kv.getValue().contains(idCard)){
                        status.put(kv.getKey(),true);
                    }else {
                        status.put(kv.getKey(),false);
                    }
                }
            }
        });
        return status;
    }

    @Transactional
    @Override
    public Workflow updateFlow(User user, Workflow flow) {
        if (flow.getDefaultProcess()!=null&&!"".equals(flow.getDefaultProcess())){
            dealOldDefault(flow.getDefaultProcess(),flow.getArea(),flow.getPoliceCategory(),flow.getId());
        }
        if (SPECIAL_DEFAULT_PROCESS.contains(flow.getDefaultProcess())) {
            flow.setFlowStatus(1);
        }

        this.updateById(flow);
        workflowmodelService.updateById(flow.getCarry());
        workflowmodelService.updateById(flow.getApprove());
        workflowmodelService.updateById(flow.getRecheck());
        workflowmodelService.updateById(flow.getLvl1Manager());
        workflowmodelService.updateById(flow.getBigData());
        workflowmodelService.updateById(flow.getOnline());
        return flow;
    }

    /**
     * 带版本流程修改-beta
     * @param flow
     * @return
     */
    @Transactional
    @Override
    public boolean updateFlowBeta(Workflow flow){
        if (flow.getDefaultProcess()!=null&&!"".equals(flow.getDefaultProcess())){
            dealOldDefault(flow.getDefaultProcess(),flow.getArea(),flow.getPoliceCategory(),flow.getId());
        }
        if (SPECIAL_DEFAULT_PROCESS.contains(flow.getDefaultProcess())) {
            flow.setFlowStatus(1);
        }
        //有序环节数组
        LinkedList<Workflowmodel> sortedModels = getSortedModels(flow.getId(),flow.getVersion());

        //更新的环节组装成数组
        List<Workflowmodel> updateModelList = composeUpdateModels(flow);

        String uuid = UUIDUtil.getUUID();

        //修改了环节 1.插入新环节 2.更新版本号;
        if(isModifiedModels(sortedModels,updateModelList)){
            log.info("环节处理人变动，更新版本");
            flow.setVersion(flow.getVersion()+1);
            for (Workflowmodel item : sortedModels) {
                Optional<Workflowmodel> opt  = updateModelList.parallelStream().filter(e -> StringUtils.equals(e.getId(),item.getId())).findFirst();
                //如果是可配置的环节，更新处理人
                if(opt.isPresent()){
                    Workflowmodel o = opt.get();
                    item.setDefaulthandleperson(o.getDefaulthandleperson());
                    item.setAdviserperson(o.getAdviserperson());
                    item.setNoticeperon(o.getNoticeperon());
                    item.setId(uuid);
                    item.setVersion(flow.getVersion());
                }else { //只更新ID
                    item.setId(uuid);
                    item.setVersion(flow.getVersion());
                }
                //如果有下一个环节
                if(item.getNextmodelids() != null){
                    uuid = UUIDUtil.getUUID();
                    item.setNextmodelids(uuid);
                }
            }
            log.debug("final models -> {}",sortedModels);
            workflowmodelService.saveBatch(sortedModels);
        }
        log.debug("final flow -> {}",flow);
        return this.updateById(flow);
    }


    /**
     * 判断环节是否修改
     * @param sortedModel 原
     * @param updateModelList 更新
     * @return  true 修改过 false 未修改
     */
    private boolean isModifiedModels(LinkedList<Workflowmodel> sortedModel,List<Workflowmodel> updateModelList){
        List<Workflowmodel> sortedModelList = new ArrayList<>(sortedModel);


        for (Workflowmodel sortModel : sortedModelList) {
            for (Workflowmodel updateModel : updateModelList) {
                if(StringUtils.equals(sortModel.getId(),updateModel.getId())){
                    if(!checkModifiedPersons(sortModel.getDefaulthandleperson(),updateModel.getDefaulthandleperson())){
                        return true;
                    }
                    if(!checkModifiedPersons(sortModel.getAdviserperson(),updateModel.getAdviserperson())){
                        return true;
                    }
                    if(!checkModifiedPersons(sortModel.getNoticeperon(),updateModel.getNoticeperon())){
                        return true;
                    }
                }
            }
        }

        return false;

    }


    /**
     * 处理人是否改动
     * @param origin
     * @param update
     * @return true 未改动，false改动
     */
    private boolean checkModifiedPersons(String origin,String update){
        if(origin != null && update != null){ //都不为空比较
            List<String> originList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(origin);
            List<String> updateList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(update);
            //字面值相同，String的Hashcode相同
            List<String> originSortList  = originList.stream().sorted(Comparator.comparing(String::hashCode)).collect(Collectors.toList());
            List<String> updateSortList = updateList.stream().sorted(Comparator.comparing(String::hashCode)).collect(Collectors.toList());
            String originStr = originSortList.toString();
            String updateStr = updateSortList.toString();
            return StringUtils.equals(originStr,updateStr);
        }else if (StringUtils.isBlank(origin) && StringUtils.isBlank(update)) { //都未空返回未改动
            return true;
        }
        return false;
    }

    /**L
     * 拼装更新实体不为空的环节为数组
     * @param workflow
     * @return
     */
    private List<Workflowmodel> composeUpdateModels(Workflow workflow){
        List<Workflowmodel> updateModels  = Lists.newArrayList();
        if(workflow.getCarry()!=null){
            updateModels.add(workflow.getCarry());
        }
        if(workflow.getApprove() != null){
            updateModels.add(workflow.getApprove());
        }
        if(workflow.getRecheck() != null){
            updateModels.add(workflow.getRecheck());
        }

        if(workflow.getLvl1Manager() != null){
            updateModels.add(workflow.getLvl1Manager());
        }
        if(workflow.getBigData()!=null){
            updateModels.add(workflow.getBigData());
        }
        if(workflow.getOnline() != null){
            updateModels.add(workflow.getOnline());
        }
        return updateModels;
    }


    /**
     *  处理旧默认流程，所有非本流程的旧流程默认标志置为空（含逻辑删除的默认流程）
     * @param defaultProcess
     * @param area
     * @param flowId
     */
    private void dealOldDefault(String defaultProcess,String area,String policeCategory,String flowId){
        List<Workflow> defaultWorkFlowList = Lists.newArrayList();
        if(StringUtils.isBlank(policeCategory)){ //如果警种字段不是空，按警种处理默认流程
            defaultWorkFlowList = this.list(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS",defaultProcess).eq("area",area).ne("id",flowId));
        }else {
            defaultWorkFlowList = this.list(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS",defaultProcess).eq("POLICE_CATEGORY",policeCategory).ne("ID",flowId));
        }
        if(CollectionUtils.isNotEmpty(defaultWorkFlowList)){
            defaultWorkFlowList.forEach(item->{
                item.setDefaultProcess("");
            });
            this.updateBatchById(defaultWorkFlowList);
        }
    }

    /**
     * 获取环节有序集合
     * @param workflowId 流程id
     * @param version 版本
     * @return
     */
    private LinkedList<Workflowmodel> getSortedModels(String workflowId,Integer version){
        List<Workflowmodel> nowVersionModels = getNowVersionModels(workflowId,version);

        LinkedList<Workflowmodel> sortedLinked = Lists.newLinkedList();

        Workflowmodel startModel = getStartModel(nowVersionModels);

        sortedModel(nowVersionModels,sortedLinked,startModel);
        return sortedLinked;
    }

    @Override
    public Workflow getDetail(String id,Integer version) {
        Workflow flow = this.getById(id);
        if ("ZYSB".equals(flow.getDefaultProcess())) {//资源上报
            flow.setRecheck(workflowmodelService.getByFlowAndAct(id,ModelName.RECHECK.getName(),version));
            flow.setApprove(workflowmodelService.getByFlowAndAct(id,ModelName.APPROVE.getName(),version));
        } else if ("ZCSAAS".equals(flow.getDefaultProcess())) {//SAAS服务注册流程
            flow.setRecheck(workflowmodelService.getByFlowAndAct(id,ModelName.RECHECK.getName(),version));
            flow.setApprove(workflowmodelService.getByFlowAndAct(id,ModelName.APPROVE.getName(),version));
            flow.setOnline(workflowmodelService.getByFlowAndAct(id,ModelName.ONLINE.getName(),version));
        } else if ("SAAS".equals(flow.getDefaultProcess())) {//SaaS资源申请流程
            flow.setRecheck(workflowmodelService.getByFlowAndAct(id,ModelName.RECHECK.getName(),version));
            flow.setLvl1Manager(workflowmodelService.getByFlowAndAct(id,ModelName.LVL1_MANAGER.getName(),version));
            flow.setBigData(workflowmodelService.getByFlowAndAct(id,ModelName.BIG_DATA.getName(),version));
            flow.setCarry(workflowmodelService.getByFlowAndAct(id,ModelName.CARRY.getName(),version));
        }else if ("SAAS_POWER_RECOVER".equals(flow.getDefaultProcess())) { //saas资源回收流程
            flow.setRecheck(workflowmodelService.getByFlowAndAct(id,ModelName.RECHECK.getName(),version));
            flow.setBigData(workflowmodelService.getByFlowAndAct(id,ModelName.BIG_DATA.getName(),version));
            flow.setCarry(workflowmodelService.getByFlowAndAct(id,ModelName.CARRY.getName(),version));
        } else if("RECOVER".equals(flow.getDefaultProcess())){
            flow.setRecovered(workflowmodelService.getByFlowAndAct(id,ModelName.RECOVERED.getName(),version));
            flow.setApprove(workflowmodelService.getByFlowAndAct(id,ModelName.APPROVE.getName(),version));
            flow.setCarry(workflowmodelService.getByFlowAndAct(id,ModelName.CARRY.getName(),version));
        } else{
            flow.setRecheck(workflowmodelService.getByFlowAndAct(id,ModelName.RECHECK.getName(),version));
            flow.setApprove(workflowmodelService.getByFlowAndAct(id,ModelName.APPROVE.getName(),version));
            flow.setCarry(workflowmodelService.getByFlowAndAct(id,ModelName.CARRY.getName(),version));
        }
        flow.setVersion(version);
        return flow;
    }


    /**
     * 封装流程VO
     * @param id workflow-id
     * @param version 版本号
     * @return
     */
    @Override
    public WorkFlowVo getFlowVo(String id,Integer version){
        //版本为空，使用最新版本环节模型
        if(version == null){
            version = workflowmodelService.getMaxVersionOfFlow(id);
        }

        //获取流程定义信息
        Workflow workflow = this.getById(id);


        //构造vo，从定义填充基本信息
        WorkFlowVo vo = new WorkFlowVo();
        vo.setName(workflow.getWorkflowname());
        vo.setVersion(version);
        vo.setDescription(workflow.getRoutername());

        LinkedList<Workflowmodel> sortedLinked = getSortedModels(id,version);



        //去掉不显示的环节
        offShowModel(sortedLinked);
        log.info("after offShow: ");
        printList(sortedLinked);
        vo.setModel(sortedLinked);

        return vo;
    }


    /**
     * 获取目前版本环节
     * @param workflowId 环节id
     * @param version 版本
     * @return
     */
    private List<Workflowmodel> getNowVersionModels(String workflowId,Integer version){
        List<Workflowmodel> nowVersionFlow;
        if(version == null){
            nowVersionFlow = workflowmodelService.list(new QueryWrapper<Workflowmodel>().lambda().eq(Workflowmodel::getWorkflowid,workflowId).isNull(Workflowmodel::getVersion));
        }else {
            nowVersionFlow = workflowmodelService.list(new QueryWrapper<Workflowmodel>().lambda().eq(Workflowmodel::getWorkflowid,workflowId).eq(Workflowmodel::getVersion,version));

        }
        return nowVersionFlow;
    }

    /**
     * 递归对环节排序
     * @param originList 该流程全部环节信息（出于减少数据库查询次数考虑）
     * @param linkedList  用于存放有序结果的集合
     * @param workflowmodel 当前环节
     */
    private void sortedModel(List<Workflowmodel> originList,LinkedList<Workflowmodel> linkedList,Workflowmodel workflowmodel){
        if(StringUtils.isNotBlank(workflowmodel.getNextmodelids())){
            Optional<Workflowmodel> opt = originList.parallelStream().filter(item->StringUtils.equals(workflowmodel.getNextmodelids(),item.getId())).findFirst();
            if(opt.isPresent()){
                //存在下个环节，递归
                sortedModel(originList,linkedList,opt.get());
            }else {
                throw  new BaseException("Can not find next model data:"+workflowmodel.getNextmodelids());
            }
        }


        //注意是从最后环节往前开始存入集合的，应该使用addFirst
        linkedList.addFirst(workflowmodel);


    }

    /**
     * 不显示本单位审批、申请、或二级管理员审批(申请创建人即为处理人，本单位处理人由用户选择，二级管理员审批，处理人为本地市警种管理员)
     * 迭代器删除（或使用stream filter方式）
     * @param list
     */
    private void offShowModel(LinkedList<Workflowmodel> list){
        Iterator<Workflowmodel> it = list.iterator();

        while(it.hasNext()){
            Workflowmodel workflowmodel = it.next();
            if(StringUtils.equals(workflowmodel.getModelname(),ModelName.DEP_APPROVE.getName()) || StringUtils.equals(workflowmodel.getModelname(),ModelName.APPLY.getName())
                    || StringUtils.equals(workflowmodel.getModelname(),ModelName.LVL2_MANAGER.getName())){
                it.remove();
            }
        }
    }

    /**
     * 获取开始环节
     * @param list
     * @return
     */
    private Workflowmodel getStartModel(List<Workflowmodel> list){
        for (Workflowmodel workflowmodel : list) {
            if(workflowmodel.getIsstart() == null){
                continue;
            }else {
                if(workflowmodel.getIsstart().intValue() == 0){
                    return workflowmodel;
                }
            }
        }
        return null;
    }

    private void printList(List<Workflowmodel> list){
        list.forEach(item->log.info(item.getModelname()));
    }

    @Override
    public void deleteFlow(String id) {
        Workflow flow =  this.getById(id);
        flow.setFlowStatus(1);
        this.updateById(flow);
    }
}
