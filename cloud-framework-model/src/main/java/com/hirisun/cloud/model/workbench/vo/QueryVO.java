package com.hirisun.cloud.model.workbench.vo;

import lombok.Data;

@Data
public class QueryVO {
    /**
     * 服务名
     */
    private String serviceName;
    /**
     * 分类
     */
    private String category;
    /**
     * 地市
     */
    private String area;
    /**
     * 警种
     */
    private String policeCategory;
    /**
     * 申请人（或处理人）
     */
    private String creator;

    /**
     * 申请人名字
     */
    private String creatorName;

    /**
     * 申请时间(start)
     */
    private String startDate;
    /**
     * 申请时间(end)
     */
    private String endDate;
    /**
     * queryId
     */
    private String queryId;


    /***
     * =================SAAS begin=====================
     */
    /**
     *试点应用
     */
    private Integer pilot;


    /***
     * =================SAAS end=====================
     */



    /**
     * 关键字
     */
    private String keyWord;


    /**
     * 处理人
     */
    private String processType;


    private String handler;

    /**
     * 状态
     */
    private String status;

    /**
     * 资源类型
     */
    private Long type;

    /**
     * 专项名
     */
    private String projectName;

}
