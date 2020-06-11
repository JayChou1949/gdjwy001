package com.upd.hwcloud.service.application.impl;

import com.upd.hwcloud.bean.entity.application.paas.libra.PaasLibraDbWhitelist;
import com.upd.hwcloud.dao.application.PaasLibraDbWhitelistMapper;
import com.upd.hwcloud.service.application.IPaasLibraDbWhitelistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Libra+分布式并行数据库白名单 服务实现类
 * </p>
 *
 * @author junglefisher
 * @since 2020-05-09
 */
@Service
public class PaasLibraDbWhitelistServiceImpl extends ServiceImpl<PaasLibraDbWhitelistMapper, PaasLibraDbWhitelist> implements IPaasLibraDbWhitelistService {

}
