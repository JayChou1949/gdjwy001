package com.upd.hwcloud.service.application.manage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationRecords;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  应用限额   修改记录服务类
 * </p>
 *
 * @author lqm
 * @since 2020-06-29
 */
public interface IApplicationRecordsService extends IService<ApplicationRecords> {

    /**
     * 新增限额修改记录
     * @param applicationRecords
     */
    void addQuotaRecord(ApplicationRecords applicationRecords);

    /**
     * 通过id查询当前限额的修改记录
     * @param id
     * @return
     */
    Page<ApplicationRecords> getApplicationRecords(Page<ApplicationRecords>  page,String id);

}
