package com.upd.hwcloud.bean.dto;

import com.upd.hwcloud.bean.entity.Permission;

import java.util.*;

/**
 * 权限树形结构
 */
public class PermissionTree {
    //存储所有组织（键为组织ID）
    private HashMap<String, Permission> permissionsMap = new LinkedHashMap<String, Permission>();
    //组织树列表
    private List<Permission> permissionsList = new ArrayList<Permission>();

    public PermissionTree(List<Permission> list){
        initPermissionMap(list);
        initPermissionList();
    }

    private void initPermissionMap(List<Permission> list){
        Permission permission = null;
        for(Permission permission1 : list) {
            permissionsMap.put(permission1.getId(), permission1);
        }
        Iterator<Permission> iter = permissionsMap.values().iterator();
        Permission parentPermission = null;
        while(iter.hasNext()){
            permission = iter.next();
            if(permission.getPid() == null){
               continue;
            }
            parentPermission = permissionsMap.get(permission.getPid());
            if(parentPermission != null){
                parentPermission.addChild(permission);
            }else {
                permission.setPid(null);
            }
        }
    }
    private void initPermissionList(){
        if(permissionsList.size() > 0){
            return;
        }
        if(permissionsMap.size() == 0){
            return;
        }
        Iterator<Permission> iter = permissionsMap.values().iterator();
        Permission permission = null;
        while(iter.hasNext()){
            permission = iter.next();
            if(permission.getPid() == null){
                this.permissionsList.add(permission);
            }
        }
    }
    public List<Permission> getTree() {
        return this.permissionsList;
    }

    public Permission getChildList(List<Permission> list,Long permissionId) {
        Permission childpermission = null;
        if (null==list||list.size()==0){
            return childpermission;
        }

        List<Permission> childsList=new ArrayList<Permission>();
        for (Permission permission : list) {
            if (permissionId.equals(permission.getId())){
                return permission;
            }else {
                childpermission = getChildList(permission.getChildren(),permissionId);
                if (null!=childpermission){
                    return childpermission;
                }
            }
        }
        return null;
    }
}