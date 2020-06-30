package com.upd.hwcloud.service.application.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.SaasApplicationExt;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationViolation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xqp
 * @since 2020-06-30
 */
public interface ApplicationViolationService extends IService<ApplicationViolation> {

    IPage<ApplicationViolation> getViolationUser(IPage<ApplicationViolation> page, String areaOrPolice, String creatorName, String orgName, String violationTime);
}
