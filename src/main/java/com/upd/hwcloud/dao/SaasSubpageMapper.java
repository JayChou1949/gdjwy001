package com.upd.hwcloud.dao;

import com.upd.hwcloud.bean.dto.DaasServiceOverview;
import com.upd.hwcloud.bean.dto.InnerServiceOverview;
import com.upd.hwcloud.bean.dto.ServiceOverview;
import com.upd.hwcloud.bean.entity.SaasSubpage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * SAAS二级页面内容 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2019-02-11
 */
public interface SaasSubpageMapper extends BaseSubpageMapper<SaasSubpage> {

    List<DaasServiceOverview> daas(@Param("name") String name);

    List<ServiceOverview> paas(@Param("name") String name);

    List<InnerServiceOverview> paasOther(@Param("name") String name);

}
