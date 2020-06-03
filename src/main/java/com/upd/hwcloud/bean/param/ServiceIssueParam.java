package com.upd.hwcloud.bean.param;

import lombok.Data;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 11:53 2019/11/9
 * @ Description：服务发布参数
 */
@Data
public class ServiceIssueParam extends PageParam {

    private String area;//地区

    private String policeCategory;//警种

}
