package com.upd.hwcloud.bean.dto.apig;

import java.util.List;

public class ProjectList {


    /**
     * total : 1
     * projects : [{"domain_id":"29978e2182b24df79742247b957cda6e","region_status":"normal","tenant_id":"50625a6f-9101-4d0b-ba37-958a0900bf77","tenant_name":"PaaS_kexin_shujuke","level":3,"name":"PaaS_kexin_shujuke_hailianjiexun","region_id":"GDJWY-SX-1","description":"","region_name":{"en_us":"广东警务云","zh_cn":"广东警务云"},"id":"9c92707606b344f98b6d58a4f376bdcd","enabled":true}]
     */

    private int total;
    private List<Project> projects;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public static class Project {

        private List<Regions> regions;
        private String id;
        private String name;
        private String iam_project_name;
        private String description;
        private Boolean enabled;
        private String domain_id;
        private String tenant_id;
        private String tenant_name;
        private String level;
        private String role_id;
        private String role_name;

        public List<Regions> getRegions() {
            return regions;
        }

        public void setRegions(List<Regions> regions) {
            this.regions = regions;
        }

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

        public String getIam_project_name() {
            return iam_project_name;
        }

        public void setIam_project_name(String iam_project_name) {
            this.iam_project_name = iam_project_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getDomain_id() {
            return domain_id;
        }

        public void setDomain_id(String domain_id) {
            this.domain_id = domain_id;
        }

        public String getTenant_id() {
            return tenant_id;
        }

        public void setTenant_id(String tenant_id) {
            this.tenant_id = tenant_id;
        }

        public String getTenant_name() {
            return tenant_name;
        }

        public void setTenant_name(String tenant_name) {
            this.tenant_name = tenant_name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        @Override
        public String toString() {
            return "Project{" +
                    "regions=" + regions +
                    ", id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", iam_project_name='" + iam_project_name + '\'' +
                    ", description='" + description + '\'' +
                    ", enabled=" + enabled +
                    ", domain_id='" + domain_id + '\'' +
                    ", tenant_id='" + tenant_id + '\'' +
                    ", tenant_name='" + tenant_name + '\'' +
                    ", level='" + level + '\'' +
                    ", role_id='" + role_id + '\'' +
                    ", role_name='" + role_name + '\'' +
                    '}';
        }

        public static class Regions{
            private String region_id;
            private String region_status;
            private RegionName region_name;

            public String getRegion_id() {
                return region_id;
            }

            public void setRegion_id(String region_id) {
                this.region_id = region_id;
            }

            public String getRegion_status() {
                return region_status;
            }

            public void setRegion_status(String region_status) {
                this.region_status = region_status;
            }

            public RegionName getRegion_name() {
                return region_name;
            }

            public void setRegion_name(RegionName region_name) {
                this.region_name = region_name;
            }
        }

        public static class RegionName {
            /**
             * en_us : 广东警务云
             * zh_cn : 广东警务云
             */

            private String en_us;
            private String zh_cn;

            public String getEn_us() {
                return en_us;
            }

            public void setEn_us(String en_us) {
                this.en_us = en_us;
            }

            public String getZh_cn() {
                return zh_cn;
            }

            public void setZh_cn(String zh_cn) {
                this.zh_cn = zh_cn;
            }
        }
    }

}
