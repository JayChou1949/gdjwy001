package com.upd.hwcloud.bean.dto;

import com.upd.hwcloud.bean.entity.FlowStep;

import java.io.Serializable;
import java.util.List;

public class EditStepDTO implements Serializable {

    private String id;

    private List<FlowStep> stepList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FlowStep> getStepList() {
        return stepList;
    }

    public void setStepList(List<FlowStep> stepList) {
        this.stepList = stepList;
    }

}
