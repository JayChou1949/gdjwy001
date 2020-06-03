package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.SaasRegister;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;
import com.upd.hwcloud.bean.vo.workbench.ResourceOverviewVO;
import com.upd.hwcloud.bean.vo.workbench.SaasStatisticsVO;

import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.crypto.hash.Hash;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * SAAS服务注册表 服务类
 * </p>
 *
 * @author zwb
 * @since 2019-05-27
 */
public interface ISaasRegisterService extends IRegisterService<SaasRegister> {

    R applicationRegisterApply(User user, QueryVO queryVO);

    IPage<SaasRegister> getWorkbenchApplyPage(User user, IPage<SaasRegister> page, QueryVO queryVO);

    IPage<SaasStatisticsVO> saasRegisterStatisticsPage(User user, Long pageNum, Long pageSize, String creatorName, String serviceName);

    HashMap<String,Long> saasRegisterStatisticsOverview(User user);

    IPage<ResourceOverviewVO> getWorkbenchResourcePag(User user,Long pageNum,Long pageSize,String appName);

    HashMap<String,Long> getWorkbenchResourceOverview(User user);

    /**
     * 应用变更
     * @param info 应用注册信息
     * @return R
     */
    R change(User user,SaasRegister info,String oldName);

}
