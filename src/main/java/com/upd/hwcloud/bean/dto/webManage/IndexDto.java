package com.upd.hwcloud.bean.dto.webManage;

import com.upd.hwcloud.bean.entity.webManage.*;

import java.util.List;

public class IndexDto {

    private List<Case> cases;
    private List<Apply> applies;
    private List<Carousel> carousels;
    private List<News> newsList;
    private List<PoliceClass> policeClasses;

    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }

    public List<Apply> getApplies() {
        return applies;
    }

    public void setApplies(List<Apply> applies) {
        this.applies = applies;
    }

    public List<Carousel> getCarousels() {
        return carousels;
    }

    public void setCarousels(List<Carousel> carousels) {
        this.carousels = carousels;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public List<PoliceClass> getPoliceClasses() {
        return policeClasses;
    }

    public void setPoliceClasses(List<PoliceClass> policeClasses) {
        this.policeClasses = policeClasses;
    }
}
