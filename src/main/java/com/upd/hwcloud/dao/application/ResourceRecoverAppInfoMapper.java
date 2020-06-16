package com.upd.hwcloud.dao.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.application.ResourceRecoverAppInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资源回收申请信息 Mapper 接口
 * </p>
 *
 * @author yyc
 * @since 2020-05-14
 */
public interface ResourceRecoverAppInfoMapper extends BaseMapper<ResourceRecoverAppInfo> {

    IPage<ResourceRecoverAppInfo> getPage(IPage<ResourceRecoverAppInfo> page, @Param("p")Map<String,String> params);

    List<ResourceRecoverAppInfo> queryUntreatedRecover();

}
