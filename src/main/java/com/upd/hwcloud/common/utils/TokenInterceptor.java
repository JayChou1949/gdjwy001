package com.upd.hwcloud.common.utils;


import com.alibaba.fastjson.JSONObject;
import com.upd.hwcloud.bean.contains.RedisKey;
import com.upd.hwcloud.bean.response.TokenResponse;
import com.upd.hwcloud.common.lock.DistributeLock;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TokenInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    @Value("${token.url}")
    private String url;
    @Value("${token.grant_type}")
    private String grantType;
    @Value("${token.scope}")
    private String scope;
    @Value("${token.client_id}")
    private String clientId;
    @Value("${token.client_secret}")
    private String clientSecret;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private DistributeLock lock;

    @Override
    public Response intercept(Chain chain) throws IOException {
        String accessToken = this.getAccessToken();
        Request request = chain.request();
        request = request.newBuilder().header("Authorization", "Bearer " + accessToken).build();
        return chain.proceed(request);
    }

    private String getAccessToken() {
        String uuid = UUIDUtil.getUUID();
        String lockKey = TokenInterceptor.class.getSimpleName().intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                String redisKey = RedisKey.KEY_TOKEN_APIG;
                String accessToken = redisTemplate.opsForValue().get(redisKey);
                if (accessToken != null) {
                    return accessToken;
                }
                accessToken = this.getTokenFromNet();
                if (accessToken != null) {
                    redisTemplate.opsForValue().set(redisKey, accessToken);
                    redisTemplate.expire(redisKey, 30L, TimeUnit.MINUTES);
                    return accessToken;
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return null;
    }

    private String getTokenFromNet() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", grantType);
        params.put("scope", scope);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().
                sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
        Response response = OkHttpUtils.post(okHttpClient, url, params);
        if (response.code() == 200) {
            String result = response.body().string();
            TokenResponse tokenResponse = JSONObject.parseObject(result, TokenResponse.class);
            response.close();
            return tokenResponse.getAccessToken();
        }
        response.close();
        return null;
    }

}