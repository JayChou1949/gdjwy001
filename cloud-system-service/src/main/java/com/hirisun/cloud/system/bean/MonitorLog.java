package com.hirisun.cloud.system.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 第三方接口调用监控表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-04
 */
@TableName("T_MONITOR_LOG")
@ApiModel(value="MonitorLog对象", description="第三方接口调用监控表")
public class MonitorLog implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "接口名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "第三方url")
    @TableField("URL")
    private String url;

    @ApiModelProperty(value = "对应字典monitorStatus")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "厂商")
    @TableField("PRODUCER")
    private String producer;

    @ApiModelProperty(value = "最后请求时间")
    @TableField("LAST_REQ_TIME")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date lastReqTime;

    @ApiModelProperty(value = "最后请求结果")
    @TableField("LAST_RESPONSE")
    private String lastResponse;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Date getLastReqTime() {
        return lastReqTime;
    }

    public void setLastReqTime(Date lastReqTime) {
        this.lastReqTime = lastReqTime;
    }

    public String getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(String lastResponse) {
        this.lastResponse = lastResponse;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "MonitorLog{" +
        "id=" + id +
        ", name=" + name +
        ", url=" + url +
        ", status=" + status +
        ", producer=" + producer +
        ", lastReqTime=" + lastReqTime +
        ", lastResponse=" + lastResponse +
        ", module=" + module +
        "}";
    }
}
