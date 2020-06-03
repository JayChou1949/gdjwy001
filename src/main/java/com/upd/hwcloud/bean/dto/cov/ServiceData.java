package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

/**
 * @author junglefisher
 * @date 2020/5/7 10:53
 */
@Data
public class ServiceData {
    /**
     * 服务名称
     */
    private String name;
    /**
     * 被调用数
     */
    private Long callAll;
    /**
     * 被订阅数
     */
    private Long orderCount;
    /**
     * 近七天调用数
     */
    private Long lately7Call;
    /**
     * 昨日调用数
     */
    private Long yesterdayCall;
}
