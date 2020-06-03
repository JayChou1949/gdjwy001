package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.dto.ByMonth;
import com.upd.hwcloud.bean.dto.ByVender;
import com.upd.hwcloud.bean.entity.Asset;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资产 服务类
 * </p>
 *
 * @author huru
 * @since 2018-11-27
 */
public interface IAssetService extends IService<Asset> {

    Page<Asset> getPage(Page<Asset> page, String contractNo, String vender, String startTime, String endTime);

    List<ByVender> getByVender();

    List<ByMonth> getByMonth();
}
