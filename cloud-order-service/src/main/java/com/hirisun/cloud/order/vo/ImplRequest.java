package com.hirisun.cloud.order.vo;

import com.hirisun.cloud.model.file.FilesVo;

import java.util.List;
import java.util.Map;

/**
 * @author wuxiaoxing 2020-08-19
 * 服务申请实施信息
 *
 * @param <I> 实施的服务器信息
 */
public class ImplRequest<I> {

    private String applyInfoId;

    private String nodeId;

    private String activityId;

    /**
     * 实施结果 1:通过,其它:驳回
     */
    private String result;

    /**
     * 实施描述
     */
    private String remark;

    /**
     * 实施服务器信息
     */
    private List<Map<String,Object>> implServerList;

    private List<FilesVo> fileList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Map<String, Object>> getImplServerList() {
        return implServerList;
    }

    public void setImplServerList(List<Map<String, Object>> implServerList) {
        this.implServerList = implServerList;
    }

    public List<FilesVo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FilesVo> fileList) {
        this.fileList = fileList;
    }

    public String getApplyInfoId() {
        return applyInfoId;
    }

    public void setApplyInfoId(String applyInfoId) {
        this.applyInfoId = applyInfoId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
