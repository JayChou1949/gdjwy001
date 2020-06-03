package com.upd.hwcloud.bean.vo.iaasOrder.duixingCC;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 12:01 2019/11/1
 * @ Description：实施配置信息
 */
@Data
public class DuixiangCCImplVo implements Serializable{

    @Excel(name = "应用组件",orderNum = "3")
    private String component;

    @Excel(name = "申请容量（T）",orderNum = "5")
    private String storage;

    @Excel(name = "桶名称",orderNum = "6")
    private String bucketName;


}
