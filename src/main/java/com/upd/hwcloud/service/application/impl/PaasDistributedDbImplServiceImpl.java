package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.application.PaasDistributedDbImpl;
import com.upd.hwcloud.dao.application.PaasDistributedDbImplMapper;
import com.upd.hwcloud.service.application.IPaasDistributedDbImplService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yyc
 * @since 2020-05-11
 */
@Service
public class PaasDistributedDbImplServiceImpl extends ServiceImpl<PaasDistributedDbImplMapper, PaasDistributedDbImpl> implements IPaasDistributedDbImplService {

}
