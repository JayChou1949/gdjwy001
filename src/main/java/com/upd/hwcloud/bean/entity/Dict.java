package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 字典
 * </p>
 *
 * @author wuc
 * @since 2018-10-18
 */
@TableName("SYS_DICT")
public class Dict extends Model<Dict> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 排序
     */
         @TableField("SORT")
    private Integer sort;

        /**
     * 父级字典
     */
         @TableField("PID")
    private String pid;

    /**
     * 父级字典
     */
    @TableField("P_VALUE")
    private String pValue;

        /**
     * 名
     */
         @TableField("NAME")
    private String name;

        /**
     * 值
     */
         @TableField("VALUE")
    private String value;

        /**
     * 级别
     */
         @TableField("LVL")
    private Integer lvl;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;


    @TableField(exist = false)
    private List<Dict> children = new ArrayList<>();

    public List<Dict> getChildren() {
        return children;
    }

    public void setChildren(List<Dict> children) {
        this.children = children;
    }


    public String getId() {
        return id;
    }

    public Dict setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public Dict setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }

    public String getName() {
        return name;
    }

    public Dict setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Dict setValue(String value) {
        this.value = value;
        return this;
    }

    public Integer getLvl() {
        return lvl;
    }

    public Dict setLvl(Integer lvl) {
        this.lvl = lvl;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Dict setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Dict{" +
        "id=" + id +
        ", sort=" + sort +
        ", pid=" + pid +
        ", pValue=" + pValue +
        ", name=" + name +
        ", value=" + value +
        ", lvl=" + lvl +
        ", remark=" + remark +
        "}";
    }
}
