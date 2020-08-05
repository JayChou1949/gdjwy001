package com.hirisun.cloud.model.ncov.vo.daas;

import lombok.Data;

@Data
public class NcovDataLongVo implements Comparable<NcovDataLongVo>{
    /**
     * 名称
     */
    private String name;
    /**
     * 数量
     */
    private Long count;

    @Override
    public int compareTo(NcovDataLongVo ncovDataLongVo) {
        long sub = this.count - ncovDataLongVo.getCount();
        if (sub>0){
            return -1;
        }
        return 1;
    }

	public NcovDataLongVo(String name, Long count) {
		super();
		this.name = name;
		this.count = count;
	}
    
    
}
