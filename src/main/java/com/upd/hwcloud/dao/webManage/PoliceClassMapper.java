package com.upd.hwcloud.dao.webManage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Apply;
import com.upd.hwcloud.bean.entity.webManage.PoliceClass;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 警种上云 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
public interface PoliceClassMapper extends BaseMapper<PoliceClass> {

    Page<PoliceClass> getPage(Page<PoliceClass> page, @Param("user") User user, @Param("status") Integer status, @Param("name") String name);

    Page<PoliceClass> getPoliceClassByPage(Page<PoliceClass> page);

}
