package com.upd.hwcloud.bean.entity;

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
@TableName("TB_IAASDISTRUBITE")
public class Iaasdistrubite extends Model<Iaasdistrubite> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    @TableField("NAME")
    private String name;

    @TableField("NUM")
    private Long num;

    @TableField("NET_ID")
    private Long netId;

    @TableField("ICON")
    private String icon;

    @TableField("BACKGROUND")
    private String background;


    public String getId() {
        return id;
    }

    public Iaasdistrubite setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Iaasdistrubite setName(String name) {
        this.name = name;
        return this;
    }

    public Long getNum() {
        return num;
    }

    public Iaasdistrubite setNum(Long num) {
        this.num = num;
        return this;
    }

    public Long getNetId() {
        return netId;
    }

    public Iaasdistrubite setNetId(Long netId) {
        this.netId = netId;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Iaasdistrubite setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getBackground() {
        return background;
    }

    public Iaasdistrubite setBackground(String background) {
        this.background = background;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Iaasdistrubite{" +
                "id=" + id +
                ", name=" + name +
                ", num=" + num +
                ", netId=" + netId +
                ", icon=" + icon +
                ", background=" + background +
                "}";
    }
}
