package com.upd.hwcloud.service.impl;

import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.Paas;
import com.upd.hwcloud.bean.entity.PaasRegister;
import com.upd.hwcloud.bean.entity.PaasSubpage;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.dao.PaasRegisterMapper;
import com.upd.hwcloud.service.IPaasRegisterService;
import com.upd.hwcloud.service.IPaasService;
import com.upd.hwcloud.service.IPaasSubpageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * PAAS服务注册表 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-06-12
 */
@Service
public class PaasRegisterServiceImpl extends RegisterServiceImpl<PaasRegisterMapper, PaasRegister> implements IPaasRegisterService {

    @Autowired
    IPaasService paasService;
    @Autowired
    IPaasSubpageService paasSubpageService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveService(PaasRegister register){
        Paas paas = this.getPaas(register);
        paasService.save(paas);
        PaasSubpage subpage = this.getPaasSubpage(paas.getId(), register);
        paasSubpageService.save(subpage);
    }

    private PaasSubpage getPaasSubpage(String paasId, PaasRegister info) {
        PaasSubpage subpage = new PaasSubpage();
        subpage.setId(null);
        subpage.setMasterId(paasId);
        subpage.setJsUnit(info.getJsUnit());
        subpage.setJsPrincipal(info.getJsPrincipal());
        subpage.setJsPrincipalPhone(info.getJsPrincipalPhone());
        subpage.setCjUnit(info.getCjUnit());
        subpage.setCjPrincipal(info.getCjPrincipal());
        subpage.setCjPrincipalPhone(info.getCjPrincipalPhone());
        return subpage;
    }

    private Paas getPaas(PaasRegister info) {
        Paas paas = new Paas();
        paas.setId(UUIDUtil.getUUID());
        paas.setRegistId(info.getId());
        paas.setName(info.getName());
        paas.setSubType(info.getSubType());
        paas.setSort(new Date().getTime()/1000);
        paas.setUrl(info.getUrl());
        paas.setImage(info.getImage());
        paas.setDescription(info.getDescription());
        paas.setTop("0");
        paas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        paas.setHome("1");
        paas.setBuildStatus(info.getBuildStatus());
        paas.setSubPagePermission(1L);
        paas.setCanApplication(info.getCanApplication());
        paas.setHasDoc("0");
        paas.setCreator(info.getCreator());
        paas.setApiAccess(info.getApiAccess());
        return paas;
    }
}
