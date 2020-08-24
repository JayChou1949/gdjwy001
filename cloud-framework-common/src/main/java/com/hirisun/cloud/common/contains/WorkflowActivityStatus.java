package com.hirisun.cloud.common.contains;

import com.hirisun.cloud.common.exception.ExceptionCast;
import com.hirisun.cloud.common.vo.CommonCode;
import org.apache.commons.lang.StringUtils;

public enum WorkflowActivityStatus {
    WAITING(0, "待办"),
    SUBMIT(1, "已提交"),
    REJECT(2, "已回退"),
    AUDIT(3, "已呈批"),
    PREEMPT(4, "已抢占"),
    TERMINAT(5, "已终止"),
    FORWARD(6, "已转发");

    private final Integer code;
    private final String name;

    WorkflowActivityStatus(Integer code, String name) {
        this.code = code;
        this.name  = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String valueOf(Long status) {
        if (status == null) {
            return "";
        } else {
            for (WorkflowNodeAbilityType s : WorkflowNodeAbilityType.values()) {
                if (s.getCode().equals(status)) {
                    return s.getName();
                }
            }
            return "";
        }
    }

}
