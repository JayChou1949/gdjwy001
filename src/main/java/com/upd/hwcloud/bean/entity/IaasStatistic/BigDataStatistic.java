package com.upd.hwcloud.bean.entity.IaasStatistic;

import java.util.List;

/**
 * @Description:
 * @author: yyc
 * @date: 2018-10-28 14:22
 **/
public class BigDataStatistic {
    private List<BigDataUint> cpu;
    private List<BigDataUint> mem;
    private List<BigDataUint> store;

    public List<BigDataUint> getCpu() {
        return cpu;
    }

    public void setCpu(List<BigDataUint> cpu) {
        this.cpu = cpu;
    }

    public List<BigDataUint> getMem() {
        return mem;
    }

    public void setMem(List<BigDataUint> mem) {
        this.mem = mem;
    }

    public List<BigDataUint> getStore() {
        return store;
    }

    public void setStore(List<BigDataUint> store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "BigDataStatistic{" +
                "cpu=" + cpu +
                ", mem=" + mem +
                ", store=" + store +
                '}';
    }
}
