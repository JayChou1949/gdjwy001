package com.upd.hwcloud.common.config;

import com.upd.hwcloud.bean.contains.NotifyType;
import com.upd.hwcloud.bean.contains.SessionName;
import com.upd.hwcloud.bean.contains.UserType;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.common.utils.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

public class CurrentUserArgumentResolver implements WebArgumentResolver {

    @Autowired
    private ApplicationContext application;

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) {
        if(methodParameter.getParameterType() != null
                && methodParameter.getParameterType().equals(User.class)
                && methodParameter.hasParameterAnnotation(LoginUser.class)){
            // 从 session 中取出 User
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            Object currentUser = request.getSession().getAttribute(SessionName.USER);

            String profile = EnvironmentUtils.getCurrentProfile(application);
            if (EnvironmentUtils.DEV.equals(profile) && null == currentUser) {
                currentUser = new User().setName("唐彪").setIdcard("410184198209096919").setOrgId("7B038CBFE0BFC09CE0530A282E21C09C")
                        .setOrgName("北京海联捷讯科技股份有限公司").setPostType("科长").setType(UserType.MANAGER.getCode()).setNotifyType(NotifyType.WX.getCode());
                request.getSession().setAttribute(SessionName.USER,currentUser);
                return currentUser;
            }
            return currentUser;
        }
        return UNRESOLVED;
    }

}
