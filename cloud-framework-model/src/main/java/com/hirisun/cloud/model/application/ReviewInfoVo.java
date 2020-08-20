package com.hirisun.cloud.model.application;

import lombok.Data;

import java.util.Date;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 20:30 2019/11/15
 * @ Description：审核信息
 */
@Data
public class ReviewInfoVo {

    private String name;//审批人

    private Date createTime;//审批时间

    private String result;//审批结果

    private String remark;//审批信息

    private String modelName;//环节名

    private String modelType;
}
