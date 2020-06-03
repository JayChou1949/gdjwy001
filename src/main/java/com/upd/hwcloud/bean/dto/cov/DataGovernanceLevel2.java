package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

/**
 * @author junglefisher
 * @date 2020/5/5 21:05
 */
@Data
public class DataGovernanceLevel2 {
    /**
     * 类型
     */
    private String type;
    /**
     * 数量
     */
    private String num;
    /**
     * 总数
     */
    private String sum;
    /**
     * 百分比
     */
    private String percentage;
}
