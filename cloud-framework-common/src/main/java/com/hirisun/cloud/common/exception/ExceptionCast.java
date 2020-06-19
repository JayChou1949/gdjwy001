package com.hirisun.cloud.common.exception;

import com.hirisun.cloud.common.vo.ResultCode;

/**
 * @ClassName ExceptionCast
 * @Author zhoufeng
 * @Date 2019/11/14 10:50
 * @Description 异常捕获类
 */
public class ExceptionCast {
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
