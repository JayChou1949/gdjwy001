package com.upd.hwcloud.service.dm.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.dm.CloudResourceLevel1;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.dao.dm.CloudResourceLevel1Mapper;
import com.upd.hwcloud.service.dm.ICloudResourceLevel1Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yyc
 * @since 2019-08-13
 */
@Service
public class CloudResourceLevel1ServiceImpl extends ServiceImpl<CloudResourceLevel1Mapper, CloudResourceLevel1> implements ICloudResourceLevel1Service {

    @Autowired
    private CloudResourceLevel1Mapper cloudResourceLevel1Mapper;
    
    @Override
    public R overview() {
        CloudResourceLevel1 oneCloud = cloudResourceLevel1Mapper.selectOne(new QueryWrapper<CloudResourceLevel1>().lambda()
                .eq(CloudResourceLevel1::getName,"一片云"));
        if(oneCloud != null){
            return R.ok(oneCloud);
        }
        return R.error("获取一片云资源概览失败");

    }

    
    @Override
    public List<CloudResourceLevel1> getAll() {
        return cloudResourceLevel1Mapper.selectList(new QueryWrapper<>());
    }
}
