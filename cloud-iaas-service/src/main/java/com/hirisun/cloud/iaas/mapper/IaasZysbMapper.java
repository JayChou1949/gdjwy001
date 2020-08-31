package com.hirisun.cloud.iaas.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.iaas.bean.IaasZysb;
import com.hirisun.cloud.model.export.vo.ReportExportVo;
import com.hirisun.cloud.model.iaas.vo.IaasZysbExportVo;
import com.hirisun.cloud.model.user.UserVO;

/**
 * 申请信息表 Mapper 接口
 */
public interface IaasZysbMapper extends BaseMapper<IaasZysb> {

    IaasZysb getDetails(String id);

    IPage<IaasZysb> getPage(IPage<IaasZysb> page, @Param("p")Map<String, Object> param);

    List<IaasZysbExportVo> getListByCondition(@Param("p")Map<String, Object> param);

    List<ReportExportVo> getBaseExportInfo(@Param("p") Map<String,Object> param);

    List<IaasZysb> getListExport(@Param("p") Map<String, Object> param);

    int  getTodoCount(@Param("user") UserVO user);

    int getResourceReportTodo(@Param("idCard") String idCard);

}
