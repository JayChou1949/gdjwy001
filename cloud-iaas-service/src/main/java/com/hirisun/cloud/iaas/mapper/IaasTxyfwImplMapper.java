package com.hirisun.cloud.iaas.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.iaas.bean.IaasTxyfwImpl;

/**
 * 弹性云服务实施信息表 Mapper 接口
 */
public interface IaasTxyfwImplMapper extends BaseMapper<IaasTxyfwImpl> {

    List<IaasTxyfwImpl> ncovEcsList(String startTime);

}
