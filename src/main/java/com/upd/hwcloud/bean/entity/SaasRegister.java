package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * SAAS服务注册表
 * </p>
 *
 * @author zwb
 * @since 2019-05-27
 */
@TableName("TB_SAAS_REGISTER")
public class SaasRegister extends Register<SaasRegister> {

    /**
     * 是否加密应用 0:否 1:是
     */
    @TableField("SECRET")
    private String secret;

    /**
     * 政府单位名称
     */
    @TableField("GOV_UNIT")
    protected String govUnit;

    /**
     * 政府单位机构代码
     */
    @TableField("GOV_ORG_CODE")
    protected String govOrgCode;

    /**
     * 政府单位负责人
     */
    @TableField("GOV_PRINCIPAL")
    protected String govPrincipal;

    /**
     * 政府单位负责人电话
     */
    @TableField("GOV_PRINCIPAL_PHONE")
    protected String govPrincipalPhone;

    /**
     * 政府单位负责人身份证
     */
    @TableField("GOV_PRINCIPAL_IDCARD")
    protected String govPrincipalIdcard;

    /**
     * 政府单位负责人职务
     */
    @TableField("GOV_PRINCIPAL_POST_TYPE")
    protected String govPrincipalPostType;


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getGovUnit() {
        return govUnit;
    }

    public void setGovUnit(String govUnit) {
        this.govUnit = govUnit;
    }

    public String getGovOrgCode() {
        return govOrgCode;
    }

    public void setGovOrgCode(String govOrgCode) {
        this.govOrgCode = govOrgCode;
    }

    public String getGovPrincipal() {
        return govPrincipal;
    }

    public void setGovPrincipal(String govPrincipal) {
        this.govPrincipal = govPrincipal;
    }

    public String getGovPrincipalPhone() {
        return govPrincipalPhone;
    }

    public void setGovPrincipalPhone(String govPrincipalPhone) {
        this.govPrincipalPhone = govPrincipalPhone;
    }

    public String getGovPrincipalIdcard() {
        return govPrincipalIdcard;
    }

    public void setGovPrincipalIdcard(String govPrincipalIdcard) {
        this.govPrincipalIdcard = govPrincipalIdcard;
    }

    public String getGovPrincipalPostType() {
        return govPrincipalPostType;
    }

    public void setGovPrincipalPostType(String govPrincipalPostType) {
        this.govPrincipalPostType = govPrincipalPostType;
    }

}
