package com.hirisun.cloud.platform.document.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.contains.ReviewStatus;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.document.bean.DevDoc;
import com.hirisun.cloud.platform.document.bean.DevDocClass;
import com.hirisun.cloud.platform.document.mapper.DevDocMapper;
import com.hirisun.cloud.platform.document.service.DevDocService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 文档 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
@Service
public class DevDocServiceImpl extends ServiceImpl<DevDocMapper, DevDoc> implements DevDocService {

    @Autowired
    private DevDocMapper devDocMapper;

    @Override
    public Page<DevDoc> getPage(Page<DevDoc> page,UserVO userVO, Map paramMap) {
        return devDocMapper.getPage(page,userVO,paramMap);
    }

    @Override
    public void saveOrUpdateDevDoc(UserVO user, DevDoc devDoc) {
        if (StringUtils.isEmpty(devDoc.getId())) {
            devDoc.setCreator(user.getIdCard());
            devDoc.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            this.save(devDoc);
        }else{
            devDoc.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            this.updateById(devDoc);
        }
    }

}
