package com.upd.hwcloud.common.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 7200)
public class SpringSessionConfig extends AbstractHttpSessionApplicationInitializer {

}
