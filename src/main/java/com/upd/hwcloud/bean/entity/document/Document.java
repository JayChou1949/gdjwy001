package com.upd.hwcloud.bean.entity.document;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.bean.entity.Files;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 文档
 * </p>
 *
 * @author huru
 * @since 2018-11-09
 */
@TableName("TB_DOCUMENT")
public class Document extends Model<Document> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    /**
     * 文档标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 图片
     */
    @TableField("IMAGE")
    private String image;

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
     * 状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除
     */
    @TableField("STATUS")
    private Long status;

    /**
     * 创建人
     */
    @TableField("CREATOR")
    private String creator;

    /**
     * 文档内容
     */
    @TableField("CONTENT")
    private String content;
    /**
     * 简介
     */
    @TableField("INTRO")
    private String intro;

    /**
     * 一级分类
     */
    @TableField("FIRST_CLASS")
    private String firstClass;

    /**
     * 二级分类
     */
    @TableField("SECOND_CLASS")
    private String secondClass;

    //前台需要
    @TableField(exist = false)
    private String firstClassName;
    @TableField(exist = false)
    private String secondClassName;
    @TableField(exist = false)
    private String num; //附件数量
    @TableField(exist = false)
    private List<Files> filesList; //附件列表

    /**
     * 创建人姓名
     */
    @TableField(exist = false)
    private String creatorName;

    /**
     * 文档创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("DOC_CREATE_TIME")
    private Date docCreateTime;


    public String getId() {
        return id;
    }

    public Document setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Document setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Document setImage(String image) {
        this.image = image;
        return this;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public Document setSortNum(Long sortNum) {
        this.sortNum = sortNum;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Document setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public Document setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public Long getStatus() {
        return status;
    }

    public Document setStatus(Long status) {
        this.status = status;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Document setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Document setContent(String content) {
        this.content = content;
        return this;
    }

    public String getFirstClass() {
        return firstClass;
    }

    public Document setFirstClass(String firstClass) {
        this.firstClass = firstClass;
        return this;
    }

    public String getSecondClass() {
        return secondClass;
    }

    public Document setSecondClass(String secondClass) {
        this.secondClass = secondClass;
        return this;
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

    public Date getDocCreateTime() {
        return docCreateTime;
    }

    public Document setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
        return this;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<Files> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<Files> filesList) {
        this.filesList = filesList;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", title=" + title +
                ", image=" + image +
                ", sortNum=" + sortNum +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", status=" + status +
                ", creator=" + creator +
                ", content=" + content +
                ", firstClass=" + firstClass +
                ", secondClass=" + secondClass +
                ", docCreateTime=" + docCreateTime +
                "}";
    }
}
