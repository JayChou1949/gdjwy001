package com.upd.hwcloud.dao.application;

import com.upd.hwcloud.bean.entity.application.InnerReviewUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 部门内审核关联表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-12-19
 */
public interface InnerReviewUserMapper extends BaseMapper<InnerReviewUser> {

    void deleteAddAndForwardByResourceType(@Param("resourceType") Long resourceType);

    void deleteAddAndForwardByServiceId(@Param("serviceId") String serviceId);

}
