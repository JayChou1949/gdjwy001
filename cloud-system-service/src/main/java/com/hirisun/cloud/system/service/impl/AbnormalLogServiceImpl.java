package com.hirisun.cloud.system.service.impl;

import com.hirisun.cloud.system.bean.AbnormalLog;
import com.hirisun.cloud.system.mapper.AbnormalLogMapper;
import com.hirisun.cloud.system.service.AbnormalLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 调用三方接口异常记录表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-04
 */
@Service
public class AbnormalLogServiceImpl extends ServiceImpl<AbnormalLogMapper, AbnormalLog> implements AbnormalLogService {

}
