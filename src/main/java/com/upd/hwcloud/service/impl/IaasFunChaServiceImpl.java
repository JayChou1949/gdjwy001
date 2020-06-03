package com.upd.hwcloud.service.impl;

import com.upd.hwcloud.bean.entity.IaasFunCha;
import com.upd.hwcloud.dao.IaasFunChaMapper;
import com.upd.hwcloud.service.IIaasFunChaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * IAAS功能特点 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
@Service
public class IaasFunChaServiceImpl extends ServiceImpl<IaasFunChaMapper, IaasFunCha> implements IIaasFunChaService {

    @Override
    public void save(List<IaasFunCha> funChara, String masterId) {
        int i=1;
        for (IaasFunCha scene:funChara){
            scene.setId(null);
            scene.setRemark(i+"");
            i++;
            scene.setIaasId(masterId);
        }
        this.saveBatch(funChara);
    }
}
