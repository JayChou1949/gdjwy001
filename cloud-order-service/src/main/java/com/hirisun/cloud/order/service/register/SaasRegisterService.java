package com.hirisun.cloud.order.service.register;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.register.SaasRegister;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * SAAS服务注册表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-02
 */
public interface SaasRegisterService extends IRegisterService<SaasRegister> {
    /**
        * 应用变更
     * @param info 应用注册信息
     * @return R
     */
    QueryResponseResult change(UserVO user, SaasRegister info, String oldName);
}
