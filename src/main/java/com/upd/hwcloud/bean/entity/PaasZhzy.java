package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * PAAS租户资源
 * </p>
 *
 * @author xqp
 * @since 2020-06-16
 */
@TableName("TB_PAAS_ZHZY")
public class PaasZhzy extends Model<PaasZhzy> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.INPUT)
    private String id;

        /**
     * 租户名称
     */
         @TableField("TENANT_NAME")
    private String tenantName;

        /**
     * 应用名称
     */
         @TableField("APPLY_NAME")
    private String applyName;

        /**
     * 地市
     */
         @TableField("AREA")
    private String area;

        /**
     * 警种
     */
         @TableField("POLICE")
    private String police;

        /**
     * 国家专项
     */
         @TableField("NATIONAL_SPECIAL_PROJECT")
    private String nationalSpecialProject;

        /**
     * 集群名称(英文)
     */
         @TableField("SUPER_CLUSTER_EN")
    private String superClusterEn;

        /**
     * 集群名称(中文)
     */
         @TableField("SUPER_CLUSTER_CN")
    private String superClusterCn;

        /**
     * 集群类型
     */
         @TableField("TYPE_SITE")
    private String typeSite;

        /**
     * 入库时间
     */
         @TableField("ODS_TIME")
    private Double odsTime;

        /**
     * 分配给租户的CPU总配额(vCores)
     */
         @TableField("CPU_TOTAL")
    private Double cpuTotal;

        /**
     * 租户的CPU使用率(%)
     */
         @TableField("CPU_USAGE")
    private Double cpuUsage;

        /**
     * 租户已使用的CPU(vCores)
     */
         @TableField("CPU_USED")
    private Double cpuUsed;

        /**
     * 分配给租户的内存总配额(MB)
     */
         @TableField("MEMORY_TOTAL")
    private Double memoryTotal;

        /**
     * 租户的内存使用率(%)
     */
         @TableField("MEMORY_USAGE")
    private Double memoryUsage;

        /**
     * 租户已使用的内存(MB)
     */
         @TableField("MEMORY_USED")
    private Double memoryUsed;

        /**
     * Elasticsearch存储空间名称
     */
         @TableField("ELASTICSEARCH_STORAGE_SPACE")
    private String elasticsearchStorageSpace;

        /**
     * es在ods库的表字段名
     */
         @TableField("ELASTICSEARCH_MONITOR_OBJECT")
    private String elasticsearchMonitorObject;

        /**
     * 分配给es的cpu总核数(vcores)
     */
         @TableField("ELASTICSEARCH_CPU_TOTAL")
    private Double elasticsearchCpuTotal;

        /**
     * es的cpu使用率(%)
     */
         @TableField("ELASTICSEARCH_CPU_USAGE")
    private Double elasticsearchCpuUsage;

    /**
     * es的cpu使用量
     */
    @TableField(exist = false)
         private Double elasticsearchCpuUsed;

        /**
     * 分配给es的内存大小(MB)
     */
         @TableField("ELASTICSEARCH_MEMORY_TOTAL")
    private Double elasticsearchMemoryTotal;

        /**
     * es的已使用内存(MB)
     */
        @TableField("ELASTICSEARCH_MEMORY_USED")
    private Double elasticsearchMemoryUsed;

    /**
     * es的内存使用率
     */
    @TableField(exist = false)
    private Double elasticsearchMemoryUsage;

        /**
     * es可使用磁盘空间(KB)
     */
         @TableField("ELASTICSEARCH_DISK_AVAILABLE")
    private Double elasticsearchDiskAvailable;

        /**
     * es已使用磁盘空间(KB)
     */
         @TableField("ELASTICSEARCH_DISK_USED")
    private Double elasticsearchDiskUsed;

    /**
     * es的磁盘使用率
     */
    @TableField(exist = false)
    private Double elasticsearchDiskUsage;

        /**
     * 租户对应的redis集群名称
     */
         @TableField("REDIS_CLUSTER_NAME")
    private String redisClusterName;

        /**
     * redis分配的总的内存大小(G)
     */
         @TableField("REDIS_MEMORY_TOTAL")
    private Double redisMemoryTotal;

        /**
     * redis的内存使用率(%)
     */
         @TableField("REDIS_MEMORY_USAGE")
    private Double redisMemoryUsage;

    /**
     * redis的内存使用量
     */
    @TableField(exist = false)
    private Double redisMemoryUsed;

        /**
     * Kafka的存储空间名称
     */
         @TableField("KAFKA_STORAGE_SPACE_NAME")
    private String kafkaStorageSpaceName;

    /**
     * elastricsearch是否为共享(1:共享 0：非共享)
     */
    @TableField("ELASTICSEARCH_IS_SHARE")
    private String elastricsearchIsShare;

    /**
     * elastricsearch对应的主机数
     */
    @TableField("ELASTICSEARCH_HOSTS_NUMBER")
    private String elasticsearchHostsNumber;

    @TableField("STORAGE_TOTAL")
    private Double storageTotal;

    @TableField("STORAGE_USAGE")
    private Double storageUsage;

    @TableField("STORAGE_USED")
    private Double storageUsed;


    public String getId() {
        return id;
    }

    public PaasZhzy setId(String id) {
        this.id = id;
        return this;
    }

    public String getTenantName() {
        return tenantName;
    }

    public PaasZhzy setTenantName(String tenantName) {
        this.tenantName = tenantName;
        return this;
    }

    public String getApplyName() {
        return applyName;
    }

    public PaasZhzy setApplyName(String applyName) {
        this.applyName = applyName;
        return this;
    }

    public String getArea() {
        return area;
    }

    public PaasZhzy setArea(String area) {
        this.area = area;
        return this;
    }

    public String getPolice() {
        return police;
    }

    public PaasZhzy setPolice(String police) {
        this.police = police;
        return this;
    }

    public String getNationalSpecialProject() {
        return nationalSpecialProject;
    }

    public PaasZhzy setNationalSpecialProject(String nationalSpecialProject) {
        this.nationalSpecialProject = nationalSpecialProject;
        return this;
    }

    public String getSuperClusterEn() {
        return superClusterEn;
    }

    public PaasZhzy setSuperClusterEn(String superClusterEn) {
        this.superClusterEn = superClusterEn;
        return this;
    }

    public String getSuperClusterCn() {
        return superClusterCn;
    }

    public PaasZhzy setSuperClusterCn(String superClusterCn) {
        this.superClusterCn = superClusterCn;
        return this;
    }

    public String getTypeSite() {
        return typeSite;
    }

    public PaasZhzy setTypeSite(String typeSite) {
        this.typeSite = typeSite;
        return this;
    }

    public Double getOdsTime() {
        return odsTime;
    }

    public PaasZhzy setOdsTime(Double odsTime) {
        this.odsTime = odsTime;
        return this;
    }

    public Double getCpuTotal() {
        return cpuTotal;
    }

    public PaasZhzy setCpuTotal(Double cpuTotal) {
        this.cpuTotal = cpuTotal;
        return this;
    }

    public Double getCpuUsage() {
        return cpuUsage;
    }

    public PaasZhzy setCpuUsage(Double cpuUsage) {
        this.cpuUsage = cpuUsage;
        return this;
    }

    public Double getCpuUsed() {
        return cpuUsed;
    }

    public PaasZhzy setCpuUsed(Double cpuUsed) {
        this.cpuUsed = cpuUsed;
        return this;
    }

    public Double getMemoryTotal() {
        return memoryTotal;
    }

    public PaasZhzy setMemoryTotal(Double memoryTotal) {
        this.memoryTotal = memoryTotal;
        return this;
    }

    public Double getMemoryUsage() {
        return memoryUsage;
    }

    public PaasZhzy setMemoryUsage(Double memoryUsage) {
        this.memoryUsage = memoryUsage;
        return this;
    }

    public Double getMemoryUsed() {
        return memoryUsed;
    }

    public PaasZhzy setMemoryUsed(Double memoryUsed) {
        this.memoryUsed = memoryUsed;
        return this;
    }

    public String getElasticsearchStorageSpace() {
        return elasticsearchStorageSpace;
    }

    public PaasZhzy setElasticsearchStorageSpace(String elasticsearchStorageSpace) {
        this.elasticsearchStorageSpace = elasticsearchStorageSpace;
        return this;
    }

    public String getElasticsearchMonitorObject() {
        return elasticsearchMonitorObject;
    }

    public PaasZhzy setElasticsearchMonitorObject(String elasticsearchMonitorObject) {
        this.elasticsearchMonitorObject = elasticsearchMonitorObject;
        return this;
    }

    public Double getElasticsearchCpuTotal() {
        return elasticsearchCpuTotal;
    }

    public PaasZhzy setElasticsearchCpuTotal(Double elasticsearchCpuTotal) {
        this.elasticsearchCpuTotal = elasticsearchCpuTotal;
        return this;
    }

    public Double getElasticsearchCpuUsage() {
        return elasticsearchCpuUsage;
    }

    public PaasZhzy setElasticsearchCpuUsage(Double elasticsearchCpuUsage) {
        this.elasticsearchCpuUsage = elasticsearchCpuUsage;
        return this;
    }

    public Double getElasticsearchMemoryTotal() {
        return elasticsearchMemoryTotal;
    }

    public PaasZhzy setElasticsearchMemoryTotal(Double elasticsearchMemoryTotal) {
        this.elasticsearchMemoryTotal = elasticsearchMemoryTotal;
        return this;
    }

    public Double getElasticsearchMemoryUsed() {
        return elasticsearchMemoryUsed;
    }

    public PaasZhzy setElasticsearchMemoryUsed(Double elasticsearchMemoryUsage) {
        this.elasticsearchMemoryUsed = elasticsearchMemoryUsage;
        return this;
    }

    public Double getElasticsearchDiskAvailable() {
        return elasticsearchDiskAvailable;
    }

    public PaasZhzy setElasticsearchDiskAvailable(Double elasticsearchDiskAvailable) {
        this.elasticsearchDiskAvailable = elasticsearchDiskAvailable;
        return this;
    }

    public Double getElasticsearchDiskUsed() {
        return elasticsearchDiskUsed;
    }

    public PaasZhzy setElasticsearchDiskUsed(Double elasticsearchDiskUsed) {
        this.elasticsearchDiskUsed = elasticsearchDiskUsed;
        return this;
    }

    public String getRedisClusterName() {
        return redisClusterName;
    }

    public PaasZhzy setRedisClusterName(String redisClusterName) {
        this.redisClusterName = redisClusterName;
        return this;
    }

    public Double getRedisMemoryTotal() {
        return redisMemoryTotal;
    }

    public PaasZhzy setRedisMemoryTotal(Double redisMemoryTotal) {
        this.redisMemoryTotal = redisMemoryTotal;
        return this;
    }

    public Double getRedisMemoryUsage() {
        return redisMemoryUsage;
    }

    public PaasZhzy setRedisMemoryUsage(Double redisMemoryUsage) {
        this.redisMemoryUsage = redisMemoryUsage;
        return this;
    }

    public String getKafkaStorageSpaceName() {
        return kafkaStorageSpaceName;
    }

    public PaasZhzy setKafkaStorageSpaceName(String kafkaStorageSpaceName) {
        this.kafkaStorageSpaceName = kafkaStorageSpaceName;
        return this;
    }

    public Double getElasticsearchCpuUsed() {
        return elasticsearchCpuUsed;
    }

    public void setElasticsearchCpuUsed(Double elasticsearchCpuUsed) {
        this.elasticsearchCpuUsed = elasticsearchCpuUsed;
    }

    public Double getElasticsearchMemoryUsage() {
        return elasticsearchMemoryUsage;
    }

    public void setElasticsearchMemoryUsage(Double elasticsearchMemoryUsage) {
        this.elasticsearchMemoryUsage = elasticsearchMemoryUsage;
    }

    public Double getElasticsearchDiskUsage() {
        return elasticsearchDiskUsage;
    }

    public void setElasticsearchDiskUsage(Double elasticsearchDiskUsage) {
        this.elasticsearchDiskUsage = elasticsearchDiskUsage;
    }

    public Double getRedisMemoryUsed() {
        return redisMemoryUsed;
    }

    public void setRedisMemoryUsed(Double redisMemoryUsed) {
        this.redisMemoryUsed = redisMemoryUsed;
    }

    public String getElastricsearchIsShare() {
        return elastricsearchIsShare;
    }

    public void setElastricsearchIsShare(String elastricsearchIsShare) {
        this.elastricsearchIsShare = elastricsearchIsShare;
    }

    public String getElasticsearchHostsNumber() {
        return elasticsearchHostsNumber;
    }

    public void setElasticsearchHostsNumber(String elasticsearchHostsNumber) {
        this.elasticsearchHostsNumber = elasticsearchHostsNumber;
    }

    public Double getStorageTotal() {
        return storageTotal;
    }

    public void setStorageTotal(Double storageTotal) {
        this.storageTotal = storageTotal;
    }

    public Double getStorageUsage() {
        return storageUsage;
    }

    public void setStorageUsage(Double storageUsage) {
        this.storageUsage = storageUsage;
    }

    public Double getStorageUsed() {
        return storageUsed;
    }

    public void setStorageUsed(Double storageUsed) {
        this.storageUsed = storageUsed;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasZhzy{" +
        "id=" + id +
        ", tenantName=" + tenantName +
        ", applyName=" + applyName +
        ", area=" + area +
        ", police=" + police +
        ", nationalSpecialProject=" + nationalSpecialProject +
        ", superClusterEn=" + superClusterEn +
        ", superClusterCn=" + superClusterCn +
        ", typeSite=" + typeSite +
        ", odsTime=" + odsTime +
        ", cpuTotal=" + cpuTotal +
        ", cpuUsage=" + cpuUsage +
        ", cpuUsed=" + cpuUsed +
        ", memoryTotal=" + memoryTotal +
        ", memoryUsage=" + memoryUsage +
        ", memoryUsed=" + memoryUsed +
        ", elasticsearchStorageSpace=" + elasticsearchStorageSpace +
        ", elasticsearchMonitorObject=" + elasticsearchMonitorObject +
        ", elasticsearchCpuTotal=" + elasticsearchCpuTotal +
        ", elasticsearchCpuUsage=" + elasticsearchCpuUsage +
        ", elasticsearchMemoryTotal=" + elasticsearchMemoryTotal +
        ", elasticsearchMemoryUsed=" + elasticsearchMemoryUsed +
        ", elasticsearchDiskAvailable=" + elasticsearchDiskAvailable +
        ", elasticsearchDiskUsed=" + elasticsearchDiskUsed +
        ", redisClusterName=" + redisClusterName +
        ", redisMemoryTotal=" + redisMemoryTotal +
        ", redisMemoryUsage=" + redisMemoryUsage +
        ", kafkaStorageSpaceName=" + kafkaStorageSpaceName +
        "}";
    }
}
