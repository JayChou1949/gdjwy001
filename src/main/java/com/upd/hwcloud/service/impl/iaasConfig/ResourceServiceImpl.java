package com.upd.hwcloud.service.impl.iaasConfig;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.upd.hwcloud.bean.entity.iaasConfig.Resource;
import com.upd.hwcloud.dao.iaasConfig.ResourceMapper;
import com.upd.hwcloud.service.iaasConfig.IResourceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源分配情况 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-11-17
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

}
