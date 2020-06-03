package com.upd.hwcloud.bean.entity.iaasConfig;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 全网 警种 地区 top5 low5
 * </p>
 *
 * @author huru
 * @since 2018-11-17
 */
@TableName("TB_TOP5_LOW5")
public class Top5Low5 extends Model<Top5Low5> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 主机名 警种 地区
     */
         @TableField("NAME")
    private String name;

        /**
     * cpu使用率
     */
         @TableField("CPU")
    private String cpu;

        /**
     * 内存使用率
     */
         @TableField("EMS")
    private String ems;

        /**
     * 存储使用率
     */
         @TableField("STOR")
    private String stor;

        /**
     * 1全网 2警种 3地区
     */
         @TableField("TYPE")
    private String type;

        /**
     * 1top5  2 low5
     */
         @TableField("SORT_TYPE")
    private String sortType;

        /**
     * 排序
     */
         @TableField("SORT_NUM")
    private Long sortNum;


    public String getId() {
        return id;
    }

    public Top5Low5 setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Top5Low5 setName(String name) {
        this.name = name;
        return this;
    }

    public String getCpu() {
        return cpu;
    }

    public Top5Low5 setCpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public String getEms() {
        return ems;
    }

    public Top5Low5 setEms(String ems) {
        this.ems = ems;
        return this;
    }

    public String getStor() {
        return stor;
    }

    public Top5Low5 setStor(String stor) {
        this.stor = stor;
        return this;
    }

    public String getType() {
        return type;
    }

    public Top5Low5 setType(String type) {
        this.type = type;
        return this;
    }

    public String getSortType() {
        return sortType;
    }

    public Top5Low5 setSortType(String sortType) {
        this.sortType = sortType;
        return this;
    }

    public Long getSortNum() {
        return sortNum;
    }

    public Top5Low5 setSortNum(Long sortNum) {
        this.sortNum = sortNum;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Top5Low5{" +
        "id=" + id +
        ", name=" + name +
        ", cpu=" + cpu +
        ", ems=" + ems +
        ", stor=" + stor +
        ", type=" + type +
        ", sortType=" + sortType +
        ", sortNum=" + sortNum +
        "}";
    }
}
