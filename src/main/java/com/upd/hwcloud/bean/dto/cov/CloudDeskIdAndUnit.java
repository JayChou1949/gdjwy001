package com.upd.hwcloud.bean.dto.cov;

/**
 * @author junglefisher
 * @date 2020/3/2 19:00
 */
public class CloudDeskIdAndUnit {

    /**
     * 单位
     */
    private String unit;

    /**
     * ID
     */
    private String id;

    /**
     * 申请类型
     */
    private String useType;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }
}
