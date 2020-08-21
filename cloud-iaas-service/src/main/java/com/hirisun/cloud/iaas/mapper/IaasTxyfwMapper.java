package com.hirisun.cloud.iaas.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.iaas.bean.IaasTxyfw;
import com.hirisun.cloud.iaas.bean.IaasTxyfwImpl;

/**
 * 弹性云服务器申请信息 Mapper 接口
 */
public interface IaasTxyfwMapper extends BaseMapper<IaasTxyfw> {

    Integer getTotalNum(@Param("appInfoId") String appInfoId);

    Integer getTotalNumOfShoppingCart(@Param("shoppingCartId") String shoppingCartId);

    IPage<IaasTxyfwImpl> getEcsListInWorkbench(IPage<IaasTxyfwImpl> page, @Param("p")Map<String,Object> param);



}
