package com.hirisun.cloud.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ResponseResult
 * @Author zhoufeng
 * @Date 2019/11/14 10:32
 * @Description 响应结果
 */
@Data
@NoArgsConstructor
public class ResponseResult implements Response {
	Integer code = SUCCESS_CODE;
    String msg = SUCCESS_MSG;

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(ResultCode resultCode) {
        this.code = resultCode.code();
        this.msg = resultCode.msg();
    }

    public static ResponseResult success(){
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public static ResponseResult fail(){
        return new ResponseResult(CommonCode.FAIL);
    }
}
