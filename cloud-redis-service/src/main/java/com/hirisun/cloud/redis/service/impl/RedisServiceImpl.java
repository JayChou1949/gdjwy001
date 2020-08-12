package com.hirisun.cloud.redis.service.impl;

import com.hirisun.cloud.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoufeng
 * @version 1.0
 * @className RedisServiceImpl
 * @data 2020/7/6 10:14
 * @description RedisService 实现类
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setForPerpetual(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setForTerminable(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public Object get(String key) {
        Object o = redisTemplate.opsForValue().get(key);
        return o;
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public List<Object> range(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    @Override
    public Long remove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Long leftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    @Override
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public void setForList(String key,Integer index,Object value) {
        redisTemplate.opsForList().set(key,index,value);
    }
    @Override
    public void removeValueForList(String key,Integer count,Object value) {
        redisTemplate.opsForList().remove(key, count,value);
    }
    @Override
    public Long increment(String redisKey){
        return redisTemplate.opsForValue().increment(redisKey, 1L);
    }

}
