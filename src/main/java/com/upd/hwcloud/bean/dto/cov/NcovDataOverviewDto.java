package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

/**
 * @author junglefisher
 * @date 2020/5/5 11:02
 */
@Data
public class NcovDataOverviewDto {
    /**
     * 名称
     */
    private String name;
    /**
     * 数量
     */
    private String count;
    /**
     * 单位
     */
    private String unit;
}
