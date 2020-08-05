package com.hirisun.cloud.api.log;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * @author wuxiaoxing
 * @date 2020-08-01
 * @description
 */
@FeignClient(name = "cloud-system-service", fallback = SysLogApiFallback.class)
//@RequestMapping("/system/logManage")
public interface SysLogApi {

    /**
     * 保存系统日志
     * @param creator 用户身份证
     * @param remark 备注
     * @param path 功能位置
     * @ip 本机ip
     */
    @ApiOperation("保存系统日志")
    @GetMapping("/system/logManage/saveLog")
    public boolean saveLog(@RequestParam("creator") String creator,@RequestParam("remark")String remark,@RequestParam("path")String path,@RequestParam("ip")String ip);

}
