package com.hirisun.cloud.order.service.saas.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.word.WordExportUtil;
import cn.afterturn.easypoi.word.entity.params.ExcelListEntity;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.hirisun.cloud.api.saas.SaasApplicationApi;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.contains.WorkflowNodeAbilityType;
import com.hirisun.cloud.common.enumer.ModelName;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.ExcelUtil;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.util.WorkflowUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.daas.vo.InnerServiceOverview;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.param.FilesParam;
import com.hirisun.cloud.model.saas.vo.SaasApplicationMergeVO;
import com.hirisun.cloud.model.saas.vo.SaasApplicationVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.ActivityVo;
import com.hirisun.cloud.model.workflow.WorkflowActivityVO;
import com.hirisun.cloud.model.workflow.WorkflowInstanceVO;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.order.bean.apply.ApplyFeedback;
import com.hirisun.cloud.order.bean.apply.ApplyInfo;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.continer.IImplHandler;
import com.hirisun.cloud.order.service.apply.ApplyFeedbackService;
import com.hirisun.cloud.order.service.apply.ApplyInfoService;
import com.hirisun.cloud.order.service.apply.ApplyReviewRecordService;
import com.hirisun.cloud.order.service.saas.SaasWorkflowService;
import com.hirisun.cloud.order.vo.ApproveVO;
import com.hirisun.cloud.order.vo.ImplRequest;
import com.hirisun.cloud.order.vo.OrderCode;
import com.hirisun.cloud.redis.lock.DistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SaasWorkflowServiceImpl implements SaasWorkflowService {

    @Autowired
    private DistributeLock lock;

    @Autowired
    private ApplyReviewRecordService applyReviewRecordService;

    @Autowired
    private ApplyFeedbackService applyFeedbackService;

    @Autowired
    private SaasApplicationApi saasApplicationApi;

    @Autowired
    private WorkflowApi workflowApi;

    @Autowired
    private FilesApi filesApi;

    @Autowired
    private ApplyInfoService applyInfoService;

    @Autowired
    private SmsApi smsApi;

    @Override
    public QueryResponseResult<SaasApplicationVO> detail(String id) {
        SaasApplicationMergeVO detail = saasApplicationApi.getDetails(id);
        if (detail == null) {
            return QueryResponseResult.fail("该申请不存在");
        }
        WorkflowInstanceVO instance = workflowApi.getWorkflowInstanceByBusinessId(id);
        if (instance==null) {
            log.error("流程实例未找到");
            return QueryResponseResult.fail("流程实例未找到");
        }
        List<WorkflowNodeVO> nodeVoList = workflowApi.getWorkflowNodeAndActivitys(instance.getVersion(), instance.getWorkflowId(), instance.getId());
        List<ApplyReviewRecord> reviewList= applyReviewRecordService.list(new QueryWrapper<ApplyReviewRecord>().lambda()
                .eq(ApplyReviewRecord::getApplyId, detail.getId())
                .orderByDesc(ApplyReviewRecord::getCreateTime));

        List<ApplyFeedback> feedbackList= applyFeedbackService.list(new QueryWrapper<ApplyFeedback>().lambda()
                .eq(ApplyFeedback::getAppInfoId, detail.getId())
                .orderByDesc(ApplyFeedback::getCreateTime));

        Map map = new HashMap();
        map.put("bizData", detail);
//        map.put("instance", instance);
        map.put("nodeList", nodeVoList);
        map.put("reviewList", reviewList);
        map.put("feedbackList", feedbackList);
        return QueryResponseResult.success(map);
    }

    @Override
    public QueryResponseResult adviser(UserVO user, FallBackVO vo) {
        return applyInfoService.adviser(user, vo);
    }

    @Override
    public QueryResponseResult approve(UserVO user, FallBackVO vo){
        ApplyReviewRecordVO approve = vo.getApplyReviewRecord();
        approve.setCreator(user.getIdcard());
        SaasApplicationMergeVO info = saasApplicationApi.getById(vo.getApplyReviewRecord().getApplyId());
        WorkflowActivityVO activity =workflowApi.getActivityById(vo.getCurrentActivityId());
        if (activity==null) {
            throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
        }
        if ("adviser".equals(activity.getActivityType())){
            Map<String,String> resultMap=workflowApi.adviseActivity(vo.getCurrentActivityId());
            if (resultMap.get("code") != null && resultMap.get("code").equals("200")) {
                approve.setStepName(resultMap.get("nodeName"));
                approve.setWorkflowNodeId(resultMap.get("nodeId"));
                approve.setType(resultMap.get("type"));
                ApplyReviewRecord reviewRecord = new ApplyReviewRecord();
                BeanUtils.copyProperties(approve,reviewRecord);
                if (StringUtils.isEmpty(approve.getId())) {
                    applyReviewRecordService.save(reviewRecord);
                }else{
                    applyReviewRecordService.updateById(reviewRecord);
                }
            }else{
                return QueryResponseResult.fail(resultMap.get("msg"));
            }
            return QueryResponseResult.success(null);
        }
        WorkflowNodeVO curNode = workflowApi.getNodeById(activity.getNodeId());
        Map<String,String> map = new HashMap<>();
        map.put("name", BusinessName.SAAS_RESOURCE);
        map.put("order", info.getOrderNumber());
        if (approve.getResult()!=null&&approve.getResult().equals(1)){
            Map resultMap = workflowApi.advanceActivity(vo.getCurrentActivityId(),map);
            //r数据格式：
            // {code: 200,msg: "操作成功",data:"大数据办审批"}
            WorkflowNodeVO model=null;
            try {
                if (resultMap.get("code") != null && resultMap.get("code").equals("200")) {
                    model = JSON.parseObject(resultMap.get("data").toString(),WorkflowNodeVO.class);//此处转换失败，则直接抛方法内错误
                }else if(resultMap.get("code") != null && resultMap.get("code").equals("201")){
                    return QueryResponseResult.success(null);
                }else{
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
            } catch (Exception e) {
                throw new CustomException(OrderCode.WORKFLOW_MISSING);
            }
            //检查环节名，是否是业务办理，
            boolean isImpl= WorkflowUtil.compareNodeAbility(model.getNodeFeature(), WorkflowNodeAbilityType.APPLY.getCode());
            if (isImpl){
                //下一环节名为业务办理,设置订单状态为 待实施
                info.setStatus(ApplyInfoStatus.IMPL.getCode());
            }else {
                //否则设置订单状态为   科信待审核
                info.setStatus(ApplyInfoStatus.REVIEW.getCode());
            }
            if (ModelName.LVL1_MANAGER.getName().equals(model.getNodeName())) {
                info.setRecheckTime(new Date());
            } else if (isImpl) {
                info.setBigDataTime(new Date());
            }
        }else {
            String modelIds=vo.getFallBackModelIds();
            if (modelIds==null||modelIds.trim().equals("")) {
                log.error("回退环节ID不能为空,回退失败");
                throw new CustomException(OrderCode.FALLBACK_ID_NOT_NULL);
            }
            // 流转到服务台复核或申请人
            WorkflowNodeVO fallbackModel = workflowApi.getNodeById(modelIds);
            //驳回到申请,重新申请
            boolean isApply= WorkflowUtil.compareNodeAbility(fallbackModel.getNodeFeature(), WorkflowNodeAbilityType.APPLY.getCode());
            if (isApply) {
                workflowApi.fallbackOnApproveNotPass(vo, new HashMap());
                info.setStatus(ApplyInfoStatus.INNER_REVIEW.getCode());
            }else{
                workflowApi.fallbackOnApproveNotPass(vo, map);
                //订单状态设置为 科信待审核
                info.setStatus(ApplyInfoStatus.REVIEW.getCode());
            }
        }
        //记录审核记录
        ApplyReviewRecord saveReviewRecord = new ApplyReviewRecord();
        saveReviewRecord.setRemark(approve.getRemark());
        saveReviewRecord.setResult(approve.getResult());
        saveReviewRecord.setApplyId(approve.getApplyId());
        saveReviewRecord.setCreator(user.getIdcard());
        saveReviewRecord.setStepName(curNode.getNodeName());
        saveReviewRecord.setWorkflowNodeId(curNode.getId());
        saveReviewRecord.setType(ApplyReviewRecord.TYPE_AUDIT);
        applyReviewRecordService.save(saveReviewRecord);
        saasApplicationApi.updateById(info);
        saasApplicationApi.updateStatus(info.getId(), info.getStatus());
        return QueryResponseResult.success(null);
    }

    /**
     * 工单审批业务实施
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public QueryResponseResult<ImplRequest> saveImpl(UserVO user, String id, String nodeId, String activityId, ImplRequest implRequest) throws Exception {
        SaasApplicationMergeVO info = saasApplicationApi.getById(id);
        WorkflowActivityVO activity =workflowApi.getActivityById(activityId);
        if (activity==null) {
            throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
        }
        if (implRequest.getResult() == null) {
            return QueryResponseResult.fail("请选择实施结果！");
        }
        if (!"1".equals(implRequest.getResult())) {
            implRequest.setResult("0");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("info", info);
        param.put("implRequest", implRequest);

        String uuid = UUIDUtil.getUUID();
        String lockKey = info.getId().intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                gotoImpl(user, param, activity.getNodeId());
            } else {
                return QueryResponseResult.fail("系统繁忙,请稍后重试!");
            }
        }catch (Exception e){
            throw e;
        }finally {
            lock.unlock(lockKey, uuid);
        }
        if ("1".equals(implRequest.getResult())){
            Map map = new HashMap();
            map.put("depApproveUserIds", info.getCreator());
            workflowApi.advanceActivity(activityId,map);
            saasApplicationApi.updateCarryTime(info.getId());
        }else {
            if (nodeId==null||nodeId.trim().equals("")) {
                return QueryResponseResult.fail("回退环节ID不能为空,回退失败");
            }
            FallBackVO vo = new FallBackVO();
            vo.setCurrentActivityId(activityId);
            vo.setFallBackModelIds(nodeId);
            Map<String,String> map = new HashMap<>();
            map.put("name", BusinessName.SAAS_RESOURCE);
            map.put("order", info.getOrderNumber());
            workflowApi.fallbackOnApproveNotPass(vo, map);
        }
        return QueryResponseResult.success(null);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void gotoImpl(UserVO user, Map<String, Object> param, String modelId) {
        SaasApplicationMergeVO info = (SaasApplicationMergeVO) param.get("info");
        // 添加实施信息
        ImplRequest implRequest = (ImplRequest) param.get("implRequest");
        String result = implRequest.getResult();
        String remark = implRequest.getRemark();

        ApplyReviewRecord reviewInfo = new ApplyReviewRecord();
        reviewInfo.setCreator(user.getIdcard());
        reviewInfo.setResult(Integer.parseInt(result));
        reviewInfo.setRemark(remark);
        reviewInfo.setType("2");
        reviewInfo.setStepName(ModelName.CARRY.getName());
        reviewInfo.setWorkflowNodeId(modelId);
        reviewInfo.setApplyId(info.getId());
        applyReviewRecordService.save(reviewInfo);
        // 实施附件
        refFiles(implRequest.getFileList(), reviewInfo.getId());
        ApplyInfoStatus status;
        if ("0".equals(result)) {
            // 驳回申请
            status = ApplyInfoStatus.REVIEW;
        } else {
            // 实施步骤已完成,修改申请为使用状态
            status = ApplyInfoStatus.USE;
            //通过合并后的id查找申请信息集合，然后依次给申请人发送短信
            List<SaasApplicationVO> saasApplicationList = saasApplicationApi.getListByMergeId(info.getId());
            for (SaasApplicationVO saasApplication:saasApplicationList) {
                smsApi.buildCompleteMessage(saasApplication.getCreator(), BusinessName.SAAS_RESOURCE, saasApplication.getOrderNumber());
            }
        }
        SaasApplicationMergeVO vo = new SaasApplicationMergeVO();
        vo.setId(info.getId());
        vo.setStatus(status.getCode());
        saasApplicationApi.updateByParams(vo);
        saasApplicationApi.updateStatus(info.getId(), status.getCode());
    }

    /**
     * 加办
     * @param user 用户
     * @param approveVO 审核信息
     * @return
     */
    @Override
    public QueryResponseResult add(UserVO user, ApproveVO approveVO) {
        return applyInfoService.add(user, approveVO);
    }

    /**
     * 合并后申请人员列表导出
     * @param id
     * @param request
     * @param response
     */
    @Override
    public void userExport(String id, HttpServletRequest request, HttpServletResponse response) {
        List<SaasApplicationVO> saasApplicationList = saasApplicationApi.getListByMergeId(id);
        if (CollectionUtils.isNotEmpty(saasApplicationList)) {
            for (int i = 0; i < saasApplicationList.size(); i++) {
                SaasApplicationVO sa = saasApplicationList.get(i);
                sa.setId(String.valueOf(i + 1));
            }
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("sa", new ExcelListEntity(saasApplicationList, SaasApplicationVO.class));
//        XWPFDocument doc = WordExportUtil.exportWord07("template/saas_application.docx", map);
//        WordUtil.export(request, response, "广东省公安厅警务云平台通用应用权限审批表", doc);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"人员列表"), SaasApplicationVO.class, saasApplicationList);
        ExcelUtil.export(request, response, "广东省公安厅警务云平台通用应用权限审批表", workbook);
    }
    /**
     * 实施附件，抽取成公共消息
     * @param files
     * @param refId
     */
    private void refFiles(List<FilesVo> files, String refId) {
        if (StringUtils.isEmpty(refId)) {
            return;
        }

        SubpageParam param = new SubpageParam();
        param.setRefId(refId);
        filesApi.remove(param);

        if (files != null && !files.isEmpty()) {
            for (FilesVo f : files) {
                f.setId(null);
                f.setRefId(refId);
            }
            FilesParam filesParam = new FilesParam();
            param.setFiles(files);
            filesApi.saveBatch(filesParam);
        }
    }
}
