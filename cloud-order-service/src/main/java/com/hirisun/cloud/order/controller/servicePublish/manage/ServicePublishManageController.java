package com.hirisun.cloud.order.controller.servicePublish.manage;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.servicePublish.ServicePublish;
import com.hirisun.cloud.order.handler.CommonHandler;
import com.hirisun.cloud.order.service.apply.ApplyInfoService;
import com.hirisun.cloud.order.service.servicePublish.ServicePublishService;
import com.hirisun.cloud.order.vo.ApproveVO;
import com.hirisun.cloud.order.vo.ImplRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 服务发布 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@Slf4j
@RestController
@RequestMapping("/order/servicePublishManage")
public class ServicePublishManageController {

    @Autowired
    private ServicePublishService servicePublishService;

    @Autowired
    private ApplyInfoService applyInfoService;

    @ApiOperation("服务发布申请列表")
    @GetMapping("/page")
    public QueryResponseResult<ServicePublish> page(@LoginUser UserVO user,
                                                    @ApiParam("页码") @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                    @ApiParam("每页数量") @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                                    @ApiParam("申请人") @RequestParam(required = false) String userName,
                                                    @ApiParam("申请单号") @RequestParam(required = false) String orderNumber,
                                                    @ApiParam("服务名称") @RequestParam(required = false) String serviceName,
                                                    @ApiParam("开始日期") @RequestParam(required = false) String startDate,
                                                    @ApiParam("结束日期") @RequestParam(required = false) String endDate,
                                                    @ApiParam("状态, 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施驳回")  @RequestParam(required = false, defaultValue = "") String status,
                                                    @ApiParam("处理人,0:全部 1:我 2:其它人") @RequestParam(required = false, defaultValue = "0") String processType) {
        if (!ApplyInfoStatus.REVIEW.getCode().equals(status)
                && !ApplyInfoStatus.IMPL.getCode().equals(status)) {
            processType = null; // 不是待审核/待实施状态,不能过滤处理人
        }
        IPage<ServicePublish> page = new Page<>(pageNum, pageSize);

        Map<String, Object> param = Maps.newHashMap();
        param.put("user", user);
        param.put("userName", CommonHandler.dealNameforQuery(userName));
        param.put("orderNumber",CommonHandler.dealNameforQuery(orderNumber));
        param.put("serviceName",CommonHandler.dealNameforQuery(serviceName));
        param.put("status", status);
        param.put("processType", processType);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        page = servicePublishService.getPage(user, page, param);
        return QueryResponseResult.success(page);
    }

    @ApiOperation(value = "新建申请")
    @PutMapping("/create")
    public QueryResponseResult<ServicePublish> create(@LoginUser UserVO user, @RequestBody ServicePublish info) {
        return servicePublishService.create(user, info);
    }

    @ApiOperation(value = "同步业务办理通过后的编码")
    @RequestMapping(value = "/syncServiceCode", method = RequestMethod.GET)
    public QueryResponseResult syncServiceCode() {
        //TODO
        return QueryResponseResult.success(null);
    }

    @ApiOperation(value = "申请详情")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @GetMapping("detail")
    public QueryResponseResult<ServicePublish> detail(@LoginUser UserVO user, @ApiParam(value = "申请工单id",required =  true) @RequestParam String id) {
        return servicePublishService.detail(id);
    }

    @ApiOperation(value = "修改申请信息")
    @PutMapping("/update")
    public QueryResponseResult<ServicePublish> update(@LoginUser UserVO user, @RequestBody ServicePublish info) throws IOException {
        servicePublishService.update(user,info);
        return QueryResponseResult.success(null);
    }

    @ApiOperation(value = "后台管理页面删除")
    @PostMapping("/delete")
    public QueryResponseResult delete(@LoginUser UserVO user,  @ApiParam(value = "申请工单id",required =  true) @RequestParam String id) {
        return servicePublishService.deleteObj(user, id);
    }

    @ApiOperation(value = "审核驳回后提交")
    @PostMapping("/submit")
    public QueryResponseResult submit(@LoginUser UserVO user,
                                      @ApiParam(value = "申请单id,多个使用逗号分隔",required =  true) @RequestParam(value = "id") String id,
                                      @ApiParam(value = "审核类型,inner:部门内审核 kx:科信审核",required =  true) @RequestParam(value = "type", defaultValue = "inner") String type,
                                      @ApiParam(value = "审核人id,多个使用逗号分隔",required =  true) @RequestParam(value = "userIds", required = false) String userIds) {
        return servicePublishService.submit(user, id, type, userIds);
    }


    @ApiOperation(value = "转发")
    @PostMapping("/forward")
    public QueryResponseResult forward(@LoginUser UserVO user,
                                       @ApiParam(value = "当前环节id",required =  true) @RequestParam String activityId,
                                       @ApiParam(value = "转发审核人id,多个使用逗号分隔",required =  true) @RequestParam(value = "userIds", required = true) String userIds,
                                       @ApiParam(value = "申请工单id",required = true) @RequestParam String applyInfoId) {
        return servicePublishService.submit(user,activityId,userIds,applyInfoId);
    }

    @ApiOperation(value = "参与人意见")
    @PutMapping("/adviser")
    public QueryResponseResult<FallBackVO> adviser(@LoginUser UserVO user,
                     @RequestBody FallBackVO vo) {
        return applyInfoService.adviser(user, vo);
    }


    @ApiOperation(value = "审批")
    @PutMapping("/approve")
    public QueryResponseResult<FallBackVO> approve(@LoginUser UserVO user,
                     @RequestBody FallBackVO vo) {
        return servicePublishService.approve(user, vo);
    }

    @ApiOperation(value = "回退")
    @PutMapping("/reject")
    public QueryResponseResult<FallBackVO> reject(@LoginUser UserVO user,
                    @RequestBody FallBackVO vo) {
        return servicePublishService.reject(user, vo);
    }

    @ApiOperation(value = "业务办理")
    @PutMapping("/impl")
    public QueryResponseResult<ImplRequest> impl(@LoginUser UserVO user,
                                    @RequestBody ImplRequest implRequest) throws Exception {
        String applyInfoId = implRequest.getApplyInfoId();
        String nodeId = implRequest.getNodeId();
        String activityId = implRequest.getActivityId();
        return servicePublishService.impl(user, applyInfoId, nodeId, activityId, implRequest);
    }

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "一键发布")
    @RequestMapping(value = "/publish/{id}/{userId}", method = RequestMethod.POST)
    public QueryResponseResult publish(@PathVariable(value = "id") String id,@PathVariable(value = "userId") String userId) throws Exception{
//        ServicePublish info = servicePublishService.getDetailsMY(id);
//        String orderNum = OrderNum.gen(stringRedisTemplate, RedisKey.KEY_PUBLISH_PREFIX);
//        info.setOrderNumber(orderNum);
//        info.setStatus("1");
//        servicePublishService.update(info,new QueryWrapper<ServicePublish>().lambda().eq(ServicePublish::getId, id));
//        User user=new User();
//        user.setName(info.getCreatorName());
//        user.setIdcard(info.getCreator());
//        info.updateById();
//        info.setWhereFrom("1");
//        create(user,info,userId);
//        return R.ok();
        return QueryResponseResult.success(null);
    }

    @ApiOperation(value = "加办")
    @PutMapping("/add")
    public QueryResponseResult<ApproveVO> review(@LoginUser UserVO user,
                                                 @RequestBody ApproveVO approveVO) {
        return applyInfoService.add(user, approveVO);
    }


    @ApiOperation(value = "中止业务")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="业务id", required = true, paramType="path", dataType="String"),
    })
    @RequestMapping(value = "/termination", method = RequestMethod.POST)
    public QueryResponseResult termination(String appInfoId){
        return servicePublishService.termination(appInfoId);
    }

    @ApiOperation("后台管理页面分页查询-美亚")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
            @ApiImplicitParam(name="name", value="服务名称", paramType="query", dataType="String"),
    })
    @RequestMapping(value = "/pageFromMY", method = RequestMethod.GET)
    public R pageFromMY(@LoginUser UserVO user,
                        @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                        @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false) String startDate,
                        @RequestParam(required = false) String endDate,
                        @RequestParam(required = false) String subType,
                        @RequestParam(required = false) String status) {
        IPage<ServicePublish> page = new Page<>(pageNum, pageSize);
        if(StringUtils.isNotEmpty(name)){
            StringBuilder sb = new StringBuilder().append("%").append(name).append("%");
            name = sb.toString();
        }
        Map<String, Object> param = Maps.newHashMap();
        param.put("user", user);
        param.put("name", name);
        if(StringUtils.isNotEmpty(status)){
            param.put("status",status);
        }
        param.put("processType", subType);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        //TODO
//        page = servicePublishService.getPageFromMY(user, page, param,subType);
        return R.ok(page);
    }


}

