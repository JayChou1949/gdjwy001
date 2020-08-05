package com.hirisun.cloud.system.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 调用三方接口异常记录表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-04
 */
@TableName("T_ABNORMAL_LOG")
@ApiModel(value="AbnormalLog对象", description="调用三方接口异常记录表")
public class AbnormalLog implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "接口名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "第三方url")
    @TableField("URL")
    private String url;

    @ApiModelProperty(value = "异常请求时间")
    @TableField("ABNOEMAL_REQ_TIME")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date abnoemalReqTime;

    @ApiModelProperty(value = "最后请求结果")
    @TableField("RESPONSE")
    private String response;

    @ApiModelProperty(value = "业务模块")
    @TableField("MODULE")
    private String module;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getAbnoemalReqTime() {
        return abnoemalReqTime;
    }

    public void setAbnoemalReqTime(Date abnoemalReqTime) {
        this.abnoemalReqTime = abnoemalReqTime;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "AbnormalLog{" +
        "id=" + id +
        ", name=" + name +
        ", url=" + url +
        ", abnoemalReqTime=" + abnoemalReqTime +
        ", response=" + response +
        ", module=" + module +
        "}";
    }
}
