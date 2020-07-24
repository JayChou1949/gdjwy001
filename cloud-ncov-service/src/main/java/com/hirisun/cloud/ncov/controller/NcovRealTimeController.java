package com.hirisun.cloud.ncov.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.common.util.ExcelUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.ncov.vo.realtime.HomePageNcovRealtimeVo;
import com.hirisun.cloud.model.ncov.vo.realtime.NcovRealtimeVo;
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
    
    @ApiOperation(value = "后台获取疫情实时省市数据", notes = "接口说明")
    @ApiImplicitParams({
    	  @ApiImplicitParam(name="regionType",value="区域类型1=省,2=市",dataType="int", paramType = "query")
    })
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = NcovRealtimeVo.class)
    )
    @PostMapping("/find")
    public QueryResponseResult find(@RequestParam(required = true, defaultValue = "1") int regionType) {
        List<NcovRealtimeVo> list = ncovRealtimeService.findNcovRealtimeByRegionType(regionType);
        return QueryResponseResult.success(list);
    }
    
    @ApiOperation(value = "后台获取疫情实时省市统计数据", notes = "接口说明")
    @ApiImplicitParams({
    	  @ApiImplicitParam(name="regionType",value="区域类型1=省,2=市",dataType="int", paramType = "query")
    })
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = NcovRealtimeVo.class)
    )
    @PostMapping("/total")
    public QueryResponseResult total(@RequestParam(required = true, defaultValue = "1") int regionType) {
    	NcovRealtimeVo ncovRealtimeVo = ncovRealtimeService.countNcovRealTime(regionType);
        return QueryResponseResult.success(ncovRealtimeVo);
    }
    
    @ApiOperation(value = "后台疫情实时数据全省/全市导出excel", notes = "接口说明")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "serviceType", value = "服务类型", required = true, paramType = "query"),
        @ApiImplicitParam(name = "dataType", value = "数据类型", required = true,paramType = "query"),
    	@ApiImplicitParam(name="regionType",value="区域类型1=省,2=市",required = true,paramType = "query")
    })
    @GetMapping("/region/export")
    public QueryResponseResult regionExport(@RequestParam(required = true) String serviceType,
    		@RequestParam(required = true) String dataType,
    		@RequestParam(required = true) int regionType,
    		HttpServletRequest request,HttpServletResponse response) {
    	String filePath = ncovRealtimeService.exportNcovRealtimeByRegionType(serviceType,dataType,regionType);
    	return QueryResponseResult.success(filePath);
    }
    
    
    
    
}
