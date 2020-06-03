package com.upd.hwcloud.service.application.impl;

import com.upd.hwcloud.bean.entity.application.paas.rdb.PaasRdbInfo;
import com.upd.hwcloud.dao.application.PaasRdbInfoMapper;
import com.upd.hwcloud.service.application.IPaasRdbInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 关系型数据数据库信息 服务实现类
 * </p>
 *
 * @author yyc
 * @since 2020-04-19
 */
@Service
public class PaasRdbInfoServiceImpl extends ServiceImpl<PaasRdbInfoMapper, PaasRdbInfo> implements IPaasRdbInfoService {

}
