package com.hirisun.cloud.system.controller.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.app.vo.FunChaVo;
import com.hirisun.cloud.system.service.FunChaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "服务管理-功能特点")
@RestController
@RequestMapping("/system/funcha")
public class FunChaController {

	@Autowired
	private FunChaService funChaService;
	
	@ApiIgnore
    @ApiOperation("保存功能特点")
    @PostMapping("/save")
    public void save(@RequestBody SubpageParam param) {
		funChaService.save(param);
	}
	
	@ApiIgnore
    @ApiOperation("根据masterId获取功能特点")
	@PutMapping("/find")
    public List<FunChaVo> find(@RequestBody SubpageParam param) {
		List<FunChaVo> list = funChaService.find(param);
		return list;
	}
	
	@ApiIgnore
    @ApiOperation("根据masterId删除功能特点")
	@PutMapping("/remove")
    public void remove(@RequestBody SubpageParam param) {
		funChaService.remove(param);
	}
}
