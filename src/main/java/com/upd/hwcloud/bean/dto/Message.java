package com.upd.hwcloud.bean.dto;

import com.upd.hwcloud.bean.entity.User;

public class Message {

    private String content;

    private User user;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
