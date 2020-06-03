package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.dto.SaasRecoverTotal;
import com.upd.hwcloud.bean.entity.SaasRecoverApplication;
import com.upd.hwcloud.bean.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  SAAS权限服务回收申请单 服务类
 * </p>
 *
 * @author wangbao
 * @since 2019-10-24
 */
public interface ISaasRecoverApplicationService extends IService<SaasRecoverApplication> {

    SaasRecoverApplication create(User user, SaasRecoverApplication info,String area,String policeCategory);

    void update(User user, SaasRecoverApplication info);

    void deleteById(User user, String id);

    IPage<SaasRecoverApplication> getPage(User user, IPage<SaasRecoverApplication> page, Map<String, Object> param);

    IPage<SaasRecoverApplication> getPageWithHandler(User user,IPage<SaasRecoverApplication> page, Map<String, Object> param);

    SaasRecoverApplication getDetails(String id);

    void saveImpl(User user, Map<String, Object> param, String modelid);

    /**
     * saas服务权限回收统计工单导出
     */
    List<SaasRecoverTotal> saasRecoverExport(String areas, String policeCategory, Map<String, String> params);

    void updateStatus(String id, String status);

    void deleteRecoverInfo(User user, String id);

}
