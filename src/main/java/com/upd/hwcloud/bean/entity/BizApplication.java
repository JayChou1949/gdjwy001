package com.upd.hwcloud.bean.entity;

import java.util.List;

/**
 * @Description:
 * @author: yyc
 * @date: 2018-10-15 10:57
 **/
public class BizApplication {
    private String appName;
    private String explain;
    private List<ApplicationDevDetail> lists;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public List<ApplicationDevDetail> getLists() {
        return lists;
    }

    public void setLists(List<ApplicationDevDetail> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "BizApplication{" +
                "appName='" + appName + '\'' +
                ", explain='" + explain + '\'' +
                ", lists=" + lists +
                '}';
    }
}
