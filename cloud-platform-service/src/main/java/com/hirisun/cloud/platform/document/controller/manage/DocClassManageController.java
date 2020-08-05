package com.hirisun.cloud.platform.document.controller.manage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.util.TreeUtils;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.document.bean.DevDoc;
import com.hirisun.cloud.platform.document.bean.DevDocClass;
import com.hirisun.cloud.platform.document.service.DevDocClassService;
import com.hirisun.cloud.platform.document.service.DevDocService;
import com.hirisun.cloud.platform.information.bean.News;
import com.hirisun.cloud.platform.information.util.NewsParamUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文档 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-04
 */
@RestController
@RequestMapping("/platform/docClassManage")
@Api(tags = "文档分类管理")
public class DocClassManageController {

    @Autowired
    private DevDocClassService devDocClassService;



    /**
     * 分页获取文档列表
     */
    @ApiOperation("分页获取文档列表")
    @GetMapping("/page")
    public QueryResponseResult<DevDocClass> page(@LoginUser  UserVO user,
                                            @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                            @ApiParam("每页大小") @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                            @ApiParam("分类名称") @RequestParam(required = false) String name,
                                            @ApiParam(value = "分类类型 0全部分类 1一级分类 2二级分类",required = true) @RequestParam String type
                                            ) {
        Page<DevDocClass> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Map param=new HashMap();
        param.put("type",type);
        param.put("name", name);
        page = devDocClassService.getPage(page,user,param);
        return QueryResponseResult.success(page);
    }

    @ApiOperation("创建/更新文档分类")
    @PostMapping("/saveOrUpdate")
    public QueryResponseResult<DevDocClass> saveOrUpdate(@LoginUser UserVO user, @ModelAttribute DevDocClass devDocClass) {

        devDocClass.setCreator(user.getIdCard());
        devDocClass.setUpdateTime(new Date());
        if (StringUtils.isEmpty(devDocClass.getId())) {
            devDocClassService.save(devDocClass);
        }else{
            devDocClassService.updateById(devDocClass);
        }
        return QueryResponseResult.success(devDocClass);
    }
    @ApiOperation("删除文档分类")
    @PostMapping("/delete")
    public QueryResponseResult create(@ApiParam(value = "文档分类id",required = true) @RequestParam Integer id) {

        devDocClassService.removeById(id);
        return QueryResponseResult.success(null);
    }

}

