package com.upd.hwcloud.service.impl.wfm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.wfm.Activity;
import com.upd.hwcloud.bean.entity.wfm.Instance;
import com.upd.hwcloud.bean.entity.wfm.Workflow;
import com.upd.hwcloud.bean.entity.wfm.Workflowmodel;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.dao.wfm.ActivityMapper;
import com.upd.hwcloud.dao.wfm.InstanceMapper;
import com.upd.hwcloud.dao.wfm.WorkflowMapper;
import com.upd.hwcloud.dao.wfm.WorkflowmodelMapper;
import com.upd.hwcloud.service.IUserService;
import com.upd.hwcloud.service.wfm.IInstanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-04-10
 */
@Service
public class InstanceServiceImpl extends ServiceImpl<InstanceMapper, Instance> implements IInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(InstanceServiceImpl.class);
    @Autowired
    WorkflowmodelMapper workflowmodelMapper;
    @Autowired
    WorkflowMapper workflowMapper;
    @Autowired
    InstanceMapper instanceMapper;
    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    IUserService userService;
    @Override
    public R launchInstanceOfWorkFlow(String createPersonId, String flowId, String businessId) {
        Workflow workFlow = workflowMapper.selectById(flowId);
        logger.info("launch version :{}",workFlow.getVersion());
        if (workFlow == null) {

            throw new BaseException("未找到此流程的定义!", 201);
            //return ResultMessageUtil.error(201, "未找到此流程的定义");
        }
        // 得到流程定义的开始环节
        Workflowmodel workFlowModel = workflowmodelMapper.selectOne(new QueryWrapper<Workflowmodel>().eq("workflowid",flowId).eq("isstart",0).eq("VERSION",workFlow.getVersion()));
        if (workFlowModel == null) {
            //return ResultMessageUtil.error(201, "此流程没有定义开始环节,请联系管理员!");
            throw new BaseException("此流程没有定义开始环节,请联系管理员!", 201);
        }

        User user=userService.findUserByIdcard(createPersonId);

        // 生成实例的ID
        String insId = UUIDUtil.getUUID();
        // 创建流程实例对象
        Instance instance = new Instance();
        instance.setId(insId);
        if (user==null) {
            instance.setCreateperson(createPersonId);
        }else {
            instance.setCreateperson(user.getIdcard());
        }

        instance.setCreatetime(new Date());
        instance.setInstancestatus("办理中");
        instance.setWorkflowid(flowId);
        instance.setBusinessid(businessId);
        instance.setWorkflowversion(workFlowModel.getVersion());

        // 创建开始环节信息
        Activity firstActivity = new Activity();

        //如果是外部提交则user为空，直接存传递过来的申请人名,否则存登陆人名
        if (user==null) {
            firstActivity.setCreatepersonid(createPersonId);
            firstActivity.setHandlepersonids(null);
            firstActivity.setActivitystatus("已提交");
        }else {
            firstActivity.setCreatepersonid(user.getIdcard());
            firstActivity.setHandlepersonids(createPersonId);
            firstActivity.setActivitystatus("待办");
        }
        firstActivity.setCreatetime(new Date());
        firstActivity.setInstanceid(insId);
        firstActivity.setModelid(workFlowModel.getId());
        firstActivity.setIsstart(0);
        firstActivity.setRecivetime(new Date());
        String actId = UUIDUtil.getUUID();
        firstActivity.setId(actId);

        // 保存流程实例
        int createInstanceResult = instanceMapper.insert(instance);
        // 发起流程的第一个环节,申请环节
        int createActiviteResult = activityMapper.insert(firstActivity);
        if (createActiviteResult > 0 && createInstanceResult > 0) {
            return R.ok(firstActivity.getId());
        } else {
            throw new BaseException("发起流程失败", 201);
        }
    }

    @Override
    public Instance getInstanceByBusinessId(String businessId) {
        Instance instance= this.getOne(new QueryWrapper<Instance>().eq("businessid",businessId));
        return instance;
    }
}
