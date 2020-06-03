package com.upd.hwcloud.service.report.impl;

import com.upd.hwcloud.bean.entity.report.ReportPaas;
import com.upd.hwcloud.dao.report.ReportPaasMapper;
import com.upd.hwcloud.service.report.IReportPaasService;
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
public class ReportPaasServiceImpl extends ServiceImpl<ReportPaasMapper, ReportPaas> implements IReportPaasService {

    @Autowired
    private ReportPaasMapper reportPaasMapper;

    /**
     * 统计数据
     *
     * @param appInfoId 申请单ID
     * @return 统计数据
     */
    @Override
    public List<ReportPaas> doStatistics(String appInfoId) {
        return reportPaasMapper.getStatisticsData(appInfoId);
    }
}
