package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * SAAS二级页面内容
 * </p>
 *
 * @author huru
 * @since 2019-02-11
 */
@TableName("TB_SAAS_SUBPAGE")
public class SaasSubpage extends BaseSubpage<SaasSubpage> {

    @ApiModelProperty(value = "上云架构图")
    @TableField("ARCH_IMAGE")
    private String archImage;

    /**
     * 政府单位名称
     */
    @TableField("GOV_UNIT")
    protected String govUnit;

    /**
     * 政府单位联系人
     */
    @TableField("GOV_PRINCIPAL")
    protected String govPrincipal;

    /**
     * 政府单位联系人电话
     */
    @TableField("GOV_PRINCIPAL_PHONE")
    protected String govPrincipalPhone;


    public String getArchImage() {
        return archImage;
    }

    public void setArchImage(String archImage) {
        this.archImage = archImage;
    }

    public String getGovUnit() {
        return govUnit;
    }

    public void setGovUnit(String govUnit) {
        this.govUnit = govUnit;
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

}
