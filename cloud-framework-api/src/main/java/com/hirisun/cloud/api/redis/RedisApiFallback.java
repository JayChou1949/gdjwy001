package com.hirisun.cloud.api.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoufeng
 * @date 2019/11/27 17:11
 * @description
 */
@Slf4j
@Component
public class RedisApiFallback implements RedisApi {

    @Override
    public void setForPerpetual(String key, Object value) {

    }

    @Override
    public void setForTerminable(String key, Object value, long timeout, TimeUnit timeUnit) {

    }

    @Override
    public Object get(String key) {
        return "1111";
    }

    @Override
    public String getStrValue(String key) {
        return "abc";
    }

    @Override
    public Set<String> keys(String pattern) {
        return null;
    }

    @Override
    public List<Object> range(String key, long start, long end) {
        return null;
    }

    @Override
    public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return null;
    }

    @Override
    public Long remove(String key, long count, Object value) {
        return null;
    }

    @Override
    public Boolean hasKey(String key) {
        return null;
    }

    @Override
    public Long leftPush(String key, Object value) {
        return null;
    }

    @Override
    public Long getExpire(String key, TimeUnit timeUnit) {
        return null;
    }

	@Override
	public Boolean delete(String key) {
		return false;
	}
}
