package com.upd.hwcloud.controller.backend.open;

import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.service.IServicePublishService;
import com.upd.hwcloud.service.RepeatedJudgementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 *
 * 此类用于开放给美亚作服务数据传输
 *
 * @author junglefisher
 * @date 2019/11/9 18:14
 */
@Controller
@RequestMapping("/api/getService")
public class GetServiceController {
    @Autowired
    private IServicePublishService iServicePublishService;
    @Autowired
    private RepeatedJudgementService repeatedJudgementService;

    /**
     * 美亚接口
     * @param servicePublish
     * @return
     */
    @RequestMapping(value = "/getService",method = RequestMethod.POST)
    @ResponseBody
    public R getService(@RequestBody ServicePublish servicePublish){
        String serviceName = servicePublish.getServiceName();
        if (repeatedJudgementService.judgeServiceName(serviceName)){
            throw  new BaseException(400,"服务名称重复");
        }
        List<ServicePublishBackend> backendList=servicePublish.getBackendList();
        List<ServicePublishApi> apiList=servicePublish.getApiList();
        for (ServicePublishApi servicePublishApi : apiList) {
            if (repeatedJudgementService.judgeApiName(servicePublishApi.getName())){
                throw  new BaseException(400,"api名称重复");
            }
            List<ApiOperation> apiOperationList=servicePublishApi.getApiOperationList();
            for (ApiOperation apiOperation : apiOperationList) {
                if (repeatedJudgementService.judgeApiOperationName(apiOperation.getName())){
                    throw  new BaseException(400,"api操作名称重复");
                }
                if (repeatedJudgementService.judgeApiOperationPath(apiOperation.getPath())){
                    throw  new BaseException(400,"api操作路径重复");
                }
            }
        }
        ServicePublishApiProduct servicePublishApiProduct=servicePublish.getApiProduct();
        if (repeatedJudgementService.judgeApiProductName(servicePublishApiProduct.getName())){
            throw  new BaseException(400,"api产品名称重复");
        }
        if (StringUtils.isEmpty(servicePublish.getCreator())){
            throw new BaseException(400,"缺少creator参数");
        }
        if (StringUtils.isEmpty(servicePublish.getCreatorName())){
            throw new BaseException(400,"缺少creatorName参数");
        }
        if (backendList==null||backendList==null||servicePublishApiProduct==null){
            throw new BaseException(400,"缺少Object参数");
        }
        for (ServicePublishBackend servicePublishBackend : backendList) {
            if (!servicePublishBackend.getServiceAuthType().equals("Basic认证")&&!servicePublishBackend.getServiceAuthType().equals("无认证")&&!servicePublishBackend.getServiceAuthType().equals("Public Key认证")){
                throw new BaseException(400,"serviceAuthType参数错误");
            }
            if (!servicePublishBackend.getBackendProtocol().equals("HTTP")&&!servicePublishBackend.getBackendProtocol().equals("HTTPS")){
                throw new BaseException(400,"backendProtocol参数错误");
            }
            if (!servicePublishBackend.getCascadeFlag().equals("1")&&!servicePublishBackend.getCascadeFlag().equals("0")){
                throw new BaseException(400,"cascadeFlag参数错误");
            }
            if (!servicePublishBackend.getHcProtocol().equals("NONE")&&!servicePublishBackend.getHcProtocol().equals("TCP")&&!servicePublishBackend.getHcProtocol().equals("HTTP")){
                throw new BaseException(400,"hcProtocol参数错误");
            }
            if (servicePublishBackend.getHcProtocol().equals("TCP")||servicePublishBackend.getHcProtocol().equals("HTTP")){
                if (servicePublishBackend.getHcPort()==null||servicePublishBackend.getHcThresholdNormal()==null||servicePublishBackend.getHcThresholdAbnormal()==null||servicePublishBackend.getHcTimeOut()==null||servicePublishBackend.getHcTimeInterval()==null){
                    throw new BaseException(400,"hcProtocol相关参数错误");
                }else {
                    if (servicePublishBackend.getHcProtocol().equals("HTTP")){
                        if (servicePublishBackend.getHcAddress()==null||servicePublishBackend.getHcAddressType()==null||servicePublishBackend.getHcHttpCode()==null){
                            throw new BaseException(400,"hcProtocol相关参数错误");
                        }
                    }
                }
            }
        }
        for (ServicePublishApi servicePublishApi : apiList) {
            if (!servicePublishApi.getProtocol().equals("HTTP")&&!servicePublishApi.getProtocol().equals("HTTPS")&&!servicePublishApi.getProtocol().equals("HTTP&HTTPS")&&!servicePublishApi.getProtocol().equals("WS")&&!servicePublishApi.getProtocol().equals("WSS")){
                throw new BaseException(400,"protocol参数错误");
            }
            if (!servicePublishApi.getAuthType().equals("无认证")&&!servicePublishApi.getAuthType().equals("APP Key认证")&&!servicePublishApi.getAuthType().equals("APP Token认证")){
                throw new BaseException(400,"authType参数错误");
            }
            List<ApiOperation> apiOperationList=servicePublishApi.getApiOperationList();
            if (apiOperationList==null){
                break;
            }
            for (ApiOperation apiOperation : apiOperationList) {
                if (!apiOperation.getMatchMode().equals("NORMAL")&&!apiOperation.getMatchMode().equals("PREFIX")){
                    throw new BaseException(400,"matchMode参数错误");
                }
                if (!apiOperation.getWithOrchestration().equals("0")&&!apiOperation.getWithOrchestration().equals("1")){
                    throw new BaseException(400,"withOrchestration参数错误");
                }
                if (!apiOperation.getBackendType().equals("HTTP")){
                    throw new BaseException(400,"backendType参数错误");
                }
            }
        }
        servicePublish.setStatus("8");
        servicePublish.setIsPublishApig("1");
        servicePublish.setIsFromApp("0");
        servicePublish.setServiceType("DAAS");
        servicePublish.setWhereFrom("2");
        try {
            //  保存数据到数据库
            iServicePublishService.saveMY(servicePublish);
        }catch (Exception e){
            throw new BaseException("数据库错误");
        }
        return R.ok("传输成功!");
    }
}
