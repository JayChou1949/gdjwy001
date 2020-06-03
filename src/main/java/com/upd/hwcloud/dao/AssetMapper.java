package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.dto.ByMonth;
import com.upd.hwcloud.bean.dto.ByVender;
import com.upd.hwcloud.bean.entity.Asset;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资产 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2018-11-27
 */
public interface AssetMapper extends BaseMapper<Asset> {

    Page<Asset> getPage(Page<Asset> page
            , @Param("contractNo") String contractNo
            ,@Param("vender") String vender
            ,@Param("startTime") String startTime
            ,@Param("endTime") String endTime);

    List<ByVender> getByVender();

    List<ByMonth> getByMonth();
}
