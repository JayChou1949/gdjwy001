package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.OperateRecord;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 操作记录 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
public interface OperateRecordMapper extends BaseMapper<OperateRecord> {

    IPage<OperateRecord> getPage(IPage<OperateRecord> page, @Param("targetId") String targetId);
}
