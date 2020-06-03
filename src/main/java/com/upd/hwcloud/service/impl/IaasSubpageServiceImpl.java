package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.dao.IaasSubpageMapper;
import com.upd.hwcloud.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * iaas二级页面配置 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
@Service
public class IaasSubpageServiceImpl extends BaseSubpageServiceImpl<IaasSubpageMapper, IaasSubpage> implements IIaasSubpageService {

}
