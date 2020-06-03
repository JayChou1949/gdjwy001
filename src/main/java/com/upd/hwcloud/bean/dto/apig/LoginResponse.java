package com.upd.hwcloud.bean.dto.apig;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 登录响应数据
 */
public class LoginResponse {

    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public static class Token {

        private String expires_at;
        private Project project;
        private String issued_at;
        private User user;
        private List<String> methods;
        private List<Catalog> catalog;
        private List<Roles> roles;

        public String getExpires_at() {
            return expires_at;
        }

        public void setExpires_at(String expires_at) {
            this.expires_at = expires_at;
        }

        public Project getProject() {
            return project;
        }

        public void setProject(Project project) {
            this.project = project;
        }

        public String getIssued_at() {
            return issued_at;
        }

        public void setIssued_at(String issued_at) {
            this.issued_at = issued_at;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public List<String> getMethods() {
            return methods;
        }

        public void setMethods(List<String> methods) {
            this.methods = methods;
        }

        public List<Catalog> getCatalog() {
            return catalog;
        }

        public void setCatalog(List<Catalog> catalog) {
            this.catalog = catalog;
        }

        public List<Roles> getRoles() {
            return roles;
        }

        public void setRoles(List<Roles> roles) {
            this.roles = roles;
        }

        public static class Project {
            /**
             * domain : {"name":"GDJWY","id":"29978e2182b24df79742247b957cda6e"}
             * name : PaaS_kexin_shujuke_hailianjiexun
             * id : 9c92707606b344f98b6d58a4f376bdcd
             */

            private Domain domain;
            private String name;
            private String id;

            public Domain getDomain() {
                return domain;
            }

            public void setDomain(Domain domain) {
                this.domain = domain;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class Domain {
                /**
                 * name : GDJWY
                 * id : 29978e2182b24df79742247b957cda6e
                 */

                private String name;
                private String id;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }

        public static class User {
            /**
             * domain : {"name":"GDJWY","id":"29978e2182b24df79742247b957cda6e"}
             * name : 410184198209096919
             * id : 64e0071f5e96427db1d0c9e343fd17ef
             */

            private DomainX domain;
            private String name;
            private String id;

            public DomainX getDomain() {
                return domain;
            }

            public void setDomain(DomainX domain) {
                this.domain = domain;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class DomainX {
                /**
                 * name : GDJWY
                 * id : 29978e2182b24df79742247b957cda6e
                 */

                private String name;
                private String id;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }

        public static class Catalog {
            /**
             * endpoints : [{"region_id":"GDJWY-SX-1","id":"e5cffd1da4494a278bc1ee33c528428d","region":"GDJWY-SX-1","interface":"public","url":"https://volume.az1.guangdong.jwy.gd:443/v2/9c92707606b344f98b6d58a4f376bdcd"}]
             * name : cinderv2
             * id : b729535fe1724624a3ad798cffe2dcb6
             * type : volumev2
             */

            private String name;
            private String id;
            private String type;
            private List<Endpoints> endpoints;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Endpoints> getEndpoints() {
                return endpoints;
            }

            public void setEndpoints(List<Endpoints> endpoints) {
                this.endpoints = endpoints;
            }

            public static class Endpoints {
                /**
                 * region_id : GDJWY-SX-1
                 * id : e5cffd1da4494a278bc1ee33c528428d
                 * region : GDJWY-SX-1
                 * interface : public
                 * url : https://volume.az1.guangdong.jwy.gd:443/v2/9c92707606b344f98b6d58a4f376bdcd
                 */

                private String region_id;
                private String id;
                private String region;
                @JSONField(name = "interface")
                private String interfaceX;
                private String url;

                public String getRegion_id() {
                    return region_id;
                }

                public void setRegion_id(String region_id) {
                    this.region_id = region_id;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getRegion() {
                    return region;
                }

                public void setRegion(String region) {
                    this.region = region;
                }

                public String getInterfaceX() {
                    return interfaceX;
                }

                public void setInterfaceX(String interfaceX) {
                    this.interfaceX = interfaceX;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public static class Roles {
            /**
             * name : te_admin
             * id : 140e8cf64b3f4874afda034d072e75be
             */

            private String name;
            private String id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
