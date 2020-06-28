package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.Log;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.SaasRegister;
import com.upd.hwcloud.bean.entity.SaasSubpage;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;
import com.upd.hwcloud.bean.vo.workbench.ResourceOverviewVO;
import com.upd.hwcloud.bean.vo.workbench.SaasStatisticsVO;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.dao.SaasRegisterMapper;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.ISaasRegisterService;
import com.upd.hwcloud.service.ISaasService;
import com.upd.hwcloud.service.ISaasSubpageService;
import com.upd.hwcloud.service.workbench.impl.CommonHandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * SAAS服务注册表 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-05-27
 */
@Service
public class SaasRegisterServiceImpl extends RegisterServiceImpl<SaasRegisterMapper, SaasRegister> implements ISaasRegisterService {

    @Autowired
    ISaasService saasService;
    @Autowired
    ISaasSubpageService saasSubpageService;

    @Autowired
    SaasRegisterMapper saasRegisterMapper;

    @Autowired
    private IFilesService filesService;



    private static final Logger logger = LoggerFactory.getLogger(SaasRegisterServiceImpl.class);

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveService(SaasRegister register){
        Saas saas = this.getSaas(register);
        saasService.save(saas);
        SaasSubpage subpage = this.getSaasSubpage(saas.getId(), register);
        saasSubpageService.save(subpage);
    }


    @Override
    public R applicationRegisterApply(User user, QueryVO queryVO) {
        int reviewCount = this.getReviewCount(user,queryVO);
        int implCount = this.getImplCount(user,queryVO);
        int rejectCount = this.getRejectCount(user,queryVO);
        int useCount = this.getUseCount(user,queryVO);
        Map<String, Integer> data = Maps.newHashMap();
        data.put("reviewCount", reviewCount);
        data.put("implCount", implCount);
        data.put("rejectCount", rejectCount);
        data.put("useCount", useCount);
        return R.ok(data);
    }

    @Override
    public IPage<SaasRegister> getWorkbenchApplyPage(User user, IPage<SaasRegister> page, QueryVO queryVO) {
        Map<String,Object> param = CommonHandler.handlerOfQueryVO(queryVO,user);
        page = saasRegisterMapper.getWorkbenchApplyPage(page,param);
        List<SaasRegister> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            for (SaasRegister record : records) {
                ApplicationInfoStatus ais = ApplicationInfoStatus.codeOf(record.getStatus());
                // 判断是否能删除
                if (ais != ApplicationInfoStatus.DELETE
                        && Objects.equals(user.getIdcard(), record.getCreator())) {
                    record.setCanDelete(true);
                }
            }
        }
        return page;
    }


    @Override
    public IPage<SaasStatisticsVO> saasRegisterStatisticsPage(User user, Long pageNum, Long pageSize, String creatorName, String serviceName) {
        IPage<SaasStatisticsVO> page = new Page<>(pageNum,pageSize);
        QueryVO queryVO = new QueryVO();
        queryVO.setServiceName(CommonHandler.dealNameforQuery(serviceName));
        queryVO.setCreatorName(CommonHandler.dealNameforQuery(creatorName));
        Map<String,Object> param = CommonHandler.handlerOfQueryVO(queryVO,user);
        page = saasRegisterMapper.saasRegisterStatisticsPage(page,param);
        List<SaasStatisticsVO> records = page.getRecords();
        for(SaasStatisticsVO record: records){
            if("2".equals(record.getStatus())){
                record.setStatus("在线");
            }else{
                record.setStatus("待上线");
            }
        }
        return page;
    }

    @Override
    public HashMap<String, Long> saasRegisterStatisticsOverview(User user) {

        QueryVO queryVO = new QueryVO();
        Map<String,Object> param = CommonHandler.handlerOfQueryVO(queryVO,user);
        HashMap<String,Long> res = saasRegisterMapper.saasRegisterStatisticsOverview(param);
        return res;
    }

    @Override
    public IPage<ResourceOverviewVO> getWorkbenchResourcePag(User user, Long pageNum, Long pageSize, String appName) {

        IPage<ResourceOverviewVO> page = new Page<>(pageNum,pageSize);
        Map<String,Object> param = CommonHandler.handlerNewPageParam(user,null,appName);
        page = saasRegisterMapper.getWorkbenchResourcePage(page,param);
        return page;
    }

    @Override
    public HashMap<String, Long> getWorkbenchResourceOverview(User user) {
        Map<String,Object> param = CommonHandler.handlerUserParam(user);
        HashMap<String,Long> res = saasRegisterMapper.getWorkbenchResourceOverview(param);
        CommonHandler.handlerOfOverview(res);
        return res;
    }

    private int getReviewCount(User user, QueryVO queryVO) {
        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO",queryVO);

        return baseMapper.getReviewCount(user,param);
    }


    private int getImplCount(User user, QueryVO queryVO) {

        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO",queryVO);

        return baseMapper.getImplCount(user,param);
    }


    private int getRejectCount(User user, QueryVO queryVO) {

        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO",queryVO);

        return baseMapper.getRejectCount(user,param);
    }

    private int getUseCount(User user, QueryVO queryVO) {
        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO",queryVO);

        return baseMapper.getUseCount(user,param);
    }


    private SaasSubpage getSaasSubpage(String saasId, SaasRegister info) {
        SaasSubpage subpage = new SaasSubpage();
        subpage.setId(null);
        subpage.setMasterId(saasId);
        subpage.setJsUnit(info.getJsUnit());
        subpage.setJsPrincipal(info.getJsPrincipal());
        subpage.setJsPrincipalPhone(info.getJsPrincipalPhone());
        subpage.setCjUnit(info.getCjUnit());
        subpage.setCjPrincipal(info.getCjPrincipal());
        subpage.setCjPrincipalPhone(info.getCjPrincipalPhone());
        subpage.setGovUnit(info.getGovUnit());
        subpage.setGovPrincipal(info.getGovPrincipal());
        subpage.setGovPrincipalPhone(info.getGovPrincipalPhone());
        return subpage;
    }

    private Saas getSaas(SaasRegister info) {
        Saas saas = new Saas();
        saas.setId(UUIDUtil.getUUID());
        saas.setRegistId(info.getId());
        saas.setName(info.getName());
        saas.setShortName(info.getName());
        saas.setSubType(info.getSubType());
        saas.setSort(new Date().getTime()/1000);
        saas.setUrl(info.getUrl());
        saas.setImage(info.getImage());
        saas.setDescription(info.getDescription());
        saas.setTop("0");
        saas.setStatus(ReviewStatus.ONLINE.getCode());
        saas.setHome("1");
        saas.setBuildStatus(info.getBuildStatus());
        saas.setSubPagePermission(1L);
        saas.setCanApplication(info.getCanApplication());
        saas.setHasDoc("0");
        saas.setSecret(info.getSecret());
        saas.setCreator(info.getCreator());
        saas.setApplyType(info.getApplyType());
        saas.setServiceFlag(0); //标记为saas应用
        saas.setVersionCode("V1.0");
        if ("2".equals(info.getApplyType())) {
            saas.setAreaName("");
            saas.setPoliceCategory("");
        }else {
            saas.setAreaName(info.getArea());
            saas.setPoliceCategory(info.getPoliceCategory());
        }
        return saas;
    }

    private Saas createChangeEntity(String appId,SaasRegister register){
        Saas saas = new Saas();
        saas.setId(appId);
        saas.setName(register.getName());
        saas.setSecret(register.getSecret());
        saas.setBuildStatus(register.getBuildStatus());
        saas.setUrl(register.getUrl());
        saas.setDescription(register.getDescription());
        saas.setImage(register.getImage());
        return saas;
    }


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public R change(User user,SaasRegister info,String oldName) {

        if(StringUtils.isBlank(oldName)){
            throw new BaseException("原应用名为空");
        }
        /*if(!StringUtils.equals(user.getIdcard(),info.getJsPrincipalIdcard())){
            logger.debug("user-> {} ,js -> {}",user.getIdcard(),info.getJsPrincipalIdcard());
            throw new BaseException("非应用建设单位负责人，不能变更");
        }*/
        //更新应用注册信息及文件信息
        this.update(user,info);
        filesService.refFiles(info.getFileList(),info.getId());
        //更新注册信息对应应用信息
        try{
            Saas saas = saasService.getOne(new QueryWrapper<Saas>().lambda().eq(Saas::getName,oldName));
            Saas updaeSaas = createChangeEntity(saas.getId(),info);
            saasService.updateById(updaeSaas);
        }catch (Exception e){
            throw  new BaseException("获取对应应用异常,请检查应用是否存在");
        }
        return R.ok();
    }
}
