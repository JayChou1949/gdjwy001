package com.hirisun.cloud.system.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.system.service.FilesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "服务管理-文件保存")
@RestController
@RequestMapping("/system/files")
public class FilesController {

	@Autowired
	private FilesService filesService;
	
	@ApiIgnore
    @ApiOperation("保存文件信息")
    @GetMapping("/refFiles")
	public void refFiles(SubpageParam param) {
		filesService.refFiles(param);
	}
	
	@ApiIgnore
    @ApiOperation("根据 SaasSubpageId 删除文件信息")
    @GetMapping("/remove")
    public void remove(@RequestBody SubpageParam param) {
		filesService.remove(param.getSubpageId());
	}
	
	@ApiIgnore
    @ApiOperation("根据 SaasSubpageId 获取文件信息")
    @GetMapping("/find")
    public void find(@RequestBody SubpageParam param) {
		filesService.findBySubpageId(param.getSubpageId());
	}
}
