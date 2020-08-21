package com.hirisun.cloud.system.vo;


import com.hirisun.cloud.model.user.UserVO;
import lombok.Data;

@Data
public class Message {
    /**
     * 内容
     */
    private String content;
    /**
     * 用户信息
     */
    private UserVO user;


}
