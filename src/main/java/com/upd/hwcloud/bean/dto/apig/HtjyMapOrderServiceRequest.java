package com.upd.hwcloud.bean.dto.apig;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: yyc
 * @Date: 16/05/2019 1:54 PM
 */
public class HtjyMapOrderServiceRequest {

    /**
     * desc : test
     * name : basemaptest0329
     * alias : test jingyi
     * project_id : 9c92707606b344f98b6d58a4f376bdcd
     * service_guid : 3b057df9-84a5-ec5f-a9ba-0c78e9696010
     * service_plan_name : customeplan
     * parameters : {"4591":true,"4592":true,"4593":false,"4594":false,"4595":false,"4596":false,"4597":false,"4598":false,"4599":false,"4600":false,"4601":false,"4602":false,"4603":false,"appName":"警务云门户系统","applyUnit":"PaaS_kexin_shujuke_hailianjiexun","terentName":"GDJWY-SX-1"}
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
         * 4591 : true
         * 4592 : true
         * 4593 : false
         * 4594 : false
         * 4595 : false
         * 4596 : false
         * 4597 : false
         * 4598 : false
         * 4599 : false
         * 4600 : false
         * 4601 : false
         * 4602 : false
         * 4603 : false
         * appName : 警务云门户系统
         * applyUnit : PaaS_kexin_shujuke_hailianjiexun
         * terentName : GDJWY-SX-1
         */

        @JSONField(name = "4591")
        private boolean four591;
        @JSONField(name = "4592")
        private boolean four592;
        @JSONField(name = "4593")
        private boolean four593;
        @JSONField(name = "4594")
        private boolean four594;
        @JSONField(name = "4595")
        private boolean four595;
        @JSONField(name = "4596")
        private boolean four596;
        @JSONField(name = "4597")
        private boolean four597;
        @JSONField(name = "4598")
        private boolean four598;
        @JSONField(name = "4599")
        private boolean four599;
        @JSONField(name = "4600")
        private boolean four600;
        @JSONField(name = "4601")
        private boolean four601;
        @JSONField(name = "4602")
        private boolean four602;
        @JSONField(name = "4603")
        private boolean four603;
        private String appName;
        private String applyUnit;
        private String terentName;


        public boolean isFour591() {
            return four591;
        }

        public void setFour591(boolean four591) {
            this.four591 = four591;
        }

        public boolean isFour592() {
            return four592;
        }

        public void setFour592(boolean four592) {
            this.four592 = four592;
        }

        public boolean isFour593() {
            return four593;
        }

        public void setFour593(boolean four593) {
            this.four593 = four593;
        }

        public boolean isFour594() {
            return four594;
        }

        public void setFour594(boolean four594) {
            this.four594 = four594;
        }

        public boolean isFour595() {
            return four595;
        }

        public void setFour595(boolean four595) {
            this.four595 = four595;
        }

        public boolean isFour596() {
            return four596;
        }

        public void setFour596(boolean four596) {
            this.four596 = four596;
        }

        public boolean isFour597() {
            return four597;
        }

        public void setFour597(boolean four597) {
            this.four597 = four597;
        }

        public boolean isFour598() {
            return four598;
        }

        public void setFour598(boolean four598) {
            this.four598 = four598;
        }

        public boolean isFour599() {
            return four599;
        }

        public void setFour599(boolean four599) {
            this.four599 = four599;
        }

        public boolean isFour600() {
            return four600;
        }

        public void setFour600(boolean four600) {
            this.four600 = four600;
        }

        public boolean isFour601() {
            return four601;
        }

        public void setFour601(boolean four601) {
            this.four601 = four601;
        }

        public boolean isFour602() {
            return four602;
        }

        public void setFour602(boolean four602) {
            this.four602 = four602;
        }

        public boolean isFour603() {
            return four603;
        }

        public void setFour603(boolean four603) {
            this.four603 = four603;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getApplyUnit() {
            return applyUnit;
        }

        public void setApplyUnit(String applyUnit) {
            this.applyUnit = applyUnit;
        }

        public String getTerentName() {
            return terentName;
        }

        public void setTerentName(String terentName) {
            this.terentName = terentName;
        }

        @Override
        public String toString() {
            return "ParametersBean{" +
                    "four591=" + four591 +
                    ", four592=" + four592 +
                    ", four593=" + four593 +
                    ", four594=" + four594 +
                    ", four595=" + four595 +
                    ", four596=" + four596 +
                    ", four597=" + four597 +
                    ", four598=" + four598 +
                    ", four599=" + four599 +
                    ", four600=" + four600 +
                    ", four601=" + four601 +
                    ", four602=" + four602 +
                    ", four603=" + four603 +
                    ", appName='" + appName + '\'' +
                    ", applyUnit='" + applyUnit + '\'' +
                    ", terentName='" + terentName + '\'' +
                    '}';
        }
    }
}
