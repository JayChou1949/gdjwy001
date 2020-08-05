package com.hirisun.cloud.common.annotation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import springfox.documentation.annotations.ApiIgnore;

import java.lang.annotation.*;
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LoginUser {

}
