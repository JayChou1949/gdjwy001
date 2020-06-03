package com.upd.hwcloud.bean.dto.apig;

/**
 * 应用详情
 */
public class AppDetail {


    /**
     * id : 06ac29ac9ba54bab98614703fa0b8a69
     * name : 测试唐彪应用1
     * status : enabled
     * app_key : b9b6cac6739d46749cbfff1491e474cb
     * app_secret : 5237fa996b594186a32d729961485e29
     * remark : 测试唐彪应用1
     * register_time : 2019-04-02T17:43:56.465344422+08:00
     * update_time : 2019-04-02T17:43:56.465344722+08:00
     */

    private String id;
    private String name;
    private String status;
    private String app_key;
    private String app_secret;
    private String remark;
    private String register_time;
    private String update_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getApp_secret() {
        return app_secret;
    }

    public void setApp_secret(String app_secret) {
        this.app_secret = app_secret;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
