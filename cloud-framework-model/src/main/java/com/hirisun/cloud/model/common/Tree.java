package com.hirisun.cloud.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wuxiaoxing 2020-07-29
 * 树形结构对象
 */
@ApiModel(value="树形结构对象", description="树形结构对象")
public class Tree<T> {
    private String id;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "父级")
    private String pid;

    @ApiModelProperty(value = "子集")
    private List<T> children;

    public String getId() {
        return id;
    }

    public Integer getSort() {
        return sort;
    }

    public String getPid() {
        return pid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
