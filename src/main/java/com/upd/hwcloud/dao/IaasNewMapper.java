package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.IaasNew;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.Subpage;
import com.upd.hwcloud.bean.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * IaaS 表 Mapper 接口
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
public interface IaasNewMapper extends BaseMapper<IaasNew> {
    IPage<IaasNew> getPage(IPage<IaasNew> page, @Param("user") User user, @Param("status") Integer status
            , @Param("name") String name, @Param("subType") String subType);
    List<IaasNew> getLabel(@Param("home") Integer home, @Param("canApplication") Integer canApplication);
    List<IaasNew> getPageByCondition(@Param("label") String label,
                                  @Param("home") Integer home, @Param("canApplication") Integer canApplication,
                                  @Param("keyword") String keyword);
}
