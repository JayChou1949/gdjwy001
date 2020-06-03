package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.dto.YqExcelDto;
import com.upd.hwcloud.bean.dto.YqServiceDetail;
import com.upd.hwcloud.bean.dto.YqStatistics;
import com.upd.hwcloud.bean.dto.apig.ServiceReturnBean;
import com.upd.hwcloud.bean.entity.Bigdata;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.param.PageParam;
import com.upd.hwcloud.bean.param.ServiceIssueParam;
import com.upd.hwcloud.bean.param.ServiceSubscribeParam;
import com.upd.hwcloud.bean.vo.daasService.ServiceIssueVo;
import com.upd.hwcloud.bean.vo.daasService.ServiceQualityVo;
import com.upd.hwcloud.bean.vo.daasService.ServiceRequestVo;
import com.upd.hwcloud.bean.vo.daasService.ServiceSubscribeVo;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 大数据库服务目录 服务类
 * </p>
 *
 * @author huru
 * @since 2018-12-26
 */
public interface IBigdataService extends IService<Bigdata> {

    Page<Bigdata> getPage(Page<Bigdata> page, String name, String dataFrom, String collectionUnit, String category);

    List<Bigdata> getList(String name, String dataFrom, String collectionUnit, String category);

    List getColumnConfig();

    void saveColumnConfig(List config);

    void resetColumnConfig();

    /**************************DaaS服务统计******************************/
    /**
     * 按发布统计
     * @return
     */
    Page<ServiceIssueVo> serviceStatisticsByIssue(ServiceIssueParam param);

    /**
     * 按订阅统计
     * @return
     */
    Page<ServiceSubscribeVo> serviceStatisticsBySubscribe(ServiceSubscribeParam param);

    /**
     * 按调用统计
     * @return
     */
    Page<ServiceRequestVo> serviceStatisticsByRequest(ServiceSubscribeParam param);

    /**
     * 按质量统计
     * @return
     */
    Page<ServiceQualityVo> serviceStatisticsByQuality(PageParam param);

    boolean servicePublish2DaaS(ServicePublish servicePublish,ServiceReturnBean returnBean);

    List<YqServiceDetail> yqServiceDetail(String label,String serviceName,String appName,Integer page,Integer size);

    List<YqStatistics> areaOrder(String label);

    List<YqStatistics> policeOrder(String label);

    List<YqStatistics> appOrder(String label);

    List<YqStatistics> serviceByOrder(String label);

    Integer yqdy(String label, String serviceName, String appName);
}
