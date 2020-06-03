package com.upd.hwcloud.bean.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.upd.hwcloud.bean.entity.IaasZysb;
import com.upd.hwcloud.bean.entity.IaasZysbDsj;
import com.upd.hwcloud.bean.entity.IaasZysbSjk;

import java.util.List;

public class IaasZysbExport extends IaasZysb {


    @Excel(name = "CPU(核)", type = 10, groupName = "警务云资源需求")
    private Double cpu;

    /**
     * 内存
     */
    @Excel(name = "内存(GB)", type = 10, groupName = "警务云资源需求")
    private Double memorys;

    /**
     * 存储
     */
    @Excel(name = "存储(TB)", type = 10, groupName = "警务云资源需求")
    private Double storages;

    @ExcelCollection(name = "大数据资源需求", orderNum = "100")
    private List<IaasZysbDsj> bigdataList;

    @ExcelCollection(name = "数据库资源需求", orderNum = "101")
    private List<IaasZysbSjk> databaseList;

    public Double getCpu() {
        return cpu;
    }

    public void setCpu(Double cpu) {
        this.cpu = cpu;
    }

    public Double getMemorys() {
        return memorys;
    }

    public void setMemorys(Double memorys) {
        this.memorys = memorys;
    }

    public Double getStorages() {
        return storages;
    }

    public void setStorages(Double storages) {
        this.storages = storages;
    }

    public List<IaasZysbDsj> getBigdataList() {
        return bigdataList;
    }

    public void setBigdataList(List<IaasZysbDsj> bigdataList) {
        this.bigdataList = bigdataList;
    }

    public List<IaasZysbSjk> getDatabaseList() {
        return databaseList;
    }

    public void setDatabaseList(List<IaasZysbSjk> databaseList) {
        this.databaseList = databaseList;
    }

}
