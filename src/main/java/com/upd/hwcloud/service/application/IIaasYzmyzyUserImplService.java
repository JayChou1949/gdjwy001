package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.entity.application.IaasYzmyzyUser;
import com.upd.hwcloud.bean.entity.application.IaasYzmyzyUserImpl;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 统一用户人员信息 服务类
 * </p>
 *
 * @author zwb
 * @since 2019-05-09
 */
public interface IIaasYzmyzyUserImplService extends IService<IaasYzmyzyUserImpl> {

    void saveUserList(List<IaasYzmyzyUser> impls, String appName);
}
