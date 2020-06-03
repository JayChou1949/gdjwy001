package com.upd.hwcloud.controller.backend.ledger;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.BusinessName;
import com.upd.hwcloud.bean.contains.ModelName;
import com.upd.hwcloud.bean.contains.UserType;
import com.upd.hwcloud.bean.dto.ImplRequest;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.wfm.*;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.lock.DistributeLock;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.ISaasApplicationService;
import com.upd.hwcloud.service.ISaasRecoverApplicationService;
import com.upd.hwcloud.service.ISaasRecoverInfoService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.upd.hwcloud.bean.contains.ApplicationInfoStatus.REVIEW_REJECT;

/**
 * <p>
 *  前端控制器
 *  SaaS权限回收申请
 * </p>
 *
 * @author wangbao
 * @since 2019-10-24
 */
@Api(description = "SaaS权限回收申请")
@RestController
@RequestMapping("/saasRecoverApplication")
public class SaasRecoverApplicationController {

    @Autowired
    private ISaasRecoverApplicationService saasRecoverApplicationService;
    @Autowired
    private ISaasApplicationService saasApplicationService;
    @Autowired
    private IWorkflowmodelService workflowmodelService;
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private DistributeLock lock;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private IInstanceService instanceService;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private ISaasRecoverInfoService saasRecoverInfoService;
    @Autowired
    private IFilesService filesService;
    @ApiOperation("创建流程")
    @RequestMapping(value = "/createflow", method = RequestMethod.POST)
    public R createflow() {
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS", "SAAS_POWER_RECOVER"));
        if (null!=workflow) {
            return R.error("资源申请流程已经存在");
        }
        Workflow flow = new Workflow();
        flow.setFlowStatus(1);
        flow.setId(UUIDUtil.getUUID());
        flow.setDefaultProcess("SAAS_POWER_RECOVER");
        flow.setWorkflowname("SAAS资源回收申请流程");
        flow.insert();

        String flowId = flow.getId();
        List<Workflowmodel> models = new ArrayList<>();
        // 业务办理
        Workflowmodel carry =  new Workflowmodel(flowId, ModelName.CARRY.getName(),null);
        models.add(carry);
        // 大数据办审批
        Workflowmodel bigData =  new Workflowmodel(flowId, ModelName.BIG_DATA.getName(), carry.getId());
        models.add(bigData);
        // 服务台复核
        Workflowmodel recheck =  new Workflowmodel(flowId, ModelName.RECHECK.getName(), bigData.getId());
        models.add(recheck);
        // 申请
        Workflowmodel apply =  new Workflowmodel(flowId, ModelName.APPLY.getName(), recheck.getId());
        apply.setIsstart(0);
        models.add(apply);
        workflowmodelService.saveBatch(models);
        return R.ok();
    }


    @ApiOperation(value = "前端判断是否一级管理员")
    @RequestMapping(value = "/saas/isLvl1Manager", method = RequestMethod.GET)
    public R lvl1Manager(@LoginUser User user){
        return R.ok(isLvl1Manager(user));
    }

    @ApiOperation(value = "saas回收申请列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "area",value = "地区",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "policeCategory",value = "警种",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "name",value = "姓名",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "idcard",value = "身份证",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "pageNum",value = "页码",defaultValue = "1",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/saas/user", method = RequestMethod.GET)
    public R getPowerRecover(@LoginUser User user,
                             @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                             @RequestParam(required = false) String area, @RequestParam(required = false) String policeCategory,
                             @RequestParam(required = false) String name, @RequestParam(required = false) String idcard,
                             @RequestParam(required = false) boolean isRecheckAdd) {//判断是否是服务台新增标记
        // 一级管理员可以查看所有申请过的用户，二级管理员只能查看二级管理所属地区或警种申请过的用户
        IPage<SaasApplication> page = new Page<>(pageNum, pageSize);
        boolean lvl1Manager = isLvl1Manager(user);
        boolean lvl2Manager = UserType.TENANT_MANAGER.getCode().equals(user.getType());
        boolean isRecheckManager = isRecheckManager(user);//服务台复核人员可以查看所有申请过的用户
        if (!lvl1Manager && !lvl2Manager && !isRecheckManager) {
            return R.ok(page);
        }
        if (lvl2Manager && !lvl1Manager && !isRecheckAdd) { // 如果同时是一级管理员和二级管理员，则使用一级管理员身份查询
            area = user.getTenantArea();
            policeCategory = user.getTenantPoliceCategory();
        }
        page = saasApplicationService.getApplicationUser(page, area, policeCategory, name, idcard);
        return R.ok(page);
    }

    /**
     * 是否一级管理员
     */
    private boolean isLvl1Manager(User user) {
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","SAAS"));
        Workflowmodel workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().lambda()
                .eq(Workflowmodel::getWorkflowid, workflow.getId())
                .eq(Workflowmodel::getModelname, ModelName.LVL1_MANAGER.getName()));
        String defaulthandleperson = workflowmodel.getDefaulthandleperson();
        boolean isLvl1Manager = false;
        if (StringUtils.isNotEmpty(defaulthandleperson) && defaulthandleperson.contains(user.getIdcard())) {
            isLvl1Manager = true;
        }
        return isLvl1Manager;
    }

    /**
     * 是否大数据办管理员
     */
    private boolean isBigManager(User user) {
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","SAAS_POWER_RECOVER"));
        Workflowmodel workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().lambda()
                .eq(Workflowmodel::getWorkflowid, workflow.getId())
                .eq(Workflowmodel::getModelname, ModelName.BIG_DATA.getName()));
        String defaulthandleperson = workflowmodel.getDefaulthandleperson();
        boolean isLvl1Manager = false;
        if (StringUtils.isNotEmpty(defaulthandleperson) && defaulthandleperson.contains(user.getIdcard())) {
            isLvl1Manager = true;
        }
        return isLvl1Manager;
    }

    /**
     * 是否一级管理员
     */
    private boolean isRecheckManager(User user) {
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","SAAS_POWER_RECOVER"));
        Workflowmodel workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().lambda()
                .eq(Workflowmodel::getWorkflowid, workflow.getId())
                .eq(Workflowmodel::getModelname, ModelName.RECHECK.getName()));
        String defaulthandleperson = workflowmodel.getDefaulthandleperson();
        boolean isRecheckManager = false;
        if (StringUtils.isNotEmpty(defaulthandleperson) && defaulthandleperson.contains(user.getIdcard())) {
            isRecheckManager = true;
        }
        return isRecheckManager;
    }
    /**
     * 查看单个用户下Saas申请下的当前具体的在用服务（通用应用   警种应用  地市应用 ）
     */
    @ApiOperation(value = "查询SAAS用户当前在用服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "creator",value = "用户身份证号",paramType = "path",dataType = "String")
    })
    @RequestMapping(value = "/saas/recover/{creator}", method = RequestMethod.GET)
    public R getAllSaasApplication(@PathVariable String creator){
        List<SaasApplication> details = saasApplicationService.getSaasUseService(creator);
        return R.ok(details);
    }

    /**
     * 创建申请单
     */
    @ApiOperation(value = "新建申请单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public R create(@LoginUser User user, @RequestBody SaasRecoverApplication info,String area,String policeCategory) {
        synchronized (info){
            info = saasRecoverApplicationService.create(user, info, area, policeCategory);
            return R.ok(info.getBillNum());
        }
    }

    @ApiOperation(value = "saas回收管理列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码",defaultValue = "1",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
            @ApiImplicitParam(name = "keyword",value = "关键字",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "status",value = "状态",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "startDate",value = "开始时间",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "endDate",value = "结束时间",paramType = "query",dataType = "String"),
    })
    @RequestMapping(value = "/saas/recover/manage", method = RequestMethod.GET)
    public R getPowerRecoverManage(@LoginUser User user,
                                   @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                   @RequestParam(required = false) String keyword, @RequestParam(required = false) String status,
                                   @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
                                   @RequestParam(required = false, defaultValue = "0") String processType) {
        IPage<SaasRecoverApplication> page = new Page<>(pageNum, pageSize);
        /*boolean lvl1Manager = isLvl1Manager(user);
        boolean lvl2Manager = UserType.TENANT_MANAGER.getCode().equals(user.getType());
        if (!lvl1Manager && !lvl2Manager) {
            return R.ok(page);
        }*/
        /*boolean isBigManager = isBigManager(user);
        if(isBigManager){//大数据办管理员可以查看所有人的申请单
            Map<String, Object> param = new HashMap<>();
            param.put("keyword", keyword);
            param.put("status", status);
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            user.setIdcard("");
            param.put("user", user);
            page = saasRecoverApplicationService.getPage(user, page, param);
        }else{*/
        if (ApplicationInfoStatus.SHOPPING_CART.getCode().equals(status)
                || ApplicationInfoStatus.SHOPPING_CART_DEL.getCode().equals(status)) {
            status = null; // 不能查购物车的数据
        }
        if (!ApplicationInfoStatus.REVIEW.getCode().equals(status)
                && !ApplicationInfoStatus.IMPL.getCode().equals(status)) {
            processType = null; // 不是待审核/待实施状态,不能过滤处理人
        }
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("keyword", keyword);
        param.put("status", status);
        param.put("processType", processType);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        page = saasRecoverApplicationService.getPageWithHandler(user, page, param);
       // }
        return R.ok(page);
    }

    @ApiOperation(value = "后台管理页面删除")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public R deleteById(@LoginUser User user, @PathVariable String id) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                saasRecoverApplicationService.deleteById(user, id);
                SaasRecoverApplication info = saasRecoverApplicationService.getById(id);
                if(!ApplicationInfoStatus.USE.equals(info.getStatus())){
                    //如果订单未回收,删除订单内的用户
                    deleteUserOfOrder(id);
                }
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


    /**
     * 删除订单的回收人
     * @param id 订单ID
     */
    private void deleteUserOfOrder(String id){
        try {
            List<SaasRecoverInfo> recoverInfos = getRecoverList(id);
            for (SaasRecoverInfo recoverInfo : recoverInfos) {
                reCheckDelete(id, recoverInfo.getReIdcard());
            }
        }catch (Exception e){
            throw new BaseException("删除订单回收人失败");
        }
    }

    /**
     * 获取订单内回收人
     * @param infoId
     * @return
     */
    private  List<SaasRecoverInfo> getRecoverList(String infoId){
        List<SaasRecoverInfo> total = saasRecoverInfoService.getUserUseService(infoId);
        return total;
    }



//    @ApiOperation(value = "申请管理页面删除")
//    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
//    public R deleteRecoverInfo(@LoginUser User user, @PathVariable String id) {
//        String uuid = UUIDUtil.getUUID();
//        String lockKey = id.intern();
//        try {
//            if (lock.lock(lockKey, uuid)) {
//                saasRecoverApplicationService.deleteRecoverInfo(user, id);
//            } else {
//                return R.error();
//            }
//        } finally {
//            lock.unlock(lockKey, uuid);
//        }
//        return R.ok();
//    }


//    @ApiOperation(value = "修改申请单")
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public R update(@LoginUser User user, @RequestBody SaasRecoverApplication info) {
//        saasRecoverApplicationService.update(user, info);
//        return R.ok();
//    }


    @ApiOperation(value = "申请详情")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R viewDetailsApply(@LoginUser User user,@PathVariable String id) {
        SaasRecoverApplication detail = saasRecoverApplicationService.getDetails(id);
        if (detail == null) {
            return R.error("该申请不存在");
        }
        Instance instance = instanceService.getInstanceByBusinessId(id);
        Activity activity = null;
        if (null!=instance){
            detail.setWorkFlowId(instance.getWorkflowid());
            detail.setWorkFlowVersion(instance.getWorkflowversion());
            List<Activity> activitys = activityService.list(new QueryWrapper<Activity>().lambda()
                    .eq(Activity::getInstanceid,instance.getId()).eq(Activity::getHandlepersonids,user.getIdcard()).eq(Activity::getActivitystatus,"待办").isNull(Activity::getActivitytype));
            if (null==activitys||activitys.size()==0){
                activitys = activityService.list(new QueryWrapper<Activity>().lambda()
                        .eq(Activity::getInstanceid,instance.getId()).eq(Activity::getHandlepersonids,user.getIdcard()).eq(Activity::getActivitystatus,"待办").eq(Activity::getActivitytype,"adviser"));
            }
            if (null!=activitys&&activitys.size()>0) activity = activitys.get(0);
        }
        String activityId = "";
        if (activity!=null) {
            activityId = activity.getId();
            if ("adviser".equals(activity.getActivitytype())){
                detail.setCanAdviser(true);
                detail.setCanReview(true);
            }else{
                Workflowmodel workflowmodel=workflowmodelService.getById(activity.getModelid());
                if (null==workflowmodel){
                    return R.error("未找到流程环节");
                }
                /*List<Activity> preActivity = activityService.list(new QueryWrapper<Activity>().lambda()
                                .eq(Activity::getPreviousactivityids,activity.getPreviousactivityids()).orderByDesc(Activity::getCreatetime));
                String preStatus = "";
                if(!preActivity.isEmpty()){
                   preStatus = preActivity.get(0).getActivitystatus();
                }*/
                String  modelName=workflowmodel.getModelname();
                if (modelName.equals(ModelName.APPLY.getName())
                        ||modelName.equals(ModelName.LVL2_MANAGER.getName())
                        //||"已回退".equals(preStatus)
                        ||modelName.equals(ModelName.RECHECK.getName())) {
                    detail.setCanEdit(true);
                }
                if (modelName.equals(ModelName.LVL2_MANAGER.getName())
                        ||modelName.equals(ModelName.RECHECK.getName())
                        ||modelName.equals(ModelName.LVL1_MANAGER.getName())
                        ||modelName.equals(ModelName.BIG_DATA.getName())) {
                    detail.setCanTrans(true);
                    detail.setCanReview(true);
                    if (!modelName.equals(ModelName.LVL2_MANAGER.getName())) {
                        detail.setCanAdd(true);
                    }
                }
                if (modelName.equals(ModelName.CARRY.getName())) {
                    detail.setCanTrans(true);
                    detail.setCanImpl(true);
                }
            }

        }
        List<AppReviewInfo> allReviewInfo = detail.getReviewList();
        Map bean = new HashMap<>();
        if (allReviewInfo!=null){
            Map<String, List<AppReviewInfo>> reviews = allReviewInfo.stream().collect(Collectors.groupingBy(a->a.getFlowStepId()+""));
            bean.put("review",reviews);
        }

        detail.setWorkFlowVersion(instance.getWorkflowversion());
        WorkFlowModelVo modelVo=workflowmodelService.getWorkFlowDefinition(id);
        if (modelVo != null) {
            bean.put("model",modelVo);
            bean.put("bizData",detail);
            bean.put("activityId",activityId);
            return R.ok(bean);
        }else {
            return R.error("未找到流程");
        }
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
                R r =  activityService.forward(activityId, userIds);
                return r;
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
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public R approve(@LoginUser User user,
                     @RequestBody FallBackVO vo) {
        AppReviewInfo approve = vo.getApprove();
        approve.setCreator(user.getIdcard());
        SaasRecoverApplication info = saasRecoverApplicationService.getById(vo.getApprove().getAppInfoId());
        Activity activity =activityService.getById(vo.getCurrentActivityId());
        if ("adviser".equals(activity.getActivitytype())){
            activityService.adviseActivity(vo.getCurrentActivityId(), approve);
            return R.ok();
        }
        Map<String,String> map = new HashMap<>();
        map.put("name", BusinessName.SAAS_RECOVER_RESOURCE);
        map.put("order", info.getBillNum());
        if ("1".equals(approve.getResult())){
            R r = activityService.advanceCurrentActivity(vo.getCurrentActivityId(), approve, map);
            Object nextModelName = r.get("data");
            if (ModelName.CARRY.getName().equals(nextModelName)){
                info.setStatus(ApplicationInfoStatus.IMPL.getCode());
            }else {
                info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
            }
            if (ModelName.BIG_DATA.getName().equals(nextModelName)) {
                info.setRecheckTime(new Date());
            } else if (ModelName.CARRY.getName().equals(nextModelName)) {
                info.setBigDataTime(new Date());
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
                info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
            }
        }
        saasRecoverApplicationService.updateById(info);
        saasApplicationService.updateStatus(info.getId(), info.getStatus());
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

    @ApiOperation(value = "回退")
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public R reject(@LoginUser User user,
                    @RequestBody FallBackVO vo) {

        AppReviewInfo approve = vo.getApprove();
        approve.setCreator(user.getIdcard());
        SaasRecoverApplication  info = saasRecoverApplicationService.getById(approve.getAppInfoId());
        if(null==info){
            return R.error("未找到待办数据");
        }
        activityService.fallbackCurrentActivity(vo);
        if (ApplicationInfoStatus.IMPL.getCode().equals(info.getStatus())){
            info.setStatus(ApplicationInfoStatus.IMPL_REJECT.getCode());
        }else {
            info.setStatus(REVIEW_REJECT.getCode());
        }
        saasRecoverApplicationService.updateById(info);
        return R.ok();
    }


    @ApiOperation(value = "业务办理")
    @ApiImplicitParams({
            @ApiImplicitParam(name="activityId", value="当前环节id", required = true, paramType="path", dataType="String"),
    })
    @RequestMapping(value = "/impl/{id}/{activityId}/{modelId}", method = RequestMethod.POST)
    public R impl(@LoginUser User user,
                  @PathVariable(value = "id") String id, @PathVariable(value = "modelId") String modelId,
                  @PathVariable(value = "activityId") String activityId, @RequestBody ImplRequest implRequest) throws Exception {
        SaasRecoverApplication info = saasRecoverApplicationService.getById(id);
        Activity activity =activityService.getById(activityId);
        if (implRequest.getResult() == null) {
            throw new BaseException("请选择实施结果");
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
                saasRecoverApplicationService.saveImpl(user, param, activity.getModelid());
            } else {
                throw new BaseException("实施失败");
            }
        }catch (Exception e){
            throw e;
        }finally {
            lock.unlock(lockKey, uuid);
        }
        if ("1".equals(implRequest.getResult())){
            activityService.advanceCurrentActivity(activity,info.getCreator());
            saasRecoverApplicationService.update(new SaasRecoverApplication(), new UpdateWrapper<SaasRecoverApplication>().lambda()
                    .eq(SaasRecoverApplication::getId, info.getId())
                    .set(SaasRecoverApplication::getCarryTime, new Date()));
            messageProvider.sendMessageAsync(messageProvider.buildCompleteMessage(info.getCreator(), BusinessName.SAAS_RECOVER_RESOURCE, info.getBillNum()));
        }else {
            if (modelId==null||modelId.trim().equals("")) {
                return R.error(201, "回退环节ID不能为空,回退失败");
            }
            FallBackVO vo = new FallBackVO();
            vo.setCurrentActivityId(activityId);
            vo.setFallBackModelIds(modelId);
            Map<String,String> map = new HashMap<>();
            map.put("name", BusinessName.SAAS_RECOVER_RESOURCE);
            map.put("order", info.getBillNum());
            activityService.fallbackOnApproveNotPass(vo, map);
        }

        return R.ok();
    }


    @ApiOperation(value = "审核驳回后提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="申请单id,多个使用逗号分隔", required = true, paramType="form", dataType="String")
    })
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R submit(@LoginUser User user, @RequestParam("id") String id) {
        if (StringUtils.isEmpty(id)) {
            return R.error("未找到数据!");
        }
        SaasRecoverApplication info = saasRecoverApplicationService.getById(id);
        Instance instance =instanceService.getInstanceByBusinessId(info.getId());
        if (null==instance){
            throw new BaseException("流程实例未找到");
        }
        Activity firstActivity = activityService.getOne(new QueryWrapper<Activity>().eq("activitystatus","待办")
                .eq("isstart",0).eq("instanceid",instance.getId()));
        String flowId = instance.getWorkflowid();
        info.setModifiedTime(new Date());
        info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
        Workflowmodel workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",flowId).eq("modelname", ModelName.RECHECK.getName()).eq("VERSION",instance.getWorkflowversion()));
        Map<String, String> modelMapToPerson = new HashMap<>();
        Map<String, String> modelMapToAdviser = new HashMap<>();
        modelMapToPerson.put(workflowmodel.getId(), workflowmodel.getDefaulthandleperson());
        modelMapToAdviser.put(workflowmodel.getId(),workflowmodel.getAdviserperson());
        AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
        advanceBeanVO.setCurrentActivityId(firstActivity.getId());
        advanceBeanVO.setModelMapToPerson(modelMapToPerson);
        advanceBeanVO.setModelMapToAdviser(modelMapToAdviser);
        Map<String,String> map = new HashMap<>();
        map.put("name",BusinessName.SAAS_RECOVER_RESOURCE);
        map.put("order",info.getBillNum());
        activityService.advanceCurrentActivity(advanceBeanVO,map);
        saasRecoverApplicationService.updateById(info);
        messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, BusinessName.SAAS_RECOVER_RESOURCE, info.getBillNum()));
        return R.ok();
    }

    /**
     *此服务主要用于saas回收管理中 服务台编辑删除被回收的人员
     * @param appId
     * @param idcard
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/user/delete/{appId}/{idcard}",method = RequestMethod.GET)
    public R reCheckDelete(@PathVariable String appId,@PathVariable String idcard){
        if(StringUtils.isEmpty(appId)&& StringUtils.isEmpty(idcard)){
            return R.error("删除失败，请重新删除！");
        }
        saasRecoverInfoService.remove(new QueryWrapper<SaasRecoverInfo>().lambda()
                .eq(SaasRecoverInfo::getAppInfoId,appId)
                .eq(SaasRecoverInfo::getReIdcard,idcard));
        saasApplicationService.update(new SaasApplication(),new UpdateWrapper<SaasApplication>().lambda()
        .eq(SaasApplication::getRecoverId,appId).eq(SaasApplication::getCreator,idcard).set(SaasApplication::getRecoverFlag,"0"));
        return R.ok("删除成功");
    }

    /**
     * 此服务主要用于saas回收管理中 服务台编辑添加回收的人员
     * @param info
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/user/add",method = RequestMethod.POST)
    public R reCheckAddNew(@RequestBody SaasRecoverApplication info){
        List<User> userList = info.getAddUserlist();//获取新添加的回收人员
        if(!userList.isEmpty()){
            for(User u : userList){
                List<SaasApplication> list = saasApplicationService.getSaasRecoverManageUseService(u.getIdcard());
                if(!list.isEmpty()){
                    List<SaasRecoverInfo> recoverInfoList = Lists.newArrayList();
                    for(SaasApplication saas : list){
                        saas.setRecoverFlag("99"); // 标记为正在回收
                        saas.setRecoverId(info.getId()); // 原始申请单和回收申请单关联
                        SaasRecoverInfo recoverInfo = new SaasRecoverInfo();
                        recoverInfo.setId(null);
                        recoverInfo.setAppInfoId(info.getId());
                        recoverInfo.setReIdcard(saas.getCreator());
                        recoverInfo.setReName(saas.getCreatorName());
                        recoverInfo.setReOrgId(saas.getOrgId());
                        recoverInfo.setReOrgName(saas.getOrgName());
                        recoverInfoList.add(recoverInfo);
                    }
                    saasRecoverInfoService.saveBatch(recoverInfoList);
                    saasApplicationService.updateBatchById(list);
                }
            }
        }
        filesService.refFiles(info.getFileList(),info.getId());//文件上传
        return R.ok();
    }
}

