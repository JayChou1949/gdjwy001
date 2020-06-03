package com.upd.hwcloud.bean.vo.workbench;

import lombok.Data;

@Data
public class ServiceStatisticsVO {

    /**
     * 服务名称
     */
    private String serviceName;


    /**
     * 上线时间
     */
    private String onlineDate;


    /**
     * 实例数（订购数）
     */
    private Integer instanceNum;

    /**
     * 被调用数
     */
    private Integer reqCount;
}
