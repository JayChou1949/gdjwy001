package com.upd.hwcloud.bean.entity.iaasConfig;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 移动信息网 大数据 视频 工程资源
 * </p>
 *
 * @author huru
 * @since 2018-11-17
 */
@TableName("TB_MOVE_DATA_VIDEO")
public class MoveDataVideo extends Model<MoveDataVideo> {

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
         @TableField("CPU_USE")
    private String cpuUse;

        /**
     * cpu分配率
     */
         @TableField("CPU_USE_PRO")
    private String cpuUsePro;

        /**
     * 内存总数
     */
         @TableField("EMS_TOTAL")
    private String emsTotal;

        /**
     * 内存分配数
     */
         @TableField("EMS_USE")
    private String emsUse;

        /**
     * 内存分配率
     */
         @TableField("EMS_USE_PRO")
    private String emsUsePro;

        /**
     * 存储总数
     */
         @TableField("DISK_TOTAL")
    private String diskTotal;

        /**
     * 存储分配数
     */
         @TableField("DISK_USE")
    private String diskUse;

        /**
     * 存储分配率
     */
         @TableField("DISK_USE_PRO")
    private String diskUsePro;

        /**
     * 类型
     */
         @TableField("TYPE")
    private String type;

        /**
     * 1移动信息网 2大数据 3视频
     */
         @TableField("STATUS")
    private String status;


    public String getId() {
        return id;
    }

    public MoveDataVideo setId(String id) {
        this.id = id;
        return this;
    }

    public String getCpuTotal() {
        return cpuTotal;
    }

    public MoveDataVideo setCpuTotal(String cpuTotal) {
        this.cpuTotal = cpuTotal;
        return this;
    }

    public String getCpuUse() {
        return cpuUse;
    }

    public MoveDataVideo setCpuUse(String cpuUse) {
        this.cpuUse = cpuUse;
        return this;
    }

    public String getCpuUsePro() {
        return cpuUsePro;
    }

    public MoveDataVideo setCpuUsePro(String cpuUsePro) {
        this.cpuUsePro = cpuUsePro;
        return this;
    }

    public String getEmsTotal() {
        return emsTotal;
    }

    public MoveDataVideo setEmsTotal(String emsTotal) {
        this.emsTotal = emsTotal;
        return this;
    }

    public String getEmsUse() {
        return emsUse;
    }

    public MoveDataVideo setEmsUse(String emsUse) {
        this.emsUse = emsUse;
        return this;
    }

    public String getEmsUsePro() {
        return emsUsePro;
    }

    public MoveDataVideo setEmsUsePro(String emsUsePro) {
        this.emsUsePro = emsUsePro;
        return this;
    }

    public String getDiskTotal() {
        return diskTotal;
    }

    public MoveDataVideo setDiskTotal(String diskTotal) {
        this.diskTotal = diskTotal;
        return this;
    }

    public String getDiskUse() {
        return diskUse;
    }

    public MoveDataVideo setDiskUse(String diskUse) {
        this.diskUse = diskUse;
        return this;
    }

    public String getDiskUsePro() {
        return diskUsePro;
    }

    public MoveDataVideo setDiskUsePro(String diskUsePro) {
        this.diskUsePro = diskUsePro;
        return this;
    }

    public String getType() {
        return type;
    }

    public MoveDataVideo setType(String type) {
        this.type = type;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public MoveDataVideo setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MoveDataVideo{" +
        "id=" + id +
        ", cpuTotal=" + cpuTotal +
        ", cpuUse=" + cpuUse +
        ", cpuUsePro=" + cpuUsePro +
        ", emsTotal=" + emsTotal +
        ", emsUse=" + emsUse +
        ", emsUsePro=" + emsUsePro +
        ", diskTotal=" + diskTotal +
        ", diskUse=" + diskUse +
        ", diskUsePro=" + diskUsePro +
        ", type=" + type +
        ", status=" + status +
        "}";
    }
}
