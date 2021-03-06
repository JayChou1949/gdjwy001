package com.hirisun.cloud.iaas.enumeration;


import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;

import com.hirisun.cloud.common.contains.ResourceType;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.iaas.bean.IaasDxccExt;
import com.hirisun.cloud.iaas.bean.IaasDxccImpl;
import com.hirisun.cloud.iaas.bean.IaasLjsfw;
import com.hirisun.cloud.iaas.bean.IaasLjsfwImpl;
import com.hirisun.cloud.iaas.bean.IaasSfswjfw;
import com.hirisun.cloud.iaas.bean.IaasSfswjfwImpl;
import com.hirisun.cloud.iaas.bean.IaasTxyfw;
import com.hirisun.cloud.iaas.bean.IaasTxyfwImpl;
import com.hirisun.cloud.iaas.bean.IaasTxyfwbg;
import com.hirisun.cloud.iaas.bean.IaasTxyfwbgImpl;
import com.hirisun.cloud.iaas.bean.IaasTxyfwxg;
import com.hirisun.cloud.iaas.bean.IaasTxyfwxgImpl;
import com.hirisun.cloud.iaas.bean.IaasTxyfzjh;
import com.hirisun.cloud.iaas.bean.IaasTxyfzjhImpl;
import com.hirisun.cloud.iaas.bean.IaasYzm;
import com.hirisun.cloud.iaas.bean.IaasYzmImpl;
import com.hirisun.cloud.iaas.bean.IaasYzmyzy;
import com.hirisun.cloud.iaas.bean.IaasYzmyzyUser;
import com.hirisun.cloud.iaas.bean.PaasRqzy;
import com.hirisun.cloud.iaas.bean.PaasRqzyImpl;
import com.hirisun.cloud.iaas.service.IApplicationHandler;
import com.hirisun.cloud.iaas.service.IIaasDxccExtService;
import com.hirisun.cloud.iaas.service.IIaasDxccImplService;
import com.hirisun.cloud.iaas.service.IIaasLjsfwImplService;
import com.hirisun.cloud.iaas.service.IIaasLjsfwService;
import com.hirisun.cloud.iaas.service.IIaasSfswjfwImplService;
import com.hirisun.cloud.iaas.service.IIaasSfswjfwService;
import com.hirisun.cloud.iaas.service.IIaasTxyfwImplService;
import com.hirisun.cloud.iaas.service.IIaasTxyfwService;
import com.hirisun.cloud.iaas.service.IIaasTxyfwbgImplService;
import com.hirisun.cloud.iaas.service.IIaasTxyfwbgService;
import com.hirisun.cloud.iaas.service.IIaasTxyfwxgImplService;
import com.hirisun.cloud.iaas.service.IIaasTxyfwxgService;
import com.hirisun.cloud.iaas.service.IIaasTxyfzjhImplService;
import com.hirisun.cloud.iaas.service.IIaasTxyfzjhService;
import com.hirisun.cloud.iaas.service.IIaasYzmImplService;
import com.hirisun.cloud.iaas.service.IIaasYzmService;
import com.hirisun.cloud.iaas.service.IIaasYzmyzyService;
import com.hirisun.cloud.iaas.service.IIaasYzmyzyUserService;
import com.hirisun.cloud.iaas.service.IImplHandler;
import com.hirisun.cloud.iaas.service.IPaasRqzyImplService;
import com.hirisun.cloud.iaas.service.IPaasRqzyService;

public enum FormNum {

    //???????????????
    IAAS_DEFAULT(ResourceType.IAAS, "IAAS????????????", null, null, null, null),
    IAAS_TXYFW(ResourceType.IAAS, "???????????????", IaasTxyfw.class, IIaasTxyfwService.class, IaasTxyfwImpl.class, IIaasTxyfwImplService.class),
    IAAS_BAREMETAL(ResourceType.IAAS, "???????????????", IaasLjsfw.class, IIaasLjsfwService.class, IaasLjsfwImpl.class, IIaasLjsfwImplService.class),
    IAAS_TXYFWXG(ResourceType.IAAS, "?????????????????????", IaasTxyfwxg.class, IIaasTxyfwxgService.class, IaasTxyfwxgImpl.class, IIaasTxyfwxgImplService.class),
    IAAS_TXYFWBG(ResourceType.IAAS, "?????????????????????", IaasTxyfwbg.class, IIaasTxyfwbgService.class, IaasTxyfwbgImpl.class, IIaasTxyfwbgImplService.class),
    IAAS_RQZY(ResourceType.IAAS, "????????????", PaasRqzy.class, IPaasRqzyService.class, PaasRqzyImpl.class, IPaasRqzyImplService.class),
    IAAS_YZM(ResourceType.IAAS, "?????????", IaasYzm.class, IIaasYzmService.class, IaasYzmImpl.class, IIaasYzmImplService.class),
    IAAS_SFSWJFW(ResourceType.IAAS, "SFS????????????", IaasSfswjfw.class, IIaasSfswjfwService.class, IaasSfswjfwImpl.class, IIaasSfswjfwImplService.class),
    IAAS_DXCC(ResourceType.IAAS, "????????????", IaasDxccExt.class, IIaasDxccExtService.class, IaasDxccImpl.class, IIaasDxccImplService.class),
    IAAS_TXYFZJH(ResourceType.IAAS, "?????????????????????", IaasTxyfzjh.class, IIaasTxyfzjhService.class, IaasTxyfzjhImpl.class, IIaasTxyfzjhImplService.class),
    IAAS_YZMYZY(ResourceType.IAAS, "??????????????????", IaasYzmyzy.class, IIaasYzmyzyService.class, IaasYzmyzyUser.class, IIaasYzmyzyUserService.class);

    private final String desc;
    private final ResourceType resourceType;
    private final Class applicationType;
    private final Class<? extends IApplicationHandler> handler;
    private final Class implType;
    private final Class<? extends IImplHandler> implHandler;

    /**
     * ?????????
     *
     * @param resourceType    ????????????
     * @param desc            ??????
     * @param applicationType ??????????????????
     * @param handler         ????????????service
     * @param implType        ??????????????????
     * @param implHandler     ????????????service
     */
    FormNum(ResourceType resourceType, String desc,
            Class applicationType, Class<? extends IApplicationHandler> handler,
            Class implType, Class<? extends IImplHandler> implHandler) {
        this.resourceType = resourceType;
        this.desc = desc;
        this.applicationType = applicationType;
        this.handler = handler;
        this.implType = implType;
        this.implHandler = implHandler;
    }

    public String getDesc() {
        return desc;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public Class getApplicationType() {
        return applicationType;
    }

    public Class<? extends IApplicationHandler> getHandler() {
        return handler;
    }

    public Class getImplType() {
        return implType;
    }

    public Class<? extends IImplHandler> getImplHandler() {
        return implHandler;
    }

    /**
     * ???????????????
     */
    private static final ConcurrentHashMap<FormNum, HandlerWrapper> HANDLER_CACHE = new ConcurrentHashMap<>();

    /**
     * ??????????????????(????????????)????????????????????????????????????
     */
    public static HandlerWrapper getHandlerWrapperByInfo(ApplicationContext context, String formNumName) {
        return getHandlerWrapperByName(context,formNumName);
    }

    /**
     * ???????????????????????????????????????
     * @param context Spring?????????
     * @param name ????????????
     * @return ???????????????
     */
    public static HandlerWrapper getHandlerWrapperByName(ApplicationContext context,String name){
        FormNum formNum = FormNum.getFormNumByName(name);
        HandlerWrapper hw = HANDLER_CACHE.get(formNum);
        if (hw != null) {
            return hw;
        }
        IApplicationHandler handler = formNum.getHandler() == null ? null : context.getBean(formNum.getHandler());
        if(formNum.getImplHandler() == null){
            System.out.println(formNum.toString() + "ImplHandler is null");
        }
        IImplHandler implHandler = formNum.getImplHandler() == null ? null : context.getBean(formNum.getImplHandler());
        Class applicationType = formNum.getApplicationType() == null ? Object.class : formNum.getApplicationType();
        Class implType = formNum.getImplType() == null ? Object.class : formNum.getImplType();
        hw = new HandlerWrapper();
        hw.setFormNum(formNum);
        hw.setApplicationType(applicationType);
        hw.setHandler(handler);
        hw.setImplType(implType);
        hw.setImplHandler(implHandler);
        HANDLER_CACHE.put(formNum, hw);
        return hw;
    }

    /**
     * ??????????????????????????????
     * @param info ??????
     * @return ????????????
     */
    public static FormNum getFormNumByInfo(String formNumName) {
        return getFormNumByName(formNumName);
    }

    /**
     * ???????????????????????????????????????
     * @param name ????????????
     * @return ????????????
     */
    public static FormNum getFormNumByName(String name){
        FormNum formNum;
        try {
            formNum = FormNum.valueOf(name);
        } catch (Exception e) {
            throw new CustomException(CommonCode.APPLY_CODE_ERROR);
        }
        return formNum;
    }
}
