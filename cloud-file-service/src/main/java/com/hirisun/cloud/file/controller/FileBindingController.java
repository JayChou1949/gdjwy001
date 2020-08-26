package com.hirisun.cloud.file.controller;


import com.hirisun.cloud.file.service.FileBindingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 * 主表-文件系统绑定表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-25
 */
@Api(tags = "文件服务处理类")
@RequestMapping("/fileBinding")
@RestController
@Slf4j
public class FileBindingController {

    @Autowired
    private FileBindingService fileBindingService;

    @ApiIgnore
    @ApiOperation(value = "保存文件绑定信息")
    @PostMapping(value = "/saveFileBinding")
    public void saveFileBinding(@RequestParam String masterId,String fileId) {
        fileBindingService.saveFileBinding(masterId, fileId);
    }
    @ApiIgnore
    @ApiOperation(value = "保存多个文件绑定信息")
    @PostMapping(value = "/saveByIds")
    public void saveByIds(@RequestParam List<String> ids,String fileId) {
        fileBindingService.saveByIds(ids, fileId);
    }

    @ApiIgnore
    @ApiOperation(value = "删除文件绑定信息")
    @PostMapping(value = "/deleteByIds")
    public void deleteByIds(@RequestParam List<String> ids) {
        fileBindingService.removeByIds(ids);
        //TODO 删除物理文件
    }

}

