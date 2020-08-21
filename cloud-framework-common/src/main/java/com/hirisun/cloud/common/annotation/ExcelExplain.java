package com.hirisun.cloud.common.annotation;

import java.lang.annotation.*;

/**
 * Created by zwb on 2018/8/30.
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelExplain {
    String head();
}
