package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

import java.util.List;

/**
 * @author junglefisher
 * @date 2020/5/5 10:51
 */
@Data
public class DataSharing {
    /**
     *  总览数据
     */
    private List<NcovDataOverviewDto> dataSharingOverview;
    /**
     * 单位下载排名
     */
    private List<NcovDataDto> unitDownload;
    /**
     * 高频使用资源排名
     */
    private List<NcovDataDto> highFrequencyUse;
}
