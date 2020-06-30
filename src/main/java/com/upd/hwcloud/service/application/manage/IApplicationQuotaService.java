package com.upd.hwcloud.service.application.manage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationQuota;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  新增配额设置 服务类
 * </p>
 *
 * @author lqm
 * @since 2020-06-29
 */
public interface IApplicationQuotaService extends IService<ApplicationQuota> {


    /**
     *新增配额设置
     * @param files
     * @param applicationQuota
     */
    void addApplicationQuota(ApplicationQuota applicationQuota);

    /**
     * 根据申请人查询配置设置列表
     * @param applyPerson
     * @return
     */
    Page<ApplicationQuota> getApplicationQuotaList(Page<ApplicationQuota> page,String applyPerson);

    /**
     * 根据id查询单个  限额的详细信息
     * @param id
     * @return
     */
    ApplicationQuota  getApplicationQuotaById(String id);
}
