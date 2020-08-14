package com.hirisun.cloud.api.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.app.vo.AppSceneVo;

import io.swagger.annotations.ApiOperation;

@FeignClient("cloud-system-service")
public interface AppSceneApi {

	
	/**
     * 保存应用场景及优势
     */
    @ApiOperation("保存应用场景及优势")
    @PostMapping("/system/appscene/save")
	public void save(@ModelAttribute SubpageParam param);
	
    /**
     * 根据master id 获取应用场景
     */
    @ApiOperation("根据master id 获取应用场景")
    @PostMapping("/system/appscene/get")
	public List<AppSceneVo> getByMasterId(@ModelAttribute SubpageParam param);
	
    /**
     * 根据master id 删除应用场景
     */
    @ApiOperation("根据master id 删除应用场景")
    @PostMapping("/system/appscene/remove")
	public void remove(@ModelAttribute SubpageParam param);
}
