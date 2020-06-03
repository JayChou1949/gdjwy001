package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.BusinessName;
import com.upd.hwcloud.bean.contains.ModelName;
import com.upd.hwcloud.bean.contains.RedisKey;
import com.upd.hwcloud.bean.dto.ImplRequest;
import com.upd.hwcloud.bean.dto.SaasRecoverTotal;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.wfm.AdvanceBeanVO;
import com.upd.hwcloud.bean.entity.wfm.Workflow;
import com.upd.hwcloud.bean.entity.wfm.Workflowmodel;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.OrderNum;
import com.upd.hwcloud.dao.SaasRecoverApplicationMapper;
import com.upd.hwcloud.service.*;
import com.upd.hwcloud.service.application.IAppReviewInfoService;
import com.upd.hwcloud.service.msg.MessageProvider;
import com.upd.hwcloud.service.wfm.IActivityService;
import com.upd.hwcloud.service.wfm.IInstanceService;
import com.upd.hwcloud.service.wfm.IWorkflowService;
import com.upd.hwcloud.service.wfm.IWorkflowmodelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangbao
 * @since 2019-10-24
 */
@Service
public class SaasRecoverApplicationServiceImpl extends ServiceImpl<SaasRecoverApplicationMapper, SaasRecoverApplication> implements ISaasRecoverApplicationService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ISaasRecoverInfoService saasRecoverInfoService;
    @Autowired
    private ISaasApplicationService saasApplicationService;
    @Autowired
    private SaasRecoverApplicationMapper saasRecoverApplicationMapper;
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private IWorkflowmodelService workflowmodelService;
    @Autowired
    private IAppReviewInfoService appReviewInfoService;
    @Autowired
    private IInstanceService instanceService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IFilesService filesService;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private MessageProvider messageProvider;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SaasRecoverApplication create(User user, SaasRecoverApplication info,String area,String policeCategory) {
        info.setId(null);
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        info.setOrgId(user.getOrgId());
        info.setOrgName(user.getOrgName());
        info.setPost(user.getPostType());
        info.setPhone(user.getPhone());
        String orderNum = OrderNum.gen(stringRedisTemplate, RedisKey.KEY_SAAS_PREFIX);
        info.setBillNum(orderNum);
        info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
        info.setAreas(!StringUtils.isEmpty(area)?area:null);
        info.setPoliceCategory(!StringUtils.isEmpty(policeCategory)?policeCategory:null);
        this.save(info);

        List<User> userList = info.getRecoverInfoList();
        for(User u : userList){
            List<SaasApplication> list = saasApplicationService.getSaasRecoverManageUseService(u.getIdcard());
            if(!list.isEmpty()){
                List<SaasRecoverInfo> recoverInfoList = Lists.newArrayList();
                for(SaasApplication saas : list){
                    saas.setRecoverFlag("99"); // 标记为正在回收
                    saas.setRecoverId(info.getId()); // 原始申请单和回收申请单关联
                    SaasRecoverInfo recoverInfo = new SaasRecoverInfo();
                    recoverInfo.setId(null);
                    recoverInfo.setAppInfoId(info.getId());
                    recoverInfo.setReIdcard(saas.getCreator());
                    recoverInfo.setReName(saas.getCreatorName());
                    recoverInfo.setReOrgId(saas.getOrgId());
                    recoverInfo.setReOrgName(saas.getOrgName());
                    recoverInfoList.add(recoverInfo);
                }
                saasRecoverInfoService.saveBatch(recoverInfoList);
                saasApplicationService.updateBatchById(list);
            }
        }
        filesService.refFiles(info.getFileList(),info.getId());//文件上传
        // 发起流程
        this.startFlow(user, info);
        return info;
    }

    private void startFlow(User user, SaasRecoverApplication info){
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","SAAS_POWER_RECOVER"));
        R r = instanceService.launchInstanceOfWorkFlow(user.getIdcard(), workflow.getId(), info.getId());
        Workflowmodel workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>()
                .eq("WORKFLOWID",workflow.getId()).eq("modelname", ModelName.RECHECK.getName()).eq("VERSION",workflow.getVersion()));
        Map<String, String> modelMapToPerson = new HashMap<>();
        Map<String, String> modelMapToAdviser = new HashMap<>();
        modelMapToPerson.put(workflowmodel.getId(), workflowmodel.getDefaulthandleperson());
        modelMapToAdviser.put(workflowmodel.getId(),workflowmodel.getAdviserperson());
        AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
        advanceBeanVO.setCurrentActivityId(r.get("data").toString());
        advanceBeanVO.setModelMapToPerson(modelMapToPerson);
        advanceBeanVO.setModelMapToAdviser(modelMapToAdviser);
        Map<String,String> map = new HashMap<>();
        map.put("name", BusinessName.SAAS_RECOVER_RESOURCE);
        map.put("order", info.getBillNum());
        activityService.advanceCurrentActivity(advanceBeanVO, map);
        messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, BusinessName.SAAS_RECOVER_RESOURCE, info.getBillNum()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRecoverInfo(User user, String id) {
        // 更新回收申请单中人员的服务状态,由 回收中-->未回收
        saasApplicationService.updateRecovering2NotRecover(id);
        // 删除回收服务
        saasRecoverInfoService.remove(new QueryWrapper<SaasRecoverInfo>().lambda().eq(SaasRecoverInfo::getAppInfoId, id));
        // 删除回收申请单
        this.removeById(id);
        activityService.terminationInstanceOfWorkFlow(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(User user, SaasRecoverApplication info) {
        info.setStatus(null);
        info.setBillNum(null);
        this.updateById(info);
    }

    @Override
    public void deleteById(User user,String id) {
        SaasRecoverApplication info = this.getById(id);
        if (info == null) {
            throw new BaseException("该记录不存在!");
        }
        if (!Objects.equals(user.getIdcard(), info.getCreator())) {
            throw new BaseException("只能删除自己的申请!");
        }
        // 逻辑删除,并设置相应的状态
        this.update(new SaasRecoverApplication(), new UpdateWrapper<SaasRecoverApplication>().lambda()
                .eq(SaasRecoverApplication::getId, id)
                .set(SaasRecoverApplication::getStatus, ApplicationInfoStatus.DELETE.getCode()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public IPage<SaasRecoverApplication> getPage(User user, IPage<SaasRecoverApplication> page, Map<String, Object> param) {
        page = saasRecoverApplicationMapper.getRecoverPage(page, param);
        List<SaasRecoverApplication> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            for (SaasRecoverApplication record : records) {
                boolean used = ApplicationInfoStatus.USE.getCode().equals(record.getStatus());
                if (!used && Objects.equals(user.getIdcard(), record.getCreator())) {
                    record.setCanDelete(true);
                }
            }
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public IPage<SaasRecoverApplication> getPageWithHandler(User user, IPage<SaasRecoverApplication> page, Map<String, Object> param) {
        page = saasRecoverApplicationMapper.getRecoverPageWithHandler(page, param);
        List<SaasRecoverApplication> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            for (SaasRecoverApplication record : records) {
                boolean used = ApplicationInfoStatus.USE.getCode().equals(record.getStatus());
                if (!used && Objects.equals(user.getIdcard(), record.getCreator())) {
                    record.setCanDelete(true);
                }
            }
        }
        return page;

    }

    @Override
    public SaasRecoverApplication getDetails(String id) {
        SaasRecoverApplication info = this.getById(id);
        if (null == info) {
            return null;
        }
        info.setUser(userService.findUserByIdcard(info.getCreator()));
        // 表单中用户当前在用服务
        List<SaasRecoverInfo> total = saasRecoverInfoService.getUserUseService(info.getId());
        info.setRecoverInfos(total);
        // 审核信息
        List<AppReviewInfo> allReviewInfo = appReviewInfoService.getAllReviewInfoByAppInfoId(id);
        info.setReviewList(allReviewInfo);
        // 实施审批信息
        AppReviewInfo implInfo = null;
        AppReviewInfo lastReviewInfo = appReviewInfoService.getLastPassReviewInfoByAppInfoId(id);
        //文件信息
        info.setFileList(filesService.getFilesByRefId(info.getId()));

        if (lastReviewInfo != null && "2".equals(lastReviewInfo.getrType())) {
            // 最近一条审核记录为实施记录
            implInfo = lastReviewInfo;
            implInfo.setFileList(filesService.getFilesByRefId(implInfo.getId()));
            info.setImpl(implInfo);
        }
        return info;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveImpl(User user, Map<String, Object> param, String modelId) {
        SaasRecoverApplication info = (SaasRecoverApplication) param.get("info");
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
            // 把申请单中包含的用户申请的所有在使用服务权限标记为回收(RecoverFlag = -1)
            saasApplicationService.updateUse2Recover(info.getId());
        }
        this.update(new SaasRecoverApplication(), new UpdateWrapper<SaasRecoverApplication>().lambda()
                .eq(SaasRecoverApplication::getId, info.getId())
                .set(SaasRecoverApplication::getStatus, status.getCode()));
    }

    /**
     * saas服务权限回收统计工单导出
     */
    @Override
    public List<SaasRecoverTotal> saasRecoverExport(String areas, String policeCategory, Map<String, String> params) {
        return baseMapper.saasRecoverExport(areas,policeCategory,params);
    }
    @Override
    public void updateStatus(String id, String status) {
        this.update(new SaasRecoverApplication(), new UpdateWrapper<SaasRecoverApplication>().lambda()
                .eq(SaasRecoverApplication::getId, id)
                .set(SaasRecoverApplication::getStatus, status));
    }

}
