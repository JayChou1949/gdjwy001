package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.EpidemicApplication;
import com.baomidou.mybatisplus.extension.service.IService;

import com.upd.hwcloud.bean.entity.SaasApplicationMerge;
import com.upd.hwcloud.bean.entity.User;

import java.util.Map;

/**
 * <p>
 * 疫情应用申请信息表 服务类
 * </p>
 *
 * @author wuc
 * @since 2020-02-27
 */
public interface IEpidemicApplicationService extends IService<EpidemicApplication> {

    void create(User user, EpidemicApplication info);

    void update(User user, EpidemicApplication info);

    void deleteById(User user,String id);

    void saveImpl(User user, Map<String, Object> param, String modelId);

    IPage<EpidemicApplication> getFlowPage(User user, IPage<EpidemicApplication> page, Map<String, Object> param);

    IPage<EpidemicApplication> getFlowPageWithServiceName(User user, IPage<EpidemicApplication> page, Map<String, Object> param);

    EpidemicApplication getDetails(String id);

    EpidemicApplication getByActId(String activityId);

}
