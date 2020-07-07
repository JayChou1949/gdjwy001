package com.hirisun.cloud.third.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.third.bean.ThreePartyInterface;

import java.util.Map;

/**
 * <p>
 * 第三方接口表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-06-28
 */
public interface ThreePartyInterfaceService extends IService<ThreePartyInterface> {

    void getDataHandler(String url,String name,String label);
    void jsonDataHandler(String url,String name,String label,String json);
    void postDataHandler(String url, String name, String label, Map<String, String> map);

}
