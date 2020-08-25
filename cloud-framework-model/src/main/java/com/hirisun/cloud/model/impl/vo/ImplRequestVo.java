package com.hirisun.cloud.model.impl.vo;


import java.util.List;
import java.util.Map;

import com.hirisun.cloud.model.file.FilesVo;

/**
 * 服务申请实施信息
 *
 */
public class ImplRequestVo {

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

    public List<Map<String,Object>> getImplServerList() {
        return implServerList;
    }

    public void setImplServerList(List<Map<String,Object>> implServerList) {
        this.implServerList = implServerList;
    }

    public List<FilesVo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FilesVo> fileList) {
        this.fileList = fileList;
    }

}
