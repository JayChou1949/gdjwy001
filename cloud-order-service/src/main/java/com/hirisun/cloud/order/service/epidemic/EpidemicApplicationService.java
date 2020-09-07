package com.hirisun.cloud.order.service.epidemic;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.epidemic.EpidemicApplication;
import com.hirisun.cloud.order.vo.ImplRequest;

import java.util.Map;

/**
 * <p>
 * SaaS资源申请原始信息表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-03
 */
public interface EpidemicApplicationService extends IService<EpidemicApplication> {

    IPage<EpidemicApplication> getFlowPage(UserVO user, IPage<EpidemicApplication> page, Map<String, Object> param);

    IPage<EpidemicApplication> getFlowPageWithServiceName(UserVO user, IPage<EpidemicApplication> page, Map<String, Object> param);

    void create(UserVO user, EpidemicApplication info);

    EpidemicApplication getDetails(String id);

    void update(UserVO user, EpidemicApplication info);

    public QueryResponseResult impl(UserVO user, ImplRequest implRequest);


}
