package com.upd.hwcloud.bean.dto;

import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.application.ApplicationFeedback;

import java.util.ArrayList;
import java.util.List;

public class FlowDetail {

    /**
     * 部门内审核
     */
    private List<AppReviewInfo> inner = new ArrayList<>();

    /**
     * 科信审核
     */
    private List<AppReviewInfo> kx = new ArrayList<>();

    /**
     * 实施
     */
    private List<AppReviewInfo> impl = new ArrayList<>();

    /**
     * 服务台复核
     */
    private List<AppReviewInfo> fwtfh = new ArrayList<>();

    /**
     * 反馈
     */
    private List<ApplicationFeedback> feedback = new ArrayList<>();

    public List<AppReviewInfo> getInner() {
        return inner;
    }

    public List<AppReviewInfo> getKx() {
        return kx;
    }

    public List<AppReviewInfo> getImpl() {
        return impl;
    }

    public List<AppReviewInfo> getFwtfh() {
        return fwtfh;
    }

    public List<ApplicationFeedback> getFeedback() {
        return feedback;
    }

}
