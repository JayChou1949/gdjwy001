package com.upd.hwcloud.service.workbench.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.contains.WorkbenchApplyStatus;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.dto.XtdyExport;
import com.upd.hwcloud.bean.entity.App;
import com.upd.hwcloud.bean.entity.Bigdata;
import com.upd.hwcloud.bean.entity.Paas;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.workbench.BigDataOfSaasVO;
import com.upd.hwcloud.bean.vo.workbench.PublicStatistics;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;
import com.upd.hwcloud.bean.vo.workbench.ResourceOverviewVO;
import com.upd.hwcloud.bean.vo.workbench.SaasLvThree;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.common.utils.easypoi.ExportMoreView;
import com.upd.hwcloud.common.utils.easypoi.ExportView;
import com.upd.hwcloud.dao.BigdataMapper;
import com.upd.hwcloud.dao.application.ApplicationInfoMapper;
import com.upd.hwcloud.service.IBigdataService;
import com.upd.hwcloud.service.IPaasService;
import com.upd.hwcloud.service.application.IApplicationInfoService;
import com.upd.hwcloud.service.workbench.Workbench;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.excel.entity.ExportParams;

@Service("paasHandler")
public class PaasHandler implements Workbench {

    //@Autowired
    //private IApplicationInfoService applicationInfoService;

    @Autowired
    private ApplicationInfoMapper applicationInfoMapper;
    @Autowired
    private BigdataMapper bigdataMapper;




    @Override
    public List<GeneralDTO> getUseResource(User user, QueryVO queryVO) {
        List<GeneralDTO> applyResource = Lists.newArrayList();
        Map<String,Object> param = Maps.newHashMap();
        queryVO.setServiceName(CommonHandler.dealNameforQuery(queryVO.getServiceName()));
        if(CommonHandler.isTenantManager(user)){

            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
            param.put("queryVO",queryVO);
            applyResource = applicationInfoMapper.getTenantUsePaasResource(param);

        }else {
            queryVO.setCreator(user.getIdcard());
            param.put("queryVO",queryVO);
            applyResource = applicationInfoMapper.getTenantUsePaasResource(param);
        }
        return applyResource;
    }

    @Override
    public PublicStatistics doPublicStatistics(User user, QueryVO queryVO) {
        return null;
    }

    @Override
    public List<GeneralDTO> getServiceOfSaas(String appName) {
        List<GeneralDTO> generalDTOList = applicationInfoMapper.serviceOfSaas(appName,ResourceType.PAAS.getCode());
        return generalDTOList;
    }

    public R serviceOfSaas(String name){

        //APIG的Paas服务
        List<SaasLvThree> saasLvThreeList = getServiceOfSaasStatistics(name);


        //api服务的ID
        List<String> apiModeIds = saasLvThreeList.stream().map(SaasLvThree::getId).collect(Collectors.toList());


        //服务列表(去重处理)
        List<GeneralDTO> distinct = getServiceList(saasLvThreeList,name);


        //非服务市场服务
        List<BigDataOfSaasVO> bigdataList  = getNonApiService(distinct,apiModeIds);

        Map<String,Object> res = Maps.newHashMap();
        res.put("list",distinct); //服务列表
        res.put("statistics",saasLvThreeList);//统计
        res.put("nonApiMode",bigdataList); //其它应用
        return R.ok(res);
    }


    /**
     * 服务列表
     */
    private List<GeneralDTO> getServiceList( List<SaasLvThree> saasLvThreeList,String name){

        //APIG的Paas服务
        //订单里的Paas服务
        List<GeneralDTO> paasList = getServiceOfSaas(name);
        if(saasLvThreeList !=null && saasLvThreeList.size()>0){
            saasLvThreeList.forEach(saasLvThree -> {
                GeneralDTO generalDTO = new GeneralDTO();
                generalDTO.setId(saasLvThree.getId());
                generalDTO.setName(saasLvThree.getServiceName());
                paasList.add(generalDTO);
            });
        }
        //根据名字去重
        List<GeneralDTO> distinct  = paasList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(GeneralDTO::getName))), ArrayList::new ));
        return distinct;
    }



    /**
     * 获取非服务市场服务
     * @param distinct
     * @param apiModeIds
     * @return
     */
    private  List<BigDataOfSaasVO> getNonApiService(List<GeneralDTO> distinct,List<String> apiModeIds){
        //去掉数据服务
        List<String> nonAPIModeNames= distinct.stream().filter(generalDTO -> !apiModeIds.contains(generalDTO.getId())).map(GeneralDTO::getName).collect(Collectors.toList());


        List<BigDataOfSaasVO> bigdataList = Lists.newArrayList();

        //如果in（）内为空，条件构造器不会拼接该条件
        if(nonAPIModeNames !=  null && nonAPIModeNames.size() > 0){
            bigdataList =  bigdataMapper.saasLvThreeExportVo(nonAPIModeNames);
        }
        return bigdataList;
    }


    @Override
    public List<SaasLvThree> getServiceOfSaasStatistics(String name) {
        List<SaasLvThree> saasLvThreeList = applicationInfoMapper.paasServiceOfSaasStatistics(name);
        return saasLvThreeList;
    }

    @Override
    public void lvThreePageExport(String name, HttpServletRequest request, HttpServletResponse response) {

        List<SaasLvThree> saasLvThreeList = getServiceOfSaasStatistics(name);
        ExportView api = new ExportView.Builder()
                .exportParams(new ExportParams(null, "服务市场"))
                .cls(SaasLvThree.class)
                .dataList(saasLvThreeList)
                .create();


        //api服务的ID
        List<String> apiModeIds = saasLvThreeList.stream().map(SaasLvThree::getId).collect(Collectors.toList());

        //服务列表(去重处理)
        List<GeneralDTO> distinct = getServiceList(saasLvThreeList,name);

        List<BigDataOfSaasVO> bigDataOfSaasVOList = getNonApiService(distinct,apiModeIds);
        ExportView nonApi = new ExportView.Builder()
                .exportParams(new ExportParams(null, "其它服务"))
                .cls(BigDataOfSaasVO.class)
                .dataList(bigDataOfSaasVOList)
                .create();

        List<ExportView> moreViewList = Lists.newArrayList();

        moreViewList.add(api);
        moreViewList.add(nonApi);

        ExportMoreView moreView = new ExportMoreView();
        moreView.setMoreViewList(moreViewList);

        ExcelUtil.exportMoreView(request,response,name+"使用PaaS服务",moreView);

    }

    @Override
    public IPage applyPage(Integer pageNum, Integer pageSize, QueryVO queryVO,String status,User user) {
        IPage<ApplicationInfo> page = new Page<>(pageNum,pageSize);

        Map<String,Object> param = CommonHandler.handlerOfQueryVO(queryVO,user,status,ResourceType.PAAS.getCode());

        page = applicationInfoMapper.getFlowPageOfWorkbench(page,param);
        List<ApplicationInfo> records = page.getRecords();
        CommonHandler.checkDeleteEnable(records,user);
        return page;
    }


    @Override
    public IPage<ResourceOverviewVO> resourcePage(Long pageNum, Long pageSize, User user, String serviceName, String appName) {
        IPage<ResourceOverviewVO> page = new Page<>(pageNum,pageSize);
        Map<String,Object> param = CommonHandler.handlerNewPageParam(user,serviceName,appName);
        page = applicationInfoMapper.getPaasResourcePage(page,param);
        return page;
    }

    @Override
    public HashMap<String, Long> resourceOverview(User user) {
        Map<String,Object> param = CommonHandler.handlerUserParam(user);
        HashMap<String,Long> res = applicationInfoMapper.getPaasResourceOverview(param);
        CommonHandler.handlerOfOverview(res);
        return res;
    }
}
