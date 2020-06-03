package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author junglefisher
 * @date 2020/5/5 17:33
 */
@Data
public class DataService {
    /**
     * 疫情服务数
     */
    private Integer serviceCount;
    /**
     * 支撑公安部门数
     */
    private Integer policeCount;
    /**
     * 支撑政府单位数
     */
    private Integer govCount;
    /**
     * 公安部门调用数
     */
    private Long policeCall;
    /**
     * 政府单位调用数
     */
    private Long govCall;
    /**
     * 昨日调用数
     */
    private Long yesterdayCall;
    /**
     * 各省直单位调用数
     */
    private List<NcovDataLongDto> govDirect;
    /**
     * 各地市调用数
     */
    private List<NcovDataLongDto> city;
    /**
     * 各公安警种调用数
     */
    private List<CallAndNameDto> police;
    /**
     * 政府部门近七天
     */
    private List<CallAndTimeDto> govLately;
    /**
     * 公安部门近七天
     */
    private List<CallAndTimeDto> policeLately;
}
