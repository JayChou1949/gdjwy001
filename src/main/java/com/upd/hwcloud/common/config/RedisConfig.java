package com.upd.hwcloud.common.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.DefaultLettucePool;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePool;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${redis.hostname}")
    private String hostname;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.maxTotal}")
    private int maxTotal;
    @Value("${redis.maxIdle}")
    private int maxIdle;
    @Value("${redis.minIdle}")
    private int minIdle;
    @Value("${redis.blockWhenExhausted}")
    private boolean isBlockWhenExhausted;
    @Value("${redis.maxWait}")
    private int maxWait;
    @Value("${redis.testOnBorrow}")
    private boolean isTestOnBorrow;
    @Value("${redis.testOnReturn}")
    private boolean isTestOnReturn;
    @Value("${redis.testWhileIdle}")
    private boolean isTestWhileIdle;
    @Value("${redis.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${redis.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${redis.dbIndex}")
    private int dbIndex;

    @Bean
    public StringRedisSerializer getStringRedisSerializer(){
        return new StringRedisSerializer();
    }

    /**
     * 配置连接池参数
     *
     * @return GenericObjectPool
     */
    @Bean
    public GenericObjectPoolConfig getRedisConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxTotal(maxTotal);
        genericObjectPoolConfig.setMinIdle(minIdle);
        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        genericObjectPoolConfig.setBlockWhenExhausted(isBlockWhenExhausted);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        //在borrow一个实例时，是否提前进行alidate操作；如果为true，则得到的实例均是可用的
        genericObjectPoolConfig.setTestOnBorrow(isTestOnBorrow);
        //调用returnObject方法时，是否进行有效检查
        genericObjectPoolConfig.setTestOnReturn(isTestOnReturn);
        //在空闲时检查有效性, 默认false
        genericObjectPoolConfig.setTestWhileIdle(isTestWhileIdle);
        //表示idle object evitor两次扫描之间要sleep的毫秒数；
        genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //表示一个对象至少停留在idle状态的最短时间，
        //然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；
        genericObjectPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

        return genericObjectPoolConfig;
    }

    /**
     * 生成连接池
     *
     * @param poolConfig 连接池配置
     * @return DefaultLettucePool
     */
    @Bean
    public DefaultLettucePool getDefaultLettucePool(GenericObjectPoolConfig poolConfig) {
        DefaultLettucePool defaultLettucePool = new DefaultLettucePool(hostname, port, poolConfig);
        defaultLettucePool.setPassword(password);
        defaultLettucePool.afterPropertiesSet();
        return defaultLettucePool;
    }

    /**
     * lettuce 连接工厂配置
     *
     * @return LettuceConnectionFactory implement RedisConnectionFactory
     */
    @Bean
    public LettuceConnectionFactory getLettuceConnectionFactory(LettucePool pool) {
        LettuceConnectionFactory factory = new LettuceConnectionFactory(pool);
        //校验连接是否有效
        factory.setValidateConnection(true);
        //选择数据库
        factory.setDatabase(dbIndex);
        factory.setTimeout(maxWait);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> getRedisTemplate(LettuceConnectionFactory factory, StringRedisSerializer stringRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //对key采用String的序列化方式--统一
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //事务支持
//        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate getStringRedisTemplate(LettuceConnectionFactory factory, StringRedisSerializer stringRedisSerializer) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        //对key采用String的序列化方式--统一
        stringRedisTemplate.setKeySerializer(stringRedisSerializer);
        //事务支持
//        stringRedisTemplate.setEnableTransactionSupport(true);
        stringRedisTemplate.setConnectionFactory(factory);
        return stringRedisTemplate;
    }

    /**
     * 缓存管理器 使用redisTemplate操作
     */
    @Bean
    public RedisCacheManager getRedisCacheManager(RedisTemplate<String, Object> redisTemplate){
        return new RedisCacheManager(redisTemplate);
    }

}
