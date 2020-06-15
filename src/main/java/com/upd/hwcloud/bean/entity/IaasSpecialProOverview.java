package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 国家专项IaaS数据总览
 * </p>
 *
 * @author xqp
 * @since 2020-06-14
 */
@TableName("TB_IAAS_SPECIAL_PRO_OVERVIEW")
public class IaasSpecialProOverview extends Model<IaasSpecialProOverview> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

        /**
     * 名称
     */
         @TableField("NAME")
    private String name;

        /**
     * 所属国家专项
     */
         @TableField("NATIONAL_SPECIAL_PROJECT")
    private String nationalSpecialProject;

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
     * 服务实例：云硬盘（GB）
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
     * 大数据：VCPU（核）
     */
         @TableField("VCPU")
    private String vcpu;

        /**
     * 大数据：内存（GB）
     */
         @TableField("MEMORY")
    private String memory;

        /**
     * 大数据：存储（TB）
     */
         @TableField("STORAGE")
    private String storage;

        /**
     * 国家专项：vCPU总数（核）
     */
         @TableField("VCPU_TOTAL")
    private String vcpuTotal;

        /**
     * 国家专项：vCPU使用率
     */
         @TableField("VCPU_USAGE")
    private String vcpuUsage;

        /**
     * 国家专项：vCPU已使用数
     */
         @TableField("VCPU_USED_NUM")
    private String vcpuUsedNum;

        /**
     * 国家专项：vCPU分配率
     */
         @TableField("VCPU_ALLOCATED_RATIO")
    private String vcpuAllocatedRatio;

        /**
     * 国家专项：vCPU已分配数
     */
         @TableField("VCPU_ALLOCATED_NUM")
    private String vcpuAllocatedNum;

        /**
     * 国家专项：GPU总数
     */
         @TableField("GPU_TOTAL")
    private String gpuTotal;

        /**
     * 国家专项：GPU使用率
     */
         @TableField("GPU_USAGE")
    private String gpuUsage;

        /**
     * 国家专项：GPU已使用数
     */
         @TableField("GPU_USED_NUM")
    private String gpuUsedNum;

        /**
     * 国家专项：GPU分配率
     */
         @TableField("GPU_ALLOCATED_RATIO")
    private String gpuAllocatedRatio;

        /**
     * 国家专项：GPU已分配数
     */
         @TableField("GPU_ALLOCATED_NUM")
    private String gpuAllocatedNum;

        /**
     * 国家专项：内存总数（GB）
     */
         @TableField("MEMORY_TOTAL")
    private String memoryTotal;

        /**
     * 国家专项：内存使用率
     */
         @TableField("MEMORY_USAGE")
    private String memoryUsage;

        /**
     * 国家专项：内存已使用数
     */
         @TableField("MEMORY_USED_NUM")
    private String memoryUsedNum;

        /**
     * 国家专项：内存分配率
     */
         @TableField("MEMORY_ALLOCATED_RATIO")
    private String memoryAllocatedRatio;

        /**
     * 国家专项：内存已分配数
     */
         @TableField("MEMORY_ALLOCATED_NUM")
    private String memoryAllocatedNum;

        /**
     * 国家专项：存储总数（GB）
     */
         @TableField("DISK_TOTAL")
    private String diskTotal;

        /**
     * 国家专项：存储使用率
     */
         @TableField("DISK_USAGE")
    private String diskUsage;

        /**
     * 国家专项：存储已使用数
     */
         @TableField("DISK_USED_NUM")
    private String diskUsedNum;

        /**
     * 国家专项：存储分配率
     */
         @TableField("DISK_ALLOCATED_RATIO")
    private String diskAllocatedRatio;

        /**
     * 国家专项：存储已分配数
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

    public IaasSpecialProOverview setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public IaasSpecialProOverview setName(String name) {
        this.name = name;
        return this;
    }

    public String getNationalSpecialProject() {
        return nationalSpecialProject;
    }

    public IaasSpecialProOverview setNationalSpecialProject(String nationalSpecialProject) {
        this.nationalSpecialProject = nationalSpecialProject;
        return this;
    }

    public String getElasticCloudServer() {
        return elasticCloudServer;
    }

    public IaasSpecialProOverview setElasticCloudServer(String elasticCloudServer) {
        this.elasticCloudServer = elasticCloudServer;
        return this;
    }

    public String getBareMetalServer() {
        return bareMetalServer;
    }

    public IaasSpecialProOverview setBareMetalServer(String bareMetalServer) {
        this.bareMetalServer = bareMetalServer;
        return this;
    }

    public String getDesktopCloud() {
        return desktopCloud;
    }

    public IaasSpecialProOverview setDesktopCloud(String desktopCloud) {
        this.desktopCloud = desktopCloud;
        return this;
    }

    public String getElasticVolume() {
        return elasticVolume;
    }

    public IaasSpecialProOverview setElasticVolume(String elasticVolume) {
        this.elasticVolume = elasticVolume;
        return this;
    }

    public String getElasticScaling() {
        return elasticScaling;
    }

    public IaasSpecialProOverview setElasticScaling(String elasticScaling) {
        this.elasticScaling = elasticScaling;
        return this;
    }

    public String getElasticFile() {
        return elasticFile;
    }

    public IaasSpecialProOverview setElasticFile(String elasticFile) {
        this.elasticFile = elasticFile;
        return this;
    }

    public String getObjectStorage() {
        return objectStorage;
    }

    public IaasSpecialProOverview setObjectStorage(String objectStorage) {
        this.objectStorage = objectStorage;
        return this;
    }

    public String getElasticLoadBalancing() {
        return elasticLoadBalancing;
    }

    public IaasSpecialProOverview setElasticLoadBalancing(String elasticLoadBalancing) {
        this.elasticLoadBalancing = elasticLoadBalancing;
        return this;
    }

    public String getServiceNum() {
        return serviceNum;
    }

    public IaasSpecialProOverview setServiceNum(String serviceNum) {
        this.serviceNum = serviceNum;
        return this;
    }

    public String getVcpu() {
        return vcpu;
    }

    public IaasSpecialProOverview setVcpu(String vcpu) {
        this.vcpu = vcpu;
        return this;
    }

    public String getMemory() {
        return memory;
    }

    public IaasSpecialProOverview setMemory(String memory) {
        this.memory = memory;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public IaasSpecialProOverview setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getVcpuTotal() {
        return vcpuTotal;
    }

    public IaasSpecialProOverview setVcpuTotal(String vcpuTotal) {
        this.vcpuTotal = vcpuTotal;
        return this;
    }

    public String getVcpuUsage() {
        return vcpuUsage;
    }

    public IaasSpecialProOverview setVcpuUsage(String vcpuUsage) {
        this.vcpuUsage = vcpuUsage;
        return this;
    }

    public String getVcpuUsedNum() {
        return vcpuUsedNum;
    }

    public IaasSpecialProOverview setVcpuUsedNum(String vcpuUsedNum) {
        this.vcpuUsedNum = vcpuUsedNum;
        return this;
    }

    public String getVcpuAllocatedRatio() {
        return vcpuAllocatedRatio;
    }

    public IaasSpecialProOverview setVcpuAllocatedRatio(String vcpuAllocatedRatio) {
        this.vcpuAllocatedRatio = vcpuAllocatedRatio;
        return this;
    }

    public String getVcpuAllocatedNum() {
        return vcpuAllocatedNum;
    }

    public IaasSpecialProOverview setVcpuAllocatedNum(String vcpuAllocatedNum) {
        this.vcpuAllocatedNum = vcpuAllocatedNum;
        return this;
    }

    public String getGpuTotal() {
        return gpuTotal;
    }

    public IaasSpecialProOverview setGpuTotal(String gpuTotal) {
        this.gpuTotal = gpuTotal;
        return this;
    }

    public String getGpuUsage() {
        return gpuUsage;
    }

    public IaasSpecialProOverview setGpuUsage(String gpuUsage) {
        this.gpuUsage = gpuUsage;
        return this;
    }

    public String getGpuUsedNum() {
        return gpuUsedNum;
    }

    public IaasSpecialProOverview setGpuUsedNum(String gpuUsedNum) {
        this.gpuUsedNum = gpuUsedNum;
        return this;
    }

    public String getGpuAllocatedRatio() {
        return gpuAllocatedRatio;
    }

    public IaasSpecialProOverview setGpuAllocatedRatio(String gpuAllocatedRatio) {
        this.gpuAllocatedRatio = gpuAllocatedRatio;
        return this;
    }

    public String getGpuAllocatedNum() {
        return gpuAllocatedNum;
    }

    public IaasSpecialProOverview setGpuAllocatedNum(String gpuAllocatedNum) {
        this.gpuAllocatedNum = gpuAllocatedNum;
        return this;
    }

    public String getMemoryTotal() {
        return memoryTotal;
    }

    public IaasSpecialProOverview setMemoryTotal(String memoryTotal) {
        this.memoryTotal = memoryTotal;
        return this;
    }

    public String getMemoryUsage() {
        return memoryUsage;
    }

    public IaasSpecialProOverview setMemoryUsage(String memoryUsage) {
        this.memoryUsage = memoryUsage;
        return this;
    }

    public String getMemoryUsedNum() {
        return memoryUsedNum;
    }

    public IaasSpecialProOverview setMemoryUsedNum(String memoryUsedNum) {
        this.memoryUsedNum = memoryUsedNum;
        return this;
    }

    public String getMemoryAllocatedRatio() {
        return memoryAllocatedRatio;
    }

    public IaasSpecialProOverview setMemoryAllocatedRatio(String memoryAllocatedRatio) {
        this.memoryAllocatedRatio = memoryAllocatedRatio;
        return this;
    }

    public String getMemoryAllocatedNum() {
        return memoryAllocatedNum;
    }

    public IaasSpecialProOverview setMemoryAllocatedNum(String memoryAllocatedNum) {
        this.memoryAllocatedNum = memoryAllocatedNum;
        return this;
    }

    public String getDiskTotal() {
        return diskTotal;
    }

    public IaasSpecialProOverview setDiskTotal(String diskTotal) {
        this.diskTotal = diskTotal;
        return this;
    }

    public String getDiskUsage() {
        return diskUsage;
    }

    public IaasSpecialProOverview setDiskUsage(String diskUsage) {
        this.diskUsage = diskUsage;
        return this;
    }

    public String getDiskUsedNum() {
        return diskUsedNum;
    }

    public IaasSpecialProOverview setDiskUsedNum(String diskUsedNum) {
        this.diskUsedNum = diskUsedNum;
        return this;
    }

    public String getDiskAllocatedRatio() {
        return diskAllocatedRatio;
    }

    public IaasSpecialProOverview setDiskAllocatedRatio(String diskAllocatedRatio) {
        this.diskAllocatedRatio = diskAllocatedRatio;
        return this;
    }

    public String getDiskAllocatedNum() {
        return diskAllocatedNum;
    }

    public IaasSpecialProOverview setDiskAllocatedNum(String diskAllocatedNum) {
        this.diskAllocatedNum = diskAllocatedNum;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public IaasSpecialProOverview setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public IaasSpecialProOverview setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasSpecialProOverview{" +
        "id=" + id +
        ", name=" + name +
        ", nationalSpecialProject=" + nationalSpecialProject +
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
        ", vcpuUsedNum=" + vcpuUsedNum +
        ", vcpuAllocatedRatio=" + vcpuAllocatedRatio +
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
