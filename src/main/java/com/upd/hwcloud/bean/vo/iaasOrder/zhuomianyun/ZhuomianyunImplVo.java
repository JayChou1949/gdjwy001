package com.upd.hwcloud.bean.vo.iaasOrder.zhuomianyun;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 12:01 2019/11/1
 * @ Description：申请使用人员信息
 */
@Data
public class ZhuomianyunImplVo implements Serializable{

    @Excel(name = "姓名",orderNum = "3")
    private String name;

    @Excel(name = "身份证号",orderNum = "4",width = 19)
    private String idcard;

    @Excel(name = "性别",orderNum = "5",replace = { "男_1", "女_2" })
    private String sex;

    @Excel(name = "联系电话",orderNum = "6",width = 12)
    private String phone;

    @Excel(name = "身份",orderNum = "7",replace = { "民警_1", "开发人员_2" })
    private String userType;

    @Excel(name = "归属单位",orderNum = "8",width = 20)
    private String company;

    @Excel(name = "申请单位责任人姓名",orderNum = "9")
    private String jsPrincipal;

    @Excel(name = "申请单位责任人电话",orderNum = "10",width = 12)
    private String jsPrincipalPhone;

    @Excel(name = "使用单位责任人姓名",orderNum = "11")
    private String cjPrincipal;

    @Excel(name = "使用单位责任人电话",orderNum = "12",width = 12)
    private String cjPrincipalPhone;

    @Excel(name = "账户名称",orderNum = "13",width = 15)
    private String account;

    @Excel(name = "桌面云IP",orderNum = "14",width = 15)
    private String yzyIp;
}
