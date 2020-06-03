package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

/**
 * @author junglefisher
 * @date 2020/5/5 18:14
 */
@Data
public class CallAndNameDto implements Comparable<CallAndNameDto>{
    /**
     * 调用次数
     */
    private Long count;
    /**
     * 名称
     */
    private String name;

    @Override
    public int compareTo(CallAndNameDto callAndNameDto) {
        long sub = this.count - callAndNameDto.getCount();
        if (sub>0){
            return -1;
        }
        return 1;
    }
}
