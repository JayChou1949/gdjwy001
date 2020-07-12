package com.hirisun.cloud.ncov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.ncov.vo.realtime.HomePageNcovRealtimeVo;
import com.hirisun.cloud.model.ncov.vo.realtime.NcovRealtimeVo;
import com.hirisun.cloud.ncov.service.NcovRealtimeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/ncov/realtime")
@RestController
@Api("疫情实时数据接口")
public class NcovRealTimeController {

    @Autowired
    private NcovRealtimeService ncovRealtimeService;

    @ApiOperation(value = "疫情实时数据接口", notes = "接口说明")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = HomePageNcovRealtimeVo.class)
    )
    @GetMapping("/query")
    public QueryResponseResult realtime() {

        HomePageNcovRealtimeVo homePageNcovRealtimeVo = ncovRealtimeService.getHomePageNcovRealtimeVo();
        return QueryResponseResult.success(homePageNcovRealtimeVo);
    }

    @ApiOperation(value = "疫情实时数据编辑", notes = "接口说明")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = ResponseResult.class)
    )
    @PostMapping("/save")
    public ResponseResult save(NcovRealtimeVo vo) {
        ncovRealtimeService.editNcovRealtime(vo);
        return ResponseResult.success();
    }
}
