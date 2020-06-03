package com.upd.hwcloud.bean.param;

import lombok.Data;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 11:53 2019/11/9
 * @ Description：服务订阅、调用参数
 */
@Data
public class ServiceSubscribeParam extends PageParam {

    private String area;//地区

    private String policeCategory;//警种

    private Integer type;//维度 1：按服务被订阅统计 2：按应用订阅统计

}
