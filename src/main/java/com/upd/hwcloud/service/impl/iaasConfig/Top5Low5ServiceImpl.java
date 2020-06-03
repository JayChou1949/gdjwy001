package com.upd.hwcloud.service.impl.iaasConfig;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.upd.hwcloud.bean.entity.iaasConfig.Top5Low5;
import com.upd.hwcloud.dao.iaasConfig.Top5Low5Mapper;
import com.upd.hwcloud.service.iaasConfig.ITop5Low5Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 全网 警种 地区 top5 low5 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-11-17
 */
@Service
public class Top5Low5ServiceImpl extends ServiceImpl<Top5Low5Mapper, Top5Low5> implements ITop5Low5Service {

    @Autowired
    Top5Low5Mapper top5Low5Mapper;

    @Override
    public List<Top5Low5> getByType(String type, String column) {
        return top5Low5Mapper.getByType(type,column);
    }
}
