package com.upd.hwcloud.bean.dto.cov;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author junglefisher
 * @date 2020/5/7 15:14
 */
@Data
public class HighFrequencyUseExport {
    @Excel(name = "序号",type = 10)
    private Integer num;
    @Excel(name = "资源名称")
    private String name;
    @Excel(name = "使用次数",type = 10)
    private String count;
}
