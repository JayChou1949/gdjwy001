package com.upd.hwcloud.common.filter;

import com.alibaba.fastjson.JSONObject;
import com.dragonsoft.dcuc.client.validation.DcucTicketValidationFilter;
import com.upd.hwcloud.bean.contains.SessionName;
import com.upd.hwcloud.bean.entity.Log;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.DCUCHttpEngin;
import com.upd.hwcloud.common.utils.EnvironmentUtils;
import com.upd.hwcloud.common.utils.IpUtil;
import com.upd.hwcloud.service.IUserService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * 继承DcucTicketValidationFilter，重写onSuccessfulValidation方法，处理业务系统的初始化逻辑
 * 登录认证成功后会执行此方法
 */
public class LoginInitFilter extends DcucTicketValidationFilter {

    @Override
    protected String onSuccessfulValidation(HttpServletRequest request, HttpServletResponse response, Assertion assertion) {
        //获取登录用户的userId
        String userId = assertion.getPrincipal().getName();
        HttpSession session = request.getSession(false);
        User user = null;
        if (EnvironmentUtils.ONLINE_PROFILES.contains(profile)) {
            user = dcucHttpEngin.getUserInfoById(userId);
            if (user == null || StringUtils.isEmpty(user.getIdcard())) {
                User govUser = dcucHttpEngin.getGovUserInfoById(userId);
                if (govUser == null || StringUtils.isEmpty(govUser.getIdcard())){
                    throw new BaseException("用户信息不存在");
                }else {
                    user = govUser;
                }
            }
            // 保存用户信息到本地数据库
            userService.saveOrUpdateFromRemote(user);
            user = userService.findUserByIdcard(user.getIdcard());
            session.setAttribute(SessionName.USER, user);
            new Log(user.getIdcard(),"","登录", IpUtil.getRealIP(request)).insert();
        } else {
            try {
                String json = FileUtils.readFileToString(new File("C:\\Users\\Administrator\\Desktop\\config_user.txt"), "UTF-8");
                user = JSONObject.parseObject(json, User.class);
                user = userService.findUserByIdcard(user.getIdcard());
                session.setAttribute(SessionName.USER, user);
            } catch (Exception e) {
                user =  new User().setName("唐彪").setIdcard("410184198209096919");
                session.setAttribute(SessionName.USER,user);
            }
            new Log(user.getIdcard(),"","登录", IpUtil.getRealIP(request)).insert();
        }
        /*
        * 登录成功后访问的地址。若不指定（return null），则默认访问被单点登录拦截的地址。
        * 注：1、单页应用一般要重定向到首页。
        * 2、前后端分离的应用可以在步骤六中指定登录后的重定向地址，这里写法如下：
        *    String redirectUrl = request.getParameter("redirectUrl");
        *    return redirectUrl;
        */
        String redirectUrl = request.getParameter("redirectUrl");
        if (StringUtils.isEmpty(redirectUrl)) {
            return nginxName;
        }
        return redirectUrl;
    }

    private DCUCHttpEngin dcucHttpEngin;
    private IUserService userService;
    private String profile;

    private String nginxName;

    /**
     * 注：过滤器中不能通过spring注解的方式注入对象，可重写initBean方式获取。
     */
    @Override
    protected void initBean(final FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(context);
        profile = EnvironmentUtils.getCurrentProfile(app);
        dcucHttpEngin = app.getBean(DCUCHttpEngin.class);
        userService = app.getBean(IUserService.class);
        Resource resource = new ClassPathResource("dcuc-client-config.properties");
        try {
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            nginxName = props.getProperty("dcuc.my.nginxName");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
