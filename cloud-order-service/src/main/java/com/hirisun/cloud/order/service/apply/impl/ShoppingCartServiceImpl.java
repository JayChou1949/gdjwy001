package com.hirisun.cloud.order.service.apply.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.hirisun.cloud.common.contains.WorkflowActivityStatus;
import com.hirisun.cloud.order.continer.ShoppingCartStatus;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.constant.FormCode;
import com.hirisun.cloud.common.constant.RedisKey;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.contains.ResourceType;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.AreaPoliceCategoryUtils;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.AdvanceBeanVO;
import com.hirisun.cloud.model.workflow.WorkflowActivityVO;
import com.hirisun.cloud.model.workflow.WorkflowInstanceVO;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.model.workflow.WorkflowVO;
import com.hirisun.cloud.order.bean.apply.ApplyInfo;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.mapper.shopping.ShoppingCartMapper;
import com.hirisun.cloud.order.service.apply.ApplyInfoService;
import com.hirisun.cloud.order.service.apply.ShoppingCartService;
import com.hirisun.cloud.order.util.SubmitRequest;
import com.hirisun.cloud.order.vo.OrderCode;
import com.hirisun.cloud.redis.service.RedisService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-08
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    @Autowired
    private WorkflowApi workflowApi;


    @Autowired
    private ApplyInfoService applyInfoService;

    @Autowired
    private RedisService redisService;

    /**
     * 加入购物车
     * @param user
     * @param shoppingCart
     */
    @Override
    public void createCart(UserVO user, ShoppingCart shoppingCart) {
        logger.debug("create shoppingCart -> {}",shoppingCart);

//        shoppingCart.setResourceType(Long.valueOf(FormCode.serviceResourceTypeValueMap.get(shoppingCart.getFormNum())));
//        shoppingCart.setStatus(ShoppingCart.STATUS_WAIT_SUBMIT);

        this.save(shoppingCart);
//        applicationInfoService.addToShoppingCart(user, shoppingCart, hw.getHandler());
    }

    /**
     * 提交购物车  事务提交
     * @param user
     * @param submitRequest
     */
    @Transactional
    @Override
    public void submit(UserVO user, SubmitRequest submitRequest) {
        /**
         * 1.提交对象组装成工单申请对象
         * 2.查询出购物车列表
         * 3.每个购物车申请生成流程实例
         * 4.每个流程实例的申请环节自动提交
         *
         */
        logger.info("submitRequest -> {}",submitRequest);
        ApplyInfo baseInfo = submitRequest.convertToApplicationInfo();
        logger.info("baseInfo -> {}",baseInfo);
        List<ApplyInfo> infoList = Lists.newArrayList();
        List<ShoppingCart> shoppingCartItems = getShoppingCartItems(user.getIdcard(),submitRequest.getShoppingCartIds());
        if(CollectionUtils.isEmpty(shoppingCartItems)) {
            logger.info("购物车不能为空!");
            throw new CustomException(OrderCode.CART_CAN_NOT_NULL);
        }
        List<String> idList=null;//删除购物车用
        if(StringUtils.equals("all",submitRequest.getShoppingCartIds())){
            idList = shoppingCartItems.stream().map(ShoppingCart::getId).distinct().collect(Collectors.toList());
        }else {
            idList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(submitRequest.getShoppingCartIds());
        }
        for (ShoppingCart shoppingCart : shoppingCartItems) {
            //根据购物车生成订单(其中包括订单选择哪个流程，用于后面的发起流程)
            ApplyInfo info = configAndSaveBaseInfo(user,shoppingCart,baseInfo);

            workflowApi.launchInstanceOfWorkflow(user.getIdcard(),info.getWorkFlowId(),info.getId());
            infoList.add(info);
        }
        for(ApplyInfo info:infoList){
            if ("1".equals(info.getDraft())) {
                logger.info("草稿不能提交!");
                throw new CustomException(OrderCode.DRAFT_CAN_NOT_COMMIT);
            }
            WorkflowInstanceVO instance = workflowApi.getWorkflowInstanceByBusinessId(info.getId());
            if (instance==null) {
                logger.info("流程实例未找到");
                throw new CustomException(OrderCode.WORKFLOW_INSTANSE_MISSING);
            }
            WorkflowActivityVO firstActivity = workflowApi.getOneWorkflowActivityByParams(WorkflowActivityStatus.WAITING.getCode(),instance.getId());
            if (firstActivity==null) {
                logger.info("未找到对应的流程活动信息！");
                throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
            }
            String flowId = instance.getWorkflowId();
            Date now = new Date();
            Map<String, String> modelMapToPerson = new HashMap<String, String>();
            info.setCreateTime(now);
            info.setModifiedTime(now);

            //查找第二个,获取处理人
            List<WorkflowNodeVO> nextNodeList= workflowApi.getWorkflowNodeByParams(instance.getVersion(),flowId,2);
            if (CollectionUtils.isEmpty(nextNodeList)) {
                logger.info("未找到对应的流程环节信息！");
                throw new CustomException(OrderCode.WORKFLOW_MISSING);
            }
            WorkflowNodeVO nextNode=nextNodeList.get(0);
            //环节处理人map key:环节ID value:审核人id ，分割
            modelMapToPerson.put(nextNode.getId(),
                    submitRequest.getUserIds());
            AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
            //当前要流程的数据
            advanceBeanVO.setCurrentActivityId(firstActivity.getId());
            //流程定义环节id与处理人对应关系
            advanceBeanVO.setModelMapToPerson(modelMapToPerson);
            Map<String,String> map = new HashMap<>();
            map.put("name",info.getServiceTypeName());
            map.put("order",info.getOrderNumber());
            Map resultMap = workflowApi.advanceActivity(firstActivity.getId(),map);
            if (!"200".equals(resultMap.get("code"))) {
                logger.error("advanceCurrentActivity调用失败:{}",resultMap.get("msg"));
                throw new CustomException(OrderCode.FEIGN_METHOD_ERROR);
            }
        }
//        this.removeByIds(idList);

    }

    /**
     * 地市提交流程申请
     * @param submitRequest
     */
    @Override
    public void workflowSubmitByArea(SubmitRequest submitRequest,String ids,String area) {
        logger.info("submitRequest -> {}",submitRequest);
        ApplyInfo baseInfo = submitRequest.convertToApplicationInfo();
        logger.info("baseInfo -> {}",baseInfo);
        //根据购物车生成订单(其中包括订单选择哪个流程，用于后面的发起流程)
        ApplyInfo info = configAndSaveBaseInfoByArea(baseInfo);

        String flag=workflowApi.launchInstanceByArea(baseInfo.getCreatorName(),info.getId(),"IAAS");
        logger.info("launchInstanceByArea:{}",flag);

    }

    /**
     * 获取当前用户选择Item中，待提交状态的Item。
     * @param idCard 当前用户身份证号
     * @param ids 购物车Items的Id字符串
     * @return 购物车Item实体List
     */
    private List<ShoppingCart> getShoppingCartItems(String idCard,String ids){
        if(StringUtils.isBlank(ids)){
            logger.debug("shoppingCartIds -> {}",ids);
            throw new CustomException(OrderCode.CART_CAN_NOT_NULL);
        }
        List<String> idList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(ids);

        return this.list(new QueryWrapper<ShoppingCart>().lambda()
                .eq(ShoppingCart::getCreatorIdCard,idCard)
                .eq(ShoppingCart::getStatus, ShoppingCartStatus.WAIT_SUBMIT)
                .in(ShoppingCart::getId,idList));


    }
    /**
     * 配置和保存购物车订单
     * @param shoppingCart  购物车
     * @param baseInfo  申请工单
     */
    private ApplyInfo configAndSaveBaseInfo(UserVO user,ShoppingCart shoppingCart,ApplyInfo baseInfo){
        /**
         * 1. iaas和paas申请从购物车中取申请说明，daas和saas从申请单中取申请说明
         * 2. 根据资源类型、地市、警种、国家专项、服务是否绑定流程等信息，获取到服务对应的申请流程
         */
        ApplyInfo info = new ApplyInfo();
        BeanUtils.copyProperties(baseInfo,info);
        info.setId(UUIDUtil.getUUID());
        logger.debug("ID -> {}",info.getId());
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        info.setStatus(ApplyInfoStatus.SHOPPING_CART.getCode());
        //全部提交时，如果有daas或saas，申请说明会有两个字段，此处将iaas、paas和daas用explanation，saas用explanationSaas
        if (shoppingCart.getResourceType().equals(ResourceType.IAAS.getCode()) || shoppingCart.getResourceType().equals(ResourceType.PAAS.getCode())) {
            info.setExplanation(shoppingCart.getExplanation());
        } else if (shoppingCart.getResourceType().equals(ResourceType.DAAS.getCode())){
            info.setExplanation(baseInfo.getExplanation());
        } else {
            info.setExplanation(baseInfo.getExplanationSaas());
        }
        info.setServiceTypeId(shoppingCart.getServiceTypeId());
        info.setServiceTypeName(shoppingCart.getServiceTypeName());
        //流程选择（重要选择流程逻辑）
        WorkflowVO workflow = workflowApi.chooseWorkFlow(
                shoppingCart.getResourceType().intValue(),
                info.getServiceTypeId(),
                info.getAreaName(),
                info.getPoliceCategory(),
                info.getNationalSpecialProject());


        if(workflow==null){
            logger.error("购物车ID:{} 资源类型:{} 地市:{} 警种: {} 服务ID:{} 国家专项:{}",shoppingCart.getId(),shoppingCart.getResourceType(),info.getAreaName(),info.getPoliceCategory(),info.getServiceTypeId(),info.getNationalSpecialProject());
            throw new CustomException(OrderCode.WORKFLOW_MISSING);
        }
        info.setWorkFlowId(workflow.getId());
        info.setResourceType(shoppingCart.getResourceType().intValue());
        info.setFormNum(shoppingCart.getFormNum());
        info.setOrderNumber(genOrderNum());
        info.setHwPoliceCategory(AreaPoliceCategoryUtils.getPoliceCategory(baseInfo.getAppName()));
        applyInfoService.save(info);
        return info;
    }
    /**
     * 配置和保存购物车订单
     * @param baseInfo  申请工单
     */
    private ApplyInfo configAndSaveBaseInfoByArea(ApplyInfo baseInfo){
        /**
         * 1. iaas和paas申请从购物车中取申请说明，daas和saas从申请单中取申请说明
         * 2. 根据资源类型、地市、警种、国家专项、服务是否绑定流程等信息，获取到服务对应的申请流程
         */
        ApplyInfo info = new ApplyInfo();
        BeanUtils.copyProperties(baseInfo,info);
        info.setId(UUIDUtil.getUUID());
        logger.debug("ID -> {}",info.getId());
        info.setCreator(null);
        info.setCreatorName(null);
        info.setStatus(ApplyInfoStatus.SHOPPING_CART.getCode());
        info.setExplanation(baseInfo.getExplanation());
        info.setServiceTypeId(baseInfo.getServiceTypeId());
        info.setServiceTypeName(baseInfo.getServiceTypeName());

        info.setResourceType(baseInfo.getResourceType());
//        info.setFormNum(baseInfo.getFormNum());
        info.setOrderNumber(genOrderNum());
        info.setHwPoliceCategory(AreaPoliceCategoryUtils.getPoliceCategory(baseInfo.getAppName()));
        applyInfoService.save(info);
        return info;
    }
    private String genOrderNum() {
        // 生成单号
        String yyyyMMdd = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String redisKey = RedisKey.KEY_ORDER_NUM_PREFIX + yyyyMMdd;
        Long increment = redisService.increment(redisKey);
        if (increment == null) {
            throw new CustomException(OrderCode.CREATE_ORDER_NUMER_ERROR);
        }
        redisService.expire(redisKey, 1L, TimeUnit.DAYS);
        return String.format("%s%04d", yyyyMMdd, increment);
    }
}
