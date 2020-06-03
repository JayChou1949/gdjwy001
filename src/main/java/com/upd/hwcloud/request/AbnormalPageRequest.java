package com.upd.hwcloud.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 异常记录分页请求
 */
@ApiModel("异常记录分页请求")
public class AbnormalPageRequest {

    @ApiModelProperty("页数")
    private Integer pageNum;
    @ApiModelProperty("每页条数")
    private Integer pageSize;
    @ApiModelProperty("关键字")
    private String keywords;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "AbnormalPageRequest{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}
