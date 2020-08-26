package com.hirisun.cloud.saas.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.constant.RedisKey;
import com.hirisun.cloud.common.contains.ApplicationInfoStatus;
import com.hirisun.cloud.common.contains.UserType;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.OrderNumUtil;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.QueryVO;
import com.hirisun.cloud.model.workbench.vo.ResourceOverviewVO;
import com.hirisun.cloud.redis.service.RedisService;
import com.hirisun.cloud.saas.bean.SaasAppExtResource;
import com.hirisun.cloud.saas.bean.SaasApplication;
import com.hirisun.cloud.saas.bean.SaasApplicationExt;
import com.hirisun.cloud.saas.bean.SaasApplicationImport;
import com.hirisun.cloud.saas.handle.CommonHandler;
import com.hirisun.cloud.saas.mapper.SaasApplicationMapper;
import com.hirisun.cloud.saas.service.ISaasAppExtResourceService;
import com.hirisun.cloud.saas.service.ISaasApplicationExtService;
import com.hirisun.cloud.saas.service.ISaasApplicationMergeService;
import com.hirisun.cloud.saas.service.ISaasApplicationService;
import com.hirisun.cloud.saas.vo.SaasExceptionCode;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

/**
 * <p>
 * SaaS资源申请原始信息表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2019-07-24
 */
@Service
public class SaasApplicationServiceImpl extends ServiceImpl<SaasApplicationMapper, 
	SaasApplication> implements ISaasApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(SaasApplicationServiceImpl.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private ISaasApplicationExtService saasApplicationExtService;
    @Autowired
    private FilesApi filesApi;
    @Autowired
    private ISaasApplicationMergeService saasApplicationMergeService;
    
    //TODO
    
//    @Autowired
//    private IActivityService activityService;
//    @Autowired
//    private IInstanceService instanceService;
//    @Autowired
//    private IWorkflowmodelService workflowmodelService;
//    @Autowired
//    private MessageProvider messageProvider;
    @Autowired
    private SaasApplicationMapper saasApplicationMapper;
    @Autowired
    private ISaasAppExtResourceService saasAppExtResourceService;

    private static String rootPath;

    @Value("${file.path}")
    public void setRootPath(String rootPath) {
        SaasApplicationServiceImpl.rootPath = rootPath;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(UserVO user, SaasApplication info) {
        info.setId(null);
        info.setCreator(user.getIdCard());
        info.setCreatorName(user.getName());
        info.setOrgId(user.getOrgId());
        info.setOrgName(user.getOrgName());
        info.setPostType(user.getPostType());
        info.setMobileWork(user.getMobileWork());
        info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
        String orderNum = redisService.genOrderNum(RedisKey.KEY_ORDER_NUM_PREFIX);
        info.setOrderNumber(orderNum);
        //设置申请单的用户id，同同一用户id  modfiy  by    qm 2020-7-30
        if(null!=user.getId()){
            info.setUserId(user.getId());
        }
        /*if ("0".equals(info.getApplyType())) {
            info.setPoliceCategory("");
        } else {
            info.setAreas("");
        }*/
        this.save(info);
        this.saveExt(info);
        this.saveExtResource(info);
        
        SubpageParam param = new SubpageParam();
        param.setFiles(info.getFileList());
        param.setRefId(info.getId());
		filesApi.refFiles(param );
        
//        messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, BusinessName.SAAS_RESOURCE, info.getOrderNumber()));
    
		//TODO: 消息队列改造
    }

    private void saveExtResource(SaasApplication info) {
        List<SaasAppExtResource> list = info.getResourceList();
        if (list != null && !list.isEmpty()) {
            list.forEach(saasAppExtResource -> {
                saasAppExtResource.setId(null);
                saasAppExtResource.setMasterId(info.getId());
            });
            saasAppExtResourceService.saveBatch(list);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UserVO user, SaasApplication info) {
        info.setStatus(null);
        info.setOrderNumber(null);
        if ("0".equals(info.getApplyType())) {
            info.setPoliceCategory("");
        } else {
            info.setAreas("");
        }
        this.updateById(info);
        saasApplicationExtService.remove(new QueryWrapper<SaasApplicationExt>().lambda()
                .eq(SaasApplicationExt::getMasterId, info.getId()));
        this.saveExt(info);
        saasAppExtResourceService.remove(new QueryWrapper<SaasAppExtResource>().lambda().eq(SaasAppExtResource::getMasterId,info.getId()));
        this.saveExtResource(info);
        
        SubpageParam param = new SubpageParam();
        param.setFiles(info.getFileList());
        param.setRefId(info.getId());
		filesApi.refFiles(param );
    }

    @Override
    public Integer getWorkbenchApplyCount(UserVO user, QueryVO queryVO) {
        Map<String,Object> param = CommonHandler.handlerOfQueryVO(queryVO,user);
        return saasApplicationMapper.getWorkbenchApplyCount(param);
    }

    @Override
    public IPage<SaasApplication> getPage(UserVO user, IPage<SaasApplication> page, Map<String, Object> param) {
        page = baseMapper.getPage(page, param);
        List<SaasApplication> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            for (SaasApplication record : records) {
                if (StringUtils.isEmpty(record.getMergeId())) {
                    record.setCanDelete(true);
                }
            }
        }
        return page;
    }

    @Override
    public Map<String, Object> getDetail(UserVO user, String id) {
        SaasApplication info = this.getById(id);
        if (info == null) {
            throw new CustomException(SaasExceptionCode.APPLY_NULL);
        }
        List<SaasApplicationExt> extList = saasApplicationExtService.getListByMasterId(info.getId());
        
        List<Map<String,Object>> serverList = JSON.parseObject(JSON.toJSON(extList).toString(), 
    			new TypeReference<List<Map<String,Object>>>(){});
        
        info.setServiceList(serverList);
        
        SubpageParam param = new SubpageParam();
        param.setRefId(info.getId());
		List<FilesVo> fileList = filesApi.find(param);
        
        info.setFileList(fileList);
        List<SaasAppExtResource> resourceList = saasAppExtResourceService.getListByMasterId(info.getId());
        info.setResourceList(resourceList);

        boolean isMerged = StringUtils.isNotEmpty(info.getMergeId());
        if (!isMerged && Objects.equals(user.getIdCard(), info.getCreator())) {
            info.setCanEdit(true);
            info.setCanDelete(true);
            if (ApplicationInfoStatus.REVIEW_REJECT.getCode().equals(info.getStatus())) {
                info.setCanSubmit(true);
            }
        }
        //TODO
//        String activityId = "";
//        Map<String, List<AppReviewInfo>> reviews = null;
//        WorkFlowModelVo modelVo = null;
//        if (isMerged) {
//            SaasApplicationMerge merge = saasApplicationMergeService.getDetails(info.getMergeId());
//            if (merge != null) {
//                Instance instance = instanceService.getInstanceByBusinessId(info.getMergeId());
//                Activity activity = null;
//                if (null!=instance){
//                    List<Activity> activitys = activityService.list(new QueryWrapper<Activity>().lambda()
//                            .eq(Activity::getInstanceid,instance.getId()).eq(Activity::getHandlepersonids,user.getIdcard()).eq(Activity::getActivitystatus,"待办").isNull(Activity::getActivitytype));
//                    if (null==activitys||activitys.size()==0){
//                        activitys = activityService.list(new QueryWrapper<Activity>().lambda()
//                                .eq(Activity::getInstanceid,instance.getId()).eq(Activity::getHandlepersonids,user.getIdcard()).eq(Activity::getActivitystatus,"待办").eq(Activity::getActivitytype,"adviser"));
//                    }
//                    if (null!=activitys&&activitys.size()>0) activity = activitys.get(0);
//                }
//                if (activity!=null) {
//                    activityId = activity.getId();
//                }
//                List<AppReviewInfo> allReviewInfo = merge.getReviewList();
//                if (allReviewInfo!=null){
//                    reviews = allReviewInfo.stream().collect(Collectors.groupingBy(a->a.getFlowStepId()+""));
//                }
//                modelVo = workflowmodelService.getWorkFlowDefinition(info.getMergeId());
//                // 放入审批记录
//                info.setReviewList(merge.getReviewList());
//                info.setImpl(merge.getImpl());
//            }
//        }

        Map<String, Object> map = new HashMap<>();
        map.put("bizData", info);
//        map.put("review", reviews);
//        map.put("model", modelVo);
//        map.put("activityId", activityId);
        map.put("merge", isMerged ? "已合并" : "未合并");
        return map;
    }

    @Override
    public IPage<SaasApplication> mergePage(UserVO user, IPage<SaasApplication> page, String keyword) {
        if (!UserType.TENANT_MANAGER.getCode().equals(user.getType())
                && !UserType.MANAGER.getCode().equals(user.getType())) {
            return page;
        }
        // 查询租户管理员管理的警种或地市
        String tenantArea = user.getTenantArea();
        String tenantPoliceCategory = user.getTenantPoliceCategory();
        if (StringUtils.isEmpty(tenantArea) && StringUtils.isEmpty(tenantPoliceCategory)) {
            return page;
        }
        return baseMapper.getMergePage(page, tenantArea, tenantPoliceCategory, keyword);
    }

    @Override
    public void updateStatus(String mergeId, String status) {
        this.update(new SaasApplication(), new UpdateWrapper<SaasApplication>().lambda()
                .eq(SaasApplication::getMergeId, mergeId)
                .set(SaasApplication::getStatus, status));
    }

    @Override
    public List<SaasApplication> getListByMergeId(String id) {
        List<SaasApplication> listByMergeId = baseMapper.getListByMergeId(id);
        //申请单详情中带上可视化建模平台的资源信息
        for (SaasApplication saasApplication:listByMergeId) {
            List<SaasAppExtResource> resourceList = saasAppExtResourceService.getListByMasterId(saasApplication.getId());
            saasApplication.setResourceList(resourceList);
        }
        return listByMergeId;
    }

    @Override
    public int getSaasTodoCount(UserVO user) {
        return baseMapper.getSaasTodoCount(user);
    }

    @Override
    public int getApplicationTodo(String idCard) {
        return baseMapper.getApplicationTodo(idCard);
    }

    @Override
    public Integer getSaasUseRes(UserVO user, String appName) {
        return baseMapper.getSaasUseRes(user, appName);
    }

    @Override
    public int getReviewCount(UserVO user, QueryVO queryVO) {
        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdCard());
        }
        param.put("queryVO",queryVO);

        return baseMapper.getReviewCount(user,param);
    }

    @Override
    public int getImplCount(UserVO user,QueryVO queryVO) {

        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdCard());
        }
        param.put("queryVO",queryVO);
        return baseMapper.getImplCount(user,param);
    }

    @Override
    public int getRejectCount(UserVO user,QueryVO queryVO) {
        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdCard());
        }
        param.put("queryVO",queryVO);
        return baseMapper.getRejectCount(user,param);
    }

    @Override
    public int getUseCount(UserVO user,QueryVO queryVO) {

        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdCard());
        }
        param.put("queryVO",queryVO);
        return baseMapper.getUseCount(user,param);
    }

    @Override
    public IPage<SaasApplication> getWorkbenchPage(IPage<SaasApplication> page, Map<String, Object> param) {
        return baseMapper.getWorkbenchPage(page, param);
    }

    @Override
    public void submit(UserVO user, String id) {
        SaasApplication saasApplication = new SaasApplication();
        saasApplication.setId(id);
        saasApplication.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
        saasApplication.updateById();
        // 发送短信
        saasApplication = this.getById(id);
//        TODO messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, BusinessName.SAAS_RESOURCE, saasApplication.getOrderNumber()));
    }

    @Override
    public void reject(UserVO user, String id) {
        SaasApplication saasApplication = new SaasApplication();
        saasApplication.setId(id);
        saasApplication.setStatus(ApplicationInfoStatus.REVIEW_REJECT.getCode());
        saasApplication.updateById();
        // 发送短信
        saasApplication = this.getById(id);
//        TODO messageProvider.sendMessageAsync(messageProvider.buildRejectMessage(saasApplication.getCreator(), BusinessName.SAAS_RESOURCE));
    }

    /**
     * 查询SAAS所申请过的用户
     */
    @Override
    public IPage<SaasApplication> getApplicationUser(IPage<SaasApplication> page, String area, String policeCategory,
                                                      String name, String idcard) {
        return baseMapper.getApplicationUser(page, area, policeCategory, name, idcard);
    }


    /**
     * 获取SAAS回收详情
     *
     * @param userId
     * @return
     */
    @Override
    public List<SaasApplication> getSaasUseService(String userId) {
        List<SaasApplication> saasApplicationList = saasApplicationMapper.getSaasUseService(userId);
        for (SaasApplication application : saasApplicationList){
            if(StringUtils.isNotEmpty(application.getServiceId())){
                String name = saasApplicationMapper.getBelongAppName(application.getServiceId());
                application.setApplicationName(name);
            }else{
                application.setApplicationName(null);
            }
        }
        return saasApplicationList;
    }

    /**
     * 获取用户正在使用的服务(包含正在回收的服务)
     *
     * @param userId
     */
    @Override
    public List<SaasApplication> getSaasRecoverManageUseService(String userId) {

        return saasApplicationMapper.getSaasRecoverManageUseService(userId);
    }

    @Override
    public void updateRecovering2NotRecover(String id) {
        saasApplicationMapper.updateRecovering2NotRecover(id);
    }

    @Override
    public void updateUse2Recover(String id) {
        saasApplicationMapper.updateUse2Recover(id);
    }

    private void saveExt(SaasApplication info) {
    	
    	List<SaasApplicationExt> serverList = JSON.parseObject(JSON.toJSON(info.getServiceList()).toString(), 
    			new TypeReference<List<SaasApplicationExt>>(){});
    	
        if (serverList != null && !serverList.isEmpty()) {
        	serverList.forEach(saasApplicationExt -> {
                saasApplicationExt.setId(null);
                saasApplicationExt.setMasterId(info.getId());
            });
            saasApplicationExtService.saveBatch(serverList);
        }
    }

    @Override
    public IPage<ResourceOverviewVO> getWorkbenchNewPage(Long pageNum, Long pageSize, UserVO user, String appName) {
        IPage<ResourceOverviewVO> page = new Page<>(pageNum,pageSize);

        Map<String,Object> param = CommonHandler.handlerNewPageParam(user,null,appName);
        page = saasApplicationMapper.getApplicationPage(page,param);
        return page;
    }

    @Override
    public HashMap<String, Long> getWorkbenchOverview(UserVO user) {
        Map<String,Object> param = CommonHandler.handlerUserParam(user);
        HashMap<String,Long> res = saasApplicationMapper.getApplicationOverview(param);
        return res;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public List<SaasApplicationImport> importData(MultipartFile file, String idcard) {
        ImportParams importParams = new ImportParams();
        List<SaasApplicationImport> importList = null;
        try {
            importList = ExcelImportUtil.importExcel(file.getInputStream(), SaasApplicationImport.class, importParams);
            if (importList != null) {
                for (SaasApplicationImport saasApplicationImport:importList) {
                    saasApplicationImport.setCreator(saasApplicationImport.getCreator().trim());
                }
            }
            logger.info("importList -> {}",importList.size());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(SaasExceptionCode.IMPORT_DATA_ERROR);
        }
        return importList;

    }

    @Override
    public void downloadFile(HttpServletResponse response) throws IOException {
        downFile(response, "应用权限回收申请.xls");
    }

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        upFile(file, "应用权限回收申请.xls");
    }

    private void upFile(MultipartFile file, String name) throws IOException {
        File targetFile = new File(rootPath+"/" + name);
        targetFile.exists();
        FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
        InputStream inputStream = file.getInputStream();
        IOUtils.copy(inputStream, fileOutputStream);
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(fileOutputStream);
    }

    private void downFile(HttpServletResponse response, String name) throws IOException {
        File file = new File(rootPath+"/" + name);
        if (file.exists()) {
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            String originaName;
            try {
                originaName = URLEncoder.encode(name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                originaName = "应用权限回收申请.xls";
            }
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + originaName);
            FileUtils.copyFile(file, response.getOutputStream());
        }
    }
}
