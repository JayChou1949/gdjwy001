package com.hirisun.cloud.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hirisun.cloud.common.constant.FormCode;
import com.hirisun.cloud.common.contains.ResourceType;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.workflow.bean.Workflow;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.hirisun.cloud.workflow.bean.WorkflowServiceBinding;
import com.hirisun.cloud.workflow.mapper.WorkflowMapper;
import com.hirisun.cloud.workflow.service.WorkflowNodeService;
import com.hirisun.cloud.workflow.service.WorkflowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.workflow.service.WorkflowServiceBindingService;
import com.hirisun.cloud.workflow.vo.WorkflowCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 流程定义表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Service
public class WorkflowServiceImpl extends ServiceImpl<WorkflowMapper, Workflow> implements WorkflowService {

    private static Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);

    @Autowired
    private WorkflowNodeService workflowNodeService;

    @Autowired
    private WorkflowServiceBindingService workflowServiceBindingService;

    /**
     * 流程类型-省厅 1
     */
    private final int FLOW_TYPE_PROVINCE=1;
    /**
     * 流程类型-省厅 2
     */
    private final int FLOW_TYPE_AREA=2;
    /**
     * 流程类型-省厅 3
     */
    private final int FLOW_TYPE_PROLICE=3;

    /**
     * 流程类型-国家专项 4
     */
    private final int FLOW_TYPE_NATIONAL_PROJECT=4;

    /**
     * 默认区域名 省厅
     */
    private String defaultArea="省厅";


    /**
     * 根据预设类型获取指定的环节
     * @param type 1 申请 2本单位审批 3二级管理员审批 4反馈
     * @param workflowId 工作流id
     * @return
     */
    public WorkflowNode getWorkflowByType(Integer type,String workflowId){
        WorkflowNode node = new WorkflowNode();
        node.setId(UUIDUtil.getUUID());
        String nodeName="";
        Integer nodeSort=1;
        String nodeFeature="1";
        //nodeFeature 环节功能 1可审核 2可加办 3可实施 4可删除 5可修改 6可反馈 7可转发 8可回退
        switch (type) {
            case 1:
                nodeName="申请";
                nodeSort=1;
                nodeFeature="1,5";
                break;
            case 2:
                nodeName="本单位审批";
                nodeSort=2;
                nodeFeature = "1,2,7,8";
                break;
            case 3:
                nodeName="二级管理员审批";
                nodeFeature="1";
                nodeSort=2;
                break;
            case 9:
                nodeName="反馈";
                nodeFeature="6";
                break;
            default:
                break;
        }
        node.setWorkflowId(workflowId);
        node.setNodeName(nodeName);
        node.setNodeSort(nodeSort);
        node.setVersion(0);
        node.setNodeFeature(nodeFeature);
        return node;
    }

    /**
     * 更新或保存工作流
     * @param user 登录用户
     * @param workflow  工作流
     */
    @Transactional
    @Override
    public boolean saveOrUpdateWorkflow(UserVO user, Workflow workflow,String list) {
        if (StringUtils.isEmpty(workflow.getId())) {
            return saveWorkFlow(user,workflow,list);
        }else{
            return updateWorkfLow(user,workflow,list);
        }
    }

    /**
     * 修改工作流 变更流程版本
     * @param user
     * @param workflow
     */
    private boolean updateWorkfLow(UserVO user, Workflow workflow,String list) {
        Integer version = workflow.getVersion() == null ? 0 : workflow.getVersion() + 1;
        List<WorkflowNode> nodeList = JSON.parseArray(list, WorkflowNode.class);
        int sort=1;
        for (WorkflowNode node : nodeList) {
            node.setWorkflowId(workflow.getId());
            node.setNodeSort(sort);
            node.setVersion(version);
            sort++;
        }
        workflow.setVersion(version);
        this.updateById(workflow);
        workflowNodeService.saveBatch(nodeList);
        return true;
    }

    /**
     * 保存工作流
     * @param user
     * @param workflow
     */

    private boolean saveWorkFlow(UserVO user, Workflow workflow,String list) {
        String workflowId = UUIDUtil.getUUID();
        List<WorkflowNode> nodeList = JSON.parseArray(list, WorkflowNode.class);
        LinkedList<WorkflowNode> allNodeList = new LinkedList<>();
        if (nodeList == null || nodeList.size() == 0) {
            return false;
        }
        /**
         * 0.新增工作流
         * 1.所有环节由前端传入，所有环节功能在前端赋予
         */
        Integer sort=1;
        for (WorkflowNode node : nodeList) {
            node.setNodeSort(sort);
            node.setWorkflowId(workflowId);
            allNodeList.add(node);
            sort++;
        }
        workflow.setCreator(user.getName());
        workflow.setCreatorId(user.getIdCard());
        workflow.setCreatorOrgId(user.getOrgId());
        workflow.setId(workflowId);
        this.save(workflow);
        workflowNodeService.saveBatch(allNodeList);
        return true;
    }


    /**
     * IPDS订单选择流程（s->saasService）
     * @param resourceType 资源类型 IAAS(1),DAAS(2),PAAS(3),SAAS(4),SAAS_SERVICE(5);
     * @param area 区域，省厅或指定地市
     * @param serviceId 服务id
     * @param nationalSpecialProject 国家专项
     */
    @Override
    public Workflow chooseWorkFlow(Integer resourceType, String area, String policeCategory, String serviceId, String nationalSpecialProject) {
        /**
         * 选择流程配置流程：
         * 1.判断是否有国家专项 ? 判断应用是否绑定了国家专项的配置 ？ 走国家专项配置流程 ： 走流程配置中类型为国家专项配置的流程
         * 2.判断是否选择省厅 ？ 判断应用是否绑定了选择的警种的配置 ？ 走绑定了选择警种的配置 ：判断应用是否绑定了省厅配置 ？走绑定的省厅的配置 ：流程配置默认流程是否是该警种 ？ 走该警种默认流程 ： 走默认配置的省厅流程
         * 3.判断应用是否绑定该地市的配置 ？ 走绑定了该地市的配置 ： 流程配置默认流程是否是该地市 ？走该地市默认流程 ：走默认配置的省厅流程
         */
        if (!StringUtils.isNotBlank(nationalSpecialProject)&&StringUtils.isBlank(area)){//国家专项不为空走国家专项
            //如果找不到国家专项专用流程，走默认省厅流程
            area =  defaultArea;
        }
        //如果ServiceId不为空=>说明是Iaas或Paas服务，服务可配置单独流程
        if(serviceId != null){
            logger.info("==IaasOrPaas==");
            return IaasOrPaasHandle(resourceType,area,policeCategory,serviceId,nationalSpecialProject);
        }else {
            logger.info("DaasOrSaasService");
            return DaasOrSaasServiceHandle(resourceType,area,policeCategory,nationalSpecialProject);
        }
    }

    /**
     * Iaas或Paas服务处理
     * @param resourceType
     * @param area
     * @param serviceId
     * @return
     */
    private Workflow IaasOrPaasHandle(Integer resourceType, String area, String policeCategory, String serviceId, String nationalSpecialProject){
        if (StringUtils.isNotBlank(nationalSpecialProject)){
            return handleWorkflowOfPaasOrIaas(resourceType, serviceId, nationalSpecialProject, null, null);
        }
        if(StringUtils.equals(area,defaultArea)){
            if(StringUtils.isBlank(policeCategory)){
                return  handleWorkflowOfPaasOrIaas(resourceType,serviceId,null,null,null);
            }else {
                return handleWorkflowOfPaasOrIaas(resourceType,serviceId,null,policeCategory,null);
            }
        }else {
            return handleWorkflowOfPaasOrIaas(resourceType,serviceId,null,null,area);
        }
    }

    /**
     * 方法整理
     * @param resourceType 资源类型
     * @param serviceId 服务id
     * @param nationalSpecialProject    国家专项
     * @param policeCategory 警种
     *
     */
    private Workflow handleWorkflowOfPaasOrIaas(Integer resourceType,String serviceId,String nationalSpecialProject,String policeCategory,String area) {
        /**
         * 1.查询服务是否绑定配置流程
         * 2.获取流程ID集合
         * 3.判断是否有指定区域类型的流程，否则都走省厅默认流程配置
         */
        List<WorkflowServiceBinding> result = getWorkflowBindingList(serviceId);
        if(CollectionUtils.isNotEmpty(result)){

            List<String> workflowIds = result.stream().map(WorkflowServiceBinding::getWorkflowId).collect(Collectors.toList());
            LambdaQueryWrapper<Workflow> wrapper = new QueryWrapper<Workflow>().lambda()
                    .in(Workflow::getId, workflowIds);
            if (!StringUtils.isEmpty(nationalSpecialProject)) {
                wrapper.eq(Workflow::getNationalSpecialProject, nationalSpecialProject);
            }
            if(!StringUtils.isEmpty(policeCategory)){
                wrapper.eq(Workflow::getPoliceCategory, policeCategory);
            }
            if(!StringUtils.isEmpty(area)){
                wrapper.eq(Workflow::getArea, area);
            }
            if (StringUtils.isEmpty(nationalSpecialProject) && StringUtils.isEmpty(policeCategory) && StringUtils.isEmpty(area)) {
                if(!StringUtils.isEmpty(area)){
                    wrapper.eq(Workflow::getArea, defaultArea);
                }
            }
            List<Workflow> flowList = this.list(wrapper);
            if (CollectionUtils.isNotEmpty(flowList)) {
                return flowList.get(0);
            }else{
                if (!StringUtils.isEmpty(policeCategory)) {
                    //查看服务是否配置省厅的
                    List<Workflow> workflowList = this.list(new QueryWrapper<Workflow>().lambda().in(Workflow::getId,workflowIds));
                    //该服务是否存在省厅流程(警种为空)
                    Optional<Workflow> optionalProvince = workflowList.stream().filter(wf->StringUtils.equals(wf.getArea(),defaultArea)&&StringUtils.isBlank(wf.getPoliceCategory())).findFirst();
                    if(optionalProvince.isPresent()){
                        return optionalProvince.get();
                    }
                    //获取该警种默认流程
                    Workflow defaultPoliceFlow  = getDefaultFlow(resourceType,FLOW_TYPE_PROLICE,policeCategory);
                    if(defaultPoliceFlow != null){
                        return defaultPoliceFlow;
                    }
                    getDefaultFlow(resourceType,FLOW_TYPE_PROVINCE,null);

                }
                if (!StringUtils.isEmpty(area)) {
                    //获取该类资源地市默认流程
                    Workflow areaDefault = getDefaultFlow(resourceType,FLOW_TYPE_AREA ,area);
                    if(areaDefault != null){
                        return areaDefault;
                    }
                    //该服务是否存在省厅流程(警种为空)
                    Optional<Workflow> optionalProvince = flowList.stream().filter(wf->StringUtils.equals(wf.getArea(),defaultArea)&&StringUtils.isBlank(wf.getPoliceCategory())).findFirst();
                    if(optionalProvince.isPresent()){
                        logger.info("====服务省厅流程===");
                        return optionalProvince.get();
                    }
                }
            }
        }
        if (!StringUtils.isEmpty(nationalSpecialProject)) {
            return getDefaultFlow(resourceType,FLOW_TYPE_NATIONAL_PROJECT, nationalSpecialProject);
        }
        if (!StringUtils.isEmpty(policeCategory)) {
            Workflow defaultPoliceFlow  = getDefaultFlow(resourceType,FLOW_TYPE_PROLICE,policeCategory);
            if(defaultPoliceFlow != null){
                return defaultPoliceFlow;
            }
            return getDefaultFlow(resourceType,FLOW_TYPE_PROVINCE,null);
        }
        if (!StringUtils.isEmpty(area)) {
            Workflow defaultArea = getDefaultFlow(resourceType,FLOW_TYPE_AREA,area);
            if(defaultArea != null){
                return defaultArea;
            }
            return getDefaultFlow(resourceType,FLOW_TYPE_PROVINCE,null);
        }
        return null;
    }

    /**
     * Daas或SaasService服务处理
     * @param area
     * @return
     */
    private Workflow DaasOrSaasServiceHandle(Integer resourceType, String area, String policeCategory, String nationalSpecialProject){

        //todo: 二级门户改造-新增国家专项维度
        if (StringUtils.isNotBlank(nationalSpecialProject)){
            logger.debug("DaaS SaaS服务 获取默认国家专项流程");
            return getDefaultFlow(resourceType,FLOW_TYPE_NATIONAL_PROJECT,nationalSpecialProject);
        }else {
            if(StringUtils.equals(area,defaultArea)){
                if(StringUtils.isNotBlank(policeCategory)){
                    logger.debug("DaaS SaaS服务 获取默认警种流程");
                    Workflow defaultPoliceFlow =  getDefaultFlow(resourceType,FLOW_TYPE_PROLICE,policeCategory);
                    if(defaultPoliceFlow == null){
                        logger.debug("未配置警种默认流程，使用省厅默认流程");
                        return getDefaultFlow(resourceType,FLOW_TYPE_PROVINCE,null);
                    }
                    logger.debug("配置为警种默认流程");
                    return defaultPoliceFlow;
                }
                return  getDefaultFlow(resourceType,FLOW_TYPE_PROVINCE,null);
            }else {
                if(StringUtils.isBlank(area)){
                    throw new CustomException(WorkflowCode.CART_AREA_CAN_NOT_NULL);
                }
                Workflow areaDefault = getDefaultFlow(resourceType,FLOW_TYPE_AREA,area);
                if(areaDefault == null) {
                    return getDefaultFlow(resourceType,FLOW_TYPE_PROVINCE,null);
                }
                return areaDefault;
            }
        }
    }





    /**
     * 查询该服务是否有配置流程
     * @param serviceId
     * @return
     */
    public List<WorkflowServiceBinding> getWorkflowBindingList(String serviceId) {
        return workflowServiceBindingService.list(new QueryWrapper<WorkflowServiceBinding>().lambda().eq(WorkflowServiceBinding::getServiceId,serviceId));
    }

    /**
     * 获取省厅默认流程
     * @param resourceType 资源类型
     * @return
     */
    private Workflow getDefaultFlow(Integer resourceType,int flowType,String flotTypeName){
        String type=FormCode.serviceResourceTypeKeyMap.get(resourceType);
        LambdaQueryWrapper<Workflow> wrapper = new QueryWrapper<Workflow>().lambda()
                .eq(Workflow::getFlowStatus, 0)
                .eq(Workflow::getDefaultProcess, type);
        if (flowType == FLOW_TYPE_PROVINCE) {
            logger.info("===获取省厅默认 {}===",type);
            wrapper.eq(Workflow::getArea, defaultArea)
                    .isNull(Workflow::getPoliceCategory);
        }else if (flowType == FLOW_TYPE_AREA) {
            logger.info("===获取地市默认:{} ,{}===",flotTypeName,type);
            wrapper.eq(Workflow::getArea, flotTypeName)
                    .isNull(Workflow::getPoliceCategory);
        }else if (flowType == FLOW_TYPE_PROLICE) {
            logger.info("获取{}警种 {} 默认流程 ->",flotTypeName,type);
            wrapper.eq(Workflow::getPoliceCategory, flotTypeName)
                    .eq(Workflow::getArea, defaultArea);
        }else if (flowType == FLOW_TYPE_NATIONAL_PROJECT) {
            logger.info("获取{}国家专项 {} 默认流程 ->",flotTypeName,type);
            wrapper.eq(Workflow::getNationalSpecialProject, flotTypeName);
        }
        return this.getOne(wrapper);
    }
}
