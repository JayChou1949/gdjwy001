package com.hirisun.cloud.model.iaas.vo;

import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;

public class IaasZysbExportVo extends IaasZysbVo {

	private static final long serialVersionUID = 9042505362596539889L;

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
    private List<IaasZysbDsjVo> bigdataList;

    @ExcelCollection(name = "数据库资源需求", orderNum = "101")
    private List<IaasZysbSjkVo> databaseList;

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

    public List<IaasZysbDsjVo> getBigdataList() {
        return bigdataList;
    }

    public void setBigdataList(List<IaasZysbDsjVo> bigdataList) {
        this.bigdataList = bigdataList;
    }

    public List<IaasZysbSjkVo> getDatabaseList() {
        return databaseList;
    }

    public void setDatabaseList(List<IaasZysbSjkVo> databaseList) {
        this.databaseList = databaseList;
    }

}
