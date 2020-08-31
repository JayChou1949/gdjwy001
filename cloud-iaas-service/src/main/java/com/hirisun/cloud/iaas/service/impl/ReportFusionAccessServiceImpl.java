package com.hirisun.cloud.iaas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.iaas.bean.ReportFusionAccess;
import com.hirisun.cloud.iaas.mapper.ReportFusionAccessMapper;
import com.hirisun.cloud.iaas.service.IReportFusionAccessService;
import com.hirisun.cloud.model.pass.vo.PaasReportVo;
import com.hirisun.cloud.model.system.ReportFusionAccessVo;

/**
 */
@Service
public class ReportFusionAccessServiceImpl extends ServiceImpl<ReportFusionAccessMapper, ReportFusionAccess> implements IReportFusionAccessService {


    @Autowired
    private ReportFusionAccessMapper reportFusionAccessMapper;
    /**
     * 统计数据
     *
     * @param appInfoId 申请单ID
     * @return 统计数据
     */
    @Override
    public List<ReportFusionAccessVo> doStatistics(String appInfoId) {
    	
    	List<ReportFusionAccess> statisticsData = reportFusionAccessMapper.getStatisticsData(appInfoId);
        return poToVo(statisticsData);
    }
    
    private List<ReportFusionAccessVo> poToVo(List<ReportFusionAccess> poList) {
		
		List<ReportFusionAccessVo> list = new ArrayList<ReportFusionAccessVo>();
		
		if(CollectionUtils.isNotEmpty(poList)) {
			
			poList.forEach(reportFusionAccess->{
				ReportFusionAccessVo vo = new ReportFusionAccessVo();
				BeanUtils.copyProperties(reportFusionAccess, vo);
				list.add(vo);
			});
		}
		return list;
		
	}
}
