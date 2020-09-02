package com.hirisun.cloud.order.service.servicePublish.impl;

import com.hirisun.cloud.order.bean.servicePublish.ServiceStatistics;
import com.hirisun.cloud.order.mapper.servicePublish.ServiceStatisticsMapper;
import com.hirisun.cloud.order.service.servicePublish.ServiceStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APIG服务调用信息表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@Service
public class ServiceStatisticsServiceImpl extends ServiceImpl<ServiceStatisticsMapper, ServiceStatistics> implements ServiceStatisticsService {

}
