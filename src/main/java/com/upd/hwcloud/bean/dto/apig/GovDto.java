package com.upd.hwcloud.bean.dto.apig;

import java.util.Date;

/**
 * @author junglefisher
 * @date 2020/1/10 11:43
 */
public class GovDto {
    private String id;// 机构id
    private String code;// 机构代码
    private String fullName;// 机构名称
    private String shortName;// 机构简称
    private String upGovId;// 上级机构id
    private String upGovName;// 上级机构名称
    private Date createTime;// 创建时间
    private Date deleteTime;// 删除时间
    private String deleted;// 是否删除
    private String isRoot;// 是否根节点
    private String sortNo;// 排序

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUpGovId() {
        return upGovId;
    }

    public void setUpGovId(String upGovId) {
        this.upGovId = upGovId;
    }

    public String getUpGovName() {
        return upGovName;
    }

    public void setUpGovName(String upGovName) {
        this.upGovName = upGovName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(String isRoot) {
        this.isRoot = isRoot;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    @Override
    public String toString() {
        return "GovDto{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", fullName='" + fullName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", upGovId='" + upGovId + '\'' +
                ", upGovName='" + upGovName + '\'' +
                ", createTime=" + createTime +
                ", deleteTime=" + deleteTime +
                ", deleted='" + deleted + '\'' +
                ", isRoot='" + isRoot + '\'' +
                ", sortNo='" + sortNo + '\'' +
                '}';
    }
}
