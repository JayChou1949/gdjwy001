package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangwy
 * @since 2019-09-09
 */
@TableName("TB_IAAS_OVERVIEW")
public class IaasOverview extends Model<IaasOverview> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    /**
     * 区域名称
     */
    @TableField("AREA")
    private String area;

    /**
     * 阶段
     */
    @TableField("PHASE")
    private Integer phase;

    /**
     * 省厅或地市（省厅-1，地市-0）
     */
    @TableField("CENTRAL")
    private Integer central;

    /**
     * 物理设备：服务器
     */
    @TableField("SERVER")
    private String server;

    /**
     * 物理设备：网络设备
     */
    @TableField("NETWORK_DEVICE")
    private String networkDevice;

    /**
     * 物理设备：存储设备
     */
    @TableField("STORAGE_DEVICE")
    private String storageDevice;

    /**
     * 服务实例：弹性云服务器
     */
    @TableField("ELASTIC_CLOUD_SERVER")
    private String elasticCloudServer;

    /**
     * 服务实例：裸金属服务器
     */
    @TableField("BARE_METAL_SERVER")
    private String bareMetalServer;

    /**
     * 服务实例：桌面云
     */
    @TableField("DESKTOP_CLOUD")
    private String desktopCloud;

    /**
     * 服务实例：云硬盘
     */
    @TableField("ELASTIC_VOLUME")
    private String elasticVolume;

    /**
     * 服务实例：弹性伸缩
     */
    @TableField("ELASTIC_SCALING")
    private String elasticScaling;

    /**
     * 服务实例：弹性文件服务
     */
    @TableField("ELASTIC_FILE")
    private String elasticFile;

    /**
     * 服务实例：对象存储服务
     */
    @TableField("OBJECT_STORAGE")
    private String objectStorage;

    /**
     * 服务实例：弹性负载均衡
     */
    @TableField("ELASTIC_LOAD_BALANCING")
    private String elasticLoadBalancing;

    /**
     * 大数据：集群数
     */
    @TableField("CLUSTERS")
    private String clusters;

    /**
     * 大数据：组件数
     */
    @TableField("MODULE")
    private String module;

    /**
     * 大数据：服务器数量
     */
    @TableField("BD_SERVER")
    private String bdServer;

    /**
     * 大数据：存储总量
     */
    @TableField("BD_TORAGE_TOTAL")
    private String bdTorageTotal;

    /**
     * 警务云：vCPU总数
     */
    @TableField("VCPU_TOTAL")
    private String vcpuTotal;

    /**
     * 警务云：vCPU已使用率
     */
    @TableField("VCPU_USED")
    private Double vcpuUsed;

    /**
     * 警务云：vCPU已使用数
     */
    @TableField(exist = false)
    private Double vcpuUsedNum;

    /**
     * 警务云：vCPU已分配率
     */
    @TableField("VCPU_ALLOCATED")
    private Double vcpuAllocated;

    /**
     * 警务云：vCPU已分配数
     */
    @TableField(exist = false)
    private Double vcpuAllocatedNum;

    /**
     * 警务云：GPU总数
     */
    @TableField("GPU_TOTAL")
    private String gpuTotal;

    /**
     * 警务云：GPU已使用率
     */
    @TableField("GPU_USED")
    private Double gpuUsed;

    /**
     * 警务云：GPU已使用数
     */
    @TableField(exist = false)
    private Double gpuUsedNum;

    /**
     * 警务云：GPU已分配率
     */
    @TableField("GPU_ALLOCATED")
    private Double gpuAllocated;

    /**
     * 警务云：GPU已分配数
     */
    @TableField(exist = false)
    private Double gpuAllocatedNum;

    /**
     * 警务云：内存总数(GB)
     */
    @TableField("MEMORY_TOTAL")
    private String memoryTotal;

    /**
     * 警务云：内存已使用率
     */
    @TableField("MEMORY_USED")
    private Double memoryUsed;

    /**
     * 警务云：内存已使用数
     */
    @TableField(exist = false)
    private Double memoryUsedNum;

    /**
     * 警务云：内存已分配率
     */
    @TableField("MEMORY_ALLOCATED")
    private Double memoryAllocated;

    /**
     * 警务云：内存已分配数
     */
    @TableField(exist = false)
    private Double memoryAllocatedNum;

    /**
     * 警务云：存储总数(TB)
     */
    @TableField("DISK_TOTAL")
    private String diskTotal;

    /**
     * 警务云：存储已使用率
     */
    @TableField("DISK_USED")
    private Double diskUsed;

    /**
     * 警务云：存储已使用数
     */
    @TableField(exist = false)
    private Double diskUsedNum;

    /**
     * 警务云：存储已分配率
     */
    @TableField("DISK_ALLOCATED")
    private Double diskAllocated;

    /**
     * 警务云：存储已分配数
     */
    @TableField(exist = false)
    private Double diskAllocatedNum;

    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "MODIFIED_TIME",fill = FieldFill.UPDATE)
    private LocalDateTime modifiedTime;


    public String getId() {
        return id;
    }

    public IaasOverview setId(String id) {
        this.id = id;
        return this;
    }

    public String getArea() {
        return area;
    }

    public IaasOverview setArea(String area) {
        this.area = area;
        return this;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public Integer getCentral() {
        return central;
    }

    public void setCentral(Integer central) {
        this.central = central;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getNetworkDevice() {
        return networkDevice;
    }

    public void setNetworkDevice(String networkDevice) {
        this.networkDevice = networkDevice;
    }

    public String getStorageDevice() {
        return storageDevice;
    }

    public void setStorageDevice(String storageDevice) {
        this.storageDevice = storageDevice;
    }

    public String getElasticCloudServer() {
        return elasticCloudServer;
    }

    public void setElasticCloudServer(String elasticCloudServer) {
        this.elasticCloudServer = elasticCloudServer;
    }

    public String getBareMetalServer() {
        return bareMetalServer;
    }

    public void setBareMetalServer(String bareMetalServer) {
        this.bareMetalServer = bareMetalServer;
    }

    public String getDesktopCloud() {
        return desktopCloud;
    }

    public void setDesktopCloud(String desktopCloud) {
        this.desktopCloud = desktopCloud;
    }

    public String getElasticVolume() {
        return elasticVolume;
    }

    public void setElasticVolume(String elasticVolume) {
        this.elasticVolume = elasticVolume;
    }

    public String getElasticScaling() {
        return elasticScaling;
    }

    public void setElasticScaling(String elasticScaling) {
        this.elasticScaling = elasticScaling;
    }

    public String getElasticFile() {
        return elasticFile;
    }

    public void setElasticFile(String elasticFile) {
        this.elasticFile = elasticFile;
    }

    public String getObjectStorage() {
        return objectStorage;
    }

    public void setObjectStorage(String objectStorage) {
        this.objectStorage = objectStorage;
    }

    public String getElasticLoadBalancing() {
        return elasticLoadBalancing;
    }

    public void setElasticLoadBalancing(String elasticLoadBalancing) {
        this.elasticLoadBalancing = elasticLoadBalancing;
    }

    public String getClusters() {
        return clusters;
    }

    public void setClusters(String clusters) {
        this.clusters = clusters;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getBdServer() {
        return bdServer;
    }

    public void setBdServer(String bdServer) {
        this.bdServer = bdServer;
    }

    public String getBdTorageTotal() {
        return bdTorageTotal;
    }

    public void setBdTorageTotal(String bdTorageTotal) {
        this.bdTorageTotal = bdTorageTotal;
    }

    public String getVcpuTotal() {
        return vcpuTotal;
    }

    public void setVcpuTotal(String vcpuTotal) {
        this.vcpuTotal = vcpuTotal;
    }

    public Double getVcpuUsed() {
        return vcpuUsed;
    }

    public void setVcpuUsed(Double vcpuUsed) {
        this.vcpuUsed = vcpuUsed;
    }

    public Double getVcpuAllocated() {
        return vcpuAllocated;
    }

    public void setVcpuAllocated(Double vcpuAllocated) {
        this.vcpuAllocated = vcpuAllocated;
    }

    public String getGpuTotal() {
        return gpuTotal;
    }

    public void setGpuTotal(String gpuTotal) {
        this.gpuTotal = gpuTotal;
    }

    public Double getGpuUsed() {
        return gpuUsed;
    }

    public void setGpuUsed(Double gpuUsed) {
        this.gpuUsed = gpuUsed;
    }

    public Double getGpuAllocated() {
        return gpuAllocated;
    }

    public void setGpuAllocated(Double gpuAllocated) {
        this.gpuAllocated = gpuAllocated;
    }

    public String getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(String memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public Double getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Double memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public Double getMemoryAllocated() {
        return memoryAllocated;
    }

    public void setMemoryAllocated(Double memoryAllocated) {
        this.memoryAllocated = memoryAllocated;
    }

    public String getDiskTotal() {
        return diskTotal;
    }

    public void setDiskTotal(String diskTotal) {
        this.diskTotal = diskTotal;
    }

    public Double getDiskUsed() {
        return diskUsed;
    }

    public void setDiskUsed(Double diskUsed) {
        this.diskUsed = diskUsed;
    }

    public Double getDiskAllocated() {
        return diskAllocated;
    }

    public void setDiskAllocated(Double diskAllocated) {
        this.diskAllocated = diskAllocated;
    }

    public Double getVcpuUsedNum() {
        return vcpuUsedNum;
    }

    public void setVcpuUsedNum(Double vcpuUsedNum) {
        this.vcpuUsedNum = vcpuUsedNum;
    }

    public Double getVcpuAllocatedNum() {
        return vcpuAllocatedNum;
    }

    public void setVcpuAllocatedNum(Double vcpuAllocatedNum) {
        this.vcpuAllocatedNum = vcpuAllocatedNum;
    }

    public Double getGpuUsedNum() {
        return gpuUsedNum;
    }

    public void setGpuUsedNum(Double gpuUsedNum) {
        this.gpuUsedNum = gpuUsedNum;
    }

    public Double getGpuAllocatedNum() {
        return gpuAllocatedNum;
    }

    public void setGpuAllocatedNum(Double gpuAllocatedNum) {
        this.gpuAllocatedNum = gpuAllocatedNum;
    }

    public Double getMemoryUsedNum() {
        return memoryUsedNum;
    }

    public void setMemoryUsedNum(Double memoryUsedNum) {
        this.memoryUsedNum = memoryUsedNum;
    }

    public Double getMemoryAllocatedNum() {
        return memoryAllocatedNum;
    }

    public void setMemoryAllocatedNum(Double memoryAllocatedNum) {
        this.memoryAllocatedNum = memoryAllocatedNum;
    }

    public Double getDiskUsedNum() {
        return diskUsedNum;
    }

    public void setDiskUsedNum(Double diskUsedNum) {
        this.diskUsedNum = diskUsedNum;
    }

    public Double getDiskAllocatedNum() {
        return diskAllocatedNum;
    }

    public void setDiskAllocatedNum(Double diskAllocatedNum) {
        this.diskAllocatedNum = diskAllocatedNum;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public IaasOverview setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public IaasOverview setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasOverview{" +
                "id=" + id +
                ", area=" + area +
                ", phase=" + phase +
                ", central=" + central +
                ", server=" + server +
                ", networkDevice=" + networkDevice +
                ", storageDevice=" + storageDevice +
                ", elasticCloudServer=" + elasticCloudServer +
                ", bareMetalServer=" + bareMetalServer +
                ", desktopCloud=" + desktopCloud +
                ", elasticVolume=" + elasticVolume +
                ", elasticScaling=" + elasticScaling +
                ", elasticFile=" + elasticFile +
                ", objectStorage=" + objectStorage +
                ", elasticLoadBalancing=" + elasticLoadBalancing +
                ", clusters=" + clusters +
                ", module=" + module +
                ", bdServer=" + bdServer +
                ", bdTorageTotal=" + bdTorageTotal +
                ", vcpuTotal=" + vcpuTotal +
                ", vcpuUsed=" + vcpuUsed +
                ", vcpuAllocated=" + vcpuAllocated +
                ", gpuTotal=" + gpuTotal +
                ", gpuUsed=" + gpuUsed +
                ", gpuAllocated=" + gpuAllocated +
                ", memoryTotal=" + memoryTotal +
                ", memoryUsed=" + memoryUsed +
                ", memoryAllocated=" + memoryAllocated +
                ", diskTotal=" + diskTotal +
                ", diskUsed=" + diskUsed +
                ", diskAllocated=" + diskAllocated +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                "}";
    }
}
