package com.hirisun.cloud.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.system.ServiceLimitVo;
import com.hirisun.cloud.system.bean.ServiceLimit;

/**
 * 限额
 */
public interface ServiceLimitService extends IService<ServiceLimit> {

	ServiceLimitVo getQuota(String formNum,String area,String policeCategory,
			String nationalSpecialProject,String clusterName);


    /**
     * 增加额度
     * @param appInfoId 上报订单号
     * @param area  地市
     * @param policeCategory 警种
     * @param nationalSpecialProject 国家专项
     */
    void increaseQuota(String appInfoId,String area,String policeCategory,String nationalSpecialProject);

}
