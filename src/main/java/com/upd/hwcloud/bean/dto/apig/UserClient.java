package com.upd.hwcloud.bean.dto.apig;


import okhttp3.OkHttpClient;

public class UserClient {
    private LoginResponse user;
    private OkHttpClient client;

    public LoginResponse getUser() {
        return user;
    }

    public void setUser(LoginResponse user) {
        this.user = user;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }
}
