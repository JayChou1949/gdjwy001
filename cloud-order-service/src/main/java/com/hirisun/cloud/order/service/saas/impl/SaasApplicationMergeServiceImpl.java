package com.hirisun.cloud.order.service.saas.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.BusinessName;
import com.upd.hwcloud.bean.contains.ModelName;
import com.upd.hwcloud.bean.contains.RedisKey;
import com.upd.hwcloud.bean.dto.ImplRequest;
import com.upd.hwcloud.bean.dto.SaasOrderTotal;
import com.upd.hwcloud.bean.dto.SaasTotal;
import com.upd.hwcloud.bean.dto.SaasUseTotal;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.SaasApplication;
import com.upd.hwcloud.bean.entity.SaasApplicationMerge;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.wfm.AdvanceBeanVO;
import com.upd.hwcloud.bean.entity.wfm.Workflow;
import com.upd.hwcloud.bean.entity.wfm.Workflowmodel;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.AreaPoliceCategoryUtils;
import com.upd.hwcloud.common.utils.OrderNum;
import com.upd.hwcloud.dao.SaasApplicationMergeMapper;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.ISaasApplicationMergeService;
import com.upd.hwcloud.service.ISaasApplicationService;
import com.upd.hwcloud.service.IUserService;
import com.upd.hwcloud.service.application.IAppReviewInfoService;
import com.upd.hwcloud.service.application.ISpeedUpService;
import com.upd.hwcloud.service.msg.MessageProvider;
import com.upd.hwcloud.service.wfm.IActivityService;
import com.upd.hwcloud.service.wfm.IInstanceService;
import com.upd.hwcloud.service.wfm.IWorkflowService;
import com.upd.hwcloud.service.wfm.IWorkflowmodelService;
import com.upd.hwcloud.service.workbench.impl.CommonHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>
 * SaaS资源申请合并信息表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2019-07-24
 */
@Service
public class SaasApplicationMergeServiceImpl extends ServiceImpl<SaasApplicationMergeMapper, SaasApplicationMerge> implements ISaasApplicationMergeService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ISaasApplicationService saasApplicationService;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private IInstanceService instanceService;
    @Autowired
    private IWorkflowmodelService workflowmodelService;
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private IFilesService filesService;
    @Autowired
    private IAppReviewInfoService appReviewInfoService;
    @Autowired
    private IUserService userService;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private ISpeedUpService speedUpService;

    private static String rootPath;

    @Value("${file.path}")
    public void setRootPath(String rootPath) {
        SaasApplicationMergeServiceImpl.rootPath = rootPath;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SaasApplicationMerge merge(User user, String ids) {
        String[] idArray = ids.split(",");
        SaasApplication application = saasApplicationService.getById(idArray[0]);
        Workflow workflow = null;
        //将可视化建模平台和通用应用流程区分开
        if ("1".equals(application.getVisible())){
            workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","SAAS_VISIBLE"));
        }else if ("2".equals(application.getVisible())){ //将广东公安数据接入平台和通用应用流程区分开
            workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","SAAS_DATA_ACCESS"));
        }else {
            workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","SAAS"));
        }
        if (workflow==null){
            throw new BaseException("未配置流程");
        }
        SaasApplicationMerge merge = new SaasApplicationMerge();
        merge.setCreator(user.getIdcard());
        merge.setCreatorName(user.getName());
        merge.setOrgId(user.getOrgId());
        merge.setOrgName(user.getOrgName());
        merge.setPostType(user.getPostType());
        merge.setMobileWork(user.getMobileWork());
        merge.setApplicationTime(new Date());
        merge.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
        merge.setWorkFlowId(workflow.getId());
        merge.setWorkFlowVersion(workflow.getVersion());
        String orderNum = OrderNum.gen(stringRedisTemplate, RedisKey.KEY_ORDER_NUM_PREFIX);
        merge.setOrderNumber(orderNum);
        merge.setAreas(user.getTenantArea());
        merge.setPoliceCategory(user.getTenantPoliceCategory());
        this.save(merge);
        // 更新原始单据为已关联
        List<SaasApplication> list = new ArrayList<>(idArray.length);
        for (String id : idArray) {
            SaasApplication saasApplication = new SaasApplication();
            saasApplication.setId(id);
            saasApplication.setMergeId(merge.getId());
            list.add(saasApplication);
        }
        saasApplicationService.updateBatchById(list);

        R r= instanceService.launchInstanceOfWorkFlow(user.getIdcard(), merge.getWorkFlowId(), merge.getId());

        Workflowmodel workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>()
                .eq("WORKFLOWID",workflow.getId()).eq("modelname", ModelName.LVL2_MANAGER.getName()).eq("VERSION",workflow.getVersion()));

        Map<String, String> modelMapToPerson = new HashMap<>();
        modelMapToPerson.put(workflowmodel.getId(), user.getIdcard());
        AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
        advanceBeanVO.setCurrentActivityId(r.get("data").toString());
        advanceBeanVO.setModelMapToPerson(modelMapToPerson);
        Map<String,String> map = new HashMap<>();
        map.put("name", BusinessName.SAAS_RESOURCE);
        map.put("order", merge.getOrderNumber());
        activityService.advanceCurrentActivity(advanceBeanVO, map);
        messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, BusinessName.SAAS_RESOURCE, merge.getOrderNumber()));
        return merge;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(User user, SaasApplicationMerge info) {
        info.setStatus(null);
        info.setOrderNumber(null);
        info.setWorkFlowId(null);
        this.updateById(info);
        // 关联文件信息
        filesService.refFiles(info.getFileList(), info.getId());
    }

    @Override
    public SaasApplicationMerge getDetails(String id) {
        SaasApplicationMerge info = this.getById(id);
        if (null == info) {
            return null;
        }
        info.setUser(userService.findUserByIdcard(info.getCreator()));
        info.setApplicationList(saasApplicationService.getListByMergeId(id));
        info.setFileList(filesService.getFilesByRefId(id));
        // 审核信息
        List<AppReviewInfo> allReviewInfo = appReviewInfoService.getAllReviewInfoByAppInfoId(id);
        info.setReviewList(allReviewInfo);
        // 实施审批信息
        AppReviewInfo implInfo = null;
        AppReviewInfo lastReviewInfo = appReviewInfoService.getLastPassReviewInfoByAppInfoId(id);
        if (lastReviewInfo != null && "2".equals(lastReviewInfo.getrType())) {
            // 最近一条审核记录为实施记录
            implInfo = lastReviewInfo;
            implInfo.setFileList(filesService.getFilesByRefId(implInfo.getId()));
            info.setImpl(implInfo);
        }
        return info;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(User user, String id) {
        SaasApplicationMerge info = this.getById(id);
        if (info == null) {
            throw new BaseException("该记录不存在!");
        }
        if (!Objects.equals(user.getIdcard(), info.getCreator())) {
            throw new BaseException("只能删除自己的申请!");
        }
        saasApplicationService.update(new SaasApplication(), new UpdateWrapper<SaasApplication>().lambda()
                .eq(SaasApplication::getMergeId, id)
                .set(SaasApplication::getMergeId, null)
                .set(SaasApplication::getStatus, ApplicationInfoStatus.INNER_REVIEW.getCode()));
        info.deleteById();
    }


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveImpl(User user, Map<String, Object> param, String modelId) {
        SaasApplicationMerge info = (SaasApplicationMerge) param.get("info");
        // 添加实施信息
        ImplRequest implRequest = (ImplRequest) param.get("implRequest");
        String result = implRequest.getResult();
        String remark = implRequest.getRemark();

        AppReviewInfo reviewInfo = new AppReviewInfo();
        reviewInfo.setCreator(user.getIdcard());
        reviewInfo.setResult(result);
        reviewInfo.setRemark(remark);
        reviewInfo.setrType("2");
        reviewInfo.setStepName(ModelName.CARRY.getName());
        reviewInfo.setFlowStepId(modelId);
        reviewInfo.setAppInfoId(info.getId());
        reviewInfo.insert();
        // 实施附件
        filesService.refFiles(implRequest.getFileList(), reviewInfo.getId());
        ApplicationInfoStatus status;
        if ("0".equals(result)) {
            // 驳回申请
            status = ApplicationInfoStatus.REVIEW;
        } else {
            // 实施步骤已完成,修改申请为使用状态
            status = ApplicationInfoStatus.USE;
            //通过合并后的id查找申请信息集合，然后依次给申请人发送短信
            List<SaasApplication> saasApplicationList = saasApplicationService.getListByMergeId(info.getId());
            for (SaasApplication saasApplication:saasApplicationList) {
                messageProvider.sendMessageAsync(messageProvider.buildCompleteMessage(saasApplication.getCreator(), BusinessName.SAAS_RESOURCE, saasApplication.getOrderNumber()));
            }
        }
        this.update(new SaasApplicationMerge(), new UpdateWrapper<SaasApplicationMerge>().lambda()
                .eq(SaasApplicationMerge::getId, info.getId())
                .set(SaasApplicationMerge::getStatus, status.getCode()));
        saasApplicationService.updateStatus(info.getId(), status.getCode());
    }

    @Override
    public IPage<SaasApplicationMerge> getFlowPage(User user, IPage<SaasApplicationMerge> page, Map<String, Object> param) {
        page = baseMapper.getFlowPage(page, param);
        List<SaasApplicationMerge> records = page.getRecords();
        if (records != null && !records.isEmpty()) {

            speedUpService.dealProcessingPersonApplication(records,user);
        }
        return page;
    }

    @Override
    public IPage<SaasApplicationMerge> getFlowPageWithServiceName(User user, IPage<SaasApplicationMerge> page, Map<String, Object> param) {
        page = baseMapper.getFlowPageWithServiceName(page, param);
        List<SaasApplicationMerge> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            speedUpService.dealProcessingPersonApplication(records,user);
        }
        return page;
    }

    @Override
    public List<SaasTotalVo> saasTotal(String areas, String policeCategory, Map<String, String> params) {
        List<SaasTotalVo> data = baseMapper.saasTotal(areas, policeCategory, params);
        for (int i = 0; i < data.size(); i++) {
            SaasTotalVo st = data.get(i);
            st.setId(i + 1);
            st.setApproveNum(st.getNum());
            st.setCarryNum(st.getNum());
            if (StringUtils.isNotEmpty(st.getAreas())) {
                st.setAreasPoliceCategory(st.getAreas());
            } else {
                st.setAreasPoliceCategory(st.getPoliceCategory());
            }
        }
        return data;
    }

    @Override
    public List<SaasOrderTotalVo> saasOrderTotal(String areas, String policeCategory, Map<String, String> params) {
        List<SaasOrderTotalVo> data = baseMapper.saasOrderTotal(areas, policeCategory, params);
        for (int i = 0; i < data.size(); i++) {
            SaasOrderTotalVo st = data.get(i);
            st.setId(i + 1);
            if ("0".equals(st.getApplyType())) {
                st.setAreasPoliceCategory(st.getAreas());
            } else {
                st.setAreasPoliceCategory(st.getPoliceCategory());
            }
        }
        return data;
    }

    @Override
    public List<SaasOrderTotalVo> saasOrderQuery(String areas, String policeCategory, Map<String, String> params) {
        List<SaasOrderTotalVo> data = baseMapper.saasOrderQuery(areas, policeCategory, params);
        for (int i = 0; i < data.size(); i++) {
            SaasOrderTotalVo st = data.get(i);
            st.setId(i + 1);
            if ("0".equals(st.getApplyType())) {
                st.setAreasPoliceCategory(st.getAreas());
            } else {
                st.setAreasPoliceCategory(st.getPoliceCategory());
            }
        }
        return data;
    }

    @Override
    public List<SaasUseTotalVo> saasUseTotal(String areas, String policeCategory, Map<String, String> params) {
        List<SaasUseTotalVo> data = baseMapper.saasUseTotal(areas, policeCategory, params);
        for (int i = 0; i < data.size(); i++) {
            SaasUseTotalVo st = data.get(i);
            st.setId(i + 1);
            if ("0".equals(st.getApplyType())) {
                st.setAreasPoliceCategory(st.getAreas());
            } else if(StringUtils.isNotBlank(st.getAreas()) && !"省厅".equals(st.getAreas())){ //地市不为空且不为省厅作为地市管理
                //属于二十一地市
                if(AreaPoliceCategoryUtils.isContainAreaName(CommonHandler.splitArea(st.getAreas()))){
                    st.setAreasPoliceCategory(st.getAreas());
                }
            }else {
                st.setAreasPoliceCategory(st.getPoliceCategory());
            }
            if ("-1".equals(st.getRecoverFlag())) {
                // 表示已经回收
                st.setStatus("否");
            } else {
                st.setStatus("是");
            }
        }
        return data;
    }

    @Override
    public void downloadFile(HttpServletResponse response) throws IOException {
        downFile(response, "建模平台用户开通申请总表.doc");
    }

    private void downFile(HttpServletResponse response, String name) throws IOException {
        File file = new File(rootPath+"/" + name);
        if (file.exists()) {
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            String originaName;
            try {
                originaName = URLEncoder.encode(name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                originaName = "建模平台用户开通申请总表.doc";
            }
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + originaName);
            FileUtils.copyFile(file, response.getOutputStream());
        }
    }

}
