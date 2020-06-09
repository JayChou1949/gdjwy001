package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 警种IAAS资源总览
 * </p>
 *
 * @author xqp
 * @since 2020-06-04
 */
@TableName("TB_IAAS_POLICE_OVERVIEW")
public class IaasPoliceOverview extends Model<IaasPoliceOverview> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

        /**
     * 警种全名
     */
         @TableField("NAME")
    private String name;

        /**
     * 所属警种
     */
         @TableField("POLICE")
    private String police;

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
     * 大数据：组件服务数
     */
         @TableField("SERVICE_NUM")
    private String serviceNum;

        /**
     * 大数据：VCPU
     */
         @TableField("VCPU")
    private String vcpu;

        /**
     * 大数据：内存
     */
         @TableField("MEMORY")
    private String memory;

        /**
     * 大数据：存储
     */
         @TableField("STORAGE")
    private String storage;

        /**
     * 警种：vCPU总数
     */
         @TableField("VCPU_TOTAL")
    private String vcpuTotal;

        /**
     * 警种：vCPU使用率
     */
         @TableField("VCPU_USAGE")
    private Double vcpuUsage;

        /**
     * 警种：vCPU分配率
     */
         @TableField("VCPU_ALLOCATED_RATIO")
    private Double vcpuAllocatedRatio;

        /**
     * 警种：vCPU已使用数
     */
         @TableField("VCPU_USED_NUM")
    private String vcpuUsedNum;

        /**
     * 警种：vCPU已分配数
     */
         @TableField("VCPU_ALLOCATED_NUM")
    private String vcpuAllocatedNum;

        /**
     * 警种：GPU总数
     */
         @TableField("GPU_TOTAL")
    private String gpuTotal;

        /**
     * 警种：GPU使用率
     */
         @TableField("GPU_USAGE")
    private Double gpuUsage;

        /**
     * 警种：GPU已使用数
     */
         @TableField("GPU_USED_NUM")
    private String gpuUsedNum;

        /**
     * 警种：GPU分配率
     */
         @TableField("GPU_ALLOCATED_RATIO")
    private Double gpuAllocatedRatio;

        /**
     * 警种：GPU已分配数
     */
         @TableField("GPU_ALLOCATED_NUM")
    private String gpuAllocatedNum;

        /**
     * 警种：内存总数
     */
         @TableField("MEMORY_TOTAL")
    private String memoryTotal;

        /**
     * 警种：内存使用率
     */
         @TableField("MEMORY_USAGE")
    private Double memoryUsage;

        /**
     * 警种：内存已使用数
     */
         @TableField("MEMORY_USED_NUM")
    private String memoryUsedNum;

        /**
     * 警种：内存分配率
     */
         @TableField("MEMORY_ALLOCATED_RATIO")
    private Double memoryAllocatedRatio;

        /**
     * 警种：内存已分配数
     */
         @TableField("MEMORY_ALLOCATED_NUM")
    private String memoryAllocatedNum;

        /**
     * 警种：存储总数
     */
         @TableField("DISK_TOTAL")
    private String diskTotal;

        /**
     * 警种：存储使用率
     */
         @TableField("DISK_USAGE")
    private Double diskUsage;

        /**
     * 警种：存储已使用数
     */
         @TableField("DISK_USED_NUM")
    private String diskUsedNum;

        /**
     * 警种：存储分配率
     */
         @TableField("DISK_ALLOCATED_RATIO")
    private Double diskAllocatedRatio;

        /**
     * 警种：存储已分配数
     */
         @TableField("DISK_ALLOCATED_NUM")
    private String diskAllocatedNum;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;


    public String getId() {
        return id;
    }

    public IaasPoliceOverview setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public IaasPoliceOverview setName(String name) {
        this.name = name;
        return this;
    }

    public String getPolice() {
        return police;
    }

    public IaasPoliceOverview setPolice(String police) {
        this.police = police;
        return this;
    }

    public String getElasticCloudServer() {
        return elasticCloudServer;
    }

    public IaasPoliceOverview setElasticCloudServer(String elasticCloudServer) {
        this.elasticCloudServer = elasticCloudServer;
        return this;
    }

    public String getBareMetalServer() {
        return bareMetalServer;
    }

    public IaasPoliceOverview setBareMetalServer(String bareMetalServer) {
        this.bareMetalServer = bareMetalServer;
        return this;
    }

    public String getDesktopCloud() {
        return desktopCloud;
    }

    public IaasPoliceOverview setDesktopCloud(String desktopCloud) {
        this.desktopCloud = desktopCloud;
        return this;
    }

    public String getElasticVolume() {
        return elasticVolume;
    }

    public IaasPoliceOverview setElasticVolume(String elasticVolume) {
        this.elasticVolume = elasticVolume;
        return this;
    }

    public String getElasticScaling() {
        return elasticScaling;
    }

    public IaasPoliceOverview setElasticScaling(String elasticScaling) {
        this.elasticScaling = elasticScaling;
        return this;
    }

    public String getElasticFile() {
        return elasticFile;
    }

    public IaasPoliceOverview setElasticFile(String elasticFile) {
        this.elasticFile = elasticFile;
        return this;
    }

    public String getObjectStorage() {
        return objectStorage;
    }

    public IaasPoliceOverview setObjectStorage(String objectStorage) {
        this.objectStorage = objectStorage;
        return this;
    }

    public String getElasticLoadBalancing() {
        return elasticLoadBalancing;
    }

    public IaasPoliceOverview setElasticLoadBalancing(String elasticLoadBalancing) {
        this.elasticLoadBalancing = elasticLoadBalancing;
        return this;
    }

    public String getServiceNum() {
        return serviceNum;
    }

    public IaasPoliceOverview setServiceNum(String serviceNum) {
        this.serviceNum = serviceNum;
        return this;
    }

    public String getVcpu() {
        return vcpu;
    }

    public IaasPoliceOverview setVcpu(String vcpu) {
        this.vcpu = vcpu;
        return this;
    }

    public String getMemory() {
        return memory;
    }

    public IaasPoliceOverview setMemory(String memory) {
        this.memory = memory;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public IaasPoliceOverview setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getVcpuTotal() {
        return vcpuTotal;
    }

    public IaasPoliceOverview setVcpuTotal(String vcpuTotal) {
        this.vcpuTotal = vcpuTotal;
        return this;
    }

    public Double getVcpuUsage() {
        return vcpuUsage;
    }

    public IaasPoliceOverview setVcpuUsage(Double vcpuUsage) {
        this.vcpuUsage = vcpuUsage;
        return this;
    }

    public Double getVcpuAllocatedRatio() {
        return vcpuAllocatedRatio;
    }

    public IaasPoliceOverview setVcpuAllocatedRatio(Double vcpuAllocatedRatio) {
        this.vcpuAllocatedRatio = vcpuAllocatedRatio;
        return this;
    }

    public String getVcpuUsedNum() {
        return vcpuUsedNum;
    }

    public IaasPoliceOverview setVcpuUsedNum(String vcpuUsedNum) {
        this.vcpuUsedNum = vcpuUsedNum;
        return this;
    }

    public String getVcpuAllocatedNum() {
        return vcpuAllocatedNum;
    }

    public IaasPoliceOverview setVcpuAllocatedNum(String vcpuAllocatedNum) {
        this.vcpuAllocatedNum = vcpuAllocatedNum;
        return this;
    }

    public String getGpuTotal() {
        return gpuTotal;
    }

    public IaasPoliceOverview setGpuTotal(String gpuTotal) {
        this.gpuTotal = gpuTotal;
        return this;
    }

    public Double getGpuUsage() {
        return gpuUsage;
    }

    public IaasPoliceOverview setGpuUsage(Double gpuUsage) {
        this.gpuUsage = gpuUsage;
        return this;
    }

    public String getGpuUsedNum() {
        return gpuUsedNum;
    }

    public IaasPoliceOverview setGpuUsedNum(String gpuUsedNum) {
        this.gpuUsedNum = gpuUsedNum;
        return this;
    }

    public Double getGpuAllocatedRatio() {
        return gpuAllocatedRatio;
    }

    public IaasPoliceOverview setGpuAllocatedRatio(Double gpuAllocatedRatio) {
        this.gpuAllocatedRatio = gpuAllocatedRatio;
        return this;
    }

    public String getGpuAllocatedNum() {
        return gpuAllocatedNum;
    }

    public IaasPoliceOverview setGpuAllocatedNum(String gpuAllocatedNum) {
        this.gpuAllocatedNum = gpuAllocatedNum;
        return this;
    }

    public String getMemoryTotal() {
        return memoryTotal;
    }

    public IaasPoliceOverview setMemoryTotal(String memoryTotal) {
        this.memoryTotal = memoryTotal;
        return this;
    }

    public Double getMemoryUsage() {
        return memoryUsage;
    }

    public IaasPoliceOverview setMemoryUsage(Double memoryUsage) {
        this.memoryUsage = memoryUsage;
        return this;
    }

    public String getMemoryUsedNum() {
        return memoryUsedNum;
    }

    public IaasPoliceOverview setMemoryUsedNum(String memoryUsedNum) {
        this.memoryUsedNum = memoryUsedNum;
        return this;
    }

    public Double getMemoryAllocatedRatio() {
        return memoryAllocatedRatio;
    }

    public IaasPoliceOverview setMemoryAllocatedRatio(Double memoryAllocatedRatio) {
        this.memoryAllocatedRatio = memoryAllocatedRatio;
        return this;
    }

    public String getMemoryAllocatedNum() {
        return memoryAllocatedNum;
    }

    public IaasPoliceOverview setMemoryAllocatedNum(String memoryAllocatedNum) {
        this.memoryAllocatedNum = memoryAllocatedNum;
        return this;
    }

    public String getDiskTotal() {
        return diskTotal;
    }

    public IaasPoliceOverview setDiskTotal(String diskTotal) {
        this.diskTotal = diskTotal;
        return this;
    }

    public Double getDiskUsage() {
        return diskUsage;
    }

    public IaasPoliceOverview setDiskUsage(Double diskUsage) {
        this.diskUsage = diskUsage;
        return this;
    }

    public String getDiskUsedNum() {
        return diskUsedNum;
    }

    public IaasPoliceOverview setDiskUsedNum(String diskUsedNum) {
        this.diskUsedNum = diskUsedNum;
        return this;
    }

    public Double getDiskAllocatedRatio() {
        return diskAllocatedRatio;
    }

    public IaasPoliceOverview setDiskAllocatedRatio(Double diskAllocatedRatio) {
        this.diskAllocatedRatio = diskAllocatedRatio;
        return this;
    }

    public String getDiskAllocatedNum() {
        return diskAllocatedNum;
    }

    public IaasPoliceOverview setDiskAllocatedNum(String diskAllocatedNum) {
        this.diskAllocatedNum = diskAllocatedNum;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public IaasPoliceOverview setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public IaasPoliceOverview setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasPoliceOverview{" +
        "id=" + id +
        ", name=" + name +
        ", police=" + police +
        ", elasticCloudServer=" + elasticCloudServer +
        ", bareMetalServer=" + bareMetalServer +
        ", desktopCloud=" + desktopCloud +
        ", elasticVolume=" + elasticVolume +
        ", elasticScaling=" + elasticScaling +
        ", elasticFile=" + elasticFile +
        ", objectStorage=" + objectStorage +
        ", elasticLoadBalancing=" + elasticLoadBalancing +
        ", serviceNum=" + serviceNum +
        ", vcpu=" + vcpu +
        ", memory=" + memory +
        ", storage=" + storage +
        ", vcpuTotal=" + vcpuTotal +
        ", vcpuUsage=" + vcpuUsage +
        ", vcpuAllocatedRatio=" + vcpuAllocatedRatio +
        ", vcpuUsedNum=" + vcpuUsedNum +
        ", vcpuAllocatedNum=" + vcpuAllocatedNum +
        ", gpuTotal=" + gpuTotal +
        ", gpuUsage=" + gpuUsage +
        ", gpuUsedNum=" + gpuUsedNum +
        ", gpuAllocatedRatio=" + gpuAllocatedRatio +
        ", gpuAllocatedNum=" + gpuAllocatedNum +
        ", memoryTotal=" + memoryTotal +
        ", memoryUsage=" + memoryUsage +
        ", memoryUsedNum=" + memoryUsedNum +
        ", memoryAllocatedRatio=" + memoryAllocatedRatio +
        ", memoryAllocatedNum=" + memoryAllocatedNum +
        ", diskTotal=" + diskTotal +
        ", diskUsage=" + diskUsage +
        ", diskUsedNum=" + diskUsedNum +
        ", diskAllocatedRatio=" + diskAllocatedRatio +
        ", diskAllocatedNum=" + diskAllocatedNum +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
