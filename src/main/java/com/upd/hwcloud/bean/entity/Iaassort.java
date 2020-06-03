package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 资源使用率实体
 * </p>
 *
 * @author yyc
 * @since 2018-10-28
 */
@TableName("TB_IAASSORT")
public class Iaassort extends Model<Iaassort> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    @TableField("NAME")
    private String name;

    @TableField("RATIO")
    private Float ratio;
    //0 appTop5 1 app Low5 2 policeTop5 3 policeLow5
    @TableField("KIND")
    private Long kind;

    @TableField("RANK")
    private Long rank;
    //资源种类 0 CPU 1 内存 2磁盘
    @TableField("RESOURCEKIND")
    private Long resourcekind;

    @TableField("NET_ID")
    private Long netId;


    public String getId() {
        return id;
    }

    public Iaassort setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Iaassort setName(String name) {
        this.name = name;
        return this;
    }

    public Float getRatio() {
        return ratio;
    }

    public Iaassort setRatio(Float ratio) {
        this.ratio = ratio;
        return this;
    }

    public Long getKind() {
        return kind;
    }

    public Iaassort setKind(Long kind) {
        this.kind = kind;
        return this;
    }

    public Long getRank() {
        return rank;
    }

    public Iaassort setRank(Long rank) {
        this.rank = rank;
        return this;
    }

    public Long getResourcekind() {
        return resourcekind;
    }

    public Iaassort setResourcekind(Long resourcekind) {
        this.resourcekind = resourcekind;
        return this;
    }

    public Long getNetId() {
        return netId;
    }

    public Iaassort setNetId(Long netId) {
        this.netId = netId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Iaassort{" +
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
