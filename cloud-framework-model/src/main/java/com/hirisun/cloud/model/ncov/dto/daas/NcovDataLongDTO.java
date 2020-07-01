package com.hirisun.cloud.model.ncov.dto.daas;

import lombok.Data;

/**
 * @author junglefisher
 * @date 2020/5/5 23:55
 */
@Data
public class NcovDataLongDTO implements Comparable<NcovDataLongDTO>{
    /**
     * 名称
     */
    private String name;
    /**
     * 数量
     */
    private Long count;

    @Override
    public int compareTo(NcovDataLongDTO ncovDataLongDto) {
        long sub = this.count - ncovDataLongDto.getCount();
        if (sub>0){
            return -1;
        }
        return 1;
    }
}
