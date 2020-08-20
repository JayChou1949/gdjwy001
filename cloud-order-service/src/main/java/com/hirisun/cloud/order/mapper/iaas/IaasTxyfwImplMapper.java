package com.hirisun.cloud.order.mapper.iaas;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.order.bean.iaas.IaasTxyfwImpl;

/**
 * 弹性云服务实施信息表 Mapper 接口
 */
public interface IaasTxyfwImplMapper extends BaseMapper<IaasTxyfwImpl> {

    List<IaasTxyfwImpl> ncovEcsList(String startTime);

}
