package com.hirisun.cloud.api.order;

import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.user.UserVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * @author wuxiaoxing
 * @date 2020-09-01
 * @description
 */
@FeignClient("cloud-order-service")
public interface ApplyReviewRecordApi {

    /**
     * 根据环节id获取环节信息
     *
     * @id 工单id
     */
    @ApiIgnore
    @ApiOperation("根据工单id获取对应的审核记录")
    @PostMapping("/order/applyReviewRecordManage/feign/getAllReviewInfoByAppInfoId")
    public List<ApplyReviewRecordVO> getAllReviewInfoByAppInfoId(@RequestParam String id);

    /**
     * 根据环节id获取环节信息
     *
     * @id 工单id
     */
    @ApiIgnore
    @ApiOperation("根据工单id获取对应的实施审批记录")
    @PostMapping("/order/applyReviewRecordManage/feign/getLastPassReviewInfoByAppInfoId")
    public ApplyReviewRecordVO getLastPassReviewInfoByAppInfoId(@RequestParam String id);
}
