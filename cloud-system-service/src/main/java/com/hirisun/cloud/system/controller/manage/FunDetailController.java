package com.hirisun.cloud.system.controller.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.app.vo.FunDetailVo;
import com.hirisun.cloud.system.service.FunDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "服务管理-功能详情")
@RestController
@RequestMapping("/system/fundetail")
public class FunDetailController {

	@Autowired
	private FunDetailService funDetailService;
	
	@ApiIgnore
    @ApiOperation("保存功能详情")
	@PutMapping("/save")
    public void save(@RequestBody SubpageParam param) {
		funDetailService.save(param);
	}
	
	@ApiIgnore
    @ApiOperation("根据masterId获取功能详情")
	@PutMapping("/find")
    public List<FunDetailVo> find(@RequestBody SubpageParam param) {
		List<FunDetailVo> list = funDetailService.getByMasterId(param);
		return list;
	}
	
	@ApiIgnore
    @ApiOperation("根据masterId删除功能详情")
	@PutMapping("/remove")
    public void remove(@RequestBody SubpageParam param) {
		funDetailService.remove(param);
	}
	
}
