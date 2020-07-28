package com.hirisun.cloud.user.service.impl;

import com.hirisun.cloud.user.bean.Permission;
import com.hirisun.cloud.user.mapper.PermissionMapper;
import com.hirisun.cloud.user.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * permission 权限表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 将系统菜单数据数据组装成树形结构数据
     * @return
     */
    @Override
    public List<Permission> listWithTree(List<Permission> list) {
        //TODO 设置redis时效
//        List<Permission> redisList= CacheUtils.getList("listWithTree:1",Permission.class);
//        if(redisList!=null&&redisList.size()>0){//有缓存，返回
//            return redisList;
//        }
        List<Permission> level1Menus= list.stream().filter(categoryEntity ->
                categoryEntity.getPid()==null
        ).map((menu)->{
            menu.setChildren(getChildrens(menu,list));
            return menu;
        }).sorted((menu1,menu2)->{
            return (int)((menu1.getSort()==null?99:menu1.getSort())-(menu2.getSort()==null?99:menu2.getSort()));
        }).collect(Collectors.toList());

//        CacheUtils.saveList("listWithTree:1",level1Menus);
        return level1Menus;
    }

    @Override
    public List<Permission> listRoleMenu(Map map) {
        return permissionMapper.listRoleMenu(map);
    }

    @Override
    public List<Permission> listUserMenu(Map map) {
        return permissionMapper.listUserMenu(map);
    }


    private List<Permission> getChildrens(Permission root,List<Permission> all){
        List<Permission> level1Menus= all.stream().filter(categoryEntity ->
                categoryEntity.getPid()!=null&&categoryEntity.getPid().equals(root.getId())
        ).map((menu)->{
            menu.setChildren(getChildrens(menu,all));
            return menu;
        }).sorted((menu1,menu2)->{
            return (int)((menu1.getSort()==null?99:menu1.getSort())-(menu2.getSort()==null?99:menu2.getSort()));
        }).collect(Collectors.toList());

        return level1Menus;
    }
}
