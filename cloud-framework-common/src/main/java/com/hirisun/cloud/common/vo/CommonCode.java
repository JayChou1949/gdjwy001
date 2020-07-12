package com.hirisun.cloud.common.vo;

/**
 * @ClassName CommonCode
 * @Author zhoufeng
 * @Date 2019/11/14 10:35
 * @Description 公共错误码
 */
public enum CommonCode implements ResultCode{
    SUCCESS(0,"成功"),
    FAIL(1,"操作失败"),
    INVALID_PARAM(1001,"非法参数"),
    JSON_RESOLVE_FAIL(1002,"JSON解析错误"),
    UPDATE_FAIL(1006,"数据更新失败"),
    DELETE_FAIL(1007,"数据删除失败"),
    IMPORT_FAIL(1008,"导入数据失败,请检查导入文件数据"),
    SERVER_ERROR(9999,"系统繁忙，稍后重试");
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String msg;

    CommonCode(Integer code, String msg) {
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
