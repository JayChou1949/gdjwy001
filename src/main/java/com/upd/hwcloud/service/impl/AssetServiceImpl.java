package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.dto.ByMonth;
import com.upd.hwcloud.bean.dto.ByVender;
import com.upd.hwcloud.bean.entity.Asset;
import com.upd.hwcloud.dao.AssetMapper;
import com.upd.hwcloud.service.IAssetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资产 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-11-27
 */
@Service
public class AssetServiceImpl extends ServiceImpl<AssetMapper, Asset> implements IAssetService {

    @Autowired
    AssetMapper assetMapper;

    @Override
    public Page<Asset> getPage(Page<Asset> page, String contractNo, String vender, String startTime, String endTime) {
        return assetMapper.getPage(page,contractNo,vender,startTime,endTime);
    }

    @Override
    public List<ByVender> getByVender() {
        return assetMapper.getByVender();
    }

    @Override
    public List<ByMonth> getByMonth() {
        return assetMapper.getByMonth();
    }
}
