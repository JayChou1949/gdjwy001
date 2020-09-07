package com.hirisun.cloud.order.mapper.epidemic;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.order.bean.epidemic.EpidemicApplication;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * SaaS资源申请原始信息表 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-03
 */
public interface EpidemicApplicationMapper extends BaseMapper<EpidemicApplication> {
    IPage<EpidemicApplication> getFlowPage(IPage<EpidemicApplication> page, @Param("p") Map<String, Object> param);
    //查服务用
    IPage<EpidemicApplication> getFlowPageWithServiceName(IPage<EpidemicApplication> page, @Param("p") Map<String, Object> param);
}
