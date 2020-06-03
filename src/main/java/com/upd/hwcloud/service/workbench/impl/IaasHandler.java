package com.upd.hwcloud.service.workbench.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.FormNum;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.contains.ServiceStatus;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.dto.HandlerWrapper;
import com.upd.hwcloud.bean.entity.Iaas;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.workbench.EcsVO;
import com.upd.hwcloud.bean.vo.workbench.PublicStatistics;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;
import com.upd.hwcloud.bean.vo.workbench.ResourceOverviewVO;
import com.upd.hwcloud.bean.vo.workbench.SaasLvThree;
import com.upd.hwcloud.common.utils.BigDecimalUtil;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.dao.application.ApplicationInfoMapper;
import com.upd.hwcloud.service.IIaasService;
import com.upd.hwcloud.service.application.IIaasTxyfwService;
import com.upd.hwcloud.service.application.IImplHandler;

import com.upd.hwcloud.service.workbench.Workbench;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import io.swagger.annotations.ApiOperation;

@Service("iaasHandler")
public class IaasHandler implements Workbench {

    private static final Logger logger = LoggerFactory.getLogger(IaasHandler.class);
    @Autowired
    private ApplicationInfoMapper applicationInfoMapper;

    @Autowired
    private IIaasService iaasService;

    @Autowired
    private IIaasTxyfwService iaasTxyfwService;

    @Autowired
    private ApplicationContext context;

    @Override
    public List<GeneralDTO> getUseResource(User user, QueryVO queryVO) {
        Map<String ,Object>  param = Maps.newHashMap();
        List<GeneralDTO> generalDTOList = Lists.newArrayList();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
            param.put("queryVO",queryVO);
            List<ApplicationInfo> applicationInfos = applicationInfoMapper.getTenantUseIaasResourceInfo(param);
            List<String> types = applicationInfos.stream().map(ApplicationInfo::getServiceTypeId).distinct().collect(Collectors.toList());
            //logger.info("过滤前表单:{}",types);
            if(applicationInfos!=null){
                doStatistics(applicationInfos,param,generalDTOList);
            }

        }else {
            queryVO.setCreator(user.getIdcard());
            param.put("queryVO",queryVO);
            List<ApplicationInfo> applicationInfos = applicationInfoMapper.getTenantUseIaasResourceInfo(param);
            if(applicationInfos!=null){
                doStatistics(applicationInfos,param,generalDTOList);
            }
        }
        return generalDTOList;
    }

    @Override
    public PublicStatistics doPublicStatistics(User user, QueryVO queryVO) {
        return null;
    }

    /**
     * 过滤无效数据（排除变更删除）
     *
     *
     */

    private List<String> filterInvaildIaasType(List<String> types){
        List<String> typeAfterFilter = Lists.newArrayList();
        for (String type:types){
            Iaas iaas = iaasService.getOne(new QueryWrapper<Iaas>().lambda().eq(Iaas::getStatus,2)
                            .ne(Iaas::getBuildStatus,1)
                            .eq(Iaas::getId,type)
                            .eq(Iaas::getCanApplication, ServiceStatus.CAN_APPLY.getCode()));
            if(iaas !=null){
                typeAfterFilter.add(iaas.getId());
            }
        }

        return typeAfterFilter;
    }

    /**
     * 统计各类别数据
     */
    private Map<String,Integer> statistics(List<ApplicationInfo> applicationInfos){


        Map<String,Integer> statisticsMap = Maps.newHashMap();


        for(ApplicationInfo applicationInfo:applicationInfos){
            statisticsMap.put(applicationInfo.getServiceTypeName(),0);
        }
        for(ApplicationInfo applicationInfo:applicationInfos){
            HandlerWrapper hw = FormNum.getHandlerWrapperByInfo(context, applicationInfo);
            IImplHandler implHandler = hw.getImplHandler();
            Integer num = implHandler.getImplServerListByAppInfoId(applicationInfo.getId()).size();
            if(num != null){
                int sum = statisticsMap.get(applicationInfo.getServiceTypeName());
                statisticsMap.put(applicationInfo.getServiceTypeName(),sum+num);
            }
        }


        return statisticsMap;

    }

    /**
     *
     * @param applicationInfos
     * @param param
     * @param generalDTOList
     * @return
     */
    private List<GeneralDTO> doStatistics(List<ApplicationInfo> applicationInfos,Map<String,Object> param,List<GeneralDTO> generalDTOList){
        List<GeneralDTO> typeDTOList = applicationInfoMapper.getTenantUseIaasType(param);
        List<String> types = applicationInfos.stream().map(ApplicationInfo::getServiceTypeId).distinct().collect(Collectors.toList());
        List<String> typeAfterFilter = filterInvaildIaasType(types);
        List<ApplicationInfo> infoAfterFilter = applicationInfos.stream().filter(applicationInfo -> typeAfterFilter.contains(applicationInfo.getServiceTypeId())).collect(Collectors.toList());
       // List<String> formNums = infoAfterFilter.stream().map(ApplicationInfo::getFormNum).distinct().collect(Collectors.toList());
       // logger.info("过滤后表单:{}",formNums);
        Map<String ,Integer> res = statistics(infoAfterFilter);
        res.entrySet().stream().forEach(entry ->{
            GeneralDTO generalDTO = new GeneralDTO();
            generalDTO.setName(entry.getKey());
            generalDTO.setValue(entry.getValue());
            generalDTOList.add(generalDTO);
        });
        if(generalDTOList !=null && generalDTOList.size()>0){
            generalDTOList.forEach(generalDTO -> {
                typeDTOList.forEach(typeDTO->{
                    if(typeDTO.getName().equals(generalDTO.getName())){
                        generalDTO.setId(typeDTO.getId());
                    }
                });

            });
        }
        return generalDTOList;
    }

    @Override
    public R serviceOfSaas(String name) {

        List<GeneralDTO> generalDTOList = getServiceOfSaas(name);
        List<SaasLvThree> saasLvThreeList = Lists.newArrayList();
        Map<String,Object> res = Maps.newHashMap();
        res.put("list",generalDTOList);
        res.put("statistics",saasLvThreeList);
        return R.ok(res);
    }

    @Override
    public List<GeneralDTO> getServiceOfSaas(String appName) {
        List<GeneralDTO> generalDTOList = applicationInfoMapper.serviceOfSaas(appName, ResourceType.IAAS.getCode());
        return generalDTOList;
    }

    @Override
    public List getServiceOfSaasStatistics(String name) {
        return null;
    }

    @Override
    public void lvThreePageExport(String name, HttpServletRequest request, HttpServletResponse response) {
        List<EcsVO> ecsVOList = iaasTxyfwService.getEcsByAppName(name);
        ecsVOList.forEach(ecsVO -> {
            if(ecsVO.getRamSize()!=0 && ecsVO.getRamSize() !=null){
                ecsVO.setRamSize(BigDecimalUtil.div(ecsVO.getRamSize().doubleValue(),1024).doubleValue());
            }
            if(ecsVO.getDiskSize() !=0 && ecsVO.getDiskSize() != null){
                ecsVO.setDiskSize(BigDecimalUtil.div(ecsVO.getDiskSize().doubleValue(),1024).doubleValue());
            }
        });
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"使用服务信息"), EcsVO.class, ecsVOList);
        ExcelUtil.export(request, response, name+"应用使用IaaS服务信息", workbook);
    }


    @Override
    public IPage applyPage(Integer pageNum, Integer pageSize, QueryVO queryVO,String status,User user) {

        IPage<ApplicationInfo> page = new Page<>(pageNum,pageSize);

        Map<String,Object> param = CommonHandler.handlerOfQueryVO(queryVO,user,status,ResourceType.IAAS.getCode());

        page = applicationInfoMapper.getFlowPageOfWorkbench(page,param);
        List<ApplicationInfo> records = page.getRecords();
        CommonHandler.checkDeleteEnable(records,user);
        return page;
    }


    @Override
    public IPage<ResourceOverviewVO> resourcePage(Long pageNum, Long pageSize, User user, String serviceName, String appName) {
        return null;
    }

    @Override
    public HashMap<String, Long> resourceOverview(User user) {
        return null;
    }
}
