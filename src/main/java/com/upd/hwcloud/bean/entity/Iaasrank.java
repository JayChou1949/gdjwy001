package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yyc
 * @since 2018-10-28
 */
@TableName("TB_IAASRANK")
public class Iaasrank extends Model<Iaasrank> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private Long id;

    @TableField("NAME")
    private String name;

    @TableField("RATIO")
    private Float ratio;

        /**
     * 0资源TOP10 1最远LOW10 2警种TOP10
     */
         @TableField("KIND")
    private Long kind;

    @TableField("RANK")
    private Long rank;

    @TableField("RESOURCEKIND")
    private Long resourcekind;

    @TableField("NET_ID")
    private Long netId;


    public Long getId() {
        return id;
    }

    public Iaasrank setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Iaasrank setName(String name) {
        this.name = name;
        return this;
    }

    public Float getRatio() {
        return ratio;
    }

    public Iaasrank setRatio(Float ratio) {
        this.ratio = ratio;
        return this;
    }

    public Long getKind() {
        return kind;
    }

    public Iaasrank setKind(Long kind) {
        this.kind = kind;
        return this;
    }

    public Long getRank() {
        return rank;
    }

    public Iaasrank setRank(Long rank) {
        this.rank = rank;
        return this;
    }

    public Long getResourcekind() {
        return resourcekind;
    }

    public Iaasrank setResourcekind(Long resourcekind) {
        this.resourcekind = resourcekind;
        return this;
    }

    public Long getNetId() {
        return netId;
    }

    public Iaasrank setNetId(Long netId) {
        this.netId = netId;
        return this;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Iaasrank{" +
        "id=" + id +
        ", name=" + name +
        ", ratio=" + ratio +
        ", kind=" + kind +
        ", rank=" + rank +
        ", resourcekind=" + resourcekind +
        ", netId=" + netId +
        "}";
    }
}
