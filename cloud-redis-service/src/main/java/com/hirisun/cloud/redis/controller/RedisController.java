package com.hirisun.cloud.redis.controller;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.api.redis.RedisApi;
import com.hirisun.cloud.redis.service.RedisService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoufeng
 * @version 1.0
 * @className RedisController
 * @data 2020/7/6 10:10
 * @description Redis 控制器
 */
@Api(tags = {"Redis管理模块"})
@RestController
@Slf4j
@RequestMapping("/redis")
public class RedisController implements RedisApi {

    @Autowired
    private RedisService redisService;

    @PostMapping("/setForPerpetual")
    @ApiOperation("添加永不过期的值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "query"),
            @ApiImplicitParam(name = "value", value = "值", required = true, paramType = "query")
    })
    @Override
    public void setForPerpetual(@RequestParam("key") String key, @RequestParam("value") Object value) {
        redisService.setForPerpetual(key, value);
    }

    @PostMapping("/setForTerminable")
    @ApiOperation("添加值并设置过期时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "query"),
            @ApiImplicitParam(name = "value", value = "值", required = true, paramType = "query"),
            @ApiImplicitParam(name = "timeout", value = "过期时间", defaultValue = "1000", paramType = "query"),
            @ApiImplicitParam(name = "timeUnit", value = "时间单位", defaultValue = "TimeUnit.SECONDS", paramType = "query")
    })
    @Override
    public void setForTerminable(@RequestParam("key") String key, @RequestParam("value") Object value
            , @RequestParam("timeout") long timeout, @RequestParam("timeUnit") final TimeUnit timeUnit) {
        redisService.setForTerminable(key, value, timeout, timeUnit);
    }

    @GetMapping("/get/{key}")
    @ApiOperation("通过键获得一个值")
    @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "path")
    @Override
    public Object get(@PathVariable("key") String key) {
        return redisService.get(key);
    }

    @GetMapping("/getStrValue/{key}")
    @ApiOperation(("通过键获得一个字符串"))
    @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "path")
    @Override
    public String getStrValue(@PathVariable("key") String key) {
        Object value = redisService.get(key);
        return value.toString();
    }

    @GetMapping("/keys")
    @ApiOperation("获得指定模式键的值列表")
    @ApiImplicitParam(name = "pattern", value = "匹配模式", defaultValue = "*", paramType = "query")
    @Override
    public Set<String> keys(@RequestParam("pattern") String pattern) {
        return redisService.keys(pattern);
    }

    @GetMapping("/range")
    @ApiOperation("根据键获得制定范围的值列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "query"),
            @ApiImplicitParam(name = "start", value = "起始值", defaultValue = "0", paramType = "query"),
            @ApiImplicitParam(name = "end", value = "结束值", defaultValue = "-1", paramType = "query")
    })
    @Override
    public List<Object> range(@RequestParam("key") String key, @RequestParam("start") long start, @RequestParam("end") long end) {
        return redisService.range(key, start, end);
    }

    @PostMapping("/expire")
    @ApiOperation("为某个键设置过期时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "query"),
            @ApiImplicitParam(name = "timeout", value = "过期时间", defaultValue = "1000", paramType = "query"),
            @ApiImplicitParam(name = "timeUnit", value = "时间单位", defaultValue = "TimeUnit.SECONDS", paramType = "query")
    })
    @Override
    public Boolean expire(@RequestParam("key") String key, @RequestParam("timeout") long timeout, @RequestParam("timeUnit") TimeUnit timeUnit) {
        return redisService.expire(key, timeout, timeUnit);
    }

    @DeleteMapping("/remove")
    @ApiOperation("根据键删除值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "value", value = "值", required = true, paramType = "query")
    })
    @Override
    public Long remove(@RequestParam("key") String key, @RequestParam("count") long count, @RequestParam("value") Object value) {
        return redisService.remove(key, count, value);
    }

    @ApiOperation("判断某个键是否存在")
    @GetMapping("/hasKey/{key}")
    @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "path")
    @Override
    public Boolean hasKey(@PathVariable("key") String key) {
        return redisService.hasKey(key);
    }

    @ApiOperation("从左添加值到List")
    @PostMapping("/leftPush")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "query"),
            @ApiImplicitParam(name = "value", value = "值", required = true, paramType = "query")
    })
    @Override
    public Long leftPush(@RequestParam("key") String key, @RequestParam("value") Object value) {
        return redisService.leftPush(key, value);
    }

    @ApiOperation("获得某个键的过期时间")
    @GetMapping("/getExpire")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "query"),
            @ApiImplicitParam(name = "timeUnit", value = "时间单位", defaultValue = "TimeUnit.SECONDS", paramType = "query")
    })
    @Override
    public Long getExpire(@RequestParam("key") String key, @RequestParam("timeUnit") final TimeUnit timeUnit) {
        return redisService.getExpire(key, timeUnit);
    }

    @ApiOperation("根据键删除值")
    @ApiImplicitParam(name = "key", value = "键", required = true, paramType = "path")
    @DeleteMapping("/delete/{key}")
    @Override
    public Boolean delete(@PathVariable("key") String key) {
        return redisService.delete(key);
    }
}
