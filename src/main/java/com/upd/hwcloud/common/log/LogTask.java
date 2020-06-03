package com.upd.hwcloud.common.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.upd.hwcloud.bean.entity.Log;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogTask implements Runnable{

    private static final transient Logger logger = LoggerFactory.getLogger(LogTask.class);
    private Object[] args;
    private OperationLog operationLog;
    private String ip;
    private String userId;
    private Class  clazz;
    private String clazzName;
    private String methodName;

    public LogTask(Class clazz, String clazzName, String methodName, Object[] args, OperationLog operationLog, String ip, String userId) {
        this.args = args;
        this.operationLog = operationLog;
        this.ip = ip;
        this.userId = userId;
        this.clazz=clazz;
        this.clazzName=clazzName;
        this.methodName=methodName;
    }

    @Override
    public void run() {
        try {
            String operation= getFieldsName();
            Log log=new Log();
            log.setCreatorId(userId);
            log.setIp(ip);
            log.setRemark(operation);
            log.setPath(operationLog.value());
            log.insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取日志信息中的动态参数，然后替换
     * @param desc
     * @return
     */
    private static List<String> descFormat(String desc){
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("#\\{([^\\}]+)\\}");
        Matcher matcher = pattern.matcher(desc);
        while(matcher.find()){
            String t = matcher.group(1);
            list.add(t);
        }
        return list;
    }

    /**
     * 通过反射机制 获取被切参数名以及参数值
     */
    private String getFieldsName() throws NotFoundException {
        String operation=operationLog.operation();
        ClassPool pool =  ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);
        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        List<String> params=descFormat(operation);//需要替换的参数名
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            try {
                //对象
                operation=getParam(operation,params,JSON.toJSONString(args[i]));
            } catch (Exception e) {
                //非对象参数
                int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
                String name=attr.variableName(i + pos);
                operation= operation.replace("#{"+name+"}",JSON.toJSONString(args[i]));
            }
        }
        return operation;
    }

    private String getParam(String operation,List<String> list,String json){
        JSONObject jsonObject=JSON.parseObject(json);
        for(String key:list){
            String val= JSONPath.eval(jsonObject,"$"+key)==null?"":JSONPath.eval(jsonObject,"$"+key).toString();
            operation = operation.replace("#{" + key + "}",val );
        }
        return operation;
    }

}
