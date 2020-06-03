package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.dto.SaasOrderTotal;
import com.upd.hwcloud.bean.dto.SaasTotal;
import com.upd.hwcloud.bean.dto.SaasUseTotal;
import com.upd.hwcloud.bean.entity.SaasApplicationMerge;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * SaaS资源申请合并信息表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2019-07-24
 */
public interface SaasApplicationMergeMapper extends BaseMapper<SaasApplicationMerge> {

    IPage<SaasApplicationMerge> getFlowPage(IPage<SaasApplicationMerge> page, @Param("p") Map<String, Object> param);

    //查服务用
    IPage<SaasApplicationMerge> getFlowPageWithServiceName(IPage<SaasApplicationMerge> page, @Param("p") Map<String, Object> param);

    //优化前的SQL
    IPage<SaasApplicationMerge> getFlowPageBank(IPage<SaasApplicationMerge> page, @Param("p") Map<String, Object> param);

    List<SaasTotal> saasTotal(@Param("areas") String areas, @Param("policeCategory") String policeCategory,
                              @Param("p") Map<String, String> params);

    List<SaasOrderTotal> saasOrderTotal(@Param("areas") String areas, @Param("policeCategory") String policeCategory,
                                        @Param("p") Map<String, String> params);

    List<SaasOrderTotal> saasOrderQuery(@Param("areas") String areas, @Param("policeCategory") String policeCategory,
                                        @Param("p") Map<String, String> params);

    List<SaasUseTotal> saasUseTotal(@Param("areas") String areas, @Param("policeCategory") String policeCategory,
                                    @Param("p") Map<String, String> params);


    List<GeneralDTO> tenantUseResource(@Param("p") Map<String ,Object> param,@Param("type") Integer type);


    List<GeneralDTO> tenantUseResourceBank(@Param("p") Map<String ,Object> param);



    List<GeneralDTO> personalUseResource(@Param("p") Map<String ,Object> param,@Param("type") Integer type);

}
