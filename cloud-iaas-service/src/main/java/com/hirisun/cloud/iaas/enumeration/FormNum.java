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

    //大数据组件
    IAAS_DEFAULT(ResourceType.IAAS, "IAAS默认表单", null, null, null, null),
    IAAS_TXYFW(ResourceType.IAAS, "弹性云服务", IaasTxyfw.class, IIaasTxyfwService.class, IaasTxyfwImpl.class, IIaasTxyfwImplService.class),
    IAAS_BAREMETAL(ResourceType.IAAS, "裸金属服务", IaasLjsfw.class, IIaasLjsfwService.class, IaasLjsfwImpl.class, IIaasLjsfwImplService.class),
    IAAS_TXYFWXG(ResourceType.IAAS, "弹性云服务修改", IaasTxyfwxg.class, IIaasTxyfwxgService.class, IaasTxyfwxgImpl.class, IIaasTxyfwxgImplService.class),
    IAAS_TXYFWBG(ResourceType.IAAS, "弹性云服务变更", IaasTxyfwbg.class, IIaasTxyfwbgService.class, IaasTxyfwbgImpl.class, IIaasTxyfwbgImplService.class),
    IAAS_RQZY(ResourceType.IAAS, "容器资源", PaasRqzy.class, IPaasRqzyService.class, PaasRqzyImpl.class, IPaasRqzyImplService.class),
    IAAS_YZM(ResourceType.IAAS, "云桌面", IaasYzm.class, IIaasYzmService.class, IaasYzmImpl.class, IIaasYzmImplService.class),
    IAAS_SFSWJFW(ResourceType.IAAS, "SFS文件服务", IaasSfswjfw.class, IIaasSfswjfwService.class, IaasSfswjfwImpl.class, IIaasSfswjfwImplService.class),
    IAAS_DXCC(ResourceType.IAAS, "对象存储", IaasDxccExt.class, IIaasDxccExtService.class, IaasDxccImpl.class, IIaasDxccImplService.class),
    IAAS_TXYFZJH(ResourceType.IAAS, "弹性云负载均衡", IaasTxyfzjh.class, IIaasTxyfzjhService.class, IaasTxyfzjhImpl.class, IIaasTxyfzjhImplService.class),
    IAAS_YZMYZY(ResourceType.IAAS, "云桌面云资源", IaasYzmyzy.class, IIaasYzmyzyService.class, IaasYzmyzyUser.class, IIaasYzmyzyUserService.class);

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
    public static HandlerWrapper getHandlerWrapperByInfo(ApplicationContext context, String formNumName) {
        return getHandlerWrapperByName(context,formNumName);
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
