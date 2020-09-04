package com.hirisun.cloud.order.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 申请信息表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-08
 */
@Data
@ApiModel(value="ApplyInfo对象", description="申请信息表")
public class WorkOrder<T> extends Model<WorkOrder<T>> {

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

    private String workFlowId;


}
