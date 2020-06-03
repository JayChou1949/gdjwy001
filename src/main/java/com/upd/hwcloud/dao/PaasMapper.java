package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.dto.OnLineServiceDto;
import com.upd.hwcloud.bean.entity.Paas;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.Subpage;
import com.upd.hwcloud.bean.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * PaaS 表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
public interface PaasMapper extends BaseMapper<Paas> {

    IPage<Paas> getPage(IPage<Paas> page, @Param("user") User user, @Param("status") Integer status
            , @Param("name") String name, @Param("subType") String subType);

    List<Paas> getLabel(@Param("typeId") String typeId, @Param("home") Integer home, @Param("canApplication") Integer canApplication);

    IPage<Paas> getPageByCondition(IPage<Paas> page, @Param("typeId") String typeId, @Param("label") String label,
                                   @Param("home") Integer home, @Param("canApplication") Integer canApplication,
                                   @Param("keyword") String keyword,@Param("applicationShow") Integer applicationShow);

    List<Paas> getPageByCondition(@Param("typeId") String typeId, @Param("label") String label,
                                  @Param("home") Integer home, @Param("canApplication") Integer canApplication,
                                  @Param("keyword") String keyword,@Param("applicationShow") Integer applicationShow);

    List<OnLineServiceDto> getOnlineService();

    String getCategoryNameById(String id);

}
