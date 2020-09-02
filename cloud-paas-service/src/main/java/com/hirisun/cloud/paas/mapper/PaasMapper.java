package com.hirisun.cloud.paas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.pass.vo.OnLineServiceVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.paas.bean.Paas;

public interface PaasMapper extends BaseMapper<Paas> {

    IPage<Paas> getPage(IPage<Paas> page, @Param("user") UserVO user, @Param("status") Integer status
            , @Param("name") String name, @Param("subType") String subType);

    List<Paas> getLabel(@Param("typeId") String typeId, @Param("home") Integer home, @Param("canApplication") Integer canApplication);

    IPage<Paas> getPageByCondition(IPage<Paas> page, @Param("typeId") String typeId, @Param("label") String label,
                                   @Param("home") Integer home, @Param("canApplication") Integer canApplication,
                                   @Param("keyword") String keyword,@Param("applicationShow") Integer applicationShow);

    List<Paas> getPageByCondition(@Param("typeId") String typeId, @Param("label") String label,
                                  @Param("home") Integer home, @Param("canApplication") Integer canApplication,
                                  @Param("keyword") String keyword,@Param("applicationShow") Integer applicationShow);

    List<OnLineServiceVo> getOnlineService();

    String getCategoryNameById(String id);

}
