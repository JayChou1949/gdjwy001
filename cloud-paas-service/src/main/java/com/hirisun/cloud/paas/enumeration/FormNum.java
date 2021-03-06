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

    //???????????????
    PAAS_GAWDSJPT(ResourceType.PAAS,"???????????????",PaasBigdataComponent.class,IPaasBigdataComponentService.class,PaasComponentImpl.class,IPaasComponentImplService.class),

    PAAS_DEFAULT(ResourceType.PAAS, "PAAS????????????", null, null, null, null),

    //    PAAS_TYXX(ResourceType.PAAS, "????????????", PaasTyxx.class, IPaasTyxxService.class, null, null),
    PAAS_TYXX(ResourceType.PAAS, "????????????", PaasTyxx.class, IPaasTyxxService.class, PaasTyyhImpl.class, IPaasTyyhImplService.class),
    PAAS_TYYH(ResourceType.PAAS, "????????????", PaasTyyh.class, IPaasTyyhService.class, PaasTyyhImpl.class, IPaasTyyhImplService.class),
    PAAS_FIREWALL_OPEN(ResourceType.PAAS,"?????????????????????", PaasFirewallOpen.class,IPaasFirewallOpenService.class,null,null),

    PAAS_LXJS(ResourceType.PAAS, "????????????", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_SSJS(ResourceType.PAAS, "????????????", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_LSJS(ResourceType.PAAS, "????????????", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_NCJS(ResourceType.PAAS, "????????????", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_QWSJK(ResourceType.PAAS, "???????????????", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_FBSWJXT(ResourceType.PAAS, "?????????????????????", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_NCSJK(ResourceType.PAAS, "???????????????", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_TSJK(ResourceType.PAAS, "????????????", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),
    PAAS_YPTDSJHDZYB(ResourceType.PAAS, "??????????????????HD?????????", PaasDatabase.class, IPaasDatabaseService.class, PaasDatabaseImpl.class, IPaasDatabaseImplService.class),

    PAAS_FBSBXSJK(ResourceType.PAAS, "????????????????????????", PaasFbsbxsjk.class, IPaasFbsbxsjkService.class, null, null),

    PAAS_DISTRIBUTED_DB(ResourceType.PAAS,"Libra+?????????????????????",PaasDistributedDbApply.class,IPaasDistributedDbApplyService.class,PaasDistributedDbApplyImpl.class,IPaasDistributedDbApplyImplService.class),

    PAAS_FBSXX(ResourceType.PAAS, "???????????????", null, null, null, null),
    PAAS_DTFW(ResourceType.PAAS, "????????????", null, null, null, null),
    PAAS_OCR(ResourceType.PAAS, "OCR", null, null, null, null),
    PAAS_YYSB(ResourceType.PAAS, "????????????", null, null, null, null),

    PAAS_DTHTJY(ResourceType.PAAS, "??????-????????????", PaasDthtjy.class, IPaasDthtjyService.class, PaasDtImpl.class, IPaasDtImplService.class),
    PAAS_DTSJGT(ResourceType.PAAS, "??????-????????????", PaasDtsjgt.class, IPaasDtsjgtService.class, PaasDtImpl.class, IPaasDtImplService.class),
    //PAAS_GAWDSJPT(ResourceType.PAAS, "??????????????????????????????", PaasGawdsjpt.class, IPaasGawdsjptService.class, null, null),

    PAAS_RELATIONAL_DATABASE(ResourceType.PAAS,"??????????????????", PaasRdbBase.class,IPaasRdbBaseService.class,PaasRdbBase.class,IPaasRdbBaseService.class),
    PAAS_APIWG(ResourceType.PAAS, "API??????", PaasApiwg.class, IPaasApiwgService.class, null, null),
    PAAS_FBSHC(ResourceType.PAAS, "???????????????", PaasFbshc.class, IPaasFbshcService.class, null, null),

    //??????DCS?????????
    PAAS_DCS(ResourceType.PAAS,"??????????????????DCS???????????????", PaasDcs.class,IPaasDcsService.class, PaasDcsImpl.class, IPaasDcsImplService.class),
    PAAS_DMS(ResourceType.PAAS,"??????????????????DMS???????????????", PaasDms.class,IPaasDmsService.class, PaasDcsImpl.class, IPaasDcsImplService.class),
    PAAS_ELB(ResourceType.PAAS,"ELB????????????????????????", PaasElb.class,IPaasElbService.class, PaasDcsImpl.class, IPaasDcsImplService.class),

    PAAS_TXRLSB(ResourceType.PAAS, "??????????????????", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_OCRWT(ResourceType.PAAS, "OCR??????", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_OCRYM(ResourceType.PAAS, "OCR??????", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_FSEQSQM(ResourceType.PAAS, "?????????????????????", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_LJFX_HTJY(ResourceType.PAAS, "??????????????????????????????-????????????", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_ZBZH_HTJY(ResourceType.PAAS, "??????????????????????????????-????????????", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_KDXF_AI(ResourceType.PAAS, "????????????????????????AI?????????", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_DZY(ResourceType.PAAS, "???????????????????????????", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_RLDSJ(ResourceType.PAAS, "?????????????????????????????????", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_TYSQ(ResourceType.PAAS, "????????????????????????????????????", PaasFseqsqm.class, IPaasFseqsqmService.class, PaasFseqsqmImpl.class, IPaasFseqsqmImplService.class),
    PAAS_GAUSSDB(ResourceType.PAAS, "GaussDB??????????????????",PassGaussdbInfo.class, IPassGaussdbInfoService.class, null, null),
    PAAS_SECURITY_WAF(ResourceType.PAAS,"?????????????????????????????????-WAF",PaasSecurityWaf.class,IPaasSecurityWafService.class,null,null),
    PAAS_SECURITY_SCAN(ResourceType.PAAS,"?????????????????????????????????-??????????????????",PaasSecurityScan.class,IPaasSecurityScanService.class,null,null),
    PAAS_SECURITY_LOG_AUDIT(ResourceType.PAAS,"?????????????????????????????????-??????????????????",PaasSecurityLogAuit.class,IPaasSecurityLogAuitService.class,null,null),
    PAAS_SECURITY_TAMPER_PROOF(ResourceType.PAAS,"?????????????????????????????????-???????????????",PaasSecurityTamperProof.class,IPaasSecurityTamperProofService.class,null,null);

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
    public static HandlerWrapper getHandlerWrapperByInfo(ApplicationContext context, String name) {
        return getHandlerWrapperByName(context,name);
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
