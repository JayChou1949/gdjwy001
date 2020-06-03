package com.upd.hwcloud.bean.dto.apig;

import java.util.List;

/**
 * 登录接口请求数据
 */
public class LoginRequest {

    private Auth auth;

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public static class Auth {

        private Identity identity;
        private Scope scope;

        public Identity getIdentity() {
            return identity;
        }

        public void setIdentity(Identity identity) {
            this.identity = identity;
        }

        public Scope getScope() {
            return scope;
        }

        public void setScope(Scope scope) {
            this.scope = scope;
        }

        public static class Identity {

            private Password password;
            private List<String> methods;

            public Password getPassword() {
                return password;
            }

            public void setPassword(Password password) {
                this.password = password;
            }

            public List<String> getMethods() {
                return methods;
            }

            public void setMethods(List<String> methods) {
                this.methods = methods;
            }

            public static class Password {

                private User user;

                public User getUser() {
                    return user;
                }

                public void setUser(User user) {
                    this.user = user;
                }

                public static class User {

                    private String name;
                    private String password;
                    private Domain domain;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getPassword() {
                        return password;
                    }

                    public void setPassword(String password) {
                        this.password = password;
                    }

                    public Domain getDomain() {
                        return domain;
                    }

                    public void setDomain(Domain domain) {
                        this.domain = domain;
                    }

                }
            }
        }

        public static class Scope {

            private Project project;

            private Domain domain;

            public Project getProject() {
                return project;
            }

            public void setProject(Project project) {
                this.project = project;
            }

            public Domain getDomain() {
                return domain;
            }

            public void setDomain(Domain domain) {
                this.domain = domain;
            }

            public static class Project {

                private String name;

                private String id;

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
            }
        }

        public static class Domain {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

    }
}
