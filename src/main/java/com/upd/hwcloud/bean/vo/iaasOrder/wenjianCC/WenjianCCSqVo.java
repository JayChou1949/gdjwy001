package com.upd.hwcloud.bean.vo.iaasOrder.wenjianCC;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 12:01 2019/11/1
 * @ Description：变更前配置信息
 */
@Data
public class WenjianCCSqVo implements Serializable{

    @Excel(name = "需要文件存储资源的服务器IP",orderNum = "3",width = 15)
    private String ip;

    @Excel(name = "存储（T）",orderNum = "5")
    private String capacity;
}
