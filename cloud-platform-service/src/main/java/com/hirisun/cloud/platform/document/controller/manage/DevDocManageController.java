package com.hirisun.cloud.platform.document.controller.manage;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.api.file.FileApi;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.contains.ReviewStatus;
import com.hirisun.cloud.common.util.TreeUtils;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.file.FileSystemVO;
import com.hirisun.cloud.model.ncov.vo.daas.HomePageDataVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.document.bean.DevDoc;
import com.hirisun.cloud.platform.document.bean.DevDocClass;
import com.hirisun.cloud.platform.document.bean.DevDocFile;
import com.hirisun.cloud.platform.document.service.DevDocClassService;
import com.hirisun.cloud.platform.document.service.DevDocFileService;
import com.hirisun.cloud.platform.document.service.DevDocService;
import com.hirisun.cloud.platform.information.bean.Carousel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 文档 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-04
 */
@RestController
@RequestMapping("/platform/devDocManage")
@Api(tags = "开发文档管理")
public class DevDocManageController {


    @Autowired
    private DevDocService devDocService;



    @Autowired
    private DevDocFileService devDocFileService;

    /**
     * 分页获取文档列表
     */
    @ApiOperation("分页获取文档列表")
    @GetMapping("/list")
    public QueryResponseResult<DevDoc> list(@LoginUser  UserVO user,
                                            @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                            @ApiParam("每页大小") @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                            @ApiParam("状态 1待上线 2已上线") @RequestParam(required = false,defaultValue = "1") Integer status,
                                            @ApiParam("关键词") @RequestParam(required = false) String keyword,
                                            @ApiParam("一级分类") @RequestParam(required = false) String firstClass,
                                            @ApiParam("二级分类") @RequestParam(required = false) String secondClass) {
        Page<DevDoc> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Map param=new HashMap();
        param.put("isFront", false);
        param.put("status",status);
        param.put("name", keyword);
        param.put("firstClass", firstClass);
        param.put("secondClass", secondClass);
        page = devDocService.getPage(page,user,param);
        return QueryResponseResult.success(page);
    }

    /**
     * 开发文档详情
     * 1.查询文档详情
     * 2.查询图片
     * 3.查询附件
     */
    @ApiOperation("开发文档详情")
    @GetMapping("/devDocDetail")
    public QueryResponseResult<DevDoc> detail(@ApiParam(value = "开发文档id",required = true) @RequestParam String id) {
        Map map=devDocService.getDetailById(id);
        return QueryResponseResult.success(map);
    }

    @ApiOperation("删除文档")
    @PostMapping(value = "/delete")
    public QueryResponseResult delete(@LoginUser UserVO user, @ApiParam(value = "开发文档id",required = true) @RequestParam String id) {
        devDocService.deleteById(user,id);
        return QueryResponseResult.success(null);
    }

    @ApiOperation("新增或修改文档")
    @PutMapping("/saveOrUpdate")
    public QueryResponseResult<DevDoc> saveOrUpdate(@LoginUser UserVO user, @RequestBody DevDoc devDoc) {
        devDocService.saveOrUpdateDevDoc(user, devDoc);
        return QueryResponseResult.success(null);
    }

    /**
     * 上/下线
     * @param type 1:上线,0:下线
     */
    @ApiOperation("上/下线操作")
    @PostMapping(value = "/publish")
    public QueryResponseResult publish(@LoginUser UserVO user,
                                       @ApiParam(value = "开发文档id",required = true) @RequestParam String id,
                                       @ApiParam(value = "类型 1上线 0下线",required = true) @RequestParam String type) {
        DevDoc devDoc = devDocService.getById(id);
        if(devDoc==null){
            return QueryResponseResult.fail("文档不存在");
        }
        if ("1".equals(type)) {
            devDoc.setStatus(ReviewStatus.ONLINE.getCode());
        }else{
            devDoc.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        }
        devDocService.updateById(devDoc);
        return QueryResponseResult.success(null);
    }

}

