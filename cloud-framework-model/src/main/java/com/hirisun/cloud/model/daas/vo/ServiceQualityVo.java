package com.hirisun.cloud.model.daas.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;


@Data
public class ServiceQualityVo {

    @Excel(name = "序号",orderNum = "1")
    private Integer id;//序号

    @Excel(name = "服务名称",orderNum = "1",width = 60)
    private String serviceName;//服务名称

    @Excel(name = "地市",orderNum = "2",width = 15)
    private String serviceAreaName;//地市

    @Excel(name = "警种",orderNum = "3",width = 15)
    private String servicePoliceCategory;//警种

    @Excel(name = "被调用异常率(%)",orderNum = "4",width = 15,type = 10)
    private Double abnormalProbability;//被调用异常率

    @Excel(name = "被调用异常数(次)",orderNum = "5",width = 15,type = 10)
    private Integer abnormal;//被调用异常数

    @JsonIgnore
    private Integer reqCount;//请求数

    @Excel(name = "被调用平均时延(ms)",orderNum = "7",width = 15,type = 10)
    private Double avgLatency;//被调用平均时延

    @Excel(name = "后端服务调用时延(ms)",orderNum = "8",width = 15,type = 10)
    private Double avgBackendLatency;//后端服务调用时延

    @JsonIgnore
    private Integer num;//数量

    public double getAbnormalProbability() {
        if (reqCount==null||reqCount==0){
            reqCount=1;
        }
        
        BigDecimal b1 = new BigDecimal(Double.toString(Double.valueOf(abnormal)));
        BigDecimal b2 = new BigDecimal(Double.toString(reqCount));
        
        double value = b1.divide(b2,2,BigDecimal.ROUND_HALF_UP)
        		.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return value;
    }

    public double getAvgLatency() {
        return new BigDecimal(avgLatency).divide(new BigDecimal(num),0, RoundingMode.HALF_UP).doubleValue();
    }

    public double getAvgBackendLatency() {
        return new BigDecimal(avgBackendLatency).divide(new BigDecimal(num),0, RoundingMode.HALF_UP).doubleValue();
    }
}
