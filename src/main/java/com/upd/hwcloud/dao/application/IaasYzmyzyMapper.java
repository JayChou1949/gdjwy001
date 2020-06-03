package com.upd.hwcloud.dao.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.application.IaasYzmyzy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.vo.workbench.CloudDesktopVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * IaaS 云桌面云资源申请信息 Mapper 接口
 * </p>
 *
 * @author zwb
 * @since 2019-05-09
 */
public interface IaasYzmyzyMapper extends BaseMapper<IaasYzmyzy> {

    IPage<CloudDesktopVO> getResourcePage(IPage<CloudDesktopVO> page, @Param("p") Map<String,Object> param);

}
