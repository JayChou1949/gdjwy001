package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.Daas;
import com.upd.hwcloud.bean.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * SaaS 表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
public interface DaasMapper extends BaseMapper<Daas> {

    IPage<Daas> getPage(IPage<Daas> page, @Param("user") User user, @Param("status") Integer status
            , @Param("name") String name, @Param("subType") String subType);

}
