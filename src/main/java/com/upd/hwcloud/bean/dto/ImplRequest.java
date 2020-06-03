package com.upd.hwcloud.bean.dto;

import com.upd.hwcloud.bean.entity.Files;

import java.util.List;

/**
 * 服务申请实施信息
 *
 * @param <I> 实施的服务器信息
 */
public class ImplRequest<I> {

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
    private List<I> implServerList;

    private List<Files> fileList;

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

    public List<I> getImplServerList() {
        return implServerList;
    }

    public void setImplServerList(List<I> implServerList) {
        this.implServerList = implServerList;
    }

    public List<Files> getFileList() {
        return fileList;
    }

    public void setFileList(List<Files> fileList) {
        this.fileList = fileList;
    }

}
