package com.upd.hwcloud.controller.hotfix;

import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IIaasService;
import com.upd.hwcloud.service.IPaasService;
import com.upd.hwcloud.service.ISaasService;

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

    @Autowired
    private IPaasService paasService;

    @Autowired
    private ISaasService saasService;

    @Autowired
    private IIaasService iaasService;

    @RequestMapping(value = "/files",method = RequestMethod.GET)
    public R files(){

        filesService.hotfix();

        return R.ok();
    }

    @RequestMapping(value = "/paas",method = RequestMethod.GET)
    public R paas(){

        paasService.hotfix();
        return R.ok();

    }

    @RequestMapping(value = "/saas",method = RequestMethod.GET)
    public R saas(){
        saasService.hotfix();
        return R.ok();
    }

    @RequestMapping(value = "/iaas",method = RequestMethod.GET)
    public R iaas(){
        iaasService.hotfix();
        return R.ok();
    }

}
