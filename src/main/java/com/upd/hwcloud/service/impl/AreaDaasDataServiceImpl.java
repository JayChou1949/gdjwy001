package com.upd.hwcloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.upd.hwcloud.bean.dto.AreaDaasData;
import com.upd.hwcloud.bean.entity.ThreePartyInterface;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.AreaDaasDataService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author junglefisher
 * @date 2020/6/14 11:23
 */
@Service
public class AreaDaasDataServiceImpl implements AreaDaasDataService {

    @Override
    public void importDaasData(AreaDaasData areaDaasData) {
        ThreePartyInterface threePartyInterface = new ThreePartyInterface();
        // 插入或更新
        String data = JSON.toJSONString(R.ok(areaDaasData));
        threePartyInterface.setCreateTime(new Date());
        threePartyInterface.setData(data);
        threePartyInterface.setId(areaDaasData.getAreaNameEn()+"DaasOverview");
        threePartyInterface.setLabel(areaDaasData.getAreaName()+"DaaS总览");
        threePartyInterface.insertOrUpdate();
    }
}
