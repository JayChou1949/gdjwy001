package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 处理环节
 * </p>
 *
 * @author wuc
 * @since 2018-11-26
 */
@TableName("TB_FLOW_STEP")
public class FlowStep extends Model<FlowStep> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 环节名
     */
         @TableField("NAME")
    private String name;

        /**
     * 环节排序
     */
         @TableField("SORT")
    private Long sort;

        /**
     * 环节类型  1:审核环节  2: 实施环节
     */
         @TableField("TYPE")
    private Long type;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;

        /**
     * 创建人
     */
         @TableField("CREATOR")
    private String creator;

    /**
     * 流程 id 或 服务 id
     */
    @TableField("FLOW_ID")
         private String flowId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @TableField(exist = false)
    private List<User> userList;

    public String getId() {
        return id;
    }

    public FlowStep setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public FlowStep setName(String name) {
        this.name = name;
        return this;
    }

    public Long getSort() {
        return sort;
    }

    public FlowStep setSort(Long sort) {
        this.sort = sort;
        return this;
    }

    public Long getType() {
        return type;
    }

    public FlowStep setType(Long type) {
        this.type = type;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public FlowStep setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public FlowStep setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FlowStep setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public FlowStep setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FlowStep{" +
        "id=" + id +
        ", name=" + name +
        ", sort=" + sort +
        ", type=" + type +
        ", remark=" + remark +
        ", creator=" + creator +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }

}
