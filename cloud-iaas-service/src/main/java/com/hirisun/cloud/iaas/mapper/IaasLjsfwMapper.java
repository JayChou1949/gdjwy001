package com.hirisun.cloud.iaas.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.iaas.bean.IaasLjsfw;

/**
 * 裸金属服务器申请信息 Mapper 接口
 */
public interface IaasLjsfwMapper extends BaseMapper<IaasLjsfw> {

    Integer getTotalNum(@Param("appInfoId") String appInfoId);

    Integer getTotalNumOfShoppingCart(@Param("shoppingCartId") String shoppingCartId);
}
