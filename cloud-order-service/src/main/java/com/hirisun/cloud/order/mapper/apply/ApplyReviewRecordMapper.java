package com.hirisun.cloud.order.mapper.apply;

import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 服务申请审核信息表 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-12
 */
public interface ApplyReviewRecordMapper extends BaseMapper<ApplyReviewRecord> {
    /**
     * 根据服务申请信息 id 获取最近一条通过的审核记录
     */
    ApplyReviewRecord getLastPassReviewInfoByAppInfoId(@Param("appInfoId") String appInfoId);
}
