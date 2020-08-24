package com.hirisun.cloud.api.system;

import com.hirisun.cloud.model.user.UserVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuxiaoxing
 * @date 2020-08-01
 * @description
 */
@FeignClient("cloud-system-service")
public interface SmsApi {
    /**
     * 发送业务受理短信
     * @param submitor 申请人
     * @param bizName 业务名称
     * @param orderNum 工单号
     */
    @ApiOperation("发送业务受理短信")
    @GetMapping("/system/sms/buildSuccessMessage")
    public void buildSuccessMessage(@RequestParam UserVO submitor, @RequestParam String bizName, @RequestParam String orderNum);

    /**
     * 发送处理人短信
     * @param bizName 业务名
     * @param orderNumber 工单号
     * @param creatorId 处理人身份证
     */
    @ApiOperation("发送处理人短信")
    @GetMapping("/system/sms/buildProcessingMessage")
    public void buildProcessingMessage(@RequestParam String bizName,@RequestParam String orderNumber,@RequestParam String creatorId);
    /**
     * 发送业务完成通知短信
     * @param creatorId 业务名
     * @param bizName 业务名
     * @param orderNum 工单号
     */
    @ApiOperation("发送业务完成通知短信")
    @GetMapping("/system/sms/buildCompleteMessage")
    public void buildCompleteMessage(@RequestParam String creatorId,@RequestParam String bizName,@RequestParam String orderNum);
    /**
     * 发送业务拒绝回退短信
     * @param creatorId 业务名
     * @param bizName 业务名
     */
    @ApiOperation("发送业务拒绝回退短信")
    @GetMapping("/system/sms/buildRejectMessage")
    public void buildRejectMessage(@RequestParam String creatorId,@RequestParam String bizName);
    /**
     * 发送资源缩配短信
     * @param creatorId 业务名
     * @param bizName 业务名
     * @param orderNum 工单号
     * @param month 月
     * @param day 日
     */
    @ApiOperation("发送资源缩配短信")
    @GetMapping("/system/sms/buildRecoverMessage")
    public void buildRecoverMessage(@RequestParam String creatorId,
                                    @RequestParam String bizName,
                                    @RequestParam String orderNum,
                                    @RequestParam String month,
                                    @RequestParam String day);
}
