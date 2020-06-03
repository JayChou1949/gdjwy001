package com.upd.hwcloud.service.application.impl;

import com.upd.hwcloud.bean.entity.application.paas.rdb.PaasRdbAcc;
import com.upd.hwcloud.dao.application.PaasRdbAccMapper;
import com.upd.hwcloud.service.application.IPaasRdbAccService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 关系型数据库账号 服务实现类
 * </p>
 *
 * @author yyc
 * @since 2020-04-19
 */
@Service
public class PaasRdbAccServiceImpl extends ServiceImpl<PaasRdbAccMapper, PaasRdbAcc> implements IPaasRdbAccService {

}
