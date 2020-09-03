package com.hirisun.cloud.api.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.app.vo.FunDetailVo;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@FeignClient("cloud-system-service")
public interface FunDetailApi {

	@ApiIgnore
    @ApiOperation("保存功能详情")
    @PutMapping("/system/fundetail/save")
    public void save(@RequestBody SubpageParam param);
	
	@ApiIgnore
    @ApiOperation("根据masterId获取功能详情")
	@PutMapping("/system/fundetail/find")
    public List<FunDetailVo> find(@RequestBody SubpageParam param);
	
	@ApiIgnore
    @ApiOperation("根据masterId删除功能详情")
	@PutMapping("/system/fundetail/remove")
    public void remove(@RequestBody SubpageParam param);
}
