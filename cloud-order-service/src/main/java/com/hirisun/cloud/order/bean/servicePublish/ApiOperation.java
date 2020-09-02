package com.hirisun.cloud.order.bean.servicePublish;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 服务发布-api-api操作
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@TableName("TB_API_OPERATION")
@ApiModel(value="ApiOperation对象", description="服务发布-api-api操作")
public class ApiOperation implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("MODIFIED_TIME")
    private LocalDateTime modifiedTime;

    @TableField("MASTER_ID")
    private String masterId;

    @ApiModelProperty(value = "API操作名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "路径")
    @TableField("PATH")
    private String path;

    @ApiModelProperty(value = "请求方法")
    @TableField("METHOD")
    private String method;

    @ApiModelProperty(value = "是否编排,1:是 0:否")
    @TableField("WITH_ORCHESTRATION")
    private String withOrchestration;

    @ApiModelProperty(value = "匹配模式,精确:NORMAL  前缀:PREFIX")
    @TableField("MATCH_MODE")
    private String matchMode;

    @ApiModelProperty(value = "后端服务名称")
    @TableField("BACKEND_NAME")
    private String backendName;

    @ApiModelProperty(value = "后端请求协议,当前仅支持 HTTP")
    @TableField("BACKEND_TYPE")
    private String backendType;

    @ApiModelProperty(value = "后端请求路径")
    @TableField("BACKEND_PATH")
    private String backendPath;

    @ApiModelProperty(value = "后端请求方法")
    @TableField("BACKEND_METHOD")
    private String backendMethod;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getWithOrchestration() {
        return withOrchestration;
    }

    public void setWithOrchestration(String withOrchestration) {
        this.withOrchestration = withOrchestration;
    }

    public String getMatchMode() {
        return matchMode;
    }

    public void setMatchMode(String matchMode) {
        this.matchMode = matchMode;
    }

    public String getBackendName() {
        return backendName;
    }

    public void setBackendName(String backendName) {
        this.backendName = backendName;
    }

    public String getBackendType() {
        return backendType;
    }

    public void setBackendType(String backendType) {
        this.backendType = backendType;
    }

    public String getBackendPath() {
        return backendPath;
    }

    public void setBackendPath(String backendPath) {
        this.backendPath = backendPath;
    }

    public String getBackendMethod() {
        return backendMethod;
    }

    public void setBackendMethod(String backendMethod) {
        this.backendMethod = backendMethod;
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
