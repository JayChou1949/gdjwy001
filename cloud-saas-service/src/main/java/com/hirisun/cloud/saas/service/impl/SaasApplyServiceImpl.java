package com.hirisun.cloud.saas.service.impl;

import com.hirisun.cloud.api.file.FileBindingApi;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.common.constant.RedisKey;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.util.OrderNumUtil;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.redis.service.RedisService;
import com.hirisun.cloud.saas.bean.SaasApply;
import com.hirisun.cloud.saas.bean.SaasApplyExt;
import com.hirisun.cloud.saas.bean.SaasApplyExtResource;
import com.hirisun.cloud.saas.mapper.SaasApplyMapper;
import com.hirisun.cloud.saas.service.SaasApplyExtResourceService;
import com.hirisun.cloud.saas.service.SaasApplyExtService;
import com.hirisun.cloud.saas.service.SaasApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * SaaS资源申请原始信息表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-24
 */
@Service
public class SaasApplyServiceImpl extends ServiceImpl<SaasApplyMapper, SaasApply> implements SaasApplyService {

    @Autowired
    private FileBindingApi fileBindingApi;

    @Autowired
    private SaasApplyExtService saasApplyExtService;

    @Autowired
    private SaasApplyExtResourceService saasApplyExtResourceService;

    @Autowired
    private SmsApi smsApi;

    @Autowired
    private RedisService redisService;

    @Override
    @Transactional
    public void create(UserVO user, SaasApply info) {
        info.setId(null);
        info.setCreator(user.getIdCard());
        info.setCreatorName(user.getName());
        info.setOrgId(user.getOrgId());
        info.setOrgName(user.getOrgName());
        info.setPostType(user.getPostType());
        info.setMobileWork(user.getMobileWork());
        info.setStatus(ApplyInfoStatus.INNER_REVIEW.getCode());
        String orderNum = redisService.genOrderNum(RedisKey.KEY_ORDER_NUM_PREFIX);
        info.setOrderNumber(orderNum);
        //设置申请单的用户id，同同一用户id  modfiy  by    qm 2020-7-30
        if(null!=user.getId()){
            info.setUserid(user.getId());
        }
        this.save(info);
        this.saveExt(info);
        this.saveExtResource(info);
        if (StringUtils.isNotEmpty(info.getFileIds())) {
            List<String> fileList = Arrays.asList(info.getFileIds().split(","));
            fileBindingApi.saveByIds(fileList, info.getId());
        }
        smsApi.buildSuccessMessage(user.getIdCard(),BusinessName.SAAS_RESOURCE, info.getOrderNumber());
    }

    private void saveExt(SaasApply info) {
        List<SaasApplyExt> list = info.getServiceList();
        if (list != null && !list.isEmpty()) {
            list.forEach(saasApplicationExt -> {
                saasApplicationExt.setId(null);
                saasApplicationExt.setMasterId(info.getId());
            });
            saasApplyExtService.saveBatch(list);
        }
    }
    private void saveExtResource(SaasApply info) {
        List<SaasApplyExtResource> list = info.getResourceList();
        if (list != null && !list.isEmpty()) {
            list.forEach(saasAppExtResource -> {
                saasAppExtResource.setId(null);
                saasAppExtResource.setMasterId(info.getId());
            });
            saasApplyExtResourceService.saveBatch(list);
        }
    }
}
