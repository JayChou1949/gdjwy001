package com.hirisun.cloud.redis.lock;

import java.util.concurrent.TimeUnit;

public interface DistributeLock {

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    long DEFAULT_EXPIRE_SECONDS = 60;
    /**
     * 锁等待时间，防止线程饥饿
     */
    int DEFAULT_TIMEOUT_MSECS = 10 * 1000;
    int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

    /**
     * 获取阻塞分布式锁,过期时间默认60秒
     * @param lockKey
     * @param requestId 唯一ID, 可以使用UUID.randomUUID().toString();
     * @return
     */
    boolean lock(String lockKey, String requestId);

    /**
     * 获取阻塞分布式锁
     * @param lockKey
     * @param requestId 唯一ID, 可以使用UUID.randomUUID().toString();
     * @param expire 过期时间
     * @param timeUnit 过期时间单位
     * @return
     */
    boolean lock(String lockKey, String requestId, long expire, TimeUnit timeUnit);

    /**
     * 释放锁
     * @param lockKey
     * @param requestId 唯一ID,需要与 lock() requestId一致
     * @return
     */
    boolean unlock(String lockKey, String requestId);

}
