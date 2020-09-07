package com.hirisun.cloud.daas.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.daas.bean.Bigdata;
import com.hirisun.cloud.model.daas.vo.ServiceIssueVo;
import com.hirisun.cloud.model.daas.vo.ServiceQualityVo;
import com.hirisun.cloud.model.daas.vo.ServiceRequestVo;
import com.hirisun.cloud.model.daas.vo.ServiceSubscribeVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 大数据库服务目录 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-02
 */
public interface BigdataMapper extends BaseMapper<Bigdata> {
	
List<ServiceIssueVo> serviceStatisticsByIssue(Page page, @Param("ew") Wrapper<Bigdata> wrapper);
	
	List<ServiceSubscribeVo> serviceStatisticsBySubscribe(Page page, @Param("ew") Wrapper<Bigdata> wrapper);
	
    List<ServiceRequestVo> serviceStatisticsByRequest(Page page, @Param("ew") Wrapper<Bigdata> wrapper);

    List<ServiceRequestVo> appStatisticsByRequest(Page page, @Param("ew") Wrapper<Bigdata> wrapper);

    List<ServiceQualityVo> serviceStatisticsByQuality(Page page, @Param("ew") Wrapper<Bigdata> wrapper);

	
    Page<Bigdata> getPage(Page<Bigdata> page, @Param("name") String name, @Param("dataFrom") String dataFrom,
                          @Param("collectionUnit") String collectionUnit, @Param("category") String category);
}
