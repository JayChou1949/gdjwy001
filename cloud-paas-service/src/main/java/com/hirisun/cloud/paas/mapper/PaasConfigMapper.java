package com.hirisun.cloud.paas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.pass.vo.OnLineServiceVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.paas.bean.PaasConfig;

public interface PaasConfigMapper extends BaseMapper<PaasConfig> {

    IPage<PaasConfig> getPage(IPage<PaasConfig> page, @Param("user") UserVO user, @Param("status") Integer status
            , @Param("name") String name, @Param("subType") String subType);

    List<PaasConfig> getLabel(@Param("typeId") String typeId, @Param("home") Integer home, @Param("canApplication") Integer canApplication);

    IPage<PaasConfig> getPageByCondition(IPage<PaasConfig> page, @Param("typeId") String typeId, @Param("label") String label,
                                   @Param("home") Integer home, @Param("canApplication") Integer canApplication,
                                   @Param("keyword") String keyword,@Param("applicationShow") Integer applicationShow);

    List<PaasConfig> getPageByCondition(@Param("typeId") String typeId, @Param("label") String label,
                                  @Param("home") Integer home, @Param("canApplication") Integer canApplication,
                                  @Param("keyword") String keyword,@Param("applicationShow") Integer applicationShow);

    List<OnLineServiceVo> getOnlineService();

    String getCategoryNameById(String id);

}
