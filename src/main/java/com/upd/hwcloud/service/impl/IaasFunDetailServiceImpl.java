package com.upd.hwcloud.service.impl;

import com.upd.hwcloud.bean.entity.IaasFunDetail;
import com.upd.hwcloud.bean.entity.IaasFunDetailExp;
import com.upd.hwcloud.dao.IaasFunDetailMapper;
import com.upd.hwcloud.service.IIaasFunDetailExpService;
import com.upd.hwcloud.service.IIaasFunDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * IAAS功能详情 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
@Service
public class IaasFunDetailServiceImpl extends ServiceImpl<IaasFunDetailMapper, IaasFunDetail> implements IIaasFunDetailService {

    @Autowired
    IIaasFunDetailExpService detailExpService;
    @Override
    public void save(List<IaasFunDetail> funDetails, String masterId) {
        int i=1;
        for (IaasFunDetail scene:funDetails){
            scene.setId(null);
            scene.setIaasId(masterId);
            scene.setRemark(i+"");
            i++;
            this.save(scene);
            String appId = scene.getId();
            List<IaasFunDetailExp> supres = scene.getDetailExps();
            int j=1;
            for (IaasFunDetailExp supre:supres){
                supre.setId(null);
                supre.setRemark(j+"");
                j++;
                supre.setIaasId(masterId);
                supre.setAppId(appId);
            }
            detailExpService.saveBatch(supres);
        }
    }

    @Override
    public List<IaasFunDetail> getByIaasId(String iaasId) {
        return baseMapper.getByIaasId(iaasId);
    }
}
