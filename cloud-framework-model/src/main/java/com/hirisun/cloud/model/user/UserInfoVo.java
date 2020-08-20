package com.hirisun.cloud.model.user;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class UserInfoVo {

	@Excel(name = "序号",orderNum = "1")
    private Integer id;

    @Excel(name = "姓名",orderNum = "2",width = 20)
    private String creatorName;

    @Excel(name = "已有权限应用数",orderNum = "3",width = 8)
    private Integer total;

    @Excel(name = "所属单位",orderNum = "4",width = 100)
    private String orgName;

    @Excel(name = "身份证号",orderNum = "5",width = 18)
    private String creator;
}
