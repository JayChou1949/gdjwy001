package com.hirisun.cloud.platform.document.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hirisun.cloud.model.file.FileSystemVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 文档
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
@TableName("T_DEV_DOC")
@ApiModel(value="DevDoc对象", description="文档")
public class DevDoc implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "文档标题")
    @TableField("TITLE")
    private String title;

    @ApiModelProperty(value = "图片")
    @TableField("IMAGE")
    private String image;

    @ApiModelProperty(value = "排序")
    @TableField("SORT_NUM")
    private Long sortNum;

    @TableField("UPDATE_TIME")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除")
    @TableField("STATUS")
    private Long status;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "文档内容")
    @TableField("CONTENT")
    private String content;

    @ApiModelProperty(value = "一级分类",required = true)
    @TableField("FIRST_CLASS")
    private String firstClass;

    @ApiModelProperty(value = "二级分类")
    @TableField("SECOND_CLASS")
    private String secondClass;

    @ApiModelProperty(value = "简介")
    @TableField("INTRO")
    private String intro;

    //前台需要
    @ApiModelProperty(value = "一级分类名")
    @TableField(exist = false)
    private String firstClassName;

    @ApiModelProperty(value = "二级分类名")
    @TableField(exist = false)
    private String secondClassName;

    @ApiModelProperty(value = "文件id")
    @TableField("FILE_ID")
    private String fileId;

    @TableField(exist = false)
    private String fileUrl;


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(String firstClass) {
        this.firstClass = firstClass;
    }

    public String getSecondClass() {
        return secondClass;
    }

    public void setSecondClass(String secondClass) {
        this.secondClass = secondClass;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getFirstClassName() {
        return firstClassName;
    }

    public void setFirstClassName(String firstClassName) {
        this.firstClassName = firstClassName;
    }

    public String getSecondClassName() {
        return secondClassName;
    }

    public void setSecondClassName(String secondClassName) {
        this.secondClassName = secondClassName;
    }

    @Override
    public String toString() {
        return "DevDoc{" +
        "id=" + id +
        ", title=" + title +
        ", image=" + image +
        ", sortNum=" + sortNum +
        ", updateTime=" + updateTime +
        ", status=" + status +
        ", creator=" + creator +
        ", content=" + content +
        ", firstClass=" + firstClass +
        ", secondClass=" + secondClass +
        ", intro=" + intro +
        "}";
    }
}
