package com.hirisun.cloud.api.system;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hirisun.cloud.model.system.OperateRecordVo;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@FeignClient("cloud-system-service")
public interface OperateRecordApi {

	@ApiIgnore
    @ApiOperation("根据配置id验证是否存在上线的记录")
    @PostMapping("/system/operate/exits")
    public boolean isNotEmpty(String configId);
	
	@ApiIgnore
    @ApiOperation("新增操作记录")
    @PostMapping("/system/operate/save")
    public void save(@RequestBody OperateRecordVo vo);
	
	@ApiIgnore
    @ApiOperation("新增最新操作记录")
    @PostMapping("/system/operate/save/latest")
    public void saveLatest(@RequestBody OperateRecordVo vo);
}
