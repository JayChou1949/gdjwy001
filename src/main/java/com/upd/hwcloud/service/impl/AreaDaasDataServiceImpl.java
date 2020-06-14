package com.upd.hwcloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.dto.AreaDaasData;
import com.upd.hwcloud.bean.entity.ThreePartyInterface;
import com.upd.hwcloud.service.AreaDaasDataService;
import com.upd.hwcloud.service.IThreePartyInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author junglefisher
 * @date 2020/6/14 11:23
 */
@Service
public class AreaDaasDataServiceImpl implements AreaDaasDataService {

    @Autowired
    private IThreePartyInterfaceService threePartyInterfaceService;

    @Override
    public void importDaasData(AreaDaasData areaDaasData) {
        ThreePartyInterface threePartyInterface = new ThreePartyInterface();
        // 插入或更新
        String data = JSON.toJSONString(areaDaasData);
        threePartyInterface.setCreateTime(new Date());
        threePartyInterface.setData(data);
        threePartyInterface.setId(areaDaasData.getAreaNameEn()+"DaasData");
        threePartyInterface.setLabel(areaDaasData.getAreaName()+"数据服务");
        threePartyInterface.insertOrUpdate();
    }

    @Override
    public AreaDaasData getDaasData(String name) {
        ThreePartyInterface one = threePartyInterfaceService.getOne(new QueryWrapper<ThreePartyInterface>().lambda().eq(ThreePartyInterface::getId, name + "DaasData"));
        if (one!=null){
            AreaDaasData areaDaasData = JSON.parseObject(one.getData(), AreaDaasData.class);
            return areaDaasData;
        }
        return null;
    }
}
