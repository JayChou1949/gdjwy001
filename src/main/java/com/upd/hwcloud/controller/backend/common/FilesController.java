package com.upd.hwcloud.controller.backend.common;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  文件上传下载 前端控制器
 * </p>
 *
 * @author huru
 * @since 2018-10-25
 */
@Api(description = "文件管理接口")
@RestController
@RequestMapping("/files")
public class FilesController {

    @Autowired
    IFilesService iFilesService;

    @ApiOperation("上传文件")
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public R upload(MultipartRequest request, String folder) throws IOException {
        List<MultipartFile> files = request.getFiles("file");
        if(files != null && files.size() > 0){
            List<Files> list = new ArrayList<>();
            for (MultipartFile file:files){
                Files fileSave = iFilesService.upload(file, folder);
                list.add(fileSave);
            }
            return R.ok(list);
        }else {
            return R.error("未上传成功");
        }
    }

    @ApiOperation("下载文件")
    @RequestMapping(value = "/download/{fileId}",method = RequestMethod.GET)
    public void download(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        if(fileId != null && !"".equals(fileId)) {
            iFilesService.download(fileId,response);
        }
    }

    @ApiOperation("删除文件")
    @RequestMapping("/del/{id}")
    public R delete(@PathVariable String id) {
        iFilesService.removeById(id);
        return R.ok();
    }

    @ApiOperation("删除疫情服务附件")
    @RequestMapping("/del/ncov/attachment/{value}")
    public R deleteByTitle(@PathVariable String value) {

        Files files = iFilesService.getOne(new QueryWrapper<Files>().lambda().eq(Files::getTitle,value));
        if(files!=null){
            String file = files.getPath()+"/"+files.getName();
            FileUtils.deleteQuietly(new File(file));
            iFilesService.remove(new QueryWrapper<Files>().lambda().eq(Files::getTitle,value));
        }
        return R.ok();
    }



    @ApiOperation("更新文件")
    @RequestMapping("/update")
    public R update(MultipartRequest request,String folder,String id) throws IOException {
        List<MultipartFile> files = request.getFiles("file");
        if(files != null && files.size() > 0){
            List<Files> list = new ArrayList<>();
            for (MultipartFile file:files){
                Files fileSave = iFilesService.update(file, folder,id);
                list.add(fileSave);
            }
            return R.ok(list);
        }else {
            return R.error("未修改成功");
        }
    }

}

