package com.upd.hwcloud.bean.dto.apig;

/**
 * @author: yyc
 * @Date: 16/05/2019 9:09 PM
 */
//todo: 改造
public class SjgtMapOrderServiceRequest{


    /**
     * desc : test
     * name : ppmaptest
     * alias : 世纪高通测试
     * project_id : 9c92707606b344f98b6d58a4f376bdcd
     * service_guid : 0673c4fa-ad24-50ae-5392-0afab536a50c
     * service_plan_name : default
     * parameters : {"password":"123456","username":"userA"}
     * cluster_id : 11111111-1111-1111-1111-111111111111
     * cluster_namespace : 9c92707606b344f98b6d58a4f376bdcd
     */

    private String desc;
    private String name;
    private String alias;
    private String project_id;
    private String service_guid;
    private String service_plan_name;
    private ParametersBean parameters;
    private String cluster_id;
    private String cluster_namespace;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
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

    public ParametersBean getParameters() {
        return parameters;
    }

    public void setParameters(ParametersBean parameters) {
        this.parameters = parameters;
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

    public static class ParametersBean {
        /**
         * password : 123456
         * username : userA
         */

        private String password;
        private String username;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
