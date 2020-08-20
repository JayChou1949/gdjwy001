package com.hirisun.cloud.model.export.vo;

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
public class ExportActVo {

    private String Id;

    private String previousactivityids;

    private Date createTime;

    private String modelName;

    private String activitystatus;
}
