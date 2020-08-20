package com.hirisun.cloud.order.service.shopping.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hirisun.cloud.common.contains.ResourceType;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.continer.FormNum;
import com.hirisun.cloud.order.continer.HandlerWrapper;
import com.hirisun.cloud.order.continer.IApplicationHandler;
import com.hirisun.cloud.order.continer.ShoppingCartStatus;
import com.hirisun.cloud.order.mapper.shopping.ShoppingCartMapper;
import com.hirisun.cloud.order.service.daas.IDaasApplicationService;
import com.hirisun.cloud.order.service.shopping.IShoppingCartService;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yyc
 * @since 2020-04-16
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, 
	ShoppingCart> implements IShoppingCartService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);


    @Autowired
    private ApplicationContext context;


    @Autowired
    private IApplicationInfoService applicationInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IFilesService filesService;

    @Autowired
    private IDaasApplicationService daasApplicationService;

    @Autowired
    private ISaasServiceApplicationService saasServiceApplicationService;

    @Autowired
    private IWorkflowService workflowService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IInstanceService instanceService;

    @Autowired
    private IWorkflowmodelService workflowmodelService;

    @Autowired
    private MessageProvider messageProvider;


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void create(UserVO user,String json,ShoppingCart origin) {
        logger.debug("create json -> {}",json);
        HandlerWrapper hw = FormNum.getHandlerWrapperByName(context,origin.getFormNum());
        ShoppingCart shoppingCart = parseShoppingCart(json,hw.getApplicationType());
        logger.debug("parseShoppingCart -> {}",shoppingCart);

        shoppingCart.setResourceType(hw.getFormNum().getResourceType().getCode());
        shoppingCart.setFormNum(hw.getFormNum().name());
        shoppingCart.setStatus(ShoppingCartStatus.WAIT_SUBMIT.getCode());
        this.save(shoppingCart);
        applicationInfoService.addToShoppingCart(user, shoppingCart, hw.getHandler());
    }

    private static <S> ShoppingCart<S> parseShoppingCart(String json, Class type) {
        return JSON.parseObject(json,
                new TypeReference<ShoppingCart<S>>(type) {});
    }

    @Override
    public List<ShoppingCart> getShoppingCartList(String idCard,Long resourceType,String name) {
        List<ShoppingCart> shoppingCartList = Lists.newArrayList();
        if(StringUtils.isNotBlank(name)){
            if(ResourceType.SAAS_SERVICE.getCode().equals(resourceType) ||  ResourceType.DAAS.getCode().equals(resourceType)){
                shoppingCartList = this.list(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getCreatorIdCard,idCard)
                        .eq(ShoppingCart::getResourceType,resourceType)
                        .like(ShoppingCart::getDsName,name).orderByDesc(ShoppingCart::getModifiedTime));
            }else {
                shoppingCartList = this.list(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getCreatorIdCard,idCard)
                        .eq(ShoppingCart::getResourceType,resourceType)
                        .like(ShoppingCart::getServiceTypeName,name).orderByDesc(ShoppingCart::getModifiedTime));
            }
        }else {
            shoppingCartList = this.list(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getCreatorIdCard,idCard)
                    .eq(ShoppingCart::getResourceType,resourceType).orderByDesc(ShoppingCart::getModifiedTime));
        }
        shoppingCartList.forEach(shoppingCart -> {
            HandlerWrapper hw = FormNum.getHandlerWrapperByName(context,shoppingCart.getFormNum());
            IApplicationHandler handler = hw.getHandler();
            if(handler != null){
                shoppingCart.setServerList(handler.getByShoppingCartId(shoppingCart.getId()));
                shoppingCart.setTotalNum(handler.getTotalNumInShoppingCart(shoppingCart.getId()));
            }else {
                shoppingCart.setTotalNum(1);
            }
        });
        return shoppingCartList;
    }

    @Override
    public ShoppingCart detail(String id) {
        ShoppingCart shoppingCart = this.getById(id);
        if(shoppingCart != null){
            HandlerWrapper hw = FormNum.getHandlerWrapperByName(context,shoppingCart.getFormNum());
            IApplicationHandler handler = hw.getHandler();
            if(handler != null){
                shoppingCart.setServerList(handler.getByShoppingCartId(shoppingCart.getId()));
            }
            List<Files> filesList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, id));
            shoppingCart.setFileList(filesList);
        }
        return shoppingCart;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public <S> void update(String json) {
        ShoppingCart origin = JSONObject.parseObject(json,ShoppingCart.class);
        logger.debug("origin -> {}",origin);
        HandlerWrapper hw = FormNum.getHandlerWrapperByName(context,origin.getFormNum());
        IApplicationHandler handler = hw.getHandler();
        ShoppingCart<S> shoppingCart = parseShoppingCart(json,hw.getApplicationType());
        logger.debug("shoppingCart -> {}",shoppingCart);
        //更新后草稿变待提交
        shoppingCart.setStatus(ShoppingCartStatus.WAIT_SUBMIT.getCode());
        shoppingCart.updateById();


        if(CollectionUtils.isNotEmpty(shoppingCart.getFileList())){
            refFiles(shoppingCart.getFileList(),shoppingCart.getId());
        }
        if(handler != null){
            handler.updateShoppingCart(shoppingCart);
        }
    }


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void delete(String ids) {
        if(StringUtils.isNotBlank(ids)){
            List<String> idList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(ids);
            if(CollectionUtils.isNotEmpty(idList)){
                idList.forEach(id ->{
                    ShoppingCart cart = this.getById(id);
                    if(cart != null){
                        HandlerWrapper hw = FormNum.getHandlerWrapperByName(context,cart.getFormNum());
                        IApplicationHandler handler = hw.getHandler();
                        if(handler != null){
                            handler.deleteByShoppingCart(id);
                        }
                    }
                });
                this.removeByIds(idList);
            }
        }

    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void submit(User user,SubmitRequest submitRequest) {


        logger.debug("submitRequest -> {}",submitRequest);
        //订单公共基本信息
        ApplicationInfo baseInfo = submitRequest.convertToApplicationInfo();
        logger.debug("baseInfo -> {}",baseInfo);
        List<ShoppingCart> shoppingCartItems;
        //获取提交购物车集合
        if(StringUtils.equals("all",submitRequest.getShoppingCartIds())){
            shoppingCartItems = this.list(new QueryWrapper<ShoppingCart>().lambda()
                                            .eq(ShoppingCart::getCreatorIdCard,user.getIdcard())
                                            .eq(ShoppingCart::getStatus,ShoppingCartStatus.WAIT_SUBMIT.getCode()));
        }else {
            shoppingCartItems = getShoppingCartItems(user.getIdcard(),submitRequest.getShoppingCartIds());
        }
        if(StringUtils.isBlank(submitRequest.getShoppingCartIds())){
            throw  new BaseException("请选择需提交的项");
        }
        List<String> idList;
        if(StringUtils.equals("all",submitRequest.getShoppingCartIds())){
            idList = shoppingCartItems.stream().map(ShoppingCart::getId).distinct().collect(Collectors.toList());
        }else {
            idList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(submitRequest.getShoppingCartIds());
        }



        List<ApplicationInfo> infoList = Lists.newArrayList();

        //处理需要合并的DaaS 和SaaS服务订单
        merge(user,shoppingCartItems,baseInfo,infoList);

        logger.debug("after merge DS -> {}",infoList.size());
        logger.debug("after merge baseInfo -> {}",baseInfo);
        int dsNum = infoList.size();

        List<ShoppingCart> shoppingCartListWithoutDS = shoppingCartItems.stream().filter(item->
               ! StringUtils.equals(FormNum.SAAS_SERVICE.toString(),item.getFormNum()) && !StringUtils.equals(FormNum.DAAS.toString(),item.getFormNum())).distinct().collect(Collectors.toList());

        List<String> withoutDsIdList = shoppingCartListWithoutDS.stream().map(ShoppingCart::getId).distinct().collect(Collectors.toList());


        logger.debug("shoppingCartListWithoutDS -> {}",shoppingCartListWithoutDS.size());
        int withoutDsNum = shoppingCartListWithoutDS.size();

        //对非DS先将文件关联到每个购物车
        List<Files> fileList  = submitRequest.getFileList();
        if(CollectionUtils.isNotEmpty(fileList)){
            List<Files> splitFileList = Lists.newArrayList();
            for(String id:withoutDsIdList){
                for(Files f:fileList){
                    Files nf =  new Files();
                    BeanUtils.copyProperties(f,nf);
                    nf.setId(null);
                    nf.setRefId(id);
                    logger.debug("File nf -> {}",nf);
                    splitFileList.add(nf);
                }
            }
            filesService.saveBatch(splitFileList);
            logger.debug("id size -> {} fileList size ->{} splitFileList -> {}",withoutDsIdList.size(),fileList.size(),splitFileList.size());
            //删除原记录
            List<String> originFileId = fileList.stream().map(Files::getId).distinct().collect(Collectors.toList());
            filesService.removeByIds(originFileId);
        }
        //IaaS 和 PaaS购物车 分单（遍历IaaS PaaS购物车）
        for (ShoppingCart shoppingCart:shoppingCartListWithoutDS){
            HandlerWrapper hw  = FormNum.getHandlerWrapperByName(context,shoppingCart.getFormNum());
            IApplicationHandler handler = hw.getHandler();
            //根据购物车生成订单(其中包括订单选择哪个流程，用于后面的发起流程)
            ApplicationInfo info = configAndSaveBaseInfo(user,shoppingCart,baseInfo);
            updateFiles(info.getId(),shoppingCart.getId());
            infoList.add(info);
            if(handler != null){
                //关联订单,清楚购物车Item及关联
                handler.refAppInfoFromShoppingCart(shoppingCart.getId(),info.getId());
            }
            //新生成的订单发起流程
           R r =  instanceService.launchInstanceOfWorkFlow(user.getIdcard(),info.getWorkFlowId(),info.getId());
            logger.debug("launchInstance -> {}",r);
        }

        logger.debug("after deal without DS -> {}",infoList.size());
        //提交审核

        if(infoList.size() != dsNum + withoutDsNum){
            throw new BaseException("分单处理异常");
        }
        Date now = new Date();
        //订单提交
        for(ApplicationInfo info:infoList){
            if ("1".equals(info.getDraft())) {
                throw new BaseException("草稿不能提交");
            }
            new Log(user.getIdcard(),"服务名称："+info.getServiceTypeName()+";申请单号："+info.getOrderNumber(),"提交申请", IpUtil.getIp()).insert();

            Instance instance =instanceService.getInstanceByBusinessId(info.getId());
            if (null==instance){
                throw new BaseException("流程实例未找到");
            }
            Activity firstActivity = activityService.getOne(new QueryWrapper<Activity>().eq("activitystatus","待办")
                    .eq("isstart",0).eq("instanceid",instance.getId()));
            String flowId = instance.getWorkflowid();
            Map<String, String> modelMapToPerson = new HashMap<String, String>();
            info.setCreateTime(now);
            info.setModifiedTime(now);
            Workflowmodel workflowmodel = new Workflowmodel();
            if ("kx".equals(submitRequest.getType())) {
                //科信待审核
                info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
                //查找名为服务台复核的环节
                workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",flowId).eq("modelname", ModelName.RECHECK.getName()).eq("VERSION",instance.getWorkflowversion()));

            }else {
                //部门内待审核
                info.setStatus(INNER_REVIEW.getCode());
                //环节表找该流程名为本单位审批的环节
                workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",flowId).eq("modelname", ModelName.DEP_APPROVE.getName()).eq("VERSION",instance.getWorkflowversion()));

            }
            //环节处理人map key:环节ID value:审核人id ，分割
            modelMapToPerson.put(workflowmodel.getId(),
                    submitRequest.getUserIds());
            AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
            //当前要流程的数据
            advanceBeanVO.setCurrentActivityId(firstActivity.getId());
            //流程定义环节id与处理人对应关系
            advanceBeanVO.setModelMapToPerson(modelMapToPerson);
            Map<String,String> map = new HashMap<>();
            map.put("name",info.getServiceTypeName());
            map.put("order",info.getOrderNumber());
            activityService.advanceCurrentActivity(advanceBeanVO,map);
            messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, info.getServiceTypeName(), info.getOrderNumber()));
        }
        applicationInfoService.updateBatchById(infoList);

        //删除购物车
        this.removeByIds(idList);
    }

//    /**
//     * 初次老数据对接
//     */
//    @Transactional(rollbackFor = Throwable.class)
//    @Override
//    public void oldDataMove(Long resourceType) {
//        //查询处于购物车状态的数据
//        List<ApplicationInfo> applicationInfoList = applicationInfoService.list(new QueryWrapper<ApplicationInfo>().lambda()
//                                .eq(ApplicationInfo::getStatus,ApplicationInfoStatus.SHOPPING_CART.getCode())
//                                .eq(ApplicationInfo::getResourceType,resourceType).ne(ApplicationInfo::getFormNum,FormNum.IAAS_YZMYZY.toString()));
//        logger.debug("ShoppingCarts Num -> {}",applicationInfoList.size());
//            //遍历同步购物车
//            for(ApplicationInfo info:applicationInfoList){
//                HandlerWrapper hw = FormNum.getHandlerWrapperByInfo(context, info);
//                IApplicationHandler handler = hw.getHandler();
//                if(ResourceType.IAAS.getCode().equals(resourceType) || ResourceType.PAAS.getCode().equals(resourceType)) {
//                    ShoppingCart shoppingCart = new ShoppingCart();
//                    //基本信息
//                    shoppingCart.setServiceTypeId(info.getServiceTypeId());
//                    shoppingCart.setServiceTypeName(info.getServiceTypeName());
//                    shoppingCart.setResourceType(info.getResourceType());
//                    if (StringUtils.equals("1", info.getDraft())) {
//                        shoppingCart.setStatus(ShoppingCartStatus.DRAFT.getCode());
//                    } else if (StringUtils.equals("0", info.getDraft())) {
//                        shoppingCart.setStatus(ShoppingCartStatus.WAIT_SUBMIT.getCode());
//                    }
//                    shoppingCart.setCreateTime(info.getCreateTime());
//                    shoppingCart.setModifiedTime(info.getModifiedTime());
//                    shoppingCart.setCreatorName(info.getCreatorName());
//                    shoppingCart.setCreatorIdCard(info.getCreator());
//                    shoppingCart.setFormNum(info.getFormNum());
//                    this.save(shoppingCart);
//                    //关联文件
//                    refApp2ShopFile(shoppingCart.getId(),info.getId());
//
//                    logger.debug("shoppingCart -> {}", shoppingCart.getId());
//
//                    if (handler != null) {
//                        //清空关联订单信息，关联购物车信息
//                        logger.debug("handler of -> {}", hw.getFormNum());
//                        handler.oldDataMove(shoppingCart.getId(), info.getId());
//                    }
//                }else if(ResourceType.DAAS.getCode().equals(resourceType)){
//                    daasApplicationService.specialOldDataDeal(info);
//                }else if(ResourceType.SAAS_SERVICE.equals(resourceType)){
//                    saasServiceApplicationService.specialOldDataDeal(info);
//                }
//
//                //终止流程，删除数据
//                //logger.debug("开始终止订单流程与删除订单:{}", info.getId());
//                //activityService.terminationInstanceOfWorkFlow(info.getId());
//                //applicationInfoService.removeById(info.getId());
//                //logger.debug("结束终止订单流程与删除订单:{}", info.getId());
//            }
//
//    }

    private void refApp2ShopFile(String shoppingCartId,String appInfoId){
        filesService.update(new Files(),new UpdateWrapper<Files>().lambda().eq(Files::getRefId,appInfoId).set(Files::getRefId,shoppingCartId));
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteOldShoppingCart(Long resourceType){
        //查询处于购物车状态的数据
        List<ApplicationInfo> applicationInfoList = applicationInfoService.list(new QueryWrapper<ApplicationInfo>().lambda()
                .eq(ApplicationInfo::getStatus,ApplicationInfoStatus.SHOPPING_CART.getCode())
                .eq(ApplicationInfo::getResourceType,resourceType).ne(ApplicationInfo::getFormNum,FormNum.IAAS_YZMYZY.toString()));
        logger.debug("ShoppingCarts Num -> {}",applicationInfoList.size());
        for(ApplicationInfo info:applicationInfoList){
            //终止流程，删除数据
            logger.debug("开始终止订单流程与删除订单:{}", info.getId());
            activityService.terminationInstanceOfWorkFlow(info.getId());
            applicationInfoService.removeById(info.getId());
            logger.debug("结束终止订单流程与删除订单:{}", info.getId());
        }
    }


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void distinct(){
        List<ShoppingCart> shoppingCartDaaSList = this.list(new QueryWrapper<ShoppingCart>().lambda().
                                                isNotNull(ShoppingCart::getDsId).eq(ShoppingCart::getResourceType,ResourceType.DAAS.getCode()));
        logger.debug("distinct::DaaS origin Size -> {}",shoppingCartDaaSList.size());
        List<String> daaSidCards = shoppingCartDaaSList.stream().map(ShoppingCart::getCreatorIdCard).distinct().collect(Collectors.toList());
        for(String idCard:daaSidCards){
            distinctShoppingCart(idCard,shoppingCartDaaSList);
        }

        List<ShoppingCart> shoppingCartSaaSList = this.list(new QueryWrapper<ShoppingCart>().lambda().
                isNotNull(ShoppingCart::getDsId).eq(ShoppingCart::getResourceType,ResourceType.SAAS_SERVICE.getCode()));
        logger.debug("distinct::SaaS origin Size -> {}",shoppingCartSaaSList.size());
        List<String> idCards = shoppingCartSaaSList.stream().map(ShoppingCart::getCreatorIdCard).distinct().collect(Collectors.toList());
        for(String idCard:idCards){
            distinctShoppingCart(idCard,shoppingCartSaaSList);
        }
    }


    /**
     * DaaS老数据去重
     * @param idCard
     */
    private void distinctShoppingCart(String idCard,List<ShoppingCart> sourceList){
        List<ShoppingCart> shoppingCarts = sourceList.stream().filter(e -> StringUtils.equals(e.getCreatorIdCard(),idCard)).collect(Collectors.toList());
        Map<String,Long> count = shoppingCarts.stream().map(ShoppingCart::getDsId)
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        List<String> dupDsIdList= count.entrySet().stream()
                .filter(e -> e.getValue()>1).map(Map.Entry::getKey)
                .collect(Collectors.toList());
        logger.debug("dupDsIDList -> {}",dupDsIdList);

        if(CollectionUtils.isNotEmpty(dupDsIdList)){
            for(String dsId:dupDsIdList){
                List<ShoppingCart> dup = shoppingCarts.stream().filter(e -> StringUtils.equals(e.getDsId(),dsId)).collect(Collectors.toList());
                logger.debug("dup size -> {},first Id -> {}",dup.size(),dup.get(0).getId());
                dup.remove(0);
                List<String> deleteIdList = dup.stream().map(ShoppingCart::getId).collect(Collectors.toList());
                logger.debug("deleteIdList size -> {}",deleteIdList.size());
                String deleteIds = Joiner.on(",").skipNulls().join(deleteIdList);
                logger.debug("deleteIds -> {}",deleteIds);
                this.delete(deleteIds);
            }
        }

    }

    /**
     * 各类资源购物车数目
     *
     * @return map
     */
    @Override
    public Map<String, Integer> getNumGroupByType(String idCard) {
        int iaas = this.count(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getResourceType,ResourceType.IAAS.getCode()).eq(ShoppingCart::getCreatorIdCard,idCard));

        int paas = this.count(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getResourceType,ResourceType.PAAS.getCode()).eq(ShoppingCart::getCreatorIdCard,idCard));
        int saas = this.count(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getResourceType,ResourceType.SAAS_SERVICE.getCode()).eq(ShoppingCart::getCreatorIdCard,idCard));

        int daas = this.count(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getResourceType,ResourceType.DAAS.getCode()).eq(ShoppingCart::getCreatorIdCard,idCard));

        Map<String,Integer> result = Maps.newHashMapWithExpectedSize(7);
        result.put("iaas",iaas);
        result.put("paas",paas);
        result.put("daas",daas);
        result.put("saas",saas);

        return result;
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
            throw new BaseException("请选择需提交购物车");
        }
        List<String> idList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(ids);

       return this.list(new QueryWrapper<ShoppingCart>().lambda()
                                                    .eq(ShoppingCart::getCreatorIdCard,idCard)
                                                    .eq(ShoppingCart::getStatus,ShoppingCartStatus.WAIT_SUBMIT.getCode())
                                                    .in(ShoppingCart::getId,idList));


    }


    /**
     * 处理需要合并的购物车资源(DaaS ,SaaS)
     * @param allItems 全部购物车
     */
    private void merge(User user,List<ShoppingCart> allItems,ApplicationInfo baseInfo,List<ApplicationInfo> infoList){

        //获取所有DaaS购物车
        List<ShoppingCart> daaSItems = getDaaSItems(allItems);
        if(CollectionUtils.isNotEmpty(daaSItems)){
            ApplicationInfo info = dealMerge(user,daaSItems,baseInfo);
            infoList.add(info);
            instanceService.launchInstanceOfWorkFlow(user.getIdcard(),info.getWorkFlowId(),info.getId());
        }
        List<ShoppingCart> saaSItems = getSaaSServiceItems(allItems);
        if(CollectionUtils.isNotEmpty(saaSItems)){
            ApplicationInfo info = dealMerge(user,saaSItems,baseInfo);
            infoList.add(info);
            instanceService.launchInstanceOfWorkFlow(user.getIdcard(),info.getWorkFlowId(),info.getId());
        }
    }

    /**
     * 处理合并
     * @param shoppingCartList DAAS或SAAS购物车集合
     * @param baseInfo 订单基本信息
     */
    private ApplicationInfo dealMerge(User user,List<ShoppingCart> shoppingCartList,ApplicationInfo baseInfo){
        if(CollectionUtils.isNotEmpty(shoppingCartList)){
            //取第一个DaaS/SaaS购物车
            ShoppingCart shoppingCart = shoppingCartList.get(0);
            //以第一个DaaS/SaaS购物车创建订单
            ApplicationInfo info = configAndSaveBaseInfo(user,shoppingCart,baseInfo);
            logger.debug("DS info -> {}",info);
            //applicationInfoService.save(info);
            //获取DaaS/SaaS购物车的所有ID
            List<String> idList = shoppingCartList.stream().map(ShoppingCart::getId).distinct().collect(Collectors.toList());
            //处理服务关联关系
            dealRef(info,idList);
            return info;
        }
        return null;
    }

    /**
     * 配置和保存订单
     * @param shoppingCart
     * @param baseInfo
     */
    private ApplicationInfo configAndSaveBaseInfo(User user,ShoppingCart shoppingCart,ApplicationInfo baseInfo){
        HandlerWrapper hw = FormNum.getHandlerWrapperByName(context,shoppingCart.getFormNum());
        ApplicationInfo info = new ApplicationInfo();
        BeanUtils.copyProperties(baseInfo,info);
        info.setId(UUIDUtil.getUUID());
        logger.debug("ID -> {}",info.getId());
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        info.setStatus(ApplicationInfoStatus.SHOPPING_CART.getCode());
        //iaas和paas申请从购物车中取申请说明，daas和saas从申请单中取申请说明
        //全部提交时，如果有daas或saas，申请说明会有两个字段，此处将iaas、paas和daas用explanation，saas用explanationSaas
        if (shoppingCart.getResourceType() == 1 || shoppingCart.getResourceType() == 3) {
            info.setExplanation(shoppingCart.getExplanation());
        } else if (shoppingCart.getResourceType() == 2){
            info.setExplanation(baseInfo.getExplanation());
        } else {
            info.setExplanation(baseInfo.getExplanationSaas());
        }
        info.setFlowStepId(null);
        info.setFlowStepIdBak(null);
        info.setServiceTypeId(shoppingCart.getServiceTypeId());
        info.setServiceTypeName(shoppingCart.getServiceTypeName());
        ResourceType resourceType = hw.getFormNum().getResourceType();
        info.setFlowNew("1");
        //流程选择（重要选择流程逻辑）
        Workflow workflow = workflowService.chooseWorkFlow(resourceType,info.getAreaName(),info.getPoliceCategory(),info.getServiceTypeId(),info.getNationalSpecialProject());
        if(workflow == null){
            logger.error("购物车ID:{} 资源类型:{} 地市:{} 警种: {} 服务ID:{} 国家专项:{}",shoppingCart.getId(),resourceType.toString(),info.getAreaName(),info.getPoliceCategory(),info.getServiceTypeId(),info.getNationalSpecialProject());
            throw  new BaseException("资源类型: "+resourceType.toString()+ "地市: "+ info.getAreaName()+"警种: "+info.getPoliceCategory()+ "服务ID: "+info.getServiceTypeId()+"国家专项: "+info.getNationalSpecialProject()+"无匹配流程");
        }
        info.setWorkFlowId(workflow.getId());
        info.setResourceType(resourceType.getCode());
        info.setFormNum(shoppingCart.getFormNum());
        info.setOrderNumber(genOrderNum());
        info.setHwPoliceCategory(AreaPoliceCategoryUtils.getPoliceCategory(baseInfo.getAppName()));
        applicationInfoService.save(info);
        return info;
    }

    /**
     * 订单关联信息处理
     * @param info
     * @param shoppingCartIdList
     */
    private void dealRef(ApplicationInfo info,List<String> shoppingCartIdList){
        //文件
        refFiles(info.getFileList(), info.getId());
        if(StringUtils.equals(FormNum.DAAS.toString(),info.getFormNum())){
            daasApplicationService.submitMerge(info.getId(),shoppingCartIdList);
        }else if(StringUtils.equals(FormNum.SAAS_SERVICE.toString(),info.getFormNum())){
            saasServiceApplicationService.submitMerge(info.getId(),shoppingCartIdList);
        }
    }


    /**
     * 更新文件关联关系
     * @param appInfoId
     * @param shoppingCartId
     */
    private  void updateFiles(String appInfoId,String shoppingCartId){
        filesService.update(new Files(),new UpdateWrapper<Files>().lambda().eq(Files::getRefId,shoppingCartId).set(Files::getRefId,appInfoId));
    }

    private void refFiles(List<Files> files, String refId) {
        if (org.apache.commons.lang.StringUtils.isEmpty(refId)) {
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

    /**
     * 获取DaaS服务购物车
     * @param allItems 全部购物车
     * @return DaaS服务购物车
     */
    private List<ShoppingCart> getDaaSItems(List<ShoppingCart> allItems){

        List<ShoppingCart> daaSItems = allItems.stream().filter(item -> StringUtils.equals(FormNum.DAAS.toString(),item.getFormNum())).collect(Collectors.toList());
        return daaSItems;
    }


    /**
     * 获取SaaS服务购物车
     * @param allItems 全部购物车
     * @return SaaS服务购物车
     */
    private List<ShoppingCart> getSaaSServiceItems(List<ShoppingCart> allItems){

        List<ShoppingCart> saaSServiceItems = allItems.stream().filter(item -> StringUtils.equals(FormNum.SAAS_SERVICE.toString(),item.getFormNum())).collect(Collectors.toList());
        return saaSServiceItems;
    }



}
