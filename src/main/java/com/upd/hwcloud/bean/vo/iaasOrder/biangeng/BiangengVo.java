package com.upd.hwcloud.bean.vo.iaasOrder.biangeng;

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
public class BiangengVo extends IaasOrderVo implements Serializable{

    @ExcelCollection(name = "变更前配置信息",orderNum = "15")
    private List<BiangengFrontVo> frontVoList;

    @ExcelCollection(name = "变更后配置信息",orderNum = "16")
    private List<BiangengAfterVo> afterVoList;

}
