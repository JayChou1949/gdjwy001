package com.upd.hwcloud.service.application.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.BusinessName;
import com.upd.hwcloud.bean.contains.ModelName;
import com.upd.hwcloud.bean.contains.ResourceRecoverStatus;
import com.upd.hwcloud.bean.dto.ImplRequest;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.application.ResourceRecover;
import com.upd.hwcloud.bean.entity.application.ResourceRecoverAppInfo;
import com.upd.hwcloud.bean.entity.application.ResourceRecoverImpl;
import com.upd.hwcloud.bean.entity.application.paas.rdb.PaasRdbBase;
import com.upd.hwcloud.dao.application.ResourceRecoverImplMapper;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.application.IResourceRecoverAppInfoService;
import com.upd.hwcloud.service.application.IResourceRecoverImplService;
import com.upd.hwcloud.service.application.IResourceRecoverService;
import com.upd.hwcloud.service.msg.MessageProvider;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资源回收实施表 服务实现类
 * </p>
 *
 * @author yyc
 * @since 2020-05-17
 */
@Service
public class ResourceRecoverImplServiceImpl extends ServiceImpl<ResourceRecoverImplMapper, ResourceRecoverImpl> implements IResourceRecoverImplService {

    @Autowired
    private IFilesService filesService;

    @Autowired
    private MessageProvider messageProvider;

    @Autowired
    private IResourceRecoverAppInfoService infoService;

    @Autowired
    private IResourceRecoverService resourceRecoverService;

    private static final Logger  logger = LoggerFactory.getLogger(ResourceRecoverImplServiceImpl.class);


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveImpl(User user, Map<String, Object> param, String modelid) throws Exception {
        logger.debug("param -> {}",param);
        logger.debug("modelid -.{}",modelid );

        ResourceRecoverAppInfo info = (ResourceRecoverAppInfo)  param.get("info");
        logger.debug("info -> {}",info);
        // 添加实施信息
        ImplRequest<ResourceRecoverImpl> implRequest = (ImplRequest<ResourceRecoverImpl>) param.get("implRequest");
        logger.debug("implRequest -> {}",implRequest);
        String result = implRequest.getResult();
        String remark = implRequest.getRemark();

        AppReviewInfo reviewInfo = new AppReviewInfo();
        reviewInfo.setCreator(user.getIdcard());
        reviewInfo.setResult(result);
        reviewInfo.setRemark(remark);
        reviewInfo.setrType("2");
        reviewInfo.setStepName(ModelName.CARRY.getName());
        reviewInfo.setFlowStepId(modelid);
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
