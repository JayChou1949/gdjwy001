package com.upd.hwcloud.dao.application.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationViolation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xqp
 * @since 2020-06-30
 */
public interface ApplicationViolationMapper extends BaseMapper<ApplicationViolation> {

    IPage<ApplicationViolation> getViolationUser(IPage<ApplicationViolation> page, @Param("areaOrPolice") String areaOrPolice,@Param("creatorName") String creatorName,@Param("orgName") String orgName,@Param("violationTime") String violationTime);
}
