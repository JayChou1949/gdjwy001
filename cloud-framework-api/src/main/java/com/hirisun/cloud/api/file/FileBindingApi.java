package com.hirisun.cloud.api.file;

import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.param.FilesParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@FeignClient("cloud-file-service")
public interface FileBindingApi {

    @ApiIgnore
    @ApiOperation(value = "保存文件绑定信息")
    @PostMapping(value = "/saveFileBinding")
    public void saveFileBinding(@RequestParam String masterId, String fileId);

    @ApiIgnore
    @ApiOperation(value = "保存多个文件绑定信息")
    @PostMapping(value = "/saveByIds")
    public void saveByIds(@RequestParam List<String> ids, String fileId);

    @ApiIgnore
    @ApiOperation(value = "删除文件绑定信息")
    @PostMapping(value = "/deleteByIds")
    public void deleteByIds(@RequestParam List<String> ids);
	

}
