package com.hirisun.cloud.order.mapper.recover;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.order.bean.recover.ResourceRecoverAppInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 资源回收申请信息 Mapper 接口
 */
public interface ResourceRecoverAppInfoMapper extends BaseMapper<ResourceRecoverAppInfo> {

    IPage<ResourceRecoverAppInfo> getPage(IPage<ResourceRecoverAppInfo> page, @Param("p")Map<String,String> params);

    List<ResourceRecoverAppInfo> queryUntreatedRecover();

}
