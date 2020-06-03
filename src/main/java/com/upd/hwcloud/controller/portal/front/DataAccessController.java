package com.upd.hwcloud.controller.portal.front;


import com.upd.hwcloud.bean.entity.DataAccess;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.IDataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 数据接入表 前端控制器
 * </p>
 *
 * @author huru
 * @since 2019-03-13
 */
@Controller
@RequestMapping("/api/dataAccess")
public class DataAccessController {

    @Autowired
    private IDataAccessService dataAccessService;

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public R save(@LoginUser User user, @RequestBody DataAccess dataAccess) {
        dataAccess.setCreator(user.getIdcard());
        dataAccessService.saveDataAccess(dataAccess);
        return R.ok();
    }

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    @ResponseBody
    public R get(@PathVariable String id) {
        DataAccess dataAccess = dataAccessService.get(id);
        return R.ok(dataAccess);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public R list(@LoginUser User user) {
        List<DataAccess> dataAccessList = dataAccessService.getList(user);
        return R.ok(dataAccessList);
    }
}

