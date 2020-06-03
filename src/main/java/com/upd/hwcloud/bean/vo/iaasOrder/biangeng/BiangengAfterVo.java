package com.upd.hwcloud.bean.vo.iaasOrder.biangeng;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 12:01 2019/11/1
 * @ Description：变更后配置信息
 */
@Data
public class BiangengAfterVo implements Serializable{

    @Excel(name = "CPU(核)",orderNum = "3")
    private String cpu;

    @Excel(name = "内存(G)",orderNum = "4")
    private String ram;

    @Excel(name = "存储（T）",orderNum = "5")
    private String storageNew;

    @Excel(name = "变更后IP",orderNum = "6")
    private String serverIp2;

    @Excel(name = "变更后系统",orderNum = "7")
    private String osNew;

    @Excel(name = "变更后说明",orderNum = "8")
    private String explanation;
}
