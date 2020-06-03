package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.BaseSubpage;
import com.upd.hwcloud.bean.entity.IaasSubpage;
import com.upd.hwcloud.bean.entity.User;

/**
 * <p>
 * iaas二级页面配置 服务类
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
public interface IBaseSubpageService<T extends BaseSubpage> extends IService<T>  {

    void saveIaasPage(User user, T iaas, String iaasId);

    void updateIaasPage(User user, T iaas, String iaasId);

    T getDetail(String iaasId);
}
