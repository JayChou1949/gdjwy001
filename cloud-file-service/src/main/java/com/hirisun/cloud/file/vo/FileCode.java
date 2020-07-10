package com.hirisun.cloud.file.vo;

import com.hirisun.cloud.common.vo.ResultCode;

public enum FileCode implements ResultCode {
    FDFS_COONECT_FAULT(2200,"FDFS连接失败，请检查连接信息！"),
    FILE_IS_NULL(2201,"上传文件为空！"),
    FDFS_INIT_FAULT(2202,"FDFS初始化失败"),
    FDFS_UPLOAD_FAULT(2203,"FDFS上传文件失败"),
    ;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String msg;

    FileCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer code() {
        return this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }
}
