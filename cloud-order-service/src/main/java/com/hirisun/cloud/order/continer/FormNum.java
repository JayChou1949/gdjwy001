package com.hirisun.cloud.order.continer;

import java.util.concurrent.ConcurrentHashMap;

import com.hirisun.cloud.common.contains.ResourceType;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.vo.CommonCode;


public enum FormNum {

    //大数据组件
    IAAS_DEFAULT(ResourceType.IAAS, "IAAS默认表单"),
    IAAS_TXYFW(ResourceType.IAAS, "弹性云服务"),
    IAAS_TXYFWXG(ResourceType.IAAS, "弹性云服务修改"),
    IAAS_TXYFWBG(ResourceType.IAAS, "弹性云服务变更"),
    IAAS_RQZY(ResourceType.IAAS, "容器资源"),
    IAAS_YZM(ResourceType.IAAS, "云桌面"),
    IAAS_SFSWJFW(ResourceType.IAAS, "SFS文件服务"),
    IAAS_DXCC(ResourceType.IAAS, "对象存储"),
    IAAS_TXYFZJH(ResourceType.IAAS, "弹性云负载均衡"),
    IAAS_YZMYZY(ResourceType.IAAS, "云桌面云资源"),

    //    PAAS_TYXX(ResourceType.PAAS, "统一消息"),
    PAAS_GAWDSJPT(ResourceType.PAAS,"大数据组件"),
    PAAS_DEFAULT(ResourceType.PAAS, "PAAS默认表单"),
    PAAS_TYXX(ResourceType.PAAS, "统一消息"),
    PAAS_TYYH(ResourceType.PAAS, "统一用户"),
    PAAS_FIREWALL_OPEN(ResourceType.PAAS,"防火墙开通表单"),

    PAAS_LXJS(ResourceType.PAAS, "离线计算"),
    PAAS_SSJS(ResourceType.PAAS, "实时计算"),
    PAAS_LSJS(ResourceType.PAAS, "流式计算"),
    PAAS_NCJS(ResourceType.PAAS, "内存计算"),
    PAAS_QWSJK(ResourceType.PAAS, "全文数据库"),
    PAAS_FBSWJXT(ResourceType.PAAS, "分布式文件系统"),
    PAAS_NCSJK(ResourceType.PAAS, "内存数据库"),
    PAAS_TSJK(ResourceType.PAAS, "图数据库"),
    PAAS_YPTDSJHDZYB(ResourceType.PAAS, "云平台大数据HD资源表"),

    PAAS_FBSBXSJK(ResourceType.PAAS, "分布式并行数据库"),

    PAAS_DISTRIBUTED_DB(ResourceType.PAAS,"Libra+分布并行数据库"),

    DAAS(ResourceType.DAAS, "DAAS资源申请"),

    SAAS_SERVICE(ResourceType.SAAS_SERVICE,"SaaS服务"),

    SAAS(ResourceType.SAAS, "SAAS资源申请"),
    PAAS_FBSXX(ResourceType.PAAS, "分布式消息"),
    PAAS_DTFW(ResourceType.PAAS, "地图服务"),
    PAAS_OCR(ResourceType.PAAS, "OCR"),
    PAAS_YYSB(ResourceType.PAAS, "语音识别"),

    PAAS_DTHTJY(ResourceType.PAAS, "地图-航天精一"),
    PAAS_DTSJGT(ResourceType.PAAS, "地图-世纪高通"),
    //PAAS_GAWDSJPT(ResourceType.PAAS, "公安网大数据平台资源", PaasGawdsjpt.class, IPaasGawdsjptService.class, null, null),

    PAAS_RELATIONAL_DATABASE(ResourceType.PAAS,"关系型数据库"),
    PAAS_APIWG(ResourceType.PAAS, "API网关"),
    PAAS_FBSHC(ResourceType.PAAS, "分布式缓存"),

    //共用DCS实施表
    PAAS_DCS(ResourceType.PAAS,"基于虚拟机的DCS分布式缓存"),
    PAAS_DMS(ResourceType.PAAS,"基于虚拟机的DMS分布式消息"),
    PAAS_ELB(ResourceType.PAAS,"ELB弹性负载均衡系统"),

    PAAS_TXRLSB(ResourceType.PAAS, "腾讯人脸识别"),
    PAAS_OCRWT(ResourceType.PAAS, "OCR文通"),
    PAAS_OCRYM(ResourceType.PAAS, "OCR云脉"),
    PAAS_FSEQSQM(ResourceType.PAAS, "飞识二期授权码"),
    PAAS_LJFX_HTJY(ResourceType.PAAS, "广东科信路径分析服务-航天精一"),
    PAAS_ZBZH_HTJY(ResourceType.PAAS, "广东科信坐标转换服务-航天精一"),
    PAAS_KDXF_AI(ResourceType.PAAS, "广东科信科大讯飞AI云服务"),
    PAAS_DZY(ResourceType.PAAS, "广东科信地址云服务"),
    PAAS_RLDSJ(ResourceType.PAAS, "广东科信人脸大数据服务"),
    PAAS_TYSQ(ResourceType.PAAS, "广东公安权限中心管理服务"),
    PAAS_GAUSSDB(ResourceType.PAAS, "GaussDB申请信息服务"),
    PAAS_SECURITY_WAF(ResourceType.PAAS,"广东公安大数据安全体系-WAF"),
    PAAS_SECURITY_SCAN(ResourceType.PAAS,"广东公安大数据安全体系-安全漏洞扫描"),
    PAAS_SECURITY_LOG_AUDIT(ResourceType.PAAS,"广东公安大数据安全体系-综合日志审计"),
    PAAS_SECURITY_TAMPER_PROOF(ResourceType.PAAS,"广东公安大数据安全体系-网页防篡改");
    
    private final String desc;
    private final ResourceType resourceType;

    /**
     * 构造器
     *
     * @param resourceType    资源类型
     * @param desc            描述
     */
    FormNum(ResourceType resourceType, String desc) {
        this.resourceType = resourceType;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * 处理器缓存
     */
    private static final ConcurrentHashMap<FormNum, HandlerWrapper> HANDLER_CACHE = new ConcurrentHashMap<>();

    /**
     * 通过表单编码获取对应处理器
     * @param context Spring上下文
     * @param name 表单编码
     * @return 处理器信息
     */
    public static HandlerWrapper getHandlerWrapperByName(String name){
        FormNum formNum = FormNum.getFormNumByName(name);
        HandlerWrapper hw = HANDLER_CACHE.get(formNum);
        if (hw != null) {
            return hw;
        }
        hw = new HandlerWrapper();
        hw.setFormNum(formNum);
        HANDLER_CACHE.put(formNum, hw);
        return hw;
    }

    /**
     * 通过订单获取表单枚举
     * @param info 订单
     * @return 表单枚举
     */
    public static FormNum getFormNumByInfo(String formNum) {
        return getFormNumByName(formNum);
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
