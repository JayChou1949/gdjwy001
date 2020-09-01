package com.hirisun.cloud.model.saas.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * SaaS申请原始信息扩展表——可视化建模平台资源信息
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-31
 */
@Data
public class SaasAppExtResourceVO implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;

        /**
     * 申请信息 id
     */
    private String masterId;

        /**
     * 目录中文名称
     */
    private String folderName;

        /**
     * 数据资源中文名称
     */
    private String resourceName;

    private Date createTime;

    private Date modifiedTime;

    /**
     * 数据资源id
     */
    private String resourceId;

    @Override
    public String toString() {
        return "SaasAppExtResource{" +
        "id=" + id +
        ", masterId=" + masterId +
        ", folderName=" + folderName +
        ", resourceName=" + resourceName +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
