package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * GAUSSDB 申请信息
 * </p>
 *
 * @author wangbao
 * @since 2019-10-22
 */
@TableName("TB_PASS_GAUSSDB_INFO")
public class PassGaussdbInfo extends Model<PassGaussdbInfo> {

    private static final long serialVersionUID = 1L;

        /**
     * 主键
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 数据库类型
     */
         @TableField("TYPE")
    private String type;

        /**
     * 应用主机地址
     */
         @TableField("IP")
    private String ip;

        /**
     * 数据库英文名
     */
         @TableField("ENGLISH_NAME")
    private String englishName;

        /**
     * 申请容量大小(G)
     */
         @TableField("CAPCITY")
    private String capcity;

        /**
     * 连接驱动
     */
         @TableField("DRIVER")
    private String driver;

        /**
     * 申请原因(用途)
     */
         @TableField("APPLICATION")
    private String application;

        /**
     * 备注
     */
         @TableField("NOTE")
    private String note;

    @TableField(value="CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value="MODIFIED_TIME",fill =FieldFill.INSERT_UPDATE )
    private Date modifiedTime;

        /**
     * 申请信息id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;


    @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;

    /**
     * 数据库账户信息
     */
    @TableField(exist = false)
    private List<PassGaussdbAccountInfo> accountDBList;

    public String getId() {
        return id;
    }

    public PassGaussdbInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public PassGaussdbInfo setType(String type) {
        this.type = type;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public PassGaussdbInfo setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getEnglishName() {
        return englishName;
    }

    public PassGaussdbInfo setEnglishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public String getCapcity() {
        return capcity;
    }

    public PassGaussdbInfo setCapcity(String capcity) {
        this.capcity = capcity;
        return this;
    }

    public String getDriver() {
        return driver;
    }

    public PassGaussdbInfo setDriver(String driver) {
        this.driver = driver;
        return this;
    }

    public String getApplication() {
        return application;
    }

    public PassGaussdbInfo setApplication(String application) {
        this.application = application;
        return this;
    }

    public String getNote() {
        return note;
    }

    public PassGaussdbInfo setNote(String note) {
        this.note = note;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PassGaussdbInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PassGaussdbInfo setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PassGaussdbInfo setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public List<PassGaussdbAccountInfo> getAccountDBList() {
        return accountDBList;
    }

    public void setAccountDBList(List<PassGaussdbAccountInfo> accountDBList) {
        this.accountDBList = accountDBList;
    }
    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "PassGaussdbInfo{" +
        "id=" + id +
        ", type=" + type +
        ", ip=" + ip +
        ", englishName=" + englishName +
        ", capcity=" + capcity +
        ", driver=" + driver +
        ", application=" + application +
        ", note=" + note +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", appInfoId=" + appInfoId +
        "}";
    }


}
