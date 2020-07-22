package com.hirisun.cloud.platform.document.controller;


import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.platform.document.bean.UserDoc;
import com.hirisun.cloud.platform.document.service.UserDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Api(description = "用户文档")
public class UserDocController {

    @Autowired
    private UserDocService userDocService;

    /**
     * 根据适用业务获取用户文档
     */
    @ApiOperation("根据适用业务获取用户文档")
    @GetMapping("/listUserDoc/{domainValue}")
    public QueryResponseResult<UserDoc> listUserDoc(@PathVariable("domainValue") String domainValue) {
        LinkedList<UserDoc> list = userDocService.listByDomain(domainValue);
        return QueryResponseResult.success(list);
    }

}

