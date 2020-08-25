package com.hirisun.cloud.saas.enumeration;


import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;

import com.hirisun.cloud.common.contains.ResourceType;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.saas.bean.SaasServiceApplication;
import com.hirisun.cloud.saas.service.IApplicationHandler;
import com.hirisun.cloud.saas.service.IImplHandler;
import com.hirisun.cloud.saas.service.ISaasServiceApplicationService;

public enum FormNum {

    SAAS_SERVICE(ResourceType.SAAS_SERVICE,"SaaS服务",SaasServiceApplication.class,ISaasServiceApplicationService.class,SaasServiceApplication.class,ISaasServiceApplicationService.class),
    SAAS(ResourceType.SAAS, "SAAS资源申请", null, null, null, null);

    private final String desc;
    private final ResourceType resourceType;
    private final Class applicationType;
    private final Class<? extends IApplicationHandler> handler;
    private final Class implType;
    private final Class<? extends IImplHandler> implHandler;

    /**
     * 构造器
     *
     * @param resourceType    资源类型
     * @param desc            描述
     * @param applicationType 申请信息实体
     * @param handler         申请信息service
     * @param implType        实施信息实体
     * @param implHandler     实施信息service
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
     * 处理器缓存
     */
    private static final ConcurrentHashMap<FormNum, HandlerWrapper> HANDLER_CACHE = new ConcurrentHashMap<>();

    /**
     * 通过申请信息(服务编码)获取该编码对应的处理器等
     */
    public static HandlerWrapper getHandlerWrapperByInfo(ApplicationContext context, String name){ 
        return getHandlerWrapperByName(context,name);
    }

    /**
     * 通过表单编码获取对应处理器
     * @param context Spring上下文
     * @param name 表单编码
     * @return 处理器信息
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
     * 通过订单获取表单枚举
     * @param info 订单
     * @return 表单枚举
     */
    public static FormNum getFormNumByInfo(String formNumName) {
        return getFormNumByName(formNumName);
    }

    /**
     * 通过表单编码获取表单枚举类
     * @param name 表单编码
     * @return 表单枚举
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
