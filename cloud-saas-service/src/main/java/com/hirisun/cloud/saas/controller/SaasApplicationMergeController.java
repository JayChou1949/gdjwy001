package com.hirisun.cloud.saas.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.model.saas.vo.SaasApplicationMergeVO;
import com.hirisun.cloud.saas.bean.SaasApplicationMerge;
import com.hirisun.cloud.saas.service.ISaasApplicationMergeService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.Map;

@Api(tags = "saas工单合并")
@RestController
@RequestMapping("/saas/saasApplicationMerge")
public class SaasApplicationMergeController {

	@Autowired
	private ISaasApplicationMergeService saasApplicationMergeService;
	
	@ApiIgnore
	@ApiOperation("获取工单列表")
    @PostMapping(value = "/getFlowPage")
    public Page<SaasApplicationMergeVO> getFlowPage(@RequestParam String userId, @RequestParam Map<String, Object> param) {
		return saasApplicationMergeService.getFlowPage(userId, param);
	}

	@ApiIgnore
	@ApiOperation("根据服务名筛选工单列表")
	@PostMapping(value = "/getFlowPageWithServiceName")
	public Page<SaasApplicationMergeVO> getFlowPageWithServiceName(@RequestParam String userId,@RequestParam Map<String, Object> param) {
		return saasApplicationMergeService.getFlowPageWithServiceName(userId,param);
	}

	@ApiIgnore
	@ApiOperation("工单合并")
	@GetMapping(value = "/merge")
	public SaasApplicationMergeVO merge(@RequestParam String userId, @RequestParam String ids) {
		SaasApplicationMerge merge = saasApplicationMergeService.merge(userId, ids);
		SaasApplicationMergeVO vo = new SaasApplicationMergeVO();
		BeanUtils.copyProperties(merge, vo);
		return vo;
	}

	@ApiIgnore
	@ApiOperation("删除工单")
	@GetMapping(value = "/deleteById")
	public void deleteById(@RequestParam String userId, @RequestParam String id) {
		saasApplicationMergeService.deleteById(userId, id);

	}

	@ApiIgnore
	@ApiOperation("更新工单")
	@PutMapping(value = "/update")
	public void update(@RequestParam String userId, @RequestBody SaasApplicationMergeVO	vo) {
		SaasApplicationMerge saasApplicationMerge = new SaasApplicationMerge();
		BeanUtils.copyProperties(vo, saasApplicationMerge);
		saasApplicationMergeService.update(userId, saasApplicationMerge);

	}

	@ApiIgnore
	@ApiOperation("获取合并工单信息")
	@GetMapping(value = "/getById")
	public SaasApplicationMergeVO getById(@RequestParam String id) {
		SaasApplicationMerge merge = saasApplicationMergeService.getById(id);
		SaasApplicationMergeVO vo = new SaasApplicationMergeVO();
		BeanUtils.copyProperties(merge, vo);
		return vo;
	}

	@ApiIgnore
	@ApiOperation("获取合并工单明细")
	@GetMapping(value = "/getDetails")
	public SaasApplicationMergeVO getDetails(@RequestParam String id) {
		SaasApplicationMerge merge = saasApplicationMergeService.getDetails(id);
		SaasApplicationMergeVO vo = new SaasApplicationMergeVO();
		BeanUtils.copyProperties(merge, vo);
		return vo;
	}

	@ApiIgnore
	@ApiOperation("更新工单")
	@GetMapping(value = "/updateById")
	public void updateById(@RequestBody SaasApplicationMergeVO vo) {
		SaasApplicationMerge saasApplicationMerge = new SaasApplicationMerge();
		BeanUtils.copyProperties(vo, saasApplicationMerge);
		saasApplicationMergeService.updateById(saasApplicationMerge);
	}

	@ApiIgnore
	@ApiOperation("更新业务办理时间")
	@GetMapping(value = "/updateCarryTime")
	public void updateCarryTime(@RequestParam String id) {
		saasApplicationMergeService.update(new SaasApplicationMerge(), new UpdateWrapper<SaasApplicationMerge>().lambda()
				.eq(SaasApplicationMerge::getId, id)
				.set(SaasApplicationMerge::getCarryTime, new Date()));
	}
	@ApiIgnore
	@ApiOperation("根据参数更新合并工单")
	@PutMapping(value = "/updateByParams")
	public void updateByParams(@RequestBody SaasApplicationMergeVO vo) {
		LambdaUpdateWrapper<SaasApplicationMerge> wrapper = new UpdateWrapper<SaasApplicationMerge>().lambda()
				.eq(SaasApplicationMerge::getId, vo.getId());
		if (vo.getStatus() != null) {
			wrapper.set(SaasApplicationMerge::getStatus, vo.getStatus());
		}
		saasApplicationMergeService.update(new SaasApplicationMerge(), wrapper);
	}

}
