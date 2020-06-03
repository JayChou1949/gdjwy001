package com.upd.hwcloud.service.dm.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.dm.CloudBusiness;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.dao.dm.CloudBusinessMapper;
import com.upd.hwcloud.service.dm.ICloudBusinessService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yyc
 * @since 2019-08-13
 */
@Service
public class CloudBusinessServiceImpl extends ServiceImpl<CloudBusinessMapper, CloudBusiness> implements ICloudBusinessService {

    @Autowired
    private CloudBusinessMapper cloudBusinessMapper;
    
    @Override
    public R usage(String name) {
        Integer timeStamp = cloudBusinessMapper.latest(name);
        CloudBusiness business = cloudBusinessMapper.selectOne(new QueryWrapper<CloudBusiness>().lambda().eq(CloudBusiness::getName,name).eq(CloudBusiness::getTimeStamp,timeStamp));
        if(business != null){
            /*String areaName = CloudVmServiceImpl.areaMapping.get(business.getName());
            if (areaName != null) {
                business.setName(areaName);
            }*/
            return R.ok(business);
        }
        return R.error("该数据不存在:"+name);
    }

    @Override
    public Page<CloudBusiness> getPage(Page<CloudBusiness> page, String name, String type, String orderType, String cloud) {
        return cloudBusinessMapper.page(page,name,type,orderType,cloud);
    }
}
