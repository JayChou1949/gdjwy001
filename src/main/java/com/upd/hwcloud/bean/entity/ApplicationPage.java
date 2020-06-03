package com.upd.hwcloud.bean.entity;

import java.util.List;

/**
 * @Description:
 * @author: yyc
 * @date: 2018-10-19 10:31
 **/
public class ApplicationPage {
    private List<Application> applications;

    private String size;

    private String pages;

    private String current;

    private String total;

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ApplicationPage{" +
                "applications=" + applications +
                ", size=" + size +
                ", pages=" + pages +
                ", current=" + current +
                '}';
    }
}
