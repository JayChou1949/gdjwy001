package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.entity.application.ServiceLimit;
import com.baomidou.mybatisplus.extension.service.IService;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuc
 * @since 2020-03-03
 */
public interface IServiceLimitService extends IService<ServiceLimit> {

    ServiceLimit getQuota(String formNum,String area,String policeCategory,String nationalSpecialProject);


    /**
     * 增加额度
     * @param appInfoId 上报订单号
     * @param area  地市
     * @param policeCategory 警种
     */
    void increaseQuota(String appInfoId,String area,String policeCategory);

}
