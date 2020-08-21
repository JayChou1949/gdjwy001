package com.hirisun.cloud.model.iaas.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.util.Date;

@Data
public class CloudDesktopVO {

    /**
     * info id
     */
    private String id;

    private String ip;

    private String name;

    private String idcard;

    private String phone;

    /**
     * 用途
     */
    private String useType;


    /**
     * 身份
     */
    private String  userType;

    private String belong;

    private String orderNumber;

    private String creatorName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applicationTime;




}
