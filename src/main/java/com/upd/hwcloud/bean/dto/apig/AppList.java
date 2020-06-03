package com.upd.hwcloud.bean.dto.apig;

import java.util.List;

/**
 * 用户应用列表
 */
public class AppList {


    /**
     * total : 3
     * size : 3
     * apps : [{"id":"f18b49b62f1b47788bcd86ca0cac9b1e","name":"测试唐彪应用","status":"enabled","app_key":"5f0123ca382c4e8c93becd09d6aa298a","remark":"测试唐彪应用","register_time":"2019-03-30T03:14:17Z","update_time":"2019-03-30T03:14:17Z"},{"id":"1145753b2fa746e8aa4d7f670dd3b2e1","name":"警务云门户系统","status":"enabled","app_key":"bc58f3f75fc74047a3829ed7a69f6242","remark":"订购服务时对应的应用系统","register_time":"2019-03-20T09:44:37Z","update_time":"2019-03-28T03:50:14.556114Z"},{"id":"973974fe12d14f2aa5f95035e07565a2","name":"gdkxsjgjcxfw_e29f5f4d","status":"enabled","app_key":"313646842c9046ad80fc1aaa9268e258","remark":"Create app for subscribable APIG","register_time":"2019-03-12T08:18:53Z","update_time":"2019-03-12T08:18:53Z"}]
     */

    private int total;
    private int size;
    private List<AppDetail> apps;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<AppDetail> getApps() {
        return apps;
    }

    public void setApps(List<AppDetail> apps) {
        this.apps = apps;
    }

}
