package com.upd.hwcloud.bean.vo.ncov;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.sound.sampled.Line;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author wuc
 * @date 2020/3/3
 */
@Data
public class NcovClusterAppVo {


    private String appName;


    private Integer cpu;

    private Double memory;

    private Double storage;

    private LinkedHashMap<String,Double> cpuRecent;

    private LinkedHashMap<String,Double> memRecent;
}
