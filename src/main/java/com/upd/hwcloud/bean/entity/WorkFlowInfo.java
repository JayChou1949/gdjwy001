package com.upd.hwcloud.bean.entity;

import java.util.List;

/**
 * @Description:
 * @author: yyc
 * @date: 2018-10-16 13:38
 **/
public class WorkFlowInfo {
    private List<BizApprove> reviews;

    private List<BizApprove> kxLeaderApproves;

    private List<BizApprove> carryouts;

    private List<BizApprove> feedBacks;

    public List<BizApprove> getReviews() {
        return reviews;
    }

    public void setReviews(List<BizApprove> reviews) {
        this.reviews = reviews;
    }

    public List<BizApprove> getKxLeaderApproves() {
        return kxLeaderApproves;
    }

    public void setKxLeaderApproves(List<BizApprove> kxLeaderApproves) {
        this.kxLeaderApproves = kxLeaderApproves;
    }

    public List<BizApprove> getCarryouts() {
        return carryouts;
    }

    public void setCarryouts(List<BizApprove> carryouts) {
        this.carryouts = carryouts;
    }

    public List<BizApprove> getFeedBacks() {
        return feedBacks;
    }

    public void setFeedBacks(List<BizApprove> feedBacks) {
        this.feedBacks = feedBacks;
    }

    @Override
    public String toString() {
        return "WorkFlowInfo{" +
                "reviews=" + reviews +
                ", kxLeaderApproves=" + kxLeaderApproves +
                ", carryouts=" + carryouts +
                ", feedBacks=" + feedBacks +
                '}';
    }
}
