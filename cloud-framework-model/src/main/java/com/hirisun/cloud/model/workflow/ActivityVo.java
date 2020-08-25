package com.hirisun.cloud.model.workflow;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ActivityVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

        /**
     * 实例id
     */
    private String instanceid;

        /**
     * 流程定义环节id
     */
    private String modelid;

        /**
     * 创建时间
     */
    private Date createtime;

        /**
     * 创建人id
     */
    private String createpersonid;

        /**
     * 接收时间
     */
    private Date recivetime;

        /**
     * 处理时间
     */
    private Date handletime;

        /**
     * 处理人
     */
    private String handlepersonids;

        /**
     *  0:是  1:不是
     */
    private Integer isstart;

        /**
     * 直接上级环节
     */
    private String previousactivityids;

        /**
     * 环节状态
     */
    private String activitystatus;

        /**
     * 处理部门
     */
    private String handledepartment;

    private String activitytype;

    private String isreceve;
}
