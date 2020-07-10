package com.hirisun.cloud.file.exception;

import com.hirisun.cloud.common.exception.ExceptionCatch;
import com.hirisun.cloud.file.vo.FileCode;
import org.csource.common.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author zhoufeng
 * @version 1.0
 * @className FdfsExceptiionCatch
 * @data 2020/7/8 17:59
 * @description FDFS文件服务异常捕获类
 */
@ControllerAdvice
public class FdfsExceptiionCatch extends ExceptionCatch {
    static {
        //定义异常类型所对应的错误代码
        builder.put(MyException.class, FileCode.FDFS_COONECT_FAULT);
    }
}
