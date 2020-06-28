package com.upd.hwcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.upd.hwcloud.bean.entity.InstanceOrderMessage;
import com.upd.hwcloud.bean.entity.ServiceInstance;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.OkHttpUtils;
import com.upd.hwcloud.common.utils.SSLSocketClient;
import com.upd.hwcloud.dao.InstanceOrderMessageMapper;
import com.upd.hwcloud.dao.ServiceInstanceMapper;
import com.upd.hwcloud.service.InstanceOrderMessageService;
import com.upd.hwcloud.service.application.impl.PaasApigServiceImpl;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author junglefisher
 * @date 2019/11/12 18:09
 */
@Service
public class InstanceOrderMessageServiceImpl implements InstanceOrderMessageService {
    private static final Logger logger = LoggerFactory.getLogger(PaasApigServiceImpl.class);

    @Value("${daas.auto.csc.base_url.v3}")
    private String cscBaseUrlV3;
    @Value("${daas.auto.csc.base_url.v2}")
    private String cscBaseUrlV2;

    @Autowired
    private InstanceOrderMessageMapper instanceOrderMessageMapper;
    @Autowired
    private ServiceInstanceMapper serviceInstanceMapper;

    /**
     * 根据guid查询订购此实例的警员信息
     * @param guid
     * @return
     */
    @Override
    public List<InstanceOrderMessage> getMessageByGuid(String guid){
        return instanceOrderMessageMapper.getMessageByGuid(guid);
    }

    /**
     * 根据项目id查询实例信息
     * @param projectId
     * @return
     */
    @Override
    public List<ServiceInstance> getServiceInstanceByProid(String projectId) {
        return serviceInstanceMapper.getServiceInstanceByProid(projectId);
    }

    @Override
    public List<InstanceOrderMessage> addKeyAndSecret(List<InstanceOrderMessage> instanceOrderMessageList) throws Exception{
        String urlPath=cscBaseUrlV2+"/service_bindings/projects/";
        for (InstanceOrderMessage instanceOrderMessage : instanceOrderMessageList) {
            OkHttpClient client = initOkHttpClient();
            //  登录获取token
            String token=this.loginByProid(instanceOrderMessage.getProjectId(),client);
            logger.info("-----------------------------token"+token);
            OkHttpClient userClient = initOkHttpClient();
            userClient = userClient.newBuilder().addInterceptor(new TokenInterceptor(token)).build();
            Map<String,String> map=new HashMap<>();
            map.put("instance_guid",instanceOrderMessage.getInstanceId());
            String url=urlPath+instanceOrderMessage.getProjectId();
            Response response = OkHttpUtils.get(userClient, url, map);
            String resultStr = response.body().string();
            if (!response.isSuccessful()) {
                logger.error("获取绑定信息失败: {}",resultStr);
                response.close();
                throw new BaseException("获取绑定信息失败");
            } else {
                Map<String, Object> returnMap=JSONObject.parseObject(resultStr,new TypeReference<Map<String, Object>>(){});
                List<Map<String,Object>> list= (List<Map<String,Object>>) returnMap.get("service_bindings");
                if (list!=null&&list.size()!=0){
                    Map<String,Object> stringObjectMap=list.get(0);
                    String credentials= (String) stringObjectMap.get("credentials");
                    Map<String,String> appMap=JSONObject.parseObject(credentials,new TypeReference<Map<String, String>>(){});
                    String appKey=appMap.get("AppKey");
                    String appSecret=appMap.get("AppSecret");
                    instanceOrderMessage.setAppKey(appKey);
                    instanceOrderMessage.setAppSecret(appSecret);
                }
            }
            response.close();
        }
        return instanceOrderMessageList;
    }

    private OkHttpClient initOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .connectionPool(new ConnectionPool(20,1,TimeUnit.MINUTES))
                .build();
        return okHttpClient;
    }

    private String loginByProid(String projectId,OkHttpClient client) throws Exception{
        List<String> passList=new ArrayList<>();
        passList.add("password");
        Map<String,Object> userMap=new HashMap<>();
        userMap.put("name","stjwy_admin");
        userMap.put("password","QAZ2wsx@123!");
        Map<String,Object> domainMap=new HashMap<>();
        domainMap.put("name","GDJWY");
        userMap.put("domain",domainMap);
        Map<String,Object> passwordMap=new HashMap<>();
        passwordMap.put("user",userMap);
        Map<String,Object> identityMap=new HashMap<>();
        identityMap.put("methods",passList);
        identityMap.put("password",passwordMap);
        Map<String,Object> projectMap=new HashMap<>();
        projectMap.put("id",projectId);
        Map<String,Object> scopeMap=new HashMap<>();
        scopeMap.put("project",projectMap);
        Map<String,Object> authMap=new HashMap<>();
        authMap.put("identity",identityMap);
        authMap.put("scope",scopeMap);
        Map<String,Object> map=new HashMap<>();
        map.put("auth",authMap);
        String loginUrl = cscBaseUrlV3 + "/auth/tokens";
        Response response = OkHttpUtils.postJson(client, loginUrl, JSONObject.toJSONString(map));
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            logger.error("登录失败: {}",resultStr);
            response.close();
            throw new BaseException("登录失败");
        }
        String token = response.header("x-subject-token");
        response.close();
        return token;
    }

    private static class TokenInterceptor implements Interceptor {

        private final String token;

        public TokenInterceptor(String token) {
            this.token = token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            request = request.newBuilder().header("X-Auth-Token", token).build();
            return chain.proceed(request);
        }

    }
}
