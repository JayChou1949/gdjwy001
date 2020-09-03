package com.hirisun.cloud.model.workbench.vo;

import lombok.Data;

/**
 * @author wuc
 * @date 2019/12/12
 */
@Data
public class SaasStatisticsVO {

    private String serviceID;

    private String serviceName;

    private String creator;

    private String creatorName;

    private String onlineTime;

    private String status;

    private String userNum;

    private String viewCount;
}
