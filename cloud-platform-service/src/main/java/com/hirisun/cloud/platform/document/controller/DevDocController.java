package com.hirisun.cloud.platform.document.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.User;
import com.hirisun.cloud.platform.document.bean.DevDoc;
import com.hirisun.cloud.platform.document.bean.DevDocClass;
import com.hirisun.cloud.platform.document.service.DevDocClassService;
import com.hirisun.cloud.platform.document.service.DevDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文档 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/api/devDoc")
@Api(description = "开发文档")
public class DevDocController {

    @Autowired
    private DevDocClassService devDocClassService;

    @Autowired
    private DevDocService devDocService;

    /**
     * 分页获取文档列表
     */
    @ApiOperation("分页获取文档列表")
    @GetMapping("/list")
    public QueryResponseResult<DevDoc> list(User user,
                                            @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                            @ApiParam("每页大小") @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                            @ApiParam("文档名称/内容") @RequestParam(required = false) String name,
                                            @ApiParam("一级分类") @RequestParam(required = false) String firstClass,
                                            @ApiParam("二级分类") @RequestParam(required = false) String secondClass) {
        Page<DevDoc> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Map param=new HashMap();
        param.put("isFront","1");
        param.put("status",DevDocClass.STATUS_ONLINE);
        param.put("name", name);
        param.put("firstClass", firstClass);
        param.put("secondClass", secondClass);
        param.put("user", user);
        page = devDocService.getPage(page,param);
        List<DevDoc> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            for (DevDoc document : records) {//附件
//                List<Files> filesList = this.findDocList(document.getId());
//                if(filesList != null) {
//                    document.setFilesList(filesList);
//                }
            }
        }
        return QueryResponseResult.success(page);
    }

    @ApiOperation("文档详情")
    @GetMapping("/{id}")
    public QueryResponseResult<DevDoc> detail(@PathVariable String id) {
        DevDoc document = devDocService.getById(id);

        //TODO 与文件绑定
//        List<Files> filesList = this.findDocList(document.getId());
//        if(filesList != null) {
//            document.setFilesList(filesList);
//        }
        return QueryResponseResult.success(document);
    }

    /**
     * 分类树,根据父字id组装成树形分类
     */
    @ApiOperation("获取文档分类")
    @GetMapping("/docTypeTree")
    public QueryResponseResult<DevDocClass> typeTree() {
        List<DevDocClass> list = devDocClassService.listWithTree();
        return QueryResponseResult.success(list);
    }

}

