package com.hirisun.cloud.system.controller.manage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.api.system.SystemApi;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.system.bean.SysLog;
import com.hirisun.cloud.system.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-29
 */
@Api(tags = "系统日志管理")
@RestController
@RequestMapping("/system/logManage")
public class SysLogManageController {

    @Autowired
    private SysLogService sysLogService;

    @ApiOperation("系统日志列表")
    @GetMapping("/page")
    public QueryResponseResult<SysLog> list(
            @LoginUser UserVO user,
            @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(required = false,defaultValue = "20") Integer pageSize,
            @ApiParam("用户身份证") @RequestParam(required = false) String userId,
            @ApiParam("开始时间") @RequestParam(required = false) String startDate,
            @ApiParam("结束时间") @RequestParam(required = false) String endDate){

        /**
         * 如果不是管理员身份，则只能查询自己的操作日志
         */
        if(!user.getType().equals(UserVO.USER_TYPE_MANAGER)){
            userId = user.getIdCard();
        }
        Map map = new HashMap<>();
        map.put("userId", userId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Page<SysLog> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page=sysLogService.getPage(page,map);
        return QueryResponseResult.success(page);
    }

    /**
     * 保存系统日志
     * @param creator 用户身份证
     * @param remark 备注
     * @param path 功能位置
     * @ip 本机ip
     */
//    @Override
    @ApiIgnore
    @ApiOperation("保存系统日志")
    @GetMapping("/saveLog")
    public boolean saveLog(@RequestParam("creator") String creator,@RequestParam("remark")String remark,@RequestParam("path")String path,@RequestParam("ip")String ip) {
        SysLog sysLog = new SysLog();
        sysLog.setCreatorId(creator);
        sysLog.setCreateTime(new Date());
        sysLog.setRemark(remark);
        sysLog.setIp(ip);
        sysLog.setPath(path);
        return sysLogService.save(sysLog);

    }
}

