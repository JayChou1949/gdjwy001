package com.hirisun.cloud.model.ncov.vo.daas;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CallAndNameVo implements Comparable<CallAndNameVo>{
    
	@ApiModelProperty(value="调用次数")
    private Long count;
	
	@ApiModelProperty(value="名称")
    private String name;

    @Override
    public int compareTo(CallAndNameVo callAndNameDto) {
        long sub = this.count - callAndNameDto.getCount();
        if (sub>0){
            return -1;
        }
        return 1;
    }
}
