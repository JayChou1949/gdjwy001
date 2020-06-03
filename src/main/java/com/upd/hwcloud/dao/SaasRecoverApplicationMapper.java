package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.dto.SaasRecoverTotal;
import com.upd.hwcloud.bean.entity.SaasApplication;
import com.upd.hwcloud.bean.entity.SaasRecoverApplication;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.User;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangbao
 * @since 2019-10-24
 */
public interface SaasRecoverApplicationMapper extends BaseMapper<SaasRecoverApplication> {

    IPage<SaasRecoverApplication> getRecoverPage(IPage<SaasRecoverApplication> page, @Param("p") Map<String, Object> param);

    List<SaasRecoverTotal> saasRecoverExport(@Param("areas") String areas, @Param("policeCategory")String policeCategory, @Param("p")Map<String, String> params);

    IPage<SaasRecoverApplication> getRecoverPageWithHandler(IPage<SaasRecoverApplication> page, @Param("p") Map<String, Object> param);
}
