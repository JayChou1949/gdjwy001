package com.upd.hwcloud.bean.entity;

import java.util.List;

/**
 * @Description: 申请详情
 * @author: yyc
 * @date: 2018-10-15 10:11
 **/
public class ApplicationDetail {
    private ApplicationBaseInfo applicationBaseInfo;
    private List<BizApplication> appList;
    private WorkFlow workFlow;
    private List<ApplicationAction> actions;
    private WorkFlowInfo workFlowInfo;

    public WorkFlowInfo getWorkFlowInfo() {
        return workFlowInfo;
    }

    public void setWorkFlowInfo(WorkFlowInfo workFlowInfo) {
        this.workFlowInfo = workFlowInfo;
    }

    public List<ApplicationAction> getActions() {
        return actions;
    }

    public void setActions(List<ApplicationAction> actions) {
        this.actions = actions;
    }

    public ApplicationBaseInfo getApplicationBaseInfo() {
        return applicationBaseInfo;
    }

    public void setApplicationBaseInfo(ApplicationBaseInfo applicationBaseInfo) {
        this.applicationBaseInfo = applicationBaseInfo;
    }

    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public List<BizApplication> getAppList() {
        return appList;
    }

    public void setAppList(List<BizApplication> appList) {
        this.appList = appList;
    }

    @Override
    public String toString() {
        return "ApplicationDetail{" +
                "applicationBaseInfo=" + applicationBaseInfo +
                ", appList=" + appList +
                ", workFlow=" + workFlow +
                ", actions=" + actions +
                ", workFlowInfo=" + workFlowInfo +
                '}';
    }
}
