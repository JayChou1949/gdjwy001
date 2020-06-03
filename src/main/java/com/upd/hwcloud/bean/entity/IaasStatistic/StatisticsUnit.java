package com.upd.hwcloud.bean.entity.IaasStatistic;

/**
 * @Description: 统计基本单位
 * @author: yyc
 * @date: 2018-10-26 13:48
 **/
public class StatisticsUnit {
    private String name;
    private Float value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "StatisticsUnit{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
