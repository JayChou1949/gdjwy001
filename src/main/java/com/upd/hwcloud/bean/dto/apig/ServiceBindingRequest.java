package com.upd.hwcloud.bean.dto.apig;

/**
 * 绑定请求数据
 */
public class ServiceBindingRequest {


    /**
     * caller_type : app
     * caller_guid : testguidtangbiao
     * instance_guid : 6dc3494a-fcab-00ab-a098-a01bd6ec9053
     * secret_name : testguidtangbiao
     * cluster_id : 11111111-1111-1111-1111-111111111111
     * cluster_namespace : 9c92707606b344f98b6d58a4f376bdcd
     */

    private String caller_type;
    private String caller_guid;
    private String instance_guid;
    private String secret_name;
    private String cluster_id;
    private String cluster_namespace;

    public String getCaller_type() {
        return caller_type;
    }

    public void setCaller_type(String caller_type) {
        this.caller_type = caller_type;
    }

    public String getCaller_guid() {
        return caller_guid;
    }

    public void setCaller_guid(String caller_guid) {
        this.caller_guid = caller_guid;
    }

    public String getInstance_guid() {
        return instance_guid;
    }

    public void setInstance_guid(String instance_guid) {
        this.instance_guid = instance_guid;
    }

    public String getSecret_name() {
        return secret_name;
    }

    public void setSecret_name(String secret_name) {
        this.secret_name = secret_name;
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
