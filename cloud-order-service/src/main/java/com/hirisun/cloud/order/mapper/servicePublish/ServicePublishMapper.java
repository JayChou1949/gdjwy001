package com.hirisun.cloud.order.mapper.servicePublish;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.servicePublish.ServicePublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 服务发布 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
public interface ServicePublishMapper extends BaseMapper<ServicePublish> {
    IPage<ServicePublish> getPage(IPage<ServicePublish> page, @Param("p") Map<String, Object> param);

    int getReviewCount(@Param("user") UserVO user, @Param("p") Map<String,Object> param);

    int getImplCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getRejectCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getUseCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);
}
