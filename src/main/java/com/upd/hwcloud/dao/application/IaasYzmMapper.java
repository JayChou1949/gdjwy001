package com.upd.hwcloud.dao.application;

import com.upd.hwcloud.bean.entity.application.IaasYzm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * IaaS 云桌面申请信息 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2019-01-07
 */
public interface IaasYzmMapper extends BaseMapper<IaasYzm> {

    Integer getTotalNum(@Param("appInfoId") String appInfoId);

    Integer getTotalNumOfShoppingCart(@Param("shoppingCartId") String shoppingCartId);
}
