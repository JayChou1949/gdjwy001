package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.EpidemicApplicationExt;
import com.upd.hwcloud.dao.EpidemicApplicationExtMapper;
import com.upd.hwcloud.service.IEpidemicApplicationExtService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  疫情应用申请信息扩展表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2020-02-27
 */
@Service
public class EpidemicApplicationExtServiceImpl extends ServiceImpl<EpidemicApplicationExtMapper, EpidemicApplicationExt> implements IEpidemicApplicationExtService {

    @Override
    public List<EpidemicApplicationExt> getListByMasterId(String id) {

        List<EpidemicApplicationExt> epidemicApplicationExtList = this.list(new QueryWrapper<EpidemicApplicationExt>().lambda().eq(EpidemicApplicationExt::getMasterId,id));
        return epidemicApplicationExtList;
    }
}
