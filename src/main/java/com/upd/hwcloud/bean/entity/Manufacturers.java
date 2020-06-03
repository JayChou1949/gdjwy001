package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 服务提供商
 * </p>
 *
 * @author wuc
 * @since 2019-05-13
 */
@TableName("TB_MANUFACTURERS")
public class Manufacturers extends Model<Manufacturers> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private String id;

        /**
     * 厂商名称
     */
         @TableField("MANUFACTURER_NAME")
    private String manufacturerName;

        /**
     * 厂商负责人
     */
         @TableField("NAME")
    private String name;

        /**
     * 负责人身份证号
     */
         @TableField("IDCARD")
    private String idcard;

        /**
     * 联系电话
     */
         @TableField("PHONE")
    private String phone;

        /**
     * 厂商代码
     */
         @TableField("CODE")
    private String code;

        /**
     * 联系地址
     */
         @TableField("ADDRESS")
    private String address;

        /**
     * 是否删除 0:使用中，1:已删除
     */
         @TableField("DELETED")
    private String deleted;


    public String getId() {
        return id;
    }

    public Manufacturers setId(String id) {
        this.id = id;
        return this;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public Manufacturers setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
        return this;
    }

    public String getName() {
        return name;
    }

    public Manufacturers setName(String name) {
        this.name = name;
        return this;
    }

    public String getIdcard() {
        return idcard;
    }

    public Manufacturers setIdcard(String idcard) {
        this.idcard = idcard;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Manufacturers setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Manufacturers setCode(String code) {
        this.code = code;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Manufacturers setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getDeleted() {
        return deleted;
    }

    public Manufacturers setDeleted(String deleted) {
        this.deleted = deleted;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Manufacturers{" +
        "id=" + id +
        ", manufacturerName=" + manufacturerName +
        ", name=" + name +
        ", idcard=" + idcard +
        ", phone=" + phone +
        ", code=" + code +
        ", address=" + address +
        ", deleted=" + deleted +
        "}";
    }
}
