package com.hirisun.cloud.system.controller.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.param.FilesParam;
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
    @PutMapping("/refFiles")
	public void refFiles(@RequestBody SubpageParam param) {
		filesService.refFiles(param);
	}
	
	@ApiIgnore
    @ApiOperation("根据 refId 删除文件信息")
    @PutMapping("/remove")
    public void remove(@RequestBody SubpageParam param) {
		filesService.remove(param.getSubpageId());
	}
	
	@ApiIgnore
    @ApiOperation("根据 refid 获取文件信息")
    @PutMapping("/find")
    public List<FilesVo> find(@RequestBody SubpageParam param) {
		List<FilesVo> list = filesService.findByRefId(param.getRefId());
		return list;
	}
	
	@ApiIgnore
    @ApiOperation("批量保存文件信息")
    @PutMapping("/save/batch")
    public void saveBatch(@RequestBody FilesParam param) {
		filesService.saveBatch(param);
	}
	
	@ApiIgnore
    @ApiOperation("根据文件id集合批量删除文件信息")
    @PutMapping("/save/delete")
    public void deleteBatch(@RequestBody FilesParam param) {
		filesService.deleteBatch(param);
	}
	
	@ApiIgnore
    @ApiOperation("更新file的关联关系")
    @PutMapping("/update/ref")
    public void updateFileRef(@RequestBody FilesParam param) {
		
	}
}
