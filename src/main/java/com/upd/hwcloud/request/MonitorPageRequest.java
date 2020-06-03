package com.upd.hwcloud.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 接口监控分页请求
 */
@ApiModel("接口监控分页请求")
public class MonitorPageRequest {

    @ApiModelProperty("页数")
    private Integer pageNum;
    @ApiModelProperty("每页条数")
    private Integer pageSize;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("关键字")
    private String keyWords;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    @Override
    public String toString() {
        return "MonitorPageRequest{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", status='" + status + '\'' +
                ", keyWords='" + keyWords + '\'' +
                '}';
    }
}
