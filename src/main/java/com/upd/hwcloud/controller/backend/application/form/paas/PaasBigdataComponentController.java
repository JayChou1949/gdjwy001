package com.upd.hwcloud.controller.backend.application.form.paas;


import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.application.IPaasBigdataComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 大数据组件申请信息 前端控制器
 * </p>
 *
 * @author junglefisher
 * @since 2020-05-08
 */
@Controller
@RequestMapping("/paasBigdataComponent")
public class PaasBigdataComponentController {

    @Autowired
    private IPaasBigdataComponentService iPaasBigdataComponentService;

    /**
     * 将大数据组件-旧的数据迁移到新表中
     */
    @RequestMapping(value = "/moveData",method = RequestMethod.GET)
    @ResponseBody
    public R moveData(){
        try {
            iPaasBigdataComponentService.moveData();
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

}

