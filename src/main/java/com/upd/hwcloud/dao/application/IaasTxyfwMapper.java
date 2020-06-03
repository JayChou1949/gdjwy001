package com.upd.hwcloud.dao.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.application.IaasTxyfw;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.application.IaasTxyfwImpl;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 弹性云服务器申请信息 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-11-30
 */
public interface IaasTxyfwMapper extends BaseMapper<IaasTxyfw> {

    Integer getTotalNum(@Param("appInfoId") String appInfoId);

    Integer getTotalNumOfShoppingCart(@Param("shoppingCartId") String shoppingCartId);

    IPage<IaasTxyfwImpl> getEcsListInWorkbench(IPage<IaasTxyfwImpl> page, @Param("p")Map<String,Object> param);



}
