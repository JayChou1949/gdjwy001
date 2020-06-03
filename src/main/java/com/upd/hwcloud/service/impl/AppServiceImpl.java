package com.upd.hwcloud.service.impl;

import com.upd.hwcloud.bean.entity.App;
import com.upd.hwcloud.dao.AppMapper;
import com.upd.hwcloud.service.IAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 厂商应用 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2019-05-13
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements IAppService {

}
