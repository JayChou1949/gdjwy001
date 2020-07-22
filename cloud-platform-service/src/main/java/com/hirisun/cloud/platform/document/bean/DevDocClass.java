package com.hirisun.cloud.platform.document.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 文档分类表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
@TableName("T_DEV_DOC_CLASS")
@ApiModel(value="DevDocClass对象", description="文档分类表")
public class DevDocClass implements Serializable {

    //文档分类状态 0审核 1待上线 2上线 3驳回 4删除
    public static final Long STATUS_AUDIT=0L;
    public static final Long STATUS_WAIT_ONLINE=1L;
    public static final Long STATUS_ONLINE=2L;
    public static final Long STATUS_REJECT=3L;
    public static final Long STATUS_DELETE=4L;

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "分类名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "排序")
    @TableField("SORT_NUM")
    private Long sortNum;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "类别 1一级分类 2二级分类")
    @TableField("TYPE")
    private Long type;

    @ApiModelProperty(value = "分类图片")
    @TableField("IMAGE")
    private String image;

    @ApiModelProperty(value = "上级id")
    @TableField("PARENT_ID")
    private String parentId;

    @TableField(exist = false)
    private List<DevDocClass> children;


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

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<DevDocClass> getChildren() {
        return children;
    }

    public void setChildren(List<DevDocClass> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "DevDocClass{" +
        "id=" + id +
        ", name=" + name +
        ", sortNum=" + sortNum +
        ", updateTime=" + updateTime +
        ", creator=" + creator +
        ", remark=" + remark +
        ", type=" + type +
        ", image=" + image +
        ", parentId=" + parentId +
        "}";
    }
}
