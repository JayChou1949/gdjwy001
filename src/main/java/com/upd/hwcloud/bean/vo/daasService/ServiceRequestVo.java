package com.upd.hwcloud.bean.vo.daasService;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upd.hwcloud.common.utils.AreaPoliceCategoryUtils;
import com.upd.hwcloud.common.utils.BigDecimalUtil;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 18:35 2019/11/5
 * @ Description：Daas服务调用
 */
@Data
public class ServiceRequestVo {

    @Excel(name = "序号",orderNum = "1")
    private Integer id;//序号

    @Excel(name = "服务名称",orderNum = "1",width = 60)
    private String serviceName;//服务名称

    @Excel(name = "地市",orderNum = "2",width = 15)
    private String serviceAreaName;//地市

    @Excel(name = "警种",orderNum = "3",width = 15)
    private String servicePoliceCategory;//警种

    @Excel(name = "应用名称",orderNum = "4",width = 60)
    private String appName;//应用名称

    @Excel(name = "地市",orderNum = "5",width = 15)
    private String appAreaName;//地市

    @Excel(name = "警种",orderNum = "6",width = 15)
    private String appPoliceCategory;//警种

    @Excel(name = "被调用总数(次)",orderNum = "7",width = 15,type = 10)
    private Integer totalReqCount;//被调用总数
}
