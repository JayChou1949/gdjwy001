package com.hirisun.cloud.redis.exception;

import com.hirisun.cloud.common.exception.ExceptionCatch;
import com.hirisun.cloud.redis.vo.RedisCode;
import io.lettuce.core.RedisCommandTimeoutException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author zhoufeng
 * @version 1.0
 * @className RedisExceptionCatch
 * @data 2020/7/6 13:41
 * @description Redis 异常捕获类
 */
@ControllerAdvice
public class RedisExceptionCatch extends ExceptionCatch {

    static {
        //定义异常类型所对应的错误代码
        builder.put(RedisConnectionFailureException.class,RedisCode.REDIS_CONNECT_TIMEOUT);
        builder.put(RedisCommandTimeoutException.class, RedisCode.REDIS_COMMAND_TIMEOUT);
    }

}
