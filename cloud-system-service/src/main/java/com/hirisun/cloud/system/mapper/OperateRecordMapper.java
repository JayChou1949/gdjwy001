package com.hirisun.cloud.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.system.bean.OperateRecord;

public interface OperateRecordMapper extends BaseMapper<OperateRecord> {

    IPage<OperateRecord> getPage(IPage<OperateRecord> page, @Param("targetId") String targetId);
}
