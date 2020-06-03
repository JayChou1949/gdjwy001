package com.upd.hwcloud.controller.backend.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.Log;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.ILogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2018-10-17
 */
@Api(description = "日志管理")
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private ILogService logService;

    @ApiOperation("分页查询日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
            @ApiImplicitParam(name="userId", value="用户idCard", paramType="query", dataType="String"),
            @ApiImplicitParam(name="startDate", value="开始日期", required = true, paramType="query", dataType="String"),
            @ApiImplicitParam(name="endDate", value="结束日期", required = true, paramType="query", dataType="String")
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public R getPage(@LoginUser User user,
                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                     @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                     String userId,
                     String startDate, String endDate) {
        if (user.getType()!=100) userId = user.getIdcard();
        IPage<Log> page = logService.getPage(pageNum, pageSize, startDate, endDate,userId);
        return R.ok(page);
    }


}

