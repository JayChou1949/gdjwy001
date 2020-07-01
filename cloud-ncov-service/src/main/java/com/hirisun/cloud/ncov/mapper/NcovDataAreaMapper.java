package com.hirisun.cloud.ncov.mapper;

import java.util.List;

import com.hirisun.cloud.model.ncov.dto.daas.CallAndNameDTO;
import com.hirisun.cloud.model.ncov.dto.daas.CallAndTimeDTO;
import com.hirisun.cloud.model.ncov.dto.daas.NcovDataLongDTO;


public interface NcovDataAreaMapper {

    Integer serviceCount();

    Integer policeCount();

    Integer areaCount();

    Long ncovPlatform();

    Long ncovServiceCall();

    Long yesterdayCall(String time);

    List<CallAndNameDTO> callByPolice();

    List<CallAndNameDTO> callByArea();

    List<CallAndTimeDTO> callAll(String time);

    List<CallAndTimeDTO> callByApp(String time);

    List<String> serviceAll();

    List<NcovDataLongDTO> serviceCallAll();

    List<NcovDataLongDTO> serviceCallLately7Days(String last7Time);

    List<NcovDataLongDTO> serviceCallYesterday(String time);

    List<NcovDataLongDTO> serviceOrder();
}
