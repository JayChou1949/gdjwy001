package com.hirisun.cloud.model.daas.vo;

import lombok.Data;

@Data
public class DaasApplicationExtVo extends DaasApplicationVo {

	private static final long serialVersionUID = -8477950135479034555L;

	private String appName;

    private String orderNumber;

    private String creatorName;

}
