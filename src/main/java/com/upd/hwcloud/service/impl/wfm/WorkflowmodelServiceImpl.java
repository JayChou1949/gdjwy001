package com.upd.hwcloud.service.impl.wfm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.wfm.*;
import com.upd.hwcloud.dao.wfm.ActivityMapper;
import com.upd.hwcloud.dao.wfm.InstanceMapper;
import com.upd.hwcloud.dao.wfm.WorkflowmodelMapper;
import com.upd.hwcloud.service.wfm.IWorkflowmodelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-04-10
 */
@Service
public class WorkflowmodelServiceImpl extends ServiceImpl<WorkflowmodelMapper, Workflowmodel> implements IWorkflowmodelService {


    private static final Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);
    @Autowired
    WorkflowmodelMapper workflowmodelMapper;
    @Autowired
    InstanceMapper instanceMapper;
    @Autowired
    ActivityMapper activityMapper;

    @Override
    public boolean updateById(Workflowmodel entity) {
        if (entity == null) {
            return false;
        }
        return super.updateById(entity);
    }

    @Override
    public Workflowmodel getByFlowAndAct(String flowId, String act,Integer version) {
        QueryWrapper wrapper = new QueryWrapper();
        if(version != null){
            wrapper.eq("VERSION",version);
        }
        wrapper.eq("workflowid",flowId);
        wrapper.eq("modelname",act);
        return this.getOne(wrapper);
    }
    public Workflowmodel getSubModel(String modelId) {
        List<Workflowmodel> models = workflowmodelMapper.getSubordinateModel(modelId);
        if (models!=null&&models.size()>0)
            return models.get(0);
        return null;
    }
    @Override
    public WorkFlowModelVo getWorkFlowDefinition(String businessId) {
        //通过业务ID查找流程实例
        Instance instance=instanceMapper.selectOne(new QueryWrapper<Instance>().eq("businessid",businessId));
        List<Activity> activities;
        String instanceId="";
        if (instance!=null) {
            instanceId=instance.getId();
            if (!instanceId.equals("")) {
                //获取当前实例活动的环节
                //该实例activityStatus不是已回退 等待 已转发 已呈批 已抢单 已抢占的流转信息
                activities=activityMapper.getResultActivities(instanceId);
            }else {
                return null;
            }
        }else {
            return null;
        }
        Workflowmodel firstModel = null;
        List<Activity> firstActivitys=new ArrayList<>();
        for (Activity activity:activities) {
            //如果流转信息的isStart==0,通过流转信息得环节ID 找到环节
            if (activity.getIsstart()==0) {
                //设置到第一个环节
                firstModel=this.getById(activity.getModelid());
                //添加到第一个流转信息
                firstActivitys.add(activity);
            }
        }
        Activity firstActivity=null;
        //第一个流转信息不为空
        //只有一个流转信息，取第一个
        if (firstActivitys.size()==1) {
            firstActivity=firstActivitys.get(0);
        }else {
            //遍历，取 activitystatus为待办的
            for (Activity activity:firstActivitys) {
                if (activity.getActivitystatus().equals("待办")) {
                    firstActivity=activity;
                }
            }
        }

        //以上都未设置firstActivity，取第一个流转信息列表的第一个流转信息
        if (firstActivity==null) {
            firstActivity=firstActivitys.get(0);
        }

        //设置Model 返回VO
        WorkFlowModelVo modelVo=new WorkFlowModelVo();
        modelVo.setDefaultHandlePerson(firstModel.getDefaulthandleperson());
        modelVo.setHandleModel(firstModel.getHandlemodel());
        modelVo.setId(firstModel.getId());
        modelVo.setModelName(firstModel.getModelname());
        modelVo.setVersion(firstModel.getVersion());
        //从流转信息取的处理时间
        modelVo.setHandleDate(firstActivity.getHandletime());
        //接收时间
        modelVo.setReciveDate(firstActivity.getRecivetime());
        //环节ID
        modelVo.setActivityId(firstActivity.getId());

        modelVo.setIsOnLine(firstModel.getIsonline());
        modelVo.setHandlePerson(firstActivity.getHandlepersonids());
        //环节状态
        modelVo.setModelStatus(firstActivity.getActivitystatus());

        modelVo.setModelStatusClassNme(firstModel.getModelstatusclassnme());
        if (firstActivity.getActivitystatus().equals("已提交")) {
            modelVo.setModelStatusCode("finished");
        }else if (firstActivity.getActivitystatus().equals("待办")) {
            modelVo.setModelStatusCode("underway");
        }else if (firstActivity.getActivitystatus().equals("终止")) {
            modelVo.setModelStatusCode("termination");
        }else {
            modelVo.setModelStatusCode("unfinished");
        }


        //获取下级环节
        List<Workflowmodel> nextModels=workflowmodelMapper.getSubordinateModel(firstModel.getId());
        updateModelInfo(modelVo, nextModels, activities);
        return modelVo;
    }

    /**
     * 递归查找环节处理信息
     * @param parent 父环节
     * @param models 子环节列表
     * @param activities 该实例所有流转信息
     */
    public void updateModelInfo(WorkFlowModelVo parent, List<Workflowmodel> models, List<Activity> activities) {
        logger.info("models -> {}",models);
        //下级环节不为空
        if (models!=null) {
            List<WorkFlowModelVo> nextModelVos=new ArrayList<WorkFlowModelVo>();
            for (Workflowmodel model:models) {
                WorkFlowModelVo workFlowModelVo=new WorkFlowModelVo();

                //设置父级环节ID
                workFlowModelVo.setPreModelId(parent.getId());
                workFlowModelVo.setId(model.getId());
                workFlowModelVo.setModelName(model.getModelname());
                workFlowModelVo.setVersion(model.getVersion());
                workFlowModelVo.setIsOnLine(model.getIsonline());
                workFlowModelVo.setModelStatusClassNme(model.getModelstatusclassnme());

                //遍历所有流转信息
                for (Activity activity:activities) {
                    //匹配环节的流转信息（匹配到就跳出循环）
                    logger.info("model -> {},activity -> {}",model,activity);
                    if (model.getId().equals(activity.getModelid())) {
                        //判断当前环节是否有多个，如果有多个则判断多个中有哪些状态，已终止>待办>已提交
                            String status=isMore(model.getId(), activities);
                            workFlowModelVo.setActivityId(activity.getId());
                            workFlowModelVo.setModelStatus(activity.getActivitystatus());
                            workFlowModelVo.setHandleDate(activity.getHandletime());
                            workFlowModelVo.setReciveDate(activity.getRecivetime());
                            workFlowModelVo.setModelStatusCode(status.split(",")[0]);
                            workFlowModelVo.setHandlePerson(activity.getHandlepersonids());
                            break;
                    }
                }

                workFlowModelVo.setModelStatusClassNme(model.getModelstatusclassnme());
                nextModelVos.add(workFlowModelVo);
                List<Workflowmodel> nextModels=workflowmodelMapper.getSubordinateModel(model.getId());
                updateModelInfo(workFlowModelVo,nextModels,activities);
            }
            parent.setNextModels(nextModelVos);
        }

    }
    //判断当前环节是否有多个，如果有多个则判断多个中有哪些状态，已终止>待办>已提交
    public String isMore(String modelId,List<Activity> activities) {
        //已终止
        boolean isTer=false;
        //待办
        boolean isTask=false;
        //已提交
        boolean isFi=false;
        String activityid="";
        for (Activity activity:activities) {
            if (modelId.equals(activity.getModelid())) {
                if (activity.getActivitystatus().equals("已提交")) {
                    isFi=true;
                }else if (activity.getActivitystatus().equals("待办")) {
                    isTask=true;
                    activityid=activity.getId();
                }else if(activity.getActivitystatus().equals("终止")){
                    isTer=true;
                }
            }
        }
        if (isTer) {
            return "termination";
        }
        if (isTask) {
            return "underway,"+activityid;
        }
        if (isFi) {
            return "finished";
        }
        return "unfinished";
    }

    @Override
    public Integer getMaxVersionOfFlow(String id) {
        return workflowmodelMapper.getMaxVersion(id);
    }
}
