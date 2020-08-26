package com.hirisun.cloud.common.util;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderNumUtil {
	
	
    /**
     *
     * 弃用，改用redisService方法
     * 生成单号,格式 yyyyMMdd0001,利用redis递增
     * @param prefix redis保存递增值key前缀
     * @return
     */
    private static String gen(String prefix) {
//        // 生成单号
//        String yyyyMMdd = DateFormatUtils.format(new Date(), "yyyyMMdd");
//        String redisKey = prefix + yyyyMMdd;
//        Long increment = template.opsForValue().increment(redisKey, 1L);
//        if (increment == null) {
//            throw new CustomException(CommonCode.SERVER_ERROR);
//        }
//        // 过期时间1天
//        template.expire(redisKey, 1L, TimeUnit.DAYS);
//        return String.format("%s%04d", yyyyMMdd, increment);
    	return null;
    }

}
