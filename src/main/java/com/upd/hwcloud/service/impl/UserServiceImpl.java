package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.NewsType;
import com.upd.hwcloud.bean.contains.UnifledUserType;
import com.upd.hwcloud.bean.contains.UserType;
import com.upd.hwcloud.bean.entity.Role;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.dao.UserMapper;
import com.upd.hwcloud.service.IRoleService;
import com.upd.hwcloud.service.IUserRoleService;
import com.upd.hwcloud.service.IUserService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-10-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public IPage<User> getPage(IPage<User> page, String name, Long type) {
        page = userMapper.getPage(page, name, type);
        return page;
    }

    @Override
    public List<Role> getRolesByUserId(String idcard) {
        Map<String, Role> roleMap = getRoleMap();
        return getRolesByUserId(idcard, roleMap);
    }

    private List<Role> getRolesByUserId(String idcard, Map<String, Role> roleMap) {
        List<String> roleIds = userMapper.getRoleIdsByUserId(idcard);
        List<Role> userRoles = new ArrayList<>();
        if (roleIds != null && !roleIds.isEmpty()) {
            for (String roleId : roleIds) {
                userRoles.add(roleMap.get(roleId));
            }
        }
        return userRoles;
    }

    private Map<String, Role> getRoleMap() {
        List<Role> allRoles = roleService.getList();
        Map<String, Role> roleMap = new HashMap<>();
        if (allRoles != null && !allRoles.isEmpty()) {
            for (Role role : allRoles) {
                roleMap.put(role.getId(), role);
            }
        }
        return roleMap;
    }

    @Override
    public User findUserByIdcard(String idcard) {
        if (StringUtils.isEmpty(idcard)) {
            return null;
        }
        return userMapper.findUserByIdcard(idcard);
    }

    @Override
    public Set<String> findPermissionsByUserId(String userId) {
        List<String> list = userMapper.findPermissionsByUserId(userId);
        return new HashSet<String>(list);
    }

    @Override
    public Set<String> findRolesByUserId(String userId) {
        return new HashSet<String>(userMapper.findRolesByUserId(userId));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveOrUpdateFromRemote(User remoteUser) {
        if(StringUtils.isEmpty(remoteUser.getIdcard())) {
            return;
        }
        User dbUser = this.findUserByIdcard(remoteUser.getIdcard()); // 查询数据库
        if (dbUser == null) {
            dbUser = this.getById(remoteUser.getId());
            if (dbUser == null) {
                remoteUser.setType(UserType.NORMAL.getCode()); // 分配普通数据权限
                remoteUser.insert(); // 插入用户
                userRoleService.userRole(remoteUser.getIdcard(), "123456"); // 分配普通角色权限
            } else {
                if (dbUser.getName().equals(remoteUser.getName())) {
                    this.excludeFeild(remoteUser, dbUser);
                    remoteUser.updateById();
                }
            }
        } else {
            this.excludeFeild(remoteUser, dbUser);
            LambdaUpdateWrapper<User> eq = new UpdateWrapper<User>().lambda()
                    .eq(User::getIdcard, dbUser.getIdcard());
            remoteUser.update(eq);
        }
    }

    private void excludeFeild(User remoteUser, User dbUser) {
        if (!"1".equals(remoteUser.getIsParentOrg())) {
            if ("1".equals(dbUser.getIsParentOrg())) {
                // 需要更新的信息不是主机构,数据库存的是主机构的情况下,不更新机构信息
                remoteUser.setOrgId(null);
                remoteUser.setOrgName(null);
                remoteUser.setIsParentOrg(null);
            }
        }
        if (StringUtils.isEmpty(remoteUser.getPostType())) {
            remoteUser.setPostType(null);
        }
    }

    @Override
    public List<User> findUserByIds(List<String> userIds) {
        return this.list(new QueryWrapper<User>().lambda().in(User::getIdcard, userIds));
    }

    @Override
    public List<User> findUserByOrgIds(List<String> orgIds) {
        LambdaQueryWrapper<User> qw = new QueryWrapper<User>().lambda();
        qw.ne(User::getUserType, UnifledUserType.WB.getCode());
        qw.ne(User::getJobType,"15");
        qw.in(User::getOrgId, orgIds);
        qw.orderByAsc(User::getSortNo);
        return this.list(qw);
    }

    @Override
    public List<User> findUserByManufacturerId(String manufacturerId) {
        return userMapper.findUserByManufacturerId(manufacturerId);
    }

    @Override
    public List<User> search(String orgId, String name) {
        List<User> search = userMapper.search(orgId, name);
        search.stream().filter(user -> UnifledUserType.WB.getCode().equals(user.getUserType()))
                .forEach(user -> user.setOrgName(user.getCompanyName()));
        return search;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeType(String idcard, Long type, String areas, String policeCategory, String nationalProject,String defaultTenant) {
        if (UserType.NORMAL.getCode().equals(type)
                || UserType.COMPANY.getCode().equals(type)
                || UserType.MANAGER.getCode().equals(type) || UserType.PROVINCIAL_MANAGER.getCode().equals(type)) {
            // 清空租户管理员对应的警种或地市
            this.update(new User(), new UpdateWrapper<User>().lambda()
                    .eq(User::getIdcard, idcard)
                    .set(User::getType, type)
                    .set(User::getTenantArea, null)
                    .set(User::getTenantPoliceCategory, null)
                    .set(User::getDefaultTenant, null));
        } else if (UserType.TENANT_MANAGER.getCode().equals(type)) {
            if ("省厅".equals(policeCategory)) {
                throw new BaseException("地市不能设置为省厅");
            }
            if ("1".equals(defaultTenant)) {
                if (StringUtils.isNotEmpty(areas)) {
                    User u = this.getOne(new QueryWrapper<User>().lambda()
                            .eq(User::getType, UserType.TENANT_MANAGER.getCode())
                            .eq(User::getTenantArea, areas)
                            .eq(User::getDefaultTenant, "1")
                            .ne(User::getIdcard, idcard));
                    if (u != null) {
                        throw new BaseException("该地市已经设置过第一租户管理员: " + u.getName());
                    }
                    this.update(new User(), new UpdateWrapper<User>().lambda()
                            .eq(User::getIdcard, idcard)
                            .set(User::getType, type)
                            .set(User::getDefaultTenant, "1")
                            .set(User::getTenantArea, areas)
                            .set(User::getTenantPoliceCategory, null)
                            .set(User::getNationalProject,null));
                } else if (StringUtils.isNotEmpty(policeCategory)) {
                    User u = this.getOne(new QueryWrapper<User>().lambda()
                            .eq(User::getType, UserType.TENANT_MANAGER.getCode())
                            .eq(User::getPoliceCategory, policeCategory)
                            .eq(User::getDefaultTenant, "1")
                            .ne(User::getIdcard, idcard));
                    if (u != null) {
                        throw new BaseException("该警种已经设置过第一租户管理员: " + u.getName());
                    }
                    this.update(new User(), new UpdateWrapper<User>().lambda()
                            .eq(User::getIdcard, idcard)
                            .set(User::getType, type)
                            .set(User::getDefaultTenant, "1")
                            .set(User::getTenantArea, null)
                            .set(User::getTenantPoliceCategory, policeCategory)
                            .set(User::getNationalProject,null));
                }else if(StringUtils.isNotEmpty(nationalProject)){ //如果国家专项
                    User u = this.getOne(new QueryWrapper<User>().lambda()
                            .eq(User::getType, UserType.TENANT_MANAGER.getCode())
                            .eq(User::getNationalProject, nationalProject)
                            .eq(User::getDefaultTenant, "1")
                            .ne(User::getIdcard, idcard));
                    if (u != null) {
                        throw new BaseException("该专项已经设置过第一租户管理员: " + u.getName());
                    }
                    this.update(new User(), new UpdateWrapper<User>().lambda()
                            .eq(User::getIdcard, idcard)
                            .set(User::getType, type)
                            .set(User::getDefaultTenant, "1")
                            .set(User::getTenantArea, null)
                            .set(User::getTenantPoliceCategory, null)
                            .set(User::getNationalProject,nationalProject));
                }
            } else {
                if (StringUtils.isNotEmpty(areas)) {
                    this.update(new User(), new UpdateWrapper<User>().lambda()
                            .eq(User::getIdcard, idcard)
                            .set(User::getType, type)
                            .set(User::getDefaultTenant, "0")
                            .set(User::getTenantArea, areas)
                            .set(User::getTenantPoliceCategory, null)
                            .set(User::getNationalProject,null));
                } else if (StringUtils.isNotEmpty(policeCategory)) {
                    this.update(new User(), new UpdateWrapper<User>().lambda()
                            .eq(User::getIdcard, idcard)
                            .set(User::getType, type)
                            .set(User::getDefaultTenant, "0")
                            .set(User::getTenantArea, null)
                            .set(User::getTenantPoliceCategory, policeCategory)
                            .set(User::getNationalProject,null));
                }else if(StringUtils.isNotEmpty(nationalProject)){
                    this.update(new User(), new UpdateWrapper<User>().lambda()
                            .eq(User::getIdcard, idcard)
                            .set(User::getType, type)
                            .set(User::getDefaultTenant, "0")
                            .set(User::getTenantArea, null)
                            .set(User::getTenantPoliceCategory, null)
                            .set(User::getNationalProject,nationalProject));
                }
            }
        }
    }


    /**
     * 是否是租户管理员
     * @param type
     * @return
     */
    public boolean isTenantManager(Long type){
        return UserType.TENANT_MANAGER.getCode().longValue() == type.longValue();
    }

    /**
     * 是否是管理员
     * @param type
     * @return
     */
    public boolean isManager(Long type){
        return UserType.MANAGER.getCode().longValue() == type.longValue();
    }

    public boolean isProvincial(Long type){
        return UserType.PROVINCIAL_MANAGER.getCode().longValue() == type.longValue();
    }

    /**
     * 是否有编辑新闻和轮播图的权限
     * @param type
     * @return true 有权限
     */
    @Override
    public boolean newsAndCarouselAbility(Long type) {

        if(isManager(type) || isTenantManager(type) || isProvincial(type)){
            return  true;
        }
        return false;
    }



    /**
     * 新闻和轮播图是否越权操作（管理员可以操作全部）
     * @param user
     * @param provincial
     * @param area
     * @param policeCategory
     * @return true 为越权， false为有权
     */
    public boolean isNewsAndCarouselCrossPermission(User user,Integer provincial,String area,String policeCategory,String nationalProject){
        long userType = user.getType();
        //管理员有所有权限
        if(userType == UserType.MANAGER.getCode()){
            return false;
        }
        //省厅新闻 => 省厅管理员
        if(NewsType.PROVINCE.getCode().equals(provincial)){
            //不是省厅管理员 越权
            if(!(UserType.PROVINCIAL_MANAGER.getCode() == userType)){
                return  true;
            }
        }else if(NewsType.AREA.getCode().equals(provincial)){
            //地市新闻，地市名不匹配返回越权
            if(!org.apache.commons.lang3.StringUtils.equals(area,user.getTenantArea()) ||
                    StringUtils.isEmpty(user.getTenantArea())){
                return  true;
            }
        }else if(NewsType.POLICE.getCode().equals(provincial)){
            //警种新闻，警种名不匹配时返回越权
            if(!org.apache.commons.lang3.StringUtils.equals(policeCategory,user.getTenantPoliceCategory()) ||
                    StringUtils.isEmpty(user.getTenantPoliceCategory())){
                return true;
            }
        }else if(NewsType.NATIONAL_PROJECT.getCode().equals(provincial) ||
                StringUtils.isEmpty(user.getNationalProject())){
            if(!org.apache.commons.lang3.StringUtils.equals(nationalProject,user.getNationalProject())){
                return true;
            }
        }
        //未越权
        return false;
    }

    /**
     * 用户是否有操作流程的权限
     * @param user  当前用户
     * @param area  流程所属地市
     * @return
     */
    @Override
    public boolean isFlowPermission(User user,String area){
        //用户类型
        long type = user.getType();
        //管理用户有所有权限
        if(type==UserType.MANAGER.getCode()){
            return true;
        }

        if(type == UserType.PROVINCIAL_MANAGER.getCode()){
            //地市为空有权限
            return StringUtils.equals(area,"省厅");
        }
        //二级租户有操作本地市的权限
        if(type==UserType.TENANT_MANAGER.getCode()){
            return StringUtils.equals(user.getTenantArea(),area);
        }
        return false;
    }

    /**
     * 限额权限检测
     * @param user
     * @param opType 0 ：查 1： 增删改
     * @param area
     * @return
     */
    @Override
    public boolean checkLimitPermission(User user,int opType, String area) {
        long type = user.getType();
        if(opType == 0){
            return  limitShowPermission(type,area,user.getTenantArea());
        }else if(opType == 1){
            return limitModifiedInsertAndDeletePermission(type);
        }
        return false;
    }


    /**
     * 限额查看权限
     * @param type 用户类型
     * @param area 查看地区
     * @param tenantArea 租户地区
     * @return
     */
    private boolean limitShowPermission(long type,String area,String tenantArea){
        //管理员和省厅管理员能看全部 直接返回
        if(type == UserType.PROVINCIAL_MANAGER.getCode() || type == UserType.MANAGER.getCode()){
            return true;
        }else if (type == UserType.TENANT_MANAGER.getCode()){
            //租户管理能看本地市的
            if(StringUtils.isNotBlank(area)){
                return StringUtils.equals(area,tenantArea);
            }
        }
        return false;
    }


    /**
     * 限额增删改权限
     * @param type
     * @return
     */
    private boolean limitModifiedInsertAndDeletePermission(long type){
        if(type == UserType.PROVINCIAL_MANAGER.getCode() || type == UserType.MANAGER.getCode()){
            return true;
        }
        return false;
    }

    /**
     * 获取身份证号和名字的映射关系
     * @param idCards 身份证号集(','分割)
     * @return map
     */
    @Override
    public Map<String,String> getIdCardsMapToName(String idCards){
        logger.info("用户映射查询开始");
        long start = System.currentTimeMillis();
        Map<String,String> result = Maps.newHashMapWithExpectedSize(7);
        if(StringUtils.isNotEmpty(idCards)){
            List<String> idCardList = Splitter.on(",").splitToList(idCards);
            List<String> distinctIdCardList = idCardList.stream().distinct().collect(Collectors.toList());
            List<User> userList = userMapper.selectList(new QueryWrapper<User>().lambda().in(User::getIdcard,distinctIdCardList).isNotNull(User::getName));
            //K重复 或 V为null会异常
            result = userList.stream().collect(Collectors.toMap(User::getIdcard,User::getName));
        }
        logger.info("用户映射查询结束，耗时:{}",System.currentTimeMillis()-start + "ms");
        return result;
    }

    /**
     * 获取身份证号和名字的映射关系
     * @param idCardList list
     * @return map
     */
    @Override
    public Map<String,String> getIdCardMapToNameByList(List<String> idCardList){
        logger.info("用户映射查询开始");
        long start = System.currentTimeMillis();
        Map<String,String> result = Maps.newHashMapWithExpectedSize(7);
        if(CollectionUtils.isNotEmpty(idCardList)){

            List<String> distinctIdCardList = idCardList.stream().distinct().collect(Collectors.toList());
            //只返回有名字的
            List<User> userList = userMapper.selectList(new QueryWrapper<User>().lambda().in(User::getIdcard,distinctIdCardList).isNotNull(User::getName));
            //K重复 或 V为null会异常
            result = userList.stream().collect(Collectors.toMap(User::getIdcard,User::getName));
        }
        logger.info("用户映射查询结束，耗时:{}",System.currentTimeMillis()-start + "ms");
        return result;
    }
}
