package com.upd.hwcloud.controller.backend.dm;


import com.upd.hwcloud.bean.entity.dm.CloudCategory;
import com.upd.hwcloud.bean.response.R;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yyc
 * @since 2019-08-13
 */
@Controller
@RequestMapping("/cloudCategory/")
public class CloudCategoryController {

    @RequestMapping("add")
    @ResponseBody
    public R add(CloudCategory cloudCategory){
        if(cloudCategory!=null){
            cloudCategory.insertOrUpdate();
            return R.ok(cloudCategory);
        }
        return R.error();
    }

}

