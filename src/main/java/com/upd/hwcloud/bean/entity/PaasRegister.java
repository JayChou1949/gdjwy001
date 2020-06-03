package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * PAAS服务注册表
 * </p>
 *
 * @author zwb
 * @since 2019-06-12
 */
@TableName("TB_PAAS_REGISTER")
public class PaasRegister  extends Register<PaasRegister> {



        /**
     * 是否API 接入；0-否，1-是
     */
         @TableField("API_ACCESS")
    private String apiAccess;



    public String getApiAccess() {
        return apiAccess;
    }

    public PaasRegister setApiAccess(String apiAccess) {
        this.apiAccess = apiAccess;
        return this;
    }

}
