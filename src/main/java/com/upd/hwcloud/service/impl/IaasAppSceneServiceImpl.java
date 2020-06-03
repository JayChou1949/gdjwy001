package com.upd.hwcloud.service.impl;

import com.upd.hwcloud.bean.entity.IaasAppScene;
import com.upd.hwcloud.bean.entity.IaasAppSupre;
import com.upd.hwcloud.dao.IaasAppSceneMapper;
import com.upd.hwcloud.service.IIaasAppSceneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.service.IIaasAppSupreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * IAAS应用场景 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
@Service
public class IaasAppSceneServiceImpl extends ServiceImpl<IaasAppSceneMapper, IaasAppScene> implements IIaasAppSceneService {

    @Autowired
    IIaasAppSupreService appSupreService;
    @Override
    public void save(List<IaasAppScene> scenes, String masterId) {
        int i=1;
        for (IaasAppScene scene:scenes){
            scene.setId(null);
            scene.setRemark(i+"");
            scene.setIaasId(masterId);
            i++;
            this.save(scene);
            String appId = scene.getId();
            List<IaasAppSupre> supres = scene.getIaasSupres();
            int j = 1;
            for (IaasAppSupre supre:supres){
                supre.setId(null);
                supre.setRemark(j+"");
                supre.setIaasId(masterId);
                supre.setAppId(appId);
                j++;
            }
            appSupreService.saveBatch(supres);
        }
    }

    @Override
    public List<IaasAppScene> getByIaasId(String iaasId) {
        return baseMapper.getByIaasId(iaasId);
    }
}
