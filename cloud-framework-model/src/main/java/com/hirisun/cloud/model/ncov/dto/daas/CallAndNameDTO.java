package com.hirisun.cloud.model.ncov.dto.daas;

import lombok.Data;


@Data
public class CallAndNameDTO implements Comparable<CallAndNameDTO>{
    /**
     * 调用次数
     */
    private Long count;
    /**
     * 名称
     */
    private String name;

    @Override
    public int compareTo(CallAndNameDTO callAndNameDto) {
        long sub = this.count - callAndNameDto.getCount();
        if (sub>0){
            return -1;
        }
        return 1;
    }
}
