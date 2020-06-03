package com.upd.hwcloud.service.report.impl;

import com.upd.hwcloud.bean.entity.report.ReportFusionAccess;
import com.upd.hwcloud.dao.report.ReportFusionAccessMapper;
import com.upd.hwcloud.service.report.IReportFusionAccessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yyc
 * @since 2020-05-19
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
    public List<ReportFusionAccess> doStatistics(String appInfoId) {
        return reportFusionAccessMapper.getStatisticsData(appInfoId);
    }
}
