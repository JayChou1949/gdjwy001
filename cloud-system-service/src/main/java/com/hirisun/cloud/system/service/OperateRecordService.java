package com.hirisun.cloud.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.system.OperateRecordVo;
import com.hirisun.cloud.system.bean.OperateRecord;

public interface OperateRecordService extends IService<OperateRecord> {

    /**
     * 操作记录
     * @param targetId 记录项 id
     * @param operator 操作人
     * @param operate 操作
     * @param result 结果
     * @param remark 描述
     */
    void insertRecord(OperateRecordVo vo);

    void insertLatestOnline(OperateRecordVo vo);

    IPage<OperateRecord> getPage(IPage<OperateRecord> page, String targetId);
    
    boolean validateExits(String configId);
    
    
}
