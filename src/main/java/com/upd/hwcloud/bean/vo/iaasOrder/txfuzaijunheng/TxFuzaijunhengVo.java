package com.upd.hwcloud.bean.vo.iaasOrder.txfuzaijunheng;

import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.upd.hwcloud.bean.vo.iaasOrder.IaasOrderVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 10:58 2019/11/1
 * @ Description：文件存储
 */
@Data
public class TxFuzaijunhengVo extends IaasOrderVo implements Serializable{

    @ExcelCollection(name = "申请信息",orderNum = "19")
    private List<TxfuzaijunhengSqVo> sqVoList;

    @ExcelCollection(name = "反馈信息",orderNum = "23")
    private List<TxFuzaijunhengImplVo> implVoList;

}
