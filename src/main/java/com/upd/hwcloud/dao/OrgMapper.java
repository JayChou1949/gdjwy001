package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.dto.cov.CloudDeskIdAndUnit;
import com.upd.hwcloud.bean.entity.Org;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 机构表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-10-12
 */
public interface OrgMapper extends BaseMapper<Org> {

    List<Org> queryParentTreeByOrgId(@Param("orgId") String orgId);

    List<Org> querySubTreeByOrgId(@Param("orgId") String orgId);

    List<Org> search(@Param("keyword") String keyword);

    List<CloudDeskIdAndUnit> allOrgByLevel1();
}
