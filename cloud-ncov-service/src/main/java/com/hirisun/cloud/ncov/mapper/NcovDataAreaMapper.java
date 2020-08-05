package com.hirisun.cloud.ncov.mapper;

import java.util.List;

import com.hirisun.cloud.model.ncov.vo.daas.CallAndNameVo;
import com.hirisun.cloud.model.ncov.vo.daas.CallAndTimeVo;
import com.hirisun.cloud.model.ncov.vo.daas.NcovDataLongVo;


public interface NcovDataAreaMapper {

    Integer serviceCount();

    Integer policeCount();

    Integer areaCount();

    Long ncovPlatform();

    Long ncovServiceCall();

    Long yesterdayCall(String time);

    List<CallAndNameVo> callByPolice();

    List<CallAndNameVo> callByArea();

    List<CallAndTimeVo> callAll(String time);

    List<CallAndTimeVo> callByApp(String time);

    List<String> serviceAll();

    List<NcovDataLongVo> serviceCallAll();

    List<NcovDataLongVo> serviceCallLately7Days(String last7Time);

    List<NcovDataLongVo> serviceCallYesterday(String time);

    List<NcovDataLongVo> serviceOrder();
}
