package com.hirisun.cloud.model.workflow;

import java.util.ArrayList;
import java.util.List;

import com.hirisun.cloud.model.service.AppReviewInfoVo;

public class FlowDetailVo {

    /**
     * 部门内审核
     */
    private List<AppReviewInfoVo> inner = new ArrayList<>();

    /**
     * 科信审核
     */
    private List<AppReviewInfoVo> kx = new ArrayList<>();

    /**
     * 实施
     */
    private List<AppReviewInfoVo> impl = new ArrayList<>();

    /**
     * 服务台复核
     */
    private List<AppReviewInfoVo> fwtfh = new ArrayList<>();

    /**
     * 反馈
     */
    private List<ApplicationFeedbackVo> feedback = new ArrayList<>();

    public List<AppReviewInfoVo> getInner() {
        return inner;
    }

    public List<AppReviewInfoVo> getKx() {
        return kx;
    }

    public List<AppReviewInfoVo> getImpl() {
        return impl;
    }

    public List<AppReviewInfoVo> getFwtfh() {
        return fwtfh;
    }

    public List<ApplicationFeedbackVo> getFeedback() {
        return feedback;
    }

}
