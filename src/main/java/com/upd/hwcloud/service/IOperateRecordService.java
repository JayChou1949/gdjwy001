package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.OperateRecord;

/**
 * <p>
 * 操作记录 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
public interface IOperateRecordService extends IService<OperateRecord> {

    /**
     * 操作记录
     * @param targetId 记录项 id
     * @param operator 操作人
     * @param operate 操作
     * @param result 结果
     * @param remark 描述
     */
    void insertRecord(String targetId, String operator, String operate, String result, String remark);

    void insertLatestOnline(String targetId, String operator, String operate, String result, String remark);

    IPage<OperateRecord> getPage(IPage<OperateRecord> page, String targetId);
}
