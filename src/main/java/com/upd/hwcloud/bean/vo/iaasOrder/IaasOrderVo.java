package com.upd.hwcloud.bean.vo.iaasOrder;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.upd.hwcloud.common.utils.DateUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 10:58 2019/11/1
 * @ Description：IAAS业务办理工单
 */
@Data
public class IaasOrderVo implements Serializable{

    @Excel(name = "序号",orderNum = "1",width = 5,needMerge = true)
    private Integer id;

    @Excel(name = "申请单号",orderNum = "2",width = 15,needMerge = true)
    private String orderNumber;

    @Excel(name = "项目名称",orderNum = "3",width = 15,needMerge = true)
    private String projectName;

    @Excel(name = "应用名称",orderNum = "4",width = 18,needMerge = true)
    private String appName;

    @Excel(name = "所属地区",orderNum = "5",width = 9,needMerge = true)
    private String areaName;

    @Excel(name = "所属警种",orderNum = "6",width = 9,needMerge = true)
    private String policeCategory;

    @Excel(name = "申请单位",orderNum = "7",width = 20,needMerge = true)
    private String creatorUnit;

    @Excel(name = "申请人",orderNum = "8",width = 8,needMerge = true)
    private String creatorName;

    @Excel(name = "联系电话",orderNum = "9",width = 12,needMerge = true)
    private String creatorPhone;

    @Excel(name = "承建单位",orderNum = "10",width = 20,needMerge = true)
    private String cjUnit;

    @Excel(name = "项目负责人",orderNum = "11",width = 8,needMerge = true)
    private String cjPrincipal;

    @Excel(name = "联系电话",orderNum = "12",width = 12,needMerge = true)
    private String cjPrincipalPhone;

    @Excel(name = "申请日期",orderNum = "13",groupName = "时间",width = 15,needMerge = true)
    private String applicationTimeStart;

    @Excel(name = "完成日期",orderNum = "14",groupName = "时间",width = 15,needMerge = true)
    private String applicationTimeEnd;

    public String getApplicationTimeStart() {
        if (!StringUtils.isEmpty(applicationTimeStart)){
            return applicationTimeStart.substring(0,10);
        }
        return applicationTimeStart;
    }

    public String getApplicationTimeEnd() {
        if (!StringUtils.isEmpty(applicationTimeEnd)){
            return applicationTimeEnd.substring(0,10);
        }
        return applicationTimeEnd;
    }
}
