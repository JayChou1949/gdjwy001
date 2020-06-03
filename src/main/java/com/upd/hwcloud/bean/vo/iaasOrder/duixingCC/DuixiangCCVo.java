package com.upd.hwcloud.bean.vo.iaasOrder.duixingCC;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.upd.hwcloud.bean.vo.iaasOrder.IaasOrderVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 10:58 2019/11/1
 * @ Description：对象存储
 */
@Data
public class DuixiangCCVo extends IaasOrderVo implements Serializable{

    @Excel(name = "同一用户账号",orderNum = "15")
    private String tyyhAccount;

    @ExcelCollection(name = "申请配置信息",orderNum = "19")
    private List<DuixiangCCSqVo> sqVoList;

    @Excel(name = "账号",orderNum = "20")
    private String account;

    @Excel(name = "访问域名",orderNum = "21")
    private String domainName;

    @Excel(name = "访问IP",orderNum = "22")
    private String ip;

    @ExcelCollection(name = "实施配置信息",orderNum = "23")
    private List<DuixiangCCImplVo> implVoList;

    public String getTyyhAccount() {
        return tyyhAccount;
    }

    public void setTyyhAccount(String tyyhAccount) {
        this.tyyhAccount = tyyhAccount;
    }

    public List<DuixiangCCSqVo> getSqVoList() {
        return sqVoList;
    }

    public void setSqVoList(List<DuixiangCCSqVo> sqVoList) {
        this.sqVoList = sqVoList;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<DuixiangCCImplVo> getImplVoList() {
        return implVoList;
    }

    public void setImplVoList(List<DuixiangCCImplVo> implVoList) {
        this.implVoList = implVoList;
    }
}
