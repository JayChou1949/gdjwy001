package com.upd.hwcloud.controller.backend.application.form.paas;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.FormNum;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.PaasDistributedDbImpl;
import com.upd.hwcloud.bean.entity.application.PaasDistributedDbInfo;
import com.upd.hwcloud.bean.entity.application.paas.libra.PaasLibraAccount;
import com.upd.hwcloud.bean.entity.application.paas.libra.PaasLibraAccountImpl;
import com.upd.hwcloud.bean.entity.application.paas.libra.PaasLibraDbWhitelist;
import com.upd.hwcloud.bean.entity.application.paas.libra.PaasLibraInfo;
import com.upd.hwcloud.bean.entity.application.paas.libra.PaasLibraInfoImpl;
import com.upd.hwcloud.bean.entity.application.paas.libra.PaasLibraWhitelistImpl;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.application.IApplicationInfoService;
import com.upd.hwcloud.service.application.IPaasDistributedDbImplService;
import com.upd.hwcloud.service.application.IPaasDistributedDbInfoService;
import com.upd.hwcloud.service.application.IPaasLibraAccountImplService;
import com.upd.hwcloud.service.application.IPaasLibraAccountService;
import com.upd.hwcloud.service.application.IPaasLibraDbWhitelistService;
import com.upd.hwcloud.service.application.IPaasLibraInfoImplService;
import com.upd.hwcloud.service.application.IPaasLibraInfoService;
import com.upd.hwcloud.service.application.IPaasLibraWhitelistImplService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yyc
 * @date 2020/6/11
 */
@RestController
@RequestMapping("/libra")
public class PaasLibraController {

    @Autowired
    private IApplicationInfoService applicationInfoService;

    @Autowired
    private IPaasDistributedDbInfoService paasDistributedDbInfoService;

    @Autowired
    private IPaasDistributedDbImplService distributedDbImplService;

    @Autowired
    private IPaasLibraInfoService libraInfoService;

    @Autowired
    private IPaasLibraInfoImplService libraInfoImplService;

    @Autowired
    private IPaasLibraAccountService libraAccountService;

    @Autowired
    private IPaasLibraAccountImplService libraAccountImplService;

    @Autowired
    private IPaasLibraDbWhitelistService dbWhitelistService;

    @Autowired
    private IPaasLibraWhitelistImplService whitelistImplService;

    @RequestMapping(value = "/sync",method = RequestMethod.GET)
    @Transactional(rollbackFor = Throwable.class)
    public R  sync(){

        //获取Libra非删除订单
        List<ApplicationInfo> applicationInfoList = applicationInfoService.list(new QueryWrapper<ApplicationInfo>().lambda().eq(ApplicationInfo::getFormNum,
                FormNum.PAAS_DISTRIBUTED_DB.toString()).ne(ApplicationInfo::getStatus, ApplicationInfoStatus.DELETE.getCode()));

        //遍历订单
        for(ApplicationInfo info:applicationInfoList){
            //获取老数据库信息
            List<PaasDistributedDbInfo> distributedDbInfoList = paasDistributedDbInfoService.list(new QueryWrapper<PaasDistributedDbInfo>().lambda()
                                                                        .eq(PaasDistributedDbInfo::getAppInfoId,info.getId()));
            if(CollectionUtils.isNotEmpty(distributedDbInfoList)){
                //取第一条数据
                PaasDistributedDbInfo oldDb = distributedDbInfoList.get(0);


                PaasLibraInfo newInfo = new PaasLibraInfo();
                newInfo.setId(null);
                newInfo.setShoppingCartId(oldDb.getShoppingCartId());
                newInfo.setAppInfoId(oldDb.getAppInfoId());
                newInfo.setRemark(oldDb.getRemark());
                newInfo.setApplyReason(oldDb.getApplyReason());
                newInfo.setCpu(oldDb.getCpu()!=null?oldDb.getCpu().doubleValue():0);
                newInfo.setMemory(oldDb.getMemory());
                newInfo.setStorage(oldDb.getStorage());
                libraInfoService.save(newInfo);

                PaasLibraAccount libraAccount = new PaasLibraAccount();
                libraAccount.setId(null);
                libraAccount.setAppInfoId(oldDb.getAppInfoId());
                libraAccount.setShoppingCartId(oldDb.getShoppingCartId());
                libraAccount.setAccount(oldDb.getAccount());
                libraAccount.setCanCreated(oldDb.getCanCreated());
                libraAccount.setCanDelete(oldDb.getCanDelete());
                libraAccount.setCanModified(oldDb.getCanModified());
                libraAccount.setCanQuery(oldDb.getCanQuery());
                libraAccount.setRemark(oldDb.getRemark());
                libraAccountService.save(libraAccount);

                if(StringUtils.equals(info.getStatus(),ApplicationInfoStatus.USE.getCode())){
                    List<PaasDistributedDbImpl> oldImplList = distributedDbImplService.list(new QueryWrapper<PaasDistributedDbImpl>()
                                                            .lambda().eq(PaasDistributedDbImpl::getAppInfoId,info.getId()));
                    if(CollectionUtils.isNotEmpty(oldImplList)){
                        PaasDistributedDbImpl oldImpl = oldImplList.get(0);
                        PaasLibraInfoImpl implInfo = new PaasLibraInfoImpl();
                        implInfo.setId(null);
                        implInfo.setAppInfoId(oldImpl.getAppInfoId());
                        implInfo.setCpu(oldImpl.getCpu()!=null?oldDb.getCpu().doubleValue():0);
                        implInfo.setMemory(oldImpl.getMemory());
                        implInfo.setStorage(oldImpl.getStorage());
                        implInfo.setIp(oldImpl.getHost());
                        implInfo.setDbType(oldImpl.getDbType());
                        implInfo.setDb(oldImpl.getDb());
                        libraInfoImplService.save(implInfo);

                        PaasLibraAccountImpl accountImpl = new PaasLibraAccountImpl();
                        accountImpl.setId(null);
                        accountImpl.setAppInfoId(oldImpl.getAppInfoId());
                        accountImpl.setAccount(oldImpl.getAccount());
                        accountImpl.setCanCreated(oldImpl.getCanCreated());
                        accountImpl.setCanDelete(oldImpl.getCanDelete());
                        accountImpl.setCanModified(oldImpl.getCanModified());
                        accountImpl.setCanQuery(oldImpl.getCanQuery());
                        accountImpl.setSchema(oldImpl.getSchema());
                        libraAccountImplService.save(accountImpl);

                        List<PaasLibraDbWhitelist> dbWhitelists = dbWhitelistService.list(new QueryWrapper<PaasLibraDbWhitelist>()
                                                                    .lambda().eq(PaasLibraDbWhitelist::getAppInfoId,info.getId()));
                        if(CollectionUtils.isNotEmpty(dbWhitelists)){
                            List<PaasLibraWhitelistImpl> whitelistListImplList = Lists.newArrayList();
                            for (PaasLibraDbWhitelist whitelist:dbWhitelists){
                                PaasLibraWhitelistImpl whitelistImpl = new PaasLibraWhitelistImpl();
                                whitelistImpl.setId(null);
                                whitelistImpl.setAppInfoId(whitelist.getAppInfoId());
                                whitelistImpl.setShoppingCartId(whitelist.getShoppingCartId());
                                whitelistImpl.setApplyReason(whitelist.getApplyReason());
                                whitelistImpl.setUserName(whitelist.getUserName());
                                whitelistListImplList.add(whitelistImpl);
                            }
                            whitelistImplService.saveBatch(whitelistListImplList);
                        }

                    }

                }
            }

        }
        return R.ok();
    }
}
