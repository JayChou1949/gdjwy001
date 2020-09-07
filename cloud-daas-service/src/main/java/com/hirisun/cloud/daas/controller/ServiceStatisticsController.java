package com.hirisun.cloud.daas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.daas.service.IBigdataService;
import com.hirisun.cloud.model.daas.vo.ServiceIssueVo;
import com.hirisun.cloud.model.daas.vo.ServiceQualityVo;
import com.hirisun.cloud.model.daas.vo.ServiceRequestVo;
import com.hirisun.cloud.model.daas.vo.ServiceSubscribeVo;
import com.hirisun.cloud.model.param.PageParam;
import com.hirisun.cloud.model.param.ServiceIssueParam;
import com.hirisun.cloud.model.param.ServiceSubscribeParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("DASS服务统计")
@RestController
@RequestMapping("/service/statistics")
public class ServiceStatisticsController {

	@Autowired
    private IBigdataService bigdataService;


    @ApiOperation("按发布统计分页")
    @RequestMapping(value = "/issue/page")
    @ResponseBody
    public ResponseResult issue(@RequestBody ServiceIssueParam param) {
        Page<ServiceIssueVo> page = bigdataService.serviceStatisticsByIssue(param);
        return QueryResponseResult.success(page);
    }
	
    @ApiOperation("按订阅统计分页")
    @RequestMapping(value = "/subscription/page", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult subscription(@RequestBody ServiceSubscribeParam param) {
        Page<ServiceSubscribeVo> page = bigdataService.serviceStatisticsBySubscribe(param);
        return QueryResponseResult.success(page);
    }
	
    @ApiOperation("按调用统计分页")
    @RequestMapping(value = "/request/page", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult request(@RequestBody ServiceSubscribeParam param) {
        Page<ServiceRequestVo> page = bigdataService.serviceStatisticsByRequest(param);
        return QueryResponseResult.success(page);
    }
	
    @ApiOperation("按质量统计分页")
    @RequestMapping(value = "/quality/page", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult quality(@RequestBody PageParam param) {
        Page<ServiceQualityVo> page = bigdataService.serviceStatisticsByQuality(param);
        return QueryResponseResult.success(page);
    }
	
	
	
}
