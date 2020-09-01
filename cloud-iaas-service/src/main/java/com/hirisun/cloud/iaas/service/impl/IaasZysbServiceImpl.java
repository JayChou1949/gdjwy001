package com.hirisun.cloud.iaas.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.hirisun.cloud.api.paas.PaasReportApi;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.system.SystemApi;
import com.hirisun.cloud.common.constant.RedisKey;
import com.hirisun.cloud.common.contains.ApplicationInfoStatus;
import com.hirisun.cloud.iaas.bean.IaasReport;
import com.hirisun.cloud.iaas.bean.IaasZysb;
import com.hirisun.cloud.iaas.bean.IaasZysbDsj;
import com.hirisun.cloud.iaas.bean.IaasZysbFbssjk;
import com.hirisun.cloud.iaas.bean.IaasZysbSjk;
import com.hirisun.cloud.iaas.bean.IaasZysbXmxx;
import com.hirisun.cloud.iaas.bean.IaasZysbXnj;
import com.hirisun.cloud.iaas.bean.ReportFusionAccess;
import com.hirisun.cloud.iaas.bean.ReportSpecial;
import com.hirisun.cloud.iaas.mapper.IaasZysbMapper;
import com.hirisun.cloud.iaas.service.IIaasReportService;
import com.hirisun.cloud.iaas.service.IIaasZysbDsjService;
import com.hirisun.cloud.iaas.service.IIaasZysbFbssjkService;
import com.hirisun.cloud.iaas.service.IIaasZysbService;
import com.hirisun.cloud.iaas.service.IIaasZysbSjkService;
import com.hirisun.cloud.iaas.service.IIaasZysbXmxxService;
import com.hirisun.cloud.iaas.service.IIaasZysbXnjService;
import com.hirisun.cloud.iaas.service.IReportFusionAccessService;
import com.hirisun.cloud.iaas.service.IReportSpecialService;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.export.vo.ReportExportVo;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.iaas.vo.IaasReportVo;
import com.hirisun.cloud.model.iaas.vo.IaasZysbDsjVo;
import com.hirisun.cloud.model.iaas.vo.IaasZysbExportVo;
import com.hirisun.cloud.model.iaas.vo.IaasZysbSjkVo;
import com.hirisun.cloud.model.pass.vo.PaasReportVo;
import com.hirisun.cloud.model.system.ReportFusionAccessVo;
import com.hirisun.cloud.model.system.SysDictVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.redis.service.RedisService;

/**
 * 申请信息表 服务实现类
 */
@Service
public class IaasZysbServiceImpl extends ServiceImpl<IaasZysbMapper, IaasZysb> implements IIaasZysbService {


    private static  final Logger logger = LoggerFactory.getLogger(IaasZysbServiceImpl.class);

    //TODO 资源上报申请审批
//    @Autowired
//    private IAppReviewInfoService appReviewInfoService;
    @Autowired
    private FilesApi filesApi;
    @Autowired
    private IIaasZysbXmxxService iaasZysbXmxxService;
    @Autowired
    private IIaasZysbXnjService iaasZysbXnjService;
    @Autowired
    private IIaasZysbDsjService iaasZysbDsjService;
    @Autowired
    private IIaasZysbSjkService iaasZysbSjkService;
    @Autowired
    private IIaasZysbFbssjkService iaasZysbFbssjkService;
    @Autowired
    private SystemApi systemApi;
    @Autowired
    private IIaasReportService reportIaasService;
//    @Autowired
//    private IReportPaasService reportPaasService;
    @Autowired
    private PaasReportApi paasReportApi;
    @Autowired
    private IReportFusionAccessService reportFusionAccessService;
    @Autowired
    private IReportSpecialService reportSpecialService;
    @Autowired
    private RedisService redisService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(UserVO user, IaasZysb info) {
        info.setId(null);
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        info.setCreatorPhone(user.getMobileWork());
        info.setCreatorUnit(user.getOrgName());
        info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
        String orderNum = redisService.genOrderNum(RedisKey.KEY_ORDER_NUM_PREFIX);;
        info.setOrderNumber(orderNum);
        this.save(info);
        saveYy(info);
    }

    @Override
    public IaasZysb getDetails(String id) {
        IaasZysb info = getBizData(id);
        if (null ==info) {
            return null;
        }

        return info;
    }

    @Override
    public IaasZysb getBizData(String id) {

        IaasZysb info = this.getById(id);
        List<IaasZysbXmxx> projectList = iaasZysbXmxxService.list(new QueryWrapper<IaasZysbXmxx>().lambda().eq(IaasZysbXmxx::getMasterId, info.getId()));
        info.setProjectList(projectList);
        if(CollectionUtils.isNotEmpty(projectList)){

            List<IaasReport> iaasListOfInfo = reportIaasService.list(new QueryWrapper<IaasReport>().lambda()
                                                                    .eq(IaasReport::getAppInfoId,id)
                                                                    .orderByDesc(IaasReport::getModifiedTime));
            
            List<PaasReportVo> paasListOfInfo = paasReportApi.find(id);
            
//            List<PaasReport> paasListOfInfo = reportPaasService.list(new QueryWrapper<PaasReport>().lambda()
//                                                                    .eq(PaasReport::getAppInfoId,id)
//                                                                    .orderByDesc(PaasReport::getModifiedTime));
            List<ReportFusionAccess> fusionAccessListOfInfo = reportFusionAccessService.list(new QueryWrapper<ReportFusionAccess>().lambda()
                                                                                            .eq(ReportFusionAccess::getAppInfoId,id)
                                                                                            .orderByDesc(ReportFusionAccess::getModifiedTime));
            List<ReportSpecial> specialListOfInfo = reportSpecialService.list(new QueryWrapper<ReportSpecial>().lambda()
                                                                                            .eq(ReportSpecial::getAppInfoId,id)
                                                                                            .orderByDesc(ReportSpecial::getModifiedTime));
            for(IaasZysbXmxx project:projectList){

            	
            	SubpageParam param = new SubpageParam();
            	param.setRefId(project.getId());
				List<FilesVo> filesList = filesApi.find(param );
            	
//                List<Files> filesList = filesService.getFilesByRefId(project.getId());
                project.setFileList(filesList);

                if(iaasListOfInfo != null){
                    List<IaasReport> iaasList = iaasListOfInfo.stream().filter(iaas->StringUtils.equals(iaas.getProjectId(),project.getId())).collect(Collectors.toList());
                    project.setIaasList(iaasList);
                }

                if(paasListOfInfo != null){
                    List<PaasReportVo> paasList = paasListOfInfo.stream().filter(paas->StringUtils.equals(paas.getProjectId(),project.getId())).collect(Collectors.toList());
                    project.setPaasList(paasList);
                }

                if(fusionAccessListOfInfo != null){
                    List<ReportFusionAccess> fusionAccessList = fusionAccessListOfInfo.stream().filter(fusion->StringUtils.equals(fusion.getProjectId(),project.getId())).collect(Collectors.toList());
                    project.setFusionAccessList(fusionAccessList);
                }

                if(specialListOfInfo != null){
                    List<ReportSpecial> specialList = specialListOfInfo.stream().filter(special->StringUtils.equals(special.getProjectId(),project.getId())).collect(Collectors.toList());
                    project.setSpecialList(specialList);
                }

            }
        }
        // 审核信息
//        List<AppReviewInfo> allReviewInfo = appReviewInfoService.getAllReviewInfoByAppInfoId(id);
//        info.setReviewList(allReviewInfo);
        return info;
    }

    @Override
    public IPage<IaasZysb> getPage(UserVO user, IPage<IaasZysb> page,Map<String, Object> param) {
        page = baseMapper.getPage(page, param);
        List<IaasZysb> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            for (IaasZysb record : records) {
                ApplicationInfoStatus ais = ApplicationInfoStatus.codeOf(record.getStatus());
                // 判断是否能删除
                if (ais != ApplicationInfoStatus.DELETE
                        && Objects.equals(user.getIdcard(), record.getCreator())) {
                    record.setCanDelete(true);
                }
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UserVO user, IaasZysb info) {
        this.updateById(info);
        iaasZysbXmxxService.remove(new QueryWrapper<IaasZysbXmxx>().lambda()
                .eq(IaasZysbXmxx::getMasterId, info.getId()));
        reportIaasService.remove(new QueryWrapper<IaasReport>().lambda()
                .eq(IaasReport::getAppInfoId, info.getId()));
        
        paasReportApi.remove(info.getId());
        
        reportFusionAccessService.remove(new QueryWrapper<ReportFusionAccess>().lambda()
                .eq(ReportFusionAccess::getAppInfoId, info.getId()));
        reportSpecialService.remove(new QueryWrapper<ReportSpecial>().lambda()
                .eq(ReportSpecial::getAppInfoId,info.getId()));

        saveYy(info);
    }


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void moveOldData() {


            List<IaasZysbXnj> xnjList = iaasZysbXnjService.list(new QueryWrapper<>());
            logger.debug("start moveVm2ReportIaas");
            moveVm2ReportIaas(xnjList);
            logger.debug("end moveVm2ReportIaas");

            List<IaasZysbDsj> dsjList = iaasZysbDsjService.list(new QueryWrapper<>());
            logger.debug("start   moveBigData2ReportPaas");
            moveBigData2ReportPaas(dsjList);
            logger.debug("end moveBigData2ReportPaas");

            List<IaasZysbSjk> sjkList = iaasZysbSjkService.list(new QueryWrapper<>());
            logger.debug("start   moveDb2ReportPaas");
            moveDb2ReportPaas(sjkList);
            logger.debug("end moveDb2ReportPaas");


            List<IaasZysbFbssjk> fbssjkList = iaasZysbFbssjkService.list(new QueryWrapper<>());
            logger.debug("start   moveLibra2ReportPaas");
            moveLibra2ReportPaas(fbssjkList);
            logger.debug("end moveLibra2ReportPaas");

    }


    private void moveVm2ReportIaas(List<IaasZysbXnj> xnjList){
        List<IaasReport> reportIaasList = Lists.newArrayList();
        for(IaasZysbXnj vm:xnjList){
            IaasReport IaasReport = new IaasReport();
            IaasReport.setId(null);
            IaasReport.setResourceName("弹性云服务器");
            IaasReport.setCpu(vm.getCpu());
            IaasReport.setMemory(vm.getMemorys());
            IaasReport.setDisk(vm.getStorages());
            IaasReport.setAppInfoId(vm.getMasterId());
            IaasReport.setProjectId(vm.getProjectId());
            IaasReport.setRemark(vm.getRemark());
            IaasReport.setCreateTime(vm.getCreateTime());
            IaasReport.setModifiedTime(vm.getModifiedTime());
            reportIaasList.add(IaasReport);
        }
        reportIaasService.saveBatch(reportIaasList);
    }

    private void moveBigData2ReportPaas(List<IaasZysbDsj> dsjList){
        List<PaasReportVo> reportPaasList = Lists.newArrayList();
        for(IaasZysbDsj dsj : dsjList){
        	PaasReportVo paas = new PaasReportVo();
            paas.setId(null);
            paas.setResourceName(dsj.getResourceName());
            paas.setAppInfoId(dsj.getMasterId());
            paas.setProjectId(dsj.getProjectId());
            paas.setCpu(dsj.getCpu());
            paas.setMemory(dsj.getMemorys());
            paas.setDisk(dsj.getStorages());
            paas.setRemark(dsj.getRemark());
            paas.setCreateTime(dsj.getCreateTime());
            paas.setModifiedTime(dsj.getModifiedTime());

            reportPaasList.add(paas);
        }
        paasReportApi.batchSave(reportPaasList);
    }

    private void moveDb2ReportPaas(List<IaasZysbSjk> sjkList){
        List<PaasReportVo> reportPaasList = Lists.newArrayList();
        for (IaasZysbSjk sjk : sjkList){
        	PaasReportVo paas = new PaasReportVo();
            paas.setId(null);
            paas.setResourceName("数据库");
            paas.setAppInfoId(sjk.getMasterId());
            paas.setProjectId(sjk.getProjectId());
            paas.setDisk(sjk.getStorages());
            paas.setRemark(sjk.getRemark());
            paas.setCreateTime(sjk.getCreateTime());
            paas.setModifiedTime(sjk.getModifiedTime());
            reportPaasList.add(paas);
        }
        paasReportApi.batchSave(reportPaasList);
    }

    private void moveLibra2ReportPaas(List<IaasZysbFbssjk> fbssjkList){

        List<PaasReportVo> reportPaasList = Lists.newArrayList();
        for(IaasZysbFbssjk fbssjk : fbssjkList){
        	PaasReportVo paas = new PaasReportVo();
            paas.setId(null);
            paas.setResourceName("Libra");
            paas.setAppInfoId(fbssjk.getMasterId());
            paas.setProjectId(fbssjk.getProjectId());
            paas.setCpu(Double.valueOf(fbssjk.getCpu()));
            paas.setMemory(fbssjk.getMemorys());
            paas.setDisk(fbssjk.getStorages());
            paas.setRemark(fbssjk.getRemark());
            paas.setCreateTime(fbssjk.getCreateTime());
            paas.setModifiedTime(fbssjk.getModifiedTime());
            reportPaasList.add(paas);
        }
        paasReportApi.batchSave(reportPaasList);
    }


    /**
     * 新报表导出
     * @param param
     * @return
     */
    public List<ReportExportVo> reportExport(Map<String,Object> param){
        List<ReportExportVo> res = baseMapper.getBaseExportInfo(param);
        if(CollectionUtils.isNotEmpty(res)){
            for (ReportExportVo reportExport: res){
                List <IaasReportVo> iaasList  = reportIaasService.doStatistics(reportExport.getId());
                if(CollectionUtils.isNotEmpty(iaasList)){
                    reportExport.setIaasList(iaasList);
                }

                List<PaasReportVo> paasList = paasReportApi.doStatistics(reportExport.getId());
                if(CollectionUtils.isNotEmpty(paasList)){
                    reportExport.setPaasList(paasList);
                }

                List<ReportFusionAccessVo> fusionAccessList = reportFusionAccessService.doStatistics(reportExport.getId());
                if(CollectionUtils.isNotEmpty(fusionAccessList)){
                    reportExport.setFusionAccessList(fusionAccessList);
                }
            }
        }
        return res;
    }

    @Override
    public List<IaasZysbExportVo> countExport(Map<String, Object> param) {
        List<IaasZysbExportVo> result = baseMapper.getListByCondition(param);
        if (result != null) {
        	
        	List<SysDictVO> zjmcDict = new ArrayList<SysDictVO>();
        	List<SysDictVO> sjkbbDict = new ArrayList<SysDictVO>();
        	String zjmcDictStr = systemApi.feignGetByValue("iaasZysbApply-zjmc");
        	if(StringUtils.isNotBlank(zjmcDictStr)) zjmcDict = JSON.parseArray(zjmcDictStr, SysDictVO.class);
        	
        	String sjkbbDictStr = systemApi.feignGetByValue("iaasZysbApply-sjkbb");
        	if(StringUtils.isNotBlank(sjkbbDictStr)) sjkbbDict = JSON.parseArray(sjkbbDictStr, SysDictVO.class);
        	
            List<String> zjmc = zjmcDict.stream().map(SysDictVO::getName).collect(Collectors.toList());
            List<String> sjkbb = sjkbbDict.stream().map(SysDictVO::getName).collect(Collectors.toList());
            for (IaasZysbExportVo zysb : result) {
                // 大数据组件统计
                List<IaasZysbDsj> bigdataList = iaasZysbDsjService.list(new QueryWrapper<IaasZysbDsj>().lambda()
                        .eq(IaasZysbDsj::getMasterId, zysb.getId()));
                List<IaasZysbDsjVo> bigdata = Lists.newArrayList();
                for (String s : zjmc) {
                    if (bigdataList != null) {
                        List<IaasZysbDsj> filterList = bigdataList.stream().filter(it -> Objects.equals(s, it.getResourceName())).collect(Collectors.toList());
                        Double cpu = filterList.stream().map(IaasZysbDsj::getCpu).reduce(Double::sum).orElse(0d);
                        Double memorys = filterList.stream().map(IaasZysbDsj::getMemorys).reduce(Double::sum).orElse(0d);
                        Double storages = filterList.stream().map(IaasZysbDsj::getStorages).reduce(Double::sum).orElse(0d);
                        IaasZysbDsjVo dsj = new IaasZysbDsjVo();
                        dsj.setResourceName(s);
                        dsj.setCpu(cpu);
                        dsj.setMemorys(memorys);
                        dsj.setStorages(storages);
                        bigdata.add(dsj);
                    } else {
                    	IaasZysbDsjVo dsj = new IaasZysbDsjVo();
                        dsj.setResourceName(s);
                        dsj.setCpu(0d);
                        dsj.setMemorys(0d);
                        dsj.setStorages(0d);
                        bigdata.add(dsj);
                    }
                }
                zysb.setBigdataList(bigdata);
                // 数据库信息统计
                List<IaasZysbSjk> databaseList = iaasZysbSjkService.list(new QueryWrapper<IaasZysbSjk>().lambda()
                        .eq(IaasZysbSjk::getMasterId, zysb.getId()));
                List<IaasZysbSjkVo> database = Lists.newArrayList();
                for (String s : sjkbb) {
                    if (databaseList != null) {
                        List<IaasZysbSjk> filterList = databaseList.stream().filter(it -> Objects.equals(s, it.getVersion())).collect(Collectors.toList());
                        Double storages = filterList.stream().map(IaasZysbSjk::getStorages).reduce(Double::sum).orElse(0d);
                        IaasZysbSjkVo sjk = new IaasZysbSjkVo();
                        sjk.setVersion(s);
                        sjk.setStorages(storages);
                        database.add(sjk);
                    } else {
                    	IaasZysbSjkVo sjk = new IaasZysbSjkVo();
                        sjk.setVersion(s);
                        sjk.setStorages(0d);
                        database.add(sjk);
                    }
                }
                zysb.setDatabaseList(database);
            }
        }
        return result;
    }


    private void saveYy(IaasZysb info){
        List<IaasZysbXmxx> projectList = info.getProjectList();
        for (IaasZysbXmxx project : projectList) {
            project.setId(null);
            project.setMasterId(info.getId());
            project.insert();
            // 文件
            SubpageParam param = new SubpageParam();
            param.setFiles(project.getFileList());
            param.setRefId(project.getId());
			filesApi.refFiles(param);
            // IaaS
            List<IaasReport> iaasList = project.getIaasList();
            if (iaasList != null) {
                for (IaasReport iaas : iaasList) {
                    iaas.setId(null);
                    iaas.setAppInfoId(info.getId());
                    iaas.setProjectId(project.getId());
                    reportIaasService.save(iaas);
                }
            }
            // PaaS
            List<PaasReportVo> paasList = project.getPaasList();
            if (paasList != null) {
                for (PaasReportVo paas : paasList) {
                    paas.setId(null);
                    paas.setAppInfoId(info.getId());
                    paas.setProjectId(project.getId());
                }
                paasReportApi.batchSave(paasList);
            }
            // 桌面云
            List<ReportFusionAccess> fusionAccessList = project.getFusionAccessList();
            if (fusionAccessList != null) {
                for (ReportFusionAccess fusionAccess : fusionAccessList) {
                    fusionAccess.setId(null);
                    fusionAccess.setAppInfoId(info.getId());
                    fusionAccess.setProjectId(project.getId());
                    fusionAccess.insert();
                }
            }

            // 特殊
            List<ReportSpecial> specialList = project.getSpecialList();
            if(CollectionUtils.isNotEmpty(specialList)){
                for(ReportSpecial special : specialList){
                    special.setId(null);
                    special.setAppInfoId(info.getId());
                    special.setProjectId(project.getId());
                    special.insert();
                }
            }
        }
    }

}
