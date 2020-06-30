package com.upd.hwcloud.bean.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.google.common.base.Converter;
import com.upd.hwcloud.bean.contains.ResourceRecoverStatus;
import com.upd.hwcloud.bean.entity.application.ResourceRecover;
import com.upd.hwcloud.bean.entity.application.SassAppUseStatistics;
import com.upd.hwcloud.bean.vo.resourceRecover.ResourceRecoverImport;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.DateUtil;
import com.upd.hwcloud.service.application.SassAppUseStatisticsService;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class ImportSaasAppUseStaticstics {

//    @Excel(name = "序号")
//    private String id;

    @Excel(name = "用户名")
    private String personname;

    @Excel(name = "身份证号码")
    private String personid;

    @Excel(name = "应用编号")
    private String appid;

    @Excel(name = "应用名称")
    private String appname;

    @Excel(name = "应用使用次数")
    private BigDecimal count;

    @Excel(name = "统计日期",importFormat = "yyyy-MM-dd")
    private Date addtime;

    public SassAppUseStatistics converterToSassAppUseStatisticsService(){
        ImportSaasAppUseStaticstics.SassAppUseStatisticsConverter converter = new ImportSaasAppUseStaticstics.SassAppUseStatisticsConverter();
        SassAppUseStatistics sassAppUseStatistics = converter.doForward(this);
        return sassAppUseStatistics;

    }


    private static class SassAppUseStatisticsConverter extends Converter<ImportSaasAppUseStaticstics,SassAppUseStatistics> {

        @Override
        protected SassAppUseStatistics doForward(ImportSaasAppUseStaticstics ImportSaasAppUseStaticstics) {
            SassAppUseStatistics sassAppUseStatistics = new SassAppUseStatistics();
            BeanUtils.copyProperties(ImportSaasAppUseStaticstics,sassAppUseStatistics);
//            Date date = new Date();
//            resourceRecover.setImportTime(date);
            sassAppUseStatistics.setAddtime(DateUtil.formateDate(ImportSaasAppUseStaticstics.getAddtime(),"yyyy-MM-dd"));
//            resourceRecover.setImportTimeStr(DateUtil.formateDate(date,"yyyy-MM-dd HH:mm"));
//            resourceRecover.setStatus(ResourceRecoverStatus.UN_PROCESSED.getCode());
//            resourceRecover.setShrinkTime(DateUtil.formateDate(resourceRecoverImport.getShrinkDate(),"yyyy-MM-dd HH:mm"));
            return sassAppUseStatistics;
        }

        @Override
        protected ImportSaasAppUseStaticstics doBackward(SassAppUseStatistics resourceRecover) {
            throw new BaseException("不支持逆向转化");
        }
    }
}
