package com.upd.hwcloud.controller.backend.css;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.Log;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.document.Document;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.IpUtil;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IOperateRecordService;
import com.upd.hwcloud.service.document.IDocumentClassService;
import com.upd.hwcloud.service.document.IDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 文档 前端控制器
 * </p>
 *
 * @author huru
 * @since 2018-11-09
 */
@Api(description = "文档管理接口")
@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    IDocumentService iDocumentService;
    @Autowired
    IOperateRecordService iOperateRecordService;
    @Autowired
    IDocumentClassService iDocumentClassService;
    @Autowired
    IFilesService iFilesService;

    @ApiOperation("新增文档")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public R create(@LoginUser User user, @RequestBody Document document) {
        document = iDocumentService.create(user, document);
        return R.ok(document.getId());
    }

    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    @ApiOperation("上下线操作")
    @RequestMapping(value = "/publish/{id}")
    @ResponseBody
    public R publish(@LoginUser User user, @PathVariable String id, Integer result,
                     @RequestParam(required = false) String remark) {
        if (result == null) {
            return R.error();
        }
        iDocumentService.publish(user, id, result, remark);
        return R.ok();
    }

    /**
     * 分页
     * @param status {@link com.upd.hwcloud.bean.contains.ReviewStatus}
     */
    @ApiOperation("文档列表")
    @RequestMapping(value = "/page")
    @ResponseBody
    public R page(@LoginUser User user, Integer pageNum, Integer pageSize, Integer status,
                  @RequestParam(required = false) String name,@RequestParam(required = false) String firstClass,
                  @RequestParam(required = false) String secondClass) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        Page<Document> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = iDocumentService.getPage(page,user,false,status,name,firstClass,secondClass);
        return R.ok(page);
    }

    @ApiOperation("删除文档")
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public R delete(@LoginUser User user, @PathVariable String id) {
        Document document = iDocumentService.getById(id);
        document.setStatus(ReviewStatus.DELETE.getCode());
        iDocumentService.updateById(document);
        new Log(user.getIdcard(),"文档id："+id,"删除文档", IpUtil.getIp()).insert();
        return R.ok();
    }

    @ApiOperation("文档详情")
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public R detail(@LoginUser User user, @PathVariable String id) {
        Document document = iDocumentService.getById(id);
        List<Files> filesList = iFilesService.getFilesByRefId(document.getId());
        document.setFilesList(filesList);
        return R.ok(document);
    }

    @ApiOperation("编辑文档")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public R edit(@LoginUser User user, @RequestBody Document document) {
        iDocumentService.edit(user, document);
        return R.ok();
    }
}

