package com.hirisun.cloud.order.service.epidemic.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.api.user.UserApi;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.common.consts.RedisKey;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.contains.RequestCode;
import com.hirisun.cloud.common.enumer.ModelName;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.ExceptionPrintUtil;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.WorkflowVO;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.bean.epidemic.EpidemicApplication;
import com.hirisun.cloud.order.bean.epidemic.EpidemicApplicationExt;
import com.hirisun.cloud.order.mapper.epidemic.EpidemicApplicationMapper;
import com.hirisun.cloud.order.service.apply.ApplyReviewRecordService;
import com.hirisun.cloud.order.service.apply.ApplyService;
import com.hirisun.cloud.order.service.epidemic.EpidemicApplicationExtService;
import com.hirisun.cloud.order.service.epidemic.EpidemicApplicationService;
import com.hirisun.cloud.order.util.WorkOrderUtils;
import com.hirisun.cloud.order.vo.ImplRequest;
import com.hirisun.cloud.order.vo.OrderCode;
import com.hirisun.cloud.redis.lock.DistributeLock;
import com.hirisun.cloud.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * SaaS资源申请原始信息表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-03
 */
@Slf4j
@Service
public class EpidemicApplicationServiceImpl extends ServiceImpl<EpidemicApplicationMapper, EpidemicApplication> implements EpidemicApplicationService {

    @Autowired
    private WorkOrderUtils workOrderUtils;

    @Autowired
    private RedisService redisService;

    @Autowired
    private FilesApi filesApi;

    @Autowired
    private EpidemicApplicationExtService epidemicApplicationExtService;

    @Autowired
    private ApplyReviewRecordService applyReviewRecordService;

    @Autowired
    private UserApi userApi;

    @Autowired
    private SmsApi smsApi;

    @Autowired
    private DistributeLock lock;

    @Autowired
    private WorkflowApi workflowApi;

    @Autowired
    private ApplyService applyService;

    @Override
    public IPage<EpidemicApplication> getFlowPage(UserVO user, IPage<EpidemicApplication> page, Map<String, Object> param) {
        page = baseMapper.getFlowPage(page, param);
        List<EpidemicApplication> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            workOrderUtils.curHandlerPerson(records,user);
        }
        return page;
    }

    @Override
    public IPage<EpidemicApplication> getFlowPageWithServiceName(UserVO user, IPage<EpidemicApplication> page, Map<String, Object> param) {
        page = baseMapper.getFlowPageWithServiceName(page, param);
        List<EpidemicApplication> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            for (EpidemicApplication record : records) {
                ApplyInfoStatus ais = ApplyInfoStatus.codeOf(record.getStatus());
                // 判断是否能删除
                if (ais != ApplyInfoStatus.DELETE
                        && Objects.equals(user.getIdcard(), record.getCreator())) {
                    record.setCanDelete(true);
                }
            }
        }
        return page;
    }

    @Override
    public void create(UserVO user, EpidemicApplication info) {
        WorkflowVO workflow = workflowApi.getWorkflowByDefaultProcess("EPIDEMIC");
        if(workflow==null){
            throw new CustomException(OrderCode.WORKFLOW_MISSING);
        }
        info.setWorkFlowId(workflow.getId());
        info.setWorkFlowVersion(workflow.getVersion());
        info.setId(null);
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        info.setOrgId(user.getOrgId());
        info.setOrgName(user.getOrgName());
        info.setPostType(user.getPostType());
        info.setMobileWork(user.getMobileWork());
        info.setStatus(ApplyInfoStatus.INNER_REVIEW.getCode());
        String orderNum = redisService.genOrderNum(RedisKey.KEY_ORDER_NUM_PREFIX);
        info.setOrderNumber(orderNum);

        this.save(info);
        this.saveExt(info);
        SubpageParam param = new SubpageParam();
        param.setRefId(info.getId());
        param.setFiles(info.getFileList());
        filesApi.refFiles(param);
    }

    private void saveExt(EpidemicApplication info) {
        List<EpidemicApplicationExt> list = info.getServiceList();
        if (list != null && !list.isEmpty()) {
            list.forEach(saasApplicationExt -> {
                saasApplicationExt.setId(null);
                saasApplicationExt.setMasterId(info.getId());
            });
            epidemicApplicationExtService.saveBatch(list);
        }
    }

    @Override
    public EpidemicApplication getDetails(String id) {
        EpidemicApplication epidemicApplication = this.getById(id);
        if(epidemicApplication == null){
            throw  new CustomException(OrderCode.WORK_ORDER_NOT_NULL);
        }
        List<EpidemicApplicationExt> extList = epidemicApplicationExtService.getListByMasterId(id);
        epidemicApplication.setServiceList(extList);
        SubpageParam param = new SubpageParam();
        param.setRefId(id);
        List<FilesVo> filesList = filesApi.find(param );
        epidemicApplication.setFileList(filesList);
        // 申请人
        UserVO creator = userApi.getUserByIdCard(epidemicApplication.getCreator());
        epidemicApplication.setUser(creator);
        // 审核信息
        List<ApplyReviewRecord> allReviewInfo = applyReviewRecordService.getAllReviewInfoByAppInfoId(id);
        epidemicApplication.setReviewList(allReviewInfo);
        // 实施审批信息
        ApplyReviewRecord implInfo = null;
        ApplyReviewRecord lastReviewInfo = applyReviewRecordService.getLastPassReviewInfoByAppInfoId(id);
        if (lastReviewInfo != null && "2".equals(lastReviewInfo.getType())) {
            // 最近一条审核记录为实施记录
            implInfo = lastReviewInfo;
            param = new SubpageParam();
            param.setRefId(implInfo.getId());
            List<FilesVo> implFileList = filesApi.find(param);
            implInfo.setFileList(implFileList);
            epidemicApplication.setImpl(implInfo);
        }
        return epidemicApplication;
    }

    @Override
    public QueryResponseResult impl(UserVO user,ImplRequest implRequest){
        EpidemicApplication info = this.getById(implRequest.getApplyInfoId());
        if (implRequest.getResult() == null) {
            return QueryResponseResult.fail("请选择实施结果");
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
                applyService.saveImpl(info,user,implRequest);
            } else {
                return QueryResponseResult.fail("系统繁忙,请稍后重试");
            }
        }catch (Exception e){
            log.error("系统错误:{}", ExceptionPrintUtil.getStackTraceInfo(e));

            return QueryResponseResult.fail(CommonCode.SERVER_ERROR);
        }finally {
            lock.unlock(lockKey, uuid);
        }
        if ("1".equals(implRequest.getResult())){
            Map resultMap = workflowApi.advanceActivity(implRequest.getActivityId(),new HashMap());
            if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
                throw new CustomException(OrderCode.FEIGN_METHOD_ERROR);
            }
            smsApi.buildCompleteMessage(info.getCreator(), info.getBusinessName(), info.getOrderNumber());
        }else {
            if (StringUtils.isEmpty(implRequest.getNodeId())) {
                return QueryResponseResult.fail("回退环节ID不能为空,回退失败");
            }
            Map<String,String> map = new HashMap<>();
            map.put("name",info.getBusinessName());
            map.put("order",info.getOrderNumber());
            FallBackVO vo = new FallBackVO();
            vo.setCurrentActivityId(implRequest.getActivityId());
            vo.setFallBackModelIds(implRequest.getNodeId());
            Map<String,String> resultMap=workflowApi.fallbackOnApproveNotPass(vo, map);
            if (resultMap.get("code") != null && !resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                return QueryResponseResult.fail(resultMap.get("msg"));
            }
        }
        return QueryResponseResult.success(null);
    }


    @Override
    public void update(UserVO user, EpidemicApplication info) {
        info.setStatus(null);
        info.setOrderNumber(null);
        if ("0".equals(info.getApplyType())) {
            info.setPoliceCategory("");
        } else {
            info.setAreas("");
        }
        this.updateById(info);
        epidemicApplicationExtService.remove(new QueryWrapper<EpidemicApplicationExt>().lambda()
                .eq(EpidemicApplicationExt::getMasterId, info.getId()));
        this.saveExt(info);
        SubpageParam param = new SubpageParam();
        param.setRefId(info.getId());
        param.setFiles(info.getFileList());
        filesApi.refFiles(param);
    }
}
