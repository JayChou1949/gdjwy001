package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.vo.applicationInfoOrder.ReviewInfoVo;
import com.upd.hwcloud.dao.application.AppReviewInfoMapper;
import com.upd.hwcloud.service.application.IAppReviewInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务申请审核信息表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-12-04
 */
@Service
public class AppReviewInfoServiceImpl extends ServiceImpl<AppReviewInfoMapper, AppReviewInfo> implements IAppReviewInfoService {

    @Autowired
    private AppReviewInfoMapper appReviewInfoMapper;

    @Override
    public AppReviewInfo getLastPassReviewInfoByAppInfoId(String id) {
        return appReviewInfoMapper.getLastPassReviewInfoByAppInfoId(id);
    }

    @Override
    public AppReviewInfo getLastDenyReviewInfoByAppInfoId(String id) {
        return appReviewInfoMapper.getLastDenyReviewInfoByAppInfoId(id);
    }

    @Override
    public List<AppReviewInfo> getAllReviewInfoByAppInfoId(String id) {
        return appReviewInfoMapper.getAllReviewInfoByAppInfoId(id);
    }

    @Override
    public ReviewInfoVo getReviewInfoVoByAppInfoId(String name, String appInfoId) {
        return appReviewInfoMapper.getReviewInfoVoByAppInfoId(name,appInfoId);
    }

}
