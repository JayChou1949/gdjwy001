package com.upd.hwcloud.bean.entity.webManage;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.bean.entity.User;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 轮播图
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
@TableName("TB_CAROUSEL")
public class Carousel extends Model<Carousel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    /**
     * 轮播标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * url链接
     */
    @TableField("URL")
    private String url;

    /**
     * 图片id
     */
    @TableField("IMAGE_ID")
    private String imageId;

    /**
     * 排序
     */
    @TableField("SORT_NUM")
    private Long sortNum;

    /**
     * 内容
     */
    @TableField("CONTENT")
    private String content;

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
     * 是否为外链 0:是 1:否
     */
    @TableField("LINK")
    private Long link;

    //前台需要
    @TableField(exist = false)
    private String imageUrl;

    /**
     * 通过nginx获取地址
     */
    @TableField(exist = false)
    private String realUrl;

    /**
     * 展示级别：1-省厅首页,2-地市首页 ,3-警种首页，4-国家专项'
     */
    @TableField("PROVINCIAL")
    private Integer provincial;

    /**
     *新闻所属警种
     */
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    /**
     *新闻所属地市
     */
    @TableField("AREA")
    private String area;

    /**
     * 新闻所属专项
     */
    @TableField("PROJECT")
    private String project;

    @TableField(exist = false)
    private User user;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Carousel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imageId='" + imageId + '\'' +
                ", sortNum=" + sortNum +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", status=" + status +
                ", creator='" + creator + '\'' +
                ", link=" + link +
                ", imageUrl='" + imageUrl + '\'' +
                ", provincial=" + provincial +
                ", policeCategory='" + policeCategory + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
