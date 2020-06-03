package com.upd.hwcloud.controller.backend.common;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.ServiceFeedback;
import com.upd.hwcloud.bean.entity.ServiceFeedbackReply;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.IServiceFeedbackReplyService;
import com.upd.hwcloud.service.IServiceFeedbackService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 服务用户反馈 前端控制器
 * </p>
 *
 * @author huru
 * @since 2019-02-15
 */
@Controller
@RequestMapping("/serviceFeedback")
public class ServiceFeedbackController {
    @Autowired
    private IServiceFeedbackReplyService serviceFeedbackReplyService;
    @Autowired
    private IServiceFeedbackService serviceFeedbackService;
    @ApiOperation("添加服务反馈")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public R create(@LoginUser User user, @RequestBody ServiceFeedback feedback) {
        feedback.setCreator(user.getIdcard());
        feedback.insert();
        return R.ok();
    }
    @ApiOperation("回复评价")
    @ApiImplicitParams({

            @ApiImplicitParam(name="content", value="回复内容", required = true, dataType="String",paramType = "form"),
            @ApiImplicitParam(name="feedbackId", value="评价ID",required = true, dataType="String",paramType = "form"),
    })
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    @ResponseBody
    public R reply(@LoginUser User user,ServiceFeedbackReply reply) {
        reply.setId(UUIDUtil.getUUID());
        reply.setCreator(user.getIdcard());
        reply.setCreatorName(user.getName());
        serviceFeedbackReplyService.save(reply);
      serviceFeedbackService.updateReplyNum(reply.getFeedbackId());
        return R.ok();
    }
}

