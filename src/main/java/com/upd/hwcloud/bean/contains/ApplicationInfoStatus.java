package com.upd.hwcloud.bean.contains;

import com.upd.hwcloud.common.exception.BaseException;
import org.apache.commons.lang.StringUtils;

public enum ApplicationInfoStatus {

    /**
     *  -1:购物车删除
     *  0:购物车
     *  1:科信待审核 --> 待审核
     *  2:待实施 --> 待实施
     *  3:使用中 --> 使用中
     *  4:已删除 --> 已删除
     *  5:科信审核驳回 --> 审核驳回
     *  6:实施驳回 --> 实施驳回
     *  7:部门内待审核 --> 待审核 或者被回收负责人审批待审核
     *  8:部门内驳回 --> 审核驳回
     *  101:加办(属于科信待审核的一种类型) --> 待审核
     *  102:转发(属于科信待审核的一种类型,当部门内审核时发生转发,不改变其状态,直接置换审核人) --> 待审核
     *  103：短信重发(被回收资源负责人超过2天未处理工单，进行短信重发)-->待审核
     */

    SHOPPING_CART_DEL("-1", "购物车删除"), SHOPPING_CART("0", "购物车"),

    REVIEW("1", "科信待审核"),

    IMPL("2", "待实施"), USE("3", "使用中"), DELETE("4", "已删除"),

    REVIEW_REJECT("5", "科信审核驳回"), IMPL_REJECT("6", "实施驳回"),

    INNER_REVIEW("7", "部门内待审核"), INNER_REJECT("8", "部门内驳回"),

    ADD("101", "加办"), FORWARD("102", "转发"),

    RESENT("103", "短信重发");

    private final String code;
    private final String des;

    ApplicationInfoStatus(String code, String des) {
        this.code = code;
        this.des = des;
    }

    public String getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }

    public static ApplicationInfoStatus codeOf(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new BaseException("申请信息状态码错误");
        }
        ApplicationInfoStatus[] values = ApplicationInfoStatus.values();
        for (ApplicationInfoStatus value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new BaseException("申请信息状态码错误");
    }

}
