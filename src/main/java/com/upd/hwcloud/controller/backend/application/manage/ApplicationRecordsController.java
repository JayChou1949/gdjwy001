package com.upd.hwcloud.controller.backend.application.manage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationRecords;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.application.manage.IApplicationRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lqm
 * @since 2020-06-29
 */
@Api(description = "配额设置")
@RestController
@RequestMapping("/applicationRecords")
public class ApplicationRecordsController {

    @Autowired
    private IApplicationRecordsService applicationRecordsService;
    @ApiOperation(value = "查询配额修改记录")
    @RequestMapping(value = "/v1/queryQuotaRecords",method = RequestMethod.GET)
    public R getApplicationRecords(@RequestParam(value = "id") String id){
        Page<ApplicationRecords> page=new Page<>();
        page.setCurrent(1);
        page.setSize(20);
        Page<ApplicationRecords> applicationRecords = applicationRecordsService.getApplicationRecords(page, id);
        return R.ok(applicationRecords);
    }


}

