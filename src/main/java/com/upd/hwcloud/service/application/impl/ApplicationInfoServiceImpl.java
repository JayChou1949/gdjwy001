package com.upd.hwcloud.service.application.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.FormNum;
import com.upd.hwcloud.bean.contains.ModelName;
import com.upd.hwcloud.bean.contains.RdbApplyType;
import com.upd.hwcloud.bean.contains.RedisKey;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.dto.DaasApplicationExt;
import com.upd.hwcloud.bean.dto.EcsStatistics;
import com.upd.hwcloud.bean.dto.EcsStatisticsTotal;
import com.upd.hwcloud.bean.dto.FlowDetail;
import com.upd.hwcloud.bean.dto.ImplRequest;
import com.upd.hwcloud.bean.dto.Message;
import com.upd.hwcloud.bean.dto.ResourceCount;
import com.upd.hwcloud.bean.dto.UpdateApplicationInfo;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.FlowStep;
import com.upd.hwcloud.bean.entity.IaasNew;
import com.upd.hwcloud.bean.entity.IaasZYSBapplicationExport;
import com.upd.hwcloud.bean.entity.IaasZysb;
import com.upd.hwcloud.bean.entity.IaasZysbXmxx;
import com.upd.hwcloud.bean.entity.Log;
import com.upd.hwcloud.bean.entity.Paas;
import com.upd.hwcloud.bean.entity.PassDaasIaasApplicationExport;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.SaasApplicationExport;
import com.upd.hwcloud.bean.entity.SaasRegister;
import com.upd.hwcloud.bean.entity.ServicePublishApplicationRegistExport;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.application.ApplicationFeedback;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.InnerReviewUser;
import com.upd.hwcloud.bean.entity.application.PaasTyyh;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.bean.entity.application.paas.rdb.PaasRdbBase;
import com.upd.hwcloud.bean.vo.applicationInfoOrder.IPDVo;
import com.upd.hwcloud.bean.vo.applicationInfoOrder.ReviewInfoVo;
import com.upd.hwcloud.bean.vo.ncov.BusinessTopDto;
import com.upd.hwcloud.bean.vo.ncov.NcovIaasVo;
import com.upd.hwcloud.bean.vo.open.maintenance.TodoVo;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.AreaPoliceCategoryUtils;
import com.upd.hwcloud.common.utils.BigDecimalUtil;
import com.upd.hwcloud.common.utils.IpUtil;
import com.upd.hwcloud.common.utils.OkHttpUtils;
import com.upd.hwcloud.dao.IaasZysbMapper;
import com.upd.hwcloud.dao.SaasRegisterMapper;
import com.upd.hwcloud.dao.application.ApplicationInfoMapper;
import com.upd.hwcloud.dao.wfm.ActivityMapper;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IFlowStepService;
import com.upd.hwcloud.service.IFlowStepUserService;
import com.upd.hwcloud.service.IIaasNewService;
import com.upd.hwcloud.service.IIaasZysbXmxxService;
import com.upd.hwcloud.service.IPaasService;
import com.upd.hwcloud.service.ISaasApplicationService;
import com.upd.hwcloud.service.ISaasRecoverInfoService;
import com.upd.hwcloud.service.ISaasRegisterService;
import com.upd.hwcloud.service.ISaasService;
import com.upd.hwcloud.service.IServicePublishService;
import com.upd.hwcloud.service.IUserService;
import com.upd.hwcloud.service.application.IAppReviewInfoService;
import com.upd.hwcloud.service.application.IApplicationFeedbackService;
import com.upd.hwcloud.service.application.IApplicationHandler;
import com.upd.hwcloud.service.application.IApplicationInfoService;
import com.upd.hwcloud.service.application.IDaasApigService;
import com.upd.hwcloud.service.application.IImplHandler;
import com.upd.hwcloud.service.application.IInnerReviewUserService;
import com.upd.hwcloud.service.application.IPaasApigService;
import com.upd.hwcloud.service.application.IPaasFseqsqmImplService;
import com.upd.hwcloud.service.application.IPaasRdbBaseService;
import com.upd.hwcloud.service.application.IPaasTyyhService;
import com.upd.hwcloud.service.application.ISpeedUpService;
import com.upd.hwcloud.service.msg.MessageProvider;
import com.upd.hwcloud.service.workbench.impl.CommonHandler;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 申请信息表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-11-30
 */
@Service
public class ApplicationInfoServiceImpl extends ServiceImpl<ApplicationInfoMapper, ApplicationInfo> implements IApplicationInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationInfoServiceImpl.class);

    /**
     * 审核类型
     */
    private static final String TYPE_REVIEW = "1";
    /**
     * 实施类型
     */
    private static final String TYPE_IMPL = "2";

    private static final String INNER_REVIEW_STEP_NAME = "部门内审核";
    private static final String FWT_REVIEW_STEP_NAME = "服务台复核";
    private static final String ADD_REVIEW_STEP_NAME = "加办";
    private static final String FORWARD_REVIEW_STEP_NAME = "转发";

    @Autowired
    private ApplicationInfoMapper applicationInfoMapper;
    @Autowired
    private ISaasApplicationService saasApplicationService;
    @Autowired
    private ISaasService saasService;
    @Autowired
    private IPaasService paasService;
    @Autowired
    private IIaasNewService iaasNewService;
    @Autowired
    private IFilesService filesService;
    @Autowired
    private IFlowStepService flowStepService;
    @Autowired
    private IFlowStepUserService flowStepUserService;
    @Autowired
    private IAppReviewInfoService appReviewInfoService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IInnerReviewUserService innerReviewUserService;
    @Autowired
    private IApplicationFeedbackService applicationFeedbackService;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IDaasApigService daasApigService;
    @Autowired
    private IPaasApigService paasApigService;
    @Autowired
    private ISaasRegisterService saasRegisterService;

    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ISaasRecoverInfoService saasRecoverInfoService;
    @Autowired
    private IServicePublishService servicePublishService;
    @Autowired
    private IaasZysbMapper iaasZysbMapper;
    @Autowired
    private IIaasZysbXmxxService iaasZysbXmxxService;
    @Autowired
    private SaasRegisterMapper registerMapper;
    @Autowired
    private IPaasTyyhService tyyhService;

    @Autowired
    private ISpeedUpService speedUpService;

    @Autowired
    private IPaasRdbBaseService rdbBaseService;

    @Value("${daas.auto.enable}")
    private boolean daasAutoEnable;
    @Value("${paas.auto.enable}")
    private boolean paasAutoEnable;

    @Value("${iMoc.pre.url}")
    private String iMocPreURL;

    @Value("${iaas.todo.url}")
    private String iaasTodoUrl;


    /**
     * 添加到购物车(改版后)
     * @param user
     * @param shoppingCart
     * @param handler
     * @param <S>
     */
    @Override
    public <S> void addToShoppingCart(User user, ShoppingCart<S> shoppingCart, IApplicationHandler<S> handler) {
        if (!(ResourceType.DAAS.getCode().equals(shoppingCart.getResourceType()) || ResourceType.SAAS_SERVICE.getCode().equals(shoppingCart.getResourceType())) && StringUtils.isEmpty(shoppingCart.getServiceTypeId())) {
            // 不是 daas 申请,必须带上所申请的服务 id
            throw new BaseException("参数服务id必填");
        }
        shoppingCart.setCreatorIdCard(user.getIdcard());
        shoppingCart.setCreatorName(user.getName());
        if(handler != null){
            handler.saveShoppingCart(shoppingCart);
        }
        if(CollectionUtils.isNotEmpty(shoppingCart.getFileList())){
            logger.debug("ref file");
            refFiles(shoppingCart.getFileList(),shoppingCart.getId());
        }
    }

    /**
     * 保存表单数据到数据库 基本数据+serverList<S>
     *
     * @param user
     * @param info
     * @param handler
     * @param <S>
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public <S> void save(User user, ApplicationInfo<S, Object> info, IApplicationHandler<S> handler,boolean special) {
        if (!(ResourceType.DAAS.getCode().equals(info.getResourceType()) || ResourceType.SAAS_SERVICE.getCode().equals(info.getResourceType())) && StringUtils.isEmpty(info.getServiceTypeId())) {
            // 不是 daas 申请,必须带上所申请的服务 id
            throw new BaseException("参数服务id必填");
        }
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        if(special){
            info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
        }else {
            info.setStatus(ApplicationInfoStatus.SHOPPING_CART.getCode());
        }
        info.setFlowStepId(null);
        info.setFlowStepIdBak(null);
        String orderNum = this.genOrderNum();
        info.setOrderNumber(orderNum);
        info.setHwPoliceCategory(AreaPoliceCategoryUtils.getPoliceCategory(info.getAppName()));
        this.save(info);
        // 关联文件信息
        refFiles(info.getFileList(), info.getId());
        // 关联服务器信息（impl）
        if (handler != null) {
            handler.save(info);
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public <S> void draft(User user, ApplicationInfo<S, Object> info, IApplicationHandler<S> handler) {
        if (!ResourceType.DAAS.getCode().equals(info.getResourceType()) && StringUtils.isEmpty(info.getServiceTypeId())) {
            // 不是 daas 申请,必须带上所申请的服务 id
            throw new BaseException("参数服务id必填");
        }
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        info.setStatus(ApplicationInfoStatus.SHOPPING_CART.getCode());
        info.setHwPoliceCategory(AreaPoliceCategoryUtils.getPoliceCategory(info.getAppName()));
        info.setFlowStepId(null);
        info.setFlowStepIdBak(null);
        if (StringUtils.isEmpty(info.getId())) {
            String orderNum = this.genOrderNum();
            info.setOrderNumber(orderNum);
            this.save(info);
            // 关联服务器信息
            if (handler != null) {
                handler.save(info);
            }
        } else {
            info.setOrderNumber(null);
            this.updateById(info);
            // 关联服务器信息
            if (handler != null) {
                handler.update(info);
            }
        }

        // 关联文件信息
        refFiles(info.getFileList(), info.getId());
    }

    private String genOrderNum() {
        // 生成单号
        String yyyyMMdd = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String redisKey = RedisKey.KEY_ORDER_NUM_PREFIX + yyyyMMdd;
        Long increment = stringRedisTemplate.opsForValue().increment(redisKey, 1L);
        if (increment == null) {
            throw new BaseException("申请单号生成错误,请重试!");
        }
        // 过期时间1天
        stringRedisTemplate.expire(redisKey, 1L, TimeUnit.DAYS);
        return String.format("%s%04d", yyyyMMdd, increment);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public <S> void update(User user, UpdateApplicationInfo<S> updateInfo, IApplicationHandler<S> handler) {
        ApplicationInfo<S, Object> info = updateInfo.getInfo();
        info.setStatus(null);
        info.setFlowStepId(null);
        info.setFlowStepIdBak(null);
        info.setOrderNumber(null);

        String type = updateInfo.getType();

        boolean isRdbAddAccout = false;

        ApplicationInfo dbInfo = this.getById(info.getId());
       /* if (!Objects.equals(user.getIdcard(), dbInfo.getCreator())) {
            throw new BaseException("只能修改自己的申请!");
        }*/
        ApplicationInfoStatus status = ApplicationInfoStatus.codeOf(dbInfo.getStatus());
        // 只有以下状态可以修改数据(购物车,审核驳回,实施驳回,部门内审核驳回)
        if (status != ApplicationInfoStatus.REVIEW_REJECT
                && status != ApplicationInfoStatus.IMPL_REJECT
                && status != ApplicationInfoStatus.SHOPPING_CART
                && status != ApplicationInfoStatus.INNER_REJECT) {
            if (status == ApplicationInfoStatus.REVIEW) {
                boolean canEdit = false;
                FlowStep step = flowStepService.getById(dbInfo.getFlowStepId());
                if (step != null && FWT_REVIEW_STEP_NAME.equals(step.getName())) {
                    // 如果是服务台复核,那么复核人可以修改
                    List<User> userList = flowStepUserService.findUserByFlowStepId(dbInfo.getFlowStepId());
                    if (userList != null && !userList.isEmpty()) {
                        for (User u : userList) {
                            if (Objects.equals(user.getIdcard(), u.getIdcard())) {
                                canEdit = true;
                                break;
                            }
                        }
                    }
                }
                if ("1".equals(dbInfo.getFlowNew())) {
                    canEdit = true;
                }
                if (!canEdit) {
                    throw new BaseException("该状态不能修改");
                }
            } else {
                //如果是关系型数据库的新增数据库，业务办理环节可以修改,不抛出异常
                if(info.getFormNum().equals(FormNum.PAAS_RELATIONAL_DATABASE.toString()) && status == ApplicationInfoStatus.IMPL){
                    List<PaasRdbBase>  baseList = (List<PaasRdbBase>)handler.getByAppInfoId(info.getId());
                    logger.debug("update::baseList -> {}",baseList);
                    if(baseList.size()==1){
                        if(RdbApplyType.ADD_ACCOUNT.getCode().equals(baseList.get(0).getApplyType())){
                            logger.debug("update::PAAS_RELATIONAL_DATABASE + IMPL + ADD_ACCOUNT");
                            isRdbAddAccout = true;
                        }else {
                            logger.debug("update:::PAAS_RELATIONAL_DATABASE + IMPL + ADD_DATABASE");
                            throw new BaseException("该状态不能修改");
                        }
                    }else {
                        logger.debug("update::PAAS_RELATIONAL_DATABASE + IMPL+baseList!=1");
                        throw new BaseException("该状态不能修改");
                    }
                }else {
                    logger.debug("update::non (PAAS_RELATIONAL_DATABASE + IMPL) + non （REVIEW + ...） ");
                    throw new BaseException("该状态不能修改");
                }
            }
        }

        // 待发送消息
        List<Message> message = null;
        /*
         * 修改状态
         * 1. 如果状态为购物车,则不改变当前任何状态,只修改申请数据
         * 2. 如果提交到科信审核,则修改状态为待审核
         * 3. 如果提交到部门内审核,则修改为部门内待审核
         * 4. 如果是服务台复核,则不改变当前任何状态,只修改申请数据
         * 5. 如果是关系型数据库新增账号业务办理，不改变当前任何状态,只修改申请数据 （2020-4-23）
         */
        if (!"1".equals(dbInfo.getFlowNew())) {
            if (status == ApplicationInfoStatus.SHOPPING_CART) {
                //购物车状态,只修改申请数据
            } else if (status == ApplicationInfoStatus.REVIEW) {
                // 服务台复核
            } else if(status == ApplicationInfoStatus.IMPL && isRdbAddAccout){
                //关系型数据库新增账号业务办理
            }else {
                if ("kx".equals(type)) {
                    // 科信审核
                    List<FlowStep> flowStepList = flowStepService.getFlowStepList(info.getServiceTypeId(), info.getResourceType(), TYPE_REVIEW);
                    String flowStepId = flowStepList.get(0).getId();
                    this.update(info.getId(), ApplicationInfoStatus.REVIEW, flowStepId, null);

                    message = buildProcessingMessage(dbInfo, flowStepUserService.findUserByFlowStepId(flowStepId));
                } else {
                    // 部门内审核
                    List<String> userIds = updateInfo.getUserIds();
                    if (userIds == null || userIds.isEmpty()) {
                        throw new BaseException("请选择审核人!");
                    }
                    innerReviewUserService.infoUser(info.getId(), userIds);
                    this.update(info.getId(), ApplicationInfoStatus.INNER_REVIEW, null, null);

                    message = buildProcessingMessage(dbInfo, userService.findUserByIds(userIds));
                }
            }
        }
        // 关联服务器信息
        if (handler != null) {
            handler.update(info);
        }
        // 关联文件信息
        refFiles(info.getFileList(), info.getId());
        // 更新主表
        info.setServiceTypeId(null);
        info.setHwPoliceCategory(AreaPoliceCategoryUtils.getPoliceCategory(info.getAppName()));
        info.updateById();

        // 异步发送消息
        if (message != null) {
            messageProvider.sendMessageAsync(message);
        }
    }

    private void refFiles(List<Files> files, String refId) {
        if (StringUtils.isEmpty(refId)) {
            return;
        }
        filesService.remove(new QueryWrapper<Files>().lambda().eq(Files::getRefId, refId));
        if (files != null && !files.isEmpty()) {
            for (Files f : files) {
                f.setId(null);
                f.setRefId(refId);
            }
            filesService.saveBatch(files);
        }
    }

    @Override
    public <S, I> ApplicationInfo<S, I> getDetail(User user, String id, IApplicationHandler<S> handler, IImplHandler<I> implHandler) {
        ApplicationInfo<S, I> info = applicationInfoMapper.getAppInfo(id);
        if (handler != null) {
            info.setServerList(handler.getByAppInfoId(id));
        }
        List<Files> filesList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, id));
        info.setFileList(filesList);
        // 申请人
        User creator = userService.findUserByIdcard(info.getCreator());
        info.setUser(creator);
        ApplicationInfoStatus curStatus = ApplicationInfoStatus.codeOf(info.getStatus());
        if (curStatus == ApplicationInfoStatus.SHOPPING_CART) {
            FormNum formNum = FormNum.getFormNumByInfo(info);
            String description = null;
            String instructions = null;
            if (formNum.getResourceType() == ResourceType.PAAS) {
                Paas paas = paasService.getById(info.getServiceTypeId());
                if (paas != null) {
                    description = paas.getDescription();
                    instructions = paas.getInstructions();
                    info.setTempList(filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, paas.getId())));
                }
            } else if (formNum.getResourceType() == ResourceType.SAAS) {
                Saas saas = saasService.getById(info.getServiceTypeId());
                if (saas != null) {
                    description = saas.getDescription();
                    instructions = saas.getInstructions();
                }
            } else if (formNum.getResourceType() == ResourceType.IAAS) {
                IaasNew iaas = iaasNewService.getById(info.getServiceTypeId());
                if (iaas != null) {
                    description = iaas.getDescription();
                    instructions = iaas.getInstructions();
                }
            }
            info.setDescription(description);
            info.setInstructions(instructions);
            return info; // 如果当前条目为购物车状态,不查询审核相关信息
        }

        // 审批流程信息
        FlowDetail flowDetail = new FlowDetail();
        List<AppReviewInfo> allReviewInfo = appReviewInfoService.getAllReviewInfoByAppInfoId(id);
        List<AppReviewInfo> reviewInfoList = new ArrayList<>();
        for (AppReviewInfo ari : allReviewInfo) {
            if ("1".equals(ari.getrType())) {
                // 审核记录
                if (INNER_REVIEW_STEP_NAME.equals(ari.getStepName())) {
                    flowDetail.getInner().add(ari);
                } else if (FWT_REVIEW_STEP_NAME.equals(ari.getStepName())) {
                    flowDetail.getFwtfh().add(ari);
                } else {
                    flowDetail.getKx().add(ari);
                }
            } else {
                // 实施记录
                flowDetail.getImpl().add(ari);
            }
            // 把审核记录全部查出来, 实施记录只查被驳回的
            if ("1".equals(ari.getrType()) || ("2".equals(ari.getrType()) && !"1".equals(ari.getResult()))) {
                reviewInfoList.add(ari);
            }
        }
        // 反馈记录
        List<ApplicationFeedback> feedback = applicationFeedbackService.getListByAppInfoId(info.getId());
        if (feedback != null && !feedback.isEmpty()) {
            flowDetail.getFeedback().addAll(feedback);
        }
        info.setFlowDetail(flowDetail);

        // 审核信息
        info.setReviewList(reviewInfoList);

        // 实施信息服务器信息
        if (implHandler != null) {
            info.setImplServerList(implHandler.getImplServerListByAppInfoId(id));
        }
        // 实施审批信息
        AppReviewInfo implInfo = null;
        AppReviewInfo lastReviewInfo = appReviewInfoService.getLastPassReviewInfoByAppInfoId(id);
        if (lastReviewInfo != null && "2".equals(lastReviewInfo.getrType())) {
            // 最近一条审核记录为实施记录
            implInfo = lastReviewInfo;
            List<Files> implFileList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, implInfo.getId()));
            implInfo.setFileList(implFileList);
            info.setImpl(implInfo);
        }
        // 如果该申请为待实施状态,并且最近没有实施通过记录,当前用户是实施人,那么该记录可以实施
        if (curStatus == ApplicationInfoStatus.IMPL && implInfo == null) {
            List<String> stepIds = getUserFlowStepIdList(user, info, TYPE_IMPL);
            if (stepIds != null && !stepIds.isEmpty()) {
                if (stepIds.contains(info.getFlowStepId())) {
                    info.setCanImpl(true);
                }
            }
        }
        // 判断是否可以修改
        if (Objects.equals(user.getIdcard(), info.getCreator())) {
            if (curStatus == ApplicationInfoStatus.REVIEW_REJECT
                    || curStatus == ApplicationInfoStatus.IMPL_REJECT
                    || curStatus == ApplicationInfoStatus.INNER_REJECT) {
                info.setCanEdit(true);
            }
        }
        if (curStatus == ApplicationInfoStatus.REVIEW) {
            FlowStep step = flowStepService.getById(info.getFlowStepId());
            if (step != null && FWT_REVIEW_STEP_NAME.equals(step.getName())) {
                // 如果是服务台复核,那么复核人可以修改
                List<User> userList = flowStepUserService.findUserByFlowStepId(info.getFlowStepId());
                if (userList != null && !userList.isEmpty()) {
                    for (User u : userList) {
                        if (Objects.equals(user.getIdcard(), u.getIdcard())) {
                            info.setCanEdit(true);
                            break;
                        }
                    }
                }
            }
        }
        // 判断是否能加办
        if (curStatus == ApplicationInfoStatus.REVIEW
                || curStatus == ApplicationInfoStatus.ADD
                || curStatus == ApplicationInfoStatus.FORWARD) {
            info.setCanAdd(true);
        }
        // 判断是够能反馈
        if (curStatus == ApplicationInfoStatus.USE
                && user.getIdcard().equals(info.getCreator())) {
            // 如果没有反馈过,就可以进行反馈
            int count = applicationFeedbackService.getCountByAppInfoId(info.getId());
            if (count <= 0) {
                info.setCanFeedback(true);
            }
        }
        // 办理进度
        switch (curStatus) {
            case INNER_REVIEW:
            case INNER_REJECT:
            case REVIEW_REJECT:
            case IMPL_REJECT:
                info.setProgressNo(2);
                break;
            case REVIEW:
            case ADD:
            case FORWARD:
                info.setProgressNo(4);
                break;
            case IMPL:
                info.setProgressNo(5);
                break;
            case USE:
                info.setProgressNo(6);
                break;
            default:
                info.setProgressNo(1);
                break;
        }
        // 服务台复核
        if (curStatus == ApplicationInfoStatus.REVIEW) {
            // 当前是科信审核,并且待处理步骤为服务台复核
            int count = flowStepService.count(new QueryWrapper<FlowStep>().lambda()
                    .eq(FlowStep::getId, info.getFlowStepId())
                    .eq(FlowStep::getType, 1)
                    .eq(FlowStep::getName, FWT_REVIEW_STEP_NAME));
            if (count > 0) {
                info.setProgressNo(3);
            }
        }

        return info;
    }

    @Override
    public <S, I> ApplicationInfo<S, I> getNewFlowDetail(User user, String id, IApplicationHandler<S> handler, IImplHandler<I> implHandler) {
        ApplicationInfo<S, I> info = applicationInfoMapper.getAppInfo(id);
        if (handler != null) {
            info.setServerList(handler.getByAppInfoId(id));
        }
        List<Files> filesList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, id));
        info.setFileList(filesList);
        // 申请人
        User creator = userService.findUserByIdcard(info.getCreator());
        info.setUser(creator);
        ApplicationInfoStatus curStatus = ApplicationInfoStatus.codeOf(info.getStatus());
        if (curStatus == ApplicationInfoStatus.SHOPPING_CART) {
            FormNum formNum = FormNum.getFormNumByInfo(info);
            String description = null;
            String instructions = null;
            if (formNum.getResourceType() == ResourceType.PAAS) {
                Paas paas = paasService.getById(info.getServiceTypeId());
                if (paas != null) {
                    description = paas.getDescription();
                    instructions = paas.getInstructions();
                    info.setTempList(filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, paas.getId())));
                }
            } else if (formNum.getResourceType() == ResourceType.SAAS) {
                Saas saas = saasService.getById(info.getServiceTypeId());
                if (saas != null) {
                    description = saas.getDescription();
                    instructions = saas.getInstructions();
                }
            } else if (formNum.getResourceType() == ResourceType.IAAS) {
                IaasNew iaas = iaasNewService.getById(info.getServiceTypeId());
                if (iaas != null) {
                    description = iaas.getDescription();
                    instructions = iaas.getInstructions();
                }
            }
            info.setDescription(description);
            info.setInstructions(instructions);
            return info; // 如果当前条目为购物车状态,不查询审核相关信息
        }
        List<AppReviewInfo> allReviewInfo = appReviewInfoService.getAllReviewInfoByAppInfoId(id);
        info.setReviewList(allReviewInfo);
        // 实施信息服务器信息
        if (implHandler != null) {
            info.setImplServerList(implHandler.getImplServerListByAppInfoId(id));
        }
        // 实施审批信息
        AppReviewInfo implInfo = null;
        AppReviewInfo lastReviewInfo = appReviewInfoService.getLastPassReviewInfoByAppInfoId(id);
        logger.debug("detail lastReviewInfo -> {}",lastReviewInfo);
        if (lastReviewInfo != null && "2".equals(lastReviewInfo.getrType())) {
            // 最近一条审核记录为实施记录
            implInfo = lastReviewInfo;
            List<Files> implFileList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, implInfo.getId()));
            implInfo.setFileList(implFileList);
            logger.debug("detail implInfo -> {}",implInfo);
            info.setImpl(implInfo);
        }
        return info;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void shoppingCartSubmit(User user, Map<String, Object> param) {
        List<String> ids = (List<String>) param.get("ids");
        List<String> userIds = (List<String>) param.get("userIds");
        String type = (String) param.get("type");

        List<ApplicationInfo> list = this.list(new QueryWrapper<ApplicationInfo>().lambda()
                .eq(ApplicationInfo::getStatus, ApplicationInfoStatus.SHOPPING_CART.getCode())
                .eq(ApplicationInfo::getCreator, user.getIdcard())
                .in(ApplicationInfo::getId, ids));
        if (list == null || list.isEmpty()) {
            return;
        }
        for (ApplicationInfo info : list) {
            if ("1".equals(info.getDraft())) {
                throw new BaseException("草稿不能提交");
            }
            new Log(user.getIdcard(), "服务名称：" + info.getServiceTypeName() + ";申请单号：" + info.getOrderNumber(), "提交申请", IpUtil.getIp()).insert();
        }

        // 待发送消息
        List<Message> msgList = new ArrayList<>(list.size() * 2);
        Date now = new Date();
        if ("kx".equals(type)) {
            // 科信审核
            Map<Long, List<FlowStep>> stepMap = new HashMap<>(6);
            // 修改状态为待审核
            for (ApplicationInfo info : list) {
                info.setCreateTime(now);
                info.setModifiedTime(now);
                Long resourceType = info.getResourceType();
                List<FlowStep> flowStepList = stepMap.get(resourceType);
                if (flowStepList == null) {
                    flowStepList = flowStepService.getFlowStepList(info.getServiceTypeId(), resourceType, TYPE_REVIEW);
                    stepMap.put(resourceType, flowStepList);
                }
                info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
                String flowStepId = flowStepList.get(0).getId();
                info.setFlowStepId(flowStepId);

                // 给申请人发送的消息
                Message toUser = buildSuccessMessage(user, info);
                msgList.add(toUser);
                // 给处理人发送的消息
                List<Message> toProcessingPerson = buildProcessingMessage(info, flowStepUserService.findUserByFlowStepId(flowStepId));
                msgList.addAll(toProcessingPerson);
            }
            this.updateBatchById(list);
        } else {
            // 部门内审核
            List<InnerReviewUser> irList = new ArrayList<>();
            for (ApplicationInfo info : list) {
                info.setCreateTime(now);
                info.setModifiedTime(now);
                for (String userId : userIds) {
                    InnerReviewUser innerReviewUser = new InnerReviewUser();
                    innerReviewUser.setAppInfoId(info.getId());
                    innerReviewUser.setUserId(userId);
                    irList.add(innerReviewUser);
                }
                // 修改状态为部门内审核
                info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());

                // 给申请人发送的消息
                Message toUser = buildSuccessMessage(user, info);
                msgList.add(toUser);
                // 给处理人发送的消息
                List<Message> toProcessingPerson = buildProcessingMessage(info, userService.findUserByIds(userIds));
                msgList.addAll(toProcessingPerson);
            }
            // 与部门内审核人建立关联
            innerReviewUserService.saveBatch(irList);
            this.updateBatchById(list);
        }

        // 异步发送消息
        messageProvider.sendMessageAsync(msgList);
    }

    @Override
    public List<ApplicationInfo> shoppingCart(User user) {
        List<ApplicationInfo> list = this.list(new QueryWrapper<ApplicationInfo>().lambda()
                .eq(ApplicationInfo::getCreator, user.getIdcard())
                .eq(ApplicationInfo::getStatus, ApplicationInfoStatus.SHOPPING_CART.getCode())
                .orderByDesc(ApplicationInfo::getModifiedTime));
        return list;
    }

    @Override
    public int shoppingCartCount(User user) {
        int count = this.count(new QueryWrapper<ApplicationInfo>().lambda()
                .eq(ApplicationInfo::getCreator, user.getIdcard())
                .eq(ApplicationInfo::getStatus, ApplicationInfoStatus.SHOPPING_CART.getCode()));
        return count;
    }

    @Override
    public IPage<ApplicationInfo> getPage(User user, IPage<ApplicationInfo> page, String userName, String status,
                                          Long resourceType, String processType) {
        // 该资源类型,用户可审核的所有环节(包含审核和实施)
        List<FlowStep> allUserStepIds = flowStepService.getUserAllFlowStepList(user, resourceType);
        // 该资源类型,用户参与的审核环节
        List<String> reviewStepIds = new ArrayList<>();
        // where in 条件
        List<String> stepIds = new ArrayList<>();
        if (allUserStepIds != null && !allUserStepIds.isEmpty()) {
            for (FlowStep step : allUserStepIds) {
                stepIds.add(step.getId());
                if (Long.valueOf(1L).equals(step.getType())) {
                    reviewStepIds.add(step.getId());
                }
            }
        }

        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("userName", userName);
        param.put("status", status);
        param.put("stepIds", stepIds);
        param.put("resourceType", resourceType);
        param.put("processType", processType);
        page = applicationInfoMapper.getPage(page, param);

        /*
         * 1. 如果用户是审核人,并且该条记录状态为待审核,那么该记录应该可以审核
         * 2. 如果状态是部门审核,标致不为空,代表可以审核
         * 3. 如果状态是加办/转发,标致不为空,代表可以审核
         */
        List<ApplicationInfo> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            for (ApplicationInfo record : records) {
                ApplicationInfoStatus ais = ApplicationInfoStatus.codeOf(record.getStatus());
                // 判断是否能删除
                if (ais != ApplicationInfoStatus.DELETE
                        && Objects.equals(user.getIdcard(), record.getCreator())) {
                    record.setCanDelete(true);
                }
                // 判断是否能加办
                if (ais == ApplicationInfoStatus.REVIEW
                        || ais == ApplicationInfoStatus.ADD
                        || ais == ApplicationInfoStatus.FORWARD) {
                    record.setCanAdd(true);
                }
                // 判断是否能审核
                if (ais == ApplicationInfoStatus.REVIEW) {
                    if (reviewStepIds.contains(record.getFlowStepId())) {
                        record.setCanReview(true);
                    }
                } else if (ais == ApplicationInfoStatus.INNER_REVIEW
                        || ais == ApplicationInfoStatus.ADD
                        || ais == ApplicationInfoStatus.FORWARD) {
                    if (record.getReviewFlag() != null) {
                        record.setCanReview(true);
                    }
                }
                // 当前环节处理人
                if (ais == ApplicationInfoStatus.REVIEW || ais == ApplicationInfoStatus.IMPL) {
                    if (StringUtils.isNotEmpty(record.getFlowStepId())) {
                        String processingPerson = applicationInfoMapper.getProcessingPersonByStepId(record.getFlowStepId());
                        record.setProcessingPerson(processingPerson);
                    }
                } else if (ais == ApplicationInfoStatus.INNER_REVIEW || ais == ApplicationInfoStatus.ADD
                        || ais == ApplicationInfoStatus.FORWARD) {
                    String processingPerson = applicationInfoMapper.getProcessingPersonByAppInfoId(record.getId());
                    record.setProcessingPerson(processingPerson);
                }
            }
        }

        return page;
    }


    @Override
    public IPage<ApplicationInfo> getFlowPage(User user, IPage<ApplicationInfo> page, Map<String, Object> param) {
        page = applicationInfoMapper.getFlowPage(page, param);
        List<ApplicationInfo> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
           speedUpService.dealProcessingPerson(records,user);
        }
        return page;
    }





    @Override
    public IPage<ApplicationInfo> getFlowPageOfDaas(User user, IPage<ApplicationInfo> page, Map<String, Object> param) {
        page = applicationInfoMapper.getFlowPageOfDaas(page, param);
        List<ApplicationInfo> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
           speedUpService.dealProcessingPerson(records,user);
        }
        return page;
    }

    @Override
    public IPage<ApplicationInfo> getFlowPageOfSaasService(User user, IPage<ApplicationInfo> page, Map<String, Object> param) {
        page = applicationInfoMapper.getFlowPageOfSaasService(page, param);
        List<ApplicationInfo> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            speedUpService.dealProcessingPerson(records,user);
        }
        return page;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void review(User user, Map<String, Object> param) throws Exception {
        String id = (String) param.get("id");
        ApplicationInfo info = this.getById(id);

        ApplicationInfoStatus curStatus = ApplicationInfoStatus.codeOf(info.getStatus());
        if (curStatus != ApplicationInfoStatus.REVIEW
                && curStatus != ApplicationInfoStatus.INNER_REVIEW
                && curStatus != ApplicationInfoStatus.ADD
                && curStatus != ApplicationInfoStatus.FORWARD) {
            throw new BaseException("此状态不能审核!");
        }

        // 审核结果
        String result = (String) param.get("result");
        // 添加审核信息
        AppReviewInfo reviewInfo = new AppReviewInfo();
        reviewInfo.setCreator(user.getIdcard());
        reviewInfo.setResult(result);
        reviewInfo.setRemark((String) param.get("remark"));
        reviewInfo.setrType("1");
        String stepName = "";
        String stepId = null;
        if (ApplicationInfoStatus.REVIEW.getCode().equals(info.getStatus())) {
            FlowStep step = flowStepService.getById(info.getFlowStepId());
            if (step != null) {
                stepName = step.getName();
            }
            stepId = info.getFlowStepId();
        } else if (curStatus == ApplicationInfoStatus.FORWARD) {
            stepName = FORWARD_REVIEW_STEP_NAME;
            stepId = info.getFlowStepIdBak();
        } else if (curStatus == ApplicationInfoStatus.ADD) {
            stepName = ADD_REVIEW_STEP_NAME;
            stepId = info.getFlowStepIdBak();
        } else {
            stepName = INNER_REVIEW_STEP_NAME;
            stepId = null;
        }
        reviewInfo.setStepName(stepName);
        reviewInfo.setFlowStepId(stepId);
        reviewInfo.setAppInfoId(info.getId());
        reviewInfo.insert();

        // 待发送消息
        List<Message> msgList = new ArrayList<>();

        if ("0".equals(result)) {
            // 驳回申请
            if (curStatus == ApplicationInfoStatus.REVIEW
                    || curStatus == ApplicationInfoStatus.ADD
                    || curStatus == ApplicationInfoStatus.FORWARD) {
                // 科信驳回
                this.update(info.getId(), ApplicationInfoStatus.REVIEW_REJECT, null, null);
            } else {
                // 部门内驳回
                this.update(info.getId(), ApplicationInfoStatus.INNER_REJECT, null, null);
            }
            // 发送通知消息
            Message rejectMessage = buildRejectMessage(info);
            msgList.add(rejectMessage);
            // 如果有部门内审核人,删除之
            innerReviewUserService.removeByAppInfoId(info.getId());
        } else {
            String type = (String) param.get("type");
            // 通过申请
            if ("kx".equals(type)) {
                // 科信审核(状态为部门内审核/科信审核/转发/加办都可以提交到科信审核)
                // 获取申请对应的审核步骤
                List<FlowStep> flowStepList = flowStepService.getFlowStepList(info.getServiceTypeId(), info.getResourceType(), TYPE_REVIEW);
                if (curStatus == ApplicationInfoStatus.INNER_REVIEW) {
                    List<InnerReviewUser> list = innerReviewUserService.getList(info.getId(), user.getIdcard());
                    if (list == null || list.isEmpty()) {
                        throw new BaseException("无审核权限");
                    }
                    // 如果还是部门审核状态,把状态改为待审核
                    String flowStepId = flowStepList.get(0).getId();
                    this.update(info.getId(), ApplicationInfoStatus.REVIEW, flowStepId, null);

                    // 给处理人发送短信
                    List<Message> message = buildProcessingMessage(info, flowStepUserService.findUserByFlowStepId(flowStepId));
                    msgList.addAll(message);
                } else if (curStatus == ApplicationInfoStatus.REVIEW) {
                    List<String> stepIds = getUserFlowStepIdList(user, info, TYPE_REVIEW);
                    if (!stepIds.contains(info.getFlowStepId())) {
                        throw new BaseException("无审核权限");
                    }
                    int curStep = -1;
                    for (int i = 0; i < flowStepList.size(); i++) {
                        if (Objects.equals(flowStepList.get(i).getId(), info.getFlowStepId())) {
                            curStep = i;
                            break;
                        }
                    }
                    if (curStep == -1) {
                        throw new BaseException("审核失败");
                    }
                    processKxNext(info, msgList, flowStepList, curStep);
                } else if (curStatus == ApplicationInfoStatus.ADD || curStatus == ApplicationInfoStatus.FORWARD) {
                    List<InnerReviewUser> list = innerReviewUserService.getList(info.getId(), user.getIdcard());
                    if (list == null || list.isEmpty()) {
                        throw new BaseException("无审核权限");
                    }
                    int curStep = -1;
                    for (int i = 0; i < flowStepList.size(); i++) {
                        if (Objects.equals(flowStepList.get(i).getId(), info.getFlowStepIdBak())) {
                            curStep = i;
                            break;
                        }
                    }
                    if (curStep == -1) {
                        throw new BaseException("审核失败");
                    }
                    processKxNext(info, msgList, flowStepList, curStep);
                }
                // 如果有部门内审核人,删除之
                innerReviewUserService.removeByAppInfoId(info.getId());
            } else if ("inner".equals(type)) {
                // 部门内审核(状态为部门内审核的才能提交到部门内审核)
                if (curStatus != ApplicationInfoStatus.INNER_REVIEW) {
                    throw new BaseException("此状态不能部门内审核!");
                }
                List<InnerReviewUser> list = innerReviewUserService.getList(info.getId(), user.getIdcard());
                if (list == null || list.isEmpty()) {
                    throw new BaseException("无审核权限");
                }
                // 插入下一级审核人
                List<String> userIds = (List<String>) param.get("userIds");
                innerReviewUserService.infoUser(info.getId(), userIds);

                // 给处理人发送短信
                List<Message> message = buildProcessingMessage(info, userService.findUserByIds(userIds));
                msgList.addAll(message);
            } else if ("add".equals(type)) {
                // 加办(状态为科信审核/转发/加办都可以加办)
                if (curStatus == ApplicationInfoStatus.REVIEW) {
                    List<String> stepIds = getUserFlowStepIdList(user, info, TYPE_REVIEW);
                    if (!stepIds.contains(info.getFlowStepId())) {
                        throw new BaseException("无审核权限");
                    }
                    // 修改状态为加办,并备份加办时的流程id
                    this.update(info.getId(), ApplicationInfoStatus.ADD, null, info.getFlowStepId());
                } else if (curStatus == ApplicationInfoStatus.ADD || curStatus == ApplicationInfoStatus.FORWARD) {
                    List<InnerReviewUser> list = innerReviewUserService.getList(info.getId(), user.getIdcard());
                    if (list == null || list.isEmpty()) {
                        throw new BaseException("无审核权限");
                    }
                    // 修改状态为加办,并备份加办时的流程id
                    this.update(info.getId(), ApplicationInfoStatus.ADD, null, info.getFlowStepIdBak());
                } else {
                    // 其它状态不能加办!
                    throw new BaseException("此状态不能加办!");
                }
                // 插入下一级审核人
                List<String> userIds = (List<String>) param.get("userIds");
                innerReviewUserService.infoUser(info.getId(), userIds);

                // 给处理人发送短信
                List<Message> message = buildProcessingMessage(info, userService.findUserByIds(userIds));
                msgList.addAll(message);
            }
        }

        // 异步发送消息
        messageProvider.sendMessageAsync(msgList);
    }

    /**
     * 科信审核下一步
     */
    private void processKxNext(ApplicationInfo info, List<Message> msgList, List<FlowStep> flowStepList, int curStep) throws Exception {
        if (curStep < flowStepList.size() - 1) {
            // 审核步骤还没走完,修改申请的审核步骤为下一步骤
            String flowStepId = flowStepList.get(curStep + 1).getId();
            this.update(info.getId(), ApplicationInfoStatus.REVIEW, flowStepId, null);

            // 给处理人发送短信
            List<Message> message = buildProcessingMessage(info, flowStepUserService.findUserByFlowStepId(flowStepId));
            msgList.addAll(message);
        } else {
            if (daasAutoEnable) {
                FormNum formNum = FormNum.getFormNumByInfo(info);
                if (formNum == FormNum.DAAS) { // daas 自动订购

                    daasApigService.orderService(info);

                    // 插入一条审核记录
                    AppReviewInfo reviewInfo = new AppReviewInfo();
                    reviewInfo.setCreator("511321198706011277"); // 默认处理人:范中文
                    reviewInfo.setResult("1");
                    reviewInfo.setRemark("自动实施完成");
                    reviewInfo.setrType("2");
                    reviewInfo.setStepName("实施");
                    reviewInfo.setFlowStepId(info.getFlowStepId());
                    reviewInfo.setAppInfoId(info.getId());
                    reviewInfo.insert();
                    // 订购完成,修改申请为使用状态
                    this.update(info.getId(), ApplicationInfoStatus.USE, null, null);
                    // 发送通知消息
                    Message message = buildCompleteMessage(info);
                    msgList.add(message);
                    return;
                }
            }
            if (paasAutoEnable) {
                FormNum formNum = FormNum.getFormNumByInfo(info);
                if (formNum == FormNum.PAAS_FSEQSQM || formNum == FormNum.PAAS_OCRWT || formNum == FormNum.PAAS_OCRYM || formNum == FormNum.PAAS_TXRLSB || formNum == FormNum.PAAS_DZY) {

                    //Map map= paasApigService.orderService(info,);


                }
            }
            // 审核步骤已完成,修改申请为实施状态
            List<FlowStep> implList = flowStepService.getFlowStepList(info.getServiceTypeId(), info.getResourceType(), TYPE_IMPL);
            String flowStepId = implList.get(0).getId();
            this.update(info.getId(), ApplicationInfoStatus.IMPL, flowStepId, null);

            // 给处理人发送短信
            List<Message> message = buildProcessingMessage(info, flowStepUserService.findUserByFlowStepId(flowStepId));
            msgList.addAll(message);
        }
    }

    private void update(String id, ApplicationInfoStatus status, String flowId, String flowIdBak) {
        this.update(new ApplicationInfo(), new UpdateWrapper<ApplicationInfo>().lambda()
                .eq(ApplicationInfo::getId, id)
                .set(ApplicationInfo::getStatus, status.getCode())
                .set(ApplicationInfo::getFlowStepId, flowId)
                .set(ApplicationInfo::getFlowStepIdBak, flowIdBak));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void forward(User user, Map<String, Object> param) {
        String id = (String) param.get("id");
        ApplicationInfo info = this.getById(id);
        ApplicationInfoStatus curStatus = ApplicationInfoStatus.codeOf(info.getStatus());

        if (curStatus != ApplicationInfoStatus.REVIEW
                && curStatus != ApplicationInfoStatus.ADD
                && curStatus != ApplicationInfoStatus.FORWARD
                && curStatus != ApplicationInfoStatus.INNER_REVIEW) {
            throw new BaseException("此状态不能转发!");
        }

        /*
         * 1. 当处于部门内审核时!!!!!!注意:不改变状态!!!!!!!直接置换审核人
         * 2. 当处于科信审核时,修改状态为转发,保存转发人
         * 3. 当处于加办时,修改状态为转发,保存转发人
         * 4. 当处于转发时,保存转发人
         */
        String userId = param.get("userId").toString();
        if (curStatus == ApplicationInfoStatus.INNER_REVIEW) {
            List<InnerReviewUser> list = innerReviewUserService.getList(info.getId(), user.getIdcard());
            if (list == null || list.isEmpty()) {
                throw new BaseException("无转发权限!");
            }
            // 把当前处理人置换为转发人
            innerReviewUserService.update(new InnerReviewUser(), new UpdateWrapper<InnerReviewUser>().lambda()
                    .eq(InnerReviewUser::getAppInfoId, info.getId())
                    .set(InnerReviewUser::getUserId, userId));
        } else if (curStatus == ApplicationInfoStatus.REVIEW) {
            List<String> flowStepIds = getUserFlowStepIdList(user, info, TYPE_REVIEW);
            if (!flowStepIds.contains(info.getFlowStepId())) {
                throw new BaseException("无转发权限!");
            }
            // 修改状态为转发
            this.update(info.getId(), ApplicationInfoStatus.FORWARD, null, info.getFlowStepId());
            // 插入转发审核人
            innerReviewUserService.infoUser(info.getId(), Collections.singletonList(userId));
        } else if (curStatus == ApplicationInfoStatus.ADD) {
            List<InnerReviewUser> list = innerReviewUserService.getList(info.getId(), user.getIdcard());
            if (list == null || list.isEmpty()) {
                throw new BaseException("无转发权限!");
            }
            // 修改状态为转发
            info.setStatus(ApplicationInfoStatus.FORWARD.getCode());
            info.setFlowStepId(null);
            info.updateById();
            // 插入转发审核人
            innerReviewUserService.infoUser(info.getId(), Collections.singletonList(userId));
        } else if (curStatus == ApplicationInfoStatus.FORWARD) {
            List<InnerReviewUser> list = innerReviewUserService.getList(info.getId(), user.getIdcard());
            if (list == null || list.isEmpty()) {
                throw new BaseException("无转发权限!");
            }
            // 插入转发审核人
            innerReviewUserService.infoUser(info.getId(), Collections.singletonList(userId));
        }

        // 给处理人发送短信
        List<Message> message = buildProcessingMessage(info, userService.findUserByIds(Collections.singletonList(userId)));
        // 异步发送消息
        messageProvider.sendMessageAsync(message);
    }

    @Override
    public void feedback(User user, String id, ApplicationFeedback feedback) {
        ApplicationInfo info = this.getById(id);
        if (info == null) {
            throw new BaseException("该申请不存在!");
        }
        // 当处于使用中并且是自己申请的应用时才能进行反馈
        if (!ApplicationInfoStatus.USE.getCode().equals(info.getStatus())
                || !user.getIdcard().equals(info.getCreator())) {
            throw new BaseException("无权限修改");
        }
        feedback.setAppInfoId(id);
        feedback.setCreator(user.getIdcard());
        feedback.insert();
    }

    @Override
    public List<ApplicationInfo> findByAppName(String status) {
        return applicationInfoMapper.findByAppName(status);
    }


    @Transactional
    @Override
    public void revertStatusByResourceType(Long resourceType) {
        // 先清除转发/加办数据
        innerReviewUserService.deleteAddAndForwardByResourceType(resourceType);
        // 重置主表状态
        List<FlowStep> flowStepList = flowStepService.getFlowStepList(null, resourceType, TYPE_REVIEW);
        this.update(new ApplicationInfo(), new UpdateWrapper<ApplicationInfo>().lambda()
                .eq(ApplicationInfo::getResourceType, resourceType)
                .notIn(ApplicationInfo::getStatus,
                        ApplicationInfoStatus.DELETE.getCode(), ApplicationInfoStatus.USE.getCode(),
                        ApplicationInfoStatus.SHOPPING_CART.getCode(), ApplicationInfoStatus.SHOPPING_CART_DEL.getCode(),
                        ApplicationInfoStatus.INNER_REVIEW.getCode(), ApplicationInfoStatus.INNER_REJECT.getCode())
                .set(ApplicationInfo::getStatus, ApplicationInfoStatus.REVIEW.getCode())
                .set(ApplicationInfo::getFlowStepId, flowStepList.get(0).getId())
                .set(ApplicationInfo::getFlowStepIdBak, null));
    }

    @Transactional
    @Override
    public void revertStatusByServiceId(String serviceId) {
        // 先清除转发/加办数据
        innerReviewUserService.deleteAddAndForwardByServiceId(serviceId);
        // 重置主表状态
        List<FlowStep> flowStepList = flowStepService.getFlowStepList(serviceId, null, TYPE_REVIEW);
        this.update(new ApplicationInfo(), new UpdateWrapper<ApplicationInfo>().lambda()
                .eq(ApplicationInfo::getServiceTypeId, serviceId)
                .notIn(ApplicationInfo::getStatus,
                        ApplicationInfoStatus.DELETE.getCode(), ApplicationInfoStatus.USE.getCode(),
                        ApplicationInfoStatus.SHOPPING_CART.getCode(), ApplicationInfoStatus.SHOPPING_CART_DEL.getCode(),
                        ApplicationInfoStatus.INNER_REVIEW.getCode(), ApplicationInfoStatus.INNER_REJECT.getCode())
                .set(ApplicationInfo::getStatus, ApplicationInfoStatus.REVIEW.getCode())
                .set(ApplicationInfo::getFlowStepId, flowStepList.get(0).getId())
                .set(ApplicationInfo::getFlowStepIdBak, null));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public <I> void impl(User user, Map<String, Object> param, IImplHandler<I> implHandler) {
        ApplicationInfo info = (ApplicationInfo) param.get("info");

        if (!ApplicationInfoStatus.IMPL.getCode().equals(info.getStatus())) {
            throw new BaseException("不为待实施状态");
        }

        List<String> stepIds = getUserFlowStepIdList(user, info, TYPE_IMPL);
        if (!stepIds.contains(info.getFlowStepId())) {
            throw new BaseException("无实施权限");
        }

        // 添加实施信息
        ImplRequest<I> implRequest = (ImplRequest<I>) param.get("implRequest");
        String result = implRequest.getResult();
        String remark = implRequest.getRemark();

        AppReviewInfo reviewInfo = new AppReviewInfo();
        reviewInfo.setCreator(user.getIdcard());
        reviewInfo.setResult(result);
        reviewInfo.setRemark(remark);
        reviewInfo.setrType("2");
        FlowStep step = flowStepService.getById(info.getFlowStepId());
        String stepName = "";
        if (step != null) {
            stepName = step.getName();
        }
        reviewInfo.setStepName(stepName);
        reviewInfo.setFlowStepId(info.getFlowStepId());
        reviewInfo.setAppInfoId(info.getId());
        reviewInfo.insert();
        // 实施附件
        refFiles(implRequest.getFileList(), reviewInfo.getId());

        Message message;
        if ("0".equals(result)) {
            // 发送通知消息
            message = buildRejectMessage(info);

            // 驳回申请
            this.update(info.getId(), ApplicationInfoStatus.IMPL_REJECT, null, null);
        } else {
            // 发送通知消息
            message = buildCompleteMessage(info);

            // 实施步骤已完成,修改申请为使用状态
            this.update(info.getId(), ApplicationInfoStatus.USE, null, null);
        }
        innerReviewUserService.removeByAppInfoId(info.getId());
        if (implHandler != null) {
            // 与实施服务器信息建立关系
            List<I> serverList = implRequest.getImplServerList();
            implHandler.update(info.getId(), serverList);
        }

        // 异步发送消息
        messageProvider.sendMessageAsync(message);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public <I> void saveImpl(User user, Map<String, Object> param, IImplHandler<I> implHandler, String modelId) throws Exception {
        ApplicationInfo info = (ApplicationInfo) param.get("info");
        // 添加实施信息
        ImplRequest<I> implRequest = (ImplRequest<I>) param.get("implRequest");
        String result = implRequest.getResult();
        String remark = implRequest.getRemark();

        AppReviewInfo reviewInfo = new AppReviewInfo();
        reviewInfo.setCreator(user.getIdcard());
        reviewInfo.setResult(result);
        reviewInfo.setRemark(remark);
        reviewInfo.setrType("2");
        reviewInfo.setStepName(ModelName.CARRY.getName());
        reviewInfo.setFlowStepId(modelId);
        reviewInfo.setAppInfoId(info.getId());
        logger.debug("impl reviewInfo -> {}",reviewInfo);
        reviewInfo.insert();
        // 实施附件
        refFiles(implRequest.getFileList(), reviewInfo.getId());
        Message message = null;
        Map<String, String> orderMap = null;
        if ("0".equals(result)) {
            // 驳回申请
            this.update(info.getId(), ApplicationInfoStatus.REVIEW, null, null);
        } else {

            if (daasAutoEnable) {
                FormNum formNum = FormNum.getFormNumByInfo(info);
                if (formNum == FormNum.DAAS) { // daas 自动订购

                    daasApigService.orderService(info);

                }
            }
            if (paasAutoEnable) {
                FormNum formNum = FormNum.getFormNumByInfo(info);
                logger.info("自动订购服务类型=====",formNum.getResourceType()+"-------"+info.getFormNum());
                if (implHandler instanceof IPaasFseqsqmImplService) {
                    orderMap = paasApigService.orderService(info, implHandler);
                } else if (formNum == FormNum.PAAS_DTSJGT || formNum == FormNum.PAAS_DTHTJY) {
                    orderMap = paasApigService.mapOrderService(info, implHandler);
                } else if (formNum == FormNum.PAAS_TYYH) {
                    //统一用户勾了系统对接才进行自动订购
                    PaasTyyh paasTyyh = tyyhService.getOne(new QueryWrapper<PaasTyyh>().lambda().eq(PaasTyyh::getAppInfoId, info.getId()));
                    if ("1".equals(paasTyyh.getBusinessType()) || "3".equals(paasTyyh.getBusinessType())) {
                        orderMap = paasApigService.orderService(info, implHandler);
                    }
                    //  勾选了添加人员才执行
                    if ("2".equals(paasTyyh.getBusinessType()) || "3".equals(paasTyyh.getBusinessType())) {
                        paasApigService.addTYYHMessage(info);
                    }
                } else if (formNum == FormNum.PAAS_TYXX) {
                    logger.info("统一消息自动订购");
                    orderMap = paasApigService.orderService(info, implHandler);
//                    paasApigService.addTYXXMessage(info);
                }
            }
            // 发送通知消息
            message = buildCompleteMessage(info);
            // 实施步骤已完成,修改申请为使用状态
            this.update(info.getId(), ApplicationInfoStatus.USE, null, null);
        }
        if (implHandler != null) {
            // 与实施服务器信息建立关系
            if(FormNum.PAAS_RELATIONAL_DATABASE.toString().equals(info.getFormNum()) && "0".equals(result)){
                rdbBaseService.emptyImplServerList(info.getId());

            }else {
                //业务办理通过才保存实施信息
                if("1".equals(result)){
                    List<I> serverList = implRequest.getImplServerList();
                    implHandler.update(info.getId(), serverList, orderMap);
                }
            }

        }
        // 异步发送消息
        if (message != null) {
            messageProvider.sendMessageAsync(message);
        }
    }

    /**
     * 后台逻辑删除
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteById(User user, String id) {
        ApplicationInfo info = this.getById(id);
        if (info == null) {
            throw new BaseException("该记录不存在!");
        }
        if (!Objects.equals(user.getIdcard(), info.getCreator())) {
            throw new BaseException("只能删除自己的申请!");
        }
        // 逻辑删除,并设置相应的状态
        this.update(info.getId(), ApplicationInfoStatus.DELETE, null, null);
        // 如果有部门内审核人,删除之
        innerReviewUserService.removeByAppInfoId(info.getId());
    }

    @Override
    public Map<String, Integer> newTodo(User user) {
        Map<String, Integer> map = new HashMap<>();
        int iaas = applicationInfoMapper.getNewCount(user, ResourceType.IAAS.getCode());
        map.put("iaas", iaas);
        int daas = applicationInfoMapper.getNewCount(user, ResourceType.DAAS.getCode());
        map.put("daas", daas);
        int paas = applicationInfoMapper.getNewCount(user, ResourceType.PAAS.getCode());

        int saasService = applicationInfoMapper.getNewCount(user, ResourceType.SAAS_SERVICE.getCode());
        map.put("saasService", saasService);

        map.put("paas", paas);
        int saas = saasApplicationService.getSaasTodoCount(user);
        map.put("saas", saas);
        int saasRegist = saasRegisterService.getTodoCount(user);
        map.put("saasRegist", saasRegist);
        //int paasRegist = paasRegisterService.getTodoCount(user);
       // map.put("paasRegist", paasRegist);

        int servicePublish = servicePublishService.getPublishTodoCount(user);
        map.put("servicePublish", servicePublish);

        int iaasZysb = iaasZysbMapper.getTodoCount(user);
        map.put("iaasZysb", iaasZysb);

        int saasRecover = saasRecoverInfoService.getTodoCount(user);
        map.put("saasRecover", saasRecover);

        //int total = iaas + daas + saas + paas + saasRegist + paasRegist + servicePublish;
        int total = iaas + daas + saas + paas + saasRegist + servicePublish + iaasZysb + saasRecover + saasService;
        map.put("total", total);

        return map;
    }

    public TodoVo getMaintenanceTodoVo(String idCard){
        User user = new User();
        user.setIdcard(idCard);
        int iaas = applicationInfoMapper.getNewCount(user, ResourceType.IAAS.getCode());

        int daas = applicationInfoMapper.getNewCount(user, ResourceType.DAAS.getCode());

        int paas = applicationInfoMapper.getNewCount(user, ResourceType.PAAS.getCode());

        int saasService = applicationInfoMapper.getNewCount(user, ResourceType.SAAS_SERVICE.getCode());

        int saas = saasApplicationService.getSaasTodoCount(user);


        //int total = iaas + daas + saas + paas + saasRegist + paasRegist + servicePublish;
        int total = iaas + daas + saas + paas + saasService;
        TodoVo todoVo = new TodoVo(total,iaasTodoUrl);
        return todoVo;
    }

    /**
     * 查询用户的适用资源类型的流程步骤
     *
     * @param info     申请单
     * @param stepType 类型 1:审核类型 2:实施类型
     */
    private List<String> getUserFlowStepIdList(User user, ApplicationInfo info, String stepType) {
        List<FlowStep> steps = flowStepService.getUserFlowStepList(user, info.getServiceTypeId(), info.getResourceType(), stepType);
        return steps.stream().map(FlowStep::getId).collect(Collectors.toList());
    }

    /**
     * 构造提交成功短信通知消息
     */
    private Message buildSuccessMessage(User user, ApplicationInfo info) {
        return messageProvider.buildSuccessMessage(user, info.getServiceTypeName(), info.getOrderNumber());
    }

    /**
     * 构造发送给处理人的短信消息
     */
    private List<Message> buildProcessingMessage(ApplicationInfo info, List<User> userList) {
        List<Message> msgList = new ArrayList<>();
        if (userList != null && !userList.isEmpty()) {
            for (User user : userList) {
                Message toProcessingPerson = messageProvider.buildProcessingMessage(info.getServiceTypeName(), info.getOrderNumber(), user.getIdcard());
                msgList.add(toProcessingPerson);
            }
        }
        return msgList;
    }

    /**
     * 构造完成申请短信通知消息
     */
    private Message buildCompleteMessage(ApplicationInfo info) {
        return messageProvider.buildCompleteMessage(info.getCreator(), info.getServiceTypeName(), info.getOrderNumber());
    }

    /**
     * 构造审核不通过的通知消息
     */
    private Message buildRejectMessage(ApplicationInfo info) {
        return messageProvider.buildRejectMessage(info.getCreator(), info.getServiceTypeName());
    }

    @Override
    public String getRePerson(String appInfoId) {
        return applicationInfoMapper.getRePerson(appInfoId);
    }

    @Override
    public ApplicationInfo getByActId(String activityId) {
        return applicationInfoMapper.getByActId(activityId);
    }

    @Override
    public EcsStatisticsTotal ecsStatistics4Recent(String type, String name) {
        EcsStatisticsTotal recent = applicationInfoMapper.ecsStatistics4Recent(type, name);
        return recent;
    }

    @Override
    public IPage<EcsStatistics> ecsList4Recent(IPage<EcsStatistics> page, String type, String name) {
        return applicationInfoMapper.ecsList4Recent(page, type, name);
    }

    @Override
    public EcsStatisticsTotal ecsStatistics4Applied(String type, String name) {
        EcsStatisticsTotal applied = applicationInfoMapper.ecsStatistics4Applied(type, name);
        return applied;
    }

    @Override
    public IPage<EcsStatistics> ecsList4Applied(IPage<EcsStatistics> page, String type, String name) {
        return applicationInfoMapper.ecsList4Applied(page, type, name);
    }

    @Override
    public EcsStatisticsTotal ecsStatistics4Review(String type, String name) {
        EcsStatisticsTotal review = applicationInfoMapper.ecsStatistics4Review(type, name);
        return review;
    }

    @Override
    public IPage<EcsStatistics> ecsList4Review(IPage<EcsStatistics> page, String type, String name) {
        return applicationInfoMapper.ecsList4Review(page, type, name);
    }

    @Override
    public List<String> getAppNameList(User user) {
        return applicationInfoMapper.getAppNameList(user);
    }

    @Override
    public List<ResourceCount> getIaasPaasUseRes(ResourceType resourceType, User user, String appName) {
        return applicationInfoMapper.getUseRes(resourceType.getCode(), user, appName);
    }

    @Override
    public Integer getDaasUseRes(User user, String appName) {
        return applicationInfoMapper.getDaasUseRes(user, appName);
    }

    @Override
    public int getReviewCount(ResourceType resourceType, User user, QueryVO queryVO) {
        Map<String, Object> param = Maps.newHashMap();
        if (CommonHandler.isTenantManager(user)) {
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        } else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO", queryVO);
        return applicationInfoMapper.getReviewCount(resourceType.getCode(), user, param);
    }

    @Override
    public int getImplCount(ResourceType resourceType, User user, QueryVO queryVO) {
        Map<String, Object> param = Maps.newHashMap();
        if (CommonHandler.isTenantManager(user)) {
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        } else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO", queryVO);
        return applicationInfoMapper.getImplCount(resourceType.getCode(), user, param);
    }

    @Override
    public int getRejectCount(ResourceType resourceType, User user, QueryVO queryVO) {
        Map<String, Object> param = Maps.newHashMap();
        if (CommonHandler.isTenantManager(user)) {
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        } else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO", queryVO);
        return applicationInfoMapper.getRejectCount(resourceType.getCode(), user, param);
    }

    @Override
    public int getUseCount(ResourceType resourceType, User user, QueryVO queryVO) {
        Map<String, Object> param = Maps.newHashMap();
        if (CommonHandler.isTenantManager(user)) {
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        } else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO", queryVO);
        return applicationInfoMapper.getUseCount(resourceType.getCode(), user, param);
    }

    @Override
    public IPage<ApplicationInfo> getIaasPaasWorkbenchPage(IPage<ApplicationInfo> page, Map<String, Object> param) {
        return applicationInfoMapper.getIaasPaasWorkbenchPage(page, param);
    }

    @Override
    public IPage<DaasApplicationExt> getDaasWorkbenchPage(IPage<DaasApplicationExt> page, Map<String, Object> param) {
        return applicationInfoMapper.getDaasWorkbenchPage(page, param);
    }

    @Override
    public List<ApplicationInfo> iaasOrderStatistics(Map params) {
        return applicationInfoMapper.iaasOrderStatistics(params);
    }

    @Override
    public List<PassDaasIaasApplicationExport> pdiApplicationExport(String areas, String policeCategory, Map<String, String> params) {
        return applicationInfoMapper.pdiApplicationExport(areas, policeCategory, params);
    }

    @Override
    public List<SaasApplicationExport> saasApplicationExport(String areas, String policeCategory, Map<String, String> params) {
        return applicationInfoMapper.saasApplicationExport(areas, policeCategory, params);
    }

    @Override
    public List<ServicePublishApplicationRegistExport> servicePublishRegistExport(String areas, String policeCategory, Map<String, String> params) {
        return applicationInfoMapper.servicePublishRegistExport(areas, policeCategory, params);
    }

    @Override
    public List<IaasZYSBapplicationExport> iaasZysbAppExport(String areas, String policeCategory, Map<String, String> params) {
        return applicationInfoMapper.iaasZysbAppExport(areas, policeCategory, params);
    }

    @Override
    public List<IPDVo> getIPDApplicationInfoOrderList(Map<String, Object> params) {
        List<IPDVo> list = applicationInfoMapper.getIPDApplicationInfoOrderList(params);
        List<IPDVo> result = new ArrayList<>();
        int count = 1;
        for (IPDVo ipdVo : list) {
            String id = ipdVo.getId();
            String node = applicationInfoMapper.getCurrentNodeById(id);
            if (node != null) {
                ipdVo.setNode(node);
                //服务台复核
                ReviewInfoVo reviewInfoVo1 = appReviewInfoService.getReviewInfoVoByAppInfoId("服务台复核", id);
                if (reviewInfoVo1 != null) {
                    ipdVo.setRecheckPerson(reviewInfoVo1.getName());
                    ipdVo.setRecheckTime(reviewInfoVo1.getCreateTime());
                    ipdVo.setRecheckResult(reviewInfoVo1.getResult());
                    ipdVo.setRecheckOpnion(reviewInfoVo1.getRemark());
                }
                //大数据办审批
                ReviewInfoVo reviewInfoVo2 = appReviewInfoService.getReviewInfoVoByAppInfoId("大数据办审批", id);
                if (reviewInfoVo2 != null) {
                    ipdVo.setBigdataPerson(reviewInfoVo2.getName());
                    ipdVo.setBigdataTime(reviewInfoVo2.getCreateTime());
                    ipdVo.setBigdataResult(reviewInfoVo2.getResult());
                    ipdVo.setBigdataOpnion(reviewInfoVo2.getRemark());
                }
                //业务办理
                ReviewInfoVo reviewInfoVo3 = appReviewInfoService.getReviewInfoVoByAppInfoId("业务办理", id);
                if (reviewInfoVo3 != null) {
                    ipdVo.setCarrayPerson(reviewInfoVo3.getName());
                    ipdVo.setCarrayTime(reviewInfoVo3.getCreateTime());
                    ipdVo.setCarrayResult(reviewInfoVo3.getResult());
                }
                ipdVo.setNum(count);
                result.add(ipdVo);
                count++;
            }
        }
        return result;
    }

    @Override
    public List<IaasZYSBapplicationExport> getIaaSZysbList(Map<String, Object> param) throws InvocationTargetException, IllegalAccessException {
        List<IaasZysb> list = iaasZysbMapper.getListExport(param);
        List<IaasZYSBapplicationExport> result = new ArrayList<>();
        int count = 1;
        for (IaasZysb iaasZysb : list) {
            String id = iaasZysb.getId();
            IaasZYSBapplicationExport vo = new IaasZYSBapplicationExport();
            BeanUtils.copyProperties(vo, iaasZysb);
            List<IaasZysbXmxx> projectList = iaasZysbXmxxService.list(new QueryWrapper<IaasZysbXmxx>().lambda().eq(IaasZysbXmxx::getMasterId, id));
            for (int i = 1; i <= projectList.size(); i++) {
                IaasZysbXmxx project = projectList.get(i - 1);
                if (i == 1) {
                    vo.setPro1(project.getProjectName());
                } else if (i == 2) {
                    vo.setPro2(project.getProjectName());
                } else if (i == 3) {
                    vo.setPro3(project.getProjectName());
                } else if (i == 4) {
                    vo.setPro4(project.getProjectName());
                } else if (i == 5) {
                    vo.setPro5(project.getProjectName());
                } else if (i == 6) {
                    vo.setPro6(project.getProjectName());
                }
            }
            //当前处理状态
            String node = applicationInfoMapper.getCurrentNodeById(id);
            if (node != null) {
                vo.setStepName(node);
            }
            if(StringUtils.equals(iaasZysb.getStatus(),ApplicationInfoStatus.USE.getCode())){
                vo.setStepName("使用中");
            }
            //服务台复核
            ReviewInfoVo reviewInfoVo = appReviewInfoService.getReviewInfoVoByAppInfoId("服务台复核", id);
            if (reviewInfoVo != null) {
                vo.setRecheckPerson(reviewInfoVo.getName());
                vo.setRecheckTime(reviewInfoVo.getCreateTime());
                vo.setRecheckResult(reviewInfoVo.getResult());
                vo.setRecheckOpnion(reviewInfoVo.getRemark());
            }
            vo.setId(count);
            count++;
            result.add(vo);
        }
        return result;
    }

    @Override
    public List<ServicePublishApplicationRegistExport> getAppRegistList(Map<String, Object> param) throws InvocationTargetException, IllegalAccessException {
        List<SaasRegister> list = registerMapper.getListExport(param);
        List<ServicePublishApplicationRegistExport> result = new ArrayList<>();
        int count = 1;
        for (SaasRegister register : list) {
            String id = register.getId();
            ServicePublishApplicationRegistExport vo = new ServicePublishApplicationRegistExport();
            BeanUtils.copyProperties(vo, register);
            if (register.getUser() != null) {
                vo.setOrgName(register.getUser().getOrgName());
            }
            vo.setAppName(register.getName());
            String type = register.getApplyType();
            if (type.equals("2")) {
                vo.setIsgov("是");
            } else {
                vo.setIsgov("否");
            }
            //当前处理状态
            String node = applicationInfoMapper.getCurrentNodeById(id);
            if (node != null) {
                vo.setStepName(node);
            }
            if(StringUtils.equals(register.getStatus(),ApplicationInfoStatus.USE.getCode())){
                vo.setStepName("使用中");
            }
            //服务台复核
            ReviewInfoVo reviewInfoVo = appReviewInfoService.getReviewInfoVoByAppInfoId("服务台复核", id);
            if (reviewInfoVo != null) {
                vo.setRecheckPerson(reviewInfoVo.getName());
                vo.setRecheckTime(reviewInfoVo.getCreateTime());
                vo.setRecheckResult(reviewInfoVo.getResult());
                vo.setRecheckOpnion(reviewInfoVo.getRemark());
            }
            vo.setId(count);
            count++;
            result.add(vo);
        }
        return result;
    }

    /**
     * 疫情-iaas-支撑信息
     *
     * @param startTime
     * @return
     */
    @Override
    public Map<String, Long> getIaasSupportNcovInfo(String startTime) {

        //疫情期间使用中的虚拟机订单
        List<ApplicationInfo> ncovUsedEcsList = applicationInfoMapper.getNcovUsedEcsList(startTime);
        //警种数
        List<String> policeCategoryList = ncovUsedEcsList.stream().filter(item -> (StringUtils.equals("省厅", item.getAreaName()) || item.getAreaName() == null) && item.getPoliceCategory() != null)
                .map(ApplicationInfo::getPoliceCategory)
                .collect(Collectors.toList());

        long policeCategoryNum = policeCategoryList.stream().distinct().count();
        //地市数
        List<String> areaNameList = ncovUsedEcsList.stream().filter(item -> !StringUtils.equals("省厅", item.getAreaName()) && item.getAreaName() != null)
                .map(ApplicationInfo::getAreaName)
                .collect(Collectors.toList());

        long areaNum = areaNameList.stream().distinct().count();

        //应用数
        List<String> appNameList = ncovUsedEcsList.stream().filter(item -> item.getAppName() != null)
                .map(ApplicationInfo::getAppName)
                .collect(Collectors.toList());


        long appNum = appNameList.stream().distinct().count();

        Map<String, Long> res = Maps.newHashMapWithExpectedSize(7);
        res.put("policeCategoryNum", policeCategoryNum);
        res.put("areaNum", areaNum);
        res.put("appNum", appNum);
        return res;
    }


    public List<BusinessTopDto> getEcsForNcovByAppNames(String appNames) {
        String url = iMocPreURL + "/imocSearch/businessTopForNcov?appNames=" + appNames;
        String data = requestList(url);
        List<BusinessTopDto> businessTopDtos = Lists.newArrayList();
        if (data != null) {
            JSONArray jsonArray = JSONArray.parseArray(data);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject dataObject = jsonArray.getJSONObject(i);
                BusinessTopDto businessTopDto = JSONObject.toJavaObject(dataObject, BusinessTopDto.class);
                businessTopDtos.add(businessTopDto);
            }
        }
        return businessTopDtos;
    }

    private String requestList(String url) {
        try {
            String data = OkHttpUtils.get(url, null).body().string();

            return data;
        } catch (Exception e) {
            logger.error("获取失败 " + url, e);
            throw new BaseException("请求定时器BusinessTop列表接口失败");
        }
    }

    @Override
    public List<NcovIaasVo> getIaasNcovList(String startTime) {
        List<NcovIaasVo> ncovIaasVoList = applicationInfoMapper.getNcovIaasVoList(startTime);

        //存储GB转TB
        ncovIaasVoList.forEach(f -> f.setStorageNum(BigDecimalUtil.div(f.getStorageNum(), 1024).doubleValue()));

        List<String> appNameList = ncovIaasVoList.stream().map(NcovIaasVo::getAppName).distinct().collect(Collectors.toList());

        String appNames = Joiner.on(",").skipNulls().join(appNameList);
        try {
            List<BusinessTopDto> businessTopDtoList = getEcsForNcovByAppNames(appNames);
            businessTopDtoList.forEach(businessTopDto -> {

                ncovIaasVoList.forEach(ncovIaasVo -> {
                    if (StringUtils.equals(businessTopDto.getName(), ncovIaasVo.getAppName())) {
                        try {
                            BeanUtils.copyProperties(ncovIaasVo, businessTopDto);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ncovIaasVoList;
    }

    /**
     * 获取处领导视图待办事项
     * @param idCard
     * @return
     */
    @Override
    public Map<String, Integer> getLeaderTodoView(String idCard) {
        Map<String,Integer> res = Maps.newHashMapWithExpectedSize(7);
        //本人待办
        res.put("personalTodo",personalTodo(idCard));
        //本人已办
        res.put("personalDone",personalDone(idCard));
        //全处待办
        res.put("AllTodo",allTodo());
        return res;
    }

    /**
     * 本人待办（）
     * @param idCard
     * @return
     */
    private int  personalTodo(String idCard){

        //IPDS
        int ipds = applicationInfoMapper.getServiceTodo(idCard);
        //应用注册

        int register = registerMapper.getRegisterTodo(idCard);
        //服务发布
        int publish = servicePublishService.getPublishTodo(idCard);

        //应用申请
        int application = saasApplicationService.getApplicationTodo(idCard);

        //权限回收
        int applicationRecover = saasRecoverInfoService.getApplicationRecoverTodo(idCard);

        //资源上报
        int resourceReport = iaasZysbMapper.getResourceReportTodo(idCard);

        int total = ipds + register + publish + application + applicationRecover + resourceReport;
        return  total;
   }

    /**
     * 本人已办
     * @param idCard
     * @return
     */
    private int personalDone(String idCard){
        return activityMapper.getKxDoneByIdCard(idCard);
    }

    /**
     * 全处待办
     * @return
     */
    private Integer allTodo(){
        //IPDS
        int ipds = applicationInfoMapper.getServiceTodo(null);
        //应用注册

        int register = registerMapper.getRegisterTodo(null);
        //服务发布
        int publish = servicePublishService.getPublishTodo(null);

        //应用申请
        int application = saasApplicationService.getApplicationTodo(null);

        //权限回收
        int applicationRecover = saasRecoverInfoService.getApplicationRecoverTodo(null);

        //资源上报
        int resourceReport = iaasZysbMapper.getResourceReportTodo(null);

        int total = ipds + register + publish + application + applicationRecover + resourceReport;
        return total;
    }
}
