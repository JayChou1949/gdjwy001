package com.hirisun.cloud.order.bean.workflow;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hirisun.cloud.common.util.UUIDUtil;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zwb
 * @since 2019-04-10
 */
@TableName("TB_WFM_WORKFLOWMODEL")
public class Workflowmodel extends Model<Workflowmodel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private String id;

    /**
     * 流程id
     */
    @TableField("WORKFLOWID")
    @ApiModelProperty("流程id")
    private String workflowid;

    /**
     * 环节名称
     */
    @TableField("MODELNAME")
    @ApiModelProperty("环节名称")
    private String modelname;

    /**
     * 下一环节id
     */
    @TableField("NEXTMODELIDS")
    @ApiModelProperty("下一环节id")
    private String nextmodelids;

    /**
     * 版本
     */
    @TableField("VERSION")
    @ApiModelProperty("版本")
    private Integer version;

    /**
     * 0 是 1不是(是否开始环节)
     */
    @TableField("ISSTART")
    @ApiModelProperty("0 是 1不是(是否开始环节) ")
    private Integer isstart;

    /**
     * 0会签 1抢占
     */
    @TableField("HANDLEMODEL")
    @ApiModelProperty("0会签 1抢占")
    private Integer handlemodel;

    /**
     * 默认处理人
     */
    @TableField("DEFAULTHANDLEPERSON")
    @ApiModelProperty("默认处理人")
    private String defaulthandleperson;

    @TableField("MODELSTATUSCLASSNME")
    @ApiModelProperty("环节状态名")
    private String modelstatusclassnme;

    /**
     * 是否线上
     */
    @TableField("ISONLINE")
    @ApiModelProperty("是否线上")
    private Integer isonline;

    /**
     * 通知人
     */
    @TableField("NOTICEPERON")
    @ApiModelProperty("通知人")
    private String noticeperon;

    /**
     * 参与人
     */
    @TableField("ADVISERPERSON")
    @ApiModelProperty("参与人")
    private String adviserperson;

    /**
     * 前序通知环节
     */
    @TableField("PRENOTICEACT")
    @ApiModelProperty("前序通知环节")
    private String prenoticeact;

    /**
     * 环节类型,判断是系统环节或者自定义环节（0系统环节 1自定义环节 2业务办理环节）
     * 旧流程配置环节数据为空
     */
    @TableField("MODELTYPE")
    @ApiModelProperty("环节类型,判断是系统环节或者自定义环节（0系统环节 1自定义环节 2业务办理环节）")
    private Integer modeltype;

    public Workflowmodel() {

    }

    public Workflowmodel(String workflowid, String name, String nextmodelids) {
        this.id = UUIDUtil.getUUID();
        this.workflowid = workflowid;
        this.modelname = name;
        this.nextmodelids = nextmodelids;

    }


    public String getId() {
        return id;
    }

    public Workflowmodel setId(String id) {
        this.id = id;
        return this;
    }

    public String getWorkflowid() {
        return workflowid;
    }

    public Workflowmodel setWorkflowid(String workflowid) {
        this.workflowid = workflowid;
        return this;
    }

    public String getModelname() {
        return modelname;
    }

    public Workflowmodel setModelname(String modelname) {
        this.modelname = modelname;
        return this;
    }

    public String getNextmodelids() {
        return nextmodelids;
    }

    public Workflowmodel setNextmodelids(String nextmodelids) {
        this.nextmodelids = nextmodelids;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public Workflowmodel setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public Integer getIsstart() {
        return isstart;
    }

    public Workflowmodel setIsstart(Integer isstart) {
        this.isstart = isstart;
        return this;
    }

    public Integer getHandlemodel() {
        return handlemodel;
    }

    public Workflowmodel setHandlemodel(Integer handlemodel) {
        this.handlemodel = handlemodel;
        return this;
    }

    public String getDefaulthandleperson() {
        return defaulthandleperson;
    }

    public Workflowmodel setDefaulthandleperson(String defaulthandleperson) {
        this.defaulthandleperson = defaulthandleperson;
        return this;
    }

    public String getModelstatusclassnme() {
        return modelstatusclassnme;
    }

    public Workflowmodel setModelstatusclassnme(String modelstatusclassnme) {
        this.modelstatusclassnme = modelstatusclassnme;
        return this;
    }

    public Integer getIsonline() {
        return isonline;
    }

    public Workflowmodel setIsonline(Integer isonline) {
        this.isonline = isonline;
        return this;
    }

    public String getNoticeperon() {
        return noticeperon;
    }

    public Workflowmodel setNoticeperon(String noticeperon) {
        this.noticeperon = noticeperon;
        return this;
    }

    public String getAdviserperson() {
        return adviserperson;
    }

    public Workflowmodel setAdviserperson(String adviserperson) {
        this.adviserperson = adviserperson;
        return this;
    }

    public String getPrenoticeact() {
        return prenoticeact;
    }

    public Workflowmodel setPrenoticeact(String prenoticeact) {
        this.prenoticeact = prenoticeact;
        return this;
    }

    public Integer getModeltype() {
        return modeltype;
    }

    public void setModeltype(Integer modeltype) {
        this.modeltype = modeltype;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Workflowmodel{" +
                "id=" + id +
                ", workflowid=" + workflowid +
                ", modelname=" + modelname +
                ", nextmodelids=" + nextmodelids +
                ", version=" + version +
                ", isstart=" + isstart +
                ", handlemodel=" + handlemodel +
                ", defaulthandleperson=" + defaulthandleperson +
                ", modelstatusclassnme=" + modelstatusclassnme +
                ", isonline=" + isonline +
                ", noticeperon=" + noticeperon +
                ", adviserperson=" + adviserperson +
                ", prenoticeact=" + prenoticeact +
                "}";
    }
}
