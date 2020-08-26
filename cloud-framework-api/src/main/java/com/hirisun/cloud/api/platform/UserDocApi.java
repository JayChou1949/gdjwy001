package com.hirisun.cloud.api.platform;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hirisun.cloud.model.platform.vo.UserDocVo;

import io.swagger.annotations.ApiOperation;

@FeignClient("cloud-platform-service")
public interface UserDocApi {
	
	@ApiOperation(value = "购物车保存购物项")
    @PostMapping("/api/userDoc/find")
	public List<UserDocVo> find(@RequestParam String domainValue);
}
