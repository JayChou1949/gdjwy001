package com.upd.hwcloud.bean.vo.iaasOrder.dashujuZujian;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.upd.hwcloud.bean.vo.iaasOrder.IaasOrderVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 10:58 2019/11/1
 * @ Description：IAAS业务办理工单
 */
@Data
public class DashujuVo extends IaasOrderVo implements Serializable{

    @Excel(name = "组件名称",orderNum = "15")
    private String component;

    @ExcelCollection(name = "申请配置信息",orderNum = "19")
    private List<DashujuSqVo> sqVoList;

    @ExcelCollection(name = "实施配置信息",orderNum = "20")
    private List<DashujuImplVo> implVoList;

}
