package com.hirisun.cloud.system.controller.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.app.vo.AppSceneVo;
import com.hirisun.cloud.system.service.AppSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "服务管理-应用场景")
@RestController
@RequestMapping("/system/appscene")
public class AppSceneController {

	@Autowired
	private AppSceneService appSceneService;
	
	@ApiIgnore
    @ApiOperation("保存应用场景及优势")
    @PostMapping("/save")
    public void save(@RequestBody SubpageParam param) {
		appSceneService.save(param);
	}
	
	@ApiIgnore
    @ApiOperation("根据masterId获取应用场景")
    @GetMapping("/get")
    public List<AppSceneVo> getByMasterId(@RequestBody SubpageParam param) {
		List<AppSceneVo> list = appSceneService.getByMasterId(param.getMasterId());
		return list;
	}
	
	@ApiIgnore
    @ApiOperation("根据masterId删除应用场景")
    @GetMapping("/remove")
    public void remove(@RequestBody SubpageParam param) {
		appSceneService.remove(param.getMasterId());
	}
}
