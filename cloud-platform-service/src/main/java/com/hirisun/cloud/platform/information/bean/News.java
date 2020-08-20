package com.hirisun.cloud.platform.information.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 新闻资讯
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-14
 */
@TableName("T_NEWS")
@ApiModel(value="News对象", description="新闻资讯")
public class News implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * show 状态 0审核
     */
    public static final Long STATUS_AUDIT=0L;
    /**
     * 状态 1待上线
     */
    public static final Long STATUS_WAIT_ONLINE=1L;
    /**
     * 状态 2上线
     */
    public static final Long STATUS_ONLINE=2L;
    /**
     * 状态 3驳回
     */
    public static final Long STATUS_REJECT=3L;
    /**
     * 状态 4删除
     */
    public static final Long STATUS_DELETE=4L;

    /**
     * 置顶状态 0无置顶
     */
    public static final Integer TOP_NO=0;
    /**
     * 置顶状态 1置顶
     */
    public static final Integer TOP_YES=1;


    @TableId(value="ID",type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "新闻标题")
    @TableField("TITLE")
    private String title;

    @ApiModelProperty(value = "url链接")
    @TableField("URL")
    private String url;

    @ApiModelProperty(value = "排序")
    @TableField("SORT_NUM")
    private Long sortNum;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除")
    @TableField("STATUS")
    private Long status;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "是否为外链 0:是 1:否")
    @TableField("LINK")
    private Long link;

    @ApiModelProperty(value = "简介")
    @TableField("INTRO")
    private String intro;

    @ApiModelProperty(value = "是否置顶")
    @TableField("IS_TOP")
    private Integer isTop;

    @ApiModelProperty(value = "新闻时间,格式yyyy-MM-dd")
    @TableField("TIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date time;

    @ApiModelProperty(value = "浏览量")
    @TableField("VIEW_COUNT")
    private Long viewCount;

    @ApiModelProperty(value = "内容")
    @TableField("CONTENT")
    private String content;

    @ApiModelProperty(value = "展示级别:1-省厅首页 2-地市首页 3-警种首页")
    @TableField("PROVINCIAL")
    private Integer provincial;

    @ApiModelProperty(value = "新闻所属警种")
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @ApiModelProperty(value = "新闻所属地市")
    @TableField("AREA")
    private String area;

    @ApiModelProperty(value = "新闻所属专项")
    @TableField("PROJECT")
    private String project;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Long getLink() {
        return link;
    }

    public void setLink(Long link) {
        this.link = link;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getProvincial() {
        return provincial;
    }

    public void setProvincial(Integer provincial) {
        this.provincial = provincial;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "News{" +
        "id=" + id +
        ", title=" + title +
        ", url=" + url +
        ", sortNum=" + sortNum +
        ", updateTime=" + updateTime +
        ", status=" + status +
        ", creator=" + creator +
        ", link=" + link +
        ", intro=" + intro +
        ", isTop=" + isTop +
        ", time=" + time +
        ", viewCount=" + viewCount +
        ", content=" + content +
        ", provincial=" + provincial +
        ", policeCategory=" + policeCategory +
        ", area=" + area +
        ", project=" + project +
        "}";
    }
}
