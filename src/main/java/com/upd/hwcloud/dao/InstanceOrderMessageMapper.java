package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.InstanceOrderMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2019-04-03
 */
public interface InstanceOrderMessageMapper extends BaseMapper<InstanceOrderMessage> {

    List<InstanceOrderMessage> getMessageByGuid(@Param("guid") String guid);

}
