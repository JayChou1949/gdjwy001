package com.hirisun.cloud.redis.vo;

import com.hirisun.cloud.common.vo.ResultCode;

public enum RedisCode implements ResultCode {
    REDIS_CONNECT_TIMEOUT(2300,"Redis连接超时，请检查连接信息是否正确！"),
    REDIS_COMMAND_TIMEOUT(2301,"Redis执行超时！"),
    ;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String msg;

    RedisCode(Integer code, String msg) {
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
