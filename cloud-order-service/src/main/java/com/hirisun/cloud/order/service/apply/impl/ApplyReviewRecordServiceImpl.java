package com.hirisun.cloud.order.service.apply.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.mapper.apply.ApplyReviewRecordMapper;
import com.hirisun.cloud.order.service.apply.ApplyReviewRecordService;

import java.util.List;

/**
 * <p>
 * 服务申请审核信息表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-12
 */
@Service
public class ApplyReviewRecordServiceImpl extends ServiceImpl<ApplyReviewRecordMapper, ApplyReviewRecord> implements ApplyReviewRecordService {
    @Autowired
    private ApplyReviewRecordMapper applyReviewRecordMapper;
    @Override
    public ApplyReviewRecord getLastPassReviewInfoByAppInfoId(String id) {
        return applyReviewRecordMapper.getLastPassReviewInfoByAppInfoId(id);
    }

    @Override
    public List<ApplyReviewRecord> getAllReviewInfoByAppInfoId(String id) {
        List<ApplyReviewRecord> reviewList= this.list(new QueryWrapper<ApplyReviewRecord>().lambda()
                .eq(ApplyReviewRecord::getApplyId, id)
                .orderByDesc(ApplyReviewRecord::getCreateTime));
        return reviewList;
    }
}
