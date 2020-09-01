package com.hirisun.cloud.saas.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.model.saas.vo.SaasApplicationMergeVO;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.daas.vo.GeneralVo;
import com.hirisun.cloud.model.saas.vo.SaasOrderTotalVo;
import com.hirisun.cloud.model.saas.vo.SaasTotalVo;
import com.hirisun.cloud.model.saas.vo.SaasUseTotalVo;
import com.hirisun.cloud.saas.bean.SaasApplicationMerge;

/**
 * SaaS资源申请合并信息表 Mapper 接口
 */
public interface SaasApplicationMergeMapper extends BaseMapper<SaasApplicationMerge> {

    Page<SaasApplicationMergeVO> getFlowPage(IPage<SaasApplicationMergeVO> page, @Param("p") Map<String, Object> param);

    //查服务用
    Page<SaasApplicationMergeVO> getFlowPageWithServiceName(IPage<SaasApplicationMergeVO> page, @Param("p") Map<String, Object> param);

    //优化前的SQL
    IPage<SaasApplicationMerge> getFlowPageBank(IPage<SaasApplicationMerge> page, @Param("p") Map<String, Object> param);

    List<SaasTotalVo> saasTotal(@Param("areas") String areas, @Param("policeCategory") String policeCategory,
                              @Param("p") Map<String, String> params);

    List<SaasOrderTotalVo> saasOrderTotal(@Param("areas") String areas, @Param("policeCategory") String policeCategory,
                                        @Param("p") Map<String, String> params);

    List<SaasOrderTotalVo> saasOrderQuery(@Param("areas") String areas, @Param("policeCategory") String policeCategory,
                                        @Param("p") Map<String, String> params);

    List<SaasUseTotalVo> saasUseTotal(@Param("areas") String areas, @Param("policeCategory") String policeCategory,
                                    @Param("p") Map<String, String> params);


    List<GeneralVo> tenantUseResource(@Param("p") Map<String ,Object> param,@Param("type") Integer type);


    List<GeneralVo> tenantUseResourceBank(@Param("p") Map<String ,Object> param);



    List<GeneralVo> personalUseResource(@Param("p") Map<String ,Object> param,@Param("type") Integer type);

}
