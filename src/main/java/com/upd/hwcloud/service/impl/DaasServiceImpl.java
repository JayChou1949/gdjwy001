package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.Daas;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.dao.DaasMapper;
import com.upd.hwcloud.service.IDaasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * DaaS 表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
@Service
public class DaasServiceImpl extends ServiceImpl<DaasMapper, Daas> implements IDaasService {

    @Autowired
    private DaasMapper daasMapper;

    @Override
    public IPage<Daas> getPage(IPage<Daas> page, User user, Integer status, String name, String subType) {
        return daasMapper.getPage(page, user, status, name, subType);
    }

    @Transactional
    @Override
    public Boolean serviceSort(String id, String ope) {
        Daas entity = baseMapper.selectById(id);
        Daas change = null;
        QueryWrapper<Daas> wrapper = new QueryWrapper<Daas>().eq("status",entity.getStatus()).eq("SUB_TYPE",entity.getSubType());
        if ("down".equals(ope)) {
           wrapper.gt("sort", entity.getSort()).orderByAsc("sort");
        }else if ("up".equals(ope)){
            wrapper.lt("sort", entity.getSort()).orderByDesc("sort");
        }
        List<Daas> pres =  baseMapper.selectPage(new Page<Daas>(1, 1),wrapper).getRecords();
        if (pres!=null&&pres.size()==1) {
            change = pres.get(0);
        }
        if (change!=null){
            Long sort = change.getSort();
            change.setSort(entity.getSort());
            entity.setSort(sort);
            baseMapper.updateById(entity);
            baseMapper.updateById(change);
        }
        return true;
    }
}
