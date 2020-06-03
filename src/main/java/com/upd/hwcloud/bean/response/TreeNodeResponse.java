package com.upd.hwcloud.bean.response;

public class TreeNodeResponse {

    private String id;

    private String pid;

    private String text;

    private String rootNodeId;

    private String isLeaf;

    private String isTreeNode;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRootNodeId() {
        return rootNodeId;
    }

    public void setRootNodeId(String rootNodeId) {
        this.rootNodeId = rootNodeId;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getIsTreeNode() {
        return isTreeNode;
    }

    public void setIsTreeNode(String isTreeNode) {
        this.isTreeNode = isTreeNode;
    }
}
