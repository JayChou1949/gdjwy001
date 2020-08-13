package com.hirisun.cloud.system.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.model.system.OperateRecordVo;
import com.hirisun.cloud.system.bean.OperateRecord;
import com.hirisun.cloud.system.mapper.OperateRecordMapper;
import com.hirisun.cloud.system.service.OperateRecordService;

@Service
public class OperateRecordServiceImpl extends ServiceImpl<OperateRecordMapper, OperateRecord> 
		implements OperateRecordService {

    @Autowired
    private OperateRecordMapper operateRecordMapper;

    /**
     * 新增操作记录
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertRecord(OperateRecordVo vo) {
        OperateRecord record = new OperateRecord();
        record.setTargetId(vo.getTargetId());
        record.setOperator(vo.getOperator());
        record.setOperate(vo.getOperate());
        record.setResult(vo.getResult());
        record.setRemark(vo.getRemark());
        operateRecordMapper.insert(record);
    }

    /**
     * 新增操作记录并设置排第一位
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertLatestOnline(OperateRecordVo vo) {
        OperateRecord record = new OperateRecord();
        record.setTargetId(vo.getTargetId());
        record.setOperator(vo.getOperator());
        record.setOperate(vo.getOperate());
        record.setResult(vo.getResult());
        record.setRemark(vo.getRemark());
        record.setFirst(1L);
        operateRecordMapper.insert(record);
    }

    @Override
    public IPage<OperateRecord> getPage(IPage<OperateRecord> page, String targetId) {
        return operateRecordMapper.getPage(page, targetId);
    }

	/**
	 * 根据配置id验证是否存在上线的记录
	 */
	public boolean validateExits(String configId) {
		
		List<OperateRecord> list = this.list(new QueryWrapper<OperateRecord>()
                .eq("TARGET_ID", configId).eq("result","上线"));
		
		return CollectionUtils.isNotEmpty(list);
		
	}

}
