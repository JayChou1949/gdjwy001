package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

/**
 * @author junglefisher
 * @date 2020/5/7 17:03
 */
@Data
public class DataAccessLevel3 {
    /**
     * 序号
     */
    private Integer num;
    /**
     * 单位名称
     */
    private String unitName;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 类型
     */
    private String type;
    /**
     * 总调用
     */
    private Long countAll;
    /**
     * 昨日调用
     */
    private Long yesyerdayCount;
}
