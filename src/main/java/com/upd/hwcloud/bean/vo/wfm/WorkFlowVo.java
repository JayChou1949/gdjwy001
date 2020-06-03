package com.upd.hwcloud.bean.vo.wfm;

import com.upd.hwcloud.bean.entity.wfm.Workflowmodel;

import java.util.LinkedList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author wuc
 * @date 2020/3/16
 */
@Data
public class WorkFlowVo {
    /**
     * 流程名
     */
    @ApiModelProperty(value = "流程名")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 最新版本
     */
    @ApiModelProperty(value = "版本")
    private Integer version;

    /**
     * 环节信息
     */
    @ApiModelProperty(value = "环节信息（已排序）")
    private LinkedList<Workflowmodel> model;


}
