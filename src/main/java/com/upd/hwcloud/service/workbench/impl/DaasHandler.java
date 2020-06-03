package com.upd.hwcloud.service.workbench.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.entity.SaasRecoverInfo;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.PaasRqzyFwq;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.workbench.PublicStatistics;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;
import com.upd.hwcloud.bean.vo.workbench.ResourceOverviewVO;
import com.upd.hwcloud.bean.vo.workbench.SaasLvThree;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.dao.application.ApplicationInfoMapper;
import com.upd.hwcloud.dao.application.DaasApplicationMapper;
import com.upd.hwcloud.service.workbench.Workbench;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;

@Service("daasHandler")
public class DaasHandler implements Workbench {

    @Autowired
    private DaasApplicationMapper daasApplicationMapper;

    @Autowired
    private ApplicationInfoMapper applicationInfoMapper;


    @Override
    public List<GeneralDTO> getUseResource(User user, QueryVO queryVO) {
        List<GeneralDTO> applyResource = Lists.newArrayList();
        Map<String,Object> param = Maps.newHashMap();
        queryVO.setServiceName(CommonHandler.dealNameforQuery(queryVO.getServiceName()));
        queryVO.setCategory(CommonHandler.dealNameforQuery(queryVO.getCategory()));
        if(CommonHandler.isTenantManager(user)){
            queryVO.setPoliceCategory(user.getTenantPoliceCategory());
            queryVO.setArea(user.getTenantArea());
            param.put("queryVO",queryVO);
            applyResource = daasApplicationMapper.tenantStatistics(param);

        }else{
            queryVO.setCreator(user.getIdcard());
            param.put("queryVO",queryVO);
            applyResource = daasApplicationMapper.tenantStatistics(param);

        }
        return applyResource;
    }


    @Override
    public PublicStatistics doPublicStatistics(User user, QueryVO queryVO) {
        return null;
    }


    @Override
    public R serviceOfSaas(String name) {
        List<SaasLvThree> saasLvThreeList = getServiceOfSaasStatistics(name);
        List<GeneralDTO> generalDTOList = Lists.newArrayList();
        saasLvThreeList.forEach(saasLvThree -> {
            GeneralDTO generalDTO = new GeneralDTO();
            generalDTO.setName(saasLvThree.getServiceName());
            generalDTOList.add(generalDTO);
        });
        Map<String,Object> res = Maps.newHashMap();
        res.put("list",generalDTOList);
        res.put("statistics",saasLvThreeList);
        return R.ok(res);
    }

    @Override
    public List<GeneralDTO> getServiceOfSaas(String appName) {
        List<GeneralDTO> generalDTOList = daasApplicationMapper.serviceOfSaas(appName);
        return generalDTOList;
    }

    @Override
    public List<SaasLvThree> getServiceOfSaasStatistics(String name) {
        List<SaasLvThree> saasLvThreeList = applicationInfoMapper.daasServiceOfSaasStatistics(name);
        return saasLvThreeList;
    }

    @Override
    public void lvThreePageExport(String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<SaasLvThree> saasLvThreeList = applicationInfoMapper.daasServiceOfSaasStatistics(name);

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"使用服务信息"), SaasLvThree.class, saasLvThreeList);
        ExcelUtil.export(request, response, name+"应用使用DaaS服务信息", workbook);

    }

    @Override
    public IPage applyPage(Integer pageNum, Integer pageSize, QueryVO queryVO,String status,User user) {
        IPage<ApplicationInfo> page = new Page<>(pageNum,pageSize);

        Map<String,Object> param = CommonHandler.handlerOfQueryVO(queryVO,user,status, ResourceType.DAAS.getCode());

        page = applicationInfoMapper.getFlowPageOfWorkbench(page,param);
        List<ApplicationInfo> records = page.getRecords();
        CommonHandler.checkDeleteEnable(records,user);
        return page;
    }

    @Override
    public IPage<ResourceOverviewVO> resourcePage(Long pageNum, Long pageSize, User user, String serviceName, String appName) {
        IPage<ResourceOverviewVO> page = new Page<>(pageNum,pageSize);
        Map<String,Object> param = CommonHandler.handlerNewPageParam(user,serviceName,appName);
        page = applicationInfoMapper.getDaasResourcePage(page,param);
        return page;
    }

    @Override
    public HashMap<String, Long> resourceOverview(User user) {
        Map<String,Object> param = CommonHandler.handlerUserParam(user);
        HashMap<String ,Long> res = applicationInfoMapper.getDaasResourceOverview(param);
        CommonHandler.handlerOfOverview(res);
        return res;
    }
}
