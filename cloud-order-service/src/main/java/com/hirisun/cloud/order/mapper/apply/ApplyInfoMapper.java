package com.hirisun.cloud.order.mapper.apply;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.apply.ApplyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 申请信息表 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-08
 */
public interface ApplyInfoMapper extends BaseMapper<ApplyInfo> {

    Page<ApplyInfo> getPage(Page<ApplyInfo> page, @Param("user") UserVO user, @Param("param") Map map);

}
