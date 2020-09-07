package com.hirisun.cloud.order.controller.manage;

import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.contains.WorkflowActivityStatus;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.WorkflowActivityVO;
import com.hirisun.cloud.model.workflow.WorkflowInstanceVO;
import com.hirisun.cloud.order.service.apply.ApplyService;
import com.hirisun.cloud.order.vo.ApproveVO;
import com.hirisun.cloud.order.vo.OrderCode;
import com.hirisun.cloud.order.vo.WorkOrder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public class ApplyController<T extends WorkOrder> {
    @Autowired
    private ApplyService applyService;

    @Autowired
    private WorkflowApi workflowApi;

    @ApiOperation(value = "转发")
    @PostMapping("/forward")
    public QueryResponseResult forward(@LoginUser UserVO user,
                                       @ApiParam(value = "当前环节id",required = true) @RequestParam String activityId,
                                       @ApiParam(value = "转发审核人id,多个使用逗号分隔",required = true) @RequestParam String userIds) {
        return applyService.forward(user, activityId, userIds);
    }

    @ApiOperation(value = "参与人意见")
    @PutMapping("/adviser")
    public QueryResponseResult<FallBackVO> adviser(@LoginUser UserVO user,
                     @RequestBody FallBackVO vo) {
        return applyService.adviser(user, vo);
    }

    @ApiOperation(value = "加办")
    @PutMapping("/add")
    public QueryResponseResult<ApproveVO> review(@LoginUser UserVO user, @RequestBody ApproveVO approveVO) {
        return applyService.add(user, approveVO);
    }

}

