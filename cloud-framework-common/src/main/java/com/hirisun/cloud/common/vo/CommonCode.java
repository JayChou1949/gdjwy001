package com.hirisun.cloud.common.vo;

/**
 * @ClassName CommonCode
 * @Author zhoufeng
 * @Date 2019/11/14 10:35
 * @Description 公共错误码
 */
public enum CommonCode implements ResultCode{
    SUCCESS(0,"成功"),
    FAIL(1,"操作失败"),
    COMPLETE(2,"操作完成"),
    INVALID_PARAM(1001,"非法参数"),
    JSON_RESOLVE_FAIL(1002,"JSON解析错误"),
    UPDATE_FAIL(1006,"数据更新失败"),
    DELETE_FAIL(1007,"数据删除失败"),
    IMPORT_FAIL(1008,"导入数据失败,请检查导入文件数据"),
    EXPORT_FAIL(1009,"导出数据失败,请检查导数据是否异常"),
    ACTIVITY_ID_NULL(1010,"当前环节ID不能为空"),
    CIRCULATION_INFO_NULL(1011,"流转信息不能为空"),
    PROCESS_LINK_NULL(1012,"当前环节对应的流程环节为空，请传入流程定义环节信息!"),
    LINK_NULL(1013,"当前环节信息为空,流转失败!"),
    FLOW_HANDLER_NULL(1014,"流程未配置处理人!"),
    HANDLER_NULL(1015,"办理人不能为空!"),
    LINK_END_NULL(1016,"未找到结束环节!"),
    REPEATED_PROCESSING(1017,"该任务已经处理，不能重复处理!"),
    FLOW_INSTANCE_NULL(1018,"未找到相关流程实例!"),
    APPLY_CODE_ERROR(1019,"申请表单编码不正确，请联系系统管理员!"),
    PROJECT_NULL(1020,"请选择需提交的项!"),
    SUBMITREQUEST_CONVERT_FAIL(1021,"SubmitRequest Convert Fail"),
    CONVERT_ERROR(1022,"不支持逆向转化"),
    ITEM_NULL(1023,"请选择需提交的项"),
    SERVICE_ID_NULL(1024,"参数服务id必填"),
    SHOPPINGCAR_ID_NULL(1025,"请选择需提交购物车"),
    ORDER_NUMBER_ERROR(1026,"申请单号生成错误,请重试!"),
    ORDER_SPLIT_ERROR(1027,"分单处理异常."),
    DRAFT_ERROR(1028,"草稿不能提交."),
    FLOW_INSTANCE_NULL_ERROR(1029,"流程实例未找到."),
    WORK_ORDERSTATUS_ERROR(1030,"工作台订单状态码错误."),
    IMPORT_ERROR(1031,"导入数据异常."),
    SERVER_ERROR(9999,"系统繁忙，稍后重试");
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String msg;

    CommonCode(Integer code, String msg) {
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
