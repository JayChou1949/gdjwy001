package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.EpidemicApplication;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.SaasApplicationMerge;
import com.upd.hwcloud.bean.entity.ServicePublish;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 疫情应用申请信息表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2020-02-27
 */
public interface EpidemicApplicationMapper extends BaseMapper<EpidemicApplication> {

    EpidemicApplication getByActId(@Param("activityId") String activityId);

    IPage<EpidemicApplication> getFlowPage(IPage<EpidemicApplication> page, @Param("p") Map<String, Object> param);

    //查服务用
    IPage<EpidemicApplication> getFlowPageWithServiceName(IPage<EpidemicApplication> page, @Param("p") Map<String, Object> param);
}
