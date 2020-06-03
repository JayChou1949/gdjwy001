package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 服务发布-api-api操作
 * </p>
 *
 * @author wuc
 * @since 2019-10-17
 */
@TableName("TB_API_OPERATION")
public class ApiOperation extends Model<ApiOperation> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @TableField("MASTER_ID")
    private String masterId;

        /**
     * API操作名称
     */
         @TableField("NAME")
    private String name;

        /**
     * 路径
     */
         @TableField("PATH")
    private String path;

        /**
     * 请求方法
     */
         @TableField("METHOD")
    private String method;

        /**
     * 是否编排,1:是 0:否
     */
         @TableField("WITH_ORCHESTRATION")
    private String withOrchestration;

        /**
     * 匹配模式.
         * 精确:NORMAL  前缀:PREFIX
     */
         @TableField("MATCH_MODE")
    private String matchMode;

        /**
     * 后端服务名称
     */
         @TableField("BACKEND_NAME")
    private String backendName;

        /**
     * 后端请求协议.
         * 当前仅支持 HTTP，表示 http 和 https 服务
     */
         @TableField("BACKEND_TYPE")
    private String backendType;

        /**
     * 后端请求路径
     */
         @TableField("BACKEND_PATH")
    private String backendPath;

        /**
     * 后端请求方法
     */
         @TableField("BACKEND_METHOD")
    private String backendMethod;


    public String getId() {
        return id;
    }

    public ApiOperation setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ApiOperation setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ApiOperation setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public ApiOperation setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ApiOperation setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ApiOperation setPath(String path) {
        this.path = path;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public ApiOperation setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getWithOrchestration() {
        return withOrchestration;
    }

    public ApiOperation setWithOrchestration(String withOrchestration) {
        this.withOrchestration = withOrchestration;
        return this;
    }

    public String getMatchMode() {
        return matchMode;
    }

    public ApiOperation setMatchMode(String matchMode) {
        this.matchMode = matchMode;
        return this;
    }

    public String getBackendName() {
        return backendName;
    }

    public ApiOperation setBackendName(String backendName) {
        this.backendName = backendName;
        return this;
    }

    public String getBackendType() {
        return backendType;
    }

    public ApiOperation setBackendType(String backendType) {
        this.backendType = backendType;
        return this;
    }

    public String getBackendPath() {
        return backendPath;
    }

    public ApiOperation setBackendPath(String backendPath) {
        this.backendPath = backendPath;
        return this;
    }

    public String getBackendMethod() {
        return backendMethod;
    }

    public ApiOperation setBackendMethod(String backendMethod) {
        this.backendMethod = backendMethod;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ApiOperation{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", masterId=" + masterId +
        ", name=" + name +
        ", path=" + path +
        ", method=" + method +
        ", withOrchestration=" + withOrchestration +
        ", matchMode=" + matchMode +
        ", backendName=" + backendName +
        ", backendType=" + backendType +
        ", backendPath=" + backendPath +
        ", backendMethod=" + backendMethod +
        "}";
    }
}
