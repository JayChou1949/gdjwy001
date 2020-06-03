package com.upd.hwcloud.bean.dto;

import com.upd.hwcloud.bean.entity.application.ApplicationInfo;

import java.util.List;

/**
 * 更新申请信息
 * @param <S> 用户申请的服务器信息
 */
public class UpdateApplicationInfo<S> {

    /**
     * 审核类型  inner:部门内审核 kx:科信审核
     */
    private String type;

    /**
     * 审核人 id
     */
    private List<String> userIds;

    private ApplicationInfo<S, Object> info;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public ApplicationInfo<S, Object> getInfo() {
        return info;
    }

    public void setInfo(ApplicationInfo<S, Object> info) {
        this.info = info;
    }

}
