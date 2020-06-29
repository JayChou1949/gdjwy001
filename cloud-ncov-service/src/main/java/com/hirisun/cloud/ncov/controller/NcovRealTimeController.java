package com.hirisun.cloud.ncov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.ncov.vo.HomePageNcovRealtimeVo;
import com.hirisun.cloud.ncov.service.NcovRealtimeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
            @ApiResponse(code = 200, message = "success", response = ResponseResult.class)
    )
    @GetMapping("/query")
    public QueryResponseResult realtime() {

        HomePageNcovRealtimeVo homePageNcovRealtimeVo = ncovRealtimeService.getHomePageNcovRealtimeVo();
        return QueryResponseResult.success(homePageNcovRealtimeVo);
    }

    @ApiOperation(value = "疫情实时数据导入接口", notes = "接口说明")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = ResponseResult.class)
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "疫情实时数据json", dataType = "String", required = true),
    })
    @PostMapping("/import")
    public ResponseResult importData(@RequestBody String json) {
        ncovRealtimeService.importNcovRealtimeData(json);
        return ResponseResult.success();
    }
}
