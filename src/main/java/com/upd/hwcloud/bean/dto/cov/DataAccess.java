package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author junglefisher
 * @date 2020/5/5 14:25
 */
@Data
public class DataAccess {
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
     * 来源公安
     */
    private Integer policeCount;
    /**
     * 来源政府
     */
    private Integer govermentCount;

    /**
     * 数据类型
     */
    private Map<String, Map<String,Long>> typeMap;

    /**
     * 政府部门数据共享情况
     */
    private List<NcovDataLongDto> govData;

    /**
     * 近七天接入总量
     */
    private List<Long> lately7Days;

    /**
     * 近七天重点数据接入总量
     */
    private List<Long> latelyFocus;

    /**
     * 时间轴
     */
    private List<String> time;
}
