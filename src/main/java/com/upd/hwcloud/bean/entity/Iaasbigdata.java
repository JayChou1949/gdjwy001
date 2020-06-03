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
@TableName("TB_IAASBIGDATA")
public class Iaasbigdata extends Model<Iaasbigdata> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    @TableField("NAME")
    private String name;

    @TableField("KIND")
    private Long kind;

    @TableField("CPUTOTAL")
    private Long cputotal;

    @TableField("CPUUSED")
    private Long cpuused;

    @TableField("CPURATIO")
    private Long cpuratio;

    @TableField("RAMTOTAL")
    private Long ramtotal;

    @TableField("RAMUSED")
    private Long ramused;

    @TableField("RAMRATIO")
    private Long ramratio;

    @TableField("DISKTOTAL")
    private Long disktotal;

    @TableField("DISKUSED")
    private Long diskused;

    @TableField("DISKRATIO")
    private Long diskratio;

    @TableField("NET_ID")
    private Long netId;


    public String getId() {
        return id;
    }

    public Iaasbigdata setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Iaasbigdata setName(String name) {
        this.name = name;
        return this;
    }

    public Long getKind() {
        return kind;
    }

    public Iaasbigdata setKind(Long kind) {
        this.kind = kind;
        return this;
    }

    public Long getCputotal() {
        return cputotal;
    }

    public Iaasbigdata setCputotal(Long cputotal) {
        this.cputotal = cputotal;
        return this;
    }

    public Long getCpuused() {
        return cpuused;
    }

    public Iaasbigdata setCpuused(Long cpuused) {
        this.cpuused = cpuused;
        return this;
    }

    public Long getCpuratio() {
        return cpuratio;
    }

    public Iaasbigdata setCpuratio(Long cpuratio) {
        this.cpuratio = cpuratio;
        return this;
    }

    public Long getRamtotal() {
        return ramtotal;
    }

    public Iaasbigdata setRamtotal(Long ramtotal) {
        this.ramtotal = ramtotal;
        return this;
    }

    public Long getRamused() {
        return ramused;
    }

    public Iaasbigdata setRamused(Long ramused) {
        this.ramused = ramused;
        return this;
    }

    public Long getRamratio() {
        return ramratio;
    }

    public Iaasbigdata setRamratio(Long ramratio) {
        this.ramratio = ramratio;
        return this;
    }

    public Long getDisktotal() {
        return disktotal;
    }

    public Iaasbigdata setDisktotal(Long disktotal) {
        this.disktotal = disktotal;
        return this;
    }

    public Long getDiskused() {
        return diskused;
    }

    public Iaasbigdata setDiskused(Long diskused) {
        this.diskused = diskused;
        return this;
    }

    public Long getDiskratio() {
        return diskratio;
    }

    public Iaasbigdata setDiskratio(Long diskratio) {
        this.diskratio = diskratio;
        return this;
    }

    public Long getNetId() {
        return netId;
    }

    public Iaasbigdata setNetId(Long netId) {
        this.netId = netId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Iaasbigdata{" +
        "id=" + id +
        ", name=" + name +
        ", kind=" + kind +
        ", cputotal=" + cputotal +
        ", cpuused=" + cpuused +
        ", cpuratio=" + cpuratio +
        ", ramtotal=" + ramtotal +
        ", ramused=" + ramused +
        ", ramratio=" + ramratio +
        ", disktotal=" + disktotal +
        ", diskused=" + diskused +
        ", diskratio=" + diskratio +
        ", netId=" + netId +
        "}";
    }
}
