package com.hirisun.cloud.paas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.model.pass.vo.PaasReportVo;
import com.hirisun.cloud.paas.bean.PaasReport;
import com.hirisun.cloud.paas.mapper.PaasReportMapper;
import com.hirisun.cloud.paas.service.IPaasReportService;

@Service
public class PaasReportServiceImpl extends ServiceImpl<PaasReportMapper, PaasReport> implements IPaasReportService {

    @Autowired
    private PaasReportMapper reportPaasMapper;

    /**
     * 统计数据
     *
     * @param appInfoId 申请单ID
     * @return 统计数据
     */
    @Override
    public List<PaasReportVo> doStatistics(String appInfoId) {
    	List<PaasReport> statisticsData = reportPaasMapper.getStatisticsData(appInfoId);
        return poToVo(statisticsData);
    }

	@Override
	public List<PaasReportVo> findPaasReportByAppInfoId(String appInfoId) {
		
		List<PaasReportVo> voList = new ArrayList<PaasReportVo>();
		
		List<PaasReport> paasList = reportPaasMapper.selectList(new QueryWrapper<PaasReport>().lambda()
				.eq(PaasReport::getAppInfoId,appInfoId).orderByDesc(PaasReport::getModifiedTime));
		
		if(CollectionUtils.isNotEmpty(paasList)) {
			paasList.forEach(paasReport->{
				PaasReportVo vo = new PaasReportVo();
				BeanUtils.copyProperties(paasReport, vo);
				voList.add(vo);
			});
		}
		
		return voList;
	}

	private List<PaasReportVo> poToVo(List<PaasReport> paasList) {
		
		List<PaasReportVo> list = new ArrayList<PaasReportVo>();
		
		if(CollectionUtils.isNotEmpty(paasList)) {
			
			paasList.forEach(paasReport->{
				PaasReportVo vo = new PaasReportVo();
				BeanUtils.copyProperties(paasReport, vo);
				list.add(vo);
			});
		}
		return list;
		
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public void remove(String appInfoId) {
		
		this.remove(new QueryWrapper<PaasReport>().lambda()
                .eq(PaasReport::getAppInfoId, appInfoId));
		
	}

	@Transactional(rollbackFor = Throwable.class)
	public void saveBatch(List<PaasReportVo> voList) {
		if(CollectionUtils.isNotEmpty(voList)) {
			
			List<PaasReport> list = new ArrayList<PaasReport>();
			voList.forEach(vo->{
				PaasReport paasReport = new PaasReport();
				BeanUtils.copyProperties(vo, paasReport);
				list.add(paasReport);
			});
			this.saveBatch(list);
		}
		
	}

}
