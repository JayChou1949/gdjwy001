package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.ModelName;
import com.upd.hwcloud.bean.contains.RedisKey;
import com.upd.hwcloud.bean.dto.ImplRequest;
import com.upd.hwcloud.bean.dto.apig.ServiceReturnBean;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;
import com.upd.hwcloud.bean.vo.workbench.ResourceOverviewVO;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.OrderNum;
import com.upd.hwcloud.dao.ServicePublishMapper;
import com.upd.hwcloud.service.*;
import com.upd.hwcloud.service.application.IAppReviewInfoService;
import com.upd.hwcloud.service.application.IPaasApigService;
import com.upd.hwcloud.service.application.ISpeedUpService;
import com.upd.hwcloud.service.workbench.impl.CommonHandler;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 服务发布 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2019-10-17
 */
@Service
public class ServicePublishServiceImpl extends ServiceImpl<ServicePublishMapper, ServicePublish> implements IServicePublishService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IFilesService filesService;
    @Autowired
    private IServicePublishApiProductService apiProductService;
    @Autowired
    private IServicePublishBackendService backendService;
    @Autowired
    private IBackendHostService backendHostService;
    @Autowired
    private IServicePublishApiService apiService;
    @Autowired
    private IApiAcIpService apiAcIpService;
    @Autowired
    private IApiOperationService apiOperationService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAppReviewInfoService appReviewInfoService;
    @Autowired
    private ServicePublishMapper servicePublishMapper;
    @Autowired
    private ISaasService saasService;
    @Autowired
    private IPaasService paasService;
    @Autowired
    private IBigdataService daasService;
    @Autowired
    private IPaasApigService paasApigService;

    @Autowired
    private ISpeedUpService speedUpService;

    private static final Logger logger = LoggerFactory.getLogger(ServicePublishServiceImpl.class);


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(User user, ServicePublish info) {
        info.setId(null);
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
        String orderNum = OrderNum.gen(stringRedisTemplate, RedisKey.KEY_PUBLISH_PREFIX);
        info.setOrderNumber(orderNum);
        this.save(info);
        // 关联文件信息
        filesService.refFiles(info.getFileList(), info.getId());
        // 保存 api 产品
        saveApiProduct(info);
        // 保存后端服务
        saveBackend(info);
        // 保存 api
        saveApi(info);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMY(ServicePublish info) {
        info.setId(null);
        this.save(info);
        // 关联文件信息
        filesService.refFiles(info.getFileList(), info.getId());
        // 保存 api 产品
        saveApiProduct(info);
        // 保存后端服务
        saveBackend(info);
        // 保存 api
        saveApi(info);
    }

    @Override
    public ServicePublish getDetails(String id) {
        ServicePublish servicePublish = this.getById(id);
        if (servicePublish == null) {
            throw new BaseException("该申请不存在");
        }
        // 获取文件
        List<Files> files = filesService.getFilesByRefId(id);
        List<Files> fileList= Lists.newArrayList();
        List<Files> interfaceFileList= Lists.newArrayList();
        List<Files> developmentFileList= Lists.newArrayList();
        if (files!=null&&files.size()>0){
            for (Files file : files) {
                String path=file.getPath();
                if (path!=null){
                    if (path.contains("interface")){
                        interfaceFileList.add(file);
                    }else if (path.contains("development")){
                        developmentFileList.add(file);
                    }else {
                        fileList.add(file);
                    }
                }
            }
        }
        servicePublish.setFileList(fileList);
        servicePublish.setInterfaceFileList(interfaceFileList);
        servicePublish.setDevelopmentFileList(developmentFileList);
        // api产品
        ServicePublishApiProduct apiProduct = apiProductService.getOne(new QueryWrapper<ServicePublishApiProduct>().lambda()
                .eq(ServicePublishApiProduct::getPublishId, id));
        servicePublish.setApiProduct(apiProduct);
        // 后端服务
        List<ServicePublishBackend> backendList = getBackendList(id);
        servicePublish.setBackendList(backendList);
        // api
        List<ServicePublishApi> apiList = getApiList(id);
        servicePublish.setApiList(apiList);
        // 申请人
        User creator = userService.findUserByIdcard(servicePublish.getCreator());
        servicePublish.setUser(creator);
        // 审核信息
        List<AppReviewInfo> allReviewInfo = appReviewInfoService.getAllReviewInfoByAppInfoId(id);
        servicePublish.setReviewList(allReviewInfo);
        // 实施审批信息
        AppReviewInfo implInfo = null;
        AppReviewInfo lastReviewInfo = appReviewInfoService.getLastPassReviewInfoByAppInfoId(id);
        if (lastReviewInfo != null && "2".equals(lastReviewInfo.getrType())) {
            // 最近一条审核记录为实施记录
            implInfo = lastReviewInfo;
            List<Files> implFileList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, implInfo.getId()));
            implInfo.setFileList(implFileList);
            servicePublish.setImpl(implInfo);
        }
        return servicePublish;
    }

    @Override
    public ServicePublish getDetailsMY(String id) {
        ServicePublish servicePublish = this.getById(id);
        if (servicePublish == null) {
            throw new BaseException("该申请不存在");
        }
        // 获取文件
        List<Files> files = filesService.getFilesByRefId(id);
        List<Files> fileList= Lists.newArrayList();
        List<Files> interfaceFileList= Lists.newArrayList();
        List<Files> developmentFileList= Lists.newArrayList();
        if (files!=null&&files.size()>0){
            for (Files file : files) {
                String path=file.getPath();
                if (path!=null){
                    if (path.contains("interface")){
                        interfaceFileList.add(file);
                    }else if (path.contains("development")){
                        developmentFileList.add(file);
                    }else {
                        fileList.add(file);
                    }
                }
            }
        }
        servicePublish.setFileList(fileList);
        servicePublish.setInterfaceFileList(interfaceFileList);
        servicePublish.setDevelopmentFileList(developmentFileList);
        // api产品
        ServicePublishApiProduct apiProduct = apiProductService.getOne(new QueryWrapper<ServicePublishApiProduct>().lambda()
                .eq(ServicePublishApiProduct::getPublishId, id));
        servicePublish.setApiProduct(apiProduct);
        // 后端服务
        List<ServicePublishBackend> backendList = getBackendList(id);
        servicePublish.setBackendList(backendList);
        // api
        List<ServicePublishApi> apiList = getApiList(id);
        servicePublish.setApiList(apiList);
        return servicePublish;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(User user, ServicePublish info) {
        info.setStatus(null);
        info.setOrderNumber(null);
        this.updateById(info);

        backendService.remove(new QueryWrapper<ServicePublishBackend>().lambda()
                .eq(ServicePublishBackend::getPublishId, info.getId()));
        apiService.remove(new QueryWrapper<ServicePublishApi>().lambda()
                .eq(ServicePublishApi::getPublishId, info.getId()));
        apiProductService.remove(new QueryWrapper<ServicePublishApiProduct>().lambda()
                .eq(ServicePublishApiProduct::getPublishId, info.getId()));
        List<ServicePublishApi> servicePublishApis=info.getApiList();
        for (ServicePublishApi servicePublishApi : servicePublishApis) {
            apiOperationService.remove(new QueryWrapper<ApiOperation>().lambda()
                    .eq(ApiOperation::getMasterId, servicePublishApi.getId()));
        }
        List<Files> fileList = info.getFileList();
        List<Files> interfaceFileList = info.getInterfaceFileList();
        List<Files> developmentFileList = info.getDevelopmentFileList();
        if (interfaceFileList!=null&&interfaceFileList.size()>0){
            fileList.addAll(interfaceFileList);
        }
        if (developmentFileList!=null&&developmentFileList.size()>0){
            fileList.addAll(developmentFileList);
        }
        // 关联文件信息
        filesService.refFiles(fileList, info.getId());
        // 保存 api 产品
        saveApiProduct(info);
        // 保存后端服务
        saveBackend(info);
        // 保存 api
        saveApi(info);
    }

    /**
     * 后台逻辑删除
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(User user, String id) {
        ServicePublish info = this.getById(id);
        if (info == null) {
            throw new BaseException("该记录不存在!");
        }
        if (!Objects.equals(user.getIdcard(), info.getCreator())) {
            throw new BaseException("只能删除自己的申请!");
        }
        // 逻辑删除,并设置相应的状态
        this.update(new ServicePublish(), new UpdateWrapper<ServicePublish>().lambda()
                .eq(ServicePublish::getId, id)
                .set(ServicePublish::getStatus, ApplicationInfoStatus.DELETE.getCode()));
    }

    @Override
    public ServicePublish getByActId(String activityId) {
        return baseMapper.getByActId(activityId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveImpl(User user, Map<String, Object> param, String modelId) throws Exception{
        ServicePublish info = (ServicePublish) param.get("info");
        // 添加实施信息
        ImplRequest implRequest = (ImplRequest) param.get("implRequest");
        String result = implRequest.getResult();
        String remark = implRequest.getRemark();

        AppReviewInfo reviewInfo = new AppReviewInfo();
        reviewInfo.setCreator(user.getIdcard());
        reviewInfo.setResult(result);
        reviewInfo.setRemark(remark);
        reviewInfo.setrType("2");
        reviewInfo.setStepName(ModelName.CARRY.getName());
        reviewInfo.setFlowStepId(modelId);
        reviewInfo.setAppInfoId(info.getId());
        reviewInfo.insert();
        // 实施附件
        filesService.refFiles(implRequest.getFileList(), reviewInfo.getId());
        ApplicationInfoStatus status;
        if ("0".equals(result)) {
            // 驳回申请
            status = ApplicationInfoStatus.REVIEW;
        } else {
          // ServiceReturnBean returnBean = getTestReturnBean(false);
            ServiceReturnBean returnBean = paasApigService.apigOrderService(info);
            //LoggerFactory.getLogger(ServiceReturnBean.class);
            System.out.println(returnBean.toString());
            if(returnBean.isError()){
               throw  new BaseException("服务创建失败");
            }
            System.out.println( "创建服务类型——————"+info.getServiceType());
            boolean isSuccess = info.getServiceType().equals("SAAS")?convertIntoSaas(info,returnBean.getServiceId()):(info.getServiceType().equals("PAAS")?convertIntoPaas(info,returnBean):convertIntoDaas(info,returnBean));
            // 实施步骤已完成,修改申请为使用状态
            if(!isSuccess){
                logger.error("apig_guid:"+returnBean.getServiceId());
                throw new BaseException("同步到服务目录失败，请联系管理员");
            }
            status = ApplicationInfoStatus.USE;
        }
        this.update(new ServicePublish(), new UpdateWrapper<ServicePublish>().lambda()
                .eq(ServicePublish::getId, info.getId())
                .set(ServicePublish::getStatus, status.getCode()));
    }


    @Override
    public int getReviewCount(User user, QueryVO queryVO) {
        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO",queryVO);

        return baseMapper.getReviewCount(user,param);
    }


    @Override
    public int getImplCount(User user, QueryVO queryVO) {

        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO",queryVO);

        return baseMapper.getImplCount(user,param);
    }

    @Override
    public int getRejectCount(User user, QueryVO queryVO) {

        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO",queryVO);

        return baseMapper.getRejectCount(user,param);
    }

    @Override
    public int getUseCount(User user, QueryVO queryVO) {
        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO",queryVO);

        return baseMapper.getUseCount(user,param);
    }


    @Override
    public R servicePublishApply(User user,QueryVO queryVO){
        int reviewCount = this.getReviewCount(user,queryVO);
        int implCount = this.getImplCount(user,queryVO);
        int rejectCount = this.getRejectCount(user,queryVO);
        int useCount = this.getUseCount(user,queryVO);
        Map<String, Integer> data = Maps.newHashMap();
        data.put("reviewCount", reviewCount);
        data.put("implCount", implCount);
        data.put("rejectCount", rejectCount);
        data.put("useCount", useCount);
        return R.ok(data);
    }

    private ServiceReturnBean getTestReturnBean(boolean error){
        ServiceReturnBean serviceReturnBean = new ServiceReturnBean();
        serviceReturnBean.setServiceId("123456789");
        serviceReturnBean.setError(error);
        return serviceReturnBean;
    }



    public ServiceReturnBean apigOrderService(ServicePublish info) throws Exception{
        return  paasApigService.apigOrderService(info);
    }




    /**
     * 将服务发布申请转换为saas服务
     * @param servicePublish 申请信息
     * @return
     */
    public boolean convertIntoSaas(ServicePublish servicePublish,String serviceGuid){
        return saasService.servicePublish2SaaS(servicePublish,serviceGuid);
    }
    /**
     * 将服务发布申请转换为Paas服务
     * @param servicePublish 申请信息
     * @return
     */
    public boolean convertIntoPaas(ServicePublish servicePublish,ServiceReturnBean returnBean){
        return paasService.servicePublish2PaaS(servicePublish,returnBean);
    }
    /**
     * 将服务发布申请转换为Daas服务
     * @param servicePublish 申请信息
     * @return
     */
    public boolean convertIntoDaas(ServicePublish servicePublish,ServiceReturnBean returnBean){
        return daasService.servicePublish2DaaS(servicePublish,returnBean);
    }


    @Override
    public IPage<ServicePublish> getPage(User user, IPage<ServicePublish> page, Map<String, Object> param) {
        page = baseMapper.getPage(page, param);
        List<ServicePublish> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
           speedUpService.dealProcessingPersonPublish(records,user);
        }
        return page;
    }


    @Override
    public IPage<ResourceOverviewVO> getResourcePage(Long pageNum, Long pageSize,String type, QueryVO queryVO, User user) {
        IPage<ResourceOverviewVO> page = new Page<>(pageNum,pageSize);
        Map<String,Object> param = CommonHandler.handlerOfKeyWord(queryVO,user);
        page = servicePublishMapper.getResourcePage(page,type,param);

        return page;
    }

    @Override
    public IPage<ServicePublish> getWorkbenchApplyPage(User user, IPage<ServicePublish> page,QueryVO vo) {

        Map<String,Object> param = CommonHandler.handlerOfQueryVO(vo,user);
        page = servicePublishMapper.getWorkbenchApplyPage(page,param);
        List<ServicePublish> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            for (ServicePublish record : records) {
                ApplicationInfoStatus ais = ApplicationInfoStatus.codeOf(record.getStatus());
                // 判断是否能删除
                if (ais != ApplicationInfoStatus.DELETE
                        && Objects.equals(user.getIdcard(), record.getCreator())) {
                    record.setCanDelete(true);
                }
            }
        }

        return page;
    }

    @Override
    public IPage<ServicePublish> getPageFromMY(User user, IPage<ServicePublish> page, Map<String, Object> param,String processType) {
        if ( StringUtils.isNotEmpty(processType)){
            String type=servicePublishMapper.getType(processType);
            param.put("type",type);
        }
        page = baseMapper.getPageFromMY(page, param);
        List<ServicePublish> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            for (ServicePublish record : records) {
                ApplicationInfoStatus ais = ApplicationInfoStatus.codeOf(record.getStatus());
                // 判断是否能删除
                if (ais != ApplicationInfoStatus.DELETE
                        && Objects.equals(user.getIdcard(), record.getCreator())) {
                    record.setCanDelete(true);
                }
            }
        }
        return page;
    }

    @Override
    public int getPublishTodoCount(User user) {
        return servicePublishMapper.getPublishTodoCount(user);
    }

    @Override
    public int getPublishTodo(String idCard) {
        return servicePublishMapper.getPublishTodo(idCard);
    }

    private List<ServicePublishApi> getApiList(String id) {
        List<ServicePublishApi> apiList = apiService.list(new QueryWrapper<ServicePublishApi>().lambda()
                .eq(ServicePublishApi::getPublishId, id));
        if (apiList != null) {
            for (ServicePublishApi api : apiList) {
                // IP信息
                List<ApiAcIp> ipList = apiAcIpService.list(new QueryWrapper<ApiAcIp>().lambda()
                        .eq(ApiAcIp::getMasterId, api.getId()));
                api.setIpList(ipList);
                // API操作
                List<ApiOperation> apiOperationList = apiOperationService.list(new QueryWrapper<ApiOperation>().lambda()
                        .eq(ApiOperation::getMasterId, api.getId()));
                api.setApiOperationList(apiOperationList);
            }
        }
        return apiList;
    }

    private List<ServicePublishBackend> getBackendList(String id) {
        List<ServicePublishBackend> backendList = backendService.list(new QueryWrapper<ServicePublishBackend>().lambda()
                .eq(ServicePublishBackend::getPublishId, id));
        if (backendList != null) {
            for (ServicePublishBackend backend : backendList) {
                // 服务地址
                List<BackendHost> hostList = backendHostService.list(new QueryWrapper<BackendHost>().lambda()
                        .eq(BackendHost::getMasterId, backend.getId()));
                backend.setHostList(hostList);
            }
        }
        return backendList;
    }

    private void saveApi(ServicePublish info) {
        List<ServicePublishApi> apiList = info.getApiList();
        if (apiList != null) {
            for (ServicePublishApi api : apiList) {
                api.setId(null);
                api.setPublishId(info.getId());
                api.insert();
                // 保存IP信息
                saveAcIp(api);
                // 保存 API 操作
                saveApiOperation(api);
            }
        }
    }

    private void saveApiOperation(ServicePublishApi api) {
        List<ApiOperation> apiOperationList = api.getApiOperationList();
        if (apiOperationList != null) {
            for (ApiOperation apiOperation : apiOperationList) {
                apiOperation.setId(null);
                apiOperation.setMasterId(api.getId());
                apiOperation.insert();
            }
        }
    }

    private void saveAcIp(ServicePublishApi api) {
        List<ApiAcIp> ipList = api.getIpList();
        if (ipList != null) {
            for (ApiAcIp ip : ipList) {
                ip.setId(null);
                ip.setMasterId(api.getId());
                ip.insert();
            }
        }
    }

    private void saveBackend(ServicePublish info) {
        List<ServicePublishBackend> backendList = info.getBackendList();
        if (backendList != null) {
            for (ServicePublishBackend backend : backendList) {
                backend.setId(null);
                backend.setPublishId(info.getId());
                backend.insert();
                // 保存服务地址
                saveBackendHost(backend);
            }
        }
    }

    private void saveBackendHost(ServicePublishBackend backend) {
        List<BackendHost> hostList = backend.getHostList();
        if (hostList != null) {
            for (BackendHost host : hostList) {
                host.setId(null);
                host.setMasterId(backend.getId());
                host.insert();
            }
        }
    }

    private void saveApiProduct(ServicePublish info) {
        ServicePublishApiProduct apiProduct = info.getApiProduct();
        if (apiProduct != null) {
            apiProduct.setId(null);
            apiProduct.setPublishId(info.getId());
            apiProduct.insert();
        }
    }


}
