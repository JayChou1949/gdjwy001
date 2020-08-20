package com.hirisun.cloud.model.workflow;

import java.io.Serializable;
import java.util.Date;

import com.hirisun.cloud.model.user.UserVO;


public class ApplicationFeedbackVo implements Serializable {

	private static final long serialVersionUID = 7771498164129366341L;

	private String id;

        /**
     * 审批人
     */
    private String creator;

    private Date createTime;

    private Date modifiedTime;

        /**
     * 结果 0:未完成 1:完成
     */
    private String result;

    private String remark;

        /**
     * 服务信息id
     */
    private String appInfoId;

        /**
     * 满意度
     */
    private String score;

    private UserVO user;

    public String getId() {
        return id;
    }

    public ApplicationFeedbackVo setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public ApplicationFeedbackVo setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ApplicationFeedbackVo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ApplicationFeedbackVo setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getResult() {
        return result;
    }

    public ApplicationFeedbackVo setResult(String result) {
        this.result = result;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ApplicationFeedbackVo setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public ApplicationFeedbackVo setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getScore() {
        return score;
    }

    public ApplicationFeedbackVo setScore(String score) {
        this.score = score;
        return this;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

}
