package com.upd.hwcloud.controller.backend.ledger;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.dto.ByMonth;
import com.upd.hwcloud.bean.dto.ByVender;
import com.upd.hwcloud.bean.entity.Asset;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.service.IAssetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * <p>
 * 资产 前端控制器
 * </p>
 *
 * @author huru
 * @since 2018-11-27
 */
@Api(description = "资产管理接口")
@Controller
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    IAssetService assetService;

    @ApiOperation("新建资产")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public R create(@LoginUser User user, @RequestBody Asset asset) {
        asset.setCreator(user.getIdcard());
        asset.insert();
        return R.ok();
    }

    @ApiOperation("分页获取资产列表")
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public R page(Integer pageNum, Integer pageSize,String contractNo,String vender,String startTime,String endTime) {
        if(pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if(pageSize == null || pageNum <=0) {
            pageSize = 10;
        }
        Page<Asset> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        Page<Asset> assetPage = assetService.getPage(page,contractNo,vender,startTime,endTime);
        return R.ok(assetPage);
    }

    @ApiOperation("编辑资产")
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public R edit(@RequestBody Asset asset) {
        asset.updateById();
        return R.ok();
    }

    @ApiOperation("删除资产")
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public R del(@PathVariable String id) {
        assetService.removeById(id);
        return R.ok();
    }

    @ApiOperation("资产详情")
    @RequestMapping("/{id}")
    @ResponseBody
    public R info(@PathVariable String id) {
        Asset asset = assetService.getById(id);
        return R.ok(asset);
    }

    @ApiOperation("导入excel")
    @RequestMapping("/importExcel")
    @ResponseBody
    public R importExcel(@RequestParam(value = "file") MultipartFile file) {
        if(file != null) {
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.indexOf("."));
            try {
                InputStream in = file.getInputStream();
                if(".xls".equals(suffix)) {
                    List<Asset> assets = ExcelUtil.readXls(in);
                    for(Asset asset : assets) {
                        asset.insert();
                    }
                    return R.ok("导入成功");
                }
                if(".xlsx".equals(suffix)) {
                    List<Asset> assets = ExcelUtil.readXlsx(in);
                    for(Asset asset : assets) {
                        asset.insert();
                    }
                    return R.ok("导入成功");
                }
                return R.error("文件格式错误");
            } catch (IOException e) {
                e.printStackTrace();
                return R.error("导入失败");
            }
        }
        return R.error("文件上传失败");
    }

    /**
     * 按厂家统计数量
     * @return
     */
    @ApiOperation("按厂家统计数量")
    @RequestMapping("/getByVender")
    @ResponseBody
    public R getByVender() {
        List<ByVender> byVender = assetService.getByVender();
        return R.ok(byVender);
    }

    @ApiOperation("按月份统计")
    @RequestMapping("/getByMonth")
    @ResponseBody
    public R getByMonth() {
        List<ByMonth> byMonth = assetService.getByMonth();
        return R.ok(byMonth);
    }
}

