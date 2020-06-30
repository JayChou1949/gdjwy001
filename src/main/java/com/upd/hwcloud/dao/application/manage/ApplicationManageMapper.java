package com.upd.hwcloud.dao.application.manage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 应用运营管理 Mapper 接口
 * </p>
 *
 * @author lqm
 * @since 2020-06-29
 */
public interface ApplicationManageMapper extends BaseMapper<ApplicationManage> {

    /**
     * 查询所以的配额信息
     * @return
     */
    Page<ApplicationManage> getApplicationQuota(Page<ApplicationManage> page);

    /**
     * 根据配额信息表  id以及 新的用户配额  设置配额信息表的用户配额数
     * @param id
     * @param quota
     */
    void  updateQuota(@Param("id") String id,@Param("quota") Integer quota);

    /**
     * 根据地市或警种查询  当前账号总限额 以及剩余数量
     * @param areaOrPolice
     * @return
     */
    ApplicationManage  getApplicationQuotaDetail(@Param("areaOrPolice")String areaOrPolice);

}
