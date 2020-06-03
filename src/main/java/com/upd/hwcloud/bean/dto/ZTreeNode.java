package com.upd.hwcloud.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upd.hwcloud.bean.contains.OrgTreeNodeType;

import java.util.ArrayList;
import java.util.List;

public class ZTreeNode {

    private String id;

    private String pid;

    private String name;

    private String title;

    private boolean open;

    private boolean expand;

    @JsonProperty("isParent")
    private boolean isParent;

    private boolean loading;

    private String mobileWork;

    private String orgName;

    private String code;
    @JsonProperty("isPerson")
    private boolean isPerson;

    /**
     * {@link com.upd.hwcloud.bean.contains.OrgTreeNodeType}
     */
    private String nodeType;

    private List<ZTreeNode> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.title = name;
    }

    public String getTitle() {
        return title;
    }

    public List<ZTreeNode> getChildren() {
        return children;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
        this.expand = open;
    }

    public boolean isExpand() {
        return expand;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public boolean isLoading() {
        return loading;
    }

    public String getMobileWork() {
        return mobileWork;
    }

    public void setMobileWork(String mobileWork) {
        this.mobileWork = mobileWork;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public boolean isPerson() {
        return isPerson;
    }

    public void setPerson(boolean person) {
        isPerson = person;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(OrgTreeNodeType nodeType) {
        this.nodeType = nodeType.name();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
