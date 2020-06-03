package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.InnerReviewUser;
import com.upd.hwcloud.dao.application.InnerReviewUserMapper;
import com.upd.hwcloud.service.application.IInnerReviewUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门内审核关联表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-12-19
 */
@Service
public class InnerReviewUserServiceImpl extends ServiceImpl<InnerReviewUserMapper, InnerReviewUser> implements IInnerReviewUserService {

    @Autowired
    private InnerReviewUserMapper innerReviewUserMapper;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void infoUser(String id, List<String> userIds) {
        removeByAppInfoId(id);
        List<InnerReviewUser> irList = new ArrayList<>();
        for (String userId : userIds) {
            InnerReviewUser innerReviewUser = new InnerReviewUser();
            innerReviewUser.setAppInfoId(id);
            innerReviewUser.setUserId(userId);
            irList.add(innerReviewUser);
        }
        // 与部门内审核人建立关联
        this.saveBatch(irList);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void removeByAppInfoId(String id) {
        this.remove(new QueryWrapper<InnerReviewUser>().lambda()
                .eq(InnerReviewUser::getAppInfoId, id));
    }

    @Override
    public List<InnerReviewUser> getList(String appInfoId, String userId) {
        List<InnerReviewUser> list = this.list(new QueryWrapper<InnerReviewUser>().lambda()
                .eq(InnerReviewUser::getAppInfoId, appInfoId)
                .eq(InnerReviewUser::getUserId, userId));
        return list;
    }

    @Override
    public void deleteAddAndForwardByResourceType(Long resourceType) {
        innerReviewUserMapper.deleteAddAndForwardByResourceType(resourceType);
    }

    @Override
    public void deleteAddAndForwardByServiceId(String serviceId) {
        innerReviewUserMapper.deleteAddAndForwardByServiceId(serviceId);
    }

}
