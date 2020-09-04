package com.hirisun.cloud.api.system;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hirisun.cloud.model.system.SysDictVO;


/**
 * @author wuxiaoxing
 * @date 2020-08-01
 * @description
 */
@FeignClient("cloud-system-service")
public interface SystemApi {

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

    /**
     * 获取数据字典列表
     * @return
     */
    @ApiOperation(value = "feign调用获取数据字典列表")
    @GetMapping("/system/dictManage/feign/list")
    public String feignList();

    /**
     * 根据字典值获取数据字典
     */
    @ApiOperation(value = "feign调用根据字典值获取数据字典列表")
    @GetMapping("/system/dictManage/feign/getByValue")
    public String feignGetByValue(@RequestParam("value") String value);

    @ApiIgnore
    @ApiOperation(value = "feign调用根据字典值获取数据字典列表")
    @GetMapping("/system/dictManage/feign/getById")
    public SysDictVO feignGetById(@RequestParam("id") String id);
    
    @ApiIgnore
    @ApiOperation(value = "feign调用根据字典值获取数据字典列表")
    @GetMapping("/system/dictManage/feign/findByvalue")
    public List<SysDictVO> findDictByValue(@RequestParam("value") String value);
}
