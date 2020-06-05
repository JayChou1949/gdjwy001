package com.upd.hwcloud.bean.vo.wfm;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yyc
 * @date 2020/6/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeAndModelName {

    private Date createTime;

    private String modelName;
}
