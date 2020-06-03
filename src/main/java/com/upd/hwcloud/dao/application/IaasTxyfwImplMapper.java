package com.upd.hwcloud.dao.application;

import com.upd.hwcloud.bean.entity.application.IaasTxyfwImpl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 弹性云服务实施信息表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-12-04
 */
public interface IaasTxyfwImplMapper extends BaseMapper<IaasTxyfwImpl> {

    List<IaasTxyfwImpl> ncovEcsList(String startTime);

}
