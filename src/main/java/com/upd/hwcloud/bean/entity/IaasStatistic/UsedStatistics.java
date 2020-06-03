package com.upd.hwcloud.bean.entity.IaasStatistic;

import java.util.List;

/**
 * @Description: 统计实体
 * @author: yyc
 * @date: 2018-10-26 13:53
 **/
public class UsedStatistics {
    /**CPU*/
    private List<StatisticsUnit> cpu;
    /**内存*/
    private List<StatisticsUnit> mem;
    /**存储*/
    private List<StatisticsUnit> store;

    public List<StatisticsUnit> getCpu() {
        return cpu;
    }

    public void setCpu(List<StatisticsUnit> cpu) {
        this.cpu = cpu;
    }

    public List<StatisticsUnit> getMem() {
        return mem;
    }

    public void setMem(List<StatisticsUnit> mem) {
        this.mem = mem;
    }

    public List<StatisticsUnit> getStore() {
        return store;
    }

    public void setStore(List<StatisticsUnit> store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "UsedStatistics{" +
                "cpu=" + cpu +
                ", mem=" + mem +
                ", store=" + store +
                '}';
    }

}
