package com.hirisun.cloud.saas.service;

import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.saas.bean.SaasApply;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * SaaS资源申请原始信息表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-24
 */
public interface SaasApplyService extends IService<SaasApply> {
    void create(UserVO user, SaasApply info);

}
