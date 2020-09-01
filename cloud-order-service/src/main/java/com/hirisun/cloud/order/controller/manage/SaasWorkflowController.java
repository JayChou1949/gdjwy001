package com.hirisun.cloud.order.controller.manage;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.hirisun.cloud.api.saas.SaasApplicationApi;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.saas.vo.SaasApplicationMergeVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.handler.CommonHandler;
import com.hirisun.cloud.order.service.saas.SaasWorkflowService;
import com.hirisun.cloud.order.vo.ApproveVO;
import com.hirisun.cloud.order.vo.ImplRequest;
import com.hirisun.cloud.redis.lock.DistributeLock;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Api(tags = "应用申请审批工单管理")
@RestController
@RequestMapping("/saas/saasWorkflow")
public class SaasWorkflowController {

    @Autowired
    private DistributeLock lock;

    @Autowired
    private WorkflowApi workflowApi;

    @Autowired
    private SaasApplicationApi saasApplicationApi;

    @Autowired
    private SaasWorkflowService saasWorkflowService;

    @ApiOperation("本单位申请总表")
    @GetMapping("/page")
    public QueryResponseResult<SaasApplicationMergeVO> page(@LoginUser UserVO user,
                                                            @ApiParam(value = "页码",required = false) @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                            @ApiParam(value = "每页数量",required = false) @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                                            @ApiParam(value = "申请人",required = false) @RequestParam(required = false) String userName,
                                                            @ApiParam(value = "申请服务",required = false) @RequestParam(required = false) String serviceName,
                                                            @ApiParam(value = "订单号",required = false) @RequestParam(required = false) String orderNumber,
                                                            @ApiParam(value = "开始时间",required = false) @RequestParam(required = false) String startDate,
                                                            @ApiParam(value = "结束时间",required = false) @RequestParam(required = false) String endDate,
                                                            @ApiParam(value = "状态, 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施驳回",required = false) @RequestParam(required = false, defaultValue = "") String status,
                                                            @ApiParam(value = "处理人,0:全部 1:我 2:其它人",required = false) @RequestParam(required = false, defaultValue = "0") String processType) {
        if (!ApplyInfoStatus.REVIEW.getCode().equals(status)
                && !ApplyInfoStatus.IMPL.getCode().equals(status)) {
            processType = null; // 不是待审核/待实施状态,不能过滤处理人
        }

        Page<SaasApplicationMergeVO> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        Map<String, Object> param = new HashMap<>();
        param.put("pageNum", pageNum);
        param.put("pageSize", pageSize);
        param.put("user", JSON.toJSONString(user));
        param.put("userName", CommonHandler.dealNameForEqualQuery(userName));
        param.put("status", status);
        param.put("orderNumber",CommonHandler.dealNameforQuery(orderNumber));
        param.put("processType", processType);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        //要查询serviceName需要多关联两张表，需要时才关联，提高查询效率
        if(StringUtils.isBlank(serviceName)){
            page=saasApplicationApi.getFlowPage(user.getIdcard(), param);
        }else {
            param.put("serviceName",CommonHandler.dealNameforQuery(serviceName));
            page = saasApplicationApi.getFlowPageWithServiceName(user.getIdcard(), param);
        }
        return QueryResponseResult.success(page);
    }

    @ApiOperation("合并")
    @PostMapping("/merge")
    public QueryResponseResult merge(@LoginUser UserVO user, @ApiParam(value = "要和并的单子id,多个使用逗号分隔",required = true) @RequestParam String ids) {
        if (StringUtils.isEmpty(ids)) {
            return QueryResponseResult.fail("请传入ids参数");
        }
        String uuid = UUIDUtil.getUUID();
        String lockKey = ids.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                SaasApplicationMergeVO merge = saasApplicationApi.merge(user.getIdcard(), ids);
                return QueryResponseResult.success(merge);
            } else {
                return QueryResponseResult.fail("系统繁忙,请稍后重试!");
            }
        } catch (Exception e) {
            return QueryResponseResult.fail("系统繁忙,请稍后重试!");
        } finally {
            lock.unlock(lockKey, uuid);
        }
    }

    @ApiOperation(value = "撤销合并")
    @PostMapping("/merge/undo")
    public QueryResponseResult deleteById(@LoginUser UserVO user, @ApiParam(value = "申请工单id",required = true) @RequestParam String id) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                saasApplicationApi.deleteById(user.getId(), id);
                Map<String, String> stringStringMap = workflowApi.terminationOrder(id);
                if (!"200".equals(stringStringMap.get("code"))) {
                    return QueryResponseResult.fail(stringStringMap.get("msg"));
                }
            } else {
                return QueryResponseResult.fail("系统繁忙,请稍后重试!");
            }
        } catch (Exception e) {
            return QueryResponseResult.fail("系统繁忙,请稍后重试!");
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return QueryResponseResult.success(null);
    }

    @ApiOperation(value = "修改申请信息")
    @PutMapping("/update")
    public QueryResponseResult<SaasApplicationMergeVO> update(@LoginUser UserVO user, @RequestBody SaasApplicationMergeVO info) throws IOException {
        saasApplicationApi.update(user.getId(),info);
        return QueryResponseResult.success(null);
    }

    @ApiOperation(value = "申请详情")
    @GetMapping("/detail")
    public QueryResponseResult detail(@LoginUser UserVO user,@ApiParam(value = "申请工单id",required = true) @RequestParam String id) {
        return saasWorkflowService.detail(id);
    }

    @ApiOperation(value = "转发")
    @PostMapping("/forward")
    public QueryResponseResult forward(@LoginUser UserVO user,
                                       @ApiParam(value = "当前环节id",required = true) @RequestParam String activityId,
                                       @ApiParam(value = "转发审核人id,多个使用逗号分隔",required = true) @RequestParam String userIds) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = activityId.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                Map<String,String> resultMap =  workflowApi.activityForward(activityId, userIds);
                if (!"200".equals(resultMap.get("code"))) {
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
                return QueryResponseResult.success(null);
            } else {
                return QueryResponseResult.fail("频繁访问,请稍后重试");
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
    }

    @ApiOperation(value = "参与人意见")
    @PutMapping("/adviser")
    public QueryResponseResult<FallBackVO> adviser(@LoginUser UserVO user,
                     @RequestBody FallBackVO vo) {
        return saasWorkflowService.adviser(user, vo);
    }


    @ApiOperation(value = "审批")
    @PutMapping("/approve")
    public QueryResponseResult<FallBackVO> approve(@LoginUser UserVO user,@RequestBody FallBackVO vo) {
        return saasWorkflowService.approve(user, vo);
    }

    @ApiOperation(value = "业务办理")
    @PutMapping("/impl")
    public QueryResponseResult<ImplRequest> impl(@LoginUser UserVO user,@RequestBody ImplRequest implRequest) throws Exception {
        String applyInfoId = implRequest.getApplyInfoId();
        String nodeId = implRequest.getNodeId();
        String activityId = implRequest.getActivityId();
        return saasWorkflowService.saveImpl(user, applyInfoId, nodeId, activityId, implRequest);
    }

    @ApiOperation(value = "加办")
    @PutMapping("/add")
    public QueryResponseResult<ApproveVO> review(@LoginUser UserVO user, @RequestBody ApproveVO approveVO) {
        return saasWorkflowService.add(user, approveVO);
    }
    @ApiOperation("合并后申请人员列表导出")
    @RequestMapping(value = "/export/user", method = RequestMethod.GET)
    public void userExport(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        saasWorkflowService.userExport(id, request, response);
    }

//    @ApiOperation("SaaS应用总表工单统计表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="areas", value="地区", paramType="query", dataType="String"),
//            @ApiImplicitParam(name="policeCategory", value="警种", paramType="query", dataType="String"),
//            @ApiImplicitParam(name="submitStartDate", value="提交申请开始日期", required = true, paramType="query", dataType="String"),
//            @ApiImplicitParam(name="submitEndDate", value="提交申请结束日期", required = true, paramType="query", dataType="String"),
//            @ApiImplicitParam(name="bigdataStartDate", value="大数据办审核开始日期", required = true, paramType="query", dataType="String"),
//            @ApiImplicitParam(name="bigdataEndDate", value="大数据办审核结束日期", required = true, paramType="query", dataType="String"),
//            @ApiImplicitParam(name="carryStartDate", value="业务办理开始日期", required = true, paramType="query", dataType="String"),
//            @ApiImplicitParam(name="carryEndDate", value="业务办理结束日期", required = true, paramType="query", dataType="String")
//    })
//    @RequestMapping(value = "/export/saasTotal", method = RequestMethod.GET)
//    public void saasTotal(@LoginUser UserVO user, HttpServletRequest request, HttpServletResponse response,
//                          @RequestParam(required = false) String areas,
//                          @RequestParam(required = false) String policeCategory,
//                          String submitStartDate, String submitEndDate,
//                          String bigdataStartDate, String bigdataEndDate,
//                          String carryStartDate, String carryEndDate) throws Exception {
//        if (UserType.TENANT_MANAGER.getCode().equals(user.getType())) {
//            areas = user.getTenantArea();
//            policeCategory = user.getTenantPoliceCategory();
//        }
//        Map<String, String> params = getParamsMap(submitStartDate, submitEndDate, bigdataStartDate, bigdataEndDate, carryStartDate, carryEndDate);
//        List<SaasTotal> data = saasApplicationMergeService.saasTotal(areas, policeCategory, params);
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"SaaS应用总表工单统计"), SaasTotal.class, data);
//        ExcelUtil.export(request, response, "SaaS应用总表工单统计表", workbook);
//    }
//
//    @ApiOperation("SaaS应用申请统计表")
//    @RequestMapping(value = "/export/saasOrderTotal", method = RequestMethod.GET)
//    public void saasOrderTotal(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
//                               @RequestParam(required = false) String areas,
//                               @RequestParam(required = false) String policeCategory,
//                               String submitStartDate, String submitEndDate,
//                               String bigdataStartDate, String bigdataEndDate,
//                               String carryStartDate, String carryEndDate) throws Exception {
//        if (UserType.TENANT_MANAGER.getCode().equals(user.getType())) {
//            areas = user.getTenantArea();
//            policeCategory = user.getTenantPoliceCategory();
//        }
//        Map<String, String> params = getParamsMap(submitStartDate, submitEndDate, bigdataStartDate, bigdataEndDate, carryStartDate, carryEndDate);
//        List<SaasOrderTotal> data = saasApplicationMergeService.saasOrderTotal(areas, policeCategory, params);
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"SaaS应用申请统计"), SaasOrderTotal.class, data);
//        ExcelUtil.export(request, response, "SaaS应用申请统计表", workbook);
//    }
//
//    @ApiOperation("SaaS应用工单查询")
//    @RequestMapping(value = "/export/saasOrderQuery", method = RequestMethod.GET)
//    public void saasOrderQuery(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
//                               @RequestParam(required = false) String areas,
//                               @RequestParam(required = false) String policeCategory,
//                               String submitStartDate, String submitEndDate,
//                               String bigdataStartDate, String bigdataEndDate,
//                               String carryStartDate, String carryEndDate) throws Exception {
//        if (UserType.TENANT_MANAGER.getCode().equals(user.getType())) {
//            areas = user.getTenantArea();
//            policeCategory = user.getTenantPoliceCategory();
//        }
//        Map<String, String> params = getParamsMap(submitStartDate, submitEndDate, bigdataStartDate, bigdataEndDate, carryStartDate, carryEndDate);
//        ArrayList<SaasOrderTotal> data = (ArrayList<SaasOrderTotal>) saasApplicationMergeService.saasOrderQuery(areas, policeCategory, params);
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"SaaS应用工单查询"), SaasOrderTotal.class, data);
//        ExcelUtil.export(request, response, "SaaS应用工单查询", workbook);
//    }
//
//    @ApiOperation("SaaS应用人员使用状况统计表")
//    @RequestMapping(value = "/export/saasUseTotal", method = RequestMethod.GET)
//    public void saasUseTotal(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
//                             @RequestParam(required = false) String areas,
//                             @RequestParam(required = false) String policeCategory,
//                             String submitStartDate, String submitEndDate,
//                             String bigdataStartDate, String bigdataEndDate,
//                             String carryStartDate, String carryEndDate) throws Exception {
//        if (UserType.TENANT_MANAGER.getCode().equals(user.getType())) {
//            areas = user.getTenantArea();
//            policeCategory = user.getTenantPoliceCategory();
//        }
//        Map<String, String> params = getParamsMap(submitStartDate, submitEndDate, bigdataStartDate, bigdataEndDate, carryStartDate, carryEndDate);
//        List<SaasUseTotal> data = saasApplicationMergeService.saasUseTotal(areas, policeCategory, params);
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"SaaS应用人员使用状况统计"), SaasUseTotal.class, data);
//        ExcelUtil.export(request, response, "SaaS应用人员使用状况统计表", workbook);
//    }
//
//    @ApiOperation("SaaS应用权限回收工单统计")
//    @RequestMapping(value = "/export/saasRecoverTotal", method = RequestMethod.GET)
//    public void saasRecoverTotal(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
//                                 @RequestParam(required = false) String areas,
//                                 @RequestParam(required = false) String policeCategory,
//                                 String submitStartDate, String submitEndDate,
//                                 String bigdataStartDate, String bigdataEndDate,
//                                 String carryStartDate, String carryEndDate) throws Exception {
//        if (UserType.TENANT_MANAGER.getCode().equals(user.getType())) {
//            areas = user.getTenantArea();
//            policeCategory = user.getTenantPoliceCategory();
//        }
//        Map<String, String> params = getParamsMap(submitStartDate, submitEndDate, bigdataStartDate, bigdataEndDate, carryStartDate, carryEndDate);
//        List<SaasRecoverTotal> data = saasRecoverApplicationService.saasRecoverExport(areas, policeCategory, params);
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"SaaS应用权限回收工单统计"), SaasRecoverTotal.class, data);
//        ExcelUtil.export(request, response, "SaaS应用权限回收工单统计", workbook);
//    }

    public static Map<String, String> getParamsMap(String submitStartDate, String submitEndDate, String bigdataStartDate, String bigdataEndDate, String carryStartDate, String carryEndDate) {
        if (StringUtils.isEmpty(submitStartDate) || StringUtils.isEmpty(submitEndDate)) {
            submitStartDate = null;
            submitEndDate = null;
        }
        if (StringUtils.isEmpty(bigdataStartDate) || StringUtils.isEmpty(bigdataEndDate)) {
            bigdataStartDate = null;
            bigdataEndDate = null;
        }
        if (StringUtils.isEmpty(carryStartDate) || StringUtils.isEmpty(carryEndDate)) {
            carryStartDate = null;
            carryEndDate = null;
        }
        Map<String, String> params = Maps.newHashMap();
        params.put("submitStartDate", submitStartDate);
        params.put("submitEndDate", submitEndDate);
        params.put("bigdataStartDate", bigdataStartDate);
        params.put("bigdataEndDate", bigdataEndDate);
        params.put("carryStartDate", carryStartDate);
        params.put("carryEndDate", carryEndDate);
        return params;
    }


//    @ApiOperation("SaaS旧数据Excel导入")
//    @RequestMapping(value = "/import/oldData", method = RequestMethod.GET)
//    public R importOldData() throws Exception {
//        File file = new File(filePath);
//        //File file = new File("F://test.xlsx");
//        if (!file.exists()) {
//            return R.error("文件不存在");
//        }
//        ImportParams params = new ImportParams();
//        List<ImportSaasApplication> list = ExcelImportUtil.importExcel(file, ImportSaasApplication.class, params);
//        saveData(list);
//        return R.ok("数据导入成功");
//    }
//
//    /**
//     * 建模平台用户开通申请总表下载
//     *
//     * @param response
//     * @return
//     */
//    @ApiOperation("建模平台用户开通申请总表下载")
//    @RequestMapping("/downloadFile")
//    public R downloadFile(HttpServletResponse response) {
//        try {
//            saasApplicationMergeService.downloadFile(response);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return R.error();
//        }
//        return R.ok();
//    }
//
//    private void saveData(List<ImportSaasApplication> list) throws Exception {
//        if (list == null) {
//            return;
//        }
//        for (ImportSaasApplication impt :list){
//            logger.error("正在导入,序号:[{}]", impt.getId());
//
//            SaasApplicationMerge merge = getSaasApplicationMerge(impt);
//            merge.insert();
//
//            SaasApplication saasApplication = getSaasApplication(impt, merge);
//            if ("是".equals(impt.getLvl2Recycle())) {
//                saasApplication.setRecoverFlag("-1");
//                insertRevocerData(impt,saasApplication);
//            } else switch (impt.getBigDataRecycle()) {
//                case "否":
//                    saasApplication.setRecoverFlag("0"); // 导出表格中使用权限状态为:是
//                    break;
//                case "是":
//                    saasApplication.setRecoverFlag("-1"); // 导出表格中使用权限状态为:否
//                    insertRevocerData(impt, saasApplication);
//                    break;
//                case "是，一键申请重复":
//                    saasApplication.setRecoverFlag("0");
//                    createNewRecord(impt);
//                    insertRevocerData(impt, saasApplication);
//                    break;
//                case "再开通":
//                    saasApplication.setRecoverFlag("0");
//                    insertRevocerData(impt, saasApplication);
//                    break;
//                case "再开通，一键申请重复":
//                    saasApplication.setRecoverFlag("0");
//                    createNewRecord(impt);
//                    insertRevocerData(impt, saasApplication);
//                    break;
//                default:
//                    break;
//            }
//            saasApplication.insert();
//
//            saveExt(impt, saasApplication);
//        }
//    }
//
//    /**
//     * 回收信息记录
//     * @param impt
//     * @throws Exception
//     */
//    private void insertRevocerData( ImportSaasApplication impt, SaasApplication saasApplication) throws Exception {
//        SaasRecoverApplication recoverApplication = getSaasRecoverApplication(impt);
//        recoverApplication.insert();//回收主表插入数据
//        saasApplication.setRecoverId(recoverApplication.getId());// 原始申请单和回收申请单关联
//        getSaasRecoverInfo(impt,recoverApplication);//回收明细表插入数据
//    }
//
//    /**
//     * 往回收信息主表中插入数据
//     * @param impt 导入的数据实体
//     * @return
//     */
//    private SaasRecoverApplication getSaasRecoverApplication(ImportSaasApplication impt) throws Exception {
//        //List<SaasRecoverApplication> saasRecoverApplicationList = new ArrayList<>();
//        SaasRecoverApplication recover = new SaasRecoverApplication();
//        recover.setIsImport("1");
//        recover.setStatus(ApplicationInfoStatus.USE.getCode());
//        recover.setCreator(impt.getIdcard());
//        recover.setCreatorName(impt.getName());
//        recover.setOrgName(impt.getWorkOrgName());
//        recover.setPost(impt.getPostType());
//        recover.setPhone(impt.getPhone());
//        String orgName = impt.getRealOrgName();//所属地区或警种
//        String area = AreaPoliceCategoryUtils.getAreaName(orgName);
//        if("省厅".equals(area)){//是省厅的所属地区为省厅 警种为所属警种，非省厅的只有地区
//            recover.setAreas(area);
//            recover.setApplyType("1");
//            String policecategory = AreaPoliceCategoryUtils.getPoliceCategory(orgName);
//            recover.setPoliceCategory(policecategory);
//        }else{
//            recover.setApplyType("0");
//            recover.setAreas(area);
//        }
//        /*String param1 = impt.getPoliceCategory();//所属警种
//        String policeCategory = AreaPoliceCategoryUtils.getPoliceCategory(param1);
//        if(StringUtils.isNotEmpty(policeCategory)){
//            recover.setApplyType("0");
//            recover.setAreas(area);
//        }else{
//            recover.setApplyType("1");
//            recover.setPoliceCategory(param);
//        }*/
//
//        if (!"/".equals(impt.getRecycleTime())) {
//            SimpleDateFormat sdf  = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US);
//            //String date = ;
//            //Date date1 = new Date();
//            Date date = sdf.parse(impt.getRecycleTime());
//            sdf=new SimpleDateFormat("yyyy/MM/dd");
//            String mydate  =  sdf.format(date);
//            Date recycleTime  = sdf.parse(mydate);
//            recover.setCreateTime(recycleTime);
//            recover.setModifiedTime(recycleTime);
//            recover.setRecheckTime(recycleTime);
//            recover.setBigDataTime(recycleTime);
//            recover.setCarryTime(recycleTime);
//        }
//        //saasRecoverApplicationList.add(recover);
//        return recover;
//    }
//
//    /**
//     * 往回收信息明细表中插入数据
//     * @param impt 导入的数据实体
//     * @param recoverApp  回收信息主表实体
//     * @return
//     */
//    private void getSaasRecoverInfo(ImportSaasApplication impt,SaasRecoverApplication recoverApp) throws ParseException, IllegalAccessException {
//        Field[] fileds = ImportSaasApplication.class.getDeclaredFields();
//        List<SaasRecoverInfo> recoverInfoList = new ArrayList<>();
//        SaasRecoverInfo saasRecoverInfo = null;
//        for(Field field : fileds ){
//            field.setAccessible(true);
//            if(field.getName().startsWith("service")){
//               Object val  = field.get(impt);
//               //服务为1的需要导入
//                if("1".equals(val)){
//                    saasRecoverInfo = new SaasRecoverInfo();
//                    saasRecoverInfo.setIsImport("1");
//                    saasRecoverInfo.setAppInfoId(recoverApp.getId());
//                    saasRecoverInfo.setReName(impt.getName());
//                    saasRecoverInfo.setReIdcard(impt.getIdcard());
//                    saasRecoverInfo.setReOrgName(impt.getWorkOrgName());
//                    if (!"/".equals(impt.getRecycleTime())) {
//                        SimpleDateFormat sdf  = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US);
//                        Date date = sdf.parse(impt.getRecycleTime());
//                        sdf=new SimpleDateFormat("yyyy/MM/dd");
//                        String mydate  =  sdf.format(date);
//                        Date recycleTime  = sdf.parse(mydate);
//                        //Date recycleTime = sdf.parse(impt.getRecycleTime());
//                        saasRecoverInfo.setCreateTime(recycleTime);
//                        saasRecoverInfo.setModifiedTime(recycleTime);
//                    }
//                    recoverInfoList.add(saasRecoverInfo) ;
//                }
//            }
//        }
//        if(!recoverInfoList.isEmpty()){
//            saasRecoverInfoService.saveBatch(recoverInfoList);
//        }
//    }
//    /**
//     * 新增一条记录
//     */
//    private void createNewRecord(ImportSaasApplication impt) throws Exception {
//        Instant instant = LocalDate.of(2019, 8, 8)
//                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
//        Date date = Date.from(instant);
//        SaasApplicationMerge merge = getSaasApplicationMerge(impt);
//        merge.setApplicationTime(date);
//        merge.setCreateTime(date);
//        merge.setModifiedTime(date);
//        merge.setRecheckTime(date);
//        merge.setBigDataTime(date);
//        merge.setCarryTime(date);
//        merge.insert();
//
//        SaasApplication saasApplication = getSaasApplication(impt, merge);
//        saasApplication.setApplicationTime(date);
//        saasApplication.setCreateTime(date);
//        saasApplication.setModifiedTime(date);
//        saasApplication.setRecoverFlag("0");
//        saasApplication.insert();
//
//        saveExt(impt, saasApplication);
//    }
//
//    private SaasApplicationMerge getSaasApplicationMerge(ImportSaasApplication impt) {
//        SaasApplicationMerge merge = new SaasApplicationMerge();
//        merge.setIsImport("1");
//        merge.setStatus(ApplicationInfoStatus.USE.getCode());
//        merge.setCreator(impt.getIdcard());
//        merge.setCreatorName(impt.getName());
//        merge.setOrgName(impt.getWorkOrgName());
//        merge.setPostType(impt.getPostType());
//        merge.setMobileWork(impt.getPhone());
//        merge.setApplicationTime(impt.getSubmitTime());
//        merge.setCreateTime(impt.getSubmitTime());
//        merge.setModifiedTime(impt.getSubmitTime());
//        merge.setPoliceCategory(impt.getPoliceCategory());
//        merge.setRecheckTime(impt.getSubmitTime());
//        merge.setBigDataTime(impt.getBigDataTime());
//        merge.setCarryTime(impt.getCarryTime());
//        return merge;
//    }
//
//    private SaasApplication getSaasApplication(ImportSaasApplication impt, SaasApplicationMerge merge) {
//        SaasApplication saasApplication = new SaasApplication();
//        saasApplication.setIsImport("1");
//        saasApplication.setStatus(ApplicationInfoStatus.USE.getCode());
//        saasApplication.setCreator(impt.getIdcard());
//        saasApplication.setCreatorName(impt.getName());
//        saasApplication.setOrgName(impt.getWorkOrgName());
//        saasApplication.setPostType(impt.getPostType());
//        saasApplication.setMobileWork(impt.getPhone());
//        saasApplication.setApplicationTime(impt.getSubmitTime());
//        String orgName = impt.getRealOrgName();//所属地区或警种
//        String area = AreaPoliceCategoryUtils.getAreaName(orgName);
//        if("省厅".equals(area)){//是省厅的所属地区为省厅 警种为所属警种，非省厅的只有地区
//            saasApplication.setAreas(area);
//            saasApplication.setApplyType("1");
//            String policecategory = AreaPoliceCategoryUtils.getPoliceCategory(orgName);
//            saasApplication.setPoliceCategory(policecategory);
//        }else{
//            saasApplication.setApplyType("0");
//            saasApplication.setAreas(area);
//        }
//       /* String param = impt.getPoliceCategory();//二级管理单位（地市/警种）
//        String area = AreaPoliceCategoryUtils.getAreaName(param);
//        if(StringUtils.isNotEmpty(area)){
//            saasApplication.setApplyType("0");
//            saasApplication.setAreas(area);
//        }else{
//            saasApplication.setApplyType("1");
//            saasApplication.setPoliceCategory(param);
//        }*/
//        saasApplication.setCreateTime(impt.getSubmitTime());
//        saasApplication.setModifiedTime(impt.getSubmitTime());
//        saasApplication.setIp(impt.getIp());
//        saasApplication.setMergeId(merge.getId());
//        return saasApplication;
//    }
//
//    private void saveExt(ImportSaasApplication impt, SaasApplication saasApplication) throws Exception {
//        Field[] fields = ImportSaasApplication.class.getDeclaredFields();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            String name = field.getName();
//            if (name.startsWith("service")) {
//                Object val = field.get(impt);
//                if ("1".equals(val)) {
//                    Excel excel = field.getAnnotation(Excel.class);
//                    SaasApplicationExt ext = new SaasApplicationExt();
//                    ext.setServiceName(excel.name());
//                    ext.setMasterId(saasApplication.getId());
//                    ext.insert();
//                }
//            }
//        }
//    }

}

