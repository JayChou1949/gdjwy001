package com.hirisun.cloud.common.util;

import com.hirisun.cloud.model.user.UserVO;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wuxiaoxing 2020-07-28
 *
 * 处理传参，根据传入参数重组sql查询所需字段
 */
public class PermissionUtil {

    /**
     * 资讯类型-省厅 1
     */
    public static final Integer NEWS_TYPE_PROVINCE=1;
    /**
     * 资讯类型-地市 2
     */
    public static final Integer NEWS_TYPE_AREA=2;
    /**
     * 资讯类型-警种 3
     */
    public static final Integer NEWS_TYPE_POLICE=3;
    /**
     * 资讯类型-国家专项 4
     */
    public static final Integer NEWS_TYPE_NATIONAL_PROJECT=4;

    /**
     * 检测用户是否越权操作资讯内容的权限
     * @param provincial 资讯类别
     * @param belong 类别所属
     * @return boolean 如果越权，返回true，否则返回false
     */
    public static boolean checkUserInfomationPermission(UserVO user, Integer provincial, String belong) {
        /**
         * 1.如果是管理员，直接返回
         * 2.如果资讯是省厅，用户类型必须是省厅管理员
         * 3.如果资讯根绝地市分类，用户必须所属该地市
         * 4.如果资讯是警种分类，用户必须所属该警种
         * 5.如果资讯是国家专项分类，用户必须所属该国家专项
         */
        long userType=user.getType();
        if(userType == UserVO.USER_TYPE_MANAGER){
            return false;
        }
        if (provincial.equals(NEWS_TYPE_PROVINCE)
                && !user.getType().equals(UserVO.USER_TYPE_PROVINCIAL_MANAGER)) {
            return true;
        }
        if (provincial.equals(NEWS_TYPE_AREA)
                && (StringUtils.isEmpty(belong) || !belong.equals(user.getTenantArea()))) {
            return true;
        }
        if (provincial.equals(NEWS_TYPE_POLICE)
                && (StringUtils.isEmpty(belong) || !belong.equals(user.getTenantPoliceCategory()))) {
            return true;
        }
        if (provincial.equals(NEWS_TYPE_NATIONAL_PROJECT)
                && (StringUtils.isEmpty(belong) || !belong.equals(user.getNationalProject()))) {
            return true;
        }
        return false;
    }

    /**
     * 根据用户类型判断是否允许操作资讯模块
     * @param type 用户类别
     * @return boolean 如果有权限，返回true，否则返回false
     */
    public static boolean infomationPermission(Long type) {
        /**
         * 系统管理员、省厅管理员、租户管理员允许操作资讯模块
         */
        if(type.equals(UserVO.USER_TYPE_MANAGER)
                || type.equals(UserVO.USER_TYPE_PROVINCIAL_MANAGER)
                || type.equals(UserVO.USER_TYPE_TENANT_MANAGER)){
            return  true;
        }
        return false;
    }

}
