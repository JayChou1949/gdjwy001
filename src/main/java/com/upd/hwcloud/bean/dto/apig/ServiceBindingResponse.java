package com.upd.hwcloud.bean.dto.apig;

/**
 * 绑定返回结果
 */
public class ServiceBindingResponse {


    /**
     * id : 959
     * bind_id : 9d09d55c-984a-b638-e4f4-c376318f4735
     * caller_type : app
     * caller_guid : testguidtangbiao
     * caller_flag : 9c92707606b344f98b6d58a4f376bdcd
     * instance_guid : 7a02760f-c32d-51d2-c5e0-bdffde6e8021
     * secret_name : testguidtangbiao
     * cluster_id : 11111111-1111-1111-1111-111111111111
     * cluster_namespace : 9c92707606b344f98b6d58a4f376bdcd
     * credentials : {"AppKey":"e2afeb9100084fd2b19a0dfbc691c957","AppSecret":"193e3a204a2f41619957bfba765dd6f6"}
     * created_at : 2019-04-02 10:24:03.969045246 +0000 UTC
     */

    private int id;
    private String bind_id;
    private String caller_type;
    private String caller_guid;
    private String caller_flag;
    private String instance_guid;
    private String secret_name;
    private String cluster_id;
    private String cluster_namespace;
    private Credentials credentials;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBind_id() {
        return bind_id;
    }

    public void setBind_id(String bind_id) {
        this.bind_id = bind_id;
    }

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

    public String getCaller_flag() {
        return caller_flag;
    }

    public void setCaller_flag(String caller_flag) {
        this.caller_flag = caller_flag;
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

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public static class Credentials {
        /**
         * AppKey : e2afeb9100084fd2b19a0dfbc691c957
         * AppSecret : 193e3a204a2f41619957bfba765dd6f6
         */

        private String AppKey;
        private String AppSecret;

        public String getAppKey() {
            return AppKey;
        }

        public void setAppKey(String AppKey) {
            this.AppKey = AppKey;
        }

        public String getAppSecret() {
            return AppSecret;
        }

        public void setAppSecret(String AppSecret) {
            this.AppSecret = AppSecret;
        }
    }
}
