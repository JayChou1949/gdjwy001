package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.dto.IaasZysbExport;
import com.upd.hwcloud.bean.entity.IaasZysb;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.vo.resourceReport.export.ReportExport;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 申请信息表 Mapper 接口
 * </p>
 *
 * @author zwb
 * @since 2019-05-22
 */
public interface IaasZysbMapper extends BaseMapper<IaasZysb> {

    IaasZysb getDetails(String id);

    IPage<IaasZysb> getPage(IPage<IaasZysb> page, @Param("p")Map<String, Object> param);

    List<IaasZysbExport> getListByCondition(@Param("p")Map<String, Object> param);

    List<ReportExport> getBaseExportInfo(@Param("p") Map<String,Object> param);

    List<IaasZysb> getListExport(@Param("p") Map<String, Object> param);

    int  getTodoCount(@Param("user") User user);

    int getResourceReportTodo(@Param("idCard") String idCard);

}
