package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.entity.EpidemicApplication;
import com.upd.hwcloud.bean.entity.IaasZysb;
import com.upd.hwcloud.bean.entity.Register;
import com.upd.hwcloud.bean.entity.SaasApplicationMerge;
import com.upd.hwcloud.bean.entity.SaasRegister;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.ResourceRecover;
import com.upd.hwcloud.bean.entity.application.ResourceRecoverAppInfo;

import java.util.List;
import java.util.Map;

/**
 * 用于提高分页查询效率的工具类服务
 * @author yyc
 * @date 2020/4/10
 */
public interface ISpeedUpService {


    /**
     * 传入实例ID集合获取    实例ID-待办人身份证号集合映射关系
     * @param instanceIdList 实例ID集合
     * @return map
     */
    Map<String,String> instanceToHandleIdCards(List<String> instanceIdList);


    /**
     * 身份证号转名字
     * @param idCardToNameMap 身份证和名字映射关系
     * @param record 表单数据
     */
    void convertIdCardToName(Map<String,String> idCardToNameMap,ApplicationInfo record);


    /**
     * 获取身份证号与名字关联的Map
     * @param idCardsList 身份证号集合 {"5110022522,545451515","45454551515,454554"}
     * @return  {"5110022522":"jack"}
     */
    Map<String,String> idCardsNameMap(List<String> idCardsList);

    /**
     * 填充当前处理人
     * @param records
     * @param user
     */
    void dealProcessingPerson(List<ApplicationInfo> records, User user);

    /**
     * 疫情应用
     * @param records
     * @param user
     */
    void dealProcessingPersonEpidemic(List<EpidemicApplication> records, User user);

    /**
     * 注册
     * @param records
     * @param user
     * @param <T>
     */
    <T extends Register>void dealProcessingPersonRegister(List<T> records, User user);

    /**
     * 应用
     * @param records
     * @param user
     */
    void dealProcessingPersonApplication(List<SaasApplicationMerge> records, User user);


    /**
     * 服务发布
     * @param records
     * @param user
     */
    void dealProcessingPersonPublish(List<ServicePublish> records, User user);

    /**
     * 资源上报
     * @param records
     * @param user
     */
    void dealProcessingPersonZysb(List<IaasZysb> records, User user);


    void dealProcessingPersonResourceRecovered(List<ResourceRecoverAppInfo> records,User user);


}
