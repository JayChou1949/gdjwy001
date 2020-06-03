package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.entity.IaasFunDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * IAAS功能详情 服务类
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
public interface IIaasFunDetailService extends IService<IaasFunDetail> {

    void save(List<IaasFunDetail> funDetails, String masterId);

    List<IaasFunDetail> getByIaasId(String iaasId);
}
