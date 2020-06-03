package com.upd.hwcloud.bean.dto.cov;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author junglefisher
 * @date 2020/5/7 14:00
 */
@Data
public class PreventionSituation {
    /**
     * 省直/地市
     */
    @Excel(name = "省直/地市")
    private String type;
    /**
     * 部门名称
     */
    @Excel(name = "部门名称")
    private String unitName;
    /**
     * 服务名称
     */
    @Excel(name = "服务名称")
    private String serviceName;
    /**
     * 调用总次数
     */
    @Excel(name = "调用总次数", type = 10)
    private String callAll;
    /**
     * 昨日调用次数
     */
    @Excel(name = "昨日调用次数", type = 10)
    private String yesterdayCall;
}
