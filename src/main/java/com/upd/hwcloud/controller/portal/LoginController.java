package com.upd.hwcloud.controller.portal;


import com.dragonsoft.dcuc.client.login.DcucLoginHandler;
import com.dragonsoft.dcuc.client.logout.DcucLogoutHandler;
import com.upd.hwcloud.bean.contains.SessionName;
import com.upd.hwcloud.bean.entity.Log;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.common.utils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * <p>
 * 登录 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2018-10-12
 */
@Api(description = "登录/退出")
@Controller
public class LoginController {

    @ApiOperation("退出")
    @RequestMapping(value = "/api/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public void exit(HttpServletRequest request, HttpServletResponse response) {

        String redirectUrl = request.getParameter("redirectUrl");
        //单点登录退出
        Subject subject = SecurityUtils.getSubject();
        HttpSession session =  request.getSession();
        if (session != null) {
            User user = (User) session.getAttribute(SessionName.USER);
            if (user != null) {
                new Log(user.getIdcard(),"退出登录","退出登录", IpUtil.getRealIP(request)).insert();
            }
        }
        subject.logout();
        DcucLogoutHandler.logout(request, response, redirectUrl);
    }

    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public void login(HttpServletRequest request, HttpServletResponse response) {
        //登录后重定向的地址,必须包含ip、Port和上下文的完整路径（如http://127.0.0.1:8080/ssotest/）
        String redirectUrl = request.getParameter("redirectUrl");
        //单点登录
        DcucLoginHandler.login(request, response, redirectUrl);
    }

}

