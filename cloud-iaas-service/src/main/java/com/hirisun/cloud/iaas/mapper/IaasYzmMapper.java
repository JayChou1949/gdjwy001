package com.hirisun.cloud.iaas.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.iaas.bean.IaasYzm;

/**
 * IaaS 云桌面申请信息 Mapper 接口
 */
public interface IaasYzmMapper extends BaseMapper<IaasYzm> {

    Integer getTotalNum(@Param("appInfoId") String appInfoId);

    Integer getTotalNumOfShoppingCart(@Param("shoppingCartId") String shoppingCartId);
}
