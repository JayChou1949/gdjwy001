package com.hirisun.cloud.iaas.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.common.workbench.CommonHandler;
import com.hirisun.cloud.iaas.bean.IaasYzmyzy;
import com.hirisun.cloud.iaas.bean.IaasYzmyzyUser;
import com.hirisun.cloud.iaas.mapper.IaasYzmyzyMapper;
import com.hirisun.cloud.iaas.service.IIaasYzmyzyService;
import com.hirisun.cloud.iaas.service.IIaasYzmyzyUserService;
import com.hirisun.cloud.model.iaas.vo.CloudDesktopVO;
import com.hirisun.cloud.model.shopping.vo.ApplicationInfoVo;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.QueryVO;

/**
 * IaaS 云桌面云资源申请信息 服务实现类
 */
@Service
public class IaasYzmyzyServiceImpl extends ServiceImpl<IaasYzmyzyMapper, 
	IaasYzmyzy> implements IIaasYzmyzyService {

    @Autowired
    IIaasYzmyzyUserService iaasYzmyzyUserService;

    @Autowired
    private IaasYzmyzyMapper iaasYzmyzyMapper;


    /**
     * 桌面云需要建设单位等信息，不走新版购物车
     * @param shoppingCart
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveShoppingCart(ShoppingCartVo shoppingCart) {

    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void save(ApplicationInfoVo info) {
    	
    	List<IaasYzmyzy> serverList = JSON.parseObject(JSON.toJSON(info.getServerList()).toString(), 
    			new TypeReference<List<IaasYzmyzy>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
            for (IaasYzmyzy txyfw : serverList) {
                txyfw.setId(null);
                txyfw.setAppInfoId(info.getId());
                this.save(txyfw);

                List<IaasYzmyzyUser> userList = txyfw.getYzyUsers();
                if (userList != null && !userList.isEmpty()) {
                    for (IaasYzmyzyUser user : userList) {
                        user.setId(null);
                        user.setAppInfoId(info.getId());
                        user.setProjectName(info.getAppName());
                        user.setApplyType(txyfw.getApplyType());
                        iaasYzmyzyUserService.save(user);
                    }
                }
            }
        }
    }

    @Override
    public List<IaasYzmyzy> getByAppInfoId(String appInfoId) {
        List<IaasYzmyzy> tyxxList = this.list(new QueryWrapper<IaasYzmyzy>().lambda()
                .eq(IaasYzmyzy::getAppInfoId, appInfoId)
                .orderByAsc(IaasYzmyzy::getCreateTime));
        if (tyxxList != null && !tyxxList.isEmpty()) {
            for (IaasYzmyzy tyxx : tyxxList) {
                List<IaasYzmyzyUser> userList = iaasYzmyzyUserService.list(new QueryWrapper<IaasYzmyzyUser>().lambda()
                        .eq(IaasYzmyzyUser::getAppInfoId, appInfoId)
                        .orderByAsc(IaasYzmyzyUser::getCreateTime));
                tyxx.setYzyUsers(userList);
            }
        }
        return tyxxList;
    }

    @Override
    public List<IaasYzmyzy> getByShoppingCartId(String shoppingCartId) {
        return null;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(ApplicationInfoVo info) {
        this.remove(new QueryWrapper<IaasYzmyzy>().lambda().eq(IaasYzmyzy::getAppInfoId, info.getId()));
        iaasYzmyzyUserService.remove(new QueryWrapper<IaasYzmyzyUser>().lambda().eq(IaasYzmyzyUser::getAppInfoId, info.getId()));
        save(info);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateShoppingCart(ShoppingCartVo shoppingCart) {

    }

    @Override
    public Integer getTotalNum(String appInfoId) {
        return 1;
    }

    @Override
    public Integer getTotalNumInShoppingCart(String shoppingCartId) {
        return null;
    }

    /**
     * 购物车删除
     *
     * @param shoppingCartId 购物车ID
     */
    @Override
    public void deleteByShoppingCart(String shoppingCartId) {

    }

    /**
     * 服务关联订单
     *
     * @param shoppingCartId
     * @param appInfoId
     */
    @Override
    public void refAppInfoFromShoppingCart(String shoppingCartId, String appInfoId) {

    }

//    /**
//     * 老数据迁移
//     *
//     * @param shoppingCartId
//     * @param appInfoId
//     */
//    @Override
//    public void oldDataMove(String shoppingCartId, String appInfoId) {
//
//    }


    @Override
    public IPage<CloudDesktopVO> getResourcePage(Long pageNum, Long pageSize, UserVO user,QueryVO queryVO) {
        IPage<CloudDesktopVO> page = new Page<>(pageNum,pageSize);
        Map<String,Object> param = CommonHandler.handlerOfKeyWord(queryVO,user);
        page = iaasYzmyzyMapper.getResourcePage(page,param);
        return page;
    }
}
