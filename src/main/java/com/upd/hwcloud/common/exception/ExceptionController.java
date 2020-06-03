package com.upd.hwcloud.common.exception;

import com.upd.hwcloud.bean.response.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 控制器公共方法
 * @author: hui.z
 * @date: 2017-12-26 16:25
 **/
@ControllerAdvice
public class ExceptionController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * 获取用户IP
     */
    public static String getRemoteIP(HttpServletRequest request) {
        String ip = null;
        if ((request.getHeader("x-forwarded-for") != null) && (!"unknown".equalsIgnoreCase(request.getHeader("x-forwarded-for")))) {
            ip = request.getHeader("x-forwarded-for");
        } else {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler({BaseException.class})
    @ResponseBody
    public R businessException(BaseException e) {
        logger.error("BusinessException，errorMsg= " + e.getMessage(), e);
        return R.error(e.getCode(), e.getMessage());
    }

//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(UnauthorizedException.class)
//    public RestResult UnauthorizedException(Exception e) {
//        logger.error("权限异常1", e);
//        return new RestResult(null, RestErrorCode.UNAUTHORIZED.getCode(),RestErrorCode.UNAUTHORIZED.getMsg(),null);
//    }

    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public R allException(Exception e) {
        logger.error("系统异常", e);
        return R.error();
    }

}
