package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

/**
 * @author junglefisher
 * @date 2020/5/5 18:17
 */
@Data
public class CallAndTimeDto {
    /**
     * 调用次数
     */
    private Long count;
    /**
     * 日期
     */
    private String time;
}
