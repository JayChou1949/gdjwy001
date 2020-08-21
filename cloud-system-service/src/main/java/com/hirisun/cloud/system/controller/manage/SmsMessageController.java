package com.hirisun.cloud.system.controller.manage;


import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.system.msg.MessageProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-29
 */
@Api(tags = "短信通知")
@RestController
@RequestMapping("/system/sms")
public class SmsMessageController {
    @Autowired
    private MessageProvider messageProvider;

    /**
     * 发送业务受理短信
     * @param submitor 申请人
     * @param bizName 业务名称
     * @param orderNum 工单号
     */
    @ApiOperation("发送业务受理短信")
    @GetMapping("/buildSuccessMessage")
    public void buildSuccessMessage(@RequestParam UserVO submitor, @RequestParam String bizName, @RequestParam String orderNum) {
        messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(submitor,bizName,orderNum));
        return ;
    }

    /**
     * 发送处理人短信
     * @param bizName 业务名
     * @param orderNumber 工单号
     * @param creatorId 处理人身份证
     */
    @ApiOperation("发送处理人短信")
    @GetMapping("/buildProcessingMessage")
    public void buildProcessingMessage(@RequestParam String bizName,@RequestParam String orderNumber,@RequestParam String creatorId) {
        messageProvider.sendMessageAsync(messageProvider.buildProcessingMessage(bizName,orderNumber,creatorId));
        return ;
    }
    /**
     * 发送业务完成通知短信
     * @param creatorId 业务名
     * @param bizName 业务名
     * @param orderNum 工单号
     */
    @ApiOperation("发送业务完成通知短信")
    @GetMapping("/buildCompleteMessage")
    public void buildCompleteMessage(@RequestParam String creatorId,@RequestParam String bizName,@RequestParam String orderNum) {
        messageProvider.sendMessageAsync(messageProvider.buildCompleteMessage(creatorId,bizName,orderNum));
        return ;
    }
    /**
     * 发送业务拒绝回退短信
     * @param creatorId 业务名
     * @param bizName 业务名
     */
    @ApiOperation("发送业务拒绝回退短信")
    @GetMapping("/buildRejectMessage")
    public void buildProcessingMessage(@RequestParam String creatorId,@RequestParam String bizName) {
        messageProvider.sendMessageAsync(messageProvider.buildRejectMessage(creatorId,bizName));
        return ;
    }
    /**
     * 发送资源缩配短信
     * @param creatorId 业务名
     * @param bizName 业务名
     * @param orderNum 工单号
     * @param month 月
     * @param day 日
     */
    @ApiOperation("发送资源缩配短信")
    @GetMapping("/buildRecoverMessage")
    public void buildRecoverMessage(@RequestParam String creatorId,
                                    @RequestParam String bizName,
                                    @RequestParam String orderNum,
                                    @RequestParam String month,
                                    @RequestParam String day) {
        messageProvider.sendMessageAsync(messageProvider.buildRecoverMessage(creatorId,bizName,orderNum,month,day));
        return ;
    }
}

