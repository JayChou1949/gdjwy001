package com.upd.hwcloud.bean.entity.IaasStatistic;

import com.upd.hwcloud.bean.entity.IaasTotal;
import com.upd.hwcloud.bean.entity.Iaasbigdata;
import com.upd.hwcloud.bean.entity.Iaasdistrubite;

import java.util.List;

/**
 * @Description:
 * @author: yyc
 * @date: 2018-10-28 13:04
 **/
public class Data {
    private IaasTotal iaas;
    /**应用资源Top5*/
    private UsedStatistics appTop5;
    /**应用资源low5*/
    private UsedStatistics appLow5;
    /**警种Top5*/
    private UsedStatistics policeTop5;
    /**警种Low5*/
    private UsedStatistics policeLow5;
    /**libra集群数据统计*/
    private List<Iaasbigdata> libras;
    private List<Iaasbigdata> hds;
    private List<Iaasdistrubite> department;


    public List<Iaasbigdata> getLibras() {
        return libras;
    }

    public void setLibras(List<Iaasbigdata> libras) {
        this.libras = libras;
    }

    public List<Iaasbigdata> getHds() {
        return hds;
    }

    public void setHds(List<Iaasbigdata> hds) {
        this.hds = hds;
    }

    public List<Iaasdistrubite> getDepartment() {
        return department;
    }

    public void setDepartment(List<Iaasdistrubite> department) {
        this.department = department;
    }

    public IaasTotal getIaas() {
        return iaas;
    }

    public void setIaas(IaasTotal iaas) {
        this.iaas = iaas;
    }

    public UsedStatistics getAppTop5() {
        return appTop5;
    }

    public void setAppTop5(UsedStatistics appTop5) {
        this.appTop5 = appTop5;
    }

    public UsedStatistics getAppLow5() {
        return appLow5;
    }

    public void setAppLow5(UsedStatistics appLow5) {
        this.appLow5 = appLow5;
    }

    public UsedStatistics getPoliceTop5() {
        return policeTop5;
    }

    public void setPoliceTop5(UsedStatistics policeTop5) {
        this.policeTop5 = policeTop5;
    }

    public UsedStatistics getPoliceLow5() {
        return policeLow5;
    }

    public void setPoliceLow5(UsedStatistics policeLow5) {
        this.policeLow5 = policeLow5;
    }

    @Override
    public String toString() {
        return "Data{" +
                "iaas=" + iaas +
                ", appTop5=" + appTop5 +
                ", appLow5=" + appLow5 +
                ", policeTop5=" + policeTop5 +
                ", policeLow5=" + policeLow5 +
                ", libras=" + libras +
                ", hds=" + hds +
                ", department=" + department +
                '}';
    }
}
