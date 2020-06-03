package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.Permission;
import com.upd.hwcloud.dao.PermissionMapper;
import com.upd.hwcloud.service.IPermissionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * permission 权限表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-11-19
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {



    @Override
    public List<Permission> selectRoleMenu(String rid,String pid,Integer type) {
        return baseMapper.selectRoleMenu(rid,pid, type);
    }

    @Override
    public List<Permission> selectUserMenu(String uid, String pid, Integer type) {
        return baseMapper.selectUserMenu(uid,pid, type);
    }

}
