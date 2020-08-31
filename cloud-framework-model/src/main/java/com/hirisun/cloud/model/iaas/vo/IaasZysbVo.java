package com.hirisun.cloud.model.iaas.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.model.user.UserVO;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("iaas 资源上报申请信息表")
@Data
public class IaasZysbVo implements Serializable {

	private static final long serialVersionUID = -3849366651986227583L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("申请人")
    private String creator;

	@ApiModelProperty("状态 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 ")
    private String status;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;
    
	@ApiModelProperty("上报方式 0-地区 1-警种")
    private String applyType;
    
	@ApiModelProperty(value = "流程id")
    private String workFlowId;

	@ApiModelProperty("流程版本")
    private Integer workFlowVersion;

    /**
     * 国家专项
     */
	@ApiModelProperty("国家专项")
    private String nationalSpecialProject;

    @Excel(name = "工单号")
    @ApiModelProperty("申请单号")
    private String orderNumber;

    @Excel(name = "申请人")
    @ApiModelProperty("申请人姓名")
    private String creatorName;

    @ApiModelProperty("申请说明")
    private String explanation;

    @ApiModelProperty("系统所属警种")
    private String policeCategory;

    @Excel(name = "申请单位")
    @ApiModelProperty("申请人单位")
    private String creatorUnit;

    @Excel(name = "申请时间", exportFormat = "yyyy-MM-dd")
    @ApiModelProperty("申请时间")
    private Date applicationTime;

    @ApiModelProperty("申请人电话")
    private String creatorPhone;

    @ApiModelProperty("地区")
    private String areas;

         //todo:二级门户改造-租户上报新增nationalProject属性

    @Excel(name = "预计使用时间", exportFormat = "yyyy-MM-dd")
    @ApiModelProperty("开始时间")
    private Date startDate;

    @ApiModelProperty("资源上报项目信息")
    private List<IaasZysbXmxxVo> projectList;

    /**
     * 审核记录(包含实施记录)
     */
    @ApiModelProperty("审核记录(包含实施记录)")
    private List<AppReviewInfoVo> reviewList;
    
    @ApiModelProperty("创建人")
    private UserVO user;

    @ApiModelProperty("是否可审核")
    private boolean canReview = false;

    @ApiModelProperty("是否可以加办")
    private boolean canAdd = false;
    
    @ApiModelProperty("是否可删除")
    private boolean canDelete = false;

    @ApiModelProperty("是否可修改")
    private boolean canEdit = false;
    
    @ApiModelProperty("是否可转发")
    private boolean canTrans = false;

    @ApiModelProperty("是否可回退")
    private boolean canFall = false;
    
    @ApiModelProperty("当前环节处理人")
    private String processingPerson;
    
    @ApiModelProperty("")
    private boolean canAdviser;

    @ApiModelProperty("实例id")
    private String instanceId;

}
