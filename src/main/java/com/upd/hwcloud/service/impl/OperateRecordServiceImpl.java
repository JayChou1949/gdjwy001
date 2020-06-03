package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.OperateRecord;
import com.upd.hwcloud.dao.OperateRecordMapper;
import com.upd.hwcloud.service.IOperateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作记录 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
@Service
public class OperateRecordServiceImpl extends ServiceImpl<OperateRecordMapper, OperateRecord> implements IOperateRecordService {

    @Autowired
    private OperateRecordMapper operateRecordMapper;

    @Override
    public void insertRecord(String targetId, String operator, String operate, String result, String remark) {
        OperateRecord record = new OperateRecord();
        record.setTargetId(targetId);
        record.setOperator(operator);
        record.setOperate(operate);
        record.setResult(result);
        record.setRemark(remark);
        record.insert();
    }

    @Override
    public void insertLatestOnline(String targetId, String operator, String operate, String result, String remark) {
        OperateRecord record = new OperateRecord();
        record.setTargetId(targetId);
        record.setOperator(operator);
        record.setOperate(operate);
        record.setResult(result);
        record.setRemark(remark);
        record.setFirst(1L);
        record.insert();
    }

    @Override
    public IPage<OperateRecord> getPage(IPage<OperateRecord> page, String targetId) {
        return operateRecordMapper.getPage(page, targetId);
    }

}
