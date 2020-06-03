package com.upd.hwcloud.bean.entity;

import com.upd.hwcloud.bean.dto.FlowDto;

import java.util.List;

/**
 * @Description: 流程详情
 * @author: yyc
 * @date: 2018-10-13 14:52
 **/
public class WorkFlow {
    private List<FlowDto> rechecks;
    private List<FlowDto> kxchecks;
    private List<FlowDto> carrys;
    private List<FlowDto> feedbacks;

    public List<FlowDto> getRechecks() {
        return rechecks;
    }

    public void setRechecks(List<FlowDto> rechecks) {
        this.rechecks = rechecks;
    }

    public List<FlowDto> getKxchecks() {
        return kxchecks;
    }

    public void setKxchecks(List<FlowDto> kxchecks) {
        this.kxchecks = kxchecks;
    }

    public List<FlowDto> getCarrys() {
        return carrys;
    }

    public void setCarrys(List<FlowDto> carrys) {
        this.carrys = carrys;
    }

    public List<FlowDto> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FlowDto> feedbacks) {
        this.feedbacks = feedbacks;
    }

    @Override
    public String toString() {
        return "WorkFlow{" +
                "rechecks=" + rechecks +
                ", kxchecks=" + kxchecks +
                ", carrys=" + carrys +
                ", feedbacks=" + feedbacks +
                '}';
    }
}
