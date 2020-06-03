package com.upd.hwcloud.controller.portal.ncov;

import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.ncov.NcovEcsOverview;
import com.upd.hwcloud.bean.vo.ncov.NcovIaasVo;
import com.upd.hwcloud.common.utils.easypoi.NcovEcsImportUtil;
import com.upd.hwcloud.service.application.IApplicationInfoService;
import com.upd.hwcloud.service.application.IIaasTxyfwImplService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author wuc
 * @date 2020/2/27
 */
@Api("疫情-IaaS(ECS)")
@RestController
@RequestMapping("/ncov/iaas")
public class NcovIaasController {

    @Autowired
    private IApplicationInfoService applicationInfoService;

    @Autowired
    private IIaasTxyfwImplService iaasTxyfwImplService;

    @Value("${ncov.iaas.start.time}")
    private String startTime;


    /**
     * 疫情支撑iaas数据总览
     * @return
     */
    @ApiOperation("总览")
    @RequestMapping(value = "/overview",method = RequestMethod.GET)
    public R overview(){
        return R.ok(getOverview());
    }

    @ApiOperation("支撑情况")
    @RequestMapping(value = "/support",method = RequestMethod.GET)
    public R support(){
        return R.ok(getSupport());
    }

    @ApiOperation("分配情况")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public  R list(){

        return R.ok(getAllocation());

    }

    /**
     * 总览
     * @return
     */
    private Map<String,Object> getOverview(){

        NcovEcsOverview overview =  NcovEcsImportUtil.getOverviewData();
        Map<String,Object> res = Maps.newHashMapWithExpectedSize(7);
        res.put("ecsNum",overview.getTotal());
        res.put("cpuNum",overview.getCpu());
        res.put("memoryNum",overview.getMemory());
        res.put("storageNum", overview.getStorage());
        return  res;

    }

    /**
     * 支撑
     * @return
     */
    private Map<String,Integer> getSupport(){
        NcovEcsOverview overview =  NcovEcsImportUtil.getOverviewData();
        Map<String,Integer> res = Maps.newHashMapWithExpectedSize(7);
        res.put("policeCategoryNum",overview.getSupportPolice());
        res.put("areaNum",overview.getSupportArea());
        res.put("appNum",overview.getSupportApp());
        return res;
    }

    private List<NcovIaasVo> getAllocation(){
        List<NcovIaasVo> list = NcovEcsImportUtil.getAllocationData();
        return list;
    }
}
