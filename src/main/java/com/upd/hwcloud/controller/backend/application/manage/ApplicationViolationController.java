package com.upd.hwcloud.controller.backend.application.manage;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationViolation;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.application.manage.ApplicationViolationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * @author xqp
 * @since 2020-06-30
 */
@Api(description = "违规")
@RestController
@RequestMapping("/applicationViolation")
public class ApplicationViolationController {

    @Autowired
    private ApplicationViolationService applicationViolationService;

    @ApiOperation(value = "违规/严重违规")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaOrPolice",value = "地市/警种",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "creatorName",value = "姓名",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "orgName",value = "所属单位",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "violationTime",value = "违规时间",paramType = "query",dataType = "String"),
    })
    @RequestMapping(value = "/getAppDetails",method = RequestMethod.GET)
    public R getAppDetails(@RequestParam(value = "areaOrPolice",required = false) String areaOrPolice,
                           @RequestParam(value = "creatorName",required = false) String creatorName,
                           @RequestParam(value = "orgName",required = false) String orgName,
                           @RequestParam(value = "violationTime",required = false) String violationTime,
                           @RequestParam(defaultValue = "1") Long current,
                           @RequestParam(defaultValue = "10") Long size){
        IPage<ApplicationViolation> page = new Page<>(current,size);
        try {
            page = applicationViolationService.getViolationUser(page,areaOrPolice,creatorName,orgName,violationTime);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(page);
    }

}

