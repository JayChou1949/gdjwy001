package com.upd.hwcloud.service.application.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upd.hwcloud.bean.contains.FormNum;
import com.upd.hwcloud.bean.dto.apig.*;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.DaasApplication;
import com.upd.hwcloud.bean.entity.application.SaasServiceApplication;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.*;
import com.upd.hwcloud.service.application.IApplicationInfoService;
import com.upd.hwcloud.service.application.IDaasApplicationService;
import com.upd.hwcloud.service.application.ISaasApigService;
import com.upd.hwcloud.service.application.ISaasServiceApplicationService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author junglefisher
 * @date 2020/6/18 11:11
 */
@Service
public class SaasApigServiceImpl implements ISaasApigService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationInfoServiceImpl.class);

    @Value("${daas.auto.apig.base_url}")
    private String apigBaseUrl;
    @Value("${daas.auto.csc.base_url.v2}")
    private String cscBaseUrlV2;
    @Value("${daas.auto.csc.base_url.v3}")
    private String cscBaseUrlV3;
    @Value("${daas.auto.project.base_url}")
    private String projectBaseUrl;

    @Autowired
    private ISaasServiceApplicationService saasServiceApplicationService;
    @Autowired
    private IApplicationInfoService applicationInfoService;
    @Autowired
    private DCUCHttpEngin dcucHttpEngin;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void orderService(ApplicationInfo info) throws Exception {
        List<SaasServiceApplication> serverList = saasServiceApplicationService.getUnsucessByAppInfoId(info.getId());
        this.order(info, serverList);
    }

    private void order(ApplicationInfo info, List<SaasServiceApplication> serverList) throws Exception {
        FormNum formNum = FormNum.getFormNumByInfo(info);
        if (formNum != FormNum.SAAS) {
            throw new BaseException("必须为SaaS申请");
        }
        if (StringUtils.isEmpty(info.getCreator())) {
            throw new BaseException("创建人不能为空");
        }
        if (StringUtils.isEmpty(info.getAppName())) {
            throw new BaseException("应用名称不能为空");
        }
        logger.error("开始自动订购: {}", info.toString());
        if (serverList == null || serverList.isEmpty()) {
            return;
        }
        dcucHttpEngin.createIamUser(info.getCreator());

        List<ProjectList.Project> pjcts = this.getUserProject(info);
        String loginUrl = cscBaseUrlV3 + "/auth/tokens";
        OkHttpClient client = this.initOkHttpClient();
        //遍历user下所有project项目id，遍历请求usertoken
        List<UserClient> userClients = new ArrayList<>();
        for (ProjectList.Project pjct : pjcts) {
            logger.error("project参数详情:{}" , pjct.toString());
            // 1.普通用户登录
            LoginRequest userRequest = this.buildUserRequest(info);
            LoginRequest.Auth.Scope.Project project = new LoginRequest.Auth.Scope.Project();
            project.setName(pjct.getName());
            userRequest.getAuth().getScope().setProject(project);
            Response userLoginResponse = OkHttpUtils.postJson(client, loginUrl, JSONObject.toJSONString(userRequest));
            String userJsonStr = userLoginResponse.body().string();
            if (!userLoginResponse.isSuccessful()) {
                userLoginResponse.close();
                throw new BaseException("发布失败,失败原因:用户登录失败");
            }
            final String userToken = userLoginResponse.header("x-subject-token");
            userLoginResponse.close();
            LoginResponse user = JSONObject.parseObject(userJsonStr, LoginResponse.class);
            // 带有用户token的OkHttpClient
            OkHttpClient userClient = initOkHttpClient();
            userClient = userClient.newBuilder().addInterceptor(new SaasApigServiceImpl.TokenInterceptor(userToken)).build();
            UserClient usersClient = new UserClient();
            usersClient.setUser(user);
            usersClient.setClient(userClient);
            userClients.add(usersClient);
        }

        // 2.管理员用户登录
        LoginRequest managerRequest = this.buildManagerRequest2();
        Response managerLoginResponse = OkHttpUtils.postJson(client, loginUrl, JSONObject.toJSONString(managerRequest));
        String managerJsonStr = managerLoginResponse.body().string();
        if (!managerLoginResponse.isSuccessful()) {
            logger.error("管理员登录失败: {}", managerJsonStr);
            managerLoginResponse.close();
            throw new BaseException(401, "订购失败,失败原因:管理员登录失败");
        }
        final String managerToken = managerLoginResponse.header("x-subject-token");
        managerLoginResponse.close();
        // 带有管理员token的OkHttpClient
        OkHttpClient managerClient = initOkHttpClient();
        managerClient = managerClient.newBuilder().addInterceptor(new SaasApigServiceImpl.TokenInterceptor(managerToken)).build();
        Boolean isBatch=false;
        if (serverList.size()>1){
            isBatch=true;
        }
        Boolean isAllSuccess=true;
        for (SaasServiceApplication saasServiceApplication : serverList) {
            if (isBatch){
                try {
                    isBatch(saasServiceApplication,userClients,info,managerClient);
                }catch (Exception e){
                    logger.error("订购失败的ID:{}",saasServiceApplication.getID());
                    isAllSuccess=false;
                }
            }else {
                isBatch(saasServiceApplication,userClients,info,managerClient);
            }
        }
        if (!isAllSuccess){
            throw new BaseException("订购部分出错！");
        }
    }

    private void isBatch(SaasServiceApplication saasServiceApplication,List<UserClient> userClients,ApplicationInfo info,OkHttpClient managerClient) throws Exception{
        String serviceId = saasServiceApplication.getServiceId();
        if (StringUtils.isEmpty(serviceId)) {
            saasServiceApplication.setErrorMsg("服务ID为空");
            saasServiceApplication.setErrorJson("");
            saasServiceApplication.updateById();
            throw new BaseException("所申请的服务ID不能为空");
        }
        // 3.查询此用户的应用列表
        AppDetail existsApp = null;
        OkHttpClient okHttpClient = null;
        LoginResponse currentUser = null;
        look: for (UserClient userClient : userClients) {
            AppList appList = this.getUserAppList(userClient.getClient(),saasServiceApplication);
            List<AppDetail> apps = appList.getApps();
            if (apps != null && !apps.isEmpty()) {
                for (AppDetail app : apps) {
                    if (info.getAppName().equals(app.getName())) {
                        existsApp = app;
                        okHttpClient = userClient.getClient();
                        currentUser = userClient.getUser();
                        break look;
                    }
                }
            }
        }
        if (okHttpClient == null) {
            okHttpClient = userClients.get(0).getClient();
        }
        if (currentUser == null) {
            currentUser = userClients.get(0).getUser();
        }
        // 4.查询套餐信息(略)
        // 5.获取需要订购的服务详情
        ServiceDetail serviceDetail = this.getServiceDetail(okHttpClient, serviceId,saasServiceApplication);
        if (existsApp == null) {
            // 6.不存在,创建应用
            AppDetail appDetail = this.createApp(okHttpClient, info,saasServiceApplication);
            existsApp = appDetail;
        }
        // 7.订购服务
        ServiceInstance orderInstance = this.orderService(okHttpClient, info, serviceDetail, currentUser, existsApp,saasServiceApplication);
        // 8.审核
        ServiceInstance reviewInstance = this.review(managerClient, okHttpClient, orderInstance,saasServiceApplication);
        //进行绑定操作时需等待实例进入运行状态
        boolean flag = true;
        int count = 0;
        ServiceBindingResponse serviceBinding;
        do {
            Thread.sleep(3000);
            //9.绑定
            try {
                serviceBinding = this.binding(okHttpClient, reviewInstance, currentUser, serviceDetail, saasServiceApplication);
                flag = false;
                saasServiceApplication.setAppKey(serviceBinding.getCredentials().getAppKey());
                saasServiceApplication.setAppSecret(serviceBinding.getCredentials().getAppSecret());
                //成功后将错误信息置空
                saasServiceApplication.setErrorMsg("");
                saasServiceApplication.setErrorJson("");
                saasServiceApplication.updateById();

            }catch (Exception e) {
                count++;
                if(count > 2) {
                    throw new BaseException("订购失败");
                }
            }
        } while (flag);
        // 获取api列表
        this.saveUserData(okHttpClient, saasServiceApplication, reviewInstance.getInstance_guid());
    }

    private <I> List<ProjectList.Project> getUserProject(ApplicationInfo info) throws Exception {
        // 1.普通用户登录
        String loginUrl = cscBaseUrlV3 + "/auth/tokens";
        OkHttpClient client = this.initOkHttpClient();
        // 2.管理员用户登录
        LoginRequest managerRequest = this.buildManagerRequest();
        Response managerLoginResponse = OkHttpUtils.postJson(client, loginUrl, JSONObject.toJSONString(managerRequest));
        String managerJsonStr = managerLoginResponse.body().string();
        if (!managerLoginResponse.isSuccessful()) {
            logger.info(managerJsonStr);
            managerLoginResponse.close();
            throw new BaseException(401, "订购失败,失败原因:管理员登录失败");
        }
        final String managerToken = managerLoginResponse.header("x-subject-token");
        managerLoginResponse.close();
        // 带有管理员token的OkHttpClient
        OkHttpClient managerClient = initOkHttpClient();
        managerClient = managerClient.newBuilder().addInterceptor(new SaasApigServiceImpl.TokenInterceptor(managerToken)).build();


        List<ProjectList.Project> project = getUserProject(managerClient, info);
        if (project == null) {
            throw new BaseException(401, "订购失败,失败原因:获取状态正常的项目失败");
        }
        logger.error("用户正常状态的项目:{}", project.toString());
        return project;
    }

    //获取用户的所有projectid (原因：huawei那边说用户应用过滤不全，导致应用重名）
    private <I> List<ProjectList.Project> getUserProject(OkHttpClient client, ApplicationInfo info) throws Exception {
        //应用过滤重新更改接口2020.03.24
        String url = projectBaseUrl + "/rest/vdc/v3.0/users?query_string=" + info.getCreator();
        logger.error(url);
        Response response = OkHttpUtils.get(client, url, null);
        String resultStr = response.body().string();
        logger.error("获取登录用户的userId======="+resultStr);
        if (!response.isSuccessful()) {
            logger.error(resultStr);
            response.close();
            throw new BaseException(401, "订购失败,失败原因:获取用户id失败");
        }
        response.close();
        ProjectListNew projectListNew = JSONObject.parseObject(resultStr, ProjectListNew.class);
        if (projectListNew.getTotal() > 1) {
            throw new BaseException(401, "订购失败,失败原因:获取用户id数量不正常");
        }
        String projectListUserid = projectListNew.getUsers().get(0).getId();
        //获取用户相关项目projectId接口
        String projectUrl = projectBaseUrl + "/rest/vdc/v3.1/users/" + projectListUserid + "/projects";
        Response response1 = OkHttpUtils.get(client, projectUrl, null);
        String resultStr1 = response1.body().string();
        logger.error("获取个人所有projectid======"+resultStr1);
        if (!response1.isSuccessful()) {
            logger.error(resultStr1);
            response1.close();
            throw new BaseException(401, "订购失败,失败原因:获取状态正常的项目失败");
        }
        response1.close();
        ProjectList projectList = JSONObject.parseObject(resultStr1, ProjectList.class);
        List<ProjectList.Project> projects = projectList.getProjects();
        List<ProjectList.Project> projectsNormal = new ArrayList<>();
        if (projects == null || projects.isEmpty()) {
            return null;
        }
        for (ProjectList.Project project : projects) {
            if ("normal".equals(project.getRegions().get(0).getRegion_status())) {
                projectsNormal.add(project);
            }
        }
        return projectsNormal;
    }

    //todo:能够删除普通用户的实例?
    private ServiceBindingResponse binding(OkHttpClient userClient, ServiceInstance reviewInstance,
                                           LoginResponse user, ServiceDetail serviceDetail,
                                           SaasServiceApplication saasServiceApplication) throws Exception {
        ServiceBindingRequest requestData = new ServiceBindingRequest();
        requestData.setCaller_type("app"); // 固定
        String secretName = "a" + this.genUuidMd5Bit16();  // secretName/Caller_guid 必须一致
        requestData.setCaller_guid(secretName);
        requestData.setInstance_guid(reviewInstance.getInstance_guid());
        requestData.setSecret_name(secretName);
        requestData.setCluster_id(serviceDetail.getClusterId());
        requestData.setCluster_namespace(user.getToken().getProject().getId());

        String url = cscBaseUrlV2 + "/service_bindings?isReconfig=0";
        Response response = OkHttpUtils.postJson(userClient, url, JSON.toJSONString(requestData));
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            saasServiceApplication.setErrorMsg("绑定失败");
            saasServiceApplication.setErrorJson(resultStr);
            saasServiceApplication.updateById();
            response.close();
            throw new BaseException("订购失败,失败原因:绑定失败");
        }
        ServiceBindingResponse binding = JSONObject.parseObject(resultStr, ServiceBindingResponse.class);
        response.close();
        return binding;
    }

    //todo:管理员能够删除普通用户的实例?
    private ServiceInstance review(OkHttpClient managerClient, OkHttpClient userClient, ServiceInstance orderInstance,SaasServiceApplication saasServiceApplication) throws Exception {
        String url = cscBaseUrlV2 + "/service_instances/" + orderInstance.getInstance_guid() + "/approval";
        Response response = OkHttpUtils.post(managerClient, url, null);
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            saasServiceApplication.setErrorMsg("审核失败");
            saasServiceApplication.setErrorJson(resultStr);
            saasServiceApplication.updateById();
            response.close();
            throw new BaseException("订购失败,失败原因:审核失败");
        }
        ServiceInstance instance = JSONObject.parseObject(resultStr, ServiceInstance.class);
        response.close();
        return instance;
    }


    private ServiceInstance orderService(OkHttpClient client, ApplicationInfo info,
                                         ServiceDetail serviceDetail, LoginResponse user,
                                         AppDetail existsApp, SaasServiceApplication saasServiceApplication) throws Exception {
        OrderServiceRequest requestData = new OrderServiceRequest();
        String appName = info.getAppName();
        requestData.setName("a" + this.genUuidMd5Bit16());
        requestData.setService_guid(serviceDetail.getGuid());
        List<ServiceDetail.Plans> plans = serviceDetail.getPlans();
        if (plans == null || plans.isEmpty()) {
            saasServiceApplication.setErrorMsg("服务套餐为空");
            saasServiceApplication.setErrorJson("");
            saasServiceApplication.updateById();
            throw new BaseException("订购失败,失败原因:服务套餐为空");
        }
        requestData.setService_plan_name(plans.get(0).getName());
        LoginResponse.Token.Project project = user.getToken().getProject();
        requestData.setProject_id(project.getId());
        requestData.setDesc("自动订购");
        requestData.setAlias(String.format("%s（%s）", appName, serviceDetail.getAlias()));
        requestData.setCluster_id(serviceDetail.getClusterId());
        requestData.setCluster_namespace(project.getId());

        String url = cscBaseUrlV2 + "/service_instances?app_id=" + existsApp.getId();
        Response response = OkHttpUtils.postJson(client, url, JSON.toJSONString(requestData));
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            saasServiceApplication.setErrorMsg("订购服务失败");
            saasServiceApplication.setErrorJson(resultStr);
            saasServiceApplication.updateById();
            response.close();
            throw new BaseException("订购服务失败");
        }
        response.close();
        // 保存实例id
        ServiceInstance instance = JSONObject.parseObject(resultStr, ServiceInstance.class);
        saasServiceApplication.setInstanceGuid(instance.getInstance_guid());
        saasServiceApplication.updateById();
        return instance;
    }

    /**
     * 保存api列表(user_data,审核之后才能获取到)
     */
    private void saveUserData(OkHttpClient client, SaasServiceApplication saasServiceApplication, String instanceGuid) {
        String url = cscBaseUrlV2 + "/service_instances/" + instanceGuid;
        Response response = null;
        try {
            response = OkHttpUtils.get(client, url, null);
            String resultStr = response.body().string();
            if (!response.isSuccessful()) {
                logger.error("获取api列表失败: {}", resultStr);
                return;
            }
            InstanceDetail instanceDetail = JSONObject.parseObject(resultStr, InstanceDetail.class);
            String userData = instanceDetail.getUser_data();
            saasServiceApplication.setUserData(userData);
            saasServiceApplication.updateById();
        } catch (Exception e) {
            logger.error("获取api列表失败", e);
        } finally {
            if (response!=null){
                response.close();
            }
        }
    }

    private String genUuidMd5Bit16() {
        String uuid = UUIDUtil.getUUID();
        return MD5Util.md5bit16(uuid);
    }

    private AppDetail createApp(OkHttpClient client, ApplicationInfo info,SaasServiceApplication saasServiceApplication) throws Exception {
        Map<String, String> createInfo = new HashMap<>(2);
        createInfo.put("name", info.getAppName());
        createInfo.put("remark", new StringBuffer(info.getPoliceCategory()).append("|").append(info.getCjUnit()).toString());

        String url = apigBaseUrl + "/v1/apigw/extension/apps";
        Response response = OkHttpUtils.postJson(client, url, JSON.toJSONString(createInfo));
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            saasServiceApplication.setErrorMsg("创建应用失败");
            saasServiceApplication.setErrorJson(resultStr);
            saasServiceApplication.updateById();
            response.close();
            throw new BaseException("订购失败,失败原因:创建应用失败");
        }
        response.close();
        AppDetail appDetail = JSONObject.parseObject(resultStr, AppDetail.class);
        return appDetail;
    }

    private AppList getUserAppList(OkHttpClient client,SaasServiceApplication saasServiceApplication) throws Exception {
        String url = apigBaseUrl + "/v1/apigw/extension/apps";
        Map<String, String> param = new HashMap<>();
        param.put("page_size", "500");
        Response response = OkHttpUtils.get(client, url, param);
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            saasServiceApplication.setErrorMsg("获取应用列表失败");
            saasServiceApplication.setErrorJson(resultStr);
            saasServiceApplication.updateById();
            response.close();
            throw new BaseException("订购失败,失败原因:获取应用列表失败");
        }
        AppList appList = JSONObject.parseObject(resultStr, AppList.class);
        response.close();
        return appList;
    }

    private ServiceDetail getServiceDetail(OkHttpClient client, String serviceId,SaasServiceApplication saasServiceApplication) throws Exception {
        String url = cscBaseUrlV2 + "/services/" + serviceId;
        Response response = OkHttpUtils.get(client, url, null);
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            saasServiceApplication.setErrorMsg("获取服务详情失败");
            saasServiceApplication.setErrorJson(resultStr);
            saasServiceApplication.updateById();
            response.close();
            throw new BaseException("订购失败,失败原因:获取服务详情失败");
        }
        ServiceDetail detail = JSONObject.parseObject(resultStr, ServiceDetail.class);
        response.close();
        return detail;
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

    private LoginRequest buildUserRequest(ApplicationInfo info) {
        LoginRequest userRequest = JSONObject.parseObject(USER_LOGIN_JSON, LoginRequest.class);
        LoginRequest.Auth.Identity.Password.User user = userRequest.getAuth().getIdentity().getPassword().getUser();
        user.setName(info.getCreator());
        userRequest.getAuth().getScope().setProject(null);
        user.setPassword("Gdga12#$");
        return userRequest;
    }

    private LoginRequest buildManagerRequest() {
        LoginRequest managerRequest = JSONObject.parseObject(MANAGER_LOGIN_JSON, LoginRequest.class);
        return managerRequest;
    }

    //  审核必须要用此管理员账号
    private LoginRequest buildManagerRequest2() {
        LoginRequest managerRequest = JSONObject.parseObject(MANAGER_LOGIN_JSON2, LoginRequest.class);
        return managerRequest;
    }

    private OkHttpClient initOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
        return okHttpClient;
    }


    private static final String USER_LOGIN_JSON = "{\n" +
            "\t\"auth\": {\n" +
            "\t\t\"identity\": {\n" +
            "\t\t\t\"methods\": [\n" +
            "\t\t\t\t\"password\"\n" +
            "\t\t\t],\n" +
            "\t\t\t\"password\": {\n" +
            "\t\t\t\t\"user\": {\n" +
            "\t\t\t\t\t\"name\": \"410184198209096919\",\n" +
            "\t\t\t\t\t\"password\": \"QAZ2wsx@123!\",\n" +
            "\t\t\t\t\t\"domain\": {\n" +
            "\t\t\t\t\t\t\"name\": \"GDJWY\"\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t},\n" +
            "\t\t\"scope\": {\n" +
            "\t\t\t\"project\": {\n" +
            "\t\t\t\t\"name\": \"PaaS_kexin_shujuke_hailianjiexun\"\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}";

    private static final String MANAGER_LOGIN_JSON = "{\n" +
            "  \"auth\":{\n" +
            "    \"identity\":{\n" +
            "      \"methods\":[\n" +
            "        \"password\"\n" +
            "      ],\n" +
            "      \"password\":{\n" +
            "        \"user\":{\n" +
            "          \"name\":\"stjwy_admin\",\n" +
            "          \"password\":\"QAZ2wsx@123!\",\n" +
            "          \"domain\":{\n" +
            "            \"name\":\"GDJWY\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"scope\":{\n" +
            "      \"project\":{\n" +
            "        \"name\":\"GDJWY\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    private static final String MANAGER_LOGIN_JSON2 = "{\n" +
            "\t\"auth\": {\n" +
            "\t\t\"identity\": {\n" +
            "\t\t\t\"methods\": [\n" +
            "\t\t\t\t\"password\"\n" +
            "\t\t\t],\n" +
            "\t\t\t\"password\": {\n" +
            "\t\t\t\t\"user\": {\n" +
            "\t\t\t\t\t\"name\": \"op_svc_servicestage\",\n" +
            "\t\t\t\t\t\"password\": \"QAZ2wsx@123!\",\n" +
            "\t\t\t\t\t\"domain\": {\n" +
            "\t\t\t\t\t\t\"name\": \"op_svc_servicestage\"\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t},\n" +
            "\t\t\"scope\": {\n" +
            "\t\t\t\"domain\": {\n" +
            "\t\t\t\t\"name\": \"op_svc_servicestage\"\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}";
}
