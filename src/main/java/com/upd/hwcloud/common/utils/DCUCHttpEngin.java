package com.upd.hwcloud.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.upd.hwcloud.bean.dto.IamResponse;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.common.exception.BaseException;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DCUCHttpEngin implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(DCUCHttpEngin.class);

    @Value("${dcuc.business.url}")
    private String businessUrl;
    @Value("${dcuc.iam.adapter.url}")
    private String iamAdapterUrl;
    @Autowired
    private TokenInterceptor tokenInterceptor;
    private OkHttpClient okHttpClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(tokenInterceptor)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
    }

    public User getUserInfoById(String userId) {
        Response response = null;
        try {
            response = OkHttpUtils.get(okHttpClient, businessUrl + "/users/id/" + userId + ".action", null);
            String res = response.body().string();
            return JSONObject.parseObject(res, User.class);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        } finally {
            if (response!=null){
                response.close();
            }
        }
    }

    /**
     * 创建 IAM 用户
     * @param idcard 身份证号
     */
    public void createIamUser(String idcard) throws Exception {
        Response response = OkHttpUtils.get(okHttpClient, iamAdapterUrl + "/" + idcard, null);
        String result = response.body().string();
        if (!response.isSuccessful()) {
            logger.error("创建IAM用户:[{}][{}]", idcard, result);
            throw new BaseException("创建用户失败");
        }
        response.close();
        logger.error("创建IAM用户:[{}][{}]", idcard, result);
        IamResponse iamResponse = JSONObject.parseObject(result, IamResponse.class);
        boolean userExists = !iamResponse.isSuccess() && "用户已存在".equals(iamResponse.getMessage());
        if (iamResponse.isSuccess() || userExists) {
            return;
        }
        throw new BaseException(iamResponse.getMessage());
    }

}
