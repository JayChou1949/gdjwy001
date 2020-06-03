package com.upd.hwcloud.common.log;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface OperationLog {
    String value() default "";

    String operation() default "";
}
