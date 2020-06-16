package com.upd.hwcloud.runner;

import com.upd.hwcloud.service.application.IResourceRecoverAppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * @author  wuxiaoxing
 * 2020-06-16
 * 项目初始化后运行类，当项目启动后需要重新执行检测方法
 */
@Component
public class AppRunner implements ServletContextAware {
    @Autowired
    private IResourceRecoverAppInfoService appInfoService;

    @Override
    public void setServletContext(ServletContext servletContext) {
        System.out.println("--------项目启动完毕，开始运行未完成方法-------------");
        //取未被负责人处理的回收资源工单
        appInfoService.queryUntreatedRecover();
    }

    /**
     * 获取未被负责人处理的回收资源工单
     */
//    public void queryResourceRecover(){
//        appInfoService.queryUntreatedRecover();
//
//    }

}
