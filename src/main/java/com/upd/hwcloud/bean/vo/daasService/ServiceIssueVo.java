package com.upd.hwcloud.bean.vo.daasService;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upd.hwcloud.common.utils.AreaPoliceCategoryUtils;
import com.upd.hwcloud.common.utils.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 18:35 2019/11/5
 * @ Description：Daas服务发布
 */
@Data
public class ServiceIssueVo {

    @Excel(name = "序号",orderNum = "1")
    private Integer id;//序号

    @Excel(name = "服务名称",orderNum = "2",width = 60)
    private String name;//服务名称

    @Excel(name = "地市",orderNum = "3",width = 15)
    private String areaName;//地市

    @Excel(name = "警种",orderNum = "4",width = 15)
    private String policeCategory;//警种

    @Excel(name = "发布时间",orderNum = "5",width = 25)
    private String createTime;//发布时间

}
