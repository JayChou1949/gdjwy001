package com.hirisun.cloud.model.ncov.vo.daas;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class DataAccessLevel3Vo implements Serializable{

	private static final long serialVersionUID = -643517662185108106L;

	@ApiModelProperty(value="序号")
    private Integer num;
	
	@ApiModelProperty(value="单位名称")
    private String unitName;
	
	@ApiModelProperty(value="表名")
    private String tableName;
	
	@ApiModelProperty(value="类型")
    private String type;
	
	@ApiModelProperty(value="总调用")
    private Long countAll;
	
	@ApiModelProperty(value="昨日调用")
    private Long yesyerdayCount;
}
