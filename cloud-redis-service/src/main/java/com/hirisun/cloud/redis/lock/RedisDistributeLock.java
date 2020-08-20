package com.hirisun.cloud.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
public class RedisDistributeLock implements DistributeLock {

    private static final Long LOCK_SUCCESS = 1L;
    private static final Long UNLOCK_SUCCESS = 1L;
    private static final String LOCK_SCRIPT = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end";
    private static final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    private static final String LOCK_PREFIX = "RedisLock:";

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取阻塞分布式锁,过期时间默认60秒
     * @param lockKey
     * @param requestId 唯一ID, 可以使用UUID.randomUUID().toString();
     * @return
     */
    @Override
    public boolean lock(String lockKey, String requestId) {
        return lock(lockKey, requestId, DEFAULT_EXPIRE_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * 获取阻塞分布式锁
     * @param lockKey
     * @param requestId 唯一ID, 可以使用UUID.randomUUID().toString();
     * @param expire 过期时间
     * @param timeUnit 过期时间单位
     * @return
     */
    @Override
    public boolean lock(String lockKey, String requestId, long expire, TimeUnit timeUnit) {
        int timeout = DEFAULT_TIMEOUT_MSECS;
        while (timeout >= 0) {
            boolean havaGetLock = tryLock(lockKey, requestId, expire, timeUnit);
            if (havaGetLock) {
                return true;
            }
            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
            /*
             * 延迟100 毫秒,这里使用随机时间可能会好一点,可以防止饥饿线程的出现,即,当同时到达多个线程,
             * 只会有一个线程获得锁,其他的都用同样的频率进行尝试,后面又来了一些线程,也以同样的频率申请锁,
             * 这将可能导致前面来的锁得不到满足.使用随机的等待时间可以一定程度上保证公平性
             */
            try {
                Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    private boolean tryLock(String lockKey, String requestId, long expire, TimeUnit timeUnit) {
        String _lockKey = LOCK_PREFIX + lockKey;
        DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript<>(LOCK_SCRIPT, Long.class);
        Long result = redisTemplate.execute(longDefaultRedisScript, Collections.singletonList(_lockKey), requestId,
                String.valueOf(timeUnit.toSeconds(expire)));
        return LOCK_SUCCESS.equals(result);
    }

    /**
     * 释放锁
     * @param lockKey
     * @param requestId 唯一ID,需要与 lock() requestId一致
     * @return
     */
    @Override
    public boolean unlock(String lockKey, String requestId) {
        String _lockKey = LOCK_PREFIX + lockKey;
        DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript<>(UNLOCK_SCRIPT, Long.class);
        Long result = redisTemplate.execute(longDefaultRedisScript, Collections.singletonList(_lockKey), requestId);
        return UNLOCK_SUCCESS.equals(result);
    }


}
