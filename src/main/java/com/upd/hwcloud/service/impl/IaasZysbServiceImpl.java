package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.RedisKey;
import com.upd.hwcloud.bean.dto.IaasZysbExport;
import com.upd.hwcloud.bean.entity.Dict;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.IaasZysb;
import com.upd.hwcloud.bean.entity.IaasZysbDsj;
import com.upd.hwcloud.bean.entity.IaasZysbFbssjk;
import com.upd.hwcloud.bean.entity.IaasZysbSjk;
import com.upd.hwcloud.bean.entity.IaasZysbXmxx;
import com.upd.hwcloud.bean.entity.IaasZysbXnj;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.report.ReportFusionAccess;
import com.upd.hwcloud.bean.entity.report.ReportIaas;
import com.upd.hwcloud.bean.entity.report.ReportPaas;
import com.upd.hwcloud.bean.entity.report.ReportSpecial;
import com.upd.hwcloud.bean.vo.resourceReport.export.ReportExport;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.dao.IaasZysbMapper;
import com.upd.hwcloud.service.IDictService;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IIaasZysbDsjService;
import com.upd.hwcloud.service.IIaasZysbFbssjkService;
import com.upd.hwcloud.service.IIaasZysbService;
import com.upd.hwcloud.service.IIaasZysbSjkService;
import com.upd.hwcloud.service.IIaasZysbXmxxService;
import com.upd.hwcloud.service.IIaasZysbXnjService;
import com.upd.hwcloud.service.application.IAppReviewInfoService;
import com.upd.hwcloud.service.report.IReportFusionAccessService;
import com.upd.hwcloud.service.report.IReportIaasService;
import com.upd.hwcloud.service.report.IReportPaasService;
import com.upd.hwcloud.service.report.IReportSpecialService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 申请信息表 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-05-22
 */
@Service
public class IaasZysbServiceImpl extends ServiceImpl<IaasZysbMapper, IaasZysb> implements IIaasZysbService {


    private static  final Logger logger = LoggerFactory.getLogger(IaasZysbServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IAppReviewInfoService appReviewInfoService;
    @Autowired
    private IFilesService filesService;
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
    private IDictService dictService;
    @Autowired
    private IReportIaasService reportIaasService;
    @Autowired
    private IReportPaasService reportPaasService;
    @Autowired
    private IReportFusionAccessService reportFusionAccessService;
    @Autowired
    private IReportSpecialService reportSpecialService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(User user, IaasZysb info) {
        info.setId(null);
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        info.setCreatorPhone(user.getMobileWork());
        info.setCreatorUnit(user.getOrgName());
        info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
        String orderNum = this.genOrderNum();
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

            List<ReportIaas> iaasListOfInfo = reportIaasService.list(new QueryWrapper<ReportIaas>().lambda()
                                                                    .eq(ReportIaas::getAppInfoId,id)
                                                                    .orderByDesc(ReportIaas::getModifiedTime));
            List<ReportPaas> paasListOfInfo = reportPaasService.list(new QueryWrapper<ReportPaas>().lambda()
                                                                    .eq(ReportPaas::getAppInfoId,id)
                                                                    .orderByDesc(ReportPaas::getModifiedTime));
            List<ReportFusionAccess> fusionAccessListOfInfo = reportFusionAccessService.list(new QueryWrapper<ReportFusionAccess>().lambda()
                                                                                            .eq(ReportFusionAccess::getAppInfoId,id)
                                                                                            .orderByDesc(ReportFusionAccess::getModifiedTime));
            List<ReportSpecial> specialListOfInfo = reportSpecialService.list(new QueryWrapper<ReportSpecial>().lambda()
                                                                                            .eq(ReportSpecial::getAppInfoId,id)
                                                                                            .orderByDesc(ReportSpecial::getModifiedTime));
            for(IaasZysbXmxx project:projectList){

                List<Files> filesList = filesService.getFilesByRefId(project.getId());
                project.setFileList(filesList);

                if(iaasListOfInfo != null){
                    List<ReportIaas> iaasList = iaasListOfInfo.stream().filter(iaas->StringUtils.equals(iaas.getProjectId(),project.getId())).collect(Collectors.toList());
                    project.setIaasList(iaasList);
                }

                if(paasListOfInfo != null){
                    List<ReportPaas> paasList = paasListOfInfo.stream().filter(paas->StringUtils.equals(paas.getProjectId(),project.getId())).collect(Collectors.toList());
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
        List<AppReviewInfo> allReviewInfo = appReviewInfoService.getAllReviewInfoByAppInfoId(id);
        info.setReviewList(allReviewInfo);
        return info;
    }

    @Override
    public IPage<IaasZysb> getPage(User user, IPage<IaasZysb> page,Map<String, Object> param) {
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
    public void update(User user, IaasZysb info) {
        this.updateById(info);
        iaasZysbXmxxService.remove(new QueryWrapper<IaasZysbXmxx>().lambda()
                .eq(IaasZysbXmxx::getMasterId, info.getId()));
        reportIaasService.remove(new QueryWrapper<ReportIaas>().lambda()
                .eq(ReportIaas::getAppInfoId, info.getId()));
        reportPaasService.remove(new QueryWrapper<ReportPaas>().lambda()
                .eq(ReportPaas::getAppInfoId, info.getId()));
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
        List<ReportIaas> reportIaasList = Lists.newArrayList();
        for(IaasZysbXnj vm:xnjList){
            ReportIaas reportIaas = new ReportIaas();
            reportIaas.setId(null);
            reportIaas.setResourceName("弹性云服务器");
            reportIaas.setCpu(vm.getCpu());
            reportIaas.setMemory(vm.getMemorys());
            reportIaas.setDisk(vm.getStorages());
            reportIaas.setAppInfoId(vm.getMasterId());
            reportIaas.setProjectId(vm.getProjectId());
            reportIaas.setRemark(vm.getRemark());
            reportIaas.setCreateTime(vm.getCreateTime());
            reportIaas.setModifiedTime(vm.getModifiedTime());
            reportIaasList.add(reportIaas);
        }
        reportIaasService.saveBatch(reportIaasList);
    }

    private void moveBigData2ReportPaas(List<IaasZysbDsj> dsjList){
        List<ReportPaas> reportPaasList = Lists.newArrayList();
        for(IaasZysbDsj dsj : dsjList){
            ReportPaas paas = new ReportPaas();
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
        reportPaasService.saveBatch(reportPaasList);
    }

    private void moveDb2ReportPaas(List<IaasZysbSjk> sjkList){
        List<ReportPaas> reportPaasList = Lists.newArrayList();
        for (IaasZysbSjk sjk : sjkList){
            ReportPaas paas = new ReportPaas();
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
        reportPaasService.saveBatch(reportPaasList);
    }

    private void moveLibra2ReportPaas(List<IaasZysbFbssjk> fbssjkList){

        List<ReportPaas> reportPaasList = Lists.newArrayList();
        for(IaasZysbFbssjk fbssjk : fbssjkList){
            ReportPaas paas = new ReportPaas();
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
        reportPaasService.saveBatch(reportPaasList);
    }


    /**
     * 新报表导出
     * @param param
     * @return
     */
    public List<ReportExport> reportExport(Map<String,Object> param){
        List<ReportExport> res = baseMapper.getBaseExportInfo(param);
        if(CollectionUtils.isNotEmpty(res)){
            for (ReportExport reportExport: res){
                List <ReportIaas> iaasList  = reportIaasService.doStatistics(reportExport.getId());
                if(CollectionUtils.isNotEmpty(iaasList)){
                    reportExport.setIaasList(iaasList);
                }

                List<ReportPaas> paasList = reportPaasService.doStatistics(reportExport.getId());
                if(CollectionUtils.isNotEmpty(paasList)){
                    reportExport.setPaasList(paasList);
                }

                List<ReportFusionAccess> fusionAccessList = reportFusionAccessService.doStatistics(reportExport.getId());
                if(CollectionUtils.isNotEmpty(fusionAccessList)){
                    reportExport.setFusionAccessList(fusionAccessList);
                }
            }
        }
        return res;
    }


    @Override
    public List<IaasZysbExport> countExport(Map<String, Object> param) {
        List<IaasZysbExport> result = baseMapper.getListByCondition(param);
        if (result != null) {
            List<Dict> zjmcDict = dictService.getChildByValue("iaasZysbApply-zjmc");
            List<Dict> sjkbbDict = dictService.getChildByValue("iaasZysbApply-sjkbb");
            List<String> zjmc = zjmcDict.stream().map(Dict::getName).collect(Collectors.toList());
            List<String> sjkbb = sjkbbDict.stream().map(Dict::getName).collect(Collectors.toList());
            for (IaasZysbExport zysb : result) {
                // 大数据组件统计
                List<IaasZysbDsj> bigdataList = iaasZysbDsjService.list(new QueryWrapper<IaasZysbDsj>().lambda()
                        .eq(IaasZysbDsj::getMasterId, zysb.getId()));
                List<IaasZysbDsj> bigdata = Lists.newArrayList();
                for (String s : zjmc) {
                    if (bigdataList != null) {
                        List<IaasZysbDsj> filterList = bigdataList.stream().filter(it -> Objects.equals(s, it.getResourceName())).collect(Collectors.toList());
                        Double cpu = filterList.stream().map(IaasZysbDsj::getCpu).reduce(Double::sum).orElse(0d);
                        Double memorys = filterList.stream().map(IaasZysbDsj::getMemorys).reduce(Double::sum).orElse(0d);
                        Double storages = filterList.stream().map(IaasZysbDsj::getStorages).reduce(Double::sum).orElse(0d);
                        IaasZysbDsj dsj = new IaasZysbDsj();
                        dsj.setResourceName(s);
                        dsj.setCpu(cpu);
                        dsj.setMemorys(memorys);
                        dsj.setStorages(storages);
                        bigdata.add(dsj);
                    } else {
                        IaasZysbDsj dsj = new IaasZysbDsj();
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
                List<IaasZysbSjk> database = Lists.newArrayList();
                for (String s : sjkbb) {
                    if (databaseList != null) {
                        List<IaasZysbSjk> filterList = databaseList.stream().filter(it -> Objects.equals(s, it.getVersion())).collect(Collectors.toList());
                        Double storages = filterList.stream().map(IaasZysbSjk::getStorages).reduce(Double::sum).orElse(0d);
                        IaasZysbSjk sjk = new IaasZysbSjk();
                        sjk.setVersion(s);
                        sjk.setStorages(storages);
                        database.add(sjk);
                    } else {
                        IaasZysbSjk sjk = new IaasZysbSjk();
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
            filesService.refFiles(project.getFileList(), project.getId());
            // IaaS
            List<ReportIaas> iaasList = project.getIaasList();
            if (iaasList != null) {
                for (ReportIaas iaas : iaasList) {
                    iaas.setId(null);
                    iaas.setAppInfoId(info.getId());
                    iaas.setProjectId(project.getId());
                    iaas.insert();
                }
            }
            // PaaS
            List<ReportPaas> paasList = project.getPaasList();
            if (paasList != null) {
                for (ReportPaas paas : paasList) {
                    paas.setId(null);
                    paas.setAppInfoId(info.getId());
                    paas.setProjectId(project.getId());
                    paas.insert();
                }
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

    private String genOrderNum() {
        // 生成单号
        String yyyyMMdd = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String redisKey = RedisKey.KEY_IAAS_PREFIX + yyyyMMdd;
        Long increment = stringRedisTemplate.opsForValue().increment(redisKey, 1L);
        if (increment == null) {
            throw new BaseException("申请单号生成错误,请重试!");
        }
        // 过期时间1天
        stringRedisTemplate.expire(redisKey, 1L, TimeUnit.DAYS);
        return String.format("%s%04d", yyyyMMdd, increment);
    }

}
