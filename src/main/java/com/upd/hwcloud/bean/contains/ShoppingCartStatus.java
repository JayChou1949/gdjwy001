package com.upd.hwcloud.bean.contains;

/**
 * @author yyc
 * @date 2020/4/16
 */
public enum ShoppingCartStatus {

    WAIT_SUBMIT(0),
    DRAFT(1);

    private int code;
    ShoppingCartStatus(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
