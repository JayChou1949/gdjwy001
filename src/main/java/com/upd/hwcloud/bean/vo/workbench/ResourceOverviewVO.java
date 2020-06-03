package com.upd.hwcloud.bean.vo.workbench;

import lombok.Data;

/**
 * @author wuc
 * @date 2019/12/16
 */
@Data
public class ResourceOverviewVO {

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * 应用ID
     */
    private String appId;


    /**
     * 订单ID
     */
    private String infoId;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 申请人
     */
    private String creatorName;

    /**
     * 申请单位
     */
    private String creatorUnit;

    /**
     * 申请时间
     */
    private String createTime;

    /**
     * 用户数
     */
    private Integer userNum;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 调用次数
     */
    private Long count;





}
