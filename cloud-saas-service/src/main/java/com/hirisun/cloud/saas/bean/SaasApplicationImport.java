package com.hirisun.cloud.saas.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ Author：xqp
 * @ Description：应用权限回收申请导入实体
 * @ Date：Created in 17:40 2020/7/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaasApplicationImport {

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String creatorName;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String creator;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String orgName;
}
