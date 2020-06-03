package com.upd.hwcloud.bean.vo.iaasOrder.wenjianCC;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 12:01 2019/11/1
 * @ Description：实施配置信息
 */
@Data
public class WenjianCCImplVo implements Serializable{

    @Excel(name = "域名",orderNum = "3")
    private String domainName;

    @Excel(name = "DNS",orderNum = "5")
    private String dns;

    @Excel(name = "路径",orderNum = "6")
    private String path;

    @Excel(name = "存储（T）",orderNum = "7")
    private String storage;


}
