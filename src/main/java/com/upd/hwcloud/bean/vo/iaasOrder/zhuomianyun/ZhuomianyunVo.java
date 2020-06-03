package com.upd.hwcloud.bean.vo.iaasOrder.zhuomianyun;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 14:36 2019/11/2
 * @ Description：桌面云
 */
@Data
public class ZhuomianyunVo implements Serializable{

    @Excel(name = "序号",orderNum = "1",width = 5,needMerge = true)
    private Integer id;

    @Excel(name = "申请单号",orderNum = "2",width = 15,needMerge = true)
    private String orderNumber;

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

    @Excel(name = "申请日期",orderNum = "13",groupName = "时间",width = 15,needMerge = true)
    private String applicationTimeStart;

    @Excel(name = "完成日期",orderNum = "14",groupName = "时间",width = 15,needMerge = true)
    private String applicationTimeEnd;

    @Excel(name = "用途",orderNum = "15",width = 12,needMerge = true)
    private String useType;

    @Excel(name = "归属",orderNum = "16",width = 12,needMerge = true)
    private String belong;

    @ExcelCollection(name = "申请使用人员信息",orderNum = "19")
    private List<ZhuomianyunSqVo> sqVoList;

    @ExcelCollection(name = "实际发放",orderNum = "20")
    private List<ZhuomianyunImplVo> implVoList;

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
