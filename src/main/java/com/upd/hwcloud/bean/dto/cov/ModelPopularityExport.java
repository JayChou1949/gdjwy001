package com.upd.hwcloud.bean.dto.cov;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author junglefisher
 * @date 2020/5/7 15:28
 */
@Data
public class ModelPopularityExport {
    @Excel(name = "序号",type = 10)
    private Integer num;
    @Excel(name = "模型名称")
    private String name;
    @Excel(name = "热度",type = 10)
    private String count;
}
