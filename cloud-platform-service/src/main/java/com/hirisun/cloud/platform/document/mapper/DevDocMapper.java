package com.hirisun.cloud.platform.document.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.platform.document.bean.DevDoc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.platform.document.bean.DevDocClass;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 文档 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
public interface DevDocMapper extends BaseMapper<DevDoc> {

    Page<DevDoc> getPage(Page<DevDoc> page, @Param("param") Map paramMap);

}
