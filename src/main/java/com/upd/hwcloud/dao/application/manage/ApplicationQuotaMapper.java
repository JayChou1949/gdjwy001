package com.upd.hwcloud.dao.application.manage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationQuota;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lqm
 * @since 2020-06-29
 */
public interface ApplicationQuotaMapper extends BaseMapper<ApplicationQuota> {

    /**
     * 查询所以的配额信息
     * @return
     */
    Page<ApplicationQuota> getApplicationQuota();

    /**
     * 根据配额信息表  id以及 新的用户配额  设置配额信息表的用户配额数
     * @param id
     * @param quota
     */
    void  updateQuota(@Param("id")String id,@Param("quota")Integer quota);

    /**
     * 根据地市或警种查询  当前账号总限额 以及剩余数量
     * @param areaOrPolice
     * @return
     */
    ApplicationQuota  getApplicationQuotaDetail(@Param("areaOrPolice") String areaOrPolice);

    /**
     *新增配额设置
     * @param files
     * @param applicationQuota
     */
    void addApplicationQuota(@Param("files")Files files,@Param("applicationQuota") ApplicationQuota applicationQuota);

    /**
     * 根据申请人查询配置设置列表
     * @param applyPerson
     * @return
     */
    Page<ApplicationQuota> getApplicationQuotaList(Page<ApplicationQuota> page,@Param("applyPerson")String applyPerson);

    /**
     * 根据id查询单个  限额的详细信息
     * @param id
     * @return
     */
    ApplicationQuota  getApplicationQuotaById(@Param("id") String id);

}
