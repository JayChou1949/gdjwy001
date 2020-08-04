package com.hirisun.cloud.api.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author wuxiaoxing
 * @date 2020-08-01
 * @description
 */
@Slf4j
@Component
public class SysLogApiFallback implements SysLogApi {


    @Override
    public boolean saveLog(String creator, String remark, String path, String ip) {
        return false;
    }
}
