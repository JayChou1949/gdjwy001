package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.BusinessName;
import com.upd.hwcloud.bean.contains.ModelName;
import com.upd.hwcloud.bean.contains.RedisKey;
import com.upd.hwcloud.bean.dto.ImplRequest;
import com.upd.hwcloud.bean.entity.EpidemicApplication;
import com.upd.hwcloud.bean.entity.EpidemicApplicationExt;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.SaasApplication;
import com.upd.hwcloud.bean.entity.SaasApplicationExt;
import com.upd.hwcloud.bean.entity.SaasApplicationMerge;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.wfm.Workflow;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.OrderNum;
import com.upd.hwcloud.dao.EpidemicApplicationMapper;
import com.upd.hwcloud.service.IEpidemicApplicationExtService;
import com.upd.hwcloud.service.IEpidemicApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IUserService;
import com.upd.hwcloud.service.application.IAppReviewInfoService;
import com.upd.hwcloud.service.application.ISpeedUpService;
import com.upd.hwcloud.service.msg.MessageProvider;
import com.upd.hwcloud.service.wfm.IWorkflowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  疫情应用申请信息表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2020-02-27
 */
@Service
public class EpidemicApplicationServiceImpl extends ServiceImpl<EpidemicApplicationMapper, EpidemicApplication> implements IEpidemicApplicationService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IFilesService filesService;

    @Autowired
    private IEpidemicApplicationExtService epidemicApplicationExtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAppReviewInfoService appReviewInfoService;


    @Autowired
    private ISpeedUpService speedUpService;

    @Autowired
    private MessageProvider messageProvider;

    @Override
    public void create(User user, EpidemicApplication info) {
        info.setId(null);
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        info.setOrgId(user.getOrgId());
        info.setOrgName(user.getOrgName());
        info.setPostType(user.getPostType());
        info.setMobileWork(user.getMobileWork());
        info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
        String orderNum = OrderNum.gen(stringRedisTemplate, RedisKey.KEY_ORDER_NUM_PREFIX);
        info.setOrderNumber(orderNum);
        /*if ("0".equals(info.getApplyType())) {
            info.setPoliceCategory("");
        } else {
            info.setAreas("");
        }*/
        this.save(info);
        this.saveExt(info);
        filesService.refFiles(info.getFileList(), info.getId());
    }

    @Override
    public EpidemicApplication getByActId(String activityId) {
        return baseMapper.getByActId(activityId);
    }

    private void saveExt(EpidemicApplication info) {
        List<EpidemicApplicationExt> list = info.getServiceList();
        if (list != null && !list.isEmpty()) {
            list.forEach(saasApplicationExt -> {
                saasApplicationExt.setId(null);
                saasApplicationExt.setMasterId(info.getId());
            });
            epidemicApplicationExtService.saveBatch(list);
        }
    }

    @Override
    public EpidemicApplication getDetails(String id) {
        EpidemicApplication epidemicApplication = this.getById(id);
        if(epidemicApplication == null){
            throw  new BaseException("该申请单不存在");
        }
        List<EpidemicApplicationExt> extList = epidemicApplicationExtService.getListByMasterId(id);
        epidemicApplication.setServiceList(extList);
        epidemicApplication.setFileList(filesService.getFilesByRefId(id));
        // 申请人
        User creator = userService.findUserByIdcard(epidemicApplication.getCreator());
        epidemicApplication.setUser(creator);
        // 审核信息
        List<AppReviewInfo> allReviewInfo = appReviewInfoService.getAllReviewInfoByAppInfoId(id);
        epidemicApplication.setReviewList(allReviewInfo);
        // 实施审批信息
        AppReviewInfo implInfo = null;
        AppReviewInfo lastReviewInfo = appReviewInfoService.getLastPassReviewInfoByAppInfoId(id);
        if (lastReviewInfo != null && "2".equals(lastReviewInfo.getrType())) {
            // 最近一条审核记录为实施记录
            implInfo = lastReviewInfo;
            List<Files> implFileList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, implInfo.getId()));
            implInfo.setFileList(implFileList);
            epidemicApplication.setImpl(implInfo);
        }
        return epidemicApplication;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(User user, EpidemicApplication info) {
        info.setStatus(null);
        info.setOrderNumber(null);
        if ("0".equals(info.getApplyType())) {
            info.setPoliceCategory("");
        } else {
            info.setAreas("");
        }
        this.updateById(info);
        epidemicApplicationExtService.remove(new QueryWrapper<EpidemicApplicationExt>().lambda()
                .eq(EpidemicApplicationExt::getMasterId, info.getId()));
        this.saveExt(info);
        filesService.refFiles(info.getFileList(), info.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(User user,String id) {
        EpidemicApplication info = this.getById(id);
        if (info == null) {
            throw new BaseException("该记录不存在!");
        }
        if (!Objects.equals(user.getIdcard(), info.getCreator())) {
            throw new BaseException("只能删除自己的申请!");
        }
        // 逻辑删除,并设置相应的状态
        this.update(new EpidemicApplication(), new UpdateWrapper<EpidemicApplication>().lambda()
                .eq(EpidemicApplication::getId, id)
                .set(EpidemicApplication::getStatus, ApplicationInfoStatus.DELETE.getCode()));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveImpl(User user, Map<String, Object> param, String modelId) {
        EpidemicApplication info = (EpidemicApplication) param.get("info");
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
            messageProvider.sendMessageAsync(messageProvider.buildCompleteMessage(info.getCreator(), BusinessName.SAAS_RESOURCE, info.getOrderNumber()));
        }
        this.update(new EpidemicApplication(), new UpdateWrapper<EpidemicApplication>().lambda()
                .eq(EpidemicApplication::getId, info.getId())
                .set(EpidemicApplication::getStatus, status.getCode()));
    }

    @Override
    public IPage<EpidemicApplication> getFlowPage(User user, IPage<EpidemicApplication> page, Map<String, Object> param) {
        page = baseMapper.getFlowPage(page, param);
        List<EpidemicApplication> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            speedUpService.dealProcessingPersonEpidemic(records,user);
        }
        return page;
    }


    @Override
    public IPage<EpidemicApplication> getFlowPageWithServiceName(User user, IPage<EpidemicApplication> page, Map<String, Object> param) {
        page = baseMapper.getFlowPageWithServiceName(page, param);
        List<EpidemicApplication> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            for (EpidemicApplication record : records) {
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
}
