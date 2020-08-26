package com.hirisun.cloud.paas.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 基于虚拟机的DCS分布式缓存
 * </p>
 *
 * @author yyc
 * @since 2020-05-07
 */
@TableName("TB_PAAS_DCS")
public class PaasDcs extends Model<PaasDcs> {

    private static final long serialVersionUID = 1L;

        /**
     * 主键UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 组件名
     */
         @TableField("COMPONENT")
    private String component;

        /**
     * 开源组件版本号
     */
         @TableField("OPEN_SOURCE_VERSION")
    private String openSourceVersion;

        /**
     * 架构类型
     */
         @TableField("ARCHITECTURE")
    private String architecture;

        /**
     * 总内存
     */
         @TableField("TOTAL_MEMORY")
    private String totalMemory;

        /**
     * 单节点规格
     */
         @TableField("SINGLE_NODE_SPECIFICATIONS")
    private String singleNodeSpecifications;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 订单ID
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 购物车ID
     */
         @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;


    public String getId() {
        return id;
    }

    public PaasDcs setId(String id) {
        this.id = id;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public PaasDcs setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getOpenSourceVersion() {
        return openSourceVersion;
    }

    public PaasDcs setOpenSourceVersion(String openSourceVersion) {
        this.openSourceVersion = openSourceVersion;
        return this;
    }

    public String getArchitecture() {
        return architecture;
    }

    public PaasDcs setArchitecture(String architecture) {
        this.architecture = architecture;
        return this;
    }

    public String getTotalMemory() {
        return totalMemory;
    }

    public PaasDcs setTotalMemory(String totalMemory) {
        this.totalMemory = totalMemory;
        return this;
    }

    public String getSingleNodeSpecifications() {
        return singleNodeSpecifications;
    }

    public PaasDcs setSingleNodeSpecifications(String singleNodeSpecifications) {
        this.singleNodeSpecifications = singleNodeSpecifications;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public PaasDcs setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasDcs setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public PaasDcs setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasDcs{" +
        "id=" + id +
        ", component=" + component +
        ", openSourceVersion=" + openSourceVersion +
        ", architecture=" + architecture +
        ", totalMemory=" + totalMemory +
        ", singleNodeSpecifications=" + singleNodeSpecifications +
        ", remark=" + remark +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", appInfoId=" + appInfoId +
        ", shoppingCartId=" + shoppingCartId +
        "}";
    }
}
