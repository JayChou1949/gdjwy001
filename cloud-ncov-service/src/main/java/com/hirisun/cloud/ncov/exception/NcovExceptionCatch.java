package com.hirisun.cloud.ncov.exception;

import com.alibaba.fastjson.JSONException;
import com.hirisun.cloud.common.exception.ExceptionCatch;
import com.hirisun.cloud.common.vo.CommonCode;
import org.springframework.stereotype.Component;

/**
 * @author zhoufeng
 * @version 1.0
 * @className NcovExceptionCatch
 * @data 2020/6/29 16:19
 * @description
 */
@Component
public class NcovExceptionCatch extends ExceptionCatch {

    static {
        //定义异常类型所对应的错误代码
        builder.put(JSONException.class, CommonCode.INVALID_PARAM);
    }
}
