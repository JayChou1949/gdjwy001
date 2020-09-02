package com.hirisun.cloud.saas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.api.user.UserApi;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.contains.UserType;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.saas.vo.SaasApplicationMergeVO;
import com.hirisun.cloud.model.saas.vo.SaasApplicationVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.saas.bean.SaasApplication;
import com.hirisun.cloud.saas.bean.SaasApplicationMerge;
import com.hirisun.cloud.saas.handle.CommonHandler;
import com.hirisun.cloud.saas.service.ISaasApplicationService;
import io.swagger.annotations.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hirisun.cloud.saas.service.ISaasServiceApplicationService;

import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "saas应用管理")
@RestController
@RequestMapping("/saas/application")
public class SaasApplicationController {

	@Autowired
	private ISaasServiceApplicationService saasServiceApplicationService;

	@Autowired
	private ISaasApplicationService saasApplicationService;

	@Autowired
	private UserApi userApi;
	
	@ApiIgnore
	@ApiOperation("合并提交saas服务")
    @PostMapping(value = "/submit/merge")
    public void submitMerge(@RequestParam String appInfoId, @RequestParam List<String> shoppingCartIdList) {
		saasServiceApplicationService.submitMerge(appInfoId, shoppingCartIdList);
	}

	@ApiOperation(value = "查询用户对应的二级管理员")
	@GetMapping("/lv2Manager")
	public QueryResponseResult<UserVO> getLv2Manager(@ApiParam(value = "地区",required = true) @RequestParam(required = false,defaultValue = "省厅") String area,
													 @ApiParam(value = "地区名/警种名",required = true) @RequestParam  String name) {
		if (StringUtils.isEmpty(name)) {
			return QueryResponseResult.fail("参数错误");
		}
		UserVO lv2Manager=null;
		//如果地区选择省厅，则提交给省厅下的各个警种所对应的的二级管理员
		UserVO user = new UserVO();
		user.setType(UserType.TENANT_MANAGER.getCode());
		user.setDefaultTenant("1");//是否第一租户管理员 1 是 0 否
		if ("省厅".equals(area)) {
			user.setTenantPoliceCategory(name);
			List<UserVO> userList = userApi.getUserByParams(user);
			if (CollectionUtils.isNotEmpty(userList)) {
				lv2Manager = userList.get(0);
			}
		} else {//如果地区选择其他地区，则提交给其他地区下的各个警种所对应的的二级管理员
			user.setTenantArea(area);
			List<UserVO> userList = userApi.getUserByParams(user);
			if (CollectionUtils.isNotEmpty(userList)) {
				lv2Manager = userList.get(0);
			}

		}
		return QueryResponseResult.success(lv2Manager);
	}

	@ApiOperation(value = "新建原始申请单")
	@PutMapping("/create")
	public QueryResponseResult<SaasApplication> create(@LoginUser UserVO user, @RequestBody SaasApplication info) {
		saasApplicationService.create(user, info);
		return QueryResponseResult.success(null);
	}

	@ApiOperation(value = "修改原始申请单")
	@PutMapping("/update")
	public QueryResponseResult<SaasApplication> update(@LoginUser UserVO user, @RequestBody SaasApplication info) {
		saasApplicationService.update(user, info);
		return QueryResponseResult.success(null);
	}

	@ApiOperation(value = "驳回后提交")
	@PostMapping("/submit")
	public QueryResponseResult submit(@LoginUser UserVO user, @ApiParam(value = "申请工单id",required = true) @RequestParam String id) {
		saasApplicationService.submit(user, id);
		return QueryResponseResult.success(null);
	}

	@ApiOperation(value = "删除原始申请单")
	@PostMapping("/delete")
	public QueryResponseResult create(@LoginUser UserVO user, @ApiParam(value = "申请工单id",required = true) @RequestParam String id) {
		saasApplicationService.removeById(id);
		return QueryResponseResult.success(null);
	}

	@ApiOperation("我的申请单(原始单据)")
	@GetMapping("/page")
	public QueryResponseResult<SaasApplication> page(@LoginUser UserVO user,
													 @ApiParam(value = "页码",required = false) @RequestParam(required = false, defaultValue = "1") Integer pageNum,
													 @ApiParam(value = "每页数量",required = false) @RequestParam(required = false, defaultValue = "20") Integer pageSize,
													 @ApiParam(value = "申请人",required = false) @RequestParam(required = false) String userName,
													 @ApiParam(value = "申请服务",required = false) @RequestParam(required = false) String serviceName,
													 @ApiParam(value = "申请单号",required = false) @RequestParam(required = false) String orderNumber,
													 @ApiParam(value = "开始日期",required = false) @RequestParam(required = false) String startDate,
													 @ApiParam(value = "结束日期",required = false) @RequestParam(required = false) String endDate,
													 @ApiParam(value = "状态, 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施驳回",required = false) @RequestParam(required = false, defaultValue = "") String status) {

		IPage<SaasApplication> page = new Page<>();
		page.setCurrent(pageNum);
		page.setSize(pageSize);
		Map<String, Object> param = new HashMap<>();
		param.put("user", user);
		param.put("userName", CommonHandler.dealNameforQuery(userName));
		param.put("orderNumber",CommonHandler.dealNameforQuery(orderNumber));
		param.put("serviceName",CommonHandler.dealNameforQuery(serviceName));
		param.put("status", status);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		page = saasApplicationService.getPage(user, page, param);
		return QueryResponseResult.success(page);
	}

	@ApiOperation(value = "申请详情(原始单据)")
	@GetMapping(value = "/detail")
	public QueryResponseResult detail(@LoginUser UserVO user, @ApiParam(value = "申请工单id",required = true) @RequestParam String id) {
		Map<String, Object> map = saasApplicationService.getDetail(user, id);
		return QueryResponseResult.success(map);
	}


	@ApiOperation("待合并列表")
	@GetMapping(value = "/mergePage")
	public QueryResponseResult mergePage(@LoginUser UserVO user,
					   @RequestParam(required = false, defaultValue = "1") Integer pageNum,
					   @RequestParam(required = false, defaultValue = "20") Integer pageSize,
										 @ApiParam(value = "关键字",required = true) @RequestParam(required = false) String userName) {
		IPage<SaasApplication> page = new Page<>();
		page.setCurrent(pageNum);
		page.setSize(pageSize);
		page = saasApplicationService.mergePage(user, page, userName);
		return QueryResponseResult.success(page);
	}

	@ApiOperation(value = "待合并列表驳回")
	@PostMapping("/reject")
	public QueryResponseResult reject(@LoginUser UserVO user, @ApiParam(value = "申请工单id",required = true) @RequestParam String id) {
		saasApplicationService.reject(user, id);
		return QueryResponseResult.success(null);
	}

	@ApiIgnore
	@ApiOperation("更新状态")
	@GetMapping(value = "/updateStatus")
	public void updateStatus(@RequestParam String mergeId,String status) {
		saasApplicationService.updateStatus(mergeId,status);
	}

	@ApiIgnore
	@ApiOperation("同步合并后单子的id查询合并的申请原始单")
	@GetMapping(value = "/getListByMergeId")
	public List<SaasApplicationVO> getListByMergeId(@RequestParam String id) {
		List<SaasApplication> list = saasApplicationService.getListByMergeId(id);
		if(CollectionUtils.isNotEmpty(list)) {
			List<SaasApplicationVO> newList = JSON.parseObject(JSON.toJSON(list).toString(),
					new TypeReference<List<SaasApplicationVO>>(){});
			return newList;
		}
		return null;
	}

}
