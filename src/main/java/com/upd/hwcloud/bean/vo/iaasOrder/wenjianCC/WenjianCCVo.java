package com.upd.hwcloud.bean.vo.iaasOrder.wenjianCC;

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
public class WenjianCCVo extends IaasOrderVo implements Serializable{

    @ExcelCollection(name = "申请配置信息",orderNum = "19")
    private List<WenjianCCSqVo> sqVoList;

    @ExcelCollection(name = "实施配置信息",orderNum = "23")
    private List<WenjianCCImplVo> implVoList;

    public List<WenjianCCSqVo> getSqVoList() {
        return sqVoList;
    }

    public void setSqVoList(List<WenjianCCSqVo> sqVoList) {
        this.sqVoList = sqVoList;
    }

    public List<WenjianCCImplVo> getImplVoList() {
        return implVoList;
    }

    public void setImplVoList(List<WenjianCCImplVo> implVoList) {
        this.implVoList = implVoList;
    }
}
