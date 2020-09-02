package com.hirisun.cloud.order.bean.servicePublish;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * APIG服务调用信息表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@TableName("TB_SERVICE_STATISTICS")
@ApiModel(value="ServiceStatistics对象", description="APIG服务调用信息表")
public class ServiceStatistics implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "当前时间")
    @TableField("CURRENT_MINUTE")
    private Long currentMinute;

    @ApiModelProperty(value = "服务ID")
    @TableField("SERVICE_GUID")
    private String serviceGuid;

    @ApiModelProperty(value = "请求次数")
    @TableField("REQ_COUNT")
    private Long reqCount;

    @ApiModelProperty(value = "请求的平均时延")
    @TableField("AVG_LATENCY")
    private BigDecimal avgLatency;

    @ApiModelProperty(value = "访问后端平均处理时延")
    @TableField("AVG_BACKEND_LATENCY")
    private BigDecimal avgBackendLatency;

    @ApiModelProperty(value = "当前时间(年-月-日)")
    @TableField("CURRENT_TIME")
    private String currentTime;

    @TableField("CATA_LOG")
    private Integer cataLog;

    @ApiModelProperty(value = "访问网关最大处理时延，")
    @TableField("MAX_INNER_LATENCY")
    private Long maxInnerLatency;

    @ApiModelProperty(value = "访问网关平均处理时延")
    @TableField("AVG_INNER_LATENCY")
    private BigDecimal avgInnerLatency;

    @ApiModelProperty(value = "访问后端最大处理时延")
    @TableField("MAX_BACKEND_LATENCY")
    private Long maxBackendLatency;

    @ApiModelProperty(value = "请求消息总量")
    @TableField("INPUT_THROUGHPUT")
    private Long inputThroughput;

    @ApiModelProperty(value = "请求的最大时延")
    @TableField("MAX_LATENCY")
    private Long maxLatency;

    @ApiModelProperty(value = "响应消息总量")
    @TableField("OUTPUT_THROUGHPUT")
    private Long outputThroughput;

    @ApiModelProperty(value = "请求报 4xx 响应码总次数")
    @TableField("REQ_COUNT_4XX")
    private Long reqCount4xx;

    @ApiModelProperty(value = "请求报 5xx 响应码总次数")
    @TableField("REQ_COUNT_5XX")
    private Long reqCount5xx;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCurrentMinute() {
        return currentMinute;
    }

    public void setCurrentMinute(Long currentMinute) {
        this.currentMinute = currentMinute;
    }

    public String getServiceGuid() {
        return serviceGuid;
    }

    public void setServiceGuid(String serviceGuid) {
        this.serviceGuid = serviceGuid;
    }

    public Long getReqCount() {
        return reqCount;
    }

    public void setReqCount(Long reqCount) {
        this.reqCount = reqCount;
    }

    public BigDecimal getAvgLatency() {
        return avgLatency;
    }

    public void setAvgLatency(BigDecimal avgLatency) {
        this.avgLatency = avgLatency;
    }

    public BigDecimal getAvgBackendLatency() {
        return avgBackendLatency;
    }

    public void setAvgBackendLatency(BigDecimal avgBackendLatency) {
        this.avgBackendLatency = avgBackendLatency;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public Integer getCataLog() {
        return cataLog;
    }

    public void setCataLog(Integer cataLog) {
        this.cataLog = cataLog;
    }

    public Long getMaxInnerLatency() {
        return maxInnerLatency;
    }

    public void setMaxInnerLatency(Long maxInnerLatency) {
        this.maxInnerLatency = maxInnerLatency;
    }

    public BigDecimal getAvgInnerLatency() {
        return avgInnerLatency;
    }

    public void setAvgInnerLatency(BigDecimal avgInnerLatency) {
        this.avgInnerLatency = avgInnerLatency;
    }

    public Long getMaxBackendLatency() {
        return maxBackendLatency;
    }

    public void setMaxBackendLatency(Long maxBackendLatency) {
        this.maxBackendLatency = maxBackendLatency;
    }

    public Long getInputThroughput() {
        return inputThroughput;
    }

    public void setInputThroughput(Long inputThroughput) {
        this.inputThroughput = inputThroughput;
    }

    public Long getMaxLatency() {
        return maxLatency;
    }

    public void setMaxLatency(Long maxLatency) {
        this.maxLatency = maxLatency;
    }

    public Long getOutputThroughput() {
        return outputThroughput;
    }

    public void setOutputThroughput(Long outputThroughput) {
        this.outputThroughput = outputThroughput;
    }

    public Long getReqCount4xx() {
        return reqCount4xx;
    }

    public void setReqCount4xx(Long reqCount4xx) {
        this.reqCount4xx = reqCount4xx;
    }

    public Long getReqCount5xx() {
        return reqCount5xx;
    }

    public void setReqCount5xx(Long reqCount5xx) {
        this.reqCount5xx = reqCount5xx;
    }

    @Override
    public String toString() {
        return "ServiceStatistics{" +
        "id=" + id +
        ", currentMinute=" + currentMinute +
        ", serviceGuid=" + serviceGuid +
        ", reqCount=" + reqCount +
        ", avgLatency=" + avgLatency +
        ", avgBackendLatency=" + avgBackendLatency +
        ", currentTime=" + currentTime +
        ", cataLog=" + cataLog +
        ", maxInnerLatency=" + maxInnerLatency +
        ", avgInnerLatency=" + avgInnerLatency +
        ", maxBackendLatency=" + maxBackendLatency +
        ", inputThroughput=" + inputThroughput +
        ", maxLatency=" + maxLatency +
        ", outputThroughput=" + outputThroughput +
        ", reqCount4xx=" + reqCount4xx +
        ", reqCount5xx=" + reqCount5xx +
        "}";
    }
}
