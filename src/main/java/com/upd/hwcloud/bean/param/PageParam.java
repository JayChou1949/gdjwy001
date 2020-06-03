package com.upd.hwcloud.bean.param;

import lombok.Data;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 16:07 2019/8/14
 * @ Description：分页参数
 */
@Data
public class PageParam {

    private long current = 1;//页数

    private long size = 100000;//数据大小

    private String name;//名称

    private String startDate;//开始日期

    private String endDate;//结束日期

    private Integer catalog;//目录 1：DASS 2:PASS



}
