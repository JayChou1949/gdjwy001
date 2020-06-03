package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.PaasDistributedDbInfo;
import com.upd.hwcloud.dao.application.PaasDistributedDbInfoMapper;
import com.upd.hwcloud.service.application.IPaasDistributedDbInfoService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * Libra+分布式并行数据库申请表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2020-03-20
 */
@Service
public class PaasDistributedDbInfoServiceImpl extends ServiceImpl<PaasDistributedDbInfoMapper, PaasDistributedDbInfo> implements IPaasDistributedDbInfoService {

}
