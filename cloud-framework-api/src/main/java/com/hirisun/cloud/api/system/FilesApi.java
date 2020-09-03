package com.hirisun.cloud.api.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.param.FilesParam;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@FeignClient("cloud-system-service")
public interface FilesApi {
		
	@ApiIgnore
    @ApiOperation("保存文件信息")
    @PutMapping("/system/files/refFiles")
	public void refFiles(@RequestBody SubpageParam param);
	
	@ApiIgnore
    @ApiOperation("根据 SaasSubpageId 删除文件信息")
    @PutMapping("/system/files/remove")
    public void remove(@RequestBody SubpageParam param);
	
	@ApiIgnore
    @ApiOperation("根据 refId 获取文件信息")
    @PutMapping("/system/files/find")
    public List<FilesVo> find(@RequestBody SubpageParam param);
	
	@ApiIgnore
    @ApiOperation("批量保存文件信息")
    @PutMapping("/system/files/save/batch")
    public void saveBatch(@RequestBody FilesParam param);
	
	@ApiIgnore
    @ApiOperation("根据文件id集合批量删除文件信息")
    @PutMapping("/system/files/save/delete")
    public void deleteBatch(@RequestBody FilesParam param);
	
	@ApiIgnore
    @ApiOperation("更新file的关联关系")
    @PutMapping("/system/files/update/ref")
    public void updateFileRef(@RequestBody FilesParam param);
}
