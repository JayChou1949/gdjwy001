package com.upd.hwcloud.bean.vo.iaasOrder.dashujuZujian;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 12:01 2019/11/1
 * @ Description：实施配置信息
 */
@Data
public class DashujuImplVo implements Serializable{

    @Excel(name = "CPU(核)",orderNum = "3")
    private String cpu;

    @Excel(name = "内存(G)",orderNum = "4")
    private String ram;

    @Excel(name = "存储（T）",orderNum = "5")
    private String storage;
}
