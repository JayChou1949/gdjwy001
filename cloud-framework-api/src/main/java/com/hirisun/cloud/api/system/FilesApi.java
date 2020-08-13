package com.hirisun.cloud.api.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.file.FilesVo;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@FeignClient("cloud-system-service")
public interface FilesApi {
		
	@ApiIgnore
    @ApiOperation("保存文件信息")
    @GetMapping("/system/files/refFiles")
	public void refFiles(SubpageParam param);
	
	@ApiIgnore
    @ApiOperation("根据 SaasSubpageId 删除文件信息")
    @GetMapping("/system/files/remove")
    public void remove(@RequestBody SubpageParam param);
	
	@ApiIgnore
    @ApiOperation("根据 SaasSubpageId 获取文件信息")
    @GetMapping("/system/files/find")
    public List<FilesVo> find(@RequestBody SubpageParam param);
}
