package com.upd.hwcloud.common.log;

import com.alibaba.fastjson.JSON;
import com.upd.hwcloud.bean.contains.SessionName;
import com.upd.hwcloud.bean.entity.Log;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.filter.HttpRequestWrapper;
import com.upd.hwcloud.common.log.OperationLog;
import com.upd.hwcloud.common.utils.IpUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

/**
 * Controller aop 切入
 */
@Aspect
@Component
public class ParameterAspect{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 切入点
	 */
	@Pointcut("execution(* com.upd.hwcloud.controller..*Controller.*(..))")
	public void aspect(){}
	@Pointcut("@annotation(com.upd.hwcloud.common.log.OperationLog)")
	public void OperationLog() {
	}
	/**
	 * 请求参数环绕
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value ="OperationLog()&&@annotation(annotation)")
	public Object doAround(ProceedingJoinPoint joinPoint, OperationLog annotation) throws Throwable{
		Object returnValue=null;
		StringBuilder builder=new StringBuilder();
		String ope = "";
		if (annotation!=null){
			ope = annotation.operation();
		}
		User u = new User();
		Log log= new Log();
		try {
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			ServletRequestAttributes sra = (ServletRequestAttributes) ra;
			HttpServletRequest req = sra.getRequest();
			Object user = req.getSession().getAttribute(SessionName.USER);
			log.setPath(ope);
			if (user != null) {
				 u = (User)user;
			}

			HttpServletRequest request = req;
			log.setCreatorId(u.getIdcard());
			log.setIp(IpUtil.getRealIP(request));
			String requestUrl = "http://" + request.getServerName() + ":"   + request.getServerPort() + request.getContextPath() + request.getServletPath();
			builder.append("【请求路径:"+requestUrl+"】");
			builder.append("【请求方法："+request.getMethod()+"】");
			builder.append("参数【"+JSON.toJSON(request.getParameterMap())+"】");
			returnValue = joinPoint.proceed();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			returnValue=R.error(e.getMessage());
		}
		if(returnValue instanceof R) {
			R re=(R)returnValue;
			builder.append("【请求结果："+re.get("msg")+"】\n");
		}
		log.setRemark(builder.toString());
		log.insert();
		logger.info(builder.toString());
		return returnValue;
	}


	//@Around(value ="aspect()")
	public Object controllerAsp(ProceedingJoinPoint joinPoint) throws Throwable{
		Object returnValue=null;
		StringBuilder builder=new StringBuilder();
		try {
			RequestAttributes ra = RequestContextHolder.getRequestAttributes();
			ServletRequestAttributes sra = (ServletRequestAttributes) ra;
			HttpServletRequest req = sra.getRequest();
			HttpRequestWrapper request = (HttpRequestWrapper) req;
			builder.append("******************************************************************\n");
			String requestUrl = "http://" + request.getServerName() + ":"   + request.getServerPort() + request.getContextPath() + request.getServletPath();
			builder.append("【请求路径:"+requestUrl+"】\n");
			builder.append("【请求方法："+request.getMethod()+"】\n");
			if(!((HttpRequestWrapper) req).isMultipart()) {
				builder.append("参数【"+request.getBody()+";"+JSON.toJSON(request.getParameterMap())+"】\n");
			}else {
				builder.append("【文件字节流】【参数:"+JSON.toJSON(request.getParameterMap())+"】\n");
			}
			returnValue = joinPoint.proceed();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			returnValue=R.error();
		}
		if(returnValue instanceof R) {
			R re=(R)returnValue;
			builder.append("【请求结果："+re.toString()+"】\n");
			builder.append("******************************************************************\n");
		}
		logger.info(builder.toString());
		return returnValue;
	}

}
