package com.hirisun.cloud.model.param;

import com.hirisun.cloud.model.param.PageParam;

import lombok.Data;

@Data
public class ServiceIssueParam extends PageParam {

    private String area;//地区

    private String policeCategory;//警种

}
