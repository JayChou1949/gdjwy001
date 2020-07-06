package com.hirisun.cloud.api.redis;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangwy
 * @date 2020/4/7 16:46
 * @description
 */
@FeignClient(name = "cloud-redis-service", fallback = RedisApiFallback.class)
public interface RedisApi {

    /**
     * 添加永不过期的值
     *
     * @param key 键
     * @param value 值
     */
    @PostMapping("/redis/setForPerpetual")
    void setForPerpetual(@RequestParam("key") String key, @RequestParam("value") Object value);

    /**
     * 添加值并设置过期时间
     *
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     */
    @PostMapping("/redis/setForTerminable")
    void setForTerminable(@RequestParam("key") String key, @RequestParam("value") Object value
            , @RequestParam("timeout") long timeout, @RequestParam("timeUnit") final TimeUnit timeUnit);

    /**
     * 通过键获得一个值
     *
     * @param key 键
     * @return
     */
    @GetMapping("/redis/get/{key}")
    Object get(@PathVariable("key") String key);

    /**
     * 通过键获得一个字符串
     * @param key 键
     * @return 字符串值
     */
    @GetMapping("/redis/getStrValue/{key}")
    String getStrValue(@PathVariable("key") String key);

    /**
     * 获得指定模式键的值列表
     *
     * @param pattern 值列表
     * @return
     */
    @GetMapping("/redis/getKeys")
    Set<String> keys(@RequestParam("pattern") String pattern);

    /**
     * 根据键获得制定范围的值列表
     *
     * @param key 键
     * @param start 起始值
     * @param end 结束值
     * @return 值列表
     */
    @GetMapping("/redis/range")
    List<Object> range(@RequestParam("key") String key, @RequestParam("start") long start, @RequestParam("end") long end);

    /**
     * 为某个键设置过期时间
     *
     * @param key 键
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     * @return
     */
    @PostMapping("/redis/expire")
    Boolean expire(@RequestParam("key") String key, @RequestParam("timeout") long timeout, @RequestParam("timeUnit") TimeUnit timeUnit);

    /**
     * 根据键删除值
     *
     * @param key 键
     * @param count 删除个数
     * @param value 值
     * @return 删除个数
     */
    @DeleteMapping("/redis/remove")
    Long remove(@RequestParam("key") String key, @RequestParam("count") long count, @RequestParam("value") Object value);


    /**
     * 判断某个键是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    @GetMapping("/redis/hasKey/{key}")
    Boolean hasKey(@PathVariable("key") String key);

    /**
     * 从左添加值到List
     *
     * @param key 键
     * @param value 值
     * @return
     */
    @PostMapping("/redis/leftPush")
    Long leftPush(@RequestParam("key") String key, @RequestParam("value") Object value);

    /**
     * 获得某个键的过期时间
     *
     * @param key 键
     * @param timeUnit 时间单位
     * @return
     */
    @GetMapping("/redis/getExpire")
    Long getExpire(@RequestParam("key") String key, @RequestParam("timeUnit") final TimeUnit timeUnit);

}
