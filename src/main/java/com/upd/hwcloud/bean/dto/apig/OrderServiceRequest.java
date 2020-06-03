package com.upd.hwcloud.bean.dto.apig;

public class OrderServiceRequest {


    /**
     * name : tangbiaotest
     * service_guid : 49208536-07ef-e8fc-8382-a50cf515931a
     * service_plan_name : 247f3d7e7b1b4d4ca0391249662552a6_default
     * project_id : 9c92707606b344f98b6d58a4f376bdcd
     * desc : testtesttangbiao
     * alias : 应用名称（订购的服务名称）
     * cluster_id : 11111111-1111-1111-1111-111111111111
     * cluster_namespace : PaaS_kexin_shujuke_hailianjiexun
     */

    private String name;
    private String service_guid;
    private String service_plan_name;
    private String project_id;
    private String desc;
    private String alias;
    private String cluster_id;
    private String cluster_namespace;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService_guid() {
        return service_guid;
    }

    public void setService_guid(String service_guid) {
        this.service_guid = service_guid;
    }

    public String getService_plan_name() {
        return service_plan_name;
    }

    public void setService_plan_name(String service_plan_name) {
        this.service_plan_name = service_plan_name;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(String cluster_id) {
        this.cluster_id = cluster_id;
    }

    public String getCluster_namespace() {
        return cluster_namespace;
    }

    public void setCluster_namespace(String cluster_namespace) {
        this.cluster_namespace = cluster_namespace;
    }

}
