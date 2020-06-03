package com.upd.hwcloud.common.utils;

import com.upd.hwcloud.common.exception.BaseException;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class OrderNum {

    /**
     * 生成单号,格式 yyyyMMdd0001,利用redis递增
     * @param prefix redis保存递增值key前缀
     * @return
     */
    public static String gen(StringRedisTemplate template, String prefix) {
        // 生成单号
        String yyyyMMdd = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String redisKey = prefix + yyyyMMdd;
        Long increment = template.opsForValue().increment(redisKey, 1L);
        if (increment == null) {
            throw new BaseException("申请单号生成错误,请重试!");
        }
        // 过期时间1天
        template.expire(redisKey, 1L, TimeUnit.DAYS);
        return String.format("%s%04d", yyyyMMdd, increment);
    }

}
