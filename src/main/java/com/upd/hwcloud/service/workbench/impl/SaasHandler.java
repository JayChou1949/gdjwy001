package com.upd.hwcloud.service.workbench.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.WorkbenchApplyStatus;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.entity.SaasApplication;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.workbench.PublicStatistics;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;
import com.upd.hwcloud.bean.vo.workbench.ResourceOverviewVO;
import com.upd.hwcloud.bean.vo.workbench.SaasLvThree;
import com.upd.hwcloud.dao.SaasApplicationMapper;
import com.upd.hwcloud.dao.SaasApplicationMergeMapper;
import com.upd.hwcloud.service.workbench.Workbench;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service("saasHandler")
public class SaasHandler implements Workbench {
    @Autowired
    private SaasApplicationMergeMapper saasApplicationMergeMapper;

    @Autowired
    private SaasApplicationMapper saasApplicationMapper;

    @Override
    public List<GeneralDTO> getUseResource(User user, QueryVO queryVO) {
        List<GeneralDTO> applyResource = Lists.newArrayList();
        Map<String,Object> param = Maps.newHashMap();
        queryVO.setServiceName(CommonHandler.dealNameforQuery(queryVO.getServiceName()));
        if(CommonHandler.isTenantManager(user)){

            queryVO.setArea(user.getTenantArea());

            queryVO.setPoliceCategory(user.getTenantPoliceCategory());
            param.put("queryVO",queryVO);
            applyResource = saasApplicationMergeMapper.tenantUseResource(param,0);

        }else{
            queryVO.setCreator(user.getIdcard());
            param.put("queryVO",queryVO);
            applyResource = saasApplicationMergeMapper.personalUseResource(param,0);
        }
        return applyResource;
    }


    @Override
    public PublicStatistics doPublicStatistics(User user, QueryVO queryVO) {
        return null;
    }


    @Override
    public List<GeneralDTO> getServiceOfSaas(String appName) {
        List<GeneralDTO> saasList = Lists.newArrayList();
        return saasList;
    }

    @Override
    public R serviceOfSaas(String name) {
        List<GeneralDTO> generalDTOList = Lists.newArrayList();
        List<SaasLvThree> saasLvThreeList = Lists.newArrayList();
        Map<String,Object> res = Maps.newHashMap();
        res.put("list",generalDTOList.size()>10?generalDTOList.subList(0,10):generalDTOList);
        res.put("statistics",saasLvThreeList.size()>10?saasLvThreeList.subList(0,10):saasLvThreeList);
        return R.ok(res);
    }

    @Override
    public List<SaasLvThree> getServiceOfSaasStatistics(String name) {
        return null;
    }

    @Override
    public void lvThreePageExport(String name, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public IPage<SaasApplication> applyPage(Integer pageNum, Integer pageSize, QueryVO queryVO, String status,User user) {
        IPage<SaasApplication> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        Map<String,Object> param = CommonHandler.handlerOfQueryVO(queryVO,user);
        page = saasApplicationMapper.getWorkbenchApplyPage(page,param);
        List<SaasApplication> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            for (SaasApplication record : records) {
                if (StringUtils.isEmpty(record.getMergeId())) {
                    record.setCanDelete(true);
                }
            }
        }
        //Map<String,Object> param = handlerOfQueryVO(queryVO,user);
        return page;
    }

    /**
     * 根据订单状态获取Mapper处理
     * @param status
     * @param page
     * @param param
     * @return
     */
    @Deprecated
    private IPage<SaasApplication> handlerOfStatus(String status,IPage<SaasApplication> page,Map<String,Object> param){
        WorkbenchApplyStatus applyStatus = WorkbenchApplyStatus.codeOf(status);
        switch (applyStatus){
            case REVIEW_STATUS:
                return saasApplicationMapper.getReviewOfWorkbench(page,param);
            case IMPL_STATUS:
                return saasApplicationMapper.getImplOfWorkbench(page,param);
            case REJECT_STATUS:
                return saasApplicationMapper.getRejectOfWorkbench(page,param);
            case USE_STATUS:
                return  saasApplicationMapper.getUseOfWorkbench(page,param);
            default:
                return page;
        }
    }
    @Deprecated
    private Map<String,Object> handlerOfQueryVO(QueryVO vo,User user){
        Map<String,Object> param = Maps.newHashMap();
        //申请人名或订单号
        //vo.setCreatorName(CommonHandler.dealNameforQuery(vo.getCreatorName()));
        if(vo.getCreatorName() != null){
            vo.setCreatorName(vo.getCreatorName().trim());
        }
        if(CommonHandler.isTenantManager(user)){
            vo.setPoliceCategory(user.getTenantPoliceCategory());
            vo.setArea(user.getTenantArea());
        }else{
            //此处为处理人
            vo.setCreator(user.getId());
        }
        param.put("queryVO",vo);
        return  param;
    }

    /**
     * SaaS服务分页
     * @param pageNum
     * @param pageSize
     * @param user
     * @param serviceName
     * @param appName
     * @return
     */
    @Override
    public IPage<ResourceOverviewVO> resourcePage(Long pageNum, Long pageSize, User user, String serviceName, String appName) {

        IPage<ResourceOverviewVO> page = new Page<>(pageNum,pageSize);
        return page;
    }

    @Override
    public HashMap<String, Long> resourceOverview(User user) {
        HashMap<String,Long> res = Maps.newHashMap();
        res.put("NUM",0L);
        res.put("SUMCOUNT",0L);
       // CommonHandler.handlerOfOverview(res);
        return res;
    }
}
