package com.hirisun.cloud.order.controller.reported;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.hirisun.cloud.api.system.ServiceLimitApi;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.contains.ApplicationInfoStatus;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.bean.epidemic.EpidemicApplication;
import com.hirisun.cloud.order.bean.reported.IaasZysb;
import com.hirisun.cloud.order.controller.manage.ApplyController;
import com.hirisun.cloud.order.service.apply.ApplyService;
import com.hirisun.cloud.order.service.reported.IIaasZysbService;
import com.hirisun.cloud.redis.lock.DistributeLock;
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
public class ReportedResourceController extends ApplyController<IaasZysb> {

    @Autowired
    private DistributeLock lock;

    @Autowired
    private IIaasZysbService iaasZysbService;

    @Autowired
    private ApplyService<IaasZysb> applyService;

    @Autowired
    private ServiceLimitApi serviceLimitApi;

    @Autowired
    private SmsApi smsApi;

    @ApiOperation(value = "新建申请")
    @ApiImplicitParam(name="userId", value="部门内审核人身份证号", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public QueryResponseResult create(@LoginUser UserVO user, @RequestBody IaasZysb info) throws IOException {
        iaasZysbService.create(user,info);
        applyService.workflowStart(info, user);
        return QueryResponseResult.success(null);
    }
  
    @ApiOperation(value = "修改申请信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public QueryResponseResult update(@LoginUser UserVO user, @RequestBody IaasZysb info) throws IOException {
        iaasZysbService.update(user,info);
        return QueryResponseResult.success(null);
    }

    @ApiOperation(value = "申请详情")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public QueryResponseResult detail(@LoginUser UserVO user,@PathVariable String id) {
        IaasZysb info = iaasZysbService.getDetails(id);
        return applyService.detail(info);
    }


    @ApiOperation(value = "后台管理页面删除")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public QueryResponseResult deleteById(@LoginUser UserVO user, @PathVariable String id) {
        IaasZysb info=iaasZysbService.getById(id);
        return applyService.deleteById(info,user);
    }

    @ApiOperation(value = "审核驳回后提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type", value="审核类型,inner:部门内审核 kx:科信审核", required = true, defaultValue = "kx", paramType="form", dataType="String"),
            @ApiImplicitParam(name="id", value="申请单id,多个使用逗号分隔", required = true, paramType="form", dataType="String"),
            @ApiImplicitParam(name="userIds", value="审核人id,多个使用逗号分隔", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public QueryResponseResult submit(@LoginUser UserVO user, @RequestParam("id") String id,
                    @RequestParam(value = "type", defaultValue = "kx") String type,
                    @RequestParam(value = "userIds", required = false) String userIds) {
        IaasZysb info=iaasZysbService.getById(id);
        return applyService.submit(info,user, id, type, userIds);
    }

    @ApiOperation(value = "审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name="activityId", value="当前环节id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="userIds", value="转发审核人id,多个使用逗号分隔", required = true, paramType="form", dataType="String")
    })
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public QueryResponseResult approve(@LoginUser UserVO user,
                     @RequestBody FallBackVO vo) {

        IaasZysb info=iaasZysbService.getById(vo.getApplyReviewRecord().getApplyId());
        QueryResponseResult result = applyService.approve(info, user, vo);
        if ("finished".equals(result.getData())) {
            //添加到限额
            serviceLimitApi.increaseQuota(info.getId(),info.getAreas(),info.getPoliceCategory(),info.getNationalSpecialProject());
            smsApi.buildCompleteMessage(info.getCreator(), info.getBusinessName(), info.getOrderNumber());
        }
        return QueryResponseResult.success(null);
    }
    @ApiOperation(value = "回退")
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public QueryResponseResult reject(@LoginUser UserVO user,
                    @RequestBody FallBackVO vo) {
        IaasZysb info=iaasZysbService.getById(vo.getApplyReviewRecord().getApplyId());
        return applyService.reject(info,user,vo);
    }


    @ApiOperation(value = "中止业务")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="业务id", required = true, paramType="path", dataType="String"),
    })
    @RequestMapping(value = "/termination", method = RequestMethod.POST)
    public QueryResponseResult termination(@LoginUser UserVO user,String appInfoId){
        IaasZysb info = iaasZysbService.getById(appInfoId);
        return applyService.termination(info, user);
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
    public QueryResponseResult page(@LoginUser UserVO user,
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
        return QueryResponseResult.success(page);
    }

//    @ApiOperation("IAAS上报统计导出")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="areas", value="地区", paramType="query", dataType="String"),
//            @ApiImplicitParam(name="policeCategory", value="警种", paramType="query", dataType="String"),
//            @ApiImplicitParam(name = "startDate",value="开始时间：yyyy-mm-dd",  paramType="query", dataType="String"),
//            @ApiImplicitParam(name="endDate", value="结束时间：yyyy-mm-dd",  paramType="query", dataType="String")
//    })
//    @RequestMapping(value = "/count/export", method = RequestMethod.GET)
//    public void countExport(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
//                            @RequestParam(required = false) String areas,
//                            @RequestParam(required = false) String policeCategory,
//                            String startDate, String endDate) {
//        Map<String, Object> param = Maps.newHashMap();
//        param.put("startDate",startDate);
//        param.put("endDate",endDate);
//        param.put("areas",areas);
//        param.put("policeCategory",policeCategory);
//        List<ReportExport> data = iaasZysbService.reportExport(param);
//       // List<IaasZysbExport> data = iaasZysbService.countExport(param);
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"租户资源上报统计"), ReportExport.class, data);
//        ExcelUtil.export(request, response, "租户资源上报统计", workbook);
//    }


}

