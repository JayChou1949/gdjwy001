package com.upd.hwcloud.controller.portal.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.document.Document;
import com.upd.hwcloud.bean.entity.document.DocumentClass;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.document.IDocumentClassService;
import com.upd.hwcloud.service.document.IDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wuc
 * @date 2018/11/20
 */
@Api(description = "门户网站文档接口")
@RestController
@RequestMapping("/api/document")
public class ApiDocumentController {

    @Autowired
    private IDocumentClassService documentClassService;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private IFilesService filesService;

    /**
     * 分类树
     */
    @ApiOperation("获取文档分类")
    @RequestMapping("/typeTree")
    public R typeTree() {
        LambdaQueryWrapper<DocumentClass> qw = new LambdaQueryWrapper<DocumentClass>()
                .orderByAsc(DocumentClass::getSortNum)
                .orderByDesc(DocumentClass::getModifiedTime);
        List<DocumentClass> firstLevel = documentClassService.list(qw.clone().eq(DocumentClass::getType, 1)); // 一级分类
        firstLevel.forEach(parent -> {
            List<DocumentClass> list = documentClassService.list(
                    qw.clone().eq(DocumentClass::getType, 2).eq(DocumentClass::getParentId, parent.getId())
            );
            parent.setSubType(list); // 一级分类对应的二级分类
        });
        return R.ok(firstLevel);
    }

    /**
     * 分页获取文档列表
     */
    @ApiOperation("分页获取文档列表")
    @RequestMapping("/page")
    public R docList(@LoginUser User user, Integer pageNum, Integer pageSize,
                     @RequestParam(required = false) String name, @RequestParam(required = false) String firstClass,
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
        page = documentService.getPage(page, user, true, ReviewStatus.ONLINE.getCode().intValue() ,name,firstClass,secondClass);
        List<Document> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            for (Document document : records) {
                List<Files> filesList = this.findDocList(document.getId());
                if(filesList != null) {
                    document.setFilesList(filesList);
                }
            }
        }
        return R.ok(page);
    }

    @ApiOperation("文档详情")
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public R detail(@PathVariable String id) {
        Document document = documentService.getById(id);
        List<Files> filesList = this.findDocList(document.getId());
        if(filesList != null) {
            document.setFilesList(filesList);
        }
        return R.ok(document);
    }

    private List<Files> findDocList(String docId){
        List<Files> filesList = filesService.getFilesByRefId(docId);
        return filesList;
    }

}
