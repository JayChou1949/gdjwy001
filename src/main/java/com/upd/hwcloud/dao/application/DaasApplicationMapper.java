package com.upd.hwcloud.dao.application;

import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.entity.application.DaasApplication;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * DaaS服务申请信息 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2019-03-10
 */
public interface DaasApplicationMapper extends BaseMapper<DaasApplication> {

    List<GeneralDTO> tenantStatistics(@Param("p") Map<String,Object> param);

    List<GeneralDTO> serviceOfSaas(String name);

}
