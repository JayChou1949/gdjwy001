package com.upd.hwcloud.bean.vo.resourceRecover;

import com.upd.hwcloud.bean.entity.Files;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 资源回收申请提交实体
 * @author yyc
 * @date 2020/5/14
 */
@ApiModel(description ="资源回收申请提交实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRecoverSubmit {

    @ApiModelProperty("申请资料")
    private List<Files> fileList;

    @ApiModelProperty("被回收人信息数组")
    private List<RecoveredUserInfo> recoveredUserInfoList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecoveredUserInfo{
        @ApiModelProperty("被回收人")
        private String applicant;

        @ApiModelProperty("被回收人电话")
        private String applicantPhone;

        @ApiModelProperty("被回收人单位")
        private String applicantOrgName;


        @ApiModelProperty("导入时间")
        private String importTime;
    }

}
