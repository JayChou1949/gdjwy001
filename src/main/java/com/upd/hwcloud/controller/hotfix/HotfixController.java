package com.upd.hwcloud.controller.hotfix;

import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IFilesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyc
 * @date 2020/6/4
 */
@RestController
@RequestMapping("/hotfix")
public class HotfixController {

    @Autowired
    private IFilesService filesService;

    @RequestMapping(value = "/files",method = RequestMethod.GET)
    public R files(){

        filesService.hotfix();

        return R.ok();
    }

}
