package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

import java.util.List;

/**
 * @author junglefisher
 * @date 2020/5/5 11:51
 */
@Data
public class DataModeling {
    /**
     *  总览数据
     */
    private List<NcovDataOverviewDto> dataModelingOverview;
    /**
     * 单位建模排名
     */
    private List<NcovDataDto> unitModeling;
    /**
     * 公共模型建设单位排名
     */
    private List<NcovDataDto> publicModelConstructionUnit;
    /**
     * 模型热度排名
     */
    private List<NcovDataDto> modelPopularity;
}
