package com.hirisun.cloud.platform.document.controller;


import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.platform.document.bean.UserDoc;
import com.hirisun.cloud.platform.document.service.UserDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 用户操作文档管理 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/api/userDoc")
@Api(tags = "用户文档")
public class UserDocController {

    @Autowired
    private UserDocService userDocService;

    /**
     * 根据适用业务获取用户文档
     */
    @ApiOperation("根据适用业务获取用户文档")
    @GetMapping("/listUserDocByDomain")
    public QueryResponseResult<UserDoc> listUserDoc(@ApiParam(value = "文档类型：用户指南",required = true) @RequestParam String domainValue) {
        LinkedList<UserDoc> list = userDocService.listByDomain(domainValue);
        return QueryResponseResult.success(list);
    }

}

