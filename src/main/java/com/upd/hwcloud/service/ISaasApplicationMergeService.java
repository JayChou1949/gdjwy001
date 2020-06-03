package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.dto.SaasOrderTotal;
import com.upd.hwcloud.bean.dto.SaasTotal;
import com.upd.hwcloud.bean.dto.SaasUseTotal;
import com.upd.hwcloud.bean.entity.SaasApplicationMerge;
import com.upd.hwcloud.bean.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * SaaS资源申请合并信息表 服务类
 * </p>
 *
 * @author wuc
 * @since 2019-07-24
 */
public interface ISaasApplicationMergeService extends IService<SaasApplicationMerge> {

    /**
     * 合并单子
     * @param ids 待合并单子的id,多个使用,分隔
     */
    SaasApplicationMerge merge(User user, String ids);

    void update(User user, SaasApplicationMerge info);

    SaasApplicationMerge getDetails(String id);

    void deleteById(User user, String id);

    void saveImpl(User user, Map<String, Object> param, String modelid);

    IPage<SaasApplicationMerge> getFlowPage(User user, IPage<SaasApplicationMerge> page, Map<String, Object> param);

    IPage<SaasApplicationMerge> getFlowPageWithServiceName(User user, IPage<SaasApplicationMerge> page, Map<String, Object> param);


    List<SaasTotal> saasTotal(String areas, String policeCategory, Map<String, String> params);

    List<SaasOrderTotal> saasOrderTotal(String areas, String policeCategory, Map<String, String> params);

    List<SaasOrderTotal> saasOrderQuery(String areas, String policeCategory, Map<String, String> params);

    List<SaasUseTotal> saasUseTotal(String areas, String policeCategory, Map<String, String> params);

}
