package com.upd.hwcloud.bean.entity.iaasConfig;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 资源分配情况
 * </p>
 *
 * @author huru
 * @since 2018-11-17
 */
@TableName("TB_RESOURCE")
public class Resource extends Model<Resource> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    /**
     * 虚拟机数量
     */
    @TableField("VM_NUM")
    private String vmNum;

    /**
     * 排序
     */
    @TableField("SORT")
    private Long sort;

    /**
     * 名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 1.警种 2.地区
     */
    @TableField("TYPE")
    private String type;

    @TableField("BIG_TYPE")
    private String bigType;


    public String getId() {
        return id;
    }

    public Resource setId(String id) {
        this.id = id;
        return this;
    }

    public String getVmNum() {
        return vmNum;
    }

    public Resource setVmNum(String vmNum) {
        this.vmNum = vmNum;
        return this;
    }

    public Long getSort() {
        return sort;
    }

    public Resource setSort(Long sort) {
        this.sort = sort;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBigType() {
        return bigType;
    }

    public void setBigType(String bigType) {
        this.bigType = bigType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", vmNum=" + vmNum +
                ", sort=" + sort +
                ", name=" + name +
                "}";
    }
}
