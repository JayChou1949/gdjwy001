package com.hirisun.cloud.order.mapper.paas;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.order.bean.paas.PaasLibraDbWhitelist;

/**
 * Libra+分布式并行数据库白名单 Mapper 接口
 */
@Repository
public interface PaasLibraDbWhitelistMapper extends BaseMapper<PaasLibraDbWhitelist> {

}
