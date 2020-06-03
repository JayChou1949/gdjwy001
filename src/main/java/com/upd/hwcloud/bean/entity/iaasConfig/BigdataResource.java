package com.upd.hwcloud.bean.entity.iaasConfig;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * IAAS大数据资源
 * </p>
 *
 * @author wuc
 * @since 2019-09-06
 */
@TableName("TB_BIGDATA_RESOURCE")
public class BigdataResource extends Model<BigdataResource> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 集群名称
     */
         @TableField("NAME")
    private String name;

        /**
     * 主要组件
     */
         @TableField("COMPONENT")
    private String component;

        /**
     * 用途
     */
         @TableField("REMARK")
    private String remark;

        /**
     * 数量
     */
         @TableField("NUM")
    private String num;

        /**
     * 排序
     */
         @TableField("SORT")
    private String sort;


    public String getId() {
        return id;
    }

    public BigdataResource setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BigdataResource setName(String name) {
        this.name = name;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public BigdataResource setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public BigdataResource setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getNum() {
        return num;
    }

    public BigdataResource setNum(String num) {
        this.num = num;
        return this;
    }

    public String getSort() {
        return sort;
    }

    public BigdataResource setSort(String sort) {
        this.sort = sort;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BigdataResource{" +
        "id=" + id +
        ", name=" + name +
        ", component=" + component +
        ", remark=" + remark +
        ", num=" + num +
        ", sort=" + sort +
        "}";
    }
}
