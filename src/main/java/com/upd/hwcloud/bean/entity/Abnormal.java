package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Clob;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *  异常记录表
 * </p>
 *
 * @author huru
 * @since 2019-04-03
 */
@ApiModel("异常记录实体")
@TableName("TB_ABNORMAL")
public class Abnormal extends Model<Abnormal> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    /**
     * 接口名称
     */
    @ApiModelProperty("接口名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty("url")
    @TableField("URL")
    private String url;

    /**
     * 异常请求时间
     */
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @ApiModelProperty("异常请求时间")
    @TableField("ABNOEMAL_REQ_TIME")
    private Date abnoemalReqTime;

    /**
     * 最后请求结果
     */
    @ApiModelProperty("最后请求结果")
    @TableField("RESPONSE")
    private String response;

    /**
     * 业务模块
     */
    @ApiModelProperty("业务模块")
    @TableField("MODULE")
    private String module;


    public String getId() {
        return id;
    }

    public Abnormal setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Abnormal setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Abnormal setUrl(String url) {
        this.url = url;
        return this;
    }

    public Date getAbnoemalReqTime() {
        return abnoemalReqTime;
    }

    public Abnormal setAbnoemalReqTime(Date abnoemalReqTime) {
        this.abnoemalReqTime = abnoemalReqTime;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public Abnormal setResponse(String response) {
        this.response = response;
        return this;
    }

    public String getModule() {
        return module;
    }

    public Abnormal setModule(String module) {
        this.module = module;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Abnormal{" +
                "id=" + id +
                ", name=" + name +
                ", url=" + url +
                ", abnoemalReqTime=" + abnoemalReqTime +
                ", response=" + response +
                ", module=" + module +
                "}";
    }
}
