package com.upd.hwcloud.dao.dm;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.dto.VmGeneralExport;
import com.upd.hwcloud.bean.entity.dm.CloudVm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yyc
 * @since 2019-08-13
 */
public interface CloudVmMapper extends BaseMapper<CloudVm> {


    Page<CloudVm> page(Page<CloudVm> page,@Param("name")String name, @Param("order") String order, @Param("orderType") String orderType,@Param("cloud") String cloud);

    List<GeneralDTO> areaAppCount();

    List<CloudVm> vmListOrder(@Param("name")String name, @Param("order") String order, @Param("orderType") String orderType);

    List<String> poorAreaAppListByName(String name);

    List<CloudVm> getList(String name);

    List<String> businessUnit1();


    List<VmGeneralExport> exportVmByAppName(String appName);
}
