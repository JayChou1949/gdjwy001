package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 二级门户浏览量
 * </p>
 *
 * @author yyc
 * @since 2019-12-20
 */
@TableName("TB_SUB_PORTAL_VIEW")
public class SubPortalView extends Model<SubPortalView> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 0-警种 1-地市
     */
         @TableField("TYPE")
    @ApiModelProperty(value = "类型 0-警种 1-地市",example = "1")
    private Integer type;

        /**
     * 警种地市名
     */
         @TableField("NAME")
    @ApiModelProperty(value = "警种地市名",example = "梅州")
    private String name;

        /**
     * 浏览量
     */
         @TableField("VIEW_COUNT")
    private Long viewCount;


    public String getId() {
        return id;
    }

    public SubPortalView setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public SubPortalView setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public SubPortalView setName(String name) {
        this.name = name;
        return this;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public SubPortalView setViewCount(Long viewCount) {
        this.viewCount = viewCount;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SubPortalView{" +
        "id=" + id +
        ", type=" + type +
        ", name=" + name +
        ", viewCount=" + viewCount +
        "}";
    }
}
