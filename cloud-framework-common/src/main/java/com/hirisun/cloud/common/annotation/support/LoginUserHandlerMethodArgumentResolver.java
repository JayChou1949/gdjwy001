package com.hirisun.cloud.common.annotation.support;

import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.util.SpringContextHolder;
import com.hirisun.cloud.model.user.UserVO;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxiaoxing 2020-07-27
 * 用户登录检查处理
 */
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * 前端访问请求头设置token
     */
    public static final String LOGIN_TOKEN_KEY = "cloud-hirisun-token";

    public static final String USER = "user";


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserVO.class)&&parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer container,
                                  NativeWebRequest webRequest, WebDataBinderFactory factory) throws Exception {
        //TODO token写入redis
        //String token = webRequest.getHeader(LOGIN_TOKEN_KEY);
        if(methodParameter.getParameterType() != null
                && methodParameter.hasParameterAnnotation(LoginUser.class)){
            // 从缓存中取出 User
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            Object currentUser = request.getSession().getAttribute(USER);

            String profile = SpringContextHolder.getActiveProfile();
            if ("dev".equals(profile) && null == currentUser) {
                UserVO devUser=new UserVO();
                devUser.setName("唐彪");
                devUser.setIdCard("410184198209096919");
                devUser.setOrgId("7B038CBFE0BFC09CE0530A282E21C09C");
                devUser.setOrgName("北京海联捷讯科技股份有限公司");
                devUser.setPostType("科长");
                devUser.setType(UserVO.USER_TYPE_MANAGER);
                devUser.setNotifyType(UserVO.NOTIFY_TYPE_WX);
                request.getSession().setAttribute(USER,devUser);
                return devUser;
            }
            return currentUser;
        }
        return null;
    }
}
