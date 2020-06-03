package com.upd.hwcloud.bean.vo.iaasOrder.txfuzaijunheng;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 12:01 2019/11/1
 * @ Description：变更前配置信息
 */
@Data
public class TxfuzaijunhengSqVo implements Serializable{

    @Excel(name = "后端提供服务的服务器IP",orderNum = "3",width = 15)
    private String serverIp;

    @Excel(name = "后端端口",orderNum = "5")
    private String serverPort;

    @Excel(name = "访问路径",orderNum = "6")
    private String serverUrl;

}
