package com.hirisun.cloud.paas.enumeration;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;

import com.hirisun.cloud.common.contains.ResourceType;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.paas.bean.PaasApiwg;
import com.hirisun.cloud.paas.bean.PaasBigdataComponent;
import com.hirisun.cloud.paas.bean.PaasComponentImpl;
import com.hirisun.cloud.paas.bean.PaasDatabase;
import com.hirisun.cloud.paas.bean.PaasDatabaseImpl;
import com.hirisun.cloud.paas.bean.PaasDcs;
import com.hirisun.cloud.paas.bean.PaasDcsImpl;
import com.hirisun.cloud.paas.bean.PaasDistributedDbApply;
import com.hirisun.cloud.paas.bean.PaasDms;
import com.hirisun.cloud.paas.bean.PaasDtImpl;
import com.hirisun.cloud.paas.bean.PaasDthtjy;
import com.hirisun.cloud.paas.bean.PaasDtsjgt;
import com.hirisun.cloud.paas.bean.PaasElb;
import com.hirisun.cloud.paas.bean.PaasFbsbxsjk;
import com.hirisun.cloud.paas.bean.PaasFbshc;
import com.hirisun.cloud.paas.bean.PaasFirewallOpen;
import com.hirisun.cloud.paas.bean.PaasFseqsqm;
import com.hirisun.cloud.paas.bean.PaasFseqsqmImpl;
import com.hirisun.cloud.paas.bean.PaasRdbBase;
import com.hirisun.cloud.paas.bean.PaasSecurityLogAuit;
import com.hirisun.cloud.paas.bean.PaasSecurityScan;
import com.hirisun.cloud.paas.bean.PaasSecurityTamperProof;
import com.hirisun.cloud.paas.bean.PaasSecurityWaf;
import com.hirisun.cloud.paas.bean.PaasTyxx;
import com.hirisun.cloud.paas.bean.PaasTyyh;
import com.hirisun.cloud.paas.bean.PaasTyyhImpl;
import com.hirisun.cloud.paas.bean.PassGaussdbInfo;
import com.hirisun.cloud.paas.service.IApplicationHandler;
import com.hirisun.cloud.paas.service.IImplHandler;
import com.hirisun.cloud.paas.service.IPaasApiwgService;
import com.hirisun.cloud.paas.service.IPaasBigdataComponentService;
import com.hirisun.cloud.paas.service.IPaasComponentImplService;
import com.hirisun.cloud.paas.service.IPaasDatabaseImplService;
import com.hirisun.cloud.paas.service.IPaasDatabaseService;
import com.hirisun.cloud.paas.service.IPaasDcsImplService;
import com.hirisun.cloud.paas.service.IPaasDcsService;
import com.hirisun.cloud.paas.service.IPaasDistributedDbApplyImplService;
import com.hirisun.cloud.paas.service.IPaasDistributedDbApplyService;
import com.hirisun.cloud.paas.service.IPaasDmsService;
import com.hirisun.cloud.paas.service.IPaasDtImplService;
import com.hirisun.cloud.paas.service.IPaasDthtjyService;
import com.hirisun.cloud.paas.service.IPaasDtsjgtService;
import com.hirisun.cloud.paas.service.IPaasElbService;
import com.hirisun.cloud.paas.service.IPaasFbsbxsjkService;
import com.hirisun.cloud.paas.service.IPaasFbshcService;
import com.hirisun.cloud.paas.service.IPaasFirewallOpenService;
import com.hirisun.cloud.paas.service.IPaasFseqsqmImplService;
import com.hirisun.cloud.paas.service.IPaasFseqsqmService;
import com.hirisun.cloud.paas.service.IPaasRdbBaseService;
import com.hirisun.cloud.paas.service.IPaasSecurityLogAuitService;
import com.hirisun.cloud.paas.service.IPaasSecurityScanService;
import com.hirisun.cloud.paas.service.IPaasSecurityTamperProofService;
import com.hirisun.cloud.paas.service.IPaasSecurityWafService;
import com.hirisun.cloud.paas.service.IPaasTyxxService;
import com.hirisun.cloud.paas.service.IPaasTyyhImplService;
import com.hirisun.cloud.paas.service.IPaasTyyhService;
import com.hirisun.cloud.paas.service.IPassGaussdbInfoService;
import com.hirisun.cloud.paas.service.impl.PaasDistributedDbApplyImpl;

public enum FormNum {

    //大数据组件
    PAAS_GAWDSJPT(ResourceType.PAAS,"大数据组件",PaasBigdataComponent.class,IPaasBigdataComponentService.class,PaasComponentImpl.class,IPaasComponentImplService.class),

    PAAS_DEFAULT(ResourceType.PAAS, "PAAS默认表单", null, null, null, null),

    //    PAAS_TYXX(ResourceType.PAAS, "统一消息", PaasTyxx.class, IPaasTyxxService.class, null, null),
    PAAS_TYXX(ResourceType.PAAS, "统一消息", PaasTyxx.class, IPaasTyxxService.class, PaasTyyhImpl.class, IPaasTyyhImplService.class),
    PAAS_TYYH(ResourceType.PAAS, "统一用户", PaasTyyh.class, IPaasTyyhService.class, PaasTyyhImpl.class, IPaasTyyhImplService.class),
    PAAS_FIREWALL_OPEN(ResourceType.PAAS,"防火墙开通表单", PaasFirewallOpen.class,IPaasFirewallOpenService.class,null,null),

    PAAS_LXJS(ResourceType.PAAS, "离线计算", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_SSJS(ResourceType.PAAS, "实时计算", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_LSJS(ResourceType.PAAS, "流式计算", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_NCJS(ResourceType.PAAS, "内存计算", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_QWSJK(ResourceType.PAAS, "全文数据库", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_FBSWJXT(ResourceType.PAAS, "分布式文件系统", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_NCSJK(ResourceType.PAAS, "内存数据库", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_TSJK(ResourceType.PAAS, "图数据库", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_YPTDSJHDZYB(ResourceType.PAAS, "云平台大数据HD资源表", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),

    PAAS_FBSBXSJK(ResourceType.PAAS, "分布式并行数据库", PaasFbsbxsjk.class, IPaasFbsbxsjkService.class, null, null),

    PAAS_DISTRIBUTED_DB(ResourceType.PAAS,"Libra+分布并行数据库",PaasDistributedDbApply.class,IPaasDistributedDbApplyService.class,PaasDistributedDbApplyImpl.class,IPaasDistributedDbApplyImplService.class),

    PAAS_FBSXX(ResourceType.PAAS, "分布式消息", null, null, null, null),
    PAAS_DTFW(ResourceType.PAAS, "地图服务", null, null, null, null),
    PAAS_OCR(ResourceType.PAAS, "OCR", null, null, null, null),
    PAAS_YYSB(ResourceType.PAAS, "语音识别", null, null, null, null),

    PAAS_DTHTJY(ResourceType.PAAS, "地图-航天精一", PaasDthtjy.class, IPaasDthtjyService.class, PaasDtImpl.class, IPaasDtImplService.class),
    PAAS_DTSJGT(ResourceType.PAAS, "地图-世纪高通", PaasDtsjgt.class, IPaasDtsjgtService.class, PaasDtImpl.class, IPaasDtImplService.class),
    //PAAS_GAWDSJPT(ResourceType.PAAS, "公安网大数据平台资源", PaasGawdsjpt.class, IPaasGawdsjptService.class, null, null),

    PAAS_RELATIONAL_DATABASE(ResourceType.PAAS,"关系型数据库", PaasRdbBase.class,IPaasRdbBaseService.class,PaasRdbBase.class,IPaasRdbBaseService.class),
    PAAS_APIWG(ResourceType.PAAS, "API网关", PaasApiwg.class, IPaasApiwgService.class, null, null),
    PAAS_FBSHC(ResourceType.PAAS, "分布式缓存", PaasFbshc.class, IPaasFbshcService.class, null, null),

    //共用DCS实施表
    PAAS_DCS(ResourceType.PAAS,"基于虚拟机的DCS分布式缓存", PaasDcs.class,IPaasDcsService.class, PaasDcsImpl.class, IPaasDcsImplService.class),
    PAAS_DMS(ResourceType.PAAS,"基于虚拟机的DMS分布式消息", PaasDms.class,IPaasDmsService.class, PaasDcsImpl.class, IPaasDcsImplService.class),
    PAAS_ELB(ResourceType.PAAS,"ELB弹性负载均衡系统", PaasElb.class,IPaasElbService.class, PaasDcsImpl.class, IPaasDcsImplService.class),

    PAAS_TXRLSB(ResourceType.PAAS, "腾讯人脸识别", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_OCRWT(ResourceType.PAAS, "OCR文通", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_OCRYM(ResourceType.PAAS, "OCR云脉", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_FSEQSQM(ResourceType.PAAS, "飞识二期授权码", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_LJFX_HTJY(ResourceType.PAAS, "广东科信路径分析服务-航天精一", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_ZBZH_HTJY(ResourceType.PAAS, "广东科信坐标转换服务-航天精一", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_KDXF_AI(ResourceType.PAAS, "广东科信科大讯飞AI云服务", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_DZY(ResourceType.PAAS, "广东科信地址云服务", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_RLDSJ(ResourceType.PAAS, "广东科信人脸大数据服务", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_TYSQ(ResourceType.PAAS, "广东公安权限中心管理服务", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_GAUSSDB(ResourceType.PAAS, "GaussDB申请信息服务",PassGaussdbInfo.class, IPassGaussdbInfoService.class, null, null),
    PAAS_SECURITY_WAF(ResourceType.PAAS,"广东公安大数据安全体系-WAF",PaasSecurityWaf.class,IPaasSecurityWafService.class,null,null),
    PAAS_SECURITY_SCAN(ResourceType.PAAS,"广东公安大数据安全体系-安全漏洞扫描",PaasSecurityScan.class,IPaasSecurityScanService.class,null,null),
    PAAS_SECURITY_LOG_AUDIT(ResourceType.PAAS,"广东公安大数据安全体系-综合日志审计",PaasSecurityLogAuit.class,IPaasSecurityLogAuitService.class,null,null),
    PAAS_SECURITY_TAMPER_PROOF(ResourceType.PAAS,"广东公安大数据安全体系-网页防篡改",PaasSecurityTamperProof.class,IPaasSecurityTamperProofService.class,null,null);

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
    public static HandlerWrapper getHandlerWrapperByInfo(ApplicationContext context, String name) {
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
