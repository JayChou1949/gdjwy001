package com.upd.hwcloud.bean.vo.iaasOrder.tanxingyun;

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
public class TanxingyunVo extends IaasOrderVo implements Serializable{

    @ExcelCollection(name = "申请配置信息",orderNum = "15")
    private List<TxyfwSqVo> txyfwSqVoList;

    @ExcelCollection(name = "实施配置信息",orderNum = "16")
    private List<TxyfwImplVo> txyfwImplVoList;

}
