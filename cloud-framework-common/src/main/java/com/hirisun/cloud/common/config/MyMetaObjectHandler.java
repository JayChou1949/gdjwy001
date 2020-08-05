package com.hirisun.cloud.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author wuxiaoxing 2020-07-28
 * 自定义填充处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        this.setFieldValByName("createTime", now, metaObject);
        this.setFieldValByName("modifiedTime", now, metaObject);
    }

    public void updateFill(MetaObject metaObject) {
        Date now = new Date();
        this.setFieldValByName("modifiedTime", now, metaObject);
    }

}