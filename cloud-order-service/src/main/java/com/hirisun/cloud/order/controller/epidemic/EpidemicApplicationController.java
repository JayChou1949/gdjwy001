package com.hirisun.cloud.order.controller.epidemic;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.epidemic.EpidemicApplication;
import com.hirisun.cloud.order.controller.manage.ApplyController;
import com.hirisun.cloud.order.handler.CommonHandler;
import com.hirisun.cloud.order.service.apply.ApplyService;
import com.hirisun.cloud.order.service.epidemic.EpidemicApplicationService;
import com.hirisun.cloud.order.vo.ImplRequest;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * <p>
 * SaaS资源申请原始信息表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-03
 */
@Slf4j
@Api(tags = "疫情应用申请管理")
@RestController
@RequestMapping("/order/epidemicApplication")
public class EpidemicApplicationController extends ApplyController<EpidemicApplication> {

    @Autowired
    private ApplyService<EpidemicApplication> applyService;

    @Autowired
    private EpidemicApplicationService epidemicApplicationService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public QueryResponseResult page(@LoginUser UserVO user,
                                    @ApiParam("页码") @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                    @ApiParam("每页数量") @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                    @ApiParam("申请人姓名") @RequestParam(required = false) String userName,
                                    @ApiParam("服务名称") @RequestParam(required = false) String serviceName,
                                    @ApiParam("申请单号") @RequestParam(required = false) String orderNumber,
                                    @ApiParam("开始时间") @RequestParam(required = false) String startDate,
                                    @ApiParam("结束时间") @RequestParam(required = false) String endDate,
                                    @ApiParam("状态, 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回") @RequestParam(required = false, defaultValue = "") String status,
                                    @ApiParam("处理人,0:全部 1:我 2:其它人") @RequestParam(required = false, defaultValue = "0") String processType) {
        if (!ApplyInfoStatus.REVIEW.getCode().equals(status)
                && !ApplyInfoStatus.IMPL.getCode().equals(status)) {
            processType = null; // 不是待审核/待实施状态,不能过滤处理人
        }
        IPage<EpidemicApplication> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("userName", CommonHandler.dealNameForEqualQuery(userName));
        param.put("status", status);
        param.put("orderNumber",CommonHandler.dealNameforQuery(orderNumber));
        param.put("processType", processType);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        //要查询serviceName需要多关联两张表，需要时才关联，提高查询效率
        if(StringUtils.isBlank(serviceName)){
            page = epidemicApplicationService.getFlowPage(user, page, param);
        }else {
            param.put("serviceName",CommonHandler.dealNameforQuery(serviceName));
            page = epidemicApplicationService.getFlowPageWithServiceName(user, page, param);
        }
        return QueryResponseResult.success(page);
    }

    @ApiOperation(value = "新建申请单")
    @PutMapping("/create")
    public QueryResponseResult<EpidemicApplication> create(@LoginUser UserVO user, @RequestBody EpidemicApplication info) {
        epidemicApplicationService.create(user, info);
        applyService.workflowStart(info, user);

        return QueryResponseResult.success("发起流程成功");
    }

    @ApiOperation(value = "申请详情")
    @GetMapping("/detail")
    public QueryResponseResult detail(@LoginUser UserVO user, @ApiParam("申请工单id") @RequestParam String id) {
        EpidemicApplication detail = epidemicApplicationService.getDetails(id);
        return applyService.detail(detail);
    }

    @ApiOperation(value = "修改申请信息")
    @PutMapping("/update")
    public QueryResponseResult<EpidemicApplication> update(@LoginUser UserVO user, @RequestBody EpidemicApplication info) throws IOException {
        epidemicApplicationService.update(user,info);
        return QueryResponseResult.success(null);
    }

    @ApiOperation(value = "审批")
    @PutMapping("/approve")
    public QueryResponseResult<FallBackVO> approve(@LoginUser UserVO user,
                     @RequestBody FallBackVO vo) {
        EpidemicApplication info = epidemicApplicationService.getById(vo.getApplyReviewRecord().getApplyId());
        return applyService.approve(info,user,vo);
    }
    @ApiOperation(value = "业务办理")
    @PutMapping("/impl")
    public QueryResponseResult<ImplRequest> impl(@LoginUser UserVO user,@RequestBody ImplRequest implRequest) throws Exception {
        return epidemicApplicationService.impl(user, implRequest);
    }

    @ApiOperation(value = "后台管理页面删除")
    @GetMapping("/delete")
    public QueryResponseResult delete(@LoginUser UserVO user, @ApiParam("申请工单id") @RequestParam String id) {
        EpidemicApplication object = epidemicApplicationService.getById(id);
        return applyService.deleteById(object,user);
    }


    @ApiOperation(value = "回退")
    @PutMapping("/reject")
    public QueryResponseResult reject(@LoginUser UserVO user,
                    @RequestBody FallBackVO vo) {
        EpidemicApplication object = epidemicApplicationService.getById(vo.getApplyReviewRecord().getApplyId());
        return applyService.reject(object,user,vo);
    }

    @ApiOperation(value = "审核驳回后提交")
    @PostMapping("/submit")
    public QueryResponseResult submit(@LoginUser UserVO user,
                                      @ApiParam("申请工单id") @RequestParam("id") String id,
                                      @ApiParam("审核类型,inner:部门内审核 kx:科信审核") @RequestParam(value = "type", defaultValue = "kx") String type,
                                      @ApiParam("审核人id,多个使用逗号分隔") @RequestParam(value = "userIds", required = false) String userIds) {
        EpidemicApplication object = epidemicApplicationService.getById(id);
        return applyService.submit(object,user, id, type, userIds);
    }

}

