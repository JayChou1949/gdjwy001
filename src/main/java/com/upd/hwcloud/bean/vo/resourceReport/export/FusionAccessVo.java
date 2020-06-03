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
public class FusionAccessVo {

    @Excel(name = "民警访问(台)")
    private Integer policeAccess;

    @Excel(name = "开发人员访问(台)")
    private Integer developerAccess;
}
