package com.hirisun.cloud.iaas.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.iaas.bean.IaasYzmyzy;
import com.hirisun.cloud.model.iaas.vo.CloudDesktopVO;

/**
 * IaaS 云桌面云资源申请信息 Mapper 接口
 */
public interface IaasYzmyzyMapper extends BaseMapper<IaasYzmyzy> {

    IPage<CloudDesktopVO> getResourcePage(IPage<CloudDesktopVO> page, @Param("p") Map<String,Object> param);

}
