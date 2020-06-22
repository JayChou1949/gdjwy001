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
    INSERT_FAIL(1002,"数据插入失败"),
    REMOTE_CALL_FAILED(1003,"远程调用失败"),
    NO_ACCESS_TOKEN(1004,"Token错误或已失效"),
    SERVICE_QUEST_ERROR(1005,"服务调用失败"),
    UPDATE_FAIL(1006,"数据更新失败"),
    DELETE_FAIL(1007,"数据删除失败"),
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
