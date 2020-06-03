package com.upd.hwcloud.controller.backend.css;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.document.DocumentClass;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.document.IDocumentClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 文档分类表 前端控制器
 * </p>
 *
 * @author huru
 * @since 2018-11-09
 */
@Api(description = "文档类型管理")
@Controller
@RequestMapping("/documentClass")
public class DocumentClassController {

    @Autowired
    IDocumentClassService iDocumentClassService;

    @ApiOperation("文档类型列表")
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public R page(@LoginUser User user, Integer pageNum, Integer pageSize,@RequestParam(required = false) Integer type,
                  @RequestParam(required = false) String name) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        Page<DocumentClass> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = iDocumentClassService.getPage(page,user,type,name);
        return R.ok(page);
    }

    @ApiOperation("新增文档类型")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public R save(@LoginUser User user, @RequestBody DocumentClass documentClass) {
        documentClass.setId(null);
        documentClass.setCreator(user.getIdcard());
        iDocumentClassService.save(documentClass);
        return R.ok();
    }

    @ApiOperation("删除文档类型")
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public R del(@PathVariable String id) {
        iDocumentClassService.removeById(id);
        return R.ok();
    }

    @ApiOperation("修改文档类型")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public R update(@RequestBody DocumentClass documentClass) {
        iDocumentClassService.updateById(documentClass);
        return R.ok();
    }

    @ApiOperation("文档类型详情")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public R info(@PathVariable String id) {
        DocumentClass documentClass = iDocumentClassService.getById(id);
        return R.ok(documentClass);
    }

    /**
     * 根据类型获取文档分类
     * @param type 1 一级分类 2 二级分类
     * @return
     */
    @ApiOperation("根据类型获取文档分类")
    @RequestMapping(value = "/getByType",method = RequestMethod.GET)
    @ResponseBody
    public R getByType(Integer type) {
        List<DocumentClass> documentClasses = iDocumentClassService.list(new QueryWrapper<DocumentClass>().eq("TYPE", type));
        return R.ok(documentClasses);
    }

    /**
     * 根据一级分类id查询二级分类
     * @param parentId
     * @return
     */
    @ApiOperation("根据一级分类id查询二级分类")
    @RequestMapping(value = "/getSons",method = RequestMethod.GET)
    @ResponseBody
    public R getSonClass(String parentId) {
        List<DocumentClass> documentClasses = iDocumentClassService.list(new QueryWrapper<DocumentClass>().eq("PARENT_ID", parentId));
        return R.ok(documentClasses);
    }
}

