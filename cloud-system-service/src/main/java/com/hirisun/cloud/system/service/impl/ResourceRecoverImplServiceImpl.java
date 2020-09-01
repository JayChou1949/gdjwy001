package com.hirisun.cloud.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.common.contains.ApplicationInfoStatus;
import com.hirisun.cloud.common.enumer.ModelName;
import com.hirisun.cloud.common.enumer.ResourceRecoverStatus;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.impl.vo.ImplRequestVo;
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.system.bean.ResourceRecover;
import com.hirisun.cloud.system.bean.ResourceRecoverAppInfo;
import com.hirisun.cloud.system.bean.ResourceRecoverImpl;
import com.hirisun.cloud.system.mapper.ResourceRecoverImplMapper;
import com.hirisun.cloud.system.service.IResourceRecoverAppInfoService;
import com.hirisun.cloud.system.service.IResourceRecoverImplService;
import com.hirisun.cloud.system.service.IResourceRecoverService;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 资源回收实施表 服务实现类
 */
@Service
public class ResourceRecoverImplServiceImpl extends ServiceImpl<ResourceRecoverImplMapper, ResourceRecoverImpl> implements IResourceRecoverImplService {

    @Autowired
    private FilesApi filesApi;

    @Autowired
    private SmsApi smsApi;

    @Autowired
    private IResourceRecoverAppInfoService infoService;

    @Autowired
    private IResourceRecoverService resourceRecoverService;

    private static final Logger  logger = LoggerFactory.getLogger(ResourceRecoverImplServiceImpl.class);


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveImpl(UserVO user, Map<String, Object> param, String modelid) throws Exception {
        logger.debug("param -> {}",param);
        logger.debug("modelid -.{}",modelid );

        ResourceRecoverAppInfo info = (ResourceRecoverAppInfo)  param.get("info");
        logger.debug("info -> {}",info);
        // 添加实施信息
        ImplRequestVo implRequest = (ImplRequestVo) param.get("implRequest");
        logger.debug("implRequest -> {}",implRequest);
        String result = implRequest.getResult();
        String remark = implRequest.getRemark();

        AppReviewInfoVo reviewInfo = new AppReviewInfoVo();
        reviewInfo.setCreator(user.getIdcard());
        reviewInfo.setResult(result);
        reviewInfo.setRemark(remark);
        reviewInfo.setRType("2");
        reviewInfo.setStepName(ModelName.CARRY.getName());
        reviewInfo.setFlowStepId(modelid);
        reviewInfo.setAppInfoId(info.getId());
//      TODO  reviewInfo.insert();
        // 实施附件
        
        SubpageParam subpageParam = new SubpageParam();
        subpageParam.setFiles(implRequest.getFileList());
        subpageParam.setRefId(reviewInfo.getId());
		filesApi.refFiles(subpageParam );
        
        
        ApplicationInfoStatus status;
        if ("0".equals(result)) {
            // 驳回申请
            status = ApplicationInfoStatus.REVIEW;
        } else {
            // 实施步骤已完成,修改申请为使用状态
            status = ApplicationInfoStatus.USE;
            smsApi.buildCompleteMessage(info.getCreator(), BusinessName.SAAS_RESOURCE, info.getOrderNumber());
        }

        //更新订单状态
        infoService.update(new ResourceRecoverAppInfo(), new UpdateWrapper<ResourceRecoverAppInfo>().lambda()
                .eq(ResourceRecoverAppInfo::getId, info.getId())
                .set(ResourceRecoverAppInfo::getStatus, status.getCode()));

        //
        resourceRecoverService.update(new ResourceRecover(),new UpdateWrapper<ResourceRecover>().lambda()
                .eq(ResourceRecover::getId, info.getId())
                .set(ResourceRecover::getStatus, ResourceRecoverStatus.PROCESSED.getCode()));

        //插入实施信息
        if(CollectionUtils.isNotEmpty(implRequest.getImplServerList())){
            this.remove(new QueryWrapper<ResourceRecoverImpl>().lambda().eq(ResourceRecoverImpl::getAppInfoId,info.getId()));
            String json =JSON.toJSONString(implRequest);
            logger.debug("json -> {}",json);
            List<ResourceRecoverImpl> implList = JSON.parseArray(JSON.parseObject(json).getString("implServerList"),ResourceRecoverImpl.class);

            //被回收人不同意回收的情况下 业务办理
            if(info.getRecoveredAgree().equals(0)){
                //最新修改，无论被回收人是否同意，只要业务办理选择缩配，就按照选择的类型保存数据
//                for(ResourceRecoverImpl impl:implList){
//                    impl.setProcessResult("暂不缩配");
//                }
            }
            for(ResourceRecoverImpl impl:implList){
                impl.setId(null);
                impl.setAppInfoId(info.getId());
            }
            this.saveBatch(implList);
        }
    }
}
