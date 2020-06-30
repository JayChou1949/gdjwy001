package com.upd.hwcloud.bean.entity.application;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 应用访问统计表
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-06-29
 */
@TableName("TB_SASS_APP_USE_STATISTICS")
@ApiModel(value="SassAppUseStatistics对象", description="应用访问统计表")
@Data
public class SassAppUseStatistics implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "用户id")
    @TableField("PERSONID")
    private String personid;

    @ApiModelProperty(value = "用户名")
    @TableField("PERSONNAME")
    private String personname;

    @ApiModelProperty(value = "应用id")
    @TableField("APPID")
    private String appid;

    @ApiModelProperty(value = "应用名称")
    @TableField("APPNAME")
    private String appname;

    @ApiModelProperty(value = "应用使用次数")
    @TableField("COUNT")
    private BigDecimal count;

    @ApiModelProperty(value = "统计日期")
    @TableField("ADDTIME")
    private String addtime;


    @Override
    public String toString() {
        return "SassAppUseStatistics{" +
        "id=" + id +
        ", personid=" + personid +
        ", personname=" + personname +
        ", appid=" + appid +
        ", appname=" + appname +
        ", count=" + count +
        "}";
    }
}
