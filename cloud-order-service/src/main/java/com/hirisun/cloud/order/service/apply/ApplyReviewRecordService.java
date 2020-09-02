package com.hirisun.cloud.order.service.apply;

import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务申请审核信息表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-12
 */
public interface ApplyReviewRecordService extends IService<ApplyReviewRecord> {
    ApplyReviewRecord getLastPassReviewInfoByAppInfoId(String id);

    List<ApplyReviewRecord> getAllReviewInfoByAppInfoId(String id);
}
