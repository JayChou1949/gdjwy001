package com.upd.hwcloud.bean.vo.iaasOrder.biangeng;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 12:01 2019/11/1
 * @ Description：变更前配置信息
 */
@Data
public class BiangengFrontVo implements Serializable{

    @Excel(name = "网络类型",orderNum = "1")
    private String network;

    @Excel(name = "CPU(核)",orderNum = "3")
    private String cpu;

    @Excel(name = "内存(G)",orderNum = "4")
    private String ram;

    @Excel(name = "存储（T）",orderNum = "5")
    private String storageOld;

    @Excel(name = "变更前系统",orderNum = "6")
    private String os;
}
