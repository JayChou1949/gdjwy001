package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 *     文件
 * </p>
 *
 * @author huru
 * @since 2018-10-25
 */
@TableName("TB_FILES")
public class Files extends Model<Files> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    /**
     * 文件名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 原始名称
     */
    @TableField("ORIGINA_NAME")
    private String originaName;

    /**
     * 存储路径
     */
    @TableField("PATH")
    private String path;

    /**
     * 后缀名
     */
    @TableField("SUFFIX")
    private String suffix;

    /**
     * 下载路径
     */
    @TableField("URL")
    private String url;

    @TableField("REF_ID")
    private String refId;
    @TableField("TITLE")
    private String title;

    /**
     * 真实路径 供Nginx服务器使用
     */
    @TableField("REAL_URL")
    private String realURL;

    public String getId() {
        return id;
    }

    public Files setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Files setName(String name) {
        this.name = name;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginaName() {
        return originaName;
    }

    public Files setOriginaName(String originaName) {
        this.originaName = originaName;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Files setPath(String path) {
        this.path = path;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    public Files setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Files setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getRealURL() {
        return realURL;
    }

    public void setRealURL(String realURL) {
        this.realURL = realURL;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Files{" +
                "id=" + id +
                ", name=" + name +
                ", originaName=" + originaName +
                ", path=" + path +
                ", suffix=" + suffix +
                ", url=" + url +
                "}";
    }
}
