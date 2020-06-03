package com.upd.hwcloud.bean.contains;

import com.upd.hwcloud.common.exception.BaseException;

import org.apache.commons.lang.StringUtils;

/**
 * @author wuc
 * @date 2019/12/10
 */
public enum WorkbenchApplyStatus {

    REVIEW_STATUS("0","待审核"),
    IMPL_STATUS("1","待实施"),
    REJECT_STATUS("2","已驳回"),
    USE_STATUS("3","使用中");


    private final String code;
    private final String name;

    WorkbenchApplyStatus(String code,String name){
        this.code = code;
        this.name = name;

    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static WorkbenchApplyStatus codeOf(String code) {
        if (code==null) {
            throw new BaseException("工作台订单状态码错误");
        }
        WorkbenchApplyStatus[] values = WorkbenchApplyStatus.values();
        for (WorkbenchApplyStatus value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new BaseException("工作台订单状态码错误");
    }
}
