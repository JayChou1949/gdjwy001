package com.hirisun.cloud.model.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源回收申请页面返回实体
 * @author yyc
 * @date 2020/5/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceRecoverResponseVo {


    @ApiModelProperty(name = "申请人")
    private String applicant;

    @ApiModelProperty(name = "申请人电话")
    private String applicantPhone;

    @ApiModelProperty(name = "申请人单位")
    private String applicantOrgName;

    @ApiModelProperty(name = "拟缩配虚拟机总数")
    private String resourceNum;

    @ApiModelProperty(name = "导入时间")
    private String importTimeStr;
}
