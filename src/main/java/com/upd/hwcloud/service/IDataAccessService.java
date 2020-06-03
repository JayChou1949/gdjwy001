package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.entity.DataAccess;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.User;

import java.util.List;

/**
 * <p>
 * 数据接入表 服务类
 * </p>
 *
 * @author huru
 * @since 2019-03-13
 */
public interface IDataAccessService extends IService<DataAccess> {

    List<DataAccess> getList(User user);

    DataAccess get(String id);

    void saveDataAccess(DataAccess dataAccess);
}
