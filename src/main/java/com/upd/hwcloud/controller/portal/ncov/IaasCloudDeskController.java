package com.upd.hwcloud.controller.portal.ncov;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.dto.cov.CloudDeskDetail;
import com.upd.hwcloud.bean.dto.cov.CloudDeskHomePage;
import com.upd.hwcloud.bean.dto.cov.CloudDeskNumByAreaOrPolice;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.utils.ncov.MemoryPageUtil;
import com.upd.hwcloud.service.IaasCloudDeskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 疫情桌面云申请统计
 * @author junglefisher
 * @date 2020/3/2 17:56
 */
@Api(description = "疫情桌面云申请统计")
@Controller
@RequestMapping("/iaasCloudDesk")
public class IaasCloudDeskController {

    @Autowired
    private IaasCloudDeskService iaasCloudDeskService;

    /**
     * 首页桌面云总览数据
     * @return
     */
    @ApiOperation("首页桌面云总览数据")
    @RequestMapping("/cloudDeskHomePage")
    @ResponseBody
    public R cloudDeskHomePage(){
        try {
            CloudDeskHomePage cloudDeskHomePage=iaasCloudDeskService.cloudDeskHomePage();
            return R.ok(cloudDeskHomePage);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 二级页面各地市桌面云总量
     * @return
     */
    @ApiOperation("二级页面各地市桌面云总量")
    @RequestMapping("/cloudDeskByArea")
    @ResponseBody
    public R cloudDeskByArea(){
        try {
            List<CloudDeskNumByAreaOrPolice> cloudDeskByAreas=iaasCloudDeskService.cloudDeskByArea();
            return R.ok(cloudDeskByAreas);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 二级页面各警种桌面云总量
     * @return
     */
    @ApiOperation("二级页面各地市桌面云总量")
    @RequestMapping("/cloudDeskByPolice")
    @ResponseBody
    public R cloudDeskByPolice(){
        try {
            List<CloudDeskNumByAreaOrPolice> cloudDeskByAreas=iaasCloudDeskService.cloudDeskByPolice();
            return R.ok(cloudDeskByAreas);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 二级页面各单位支撑详情
     * @return
     */
    @ApiOperation("二级页面各单位支撑详情")
    @RequestMapping("/detailByUnit")
    @ResponseBody
    public R detailByUnit(@RequestParam("pageSize") long pageSize,@RequestParam("pageNum") long pageNum ){
        try {
            List<CloudDeskDetail> cloudDeskDetails=iaasCloudDeskService.detailByUnit();
            Page<CloudDeskDetail> page = MemoryPageUtil.page(cloudDeskDetails, pageSize, pageNum);
            return R.ok(page);
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }
}
