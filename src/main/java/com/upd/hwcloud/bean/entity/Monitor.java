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
 * 接口监控实体
 * </p>
 *
 * @author huru
 * @since 2019-04-03
 */
@ApiModel("接口监控实体")
@TableName("TB_MONITOR")
public class Monitor extends Model<Monitor> {

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
     * 对应字典monitorStatus
     */
    @ApiModelProperty("对应字典monitorStatus")
    @TableField("STATUS")
    private String status;

    /**
     * 厂商
     */
    @ApiModelProperty("厂商")
    @TableField("PRODUCER")
    private String producer;

    /**
     * 最后请求时间
     */
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @ApiModelProperty("最后请求时间")
    @TableField("LAST_REQ_TIME")
    private Date lastReqTime;

    /**
     * 最后请求结果
     */
    @ApiModelProperty("最后请求结果")
    @TableField("LAST_RESPONSE")
    private String lastResponse;

    /**
     * 业务模块
     */
    @ApiModelProperty("业务模块")
    @TableField("MODULE")
    private String module;

    //前端需要
    @TableField(exist = false)
    private String statusName;

    public String getId() {
        return id;
    }

    public Monitor setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Monitor setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Monitor setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Monitor setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getProducer() {
        return producer;
    }

    public Monitor setProducer(String producer) {
        this.producer = producer;
        return this;
    }

    public Date getLastReqTime() {
        return lastReqTime;
    }

    public Monitor setLastReqTime(Date lastReqTime) {
        this.lastReqTime = lastReqTime;
        return this;
    }

    public String getLastResponse() {
        return lastResponse;
    }

    public Monitor setLastResponse(String lastResponse) {
        this.lastResponse = lastResponse;
        return this;
    }

    public String getModule() {
        return module;
    }

    public Monitor setModule(String module) {
        this.module = module;
        return this;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Monitor{" +
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
