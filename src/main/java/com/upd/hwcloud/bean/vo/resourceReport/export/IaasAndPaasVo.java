package com.upd.hwcloud.bean.vo.resourceReport.export;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yyc
 * @date 2020/5/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IaasAndPaasVo {

    @Excel(name = "资源名称")
    private String resourceName;

    @Excel(name = "CPU (核)",type = 0)
    private Double cpu;

    @Excel(name = "内存(GB)",type = 0)
    private Double memory;

    @Excel(name = "存储(GB)",type = 0)
    private Double disk;
}
