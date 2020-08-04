package com.hirisun.cloud.system.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.hirisun.cloud.model.common.Tree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 字典
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-29
 */
@TableName("T_SYS_DICT")
@ApiModel(value="SysDict对象", description="字典")
public class SysDict extends Tree<SysDict> implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "排序")
    @TableField("SORT")
    private Integer sort;

    @ApiModelProperty(value = "父级字典")
    @TableField("PID")
    private String pid;

    @ApiModelProperty(value = "名")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "值")
    @TableField("VALUE")
    private String value;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "父级值")
    @TableField("P_VALUE")
    private String pValue;

    @TableField(exist = false)
    private List<SysDict> children;

    public List<SysDict> getChildren() {
        return children;
    }

    public void setChildren(List<SysDict> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }

    @Override
    public String toString() {
        return "SysDict{" +
        "id=" + id +
        ", sort=" + sort +
        ", pid=" + pid +
        ", name=" + name +
        ", value=" + value +
        ", remark=" + remark +
        ", pValue=" + pValue +
        "}";
    }
}
