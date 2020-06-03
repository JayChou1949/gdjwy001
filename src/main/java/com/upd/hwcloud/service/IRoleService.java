package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.Role;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-10-18
 */
public interface IRoleService extends IService<Role> {

    IPage<Role> getPage(Integer pageNum, Integer pageSize);

    List<Role> getList();

}
