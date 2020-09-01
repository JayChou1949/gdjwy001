package com.hirisun.cloud.common.enumer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yyc
 * @date 2020/5/14
 */
@Getter
@AllArgsConstructor
public enum ResourceRecoverStatus {

    UN_PROCESSED(0),PROCESSING(1),PROCESSED(2);

    private int code;



}
