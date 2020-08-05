package com.hirisun.cloud.model.ncov.vo.daas;

import lombok.Data;

@Data
public class NcovDataVo implements Comparable<NcovDataVo>{
    /**
     * 名称
     */
    private String name;
    /**
     * 数量
     */
    private String count;

    @Override
    public int compareTo(NcovDataVo ncovDataDto) {
        return Integer.valueOf(ncovDataDto.getCount()) - Integer.valueOf(this.count);
    }
}
