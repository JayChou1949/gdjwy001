package com.upd.hwcloud.bean.dto;

import lombok.Data;

/**
 *  统一用户对接返回参数
 *
 *     "statusCode": "200",
 *     "message": "保存成功",
 *     "success": true
 *
 * @author junglefisher
 * @date 2019/12/31 14:55
 */
@Data
public class ResponseStatus {

    private String statusCode;
    private String message;
    private String result;
    private String success;
}
