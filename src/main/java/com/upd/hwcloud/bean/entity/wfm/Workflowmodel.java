package com.upd.hwcloud.bean.entity.wfm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.upd.hwcloud.common.utils.UUIDUtil;

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

    @TableId(value = "ID",type = IdType.INPUT)
    private String id;

        /**
     * 流程id
     */
         @TableField("WORKFLOWID")
    private String workflowid;

        /**
     * 环节名称
     */
         @TableField("MODELNAME")
    private String modelname;

        /**
     * 下一环节id
     */
         @TableField("NEXTMODELIDS")
    private String nextmodelids;

        /**
     * 版本
     */
         @TableField("VERSION")
    private Integer version;

        /**
     * 0 是 1不是(是否开始环节) 
     */
         @TableField("ISSTART")
    private Integer isstart;

        /**
     * 0会签 1抢占
     */
         @TableField("HANDLEMODEL")
    private Integer handlemodel;

        /**
     * 默认处理人
     */
         @TableField("DEFAULTHANDLEPERSON")
    private String defaulthandleperson;

    @TableField("MODELSTATUSCLASSNME")
    private String modelstatusclassnme;

        /**
     * 是否线上
     */
         @TableField("ISONLINE")
    private Integer isonline;

        /**
     * 通知人
     */
         @TableField("NOTICEPERON")
    private String noticeperon;

        /**
     * 参与人
     */
         @TableField("ADVISERPERSON")
    private String adviserperson;

        /**
     * 前序通知环节
     */
         @TableField("PRENOTICEACT")
    private String prenoticeact;
    public Workflowmodel(){

    }
    public Workflowmodel(String workflowid,String name,String nextmodelids) {
        this.id= UUIDUtil.getUUID();
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
