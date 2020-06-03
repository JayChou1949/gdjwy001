package com.upd.hwcloud.controller.backend.tenantReport;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.BusinessName;
import com.upd.hwcloud.bean.contains.ModelName;
import com.upd.hwcloud.bean.dto.IaasZysbExport;
import com.upd.hwcloud.bean.entity.IaasZysb;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.wfm.*;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.resourceReport.export.ReportExport;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.lock.DistributeLock;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.IIaasZysbService;
import com.upd.hwcloud.service.application.IServiceLimitService;
import com.upd.hwcloud.service.msg.MessageProvider;
import com.upd.hwcloud.service.wfm.IActivityService;
import com.upd.hwcloud.service.wfm.IInstanceService;
import com.upd.hwcloud.service.wfm.IWorkflowService;
import com.upd.hwcloud.service.wfm.IWorkflowmodelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.upd.hwcloud.bean.contains.ApplicationInfoStatus.INNER_REVIEW;
import static com.upd.hwcloud.bean.contains.ApplicationInfoStatus.REVIEW_REJECT;

/**
 * <p>
 * 申请信息表 前端控制器
 * </p>
 *
 * @author zwb
 * @since 2019-05-22
 */
@Api(description = "iaas资源上报")
@RestController
@RequestMapping("/iaasZysb")
public class IaasZysbController {

    @Autowired
    private DistributeLock lock;
    @Autowired
    private IIaasZysbService iaasZysbService;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private IInstanceService instanceService;
    @Autowired
    private IWorkflowmodelService workflowmodelService;
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private IServiceLimitService serviceLimitService;
    @Autowired
    private MessageProvider messageProvider;

    private String getBusinessName() {
        return BusinessName.IAAS_ZYSB;
    }

    @ApiOperation(value = "新建申请")
    @ApiImplicitParam(name="userId", value="部门内审核人身份证号", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/create/{userId}", method = RequestMethod.POST)
    public R create(@LoginUser User user, @RequestBody IaasZysb info,@PathVariable String userId) throws IOException {
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","ZYSB"));
        if (workflow==null){
            return R.error("未配置流程");
        }
        info.setWorkFlowId(workflow.getId());
        info.setWorkFlowVersion(workflow.getVersion());
        iaasZysbService.save(user,info);
        R r= instanceService.launchInstanceOfWorkFlow(user.getIdcard(),info.getWorkFlowId(),info.getId());

        Workflowmodel workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",workflow.getId()).eq("modelname", ModelName.DEP_APPROVE.getName()).eq("VERSION",workflow.getVersion()));

        Map<String, String> modelMapToPerson = new HashMap<>();
        modelMapToPerson.put(workflowmodel.getId(), userId);
        AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
        advanceBeanVO.setCurrentActivityId(r.get("data").toString());
        advanceBeanVO.setModelMapToPerson(modelMapToPerson);

        Map<String,String> map = new HashMap<>();
        map.put("name", getBusinessName());
        map.put("order", info.getOrderNumber());
        activityService.advanceCurrentActivity(advanceBeanVO, map);
        messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, getBusinessName(), info.getOrderNumber()));
        return R.ok("发起流程成功");
    }
  
    @ApiOperation(value = "修改申请信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@LoginUser User user, @RequestBody IaasZysb info) throws IOException {
        iaasZysbService.update(user,info);
        return R.ok();
    }

    @ApiOperation(value = "申请详情")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R viewDetailsApply(@LoginUser User user,@PathVariable String id) {

        IaasZysb info = iaasZysbService.getDetails(id);
        if (info == null) {
            return R.error("该申请不存在");
        }
        Instance instance = instanceService.getInstanceByBusinessId(id);
        Activity activity = null;
        int finished = 0;
        if (null!=instance){
            info.setWorkFlowId(instance.getWorkflowid());
            info.setWorkFlowVersion(instance.getWorkflowversion());
            List<Activity> activitys = activityService.list(new QueryWrapper<Activity>().lambda()
                    .eq(Activity::getInstanceid,instance.getId()).eq(Activity::getHandlepersonids,user.getIdcard()).eq(Activity::getActivitystatus,"待办").isNull(Activity::getActivitytype));
            if (null==activitys||activitys.size()==0){
                activitys = activityService.list(new QueryWrapper<Activity>().lambda()
                        .eq(Activity::getInstanceid,instance.getId()).eq(Activity::getHandlepersonids,user.getIdcard()).eq(Activity::getActivitystatus,"待办").eq(Activity::getActivitytype,"adviser"));
            }
            if (null!=activitys&&activitys.size()>0) activity = activitys.get(0);
            if ("已完成".equals(instance.getInstancestatus())){
                finished = 1;
            }
        }
        String activityId = "";
        if (activity!=null) {
            activityId = activity.getId();
            if ("adviser".equals(activity.getActivitytype())){
                info.setCanAdviser(true);
                info.setCanReview(true);
            }else{

                Workflowmodel workflowmodel=workflowmodelService.getById(activity.getModelid());
                if (null==workflowmodel){
                    return R.error("未找到流程环节");
                }
                String  modelName=workflowmodel.getModelname();
                if (modelName.equals(ModelName.APPLY.getName())||modelName.equals(ModelName.RECHECK.getName()))
                    info.setCanEdit(true);

                if (modelName.equals(ModelName.DEP_APPROVE.getName())||modelName.equals(ModelName.RECHECK.getName())||modelName.equals(ModelName.APPROVE.getName())) {
                    info.setCanTrans(true);
                    info.setCanReview(true);
                    if (!modelName.equals(ModelName.DEP_APPROVE.getName())) {
                        info.setCanAdd(true);
                    }
                }
                if (modelName.equals(ModelName.DEP_APPROVE.getName())) {
                    info.setCanFall(true);
                }
            }

        }
        List<AppReviewInfo> allReviewInfo = info.getReviewList();
        Map bean = new HashMap<>();
        if (allReviewInfo!=null){
            Map<String, List<AppReviewInfo>> reviews = allReviewInfo.stream().collect(Collectors.groupingBy(a->a.getFlowStepId()+""));
            bean.put("review",reviews);
        }

        WorkFlowModelVo modelVo=workflowmodelService.getWorkFlowDefinition(id);

        if (modelVo!=null) {
            bean.put("model",modelVo);
            bean.put("bizData",info);
            bean.put("activityId",activityId);
            bean.put("finished",finished);
            return R.ok(bean);
        }else {
            return R.error("未找到流程");
        }

    }


    @ApiOperation(value = "后台管理页面删除")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public R deleteById(@LoginUser User user, @PathVariable String id) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                IaasZysb iaasZysb = iaasZysbService.getById(id);
                if (!Objects.equals(user.getIdcard(), iaasZysb.getCreator())) {
                    throw new BaseException("只能删除自己的申请!");
                }
                // 逻辑删除,并设置相应的状态
                iaasZysb.setStatus( ApplicationInfoStatus.DELETE.getCode());
                iaasZysbService.updateById(iaasZysb);
                activityService.terminationInstanceOfWorkFlow(id);
            } else {
                return R.error();
            }
        } catch (Exception e) {
            return R.error();
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return R.ok();
    }

    @ApiOperation(value = "审核驳回后提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type", value="审核类型,inner:部门内审核 kx:科信审核", required = true, defaultValue = "kx", paramType="form", dataType="String"),
            @ApiImplicitParam(name="id", value="申请单id,多个使用逗号分隔", required = true, paramType="form", dataType="String"),
            @ApiImplicitParam(name="userIds", value="审核人id,多个使用逗号分隔", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R submit(@LoginUser User user, @RequestParam("id") String id,
                    @RequestParam(value = "type", defaultValue = "kx") String type,
                    @RequestParam(value = "userIds", required = false) String userIds) {
        if (StringUtils.isEmpty(id)) {
            return R.error("未找到数据!");
        }

        List<String> userIdList = null;
        if ("inner".equals(type)) {
            if (StringUtils.isEmpty(userIds)) {
                return R.error("请选择审核人!");
            } else {
                userIdList = Arrays.asList(userIds.split(","));
                if (userIdList.isEmpty()) {
                    return R.error("请选择审核人!");
                }
            }
        }
        IaasZysb info = iaasZysbService.getById(id);

        Instance instance =instanceService.getInstanceByBusinessId(info.getId());
        if (null==instance){
            throw new BaseException("流程实例未找到");
        }
        Activity firstActivity = activityService.getOne(new QueryWrapper<Activity>().eq("activitystatus","待办")
                .eq("isstart",0).eq("instanceid",instance.getId()));
        String flowId = instance.getWorkflowid();
        Map<String, String> modelMapToPerson = new HashMap<String, String>();
        info.setModifiedTime(new Date());
        Workflowmodel workflowmodel = new Workflowmodel();
        if ("kx".equals(type)) {
            info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
            workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",flowId).eq("modelname", ModelName.RECHECK.getName()));

        }else {
            info.setStatus(INNER_REVIEW.getCode());
            workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",flowId).eq("modelname", ModelName.DEP_APPROVE.getName()));

        }
        modelMapToPerson.put(workflowmodel.getId(), userIds);
        AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
        advanceBeanVO.setCurrentActivityId(firstActivity.getId());
        advanceBeanVO.setModelMapToPerson(modelMapToPerson);
        Map<String,String> map = new HashMap<>();
        map.put("name", getBusinessName());
        map.put("order", info.getOrderNumber());
        activityService.advanceCurrentActivity(advanceBeanVO, map);
        iaasZysbService.updateById(info);
        return R.ok();
    }
    @ApiOperation(value = "转发")
    @ApiImplicitParams({
            @ApiImplicitParam(name="activityId", value="当前环节id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="userIds", value="转发审核人id,多个使用逗号分隔", required = true, paramType="form", dataType="String")
    })
    @RequestMapping(value = "/forward/{activityId}", method = RequestMethod.POST)
    public R forward(@LoginUser User user, @PathVariable String activityId,
                     @RequestParam(value = "userIds", required = true) String userIds) {

        String uuid = UUIDUtil.getUUID();
        String lockKey = activityId.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                return activityService.forward(activityId, userIds);
            } else {
                return R.error();
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
    }


    @ApiOperation(value = "參與人意見")
    @ApiImplicitParams({
            @ApiImplicitParam(name="activityId", value="当前环节id", required = true, paramType="path", dataType="String"),
    })
    @RequestMapping(value = "/adviser", method = RequestMethod.POST)
    public R adviser(@LoginUser User user,
                     @RequestBody FallBackVO vo) {

        AppReviewInfo approve = vo.getApprove();
        approve.setCreator(user.getIdcard());
        activityService.adviseActivity(vo.getCurrentActivityId(), approve);
        return R.ok();
    }


    @ApiOperation(value = "审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name="activityId", value="当前环节id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="userIds", value="转发审核人id,多个使用逗号分隔", required = true, paramType="form", dataType="String")
    })
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public R approve(@LoginUser User user,
                     @RequestBody FallBackVO vo) {

        AppReviewInfo approve = vo.getApprove();
        approve.setCreator(user.getIdcard());
        IaasZysb info = iaasZysbService.getById(vo.getApprove().getAppInfoId());
        Activity activity =activityService.getById(vo.getCurrentActivityId());
        if ("adviser".equals(activity.getActivitytype())){
            activityService.adviseActivity(vo.getCurrentActivityId(), approve);
            return R.ok();
        }
        Map<String,String> map = new HashMap<>();
        map.put("name", getBusinessName());
        map.put("order", info.getOrderNumber());
        if ("1".equals(approve.getResult())){
            R r = activityService.advanceCurrentActivity(vo.getCurrentActivityId(), approve, map);
            Object model = r.get("data");
            if ("finished".equals(model)){
                //添加到限额
                serviceLimitService.increaseQuota(info.getId(),info.getAreas(),info.getPoliceCategory());
                info.setStatus(ApplicationInfoStatus.USE.getCode());
                messageProvider.sendMessageAsync(messageProvider.buildCompleteMessage(info.getCreator(), getBusinessName(), info.getOrderNumber()));
            }else {
                info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
            }
        }else {
            String modelIds=vo.getFallBackModelIds();
            if (modelIds==null||modelIds.trim().equals("")) {
                return R.error(201, "回退环节ID不能为空,回退失败");
            }
            // 流转到服务台复核或申请人
            Workflowmodel fallbackModel = workflowmodelService.getById(modelIds);
            if (ModelName.RECHECK.getName().equals(fallbackModel.getModelname())) {
                activityService.fallbackOnApproveNotPass(vo, map);
                info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
            } else {
                activityService.fallbackOnApproveNotPass(vo, null);
                info.setStatus(REVIEW_REJECT.getCode());
                messageProvider.sendMessageAsync(messageProvider.buildRejectMessage(info.getCreator(), getBusinessName()));
            }
        }
        iaasZysbService.updateById(info);
        return R.ok();
    }
    @ApiOperation(value = "回退")
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public R reject(@LoginUser User user,
                    @RequestBody FallBackVO vo) {

        AppReviewInfo approve = vo.getApprove();
        approve.setCreator(user.getIdcard());
        IaasZysb info = iaasZysbService.getById(approve.getAppInfoId());
        if(null==info){
            return R.error("未找到待办数据");
        }
        activityService.fallbackCurrentActivity(vo);
        if (ApplicationInfoStatus.IMPL.getCode().equals(info.getStatus())){
            info.setStatus(ApplicationInfoStatus.IMPL_REJECT.getCode());
        }else {
            info.setStatus(REVIEW_REJECT.getCode());
        }
        iaasZysbService.updateById(info);
        return R.ok();
    }

    @ApiOperation(value = "加办")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public R review(@LoginUser User user, @RequestBody ApproveVo appVo) {

        String uuid = UUIDUtil.getUUID();
        String lockKey = appVo.getCurrentActivityId().intern();
        R r = R.error();
        try {
            if (lock.lock(lockKey, uuid)) {
                r =  activityService.add(appVo.getApprove(),appVo.getUserIds(),appVo.getCurrentActivityId(), user);
            } else {
                return R.error();
            }
        }finally {
            lock.unlock(lockKey, uuid);
        }
        return r;
    }

    @ApiOperation(value = "中止业务")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="业务id", required = true, paramType="path", dataType="String"),
    })
    @RequestMapping(value = "/termination/{id}", method = RequestMethod.POST)
    public R termination(String appInfoId){
        activityService.terminationInstanceOfWorkFlow(appInfoId);
        IaasZysb info = new IaasZysb();
        info.setId(appInfoId);
        info.setStatus(ApplicationInfoStatus.DELETE.getCode());
        iaasZysbService.updateById(info);
        return R.ok();
    }

    /**
     * 后台管理页面查询
     */
    @ApiOperation("后台管理页面分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
           @ApiImplicitParam(name = "appName",value="应用名称", defaultValue = "", paramType="query", dataType="String"),
            @ApiImplicitParam(name="status", value="状态, 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回", defaultValue = "", paramType="query", dataType="String"),
            @ApiImplicitParam(name="processType", value="处理人,0:全部 1:我 2:其它人", defaultValue = "0", paramType="query", dataType="String")

    })
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public R page(@LoginUser User user,
                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false, defaultValue = "") String status,
                              @RequestParam(required = false, defaultValue = "") String appName,
                  @RequestParam(required = false) String processType) {
        if (!ApplicationInfoStatus.REVIEW.getCode().equals(status)
                && !ApplicationInfoStatus.IMPL.getCode().equals(status)) {
            processType = null; // 不是待审核/待实施状态,不能过滤处理人
        }
        IPage<IaasZysb> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("status", status);
        param.put("appName",appName);
        param.put("processType",processType);
        page = iaasZysbService.getPage(user, page,param);
        return R.ok(page);
    }
    @ApiOperation("获取流程")
    @RequestMapping(value = "/workflow", method = RequestMethod.GET)
    public R flow(Integer version) {
        if(version ==null){
            return R.error("流程版本不能为空");
        }
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","ZYSB"));
        workflow.setRecheck(workflowmodelService.getByFlowAndAct(workflow.getId(),ModelName.RECHECK.getName(),version));
        workflow.setApprove(workflowmodelService.getByFlowAndAct(workflow.getId(),ModelName.APPROVE.getName(),version));
        workflow.setVersion(version);
        return R.ok(workflow);
    }

    @ApiOperation("IAAS上报统计导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name="areas", value="地区", paramType="query", dataType="String"),
            @ApiImplicitParam(name="policeCategory", value="警种", paramType="query", dataType="String"),
            @ApiImplicitParam(name = "startDate",value="开始时间：yyyy-mm-dd",  paramType="query", dataType="String"),
            @ApiImplicitParam(name="endDate", value="结束时间：yyyy-mm-dd",  paramType="query", dataType="String")
    })
    @RequestMapping(value = "/count/export", method = RequestMethod.GET)
    public void countExport(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(required = false) String areas,
                          @RequestParam(required = false) String policeCategory,
                          String startDate, String endDate) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("startDate",startDate);
        param.put("endDate",endDate);
        param.put("areas",areas);
        param.put("policeCategory",policeCategory);
        List<ReportExport> data = iaasZysbService.reportExport(param);
       // List<IaasZysbExport> data = iaasZysbService.countExport(param);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"资源上报统计"), ReportExport.class, data);
        ExcelUtil.export(request, response, "资源上报统计表", workbook);
    }

    @ApiOperation("老数据迁移")
    @RequestMapping(value = "/move-data",method = RequestMethod.GET)
    public R moveOldData(){
        iaasZysbService.moveOldData();
        return R.ok();
    }

}

