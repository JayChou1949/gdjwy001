package com.upd.hwcloud.bean.entity.document;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 文档分类表
 * </p>
 *
 * @author huru
 * @since 2018-11-09
 */
@TableName("TB_DOCUMENT_CLASS")
public class DocumentClass extends Model<DocumentClass> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    /**
     * 分类名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 排序
     */
    @TableField("SORT_NUM")
    private Long sortNum;

    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 创建人
     */
    @TableField("CREATOR")
    private String creator;

    /**
     * 分类编号
     */
    @TableField("CLASS_NUM")
    private String classNum;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 类别 1一级分类 2二级分类
     */
    @TableField("TYPE")
    private Long type;

    /**
     * 分类图片
     */
    @TableField("IMAGE")
    private String image;

    /**
     * 上级id
     */
    @TableField("PARENT_ID")
    private String parentId;

    //前台需要
    @TableField(exist = false)
    private String parentName; //上级名称

    @TableField(exist = false)
    private List<DocumentClass> subType; // 子分类

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getId() {
        return id;
    }

    public DocumentClass setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DocumentClass setName(String name) {
        this.name = name;
        return this;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public DocumentClass setSortNum(Long sortNum) {
        this.sortNum = sortNum;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public DocumentClass setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public DocumentClass setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public DocumentClass setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getClassNum() {
        return classNum;
    }

    public DocumentClass setClassNum(String classNum) {
        this.classNum = classNum;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public DocumentClass setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Long getType() {
        return type;
    }

    public DocumentClass setType(Long type) {
        this.type = type;
        return this;
    }

    public String getImage() {
        return image;
    }

    public DocumentClass setImage(String image) {
        this.image = image;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public DocumentClass setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public List<DocumentClass> getSubType() {
        return subType;
    }

    public void setSubType(List<DocumentClass> subType) {
        this.subType = subType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DocumentClass{" +
                "id=" + id +
                ", name=" + name +
                ", sortNum=" + sortNum +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", creator=" + creator +
                ", classNum=" + classNum +
                ", remark=" + remark +
                ", type=" + type +
                ", image=" + image +
                ", parentId=" + parentId +
                "}";
    }
}
