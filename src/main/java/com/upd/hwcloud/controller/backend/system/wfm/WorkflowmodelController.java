package com.upd.hwcloud.controller.backend.system.wfm;


import com.upd.hwcloud.bean.response.R;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwb
 * @since 2019-04-10
 */
@Controller
@RequestMapping("/workflowmodel")
public class WorkflowmodelController {

    @ApiModelProperty("动态增加环节-beta")
    @RequestMapping(value = "/beta/add")
    public R addModel(String preId,String nextId){
        return R.ok();
    }

    @ApiModelProperty("动态增加环节-beta")
    @RequestMapping(value = "/beta/sub")
    public R subModel(String preId,String nextId){
        return R.ok();
    }

}

