package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.Iaas;
import com.upd.hwcloud.bean.entity.Subpage;
import com.upd.hwcloud.bean.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * IaaS 表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
public interface IaasMapper extends BaseMapper<Iaas> {

    IPage<Iaas> getPage(IPage<Iaas> page, @Param("user") User user, @Param("status") Integer status
            , @Param("name") String name, @Param("subType") String subType);

    List<Subpage> getLabel(@Param("home") Integer home, @Param("canApplication") Integer canApplication);

    List<Iaas> getPageByCondition(@Param("label") String label,
                                  @Param("home") Integer home, @Param("canApplication") Integer canApplication,
                                  @Param("keyword") String keyword);

}
