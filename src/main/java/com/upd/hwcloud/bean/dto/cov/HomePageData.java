package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

import java.util.List;

/**
 * @author junglefisher
 * @date 2020/5/5 20:22
 */
@Data
public class HomePageData {
    /**
     * 总条数
     */
    private Long totalCount;
    /**
     * 总资源数
     */
    private Integer resourceCount;
    /**
     * 昨日增量
     */
    private Long yesterdayCount;
    /**
     * 疫情服务数
     */
    private Integer serviceCount;
    /**
     * 支撑单位数
     */
    private Integer unitCount;
    /**
     * 总调用数
     */
    private Long allCall;
    /**
     * 昨日调用数
     */
    private Long yesterdayCall;

    /**
     * 更新方式
     */
    private List<DataGovernanceLevel2> updateType;
    /**
     * 更新周期
     */
    private List<DataGovernanceLevel2> updateCycle;

    /**
     *  总览数据
     */
    private List<NcovDataOverviewDto> dataModelingOverview;

    /**
     *  总览数据
     */
    private List<NcovDataOverviewDto> dataSharingOverview;
}
