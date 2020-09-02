package com.hirisun.cloud.order.controller.manage;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.contains.WorkflowNodeAbilityType;
import com.hirisun.cloud.common.util.ExceptionPrintUtil;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.util.WorkflowUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.WorkflowActivityVO;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.order.bean.apply.ApplyInfo;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.continer.FormNum;
import com.hirisun.cloud.order.continer.HandlerWrapper;
import com.hirisun.cloud.order.service.apply.ApplyInfoService;
import com.hirisun.cloud.order.service.apply.ApplyReviewRecordService;
import com.hirisun.cloud.order.vo.ApproveVO;
import com.hirisun.cloud.order.vo.ImplRequest;
import com.hirisun.cloud.redis.lock.DistributeLock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 审核记录表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@Api(tags = "审核记录管理")
@RestController
@RequestMapping("/order/applyReviewRecordManage")
public class ApplyReviewRecordManageController {

    @Autowired
    private ApplyReviewRecordService applyReviewRecordService;
    /**
     * 根据环节id获取环节信息
     * @id 工单id
     */
    @ApiIgnore
    @ApiOperation("根据工单id获取对应的审核记录")
    @PostMapping("/feign/getAllReviewInfoByAppInfoId")
    public List<ApplyReviewRecordVO> getAllReviewInfoByAppInfoId(@RequestParam String id) {
        List<ApplyReviewRecord> list = applyReviewRecordService.getAllReviewInfoByAppInfoId(id);
        if(CollectionUtils.isNotEmpty(list)) {
            List<ApplyReviewRecordVO> newList = JSON.parseObject(JSON.toJSON(list).toString(),
                    new TypeReference<List<ApplyReviewRecordVO>>(){});
            return newList;
        }
        return null;
    }

    /**
     * 根据环节id获取环节信息
     * @id 工单id
     */
    @ApiIgnore
    @ApiOperation("根据工单id获取对应的实施审批记录")
    @PostMapping("/feign/getLastPassReviewInfoByAppInfoId")
    public ApplyReviewRecordVO getLastPassReviewInfoByAppInfoId(@RequestParam String id) {
        ApplyReviewRecord applyReviewRecord = applyReviewRecordService.getLastPassReviewInfoByAppInfoId(id);
        if (applyReviewRecord != null) {
            ApplyReviewRecordVO vo = new ApplyReviewRecordVO();
            BeanUtils.copyProperties(applyReviewRecord,vo);
            return vo;
        }
        return null;
    }
}

