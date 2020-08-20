package com.hirisun.cloud.order.bean.iaas;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hirisun.cloud.order.annotation.ExcelExplain;

/**
 * <p>
 * 弹性云服务器申请信息
 * </p>
 *
 * @author wuc
 * @since 2018-11-30
 */
@TableName("TB_IAAS_TXYFW")
public class IaasTxyfw extends Model<IaasTxyfw> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    /**
     * 应用组件
     */
    @ExcelExplain(head = "应用组件")
    @TableField("COMPONENT")
    private String component;

    /**
     * 规格
     */
    @ExcelExplain(head = "规格名称（核/G）")
    @TableField("SPECIFICATION")
    private String specification;

    /**
     * 存储
     */
    @ExcelExplain(head = "数据盘存储（GB）")
    @TableField("STORAGE")
    private String storage;

    /**
     * 操作系统
     */
    @ExcelExplain(head = "操作系统")
    @TableField("OS")
    private String os;

    /**
     * 网络
     */
    @ExcelExplain(head = "网络")
    @TableField("NET")
    private String net;

    /**
     * 申请数量
     */
    @ExcelExplain(head = "数量")
    @TableField("NUM")
    private Long num;


    /**
     * GPU数目
     */
    @TableField("GPU_NUM")
    @ExcelExplain(head = "GPU数量")
    private Integer gpuNum;

    /**
     * 部署应用
     */
    @ExcelExplain(head = "部署应用")
    @TableField("DEPLOY_APP")
    private String deployApp;

    /**
     * 申请信息 id
     */
    @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;

    /**
     * 应用类型
     */
    @ExcelExplain(head = "应用类型")
    @TableField("APP_TYPE")
    private String appType;

    /**
     * 组件描述
     */
    @ExcelExplain(head = "组件描述")
    @TableField("COMPONENT_DESC")
    private String componentDesc;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public IaasTxyfw setId(String id) {
        this.id = id;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public IaasTxyfw setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getSpecification() {
        return specification;
    }

    public IaasTxyfw setSpecification(String specification) {
        this.specification = specification;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public IaasTxyfw setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getOs() {
        return os;
    }

    public IaasTxyfw setOs(String os) {
        this.os = os;
        return this;
    }

    public String getNet() {
        return net;
    }

    public IaasTxyfw setNet(String net) {
        this.net = net;
        return this;
    }

    public Long getNum() {
        return num;
    }

    public IaasTxyfw setNum(Long num) {
        this.num = num;
        return this;
    }

    public String getDeployApp() {
        return deployApp;
    }

    public IaasTxyfw setDeployApp(String deployApp) {
        this.deployApp = deployApp;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasTxyfw setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasTxyfw setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasTxyfw setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getComponentDesc() {
        return componentDesc;
    }

    public void setComponentDesc(String componentDesc) {
        this.componentDesc = componentDesc;
    }

    public Integer getGpuNum() {
        return gpuNum;
    }

    public void setGpuNum(Integer gpuNum) {
        this.gpuNum = gpuNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    @Override
    public String toString() {
        return "IaasTxyfw{" +
                "id='" + id + '\'' +
                ", component='" + component + '\'' +
                ", specification='" + specification + '\'' +
                ", storage='" + storage + '\'' +
                ", os='" + os + '\'' +
                ", net='" + net + '\'' +
                ", num=" + num +
                ", gpuNum=" + gpuNum +
                ", deployApp='" + deployApp + '\'' +
                ", appInfoId='" + appInfoId + '\'' +
                ", appType='" + appType + '\'' +
                ", componentDesc='" + componentDesc + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
