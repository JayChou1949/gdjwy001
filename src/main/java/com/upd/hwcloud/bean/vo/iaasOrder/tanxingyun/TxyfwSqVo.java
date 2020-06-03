package com.upd.hwcloud.bean.vo.iaasOrder.tanxingyun;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 12:01 2019/11/1
 * @ Description：操作时间
 */
@Data
public class TxyfwSqVo implements Serializable{

    @Excel(name = "网络",orderNum = "1")
    private String net;

    @Excel(name = "应用类型",orderNum = "2")
    private String appType;

    @Excel(name = "CPU(核)",orderNum = "3")
    private String cpu;

    @Excel(name = "内存(G)",orderNum = "4")
    private String ram;

    @Excel(name = "存储（G）",orderNum = "5")
    private String storage;

    @Excel(name = "申请数量",orderNum = "6")
    private String num;

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
