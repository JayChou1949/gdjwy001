package com.hirisun.cloud.order.service.reported.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.api.paas.PaasReportApi;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.system.SystemApi;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.constant.RedisKey;
import com.hirisun.cloud.common.contains.ApplicationInfoStatus;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.WorkflowVO;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.bean.reported.*;
import com.hirisun.cloud.order.mapper.reported.IaasZysbMapper;
import com.hirisun.cloud.order.service.apply.ApplyReviewRecordService;
import com.hirisun.cloud.order.service.apply.ApplyService;
import com.hirisun.cloud.order.service.reported.*;
import com.hirisun.cloud.order.vo.OrderCode;
import com.hirisun.cloud.redis.service.RedisService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 申请信息表 服务实现类
 */
@Service
public class IaasZysbServiceImpl extends ServiceImpl<IaasZysbMapper, IaasZysb> implements IIaasZysbService {


    private static  final Logger logger = LoggerFactory.getLogger(IaasZysbServiceImpl.class);

    @Autowired
    private WorkflowApi workflowApi;

    @Autowired
    private ApplyService<IaasZysb> applyService;

    @Autowired
    private FilesApi filesApi;
    @Autowired
    private IIaasZysbXmxxService iaasZysbXmxxService;

    @Autowired
    private SystemApi systemApi;
    @Autowired
    private ReportIaasService reportIaasService;

    @Autowired
    private ReportPaasService reportPaasService;
    @Autowired
    private PaasReportApi paasReportApi;
    @Autowired
    private ReportFusionAccessService reportFusionAccessService;
    @Autowired
    private ReportSpecialService reportSpecialService;
    @Autowired
    private RedisService redisService;

    @Autowired
    private ApplyReviewRecordService applyReviewRecordService;


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

                SubpageParam param = new SubpageParam();
                param.setRefId(id);
                List<FilesVo> filesList = filesApi.find(param);
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
        List<ApplyReviewRecord> allReviewInfo = applyReviewRecordService.getAllReviewInfoByAppInfoId(id);
        info.setReviewList(allReviewInfo);
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
        reportIaasService.remove(new QueryWrapper<ReportIaas>().lambda()
                .eq(ReportIaas::getAppInfoId, info.getId()));
        
        paasReportApi.remove(info.getId());
        
        reportFusionAccessService.remove(new QueryWrapper<ReportFusionAccess>().lambda()
                .eq(ReportFusionAccess::getAppInfoId, info.getId()));
        reportSpecialService.remove(new QueryWrapper<ReportSpecial>().lambda()
                .eq(ReportSpecial::getAppInfoId,info.getId()));

        saveYy(info);
    }

    @Override
    public void create(UserVO user, IaasZysb info) {
        WorkflowVO workflow = workflowApi.getWorkflowByDefaultProcess("EPIDEMIC");
        if(workflow==null){
            throw new CustomException(OrderCode.WORKFLOW_MISSING);
        }
        info.setWorkFlowId(workflow.getId());
        info.setWorkFlowVersion(workflow.getVersion());
        this.save(user,info);
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
            List<ReportIaas> iaasList = project.getIaasList();
            if (iaasList != null) {
                for (ReportIaas iaas : iaasList) {
                    iaas.setId(null);
                    iaas.setAppInfoId(info.getId());
                    iaas.setProjectId(project.getId());

                }
                reportIaasService.saveBatch(iaasList);
            }
            // PaaS
            List<ReportPaas> paasList = project.getPaasList();
            if (paasList != null) {
                for (ReportPaas paas : paasList) {
                    paas.setId(null);
                    paas.setAppInfoId(info.getId());
                    paas.setProjectId(project.getId());
                }
                reportPaasService.saveBatch(paasList);
//                paasReportApi.batchSave(paasList);
            }
            // 桌面云
            List<ReportFusionAccess> fusionAccessList = project.getFusionAccessList();
            if (fusionAccessList != null) {
                for (ReportFusionAccess fusionAccess : fusionAccessList) {
                    fusionAccess.setId(null);
                    fusionAccess.setAppInfoId(info.getId());
                    fusionAccess.setProjectId(project.getId());
                }
                reportFusionAccessService.saveBatch(fusionAccessList);
            }

            // 特殊
            List<ReportSpecial> specialList = project.getSpecialList();
            if(CollectionUtils.isNotEmpty(specialList)){
                for(ReportSpecial special : specialList){
                    special.setId(null);
                    special.setAppInfoId(info.getId());
                    special.setProjectId(project.getId());
                }
                reportSpecialService.saveBatch(specialList);
            }
        }
    }

}
