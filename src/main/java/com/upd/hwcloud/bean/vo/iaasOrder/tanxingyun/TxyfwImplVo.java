package com.upd.hwcloud.bean.vo.iaasOrder.tanxingyun;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 12:01 2019/11/1
 * @ Description：操作时间
 */
@Data
public class TxyfwImplVo implements Serializable{

    @Excel(name = "CPU(核)",orderNum = "3")
    private String cpu;

    @Excel(name = "内存(G)",orderNum = "4")
    private String ram;

    @Excel(name = "存储（G）",orderNum = "7")
    private String storage;

    @Excel(name = "操作系统",orderNum = "8")
    private String os;

    @Excel(name = "IP",orderNum = "9")
    private String serverIp1;
}
