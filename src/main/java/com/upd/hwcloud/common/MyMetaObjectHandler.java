package com.upd.hwcloud.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 自定义填充处理器
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    public void insertFill(MetaObject metaObject) {
        Object testType = getFieldValByName("createTime", metaObject);//mybatis-plus版本2.0.9+
        if (testType == null) {
            Date now = new Date();
            setFieldValByName("createTime", now, metaObject);//mybatis-plus版本2.0.9+
            setFieldValByName("modifiedTime", now, metaObject);//mybatis-plus版本2.0.9+

        }
        Object sort = getFieldValByName("sort", metaObject);//mybatis-plus版本2.0.9+
        if (sort == null) {
            setFieldValByName("sort", new Date().getTime()/1000, metaObject);
        }
    }

    public void updateFill(MetaObject metaObject) {
        Date now = new Date();
        setFieldValByName("modifiedTime", now, metaObject);//mybatis-plus版本2.0.9+
    }

}