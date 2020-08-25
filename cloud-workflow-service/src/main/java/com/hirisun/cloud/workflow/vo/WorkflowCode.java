package com.hirisun.cloud.workflow.vo;

import com.hirisun.cloud.common.vo.ResultCode;

public enum WorkflowCode implements ResultCode {
    WORKFLOW_INSTANSE_MISSING(2401,"流程实例未找到!"),
    WORKFLOW_ACTIVITY_MISSING(2402,"未找到对应的流程活动信息!"),
    ADVANCE_CAN_NOT_NULL(2403,"流转信息不能为空!"),
    NODE_ID_CAN_NOT_NULL(2404,"当前环节ID不能为空!"),
    WORKFLOW_ACTIVITY_ERROR(2405,"当前流程数据有误!"),
    WORKFLOW_ACTIVITY_HANDLING(2406,"该任务已经处理，不能重复处理!"),
    WORKFLOW_NODE_HANDLER_MISSING(2407,"流程未配置处理人!"),
    CART_AREA_CAN_NOT_NULL(2407,"地区名为空,请确认订单信息!"),
    WORKFLOW_MISSING(2409,"流程配置未找到!"),
    WORKFLOW_MISSING_START_NODE(2410,"此流程没有定义开始环节,请联系管理员!"),
    WORKFLOW_CREATE_FAIL(2411,"发起流程失败"),
    WORKFLOW_NODE_NOT_NULL(2412,"环节ID不能为空"),
    WORKFLOW_ACTIVITY_NODE_ID_NOT_NULL(2413,"当前环节对应的流程环节为空，请传入流程定义环节信息"),
    WORKFLOW_ACTIVITY_STATUS_ERROR(2414,"该任务已经处理，不能重复处理"),
    WORKFLOW_NODE_NO_HANDLER(2415,"流程未配置处理人"),
    WORKFLOW_NODE_INFO_NULL(2416,"当前环节信息为空,流转失败"),
    HANDLER_NULL(2417,"办理人不能为空."),
    END_ACTIVITY_NULL(2418,"未找到结束环节!")

    ;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String msg;

    WorkflowCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer code() {
        return this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }
}
