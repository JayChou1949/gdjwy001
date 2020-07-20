package com.hirisun.cloud.platform.information.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 轮播图
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-15
 */
@TableName("T_CAROUSEL")
@ApiModel(value="Carousel对象", description="轮播图")
public class Carousel implements Serializable {

    private static final long serialVersionUID=1L;

    //轮播图状态 0审核 1待上线 2上线 3驳回 4删除
    public static final Long STATUS_AUDIT=0L;
    public static final Long STATUS_WAIT_ONLINE=1L;
    public static final Long STATUS_ONLINE=2L;
    public static final Long STATUS_REJECT=3L;
    public static final Long STATUS_DELETE=4L;


    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "轮播标题")
    @TableField("TITLE")
    private String title;

    @ApiModelProperty(value = "url链接")
    @TableField("URL")
    private String url;

    @ApiModelProperty(value = "图片id")
    @TableField("IMAGE_ID")
    private String imageId;

    @ApiModelProperty(value = "排序")
    @TableField("SORT_NUM")
    private Long sortNum;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATED_TIME")
    private Date updatedTime;

    @ApiModelProperty(value = "状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除")
    @TableField("STATUS")
    private Long status;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATOR")
    private String creator;

    @ApiModelProperty(value = "是否为外链 0:是 1:否")
    @TableField("LINK")
    private Long link;

    @ApiModelProperty(value = "内容")
    @TableField("CONTENT")
    private String content;

    @ApiModelProperty(value = "展示级别:1-省厅首页 2-地市首页 3-警种首页")
    @TableField("PROVINCIAL")
    private Integer provincial;

    @ApiModelProperty(value = "轮播图所属警种")
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @ApiModelProperty(value = "轮播图所属地市")
    @TableField("AREA")
    private String area;

    @ApiModelProperty(value = "轮播图所属专项")
    @TableField("PROJECT")
    private String project;


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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
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
        return "Carousel{" +
        "id=" + id +
        ", title=" + title +
        ", url=" + url +
        ", imageId=" + imageId +
        ", sortNum=" + sortNum +
        ", updatedTime=" + updatedTime +
        ", status=" + status +
        ", creator=" + creator +
        ", link=" + link +
        ", content=" + content +
        ", provincial=" + provincial +
        ", policeCategory=" + policeCategory +
        ", area=" + area +
        ", project=" + project +
        "}";
    }
}
