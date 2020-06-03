package com.upd.hwcloud.bean.entity.iaasConfig;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 警务云工程资源
 * </p>
 *
 * @author huru
 * @since 2018-11-17
 */
@TableName("TB_POLICE_CLOUD")
public class PoliceCloud extends Model<PoliceCloud> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * cpu总数
     */
         @TableField("CPU_TOTAL")
    private String cpuTotal;

        /**
     * cpu分配数
     */
         @TableField("CPU_ALLOTMENT")
    private String cpuAllotment;

        /**
     * cpu分配率
     */
         @TableField("CPU_ALLOTMENT_PRO")
    private String cpuAllotmentPro;

    /**
     * GPU总数
     */
    @TableField("GPU_TOTAL")
    private String gpuTotal;

    /**
     * GPU分配数
     */
    @TableField("GPU_ALLOTMENT")
    private String gpuAllotment;

    /**
     * GPU分配率
     */
    @TableField("GPU_ALLOTMENT_PRO")
    private String gpuAllotmentPro;

        /**
     * 内存总数
     */
         @TableField("EMS_TOTAL")
    private String emsTotal;

        /**
     * 内存分配数
     */
         @TableField("EMS_ALLOTMENT")
    private String emsAllotment;

        /**
     * 内存分配率
     */
         @TableField("EMS_ALLOTMENT_PRO")
    private String emsAllotmentPro;

        /**
     * 存储总数
     */
         @TableField("STOR_TOTAL")
    private String storTotal;

        /**
     * 存储分配数
     */
         @TableField("STOR_ALLOTMENT")
    private String storAllotment;

        /**
     * 存储分配率
     */
         @TableField("STOR_ALLOTMENT_PRO")
    private String storAllotmentPro;

        /**
     * 类型
     */
         @TableField("TYPE")
    private String type;

        /**
     * 地区
     */
         @TableField("AREA")
    private String area;


    public String getId() {
        return id;
    }

    public PoliceCloud setId(String id) {
        this.id = id;
        return this;
    }

    public String getCpuTotal() {
        return cpuTotal;
    }

    public PoliceCloud setCpuTotal(String cpuTotal) {
        this.cpuTotal = cpuTotal;
        return this;
    }

    public String getCpuAllotment() {
        return cpuAllotment;
    }

    public PoliceCloud setCpuAllotment(String cpuAllotment) {
        this.cpuAllotment = cpuAllotment;
        return this;
    }

    public String getCpuAllotmentPro() {
        return cpuAllotmentPro;
    }

    public PoliceCloud setCpuAllotmentPro(String cpuAllotmentPro) {
        this.cpuAllotmentPro = cpuAllotmentPro;
        return this;
    }

    public String getEmsTotal() {
        return emsTotal;
    }

    public PoliceCloud setEmsTotal(String emsTotal) {
        this.emsTotal = emsTotal;
        return this;
    }

    public String getEmsAllotment() {
        return emsAllotment;
    }

    public PoliceCloud setEmsAllotment(String emsAllotment) {
        this.emsAllotment = emsAllotment;
        return this;
    }

    public String getEmsAllotmentPro() {
        return emsAllotmentPro;
    }

    public PoliceCloud setEmsAllotmentPro(String emsAllotmentPro) {
        this.emsAllotmentPro = emsAllotmentPro;
        return this;
    }

    public String getStorTotal() {
        return storTotal;
    }

    public PoliceCloud setStorTotal(String storTotal) {
        this.storTotal = storTotal;
        return this;
    }

    public String getStorAllotment() {
        return storAllotment;
    }

    public PoliceCloud setStorAllotment(String storAllotment) {
        this.storAllotment = storAllotment;
        return this;
    }

    public String getStorAllotmentPro() {
        return storAllotmentPro;
    }

    public PoliceCloud setStorAllotmentPro(String storAllotmentPro) {
        this.storAllotmentPro = storAllotmentPro;
        return this;
    }

    public String getType() {
        return type;
    }

    public PoliceCloud setType(String type) {
        this.type = type;
        return this;
    }

    public String getArea() {
        return area;
    }

    public PoliceCloud setArea(String area) {
        this.area = area;
        return this;
    }

    public String getGpuTotal() {
        return gpuTotal;
    }

    public void setGpuTotal(String gpuTotal) {
        this.gpuTotal = gpuTotal;
    }

    public String getGpuAllotment() {
        return gpuAllotment;
    }

    public void setGpuAllotment(String gpuAllotment) {
        this.gpuAllotment = gpuAllotment;
    }

    public String getGpuAllotmentPro() {
        return gpuAllotmentPro;
    }

    public void setGpuAllotmentPro(String gpuAllotmentPro) {
        this.gpuAllotmentPro = gpuAllotmentPro;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PoliceCloud{" +
        "id=" + id +
        ", cpuTotal=" + cpuTotal +
        ", cpuAllotment=" + cpuAllotment +
        ", cpuAllotmentPro=" + cpuAllotmentPro +
        ", emsTotal=" + emsTotal +
        ", emsAllotment=" + emsAllotment +
        ", emsAllotmentPro=" + emsAllotmentPro +
        ", storTotal=" + storTotal +
        ", storAllotment=" + storAllotment +
        ", storAllotmentPro=" + storAllotmentPro +
        ", type=" + type +
        ", area=" + area +
        "}";
    }
}
