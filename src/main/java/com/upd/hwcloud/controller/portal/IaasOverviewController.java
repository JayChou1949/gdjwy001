package com.upd.hwcloud.controller.portal;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.IaasOverview;
import com.upd.hwcloud.bean.entity.IaasPoliceOverview;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IIaasOverviewService;
import com.upd.hwcloud.service.IIaasPoliceOverviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private IIaasPoliceOverviewService iaasPoliceOverviewService;

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

    /**
     * 警种IAAS数据总览
     * @param name:警种全名
     * @param police：所属警种
     * @return
     */
    @ApiOperation("警种IAAS数据总览")
    @RequestMapping(value = "/getByPolice",method = RequestMethod.GET)
    @ResponseBody
    public R getByPolice(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "police") String police){
        IaasPoliceOverview iaasPoliceOverview = new IaasPoliceOverview();
        if (name == null || "广东分署缉私局".equals(name)){
            iaasPoliceOverview = iaasPoliceOverviewService.getAllByPolice(police);
        }else{
            iaasPoliceOverview = iaasPoliceOverviewService.getOne(new QueryWrapper<>(new IaasPoliceOverview()).eq("police",police).eq("name",name));
        }
        if (iaasPoliceOverview!=null){
            iaasPoliceOverview.setVcpuUsedNum(String.valueOf(Math.ceil(Double.valueOf(iaasPoliceOverview.getVcpuTotal()) * iaasPoliceOverview.getVcpuUsage())));
            iaasPoliceOverview.setVcpuAllocatedNum(String.valueOf(Math.ceil(Double.valueOf(iaasPoliceOverview.getVcpuTotal()) * iaasPoliceOverview.getVcpuAllocatedRatio())));
            iaasPoliceOverview.setGpuUsedNum(String.valueOf(Math.ceil(Double.valueOf(iaasPoliceOverview.getGpuTotal()) * iaasPoliceOverview.getGpuUsage())));
            iaasPoliceOverview.setGpuAllocatedNum(String.valueOf(Math.ceil(Double.valueOf(iaasPoliceOverview.getGpuTotal()) * iaasPoliceOverview.getGpuAllocatedRatio())));
            iaasPoliceOverview.setMemoryUsedNum(String.valueOf(Math.ceil(Double.valueOf(iaasPoliceOverview.getMemoryTotal()) * iaasPoliceOverview.getMemoryUsage())));
            iaasPoliceOverview.setMemoryAllocatedNum(String.valueOf(Math.ceil(Double.valueOf(iaasPoliceOverview.getMemoryTotal()) * iaasPoliceOverview.getMemoryAllocatedRatio())));
            iaasPoliceOverview.setDiskUsedNum(String.valueOf(Math.ceil(Double.valueOf(iaasPoliceOverview.getDiskTotal()) * iaasPoliceOverview.getDiskUsage())));
            iaasPoliceOverview.setDiskAllocatedNum(String.valueOf(Math.ceil(Double.valueOf(iaasPoliceOverview.getDiskTotal()) * iaasPoliceOverview.getDiskAllocatedRatio())));
        }
        return R.ok(iaasPoliceOverview);
    }

}

