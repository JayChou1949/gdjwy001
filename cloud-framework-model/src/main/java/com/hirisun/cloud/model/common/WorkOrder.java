package com.hirisun.cloud.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wuxiaoxing 2020-09-01
 * 工单通用对象,将通用方法统一处理
 */
@Data
@ApiModel(value="工单通用对象", description="工单通用对象")
public class WorkOrder<T> {
    private String id;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "当前处理人")
    private String processingPerson;

    @ApiModelProperty(value = "流程实例id")
    private String instanceId;

    @ApiModelProperty(value = "是否能删除")
    private boolean canDelete=false;

    @ApiModelProperty(value = "状态 0:购物车 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施未完成")
    private String status;

    @ApiModelProperty(value = "申请人")
    private String creator;

    private String businessName;

//    private String userName;

    private Date modifiedTime;

    private String orderNumber;

}
