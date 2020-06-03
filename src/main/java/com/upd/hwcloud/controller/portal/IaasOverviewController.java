package com.upd.hwcloud.controller.portal;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.math.LongMath;
import com.upd.hwcloud.bean.entity.IaasOverview;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IIaasOverviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Random;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangwy
 * @since 2019-09-09
 */
@Api(description = "IAAS首页概况")
@Controller
@RequestMapping("/api/iaasOverview")
public class IaasOverviewController {

    @Autowired
    private IIaasOverviewService iaasOverviewService;

    @ApiOperation("")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public R get(@RequestParam(value = "phase", required = false) Integer phase, @RequestParam(value = "area", required = false) String area) {
        IaasOverview iaasOverview = new IaasOverview();
        if (phase == null) {
            //全省所有数据
            iaasOverview = iaasOverviewService.getAll(area);
        } else {
            iaasOverview = iaasOverviewService.getOne(new QueryWrapper<>(new IaasOverview())
                    .eq("phase", phase)
                    .eq(StringUtils.isEmpty(area), "central", 1)//为空默认查省厅的数据
                    .eq(StringUtils.isNotEmpty(area), "area", area));
        }
        if(iaasOverview!=null){
            iaasOverview.setVcpuUsedNum(Math.ceil(Double.valueOf(iaasOverview.getVcpuTotal()) * iaasOverview.getVcpuUsed()) );
            iaasOverview.setVcpuAllocatedNum(Math.ceil(Double.valueOf(iaasOverview.getVcpuTotal()) * iaasOverview.getVcpuAllocated()) );
            iaasOverview.setGpuUsedNum(Math.ceil(Double.valueOf(iaasOverview.getGpuTotal()) * iaasOverview.getGpuUsed()) );
            iaasOverview.setGpuAllocatedNum(Math.ceil(Double.valueOf(iaasOverview.getGpuTotal()) * iaasOverview.getGpuAllocated()) );
            iaasOverview.setMemoryUsedNum(Math.ceil(Double.valueOf(iaasOverview.getMemoryTotal()) * iaasOverview.getMemoryUsed()) );
            iaasOverview.setMemoryAllocatedNum(Math.ceil(Double.valueOf(iaasOverview.getMemoryTotal()) * iaasOverview.getMemoryAllocated()) );
            iaasOverview.setDiskUsedNum(Math.ceil(Double.valueOf(iaasOverview.getDiskTotal()) * iaasOverview.getDiskUsed()) );
            iaasOverview.setDiskAllocatedNum(Math.ceil(Double.valueOf(iaasOverview.getDiskTotal()) * iaasOverview.getDiskAllocated()) );
        }
        return R.ok(iaasOverview);
    }

}

