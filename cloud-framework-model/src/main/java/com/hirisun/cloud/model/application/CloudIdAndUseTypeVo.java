package com.hirisun.cloud.model.application;

/**
 * @author junglefisher
 * @date 2020/3/4 15:36
 */
public class CloudIdAndUseTypeVo {

    /**
     * 桌面云ID
     */
    private String id;
    /**
     * 桌面云申请类型
     */
    private String useType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }
}
