package com.upd.hwcloud.controller.backend.ledger;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Bigdata;
import com.upd.hwcloud.bean.param.PageParam;
import com.upd.hwcloud.bean.param.ServiceIssueParam;
import com.upd.hwcloud.bean.param.ServiceSubscribeParam;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.daasService.ServiceIssueVo;
import com.upd.hwcloud.bean.vo.daasService.ServiceQualityVo;
import com.upd.hwcloud.bean.vo.daasService.ServiceRequestVo;
import com.upd.hwcloud.bean.vo.daasService.ServiceSubscribeVo;
import com.upd.hwcloud.common.utils.AreaPoliceCategoryUtils;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.service.IBigdataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 17:41 2019/11/5
 * @ Description：DASS、PASS服务统计
 */
@Api(description = "DASS、PASS服务统计")
@RestController
@RequestMapping("/service/statistics")
public class ServiceStatisticsController {

    @Autowired
    private IBigdataService bigdataService;


    @ApiOperation("按发布统计分页")
    @RequestMapping(value = "/issue/page", method = RequestMethod.POST)
    @ResponseBody
    public R issue(@RequestBody ServiceIssueParam param) {
        Page<ServiceIssueVo> page = bigdataService.serviceStatisticsByIssue(param);
        return R.ok(page);
    }

    @ApiOperation("按发布统计导出")
    @RequestMapping(value = "/issue/export", method = RequestMethod.GET)
    @ResponseBody
    public void issueExport( HttpServletRequest request, HttpServletResponse response, ServiceIssueParam param) {
        List<ServiceIssueVo> data = bigdataService.serviceStatisticsByIssue(param).getRecords();
        String fileName = param.getCatalog() == 1?"DaaS服务按发布统计":"PaaS服务按发布统计";
        ExcelUtil.exportServiceStatistics(request, response, fileName, ServiceIssueVo.class, data);
    }

    @ApiOperation("按订阅统计分页")
    @RequestMapping(value = "/subscription/page", method = RequestMethod.POST)
    @ResponseBody
    public R subscription(@RequestBody ServiceSubscribeParam param) {
        Page<ServiceSubscribeVo> page = bigdataService.serviceStatisticsBySubscribe(param);
        return R.ok(page);
    }

    @ApiOperation("按订阅统计导出")
    @RequestMapping(value = "/subscription/export", method = RequestMethod.GET)
    @ResponseBody
    public void qualityExport(HttpServletRequest request, HttpServletResponse response, ServiceSubscribeParam param){
        List<ServiceSubscribeVo> data = bigdataService.serviceStatisticsBySubscribe(param).getRecords();
        String fileName = param.getCatalog() == 1?"DaaS服务按订阅统计":"PaaS服务按订阅统计";
        ExcelUtil.exportServiceStatistics(request, response, fileName, ServiceSubscribeVo.class, data);
    }

    @ApiOperation("按调用统计分页")
    @RequestMapping(value = "/request/page", method = RequestMethod.POST)
    @ResponseBody
    public R request(@RequestBody ServiceSubscribeParam param) {
        Page<ServiceRequestVo> page = bigdataService.serviceStatisticsByRequest(param);
        return R.ok(page);
    }

    @ApiOperation("按调用统计导出")
    @RequestMapping(value = "/request/export", method = RequestMethod.GET)
    @ResponseBody
    public void requestExport( HttpServletRequest request, HttpServletResponse response, ServiceSubscribeParam param) {
        List<ServiceRequestVo> data = bigdataService.serviceStatisticsByRequest(param).getRecords();
        String fileName = param.getCatalog() == 1?"DaaS服务按调用统计":"PaaS服务按调用统计";
        ExcelUtil.exportServiceStatistics(request, response, fileName, ServiceRequestVo.class, data);
    }

    @ApiOperation("按质量统计分页")
    @RequestMapping(value = "/quality/page", method = RequestMethod.POST)
    @ResponseBody
    public R quality(@RequestBody PageParam param) {
        Page<ServiceQualityVo> page = bigdataService.serviceStatisticsByQuality(param);
        return R.ok(page);
    }

    @ApiOperation("按质量统计导出")
    @RequestMapping(value = "/quality/export", method = RequestMethod.GET)
    @ResponseBody
    public void qualityExport(HttpServletRequest request, HttpServletResponse response, PageParam param) {
        List<ServiceQualityVo> data = bigdataService.serviceStatisticsByQuality(param).getRecords();
        String fileName = param.getCatalog() == 1?"DaaS服务按质量统计":"PaaS服务按质量统计";
        ExcelUtil.exportServiceStatistics(request, response, fileName, ServiceQualityVo.class, data);
    }

    @ApiOperation("处理PASS数据")
    @RequestMapping(value = "/handingPaasData", method = RequestMethod.GET)
    @ResponseBody
    public R handingPaasData() {
        QueryWrapper<Bigdata> wrapper = new QueryWrapper<>();
        wrapper.in("CATA_LOG", 8,9,10);
        List<Bigdata> list = bigdataService.list(wrapper);
        for (Bigdata bigdata : list) {
            boolean flag = false;
            if (StringUtils.isEmpty(bigdata.getAreaName())){
                flag = true;
                bigdata.setAreaName(AreaPoliceCategoryUtils.getAreaName(bigdata.getName()));
            }
            if (StringUtils.isEmpty(bigdata.getPoliceCategory())){
                flag = true;
                bigdata.setPoliceCategory(AreaPoliceCategoryUtils.getPoliceCategory(bigdata.getName()));
            }
            if (flag){
                System.out.println(bigdata.getId());
                bigdata.updateById();
            }
        }
        return R.ok();
    }
}
