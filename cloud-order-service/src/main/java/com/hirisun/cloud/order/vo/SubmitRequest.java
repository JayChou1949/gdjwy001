package com.hirisun.cloud.order.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.order.bean.apply.ApplyInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 购物车提交请求VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "购物车提交请求VO")
public class SubmitRequest implements Serializable{

	private static final long serialVersionUID = 5140813862774089657L;

    //@ApiModelProperty(value = "表单编码")
    //private String formNum;

    //@ApiModelProperty(value = "服务类别ID")
    //private String serviceTypeId;

    //@ApiModelProperty(value = "服务类别名")
    //private String serviceTypeName;

	@ApiModelProperty(value = "建设单位")
    private String jsUnit;

    @ApiModelProperty(value = "建设单位负责人")
    private String jsPrincipal;

    @ApiModelProperty(value = "建设单位负责人电话")
    private String jsPrincipalPhone;

    @ApiModelProperty(value = "项目经办人")
    private String jsManager;

    @ApiModelProperty(value = "项目经办人电话")
    private String jsManagerPhone;

    @ApiModelProperty(value = "承建单位")
    private String cjUnit;

    @ApiModelProperty(value = "承建单位负责人")
    private String cjPrincipal;

    @ApiModelProperty(value = "承建单位负责人电话")
    private String cjPrincipalPhone;

    @ApiModelProperty(value = "承建单位办理人")
    private String cjHandler;

    @ApiModelProperty(value = "承建单位办理人电话")
    private String cjHandlerPhone;

    @ApiModelProperty(value = "承建单位代码")
    private String cjOrgCode;

    @ApiModelProperty(value = "承建单位负责人身份证号")
    private String cjPrincipalIdcard;

    @ApiModelProperty(value = "上报方式 0-地区 1-警种 2-政府机构 ")
    private String applyType;

    @ApiModelProperty(value = "政府机构名称")
    private String govUnit;

    @ApiModelProperty(value = "政府机构代码")
    private String govOrgCode;

    @ApiModelProperty(value = "政府项目负责人")
    private String govPrincipal;

    @ApiModelProperty(value = "政府项目负责人职务")
    private String govPrincipalPostType;

    @ApiModelProperty(value = "政府项目负责人电话")
    private String govPrincipalPhone;

    @ApiModelProperty(value = "政府项目负责人身份证")
    private String govPrincipalIdcard;

    @ApiModelProperty(value = "承建单位输入类型 0:选择输入 1:手动输入")
    private String cjInputType;

    @ApiModelProperty(value = "申请人单位")
    private String creatorUnit;

    @ApiModelProperty(value = "申请人名字")
    private String creatorName;

    @ApiModelProperty(value = "申请人电话")
    private String creatorPhone;

    @ApiModelProperty(value = "申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applicationTime;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "应用名")
    private String appName;

    @ApiModelProperty(value = "警种")
    private String policeCategory;

    @ApiModelProperty(value = "地市")
    private String areaName;

    @ApiModelProperty(value = "国家专项")
    private String nationalSpecialProject;

    @ApiModelProperty(value = "应用名称输入类型 0:选择输入 1:手动输入")
    private String appNameInputType;

    @ApiModelProperty(value = "申请说明")
    private String explanation;

    @ApiModelProperty(value = "申请说明saas")
    private String explanationSaas;

    @ApiModelProperty(value = "建设方式 0:自建 1:第三方建设")
    private String buildMode;


    @ApiModelProperty(value = "关联文件")
    private List<FilesVo> fileList;

    @ApiModelProperty(value = "购物车ID集合 ','分割")
    private String shoppingCartIds;

    @ApiModelProperty(value = "处理人ID集合 ','分割")
    private String userIds;

    @ApiModelProperty(value = "提交类型")
    private String type;

    @ApiModelProperty(value = "大数据集群账号")
    private String clusterAccount;

    @ApiModelProperty(value = "使用资源的虚拟机IP")
    private String vmIp;

    public ApplyInfo convertToApplicationInfo(){
        ApplyInfo info = new ApplyInfo();
        BeanUtils.copyProperties(this,info);
        return info;
    }
}
