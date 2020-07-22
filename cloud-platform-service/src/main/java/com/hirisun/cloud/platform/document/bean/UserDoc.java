package com.hirisun.cloud.platform.document.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户操作文档管理
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
@TableName("T_USER_DOC")
@ApiModel(value="UserDoc对象", description="用户操作文档管理")
public class UserDoc implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("ID")
    private String id;

    @ApiModelProperty(value = "文档名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "适用业务")
    @TableField("DOMAIN")
    private String domain;

    @ApiModelProperty(value = "排序")
    @TableField("SORT")
    private Long sort;

    @ApiModelProperty(value = "描述")
    @TableField("DESCRIPTION")
    private String description;

    @ApiModelProperty(value = "状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除")
    @TableField("STATUS")
    private Long status;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATOR")
    private String creator;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField(exist = false)
    private String fileUrl;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UserDoc{" +
        "id=" + id +
        ", name=" + name +
        ", domain=" + domain +
        ", sort=" + sort +
        ", description=" + description +
        ", status=" + status +
        ", creator=" + creator +
        ", updateTime=" + updateTime +
        "}";
    }
}
