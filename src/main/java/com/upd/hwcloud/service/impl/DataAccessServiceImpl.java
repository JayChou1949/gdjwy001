package com.upd.hwcloud.service.impl;

import com.upd.hwcloud.bean.entity.DataAccess;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.dao.DataAccessMapper;
import com.upd.hwcloud.service.IDataAccessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 数据接入表 服务实现类
 * </p>
 *
 * @author huru
 * @since 2019-03-13
 */
@Service
public class DataAccessServiceImpl extends ServiceImpl<DataAccessMapper, DataAccess> implements IDataAccessService {

    @Autowired
    private DataAccessMapper dataAccessMapper;

    @Override
    public List<DataAccess> getList(User user) {
        return dataAccessMapper.findListByHistory(user.getIdcard());
    }

    @Override
    public DataAccess get(String id) {
        return dataAccessMapper.details(id);
    }

    @Override
    public void saveDataAccess(DataAccess dataAccess) {
        dataAccessMapper.insert(dataAccess);
    }
}
