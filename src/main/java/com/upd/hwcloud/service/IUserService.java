package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.Role;
import com.upd.hwcloud.bean.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-10-12
 */
public interface IUserService extends IService<User> {

    IPage<User> getPage(IPage<User> page, String name, Long type);

    List<Role> getRolesByUserId(String idcard);

    User findUserByIdcard( String idcard);
    Set<String> findPermissionsByUserId(String userId);

    Set<String> findRolesByUserId(String userId);

    /**
     * 保存/更新,同步远程用户数据
     * @param remoteUser 获取的远程用户
     * @return 保存后的用户
     */
    void saveOrUpdateFromRemote(User remoteUser);

    List<User> findUserByIds(List<String> userIds);

    /**
     * 获取组织下用户(不包含施工人员)
     */
    List<User> findUserByOrgIds(List<String> orgIds);

    /**
     * 查询应用下用户(只包含施工人员)
     * @param manufacturerId 厂商id
     */
    List<User> findUserByManufacturerId(String manufacturerId);

    /**
     * 根据组织id和名字查询用户
     * @param orgId 组织id 或厂商id. 可以为空,表示不按此维度查询
     * @param name 名字
     */
    List<User> search(String orgId, String name);

    /**
     * 更改用户类型
     * @param idcard
     * @param type
     */
    void changeType(String idcard, Long type, String areas, String policeCategory, String nationalProject,String defaultTenant);

    boolean isManager(Long type);

    boolean isTenantManager(Long type);

    boolean newsAndCarouselAbility(Long type);

    boolean isNewsAndCarouselCrossPermission(User user,Integer provincial,String area,String policeCategory,String nationalProject);

    boolean isFlowPermission(User user,String area);

    boolean checkLimitPermission(User user,int opType,String area);
    /**
     * 获取身份证和名字的映射关系
     * @param idCards 身份证集合
     * @return map
     */
    Map<String,String> getIdCardsMapToName(String idCards);

    Map<String,String> getIdCardMapToNameByList(List<String> idCardList);


}
