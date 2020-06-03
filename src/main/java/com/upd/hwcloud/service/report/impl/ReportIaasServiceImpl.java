package com.upd.hwcloud.service.report.impl;

import com.upd.hwcloud.bean.entity.report.ReportIaas;
import com.upd.hwcloud.dao.report.ReportIaasMapper;
import com.upd.hwcloud.service.report.IReportIaasService;
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
public class ReportIaasServiceImpl extends ServiceImpl<ReportIaasMapper, ReportIaas> implements IReportIaasService {

    @Autowired
    private ReportIaasMapper reportIaasMapper;

    /**
     * 统计数据
     *
     * @param appInfoId 申请单ID
     * @return 统计数据
     */
    @Override
    public List<ReportIaas> doStatistics(String appInfoId) {
        return reportIaasMapper.getStatisticsData(appInfoId);
    }
}
