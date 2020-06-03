package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Abnormal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2019-04-03
 */
public interface AbnormalMapper extends BaseMapper<Abnormal> {

    Page<Abnormal> getPage(Page<Abnormal> page,@Param("keywords") String keywords);

}
