package com.upd.hwcloud.bean.response;

import com.alibaba.fastjson.annotation.JSONField;

public class TokenResponse {

    @JSONField(name = "refresh_token")
    private String refreshToken;

    @JSONField(name = "access_token")
    private String accessToken;


    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
