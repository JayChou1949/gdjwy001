package com.hirisun.cloud.model.system;

import java.util.List;

import com.hirisun.cloud.model.file.FilesVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 资源回收申请提交实体
 */
@ApiModel(description ="资源回收申请提交实体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRecoverSubmitVo {

    @ApiModelProperty("申请资料")
    private List<FilesVo> fileList;

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
