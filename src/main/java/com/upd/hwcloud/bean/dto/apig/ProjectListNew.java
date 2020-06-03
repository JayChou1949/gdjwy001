package com.upd.hwcloud.bean.dto.apig;

import java.util.List;

public class ProjectListNew {
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<UserListDetailBean> getUsers() {
        return users;
    }

    public void setUsers(List<UserListDetailBean> users) {
        this.users = users;
    }

    private List<UserListDetailBean> users;

    public static  class UserListDetailBean {
        private String enabled;
        private String description;
        private String email;
        private String areacode;
        private String phone;
        private String id;
        private String name;
        private String display_name;
        private String create_at;
        private List<UserRoleRaw> roles;
        private String domain_id;
        private String domain_name;
        private String vdc_id;
        private String resource_tenant_id;
        private String user_type;

        public String getEnabled() {
            return enabled;
        }

        public void setEnabled(String enabled) {
            this.enabled = enabled;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAreacode() {
            return areacode;
        }

        public void setAreacode(String areacode) {
            this.areacode = areacode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public List<UserRoleRaw> getRoles() {
            return roles;
        }

        public void setRoles(List<UserRoleRaw> roles) {
            this.roles = roles;
        }

        public String getDomain_id() {
            return domain_id;
        }

        public void setDomain_id(String domain_id) {
            this.domain_id = domain_id;
        }

        public String getDomain_name() {
            return domain_name;
        }

        public void setDomain_name(String domain_name) {
            this.domain_name = domain_name;
        }

        public String getVdc_id() {
            return vdc_id;
        }

        public void setVdc_id(String vdc_id) {
            this.vdc_id = vdc_id;
        }

        public String getResource_tenant_id() {
            return resource_tenant_id;
        }

        public void setResource_tenant_id(String resource_tenant_id) {
            this.resource_tenant_id = resource_tenant_id;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }
    }

    public static class UserRoleRaw {
        private String id;
        private String name;
        private String display_name;
        private String user_role_type;

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

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public String getUser_role_type() {
            return user_role_type;
        }

        public void setUser_role_type(String user_role_type) {
            this.user_role_type = user_role_type;
        }
    }
}
