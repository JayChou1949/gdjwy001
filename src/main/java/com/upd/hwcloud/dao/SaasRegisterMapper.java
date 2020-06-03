package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.SaasRegister;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.vo.workbench.ResourceOverviewVO;
import com.upd.hwcloud.bean.vo.workbench.SaasStatisticsVO;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * SAAS服务注册表 Mapper 接口
 * </p>
 *
 * @author zwb
 * @since 2019-05-27
 */
public interface SaasRegisterMapper extends RegisterMapper<SaasRegister> {

    List<SaasRegister> getListExport(@Param("p") Map<String, Object> param);

    IPage<SaasStatisticsVO> saasRegisterStatisticsPage(IPage<SaasStatisticsVO> page,@Param("p") Map<String ,Object> param);

    HashMap<String,Long> saasRegisterStatisticsOverview(@Param("p") Map<String ,Object> param);

    IPage<SaasRegister> getWorkbenchApplyPage(IPage<SaasRegister> page, @Param("p") Map<String, Object> param);


    IPage<ResourceOverviewVO> getWorkbenchResourcePage(IPage<ResourceOverviewVO> page ,@Param("p") Map<String,Object> param);

    HashMap<String,Long> getWorkbenchResourceOverview(@Param("p") Map<String,Object> param);


    int getReviewCount(@Param("user") User user, @Param("p") Map<String,Object> param);

    int getImplCount(@Param("user") User user,@Param("p") Map<String,Object> param);

    int getRejectCount(@Param("user") User user,@Param("p") Map<String,Object> param);

    int getUseCount(@Param("user") User user,@Param("p") Map<String,Object> param);

}
