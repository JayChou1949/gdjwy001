package com.upd.hwcloud.bean.vo.workbench;

import lombok.Data;

import java.util.List;

@Data
public class PublicStatistics {

    private Integer totalServiceNum;

    private Integer reqCount;

    private String  resourceType;

    private List<ServiceStatisticsVO> serviceStatisticsVOList;

}
