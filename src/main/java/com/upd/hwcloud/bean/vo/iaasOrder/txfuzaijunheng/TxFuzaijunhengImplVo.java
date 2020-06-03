package com.upd.hwcloud.bean.vo.iaasOrder.txfuzaijunheng;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 12:01 2019/11/1
 * @ Description：实施配置信息
 */
@Data
public class TxFuzaijunhengImplVo implements Serializable{

    @Excel(name = "负载均衡IP",orderNum = "3")
    private String ip;


}
