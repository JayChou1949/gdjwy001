package com.upd.hwcloud.dao.application;

import com.upd.hwcloud.bean.dto.cov.CallAndNameDto;
import com.upd.hwcloud.bean.dto.cov.CallAndTimeDto;
import com.upd.hwcloud.bean.dto.cov.NcovDataLongDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author junglefisher
 * @date 2020/5/5 17:52
 */
@Repository
public interface NcovDataAreaMapper {

    Integer serviceCount();

    Integer policeCount();

    Integer areaCount();

    Long ncovPlatform();

    Long ncovServiceCall();

    Long yesterdayCall(String time);

    List<CallAndNameDto> callByPolice();

    List<CallAndNameDto> callByArea();

    List<CallAndTimeDto> callAll(String time);

    List<CallAndTimeDto> callByApp(String time);

    List<String> serviceAll();

    List<NcovDataLongDto> serviceCallAll();

    List<NcovDataLongDto> serviceCallLately7Days(String last7Time);

    List<NcovDataLongDto> serviceCallYesterday(String time);

    List<NcovDataLongDto> serviceOrder();
}
