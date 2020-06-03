package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author junglefisher
 * @date 2020/5/5 13:14
 */
@Data
public class DataGovernance {
    /**
     * 采集单位名称
     */
    private Set<String> names;
    /**
     * 数据汇总
     */
    private List<NcovDataDto> dataSummary;
    /**
     * 更新方式
     */
    private List<DataGovernanceLevel2> updateType;
    /**
     * 更新周期
     */
    private List<DataGovernanceLevel2> updateCycle;
}
