package com.hirisun.cloud.third.mapper;

import com.hirisun.cloud.third.bean.ThreePartyInterface;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 第三方接口表 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-05
 */
public interface ThreePartyInterfaceMapper extends BaseMapper<ThreePartyInterface> {

    public List<ThreePartyInterface> getOldDataByParams(@Param("labels") List<String> names);

}
