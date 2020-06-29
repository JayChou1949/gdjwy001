package com.upd.hwcloud.service.application.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upd.hwcloud.bean.contains.FormNum;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.dto.ResponseStatus;
import com.upd.hwcloud.bean.dto.apig.*;
import com.upd.hwcloud.bean.dto.apig.ServiceInstance;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.bean.entity.application.*;
import com.upd.hwcloud.common.Const;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.*;
import com.upd.hwcloud.dao.AppMapper;
import com.upd.hwcloud.dao.application.PassTyxxUserMapper;
import com.upd.hwcloud.dao.application.PassTyyhUserMapper;
import com.upd.hwcloud.service.IAppService;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IOrgService;
import com.upd.hwcloud.service.IPaasService;
import com.upd.hwcloud.service.application.*;

import okhttp3.*;
import org.apache.commons.beanutils.PropertyUtils;
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
 * @author: yyc
 * @Date: 14/05/2019 4:58 PM
 */
@Service
public class PaasApigServiceImpl implements IPaasApigService {
    private static final Logger logger = LoggerFactory.getLogger(PaasApigServiceImpl.class);
    @Value("${server.auto.fileupdata.base_url}")
    private String fileUpdataBaseUrl;
    @Value("${daas.auto.apig.base_url}")
    private String apigBaseUrl;
    @Value("${daas.auto.csc.base_url.v2}")
    private String cscBaseUrlV2;
    @Value("${daas.auto.csc.base_url.v3}")
    private String cscBaseUrlV3;
    @Value("${daas.auto.csc.base_url.projects.v3}")
    private String cscBaseUrlProjectV3;
    @Value("${daas.auto.project.base_url}")
    private String projectBaseUrl;
    @Value("${daas.auto.projects.base_url}")
    private String projectsBaseUrl;
    @Value("${tyyh.url}")
    private String tyyhUrl;

    @Autowired
    private IPaasService paasService;
    @Autowired
    private IPaasFseqsqmService paasFseqsqmService;

    @Autowired
    private IPaasDthtjyService paasDthtjyService;

    @Autowired
    private IPaasDtsjgtService paasDtsjgtService;
    @Autowired
    private DCUCHttpEngin dcucHttpEngin;

    @Autowired
    private IPaasTyxxService paasTyxxService;

    @Autowired
    private IPaasTyyhService paasTyyhService;

    @Autowired
    private PassTyyhUserMapper passTyyhUserMapper;

    @Autowired
    private AppMapper appMapper;
    @Autowired
    private IOrgService orgService;

    @Autowired
    private IFilesService filesService;


    private static final List<String> options = Arrays.asList(
            "4591", "4592", "4593", "4594", "4595", "4596", "4597", "4598", "4599",
            "4600", "4601", "4602", "4603"
    );

    @Override
    public void addTYYHMessage(ApplicationInfo info) {
        OkHttpClient okHttpClient = this.initOkHttpClient();
        String appCode=null;
        try {
            addVentor(okHttpClient,info);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        App app = getAppCode(info.getAppName());
        if (app==null){
            try {
                appCode=addApp(okHttpClient,info);
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }else {
            appCode=app.getApplyName();
        }
        List<PassTyyhUser> passTyyhUsers = passTyyhUserMapper.selectList(new QueryWrapper<PassTyyhUser>().lambda().eq(PassTyyhUser::getAppInfoId,info.getId()));
        for (PassTyyhUser passTyyhUser : passTyyhUsers) {
            if ("0".equals(passTyyhUser.getType())){
                try {
                    this.addVentorUser(okHttpClient,info,passTyyhUser,appCode);
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }else if ("1".equals(passTyyhUser.getType())){
                try {
                    addGovern(okHttpClient,passTyyhUser);
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
                try {
                    //  添加政务人员
                    addGovUser(okHttpClient,passTyyhUser);
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }else {
                logger.error("人员类型不匹配！");
            }
        }
    }

    //  根据政务机构代码查询机构id
    private String getGovIdByCode(OkHttpClient okHttpClient,String code) throws IOException {
        Map<String,String> map=new HashMap<>();
        map.put("code",code);
        String url="http://68.29.149.111:8380/dcuc/api/government/org";
        Response response = OkHttpUtils.post(okHttpClient, url, map);
        String resultStr = response.body().string();
        logger.error("查询机构id: {}", resultStr);
        GovDto govDto = JSONObject.parseObject(resultStr, GovDto.class);
        if (!response.isSuccessful()) {
            logger.error(govDto.toString());
            response.close();
            throw new BaseException("查询机构id失败");
        }
        response.close();
        return govDto.getId();
    }

    //  添加政务人员
    private void addGovUser(OkHttpClient okHttpClient,PassTyyhUser passTyyhUser) throws IOException {
        Map<String,String> map=new HashMap<>();
        map.put("name",passTyyhUser.getName());
        map.put("idcard",passTyyhUser.getIdcard());
        map.put("nation","1");
        String govId = getGovIdByCode(okHttpClient, passTyyhUser.getGovCode());
        if (govId==null){
            throw new BaseException("获取政务机构ID失败！");
        }
        map.put("govId",govId);
        map.put("post",passTyyhUser.getPosition());
        map.put("mobileWork",passTyyhUser.getPhone());
        map.put("sfz","350825199505281912");
        String url=tyyhUrl+"/governUser";
        Response response = OkHttpUtils.get(okHttpClient, url, map);
        String resultStr = response.body().string();
        logger.error("添加政务人员: {}",resultStr);
        ResponseStatus responseStatus = JSONObject.parseObject(resultStr, ResponseStatus.class);
        if (!response.isSuccessful()) {
            logger.error(responseStatus.getMessage());
            response.close();
            throw new BaseException("添加政务人员失败");
        }else {
            logger.error("添加政务人员: {}",responseStatus.getMessage());
        }
        response.close();
    }

    private void addGovern(OkHttpClient okHttpClient,PassTyyhUser passTyyhUser) throws IOException {
        Map<String,String> map=new HashMap<>();
        map.put("code",passTyyhUser.getGovCode());
        map.put("fullName",passTyyhUser.getGovUnit());
        map.put("upGovId","c49a93896d066a05016d0670ef6c0000");
        map.put("sfz","350825199505281912");
        String url=tyyhUrl+"/government";
        Response response = OkHttpUtils.get(okHttpClient, url, map);
        String resultStr = response.body().string();
        logger.error("添加政务机构: {}",resultStr);
        ResponseStatus responseStatus = JSONObject.parseObject(resultStr, ResponseStatus.class);
        if (!response.isSuccessful()) {
            logger.error(responseStatus.getMessage());
            response.close();
            throw new BaseException("添加政务机构失败");
        }else {
            logger.error("添加政务机构: {}",responseStatus.getMessage());
        }
        response.close();
    }

    private App getAppCode(String appName){
        App app = appMapper.selectOne(new QueryWrapper<App>().lambda().eq(App::getApplyName, appName));
        return app;
    }

    //  添加服务商人员
    private void addVentorUser(OkHttpClient okHttpClient,ApplicationInfo info, PassTyyhUser passTyyhUser,String appCode) throws IOException {
        Map<String,String> map=new HashMap<>();
        map.put("name",passTyyhUser.getName());
        map.put("idcard",passTyyhUser.getIdcard());
        map.put("nation","1");
        map.put("manufacturerCode",info.getCjOrgCode());
        map.put("manufacturerName",info.getCjUnit());
        map.put("applyName",info.getProjectName());
        map.put("applyCode",appCode);
        map.put("mobileWork",passTyyhUser.getPhone());
        map.put("sfz","350825199505281912");
        String url=tyyhUrl+"/external";
        Response response = OkHttpUtils.get(okHttpClient, url, map);
        String resultStr = response.body().string();
        logger.error("添加服务商人员: {}",resultStr);
        ResponseStatus responseStatus = JSONObject.parseObject(resultStr, ResponseStatus.class);
        if (!response.isSuccessful()) {
            logger.error(responseStatus.getMessage());
            response.close();
            throw new BaseException("添加服务商人员失败");
        }else {
            logger.error("添加服务商人员: {}",responseStatus.getMessage());
        }
        response.close();
    }
    // 添加厂商
    private void addVentor(OkHttpClient okHttpClient,ApplicationInfo info) throws IOException {
        Map<String,String> map=new HashMap<>();
        map.put("manufacturerName",info.getCjUnit());
        map.put("name",info.getCjPrincipal());
        map.put("idcard",info.getCjPrincipalIdcard());
        map.put("phone",info.getCjPrincipalPhone());
        map.put("code",info.getCjOrgCode());
        map.put("sfz","350825199505281912");
        String url=tyyhUrl+"/manufacturer";
        Response response = OkHttpUtils.get(okHttpClient, url, map);
        String resultStr = response.body().string();
        logger.error("添加厂商: {}",resultStr);
        ResponseStatus responseStatus = JSONObject.parseObject(resultStr, ResponseStatus.class);
        if (!response.isSuccessful()) {
            logger.error(responseStatus.getMessage());
            response.close();
            throw new BaseException("添加厂商失败");
        }else {
            logger.error("添加厂商: {}",responseStatus.getMessage());
        }
        response.close();
    }
    //  添加应用
    private String addApp(OkHttpClient okHttpClient,ApplicationInfo info) throws IOException {
        Map<String,String> map=new HashMap<>();
        Org org = orgService.getOne(new QueryWrapper<Org>().lambda().eq(Org::getFullName, info.getJsUnit()));
        if (org==null){
            throw new BaseException("查询建设单位失败！");
        }
        map.put("applyName",info.getProjectName());
        map.put("orgCode",org.getCode());
        map.put("applyCategory","2");
        map.put("policeSpecies",PoliceCodeUtils.getCode(info.getPoliceCategory()));
        map.put("orgId",org.getId());
        map.put("manufacturerName",info.getCjUnit());
        map.put("manufacturerCode",info.getCjOrgCode());
        map.put("fzr",info.getCjPrincipal());
        map.put("fzrPhone",info.getCjPrincipalPhone());
        map.put("sfz","350825199505281912");
        String url=tyyhUrl+"/apply";
        Response response = OkHttpUtils.get(okHttpClient, url, map);
        String resultStr = response.body().string();
        logger.error("添加应用: {}",resultStr);
        ResponseStatus responseStatus = JSONObject.parseObject(resultStr, ResponseStatus.class);
        if (!response.isSuccessful()) {
            logger.error(responseStatus.getMessage());
            response.close();
            throw new BaseException("添加应用失败");
        }else {
            logger.error("添加应用: {}",responseStatus.getMessage());
        }
        response.close();
        return responseStatus.getResult();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public <I> Map<String, String> orderService(ApplicationInfo info, IImplHandler<I> implHandler) throws Exception {
        FormNum formNum = FormNum.getFormNumByInfo(info);
        if (formNum.getResourceType() != ResourceType.PAAS) {
            throw new BaseException("必须为PaaS申请");
        }
        if (StringUtils.isEmpty(info.getCreator())) {
            throw new BaseException("创建人不能为空");
        }
        if (StringUtils.isEmpty(info.getAppName())) {
            throw new BaseException("应用名称不能为空");
        }

        /*List<DaasApplication> serverList = daasApplicationService.getByAppInfoId(info.getId());
        if (serverList == null || serverList.isEmpty()) {
            throw new BaseException("申请服务不能为空");
        }*/
        PaasFseqsqm paasFseqsqm = paasFseqsqmService.getOne(new QueryWrapper<PaasFseqsqm>().eq("APP_INFO_ID", info.getId()));

        if (formNum.getDesc() == null || "".equals(formNum.getDesc())) {
            throw new BaseException("申请的服务不能为空");
        }
        logger.error("开始自动订购: {}", info.toString());
        dcucHttpEngin.createIamUser(info.getCreator());

        List<ProjectList.Project> pjcts = this.getUserProject(info, implHandler);
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
                errorLog(formNum, "用户登录失败", userJsonStr, info.getId(), implHandler);
                userLoginResponse.close();
                throw new BaseException("发布失败,失败原因:用户登录失败");
            }
            final String userToken = userLoginResponse.header("x-subject-token");
            userLoginResponse.close();
            LoginResponse user = JSONObject.parseObject(userJsonStr, LoginResponse.class);
            // 带有用户token的OkHttpClient
            OkHttpClient userClient = initOkHttpClient();
            userClient = userClient.newBuilder().addInterceptor(new TokenInterceptor(userToken)).build();
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
            errorLog(formNum, "管理员登录失败", managerJsonStr, info.getId(), implHandler);
            managerLoginResponse.close();
            throw new BaseException("发布失败,失败原因:管理员登录失败");
        }
        final String managerToken = managerLoginResponse.header("x-subject-token");
        managerLoginResponse.close();
        // 带有管理员token的OkHttpClient
        OkHttpClient managerClient = initOkHttpClient();
        managerClient = managerClient.newBuilder().addInterceptor(new TokenInterceptor(managerToken)).build();

        //通过id从paas表获取服务guid
        Paas paas = paasService.getById(info.getServiceTypeId());
        if (paas == null) {
            errorLog(formNum, "找不到所申请的PaaS服务", "", info.getId(), implHandler);
            throw new BaseException("找不到所申请的PaaS服务");
        }

        String serviceId = paas.getServiceGuid();
        if (StringUtils.isEmpty(serviceId)) {
            errorLog(formNum, "服务ID为空", "", info.getId(), implHandler);
            throw new BaseException("服务ID为空");
        }
        // 4.查询套餐信息(略)
        // 5.查询此用户的应用列表
        AppDetail existsApp = null;
        OkHttpClient okHttpClient = null;
        LoginResponse currentUser = null;
        look: for (UserClient userClient : userClients) {
            AppList appList = this.getUserAppList(userClient.getClient(), info.getId(), formNum, implHandler);
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
        ServiceDetail serviceDetail = this.getServiceDetail(okHttpClient, serviceId, info.getId(), formNum, implHandler);
        if (existsApp == null) {
            // 6.不存在,创建应用
            AppDetail appDetail = this.createApp(okHttpClient, info, formNum, implHandler);
            existsApp = appDetail;
        }

        // 7.订购服务
        ServiceInstance orderInstance = this.orderService(okHttpClient, info, serviceDetail, currentUser, existsApp, formNum, implHandler);
        // 8.审核
        ServiceInstance reviewInstance = this.review(managerClient, okHttpClient, orderInstance, info.getId(), formNum, implHandler);
        //进行绑定操作时需等待实例进入运行状态
        this.saveUserData(okHttpClient, formNum, reviewInstance.getInstance_guid(), info.getId(), implHandler);
        boolean flag = true;
        int count = 0;
        ServiceBindingResponse serviceBinding;
        do {
            Thread.sleep(3000);
            //9.绑定
            try {
                serviceBinding = this.binding(okHttpClient, reviewInstance, currentUser, serviceDetail, info, implHandler);
                flag = false;
                Map<String, String> map = new HashMap();
                map.put("appKey", serviceBinding.getCredentials().getAppKey());
                map.put("appSecret", serviceBinding.getCredentials().getAppSecret());
                //清空错误信息
                errorLog(formNum, "", "", info.getId(), implHandler);
                return map;
            } catch (Exception e) {
                count++;
                if (count > 2) {
                    throw new BaseException("订购失败");
                }
            }
        } while (flag);
        return null;
    }

    /**
     * 地图自动订购
     *
     * @param info
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public <I> Map<String, String> mapOrderService(ApplicationInfo info, IImplHandler<I> iImplHandler) throws Exception {
        FormNum formNum = FormNum.getFormNumByInfo(info);
        if (formNum.getResourceType() != ResourceType.PAAS) {
            throw new BaseException("必须为PaaS申请");
        }
        if (StringUtils.isEmpty(info.getCreator())) {
            throw new BaseException("创建人不能为空");
        }

        if (StringUtils.isEmpty(info.getAppName())) {
            throw new BaseException("应用名称不能为空");
        }

        if (formNum.getDesc() == null || "".equals(formNum.getDesc())) {
            throw new BaseException("申请的服务不能为空");
        }


        logger.error("开始自动订购: {}", info.toString());
        dcucHttpEngin.createIamUser(info.getCreator());
        ProjectList.Project pjct = this.getUserNormalProject(info, iImplHandler);

        // 1.普通用户登录
        String loginUrl = cscBaseUrlV3 + "/auth/tokens";
        LoginRequest userRequest = this.buildUserRequest(info);
        LoginRequest.Auth.Scope.Project project = new LoginRequest.Auth.Scope.Project();

        project.setName(pjct.getName());
        userRequest.getAuth().getScope().setProject(project);
        OkHttpClient client = this.initOkHttpClient();
        Response userLoginResponse = OkHttpUtils.postJson(client, loginUrl, JSONObject.toJSONString(userRequest));
        String userJsonStr = userLoginResponse.body().string();
        if (!userLoginResponse.isSuccessful()) {
            logger.error("用户登录失败: {}", userJsonStr);
            userLoginResponse.close();
            throw new BaseException(401, "订购失败,失败原因:用户登录失败");
        }
        final String userToken = userLoginResponse.header("x-subject-token");
        userLoginResponse.close();
        LoginResponse user = JSONObject.parseObject(userJsonStr, LoginResponse.class);

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
        // 带有用户token的OkHttpClient
        OkHttpClient userClient = initOkHttpClient();
        userClient = userClient.newBuilder().addInterceptor(new TokenInterceptor(userToken)).build();
        // 带有管理员token的OkHttpClient
        OkHttpClient managerClient = initOkHttpClient();
        managerClient = managerClient.newBuilder().addInterceptor(new TokenInterceptor(managerToken)).build();

        //通过id从paas表获取服务guid
        Paas paas = paasService.getById(info.getServiceTypeId());
        if (paas == null) {
            throw new BaseException("找不到所申请的PaaS服务");
        }

        String serviceId = paas.getServiceGuid();
        if (StringUtils.isEmpty(serviceId)) {
            throw new BaseException("服务ID为空");
        }
        ServiceDetail serviceDetail = this.getServiceDetail(userClient, serviceId, info.getId(), formNum, iImplHandler);


        // 7.订购服务
        ServiceInstance orderInstance = this.orderService(userClient, info, serviceDetail, user, null, formNum, iImplHandler);

        /**if(formNum == FormNum.PAAS_DTHTJY){
         orderInstance =
         }
         if(formNum == FormNum.PAAS_DTSJGT){
         orderInstance = this.orderService(userClient, info, serviceDetail, user, null,2);
         }*/

        // 8.审核
        ServiceInstance reviewInstance = this.review(managerClient, userClient, orderInstance, info.getId(), formNum, iImplHandler);
        // 获取api列表
        this.saveUserData(userClient, formNum, reviewInstance.getInstance_guid(), info.getId(), iImplHandler);

        //进行绑定操作时需等待实例进入运行状态
        boolean flag = true;
        int count = 0;
        String credentials;
        do {
            Thread.sleep(3000);
            //9.绑定
            try {
                credentials = this.MapBinding(userClient, reviewInstance, user, serviceDetail, formNum, info.getId(), iImplHandler);
                flag = false;
                Map<String, String> map = new HashMap();
                map.put("orderResult", credentials);
                errorLog(formNum, "", "", info.getId(), iImplHandler);
                return map;
            } catch (Exception e) {
                count++;
                if (count > 2) {
                    throw new BaseException("订购失败");
                }
            }
        } while (flag);
        return null;
    }

    /**
     * 一键发布服务
     *
     * @param servicePublish
     * @return
     * @throws Exception
     */
    @Override
    public ServiceReturnBean apigOrderService(ServicePublish servicePublish) throws Exception {
        //  记录服务id及错误信息的对象
        ServiceReturnBean serviceReturnBean = new ServiceReturnBean();
        if (StringUtils.isEmpty(servicePublish.getCreator())) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("创建人为空");
            throw new BaseException("创建人不能为空");
        }
        dcucHttpEngin.createIamUser(servicePublish.getCreator());
        ProjectList.Project pjct = this.getUserNormalProject(servicePublish, serviceReturnBean);
        // 1.普通用户登录
        String loginUrl = cscBaseUrlV3 + "/auth/tokens";
        LoginRequest userRequest = this.buildUserRequest(servicePublish);
        LoginRequest.Auth.Scope.Project project = new LoginRequest.Auth.Scope.Project();
        project.setName(pjct.getName());
        userRequest.getAuth().getScope().setProject(project);
        OkHttpClient client = this.initOkHttpClient();
        Response userLoginResponse = OkHttpUtils.postJson(client, loginUrl, JSONObject.toJSONString(userRequest));
        String userJsonStr = userLoginResponse.body().string();
        if (!userLoginResponse.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("用户信息验证失败");
            logger.error("用户登录失败: {}", userJsonStr);
            userLoginResponse.close();
            throw new BaseException(401, "发布服务,失败原因:用户登录失败");
        }
        final String userToken = userLoginResponse.header("x-subject-token");
        userLoginResponse.close();
        // 2.管理员用户登录
        LoginRequest managerRequest = this.buildManagerRequest2();
        Response managerLoginResponse = OkHttpUtils.postJson(client, loginUrl, JSONObject.toJSONString(managerRequest));
        String managerJsonStr = managerLoginResponse.body().string();
        if (!managerLoginResponse.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("管理员信息验证失败");
            logger.error("管理员登录失败: {}", managerJsonStr);
            managerLoginResponse.close();
            throw new BaseException(401, "发布服务,失败原因:管理员登录失败");
        }
        final String managerToken = managerLoginResponse.header("x-subject-token");
        managerLoginResponse.close();
        // 带有用户token的OkHttpClient
        OkHttpClient userClient = initOkHttpClient();
        userClient = userClient.newBuilder().addInterceptor(new TokenInterceptor(userToken)).build();
        // 带有管理员token的OkHttpClient
        OkHttpClient managerClient = initOkHttpClient();
        managerClient = managerClient.newBuilder().addInterceptor(new TokenInterceptor(managerToken)).build();
        // 存放所有创建好的后端服务、api、api产品的id
        Map<String, List<String>> okId = new HashMap<>();
        List<Map<String, Object>> serviceList = null;
        if (servicePublish.getWhereFrom().equals("1")) {
            // 3.创建后端服务
            serviceList = this.createService(userClient, servicePublish.getBackendList(), servicePublish.getOrderNumber(), serviceReturnBean, okId);
        } else if (servicePublish.getWhereFrom().equals("2")) {
            serviceList = this.selectService(userClient, servicePublish.getBackendList(), serviceReturnBean);
        } else {
            throw new BaseException("数据来源参数错误");
        }
        // 4.创建API、API操作、API操作绑定后端服务
        List<Map<String, Object>> apiList = this.createApi(userClient, servicePublish.getApiList(), serviceList, serviceReturnBean, okId, servicePublish.getWhereFrom());
        // 5.创建API产品
        Map<String, Object> apiProductMap = this.createApiProduct(userClient, servicePublish.getApiProduct(), serviceReturnBean, okId);
        // 获取api产品的id
        String apiProductId = (String) apiProductMap.get("id");
        if (apiProductId != null) {
            // 6.为API产品绑定API
            this.apiProductBinding(userClient, servicePublish.getApiProduct(), apiProductId, apiList, serviceReturnBean, okId);
            // 7.部署API产品
            this.deployApiProduct(userClient, apiProductId, servicePublish.getApiProduct(), serviceReturnBean, okId);
            for (int i = 0; i < 3; i++) {
                Thread.sleep(3000);
                // 8.发布API产品
                if (this.publishApiProductMap(userClient, apiProductId, serviceReturnBean, okId)) {
                    break;
                } else {
                    if (i == 2) {
                        deleteCreated(userClient, okId);
                        throw new BaseException("API产品发布失败");
                    } else {
                        this.deployApiProduct(userClient, apiProductId, servicePublish.getApiProduct(), serviceReturnBean, okId);
                    }
                }
            }
            // 上传图片
//            this.uploadPic(userClient,servicePublish);
            //  清除发布失败信息
            serviceReturnBean.setErrorMessage(null);
            serviceReturnBean.setError(false);
            // 9.服务发布
            Map<String, Object> publishServiceMap = this.publishService(userClient, apiProductId, servicePublish, serviceReturnBean, okId);
            //  获取服务Id
            String serviceId = (String) publishServiceMap.get("service_id");
            serviceReturnBean.setServiceId(serviceId);
            if (serviceId != null) {
                logger.info("发布服务的ID:" + serviceId);
                // 10.服务审核
                this.approvalService(managerClient, serviceId, serviceReturnBean, okId);
            } else {
                serviceReturnBean.setError(true);
                serviceReturnBean.setErrorMessage("发布服务失败");
                logger.error("获取发布服务的Id失败");
                throw new BaseException("获取发布服务的Id失败");
            }
        } else {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("绑定API失败");
            logger.error("获取api产品的Id失败");
            deleteCreated(userClient, okId);
            throw new BaseException("获取api产品的Id失败");
        }
        return serviceReturnBean;
    }

    private List<Map<String, Object>> selectService(OkHttpClient userClient, List<ServicePublishBackend> backendList, ServiceReturnBean serviceReturnBean) throws Exception {
        logger.info("-------------------进入查询后端服务流程------------------");
        List<Map<String, Object>> returnList = new ArrayList<>();
        if (backendList == null && backendList.size() == 0) {
            throw new BaseException(400, "后端服务列表为空");
        }
        //  请求参数
        Map<String, String> map = new HashMap<>();
        map.put("name", backendList.get(0).getName());
        //  请求地址
        String url = apigBaseUrl + "/v1/apigw/extension/back-endpoints";
        //  调用创建后端服务接口
        Response response = OkHttpUtils.get(userClient, url, map);
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("查询后端服务失败");
            logger.error("查询后端服务: {}", resultStr);
            response.close();
            throw new BaseException("查询后端服务失败");
        } else {
            Map<String, Object> returnMap = JSONObject.parseObject(resultStr, new TypeReference<Map<String, Object>>() {
            });
            returnList.add(returnMap);
        }
        response.close();
        return returnList;
    }


    /**
     * 失败后删除已经创建的部分
     *
     * @param okId
     */
    private void deleteCreated(OkHttpClient userClient, Map<String, List<String>> okId) throws Exception {
        //  获取创建好的api产品
        List<String> apiProductIdList = okId.get("api产品");
        if (apiProductIdList != null) {
            System.out.println(apiProductIdList);
            for (String apiProductId : apiProductIdList) {
                //  请求地址
                String url = apigBaseUrl + "/v1/apigw/extension/products/" + apiProductId;
                //  调用删除api产品接口
                Response response = OkHttpUtils.delete(userClient, url);
                String resultStr = response.body().string();
                if (!response.isSuccessful()) {
                    logger.error("删除api产品: {}", resultStr);
                    logger.error("删除失败的api产品ID:" + apiProductId);
                }
                response.close();
            }
        }
        //  获取创建好的api
        List<String> apiIdList = okId.get("api");
        if (apiIdList != null) {
            System.out.println(apiIdList);
            for (String apiId : apiIdList) {
                //  请求地址
                String url = apigBaseUrl + "/v1/apigw/extension/apis/" + apiId;
                //  调用删除api接口
                Response response = OkHttpUtils.delete(userClient, url);
                String resultStr = response.body().string();
                if (!response.isSuccessful()) {
                    logger.error("删除api: {}", resultStr);
                    logger.error("删除失败的apiID:" + apiId);
                }
                response.close();
            }
        }
        //  获取创建好的后端服务
        List<String> serviceIdList = okId.get("后端服务");
        if (serviceIdList != null) {
            System.out.println(serviceIdList);
            for (String serviceId : serviceIdList) {
                //  请求地址
                String url = apigBaseUrl + "/v1/apigw/extension/back-endpoints/" + serviceId;
                //  调用删除后端服务接口
                Response response = OkHttpUtils.delete(userClient, url);
                String resultStr = response.body().string();
                if (!response.isSuccessful()) {
                    logger.error("删除后端服务: {}", resultStr);
                    logger.error("删除失败的后端服务ID:" + serviceId);
                }
                response.close();
            }
        }
    }

    private LoginRequest buildUserRequest(ServicePublish servicePublish) {
        LoginRequest userRequest = JSONObject.parseObject(USER_LOGIN_JSON, LoginRequest.class);
        LoginRequest.Auth.Identity.Password.User user = userRequest.getAuth().getIdentity().getPassword().getUser();
        user.setName(servicePublish.getCreator());
        userRequest.getAuth().getScope().setProject(null);
        user.setPassword("Gdga12#$");
        return userRequest;
    }

    private ProjectList.Project getUserNormalProject(ServicePublish servicePublish, ServiceReturnBean serviceReturnBean) throws Exception {
        // 1.普通用户登录
        String loginUrl = cscBaseUrlV3 + "/auth/tokens";
        LoginRequest userRequest = this.buildUserRequest(servicePublish);
        OkHttpClient client = this.initOkHttpClient();
        Response userLoginResponse = OkHttpUtils.postJson(client, loginUrl, JSONObject.toJSONString(userRequest));
        String userJsonStr = userLoginResponse.body().string();
        if (!userLoginResponse.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("用户信息验证失败");
            logger.error("服务失败,失败原因:用户登录失败");
            userLoginResponse.close();
            throw new BaseException(401, "订购失败,失败原因:用户登录失败");
        }
        userLoginResponse.close();
        LoginResponse user = JSONObject.parseObject(userJsonStr, LoginResponse.class);
        // 2.管理员用户登录
        LoginRequest managerRequest = this.buildManagerRequest();
        Response managerLoginResponse = OkHttpUtils.postJson(client, loginUrl, JSONObject.toJSONString(managerRequest));
        String managerJsonStr = managerLoginResponse.body().string();
        if (!managerLoginResponse.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("管理员信息验证失败");
            logger.error("发布服务失败,失败原因:管理员登录失败:{}", managerJsonStr);
            managerLoginResponse.close();
            throw new BaseException(401, "发布服务失败,失败原因:管理员登录失败");
        }
        final String managerToken = managerLoginResponse.header("x-subject-token");
        managerLoginResponse.close();
        // 带有管理员token的OkHttpClient
        OkHttpClient managerClient = initOkHttpClient();
        managerClient = managerClient.newBuilder().addInterceptor(new TokenInterceptor(managerToken)).build();
        ProjectList.Project project = getUserNormalProject(managerClient, user.getToken().getUser().getId(), serviceReturnBean);
        if (project == null) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("管理员信息验证失败");
            logger.error("发布服务失败,失败原因:获取状态正常的项目失败");
            throw new BaseException(401, "发布服务失败,失败原因:获取状态正常的项目失败");
        }
        logger.error("用户正常状态的项目: {}", project.toString());
        return project;
    }

    private ProjectList.Project getUserNormalProject(OkHttpClient client, String userId, ServiceReturnBean serviceReturnBean) throws Exception {
        String url = projectBaseUrl + "/rest/vdc/v3.1/users/" + userId + "/projects?start=0&limit=100&rel_projects=true";
        Response response = OkHttpUtils.get(client, url, null);
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("管理员信息验证失败");
            logger.error("发布服务失败,失败原因:管理员登录失败");
            response.close();
            throw new BaseException(401, "订购失败,失败原因:获取状态正常的项目失败");
        }
        response.close();
        ProjectList projectList = JSONObject.parseObject(resultStr, ProjectList.class);
        List<ProjectList.Project> projects = projectList.getProjects();
        if (projects == null || projects.isEmpty()) {
            return null;
        }
        for (ProjectList.Project project : projects) {
            if ("normal".equals(project.getRegions().get(0).getRegion_status())) {
                return project;
            }
        }
        return null;
    }

    /**
     * 服务审核
     *
     * @param managerClient
     * @param serviceId     发布服务的Id
     * @return
     */
    private Map<String, Object> approvalService(OkHttpClient managerClient, String serviceId, ServiceReturnBean serviceReturnBean, Map<String, List<String>> okId) throws Exception {
        //  创建服务发布返回值
        Map<String, Object> returnMap;
        System.out.println("-----------serviceId:" + serviceId);
        //  请求地址
        String url = cscBaseUrlV2 + "/services/" + serviceId + "/approval";
        //  调用服务发布接口
        Response response = OkHttpUtils.post(managerClient, url, new HashMap<>());
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("服务审核失败");
            logger.error("服务审核: {}", resultStr);
            response.close();
            throw new BaseException("服务审核");
        } else {
            returnMap = JSONObject.parseObject(resultStr, new TypeReference<Map<String, Object>>() {
            });
        }
        response.close();
        return returnMap;
    }

    private Map<String, String> getPlansMes(OkHttpClient userClient, String apiProductId, ServiceReturnBean serviceReturnBean) throws IOException {
        //  请求地址
        String url = apigBaseUrl + "/v1/apigw/extension/products/" + apiProductId;
        //  调用创建后端服务接口
        Response response = OkHttpUtils.get(userClient, url, new HashMap<>());
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("查询api产品详情失败");
            logger.error("查询api产品详情: {}", resultStr);
            response.close();
            throw new BaseException("查询api产品详情失败");
        }
        response.close();
        JSONObject jsonObject = JSONObject.parseObject(resultStr);
        List<Map<String, String>> plans = (List<Map<String, String>>) jsonObject.get("plans");
        return plans.get(0);
    }

    /**
     * 服务发布
     *
     * @param userClient
     * @param apiProductId   api产品Id
     * @param servicePublish 服务发布请求信息
     * @return
     */
    private Map<String, Object> publishService(OkHttpClient userClient, String apiProductId, ServicePublish servicePublish, ServiceReturnBean serviceReturnBean, Map<String, List<String>> okId) throws Exception {
        //  创建服务发布返回值
        Map<String, Object> returnMap;
        //  定义服务发布请求数据的map
        Map<String, Object> reqpublishService = new HashMap<>();

        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("REF_ID", servicePublish.getId());
        List<Files> files = filesService.list(wrapper);
        for (Files item : files) {
            String upFileUrl = fileUpdataBaseUrl + "/swr/v2/domains/gdjwy/namespaces/fwfb_doc/repositories/fwfb_re/packages/fwfb_package/versions/1.0.0/file_paths/" + item.getOriginaName();
            String localFilePath = item.getPath() + "/" + item.getName()+item.getSuffix();
            Response fileResponse = OkHttpUtils.putFile(userClient, MediaType.parse("file"), upFileUrl, localFilePath);
            if (fileResponse.isSuccessful()) {
                if (item.getPath().contains("examine")) {
                    reqpublishService.put("documentation_url", upFileUrl);
                } else if (item.getPath().contains("interface")) {
                    reqpublishService.put("sdk_url", upFileUrl);
                }
            }
            fileResponse.close();
        }

        //  添加请求参数
        reqpublishService.put("name", "zidong-" + servicePublish.getOrderNumber());
        reqpublishService.put("alias", servicePublish.getServiceName());
        reqpublishService.put("domain_id", "GDJWY");
        reqpublishService.put("is_public", true);
        //reqpublishService.put("logo_url",servicePublish.getLogoUrl());
        //  定义参数resource_pool集合
        List<String> resource_pool = new ArrayList<>();
        resource_pool.add("test");
        resource_pool.add("dev");
        resource_pool.add("prod");

        reqpublishService.put("resource_pool", resource_pool);
        // reqpublishService.put("catalog",servicePublish.getCategory());
        if (servicePublish.getServiceType().equals("DAAS")) {
            reqpublishService.put("catalog", "数据服务");
        } else if (servicePublish.getServiceType().equals("PAAS")) {
            reqpublishService.put("catalog", servicePublish.getCategory());
        } else {
            reqpublishService.put("catalog", "通用应用");
        }
        reqpublishService.put("description", servicePublish.getRemark());
        //  定义参数plans集合
        List<Map<String, Object>> plans = new ArrayList<>();
        Map<String, String> map = getPlansMes(userClient, apiProductId, serviceReturnBean);
        //  定义plans中存放参数的map
        Map<String, Object> plansMap = new HashMap<>();
        plansMap.put("blueprint_id", apiProductId);
        plansMap.put("name", map.get("name"));
        plansMap.put("guid", map.get("id"));
        plans.add(plansMap);
        reqpublishService.put("plans", plans);
        reqpublishService.put("parameters", "{}");
        reqpublishService.put("provision_para", "{}");
        reqpublishService.put("bind_parameters", "{}");
        String metadata_define = "{\"apiproduct_id\":{\"type\":\"string\",\"domain\":\"\",\"default\":\"" + apiProductId + "\",\"alias\":\"\",\"read_only\":false,\"optional\":false,\"check\":false,\"changed\":false,\"range_min\":0,\"range_max\":0,\"double_check\":false,\"prompt\":\"\",\"multi_select\":false}}";
        reqpublishService.put("metadata_define", metadata_define);
        reqpublishService.put("broker_custom_tag", "{}");
        String all_version = "[{\"description\":\"" + servicePublish.getApiProduct().getVersion() + "\",\"metadata\":{\"apiproduct_id\":\"" + apiProductId + "\"},\"version_code\":\"" + servicePublish.getApiProduct().getVersion() + "\"}]";
        reqpublishService.put("all_version", all_version);
        reqpublishService.put("mode", "APIMode");
        reqpublishService.put("console_tls_backend", true);
        reqpublishService.put("broker_use_https", false);
        reqpublishService.put("console_use_https", false);
        reqpublishService.put("skip_console_health_check", false);
        reqpublishService.put("memory_limit", "1024M");
        reqpublishService.put("cluster_id", "11111111-1111-1111-1111-111111111111");
        reqpublishService.put("vendor", servicePublish.getVendor());
        reqpublishService.put("local", "zh-cn");
        //  请求地址
        String url = cscBaseUrlV2 + "/services";
        //  调用服务发布接口
        Response response = OkHttpUtils.postJson(userClient, url, JSON.toJSONString(reqpublishService));
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("服务发布失败");
            logger.error("服务发布: {}", resultStr);
            response.close();
            deleteCreated(userClient, okId);
            throw new BaseException("服务发布失败");
        } else {
            returnMap = JSONObject.parseObject(resultStr, new TypeReference<Map<String, Object>>() {
            });
        }
        response.close();
        return returnMap;
    }

    /**
     * 发布API产品
     *
     * @param userClient
     * @param apiProductId api产品id
     * @return
     * @throws Exception
     */
    private boolean publishApiProductMap(OkHttpClient userClient, String apiProductId, ServiceReturnBean serviceReturnBean, Map<String, List<String>> okId) throws Exception {
        //  创建发布API产品返回值
        Map<String, Object> returnMap;
        //  定义发布API产品请求数据的map
        Map<String, Object> reqpublishApiProduct = new HashMap<>();
        reqpublishApiProduct.put("action", "publish");
        //  请求地址
        String url = apigBaseUrl + "/v1/apigw/extension/products/" + apiProductId + "/action";
        //  调用发布API产品接口
        Response response = OkHttpUtils.postJson(userClient, url, JSON.toJSONString(reqpublishApiProduct));
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("发布API产品失败");
            logger.error("发布API产品: {}", resultStr);
            response.close();
            return false;
        }
        response.close();
        return true;
    }

    /**
     * 部署API产品
     *
     * @param apiProductId api产品id
     * @param apiProduct   api产品信息
     * @return
     */
    private Map<String, Object> deployApiProduct(OkHttpClient userClient, String apiProductId, ServicePublishApiProduct apiProduct, ServiceReturnBean serviceReturnBean, Map<String, List<String>> okId) throws Exception {
        //  创建部署API产品返回值
        Map<String, Object> returnMap;
        //  定义部署API产品请求数据的map
        Map<String, Object> reqdeployApiProduct = new HashMap<>();
        reqdeployApiProduct.put("action", "deploy");
        //  定义deploy_microgw_id_list参数集合
        List<String> deploy_microgw_id_list = new ArrayList<>();
        // 根据微网关名获取微网关id
        String microgId = this.getMicrogwId(userClient, "microgateway_68.26.19.197", serviceReturnBean);
        if (microgId == null) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("发布api产品获取微网关id失败");
            logger.error("发布api产品获取微网关id失败");
            deleteCreated(userClient, okId);
            throw new BaseException("发布api产品获取微网关id失败");
        }
        deploy_microgw_id_list.add(microgId);
        reqdeployApiProduct.put("deploy_microgw_id_list", deploy_microgw_id_list);
        //  请求地址
        String url = apigBaseUrl + "/v1/apigw/extension/products/" + apiProductId + "/action";
        //  调用部署API产品接口
        Response response = OkHttpUtils.postJson(userClient, url, JSON.toJSONString(reqdeployApiProduct));
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("部署API产品失败");
            response.close();
            deleteCreated(userClient, okId);
            logger.error("部署API产品: {}", resultStr);
            throw new BaseException("部署API产品失败");
        } else {
            returnMap = JSONObject.parseObject(resultStr, new TypeReference<Map<String, Object>>() {
            });
        }
        response.close();
        return returnMap;
    }

    /**
     * 为API产品绑定API
     *
     * @param userClient
     * @param apiProduct   API产品信息
     * @param apiProductId API产品的id
     * @param apiList      需要绑定的api信息集合
     * @return
     */
    private Map<String, Object> apiProductBinding(OkHttpClient userClient, ServicePublishApiProduct apiProduct, String apiProductId, List<Map<String, Object>> apiList, ServiceReturnBean serviceReturnBean, Map<String, List<String>> okId) throws Exception {
        //  创建为API产品绑定API返回值
        Map<String, Object> returnMap;
        //  定义为API产品绑定API请求数据的map
        Map<String, Object> reqapiProductBinding = new HashMap<>();
        reqapiProductBinding.put("name", apiProduct.getName());
        reqapiProductBinding.put("version", apiProduct.getVersion());
        reqapiProductBinding.put("remark", apiProduct.getRemark());
        //  定义参数apis的集合
        List<Map<String, Object>> apis = new ArrayList<>();
        //  循环遍历获取api的id
        for (Map<String, Object> apiMap : apiList) {
            Map<String, Object> idMap = new HashMap<>();
            String apiId = (String) apiMap.get("id");
            idMap.put("id", apiId);
            apis.add(idMap);
        }
        reqapiProductBinding.put("apis", apis);
        //  请求地址
        String url = apigBaseUrl + "/v1/apigw/extension/products/" + apiProductId;
        //  调用为API产品绑定API接口
        Response response = OkHttpUtils.putJson(userClient, url, JSON.toJSONString(reqapiProductBinding));
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("绑定API失败");
            logger.error("为API产品绑定API: {}", resultStr);
            response.close();
            deleteCreated(userClient, okId);
            throw new BaseException("为API产品绑定API失败");
        } else {
            returnMap = JSONObject.parseObject(resultStr, new TypeReference<Map<String, Object>>() {
            });
        }
        response.close();
        return returnMap;
    }

    /**
     * 创建api产品
     *
     * @param userClient
     * @param apiProduct 需要创建api产品的信息
     * @return
     */
    private Map<String, Object> createApiProduct(OkHttpClient userClient, ServicePublishApiProduct apiProduct, ServiceReturnBean serviceReturnBean, Map<String, List<String>> okId) throws Exception {
        //  存放api的id
        List<String> idList = new ArrayList<>();
        Map<String, Object> returnMap;
        //  定义创建api产品请求数据的map
        Map<String, String> reqServicePublishApiProduct = new HashMap<>();
        reqServicePublishApiProduct.put("name", apiProduct.getName());
        reqServicePublishApiProduct.put("version", apiProduct.getVersion());
        reqServicePublishApiProduct.put("visibility", "public");
        reqServicePublishApiProduct.put("remark", apiProduct.getRemark());
        //  请求地址
        String url = apigBaseUrl + "/v1/apigw/extension/products";
        //  调用创建api产品接口
        Response response = OkHttpUtils.postJson(userClient, url, JSON.toJSONString(reqServicePublishApiProduct));
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("创建api产品失败");
            logger.error("创建api产品: {}", resultStr);
            response.close();
            deleteCreated(userClient, okId);
            throw new BaseException("创建api产品失败");
        } else {
            returnMap = JSONObject.parseObject(resultStr, new TypeReference<Map<String, Object>>() {
            });
            String id = (String) returnMap.get("id");
            idList.add(id);
        }
        okId.put("api产品", idList);
        response.close();
        return returnMap;
    }

    /**
     * 创建Api
     *
     * @param userClient
     * @param apiList    申请创建Api的信息集合
     * @return
     */
    private List<Map<String, Object>> createApi(OkHttpClient userClient, List<ServicePublishApi> apiList, List<Map<String, Object>> serviceList, ServiceReturnBean serviceReturnBean, Map<String, List<String>> okId, String whereFrom) throws Exception {
        //  创建Api返回值集合
        List<Map<String, Object>> returnList = new ArrayList<>();
        //  存放api的id
        List<String> idList = new ArrayList<>();
        //  循环创建Api
        for (ServicePublishApi servicePublishApi : apiList) {
            //  定义创建Api请求数据的map
            Map<String, String> reqServicePublishApi = new HashMap<>();
            //  添加请求参数
            reqServicePublishApi.put("name", servicePublishApi.getName());
            reqServicePublishApi.put("version", servicePublishApi.getVersion());
            reqServicePublishApi.put("base_path", servicePublishApi.getBasePath());
            reqServicePublishApi.put("remark", servicePublishApi.getRemark());
            reqServicePublishApi.put("protocol", servicePublishApi.getProtocol());
            //reqServicePublishApi.put("auth_type",servicePublishApi.getAuthType());
            if (servicePublishApi.getAuthType().equals("无认证")) {
                reqServicePublishApi.put("auth_type", "NONE");
            } else if (servicePublishApi.getAuthType().equals("APP Key认证")) {
                reqServicePublishApi.put("auth_type", "APP");
            } else if (servicePublishApi.getAuthType().equals("APP Token认证")) {
                reqServicePublishApi.put("auth_type", "APP_TOKEN");
            }
            //  请求地址
            String url = apigBaseUrl + "/v1/apigw/extension/apis";
            //  调用创建Api接口
            Response response = OkHttpUtils.postJson(userClient, url, JSON.toJSONString(reqServicePublishApi));
            String resultStr = response.body().string();
            if (!response.isSuccessful()) {
                serviceReturnBean.setError(true);
                serviceReturnBean.setErrorMessage("创建api失败");
                logger.error("创建api: {}", resultStr);
                response.close();
                deleteCreated(userClient, okId);
                throw new BaseException("创建api失败");
            } else {
                response.close();
                Map<String, Object> returnCreateApi = JSONObject.parseObject(resultStr, new TypeReference<Map<String, Object>>() {
                });
                returnList.add(returnCreateApi);
                Object apiId = returnCreateApi.get("id");
                if (apiId != null) {
                    try {
                        String apiIdToStr = (String) apiId;
                        idList.add(apiIdToStr);
                        okId.put("api", idList);
                        //  为api添加操作
                        this.createOperation(userClient, apiIdToStr, servicePublishApi.getApiOperationList(), serviceList, serviceReturnBean, okId, whereFrom);
                    } catch (ClassCastException e) {
                        serviceReturnBean.setError(true);
                        serviceReturnBean.setErrorMessage("为api添加操作失败");
                        deleteCreated(userClient, okId);
                        throw new BaseException("apiId类型转换失败");
                    }
                } else {
                    serviceReturnBean.setError(true);
                    serviceReturnBean.setErrorMessage("为api添加操作失败");
                    logger.error("获取apiId失败");
                    deleteCreated(userClient, okId);
                    throw new BaseException("获取apiId失败");
                }
            }
        }
        return returnList;
    }

    /**
     * 为api添加操作
     *
     * @param apiIdToStr       api的Id
     * @param apiOperationList 需要添加的操作信息的集合
     * @return
     */
    private List<Map<String, Object>> createOperation(OkHttpClient userClient, String apiIdToStr, List<ApiOperation> apiOperationList, List<Map<String, Object>> serviceList, ServiceReturnBean serviceReturnBean, Map<String, List<String>> okId, String whereFrom) throws Exception {
        //  为api添加操作返回值集合
        List<Map<String, Object>> returnList = new ArrayList<>();
        //  循环为api添加操作
        for (ApiOperation apiOperation : apiOperationList) {
            //  定义为api添加操作请求数据的map
            Map<String, Object> reqApiOperation = new HashMap<>();
            reqApiOperation.put("name", apiOperation.getName());
            reqApiOperation.put("path", apiOperation.getPath());
            reqApiOperation.put("method", apiOperation.getMethod());
            reqApiOperation.put("match_mode", apiOperation.getMatchMode());
            if (apiOperation.getWithOrchestration().equals("1")) {
                reqApiOperation.put("with_orchestration", true);
            } else {
                reqApiOperation.put("with_orchestration", false);
            }
            //  根据后端服务名查询其ID
            String serviceId = this.findServiceById(apiOperation.getBackendName(), serviceList, whereFrom);
            if (serviceId != null) {
                reqApiOperation.put("backend_id", serviceId);
                reqApiOperation.put("backend_type", apiOperation.getBackendType());
                //  参数specified_backend_operation
                Map<String, String> backend_operation = new HashMap<>();
                backend_operation.put("path", apiOperation.getBackendPath());
                backend_operation.put("method", apiOperation.getBackendMethod());
                reqApiOperation.put("specified_backend_operation", backend_operation);
                //  请求地址
                String url = apigBaseUrl + "/v1/apigw/extension/apis/" + apiIdToStr + "/operations";
                //  调用为api添加操作接口
                Response response = OkHttpUtils.postJson(userClient, url, JSON.toJSONString(reqApiOperation));
                String resultStr = response.body().string();
                if (!response.isSuccessful()) {
                    serviceReturnBean.setError(true);
                    serviceReturnBean.setErrorMessage("为api添加操作失败");
                    logger.error("为api添加操作: {}", resultStr);
                    response.close();
                    deleteCreated(userClient, okId);
                    throw new BaseException("为api添加操作失败");
                } else {
                    returnList.add(JSONObject.parseObject(resultStr, new TypeReference<Map<String, Object>>() {
                    }));
                }
                response.close();
            } else {
                serviceReturnBean.setError(true);
                serviceReturnBean.setErrorMessage("为api添加操作失败");
                logger.error("获取后端服务id失败: {}", apiOperation.getBackendName());
                deleteCreated(userClient, okId);
                throw new BaseException("获取不到名字为" + apiOperation.getBackendName() + "的后端服务id");
            }
        }
        return returnList;
    }

    /**
     * 根据后端服务名查询其ID
     *
     * @param serviceName 服务名
     * @param serviceList 服务信息集合
     * @param whereFrom   数据来源
     * @return
     */
    private String findServiceById(String serviceName, List<Map<String, Object>> serviceList, String whereFrom) {
        if (whereFrom.equals("1")) {
            for (Map<String, Object> stringObjectMap : serviceList) {
                String serviceNameByFind = (String) stringObjectMap.get("name");
                System.out.println(serviceNameByFind);
                if (serviceNameByFind.contains(serviceName)) {
                    return (String) stringObjectMap.get("id");
                }
            }
        } else if (whereFrom.equals("2")) {
            for (Map<String, Object> stringObjectMap : serviceList) {
                logger.info("查询后端服务返回参数：" + stringObjectMap.toString());
                List<Map<String, Object>> list = (List<Map<String, Object>>) stringObjectMap.get("backend_list");
                if (list != null && list.size() != 0) {
                    return (String) list.get(0).get("id");
                }
            }
        } else {
            throw new BaseException("数据来源参数错误");
        }
        return null;
    }

    /**
     * 创建后端服务
     *
     * @param userClient
     * @param list       申请创建后端服务的信息集合
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> createService(OkHttpClient userClient, List<ServicePublishBackend> list, String orderNumber, ServiceReturnBean serviceReturnBean, Map<String, List<String>> okId) throws Exception {
        //  创建后端服务返回值集合
        List<Map<String, Object>> returnList = new ArrayList<>();
        //  存放后端服务的id
        List<String> idList = new ArrayList<>();
        //  循环遍历创建后端服务
        for (ServicePublishBackend servicePublishBackend : list) {
            //  定义创建后端服务请求数据的map
            Map<String, Object> requestServicePublishBackend = new HashMap();
            //  添加请求参数
            requestServicePublishBackend.put("name", servicePublishBackend.getName() + orderNumber);
            if (servicePublishBackend.getServiceAuthType().equals("Basic认证")) {
                requestServicePublishBackend.put("service_auth_type", "BASIC");
            } else if (servicePublishBackend.getServiceAuthType().equals("Public Key认证")) {
                requestServicePublishBackend.put("service_auth_type", "PUBLICKEY");
            } else if (servicePublishBackend.getServiceAuthType().equals("无认证")) {
                requestServicePublishBackend.put("service_auth_type", "NONE");
            }
            if (servicePublishBackend.getServiceAuthType().equals("Basic认证")) {
                Map<String, String> map = new HashMap<>();
                map.put("user_name", servicePublishBackend.getAuthName());
                map.put("user_pwd", servicePublishBackend.getAuthPassword());
                String dataJson = JSON.toJSONString(map);
                requestServicePublishBackend.put("service_auth_data", dataJson);
            }
            requestServicePublishBackend.put("balance_strategy", servicePublishBackend.getBalanceStrategy());
            requestServicePublishBackend.put("backend_port", Integer.valueOf(servicePublishBackend.getBackendPort()));
            requestServicePublishBackend.put("header_host", servicePublishBackend.getHeaderHost());
            if (servicePublishBackend.getCascadeFlag().equals("0")) {
                requestServicePublishBackend.put("cascade_flag", String.valueOf(false));
            } else if (servicePublishBackend.getCascadeFlag().equals("1")) {
                requestServicePublishBackend.put("cascade_flag", String.valueOf(true));
            } else {
                throw new BaseException("cascade_flag参数错误");
            }
            //  添加参数backend_hc_info
            Map<String, Object> backend_hc_info = new HashMap();
            backend_hc_info.put("protocol", servicePublishBackend.getHcProtocol());
            if (!servicePublishBackend.getHcProtocol().equals("NONE")) {
                backend_hc_info.put("port", Integer.valueOf(servicePublishBackend.getHcPort()));
                backend_hc_info.put("threshold_normal", servicePublishBackend.getHcThresholdNormal());
                backend_hc_info.put("threshold_abnormal", servicePublishBackend.getHcThresholdAbnormal());
                backend_hc_info.put("time_out", servicePublishBackend.getHcTimeOut());
                backend_hc_info.put("time_interval", servicePublishBackend.getHcTimeInterval());
                if (servicePublishBackend.getHcProtocol().equals("HTTP")) {
                    backend_hc_info.put("path", servicePublishBackend.getHcAddress());
                    backend_hc_info.put("http_method", servicePublishBackend.getHcAddressType());
                    backend_hc_info.put("http_code", servicePublishBackend.getHcHttpCode());
                }
            }
            requestServicePublishBackend.put("backend_hc_info", backend_hc_info);
            //  定义参数endpoints的集合
            List<Map<String, Object>> endpoints = new ArrayList<>();
            //  定义endpoints中存放参数的map
            Map<String, Object> endpointsMap = new HashMap<>();
            // 根据微网关名获取微网关id
            String microgwId = this.getMicrogwId(userClient, "microgateway_68.26.19.197", serviceReturnBean);
            if (microgwId == null) {
                serviceReturnBean.setError(true);
                serviceReturnBean.setErrorMessage("创建后端服务获取微网关id失败");
                logger.error("创建后端服务获取微网关id: {}", servicePublishBackend.getMicrogw());
                deleteCreated(userClient, okId);
                throw new BaseException("创建后端服务获取微网关id失败");
            }
            endpointsMap.put("microgw_id", microgwId);
            //  定义参数backend_host的集合
            List<Map<String, Object>> backend_host = new ArrayList<>();
            //  获取服务地址集合
            List<BackendHost> hostList = servicePublishBackend.getHostList();
            //  循环添加服务地址的请求参数,并将参数添加到backend_host集合中
            for (BackendHost backendHost : hostList) {
                //  定义backend_host中存放参数的map
                Map<String, Object> backend_hostMap = new HashMap();
                backend_hostMap.put("host_value", backendHost.getHostValue());
                backend_hostMap.put("weight", backendHost.getWeight());
                backend_host.add(backend_hostMap);
            }
            endpointsMap.put("backend_host", backend_host);
            endpoints.add(endpointsMap);
            requestServicePublishBackend.put("endpoints", endpoints);
            requestServicePublishBackend.put("backend_protocol", servicePublishBackend.getBackendProtocol());
            //  请求地址
            String url = apigBaseUrl + "/v1/apigw/extension/back-endpoints";
            //  调用创建后端服务接口
            Response response = OkHttpUtils.postJson(userClient, url, JSON.toJSONString(requestServicePublishBackend));
            String resultStr = response.body().string();
            if (!response.isSuccessful()) {
                serviceReturnBean.setError(true);
                serviceReturnBean.setErrorMessage("创建后端服务失败");
                logger.error("创建后端服务: {}", resultStr);
                response.close();
                deleteCreated(userClient, okId);
                throw new BaseException("创建后端服务失败");
            } else {
                Map<String, Object> returnMap = JSONObject.parseObject(resultStr, new TypeReference<Map<String, Object>>() {
                });
                String id = (String) returnMap.get("id");
                idList.add(id);
                returnList.add(returnMap);
                okId.put("后端服务", idList);
            }
            response.close();
        }
        return returnList;
    }

    /**
     * 根据微网关名获取微网关Id
     *
     * @param userClient
     * @param microgw
     * @return
     */
    private String getMicrogwId(OkHttpClient userClient, String microgw, ServiceReturnBean serviceReturnBean) throws Exception {
        //  请求地址
        String url = apigBaseUrl + "/v1/apigw/extension/microgateways";
        //  调用查询微网关列表接口
        Response response = OkHttpUtils.get(userClient, url, new HashMap<>());
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            serviceReturnBean.setError(true);
            serviceReturnBean.setErrorMessage("获取微服务Id失败");
            logger.error("获取微服务Id: {}", resultStr);
            response.close();
            throw new BaseException("获取微服务Id失败");
        } else {
            Map<String, Object> returnMap = JSONObject.parseObject(resultStr, new TypeReference<Map<String, Object>>() {
            });
            List<Object> returnList = (List<Object>) returnMap.get("microgateways_list");
            for (Object o : returnList) {
                Map<String, Object> map = (Map<String, Object>) o;
                String name = (String) map.get("name");
                if (microgw.equals(name)) {
                    String id = (String) map.get("id");
                    response.close();
                    return id;
                }
            }
        }
        response.close();
        return null;
    }

    /**
     * 保存api列表(user_data,审核之后才能获取到)
     */
    private <I> void saveUserData(OkHttpClient client, FormNum formNum, String instanceGuid, String infoId, IImplHandler<I> implHandler) {
        String url = cscBaseUrlV2 + "/service_instances/" + instanceGuid;
        logger.error("===Into SaveUser Data===");
        try {

            Response response = OkHttpUtils.get(client, url, null);
            String resultStr = response.body().string();
            logger.error("===resultStr===:" + resultStr);
            if (!response.isSuccessful()) {
                logger.error("获取api列表失败: {}", resultStr);
                response.close();
                return;
            }
            response.close();
            InstanceDetail instanceDetail = JSONObject.parseObject(resultStr, InstanceDetail.class);
            String userData = instanceDetail.getUser_data();
            if (implHandler instanceof IPaasFseqsqmImplService) {
                logger.error("===Into General SERVICE===");
                PaasFseqsqm paasFseqsqm = paasFseqsqmService.getOne(new QueryWrapper<PaasFseqsqm>().eq("APP_INFO_ID", infoId));
                paasFseqsqm.setUserData(userData);
                paasFseqsqm.updateById();
            } else if (formNum == FormNum.PAAS_DTHTJY) {
                logger.error("===Into PAAS_DTHTJY SERVICE===");
                PaasDthtjy paasDthtjy = paasDthtjyService.getOne(new QueryWrapper<PaasDthtjy>().eq("APP_INFO_ID", infoId));
                paasDthtjy.setUserData(userData);
                paasDthtjy.updateById();
            } else if (formNum == FormNum.PAAS_DTSJGT) {
                logger.error("===Into PAAS_DTSJGT SERVICE===");
                PaasDtsjgt paasDtsjgt = paasDtsjgtService.getOne(new QueryWrapper<PaasDtsjgt>().eq("APP_INFO_ID", infoId));
                paasDtsjgt.setUserData(userData);
                paasDtsjgt.updateById();
            } else if (formNum == FormNum.PAAS_TYYH) {
                logger.error("===Into PAAS_TYYH SERVICE===");
                paasTyyhService.update(new PaasTyyh(), new UpdateWrapper<PaasTyyh>().lambda().eq(PaasTyyh::getAppInfoId, infoId)
                        .set(PaasTyyh::getUserData, userData));

            }
        } catch (Exception e) {
            logger.error("获取api列表失败", e);
        }
    }

    /**
     * 自动订购错误记录
     *
     * @param formNum   类型
     * @param errorMsg  错误信息
     * @param errorJson 返回错误JSON
     * @param infoId    表单ID
     */
    private <I> void errorLog(FormNum formNum, String errorMsg, String errorJson, String infoId, IImplHandler<I> implHandler) {
        if (implHandler instanceof IPaasFseqsqmImplService) {
            PaasFseqsqm paasFseqsqm = paasFseqsqmService.getOne(new QueryWrapper<PaasFseqsqm>().eq("APP_INFO_ID", infoId));
            paasFseqsqm.setErrorMsg(errorMsg);
            paasFseqsqm.setErrorJson(errorJson);
            paasFseqsqm.updateById();
        } else if (formNum == FormNum.PAAS_DTHTJY) {
            PaasDthtjy paasDthtjy = paasDthtjyService.getOne(new QueryWrapper<PaasDthtjy>().eq("APP_INFO_ID", infoId));
            paasDthtjy.setErrorMsg(errorMsg);
            paasDthtjy.setErrorJson(errorJson);
            paasDthtjy.updateById();
        } else if (formNum == FormNum.PAAS_DTSJGT) {
            PaasDtsjgt paasDtsjgt = paasDtsjgtService.getOne(new QueryWrapper<PaasDtsjgt>().eq("APP_INFO_ID", infoId));
            paasDtsjgt.setErrorMsg(errorMsg);
            paasDtsjgt.setErrorJson(errorJson);
            paasDtsjgt.updateById();
        } else if (formNum == FormNum.PAAS_TYYH) {
            paasTyyhService.update(new PaasTyyh(), new UpdateWrapper<PaasTyyh>().lambda().eq(PaasTyyh::getAppInfoId, infoId)
                    .set(PaasTyyh::getErrorMsg, errorMsg)
                    .set(PaasTyyh::getErrorJson, errorJson));

        } else if (formNum == FormNum.PAAS_TYXX) {
            paasTyxxService.update(new PaasTyxx(), new UpdateWrapper<PaasTyxx>().lambda().eq(PaasTyxx::getAppInfoId, infoId)
                    .set(PaasTyxx::getErrorMsg, errorMsg)
                    .set(PaasTyxx::getErrorJson, errorJson));
        }
    }

    private <I> void saveInstanceGuid(FormNum formNum, String infoId, ServiceInstance instance, IImplHandler<I> implHandler) {
        if (implHandler instanceof IPaasFseqsqmImplService) {
            PaasFseqsqm paasFseqsqm = paasFseqsqmService.getOne(new QueryWrapper<PaasFseqsqm>().eq("APP_INFO_ID", infoId));
            paasFseqsqm.setInstanceGuid(instance.getInstance_guid());
            paasFseqsqm.updateById();
        } else if (formNum == FormNum.PAAS_DTHTJY) {
            PaasDthtjy paasDthtjy = paasDthtjyService.getOne(new QueryWrapper<PaasDthtjy>().eq("APP_INFO_ID", infoId));
            paasDthtjy.setInstanceGuid(instance.getInstance_guid());
            paasDthtjy.updateById();
        } else if (formNum == FormNum.PAAS_DTSJGT) {
            PaasDtsjgt paasDtsjgt = paasDtsjgtService.getOne(new QueryWrapper<PaasDtsjgt>().eq("APP_INFO_ID", infoId));
            paasDtsjgt.setInstanceGuid(instance.getInstance_guid());
            paasDtsjgt.updateById();
        } else if (formNum == FormNum.PAAS_TYYH) {
            paasTyyhService.update(new PaasTyyh(), new UpdateWrapper<PaasTyyh>().lambda().eq(PaasTyyh::getAppInfoId, infoId)
                    .set(PaasTyyh::getInstanceGuid, instance.getInstance_guid()));

        }

    }

    private <I> ProjectList.Project getUserNormalProject(ApplicationInfo info, IImplHandler<I> implHandler) throws Exception {
        FormNum formNum = FormNum.getFormNumByInfo(info);
        // 1.普通用户登录
        String loginUrl = cscBaseUrlV3 + "/auth/tokens";
        LoginRequest userRequest = this.buildUserRequest(info);
        OkHttpClient client = this.initOkHttpClient();
        Response userLoginResponse = OkHttpUtils.postJson(client, loginUrl, JSONObject.toJSONString(userRequest));
        String userJsonStr = userLoginResponse.body().string();
        //todo:普通用户请求token失败处理
        if (!userLoginResponse.isSuccessful()) {
            errorLog(formNum, "用户登录失败", userJsonStr, info.getId(), implHandler);
            userLoginResponse.close();
            throw new BaseException(401, "订购失败,失败原因:用户登录失败");
        }
        userLoginResponse.close();
        LoginResponse user = JSONObject.parseObject(userJsonStr, LoginResponse.class);
        // 2.管理员用户登录
        LoginRequest managerRequest = this.buildManagerRequest();
        Response managerLoginResponse = OkHttpUtils.postJson(client, loginUrl, JSONObject.toJSONString(managerRequest));
        String managerJsonStr = managerLoginResponse.body().string();
        if (!managerLoginResponse.isSuccessful()) {
            errorLog(formNum, "管理员登录失败", managerJsonStr, info.getId(), implHandler);
            managerLoginResponse.close();
            throw new BaseException(401, "订购失败,失败原因:管理员登录失败");
        }
        final String managerToken = managerLoginResponse.header("x-subject-token");
        managerLoginResponse.close();
        // 带有管理员token的OkHttpClient
        OkHttpClient managerClient = initOkHttpClient();
        managerClient = managerClient.newBuilder().addInterceptor(new TokenInterceptor(managerToken)).build();


        ProjectList.Project project = getUserNormalProject(managerClient, user.getToken().getUser().getId(), info.getId(), formNum, implHandler);
        if (project == null) {
            errorLog(formNum, "获取状态正常的项目失败", "", info.getId(), implHandler);
            throw new BaseException(401, "订购失败,失败原因:获取状态正常的项目失败");
        }
        logger.error("用户正常状态的项目: {}", project.toString());
        return project;
    }
    private <I> ProjectList.Project getUserNormalProject(OkHttpClient client, String userId, String infoId, FormNum formNum,IImplHandler<I> implHandler) throws Exception {
        String url = projectBaseUrl + "/rest/vdc/v3.1/users/" + userId + "/projects?start=0&limit=100&rel_projects=true";
        logger.error(url);
        Response response = OkHttpUtils.get(client, url, null);
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            logger.error(resultStr);
            errorLog(formNum, "获取状态正常的项目失败", resultStr, infoId,implHandler);
            response.close();
            throw new BaseException(401, "订购失败,失败原因:获取状态正常的项目失败");
        }
        response.close();
        ProjectList projectList = JSONObject.parseObject(resultStr, ProjectList.class);
        List<ProjectList.Project> projects = projectList.getProjects();
        if (projects == null || projects.isEmpty()) {
            return null;
        }
        for (ProjectList.Project project : projects) {
            if ("normal".equals(project.getRegions().get(0).getRegion_status())) {
                return project;
            }
        }
        return null;
    }
    private <I> List<ProjectList.Project> getUserProject(ApplicationInfo info, IImplHandler<I> implHandler) throws Exception {
        FormNum formNum = FormNum.getFormNumByInfo(info);
        // 1.普通用户登录
        String loginUrl = cscBaseUrlProjectV3 + "/auth/tokens";
//        LoginRequest userRequest = this.buildUserRequest(info);
        OkHttpClient client = this.initOkHttpClient();
//        Response userLoginResponse = OkHttpUtils.postJson(client, loginUrl, JSONObject.toJSONString(userRequest));
//        String userJsonStr = userLoginResponse.body().string();
//        //todo:普通用户请求token失败处理
//        if (!userLoginResponse.isSuccessful()) {
//            errorLog(formNum, "用户登录失败", userJsonStr, info.getId(), implHandler);
//            throw new BaseException(401, "订购失败,失败原因:用户登录失败");
//        }
//        LoginResponse user = JSONObject.parseObject(userJsonStr, LoginResponse.class);
        // 2.管理员用户登录
        LoginRequest managerRequest = this.buildManagerRequest();
        Response managerLoginResponse = OkHttpUtils.postJson(client, loginUrl, JSONObject.toJSONString(managerRequest));
        String managerJsonStr = managerLoginResponse.body().string();
        if (!managerLoginResponse.isSuccessful()) {
            errorLog(formNum, "管理员登录失败", managerJsonStr, info.getId(), implHandler);
            managerLoginResponse.close();
            throw new BaseException(401, "订购失败,失败原因:管理员登录失败");
        }
        final String managerToken = managerLoginResponse.header("x-subject-token");
        managerLoginResponse.close();
        // 带有管理员token的OkHttpClient
        OkHttpClient managerClient = initOkHttpClient();
        managerClient = managerClient.newBuilder().addInterceptor(new TokenInterceptor(managerToken)).build();


        List<ProjectList.Project> project = getUserProject(managerClient, info, formNum, implHandler);
        if (project == null) {
            errorLog(formNum, "获取状态正常的项目失败", "", info.getId(), implHandler);
            throw new BaseException(401, "订购失败,失败原因:获取状态正常的项目失败");
        }
        logger.error("用户正常状态的项目:{}", project.toString());
        return project;
    }

    //获取用户的所有projectid (原因：huawei那边说用户应用过滤不全，导致应用重名）
    private <I> List<ProjectList.Project> getUserProject(OkHttpClient client, ApplicationInfo info, FormNum formNum, IImplHandler<I> implHandler) throws Exception {
        //应用过滤重新更改接口2020.03.24
//        String url = projectBaseUrl + "/rest/vdc/v3.1/users/" + userId + "/projects?start=0&limit=100&rel_projects=true";
        String infoId = info.getId();
        String url = projectsBaseUrl + "/rest/vdc/v3.0/users?query_string=" + info.getCreator();
        logger.error(url);
        Response response = OkHttpUtils.get(client, url, null);
        String resultStr = response.body().string();
        logger.error("获取登录用户的userId======="+resultStr);
        if (!response.isSuccessful()) {
            logger.error(resultStr);
            errorLog(formNum, "获取用户id失败", resultStr, infoId, implHandler);
            response.close();
            throw new BaseException(401, "订购失败,失败原因:获取用户id失败");
        }
        response.close();
        ProjectListNew projectListNew = JSONObject.parseObject(resultStr, ProjectListNew.class);
        if (projectListNew.getTotal() > 1) {
            errorLog(formNum, "total返回数量不正常", resultStr, infoId, implHandler);
            throw new BaseException(401, "订购失败,失败原因:获取用户id数量不正常");
        }
        String projectListUserid = projectListNew.getUsers().get(0).getId();
        //获取用户相关项目projectId接口
        String projectUrl = projectsBaseUrl + "/rest/vdc/v3.1/users/" + projectListUserid + "/projects";
        Response response1 = OkHttpUtils.get(client, projectUrl, null);
        String resultStr1 = response1.body().string();
        logger.error("获取个人所有projectid======"+resultStr1);
        if (!response1.isSuccessful()) {
            logger.error(resultStr1);
            errorLog(formNum, "获取状态正常的项目失败", resultStr1, infoId, implHandler);
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
        //之前操作判断projectid是否正常 才返回 normal代表正常
//        for (ProjectList.Project project : projects) {
//            if ("normal".equals(project.getRegions().get(0).getRegion_status())) {
//                return project;
//            }
//        }
    }

    /**
     * @param userClient
     * @param reviewInstance
     * @param user
     * @param serviceDetail
     * @param info
     * @return
     * @throws Exception
     */
    private <I> ServiceBindingResponse binding(OkHttpClient userClient, ServiceInstance reviewInstance,
                                               LoginResponse user, ServiceDetail serviceDetail,
                                               ApplicationInfo info, IImplHandler<I> implHandler) throws Exception {
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
            FormNum formNum = FormNum.getFormNumByInfo(info);
            errorLog(formNum, "绑定失败", resultStr, info.getId(), implHandler);
            response.close();
            throw new BaseException("订购失败,失败原因:绑定失败");
        }
        ServiceBindingResponse binding = JSONObject.parseObject(resultStr, ServiceBindingResponse.class);
        response.close();
        return binding;
    }

    /**
     * 航天精一绑定结果
     *
     * @param userClient
     * @param reviewInstance
     * @param user
     * @param serviceDetail
     * @return
     * @throws Exception
     */

    private <I> String MapBinding(OkHttpClient userClient, ServiceInstance reviewInstance, LoginResponse user, ServiceDetail serviceDetail, FormNum formNum, String infoId, IImplHandler<I> implHandler) throws Exception {
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
            errorLog(formNum, "绑定失败", resultStr, infoId, implHandler);
            response.close();
            throw new BaseException("订购失败,失败原因:绑定失败");
        }
        response.close();
        JSONObject resultJson = JSONObject.parseObject(resultStr);
        JSONObject credentials = resultJson.getJSONObject("credentials");
        return credentials.toJSONString();


    }

    private <I> ServiceInstance review(OkHttpClient managerClient, OkHttpClient userClient, ServiceInstance orderInstance, String infoId, FormNum formNum, IImplHandler<I> implHandler) throws Exception {
        String url = cscBaseUrlV2 + "/service_instances/" + orderInstance.getInstance_guid() + "/approval";
        Response response = OkHttpUtils.post(managerClient, url, null);
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            errorLog(formNum, "审核失败", resultStr, infoId, implHandler);
            response.close();
            throw new BaseException("订购失败,失败原因:审核失败");
        }
        ServiceInstance instance = JSONObject.parseObject(resultStr, ServiceInstance.class);
        response.close();
        return instance;
    }

    /**
     * @param client
     * @param info
     * @param serviceDetail
     * @param user
     * @param existsApp
     * @param formNum
     * @return
     * @throws Exception
     * @notice; 航天精一填写订购参数，APIG只做了记录操作，没有发现会创建应用等操作。此处目前之间用info里的appName，赋值给parameters里的appName，domain为GDJWY
     * @descriptio： 服务订购
     */
    private <I> ServiceInstance orderService(OkHttpClient client, ApplicationInfo info,
                                             ServiceDetail serviceDetail, LoginResponse user,
                                             AppDetail existsApp, FormNum formNum, IImplHandler<I> implHandler) throws Exception {
        String appName = info.getAppName();
        List<ServiceDetail.Plans> plans = serviceDetail.getPlans();
        if (plans == null || plans.isEmpty()) {
            errorLog(formNum, "服务套餐为空", "", info.getId(), implHandler);
            throw new BaseException("订购失败,失败原因:服务套餐为空");
        }

        Response response = null;
        String resultStr = "";
        //普通订购
        if (implHandler instanceof IPaasFseqsqmImplService || formNum == FormNum.PAAS_TYYH || formNum == FormNum.PAAS_TYXX) {
            String url = cscBaseUrlV2 + "/service_instances?app_id=" + existsApp.getId();
            OrderServiceRequest requestData = new OrderServiceRequest();
            requestData.setName("a" + this.genUuidMd5Bit16());
            requestData.setService_guid(serviceDetail.getGuid());
            requestData.setService_plan_name(plans.get(0).getName());
            LoginResponse.Token.Project project = user.getToken().getProject();
            requestData.setProject_id(project.getId());
            requestData.setDesc("自动订购");
            requestData.setAlias(String.format("%s（%s）", appName, serviceDetail.getAlias()));
            requestData.setCluster_id(serviceDetail.getClusterId());
            requestData.setCluster_namespace(project.getId());
            response = OkHttpUtils.postJson(client, url, JSON.toJSONString(requestData));
            resultStr = response.body().string();
        }
        //航天精一
        if (formNum == FormNum.PAAS_DTHTJY) {
            String url = cscBaseUrlV2 + "/service_instances";
            HtjyMapOrderServiceRequest requestData = new HtjyMapOrderServiceRequest();
            requestData.setName("a" + this.genUuidMd5Bit16());
            requestData.setService_guid(serviceDetail.getGuid());
            requestData.setService_plan_name(plans.get(0).getName());
            LoginResponse.Token.Project project = user.getToken().getProject();
            requestData.setProject_id(project.getId());
            requestData.setDesc("自动订购");
            requestData.setAlias(String.format("%s（%s）", appName, serviceDetail.getAlias()));
            requestData.setCluster_id(serviceDetail.getClusterId());
            requestData.setCluster_namespace(project.getId());
            PaasDthtjy paasDthtjy = paasDthtjyService.getOne(new QueryWrapper<PaasDthtjy>().eq("APP_INFO_ID", info.getId()));
            if (paasDthtjy == null) {
                logger.error("航天精一订购失败");
                throw new BaseException("找不到该服务的申请记录");
            }
            String checkOptions = paasDthtjy.getMapCheck();
            HtjyMapOrderServiceRequest.ParametersBean parameters = new HtjyMapOrderServiceRequest.ParametersBean();
            parameters.setApplyUnit(info.getCjUnit());
            parameters.setAppName(info.getAppName());
            parameters.setTerentName("GDJWY");
            if (StringUtils.isNotEmpty(checkOptions)) {
                for (String option : options) {
                    if (checkOptions.contains(option)) {
                        StringBuffer sb = new StringBuffer(option);
                        //命名开头不能为数字 所以4996-》four996，传入的数字做替换，根据传入的数字set值
                        sb.replace(0, 1, "four");
                        PropertyUtils.setProperty(parameters, sb.toString(), true);
                    }
                }

            }
            requestData.setParameters(parameters);
            response = OkHttpUtils.postJson(client, url, JSON.toJSONString(requestData));
            resultStr = response.body().string();

        }
        //世纪高通地图
        if (formNum == FormNum.PAAS_DTSJGT) {
            String url = cscBaseUrlV2 + "/service_instances";
            SjgtMapOrderServiceRequest requestData = new SjgtMapOrderServiceRequest();
            requestData.setName("a" + this.genUuidMd5Bit16());
            requestData.setService_guid(serviceDetail.getGuid());
            requestData.setService_plan_name(plans.get(0).getName());
            LoginResponse.Token.Project project = user.getToken().getProject();
            requestData.setProject_id(project.getId());
            requestData.setDesc("自动订购");
            requestData.setAlias(String.format("%s（%s）", appName, serviceDetail.getAlias()));
            requestData.setCluster_id(serviceDetail.getClusterId());
            requestData.setCluster_namespace(project.getId());
            PaasDtsjgt paasDtsjgt = paasDtsjgtService.getOne(new QueryWrapper<PaasDtsjgt>().eq("APP_INFO_ID", info.getId()));
            if (paasDtsjgt == null) {
                logger.error("世纪高通订购失败");
                throw new BaseException("找不到该服务的申请记录");
            }
            SjgtMapOrderServiceRequest.ParametersBean parameters = new SjgtMapOrderServiceRequest.ParametersBean();
            parameters.setUsername(paasDtsjgt.getUserName());
            parameters.setPassword(paasDtsjgt.getPassword());
            requestData.setParameters(parameters);
            response = OkHttpUtils.postJson(client, url, JSON.toJSONString(requestData));
            resultStr = response.body().string();
        }
        if (response == null || !response.isSuccessful()) {
            logger.error("response:" + response.toString() + ";response body:" + response.body().toString());
            logger.error("responseCode:" + response.code());
            errorLog(formNum, "订购失败", resultStr, info.getId(), implHandler);
            response.close();
            throw new BaseException("订购失败");
        }
        response.close();
        ServiceInstance instance = JSONObject.parseObject(resultStr, ServiceInstance.class);
        this.saveInstanceGuid(formNum, info.getId(), instance, implHandler);
        return instance;
    }

    private String genUuidMd5Bit16() {
        String uuid = UUIDUtil.getUUID();
        return MD5Util.md5bit16(uuid);
    }

    private <I> AppDetail createApp(OkHttpClient client, ApplicationInfo info, FormNum formNum, IImplHandler<I> implHandler) throws Exception {
        Map<String, String> createInfo = new HashMap<>(2);
        createInfo.put("name", info.getAppName());
        createInfo.put("remark", new StringBuffer(info.getPoliceCategory()).append("|").append(info.getCjUnit()).toString());

        String url = apigBaseUrl + "/v1/apigw/extension/apps";
        Response response = OkHttpUtils.postJson(client, url, JSON.toJSONString(createInfo));
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            errorLog(formNum, "创建应用失败", resultStr, info.getId(), implHandler);
            response.close();
            throw new BaseException("订购失败,失败原因:创建应用失败");
        }
        response.close();
        AppDetail appDetail = JSONObject.parseObject(resultStr, AppDetail.class);
        return appDetail;
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

    private <I> AppList getUserAppList(OkHttpClient client, String infoId, FormNum formNum,IImplHandler<I> implHandler) throws Exception {
        String url = apigBaseUrl + "/v1/apigw/extension/apps";
        Map<String, String> param = new HashMap<>();
        param.put("page_size", "500");
        Response response = OkHttpUtils.get(client, url, param);
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            errorLog(formNum, "获取应用列表失败", resultStr, infoId,implHandler);
            response.close();
            throw new BaseException("订购失败,失败原因:获取应用列表失败");
        }
        AppList appList = JSONObject.parseObject(resultStr, AppList.class);
        response.close();
        return appList;
    }

    private <I> ServiceDetail getServiceDetail(OkHttpClient client, String serviceId, String infoId, FormNum formNum,IImplHandler<I> implHandler) throws Exception {
        String url = cscBaseUrlV2 + "/services/" + serviceId;
        Response response = OkHttpUtils.get(client, url, null);
        String resultStr = response.body().string();
        if (!response.isSuccessful()) {
            errorLog(formNum, "获取服务详情失败", resultStr, infoId,implHandler);
            response.close();
            throw new BaseException("订购失败,失败原因:获取服务详情失败");
        }
        ServiceDetail detail = JSONObject.parseObject(resultStr, ServiceDetail.class);
        response.close();
        return detail;
    }

    private LoginRequest buildUserRequest(ApplicationInfo info) {
        LoginRequest userRequest = JSONObject.parseObject(USER_LOGIN_JSON, LoginRequest.class);
        LoginRequest.Auth.Identity.Password.User user = userRequest.getAuth().getIdentity().getPassword().getUser();
        user.setName(info.getCreator());
        userRequest.getAuth().getScope().setProject(null);
        user.setPassword("Gdga12#$");
        return userRequest;
    }

    private LoginRequest buildUserPjctRequest(ApplicationInfo info) {
        LoginRequest userPjctRequest = JSONObject.parseObject(USER_LOGIN_PJCTID_JSON, LoginRequest.class);
        LoginRequest.Auth.Identity.Password.User user = userPjctRequest.getAuth().getIdentity().getPassword().getUser();
        user.setName(info.getCreator());
        userPjctRequest.getAuth().getScope().setProject(null);
        user.setPassword("Gdga12#$");
        return userPjctRequest;
    }

    //  获取项目的管理员账号
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


    public static void main(String[] args) {
        System.out.println(Const.PaasResourceEnum.valueOf("广东科信飞识人像识别服务"));
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

    private static final String USER_LOGIN_PJCTID_JSON = "{\n" +
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
            "\t\t\t\t\"id\": \"9c92707606b344f98b6d58a4f376bdcd\"\n" +
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
