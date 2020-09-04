package com.hirisun.cloud.iaas.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.iaas.bean.IaasReport;
import com.hirisun.cloud.iaas.mapper.IaasReportMapper;
import com.hirisun.cloud.iaas.service.IIaasReportService;
import com.hirisun.cloud.model.iaas.vo.IaasReportVo;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yyc
 * @since 2020-05-19
 */
@Service
public class IaasReportServiceImpl extends ServiceImpl<IaasReportMapper, IaasReport> implements IIaasReportService {

    @Autowired
    private IaasReportMapper iaasReportMapper;

    /**
     * 统计数据
     *
     * @param appInfoId 申请单ID
     * @return 统计数据
     */
    @Override
    public List<IaasReportVo> doStatistics(String appInfoId) {
    	
    	List<IaasReport> iaasList = iaasReportMapper.getStatisticsData(appInfoId);
    	return poToVo(iaasList);
    }

	@Override
	public List<IaasReportVo> findIaasReportByAppInfoId(String appInfoId) {
		
		List<IaasReport> iaasList =  iaasReportMapper.selectList(new QueryWrapper<IaasReport>().lambda()
        		.eq(IaasReport::getAppInfoId,appInfoId));
		
		if(CollectionUtils.isNotEmpty(iaasList)) {
			iaasList = iaasList.parallelStream()
	    			.filter(iaas->StringUtils.equals(iaas.getResourceName(),"弹性云服务器")).collect(Collectors.toList());
		}
		
		return poToVo(iaasList);
	}

	@Override
	public List<IaasReportVo> getListByApplyId(String appInfoId) {
		List<IaasReport> iaasList =  iaasReportMapper.selectList(new QueryWrapper<IaasReport>().lambda()
				.eq(IaasReport::getAppInfoId,appInfoId).orderByDesc(Re));

		if(CollectionUtils.isNotEmpty(iaasList)) {
			iaasList = iaasList.parallelStream()
					.filter(iaas->StringUtils.equals(iaas.getResourceName(),"弹性云服务器")).collect(Collectors.toList());
		}

		return poToVo(iaasList);
	}

	private List<IaasReportVo> poToVo(List<IaasReport> iaasList) {
		
		List<IaasReportVo> list = new ArrayList<IaasReportVo>();
		
		if(CollectionUtils.isNotEmpty(iaasList)) {
			
			iaasList.forEach(iaasReport->{
				IaasReportVo vo = new IaasReportVo();
				BeanUtils.copyProperties(iaasReport, vo);
				list.add(vo);
			});
		}
		return list;
		
	}
}
