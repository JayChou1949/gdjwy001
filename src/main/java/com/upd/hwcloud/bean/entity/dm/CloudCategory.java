package com.upd.hwcloud.bean.entity.dm;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yyc
 * @since 2019-08-13
 */
@TableName("TB_DM_CLOUD_CATEGORY")
public class CloudCategory extends Model<CloudCategory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField("NAME")
    private String name;

    @TableField("NAME_CN")
    private String nameCn;

    @TableField("NUM")
    private Double num;

    @TableField("CLOUD")
    private String cloud;

    /**
     * is display on level2 page 0显示 1显示
     * */

    @TableField("DISPLAY")
    private Integer display;


    @TableField("DATA_UNIT")
    private String dataUnit;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getDataUnit() {
        return dataUnit;
    }

    public void setDataUnit(String dataUnit) {
        this.dataUnit = dataUnit;
    }

    public String getId() {
        return id;
    }

    public CloudCategory setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CloudCategory setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameCn() {
        return nameCn;
    }

    public CloudCategory setNameCn(String nameCn) {
        this.nameCn = nameCn;
        return this;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public String getCloud() {
        return cloud;
    }

    public CloudCategory setCloud(String cloud) {
        this.cloud = cloud;
        return this;
    }

    public Integer getDisplay() {
        return display;
    }

    public CloudCategory setDisplay(Integer display) {
        this.display = display;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CloudCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", nameCn='" + nameCn + '\'' +
                ", num=" + num +
                ", cloud='" + cloud + '\'' +
                ", display=" + display +
                ", dataUnit='" + dataUnit + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
