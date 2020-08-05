package com.hirisun.cloud.model.ncov.vo.daas;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CallAndTimeVo {

	@ApiModelProperty(value="调用次数")
    private Long count;

	@ApiModelProperty(value="日期")
    private String time;
}
