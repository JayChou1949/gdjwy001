package com.upd.hwcloud.common.filter;

import com.upd.hwcloud.bean.contains.SessionName;
import com.upd.hwcloud.bean.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by zwb on 2018/12/9.
 */
public class ShiroAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute(SessionName.USER);
            if (null!=user){
                try {
                    org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
                    if (!subject.isAuthenticated()) {
                        // 身份验证
                        subject.login(new UsernamePasswordToken(user.getIdcard(), user.getName()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
