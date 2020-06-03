package com.upd.hwcloud.bean.dto.cov;

import lombok.Data;

/**
 * @author junglefisher
 * @date 2020/5/5 11:05
 */
@Data
public class NcovDataDto implements Comparable<NcovDataDto>{
    /**
     * 名称
     */
    private String name;
    /**
     * 数量
     */
    private String count;

    @Override
    public int compareTo(NcovDataDto ncovDataDto) {
        return Integer.valueOf(ncovDataDto.getCount()) - Integer.valueOf(this.count);
    }
}
