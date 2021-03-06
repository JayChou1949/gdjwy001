package com.hirisun.cloud.order.service.shopping.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.hirisun.cloud.common.contains.RequestCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hirisun.cloud.api.daas.DaasApplicationApi;
import com.hirisun.cloud.api.saas.SaasApplicationApi;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.system.SystemApi;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.constant.RedisKey;
import com.hirisun.cloud.common.contains.ApplicationInfoStatus;
import com.hirisun.cloud.common.contains.ResourceType;
import com.hirisun.cloud.common.contains.WorkflowActivityStatus;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.AreaPoliceCategoryUtils;
import com.hirisun.cloud.common.util.IpUtil;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.param.ActivityParam;
import com.hirisun.cloud.model.param.FilesParam;
import com.hirisun.cloud.model.shopping.vo.ShoppingCartVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.WorkflowActivityVO;
import com.hirisun.cloud.model.workflow.WorkflowInstanceVO;
import com.hirisun.cloud.model.workflow.WorkflowVO;
import com.hirisun.cloud.order.bean.apply.ApplyInfo;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.continer.FormNum;
import com.hirisun.cloud.order.continer.HandlerWrapper;
import com.hirisun.cloud.order.continer.ShoppingCartStatus;
import com.hirisun.cloud.order.mapper.shopping.ShoppingCartMapper;
import com.hirisun.cloud.order.service.apply.ApplyInfoService;
import com.hirisun.cloud.order.service.shopping.ShoppingCartItemService;
import com.hirisun.cloud.order.service.shopping.ShoppingCartService;
import com.hirisun.cloud.order.vo.OrderCode;
import com.hirisun.cloud.order.vo.SubmitRequest;

@Service
public class ShoppingCartNewServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> 
	implements ShoppingCartService{

	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartNewServiceImpl.class);
	
	@Autowired
	private FilesApi filesApi;
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;
//	@Autowired
//	private IApplicationInfoService applicationInfoService;
	@Autowired
	private ApplyInfoService applyInfoService;
	@Autowired
	private SaasApplicationApi saasApplicationApi;
	@Autowired
	private DaasApplicationApi daasApplicationApi;
	@Autowired
	private WorkflowApi workflowApi;
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private SystemApi sysLogApi;
	@Autowired
	private ShoppingCartItemService shoppingCartItemService;
	
	@Transactional(rollbackFor = Throwable.class)
	public void create(UserVO user, ShoppingCartVo shoppingCartVo) {
		
		String formNum = shoppingCartVo.getFormNum();
		
		HandlerWrapper hw = FormNum.getHandlerWrapperByName(formNum);
		shoppingCartVo.setResourceType(hw.getFormNum().getResourceType().getCode());
		shoppingCartVo.setFormNum(hw.getFormNum().name());
		shoppingCartVo.setStatus(ShoppingCartStatus.WAIT_SUBMIT.getCode());
		shoppingCartVo.setCreatorIdCard(user.getIdcard());
		shoppingCartVo.setCreatorName(user.getName());
		
		ShoppingCart shoppingCart = new ShoppingCart();
		BeanUtils.copyProperties(shoppingCartVo, shoppingCart);
		//???????????????
		shoppingCartMapper.insert(shoppingCart);
		
		shoppingCartVo.setId(shoppingCart.getId());
		
        logger.debug("parseShoppingCart -> {}",shoppingCart);

        //????????????????????????
        shoppingCartItemService.saveShoppingCartItem(shoppingCartVo);
        
        //????????????
        if(CollectionUtils.isNotEmpty(shoppingCart.getFileList())){
            logger.debug("ref file");
            refFiles(shoppingCart.getFileList(),shoppingCart.getId());
        }
	}

	
	
	private void refFiles(List<FilesVo> files, String refId) {
        if (StringUtils.isEmpty(refId)) {
            return;
        }
        
        SubpageParam param = new SubpageParam();
        param.setRefId(refId);
		filesApi.remove(param);
        
        if (files != null && !files.isEmpty()) {
            for (FilesVo f : files) {
                f.setId(null);
                f.setRefId(refId);
            }
            FilesParam filesParam = new FilesParam();
            param.setFiles(files);
            filesApi.saveBatch(filesParam);
        }
    }
	
	@Override
	public List<ShoppingCart> getShoppingCartList(String idCard, Long resourceType, String name) {

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
        	
        	ShoppingCartVo vo = new ShoppingCartVo(shoppingCart.getId(),shoppingCart.getFormNum());
        	List list = shoppingCartItemService.getByShoppingCartId(vo);
        	Integer totalNum = shoppingCartItemService.getTotalNumInShoppingCart(vo);
            shoppingCart.setServerList(list);
            shoppingCart.setTotalNum(totalNum);
        });
        return shoppingCartList;
    
	}

	@Override
	public ShoppingCart detail(String id) {
        ShoppingCart shoppingCart = this.getById(id);
        if(shoppingCart != null){
        	
        	ShoppingCartVo vo = new ShoppingCartVo(shoppingCart.getId(),shoppingCart.getFormNum());
        	List list = shoppingCartItemService.getByShoppingCartId(vo);
        	shoppingCart.setServerList(list);
        	
            SubpageParam param = new SubpageParam();
            param.setRefId(id);
			List<FilesVo> filesList = filesApi.find(param);
            
            shoppingCart.setFileList(filesList);
        }
        return shoppingCart;
    }

	@Transactional(rollbackFor = Throwable.class)
	public void update(ShoppingCart shoppingCart) {
        logger.debug("shoppingCart -> {}",shoppingCart);
        shoppingCart.setStatus(ShoppingCartStatus.WAIT_SUBMIT.getCode());
        shoppingCartMapper.updateById(shoppingCart);

        if(CollectionUtils.isNotEmpty(shoppingCart.getFileList())){
            refFiles(shoppingCart.getFileList(),shoppingCart.getId());
        }
        //????????????????????????
        ShoppingCartVo shoppingCartVo = new ShoppingCartVo();
        BeanUtils.copyProperties(shoppingCartVo, shoppingCartVo);
        shoppingCartItemService.updateShoppingCartItem(shoppingCartVo);
	}

	@Transactional(rollbackFor = Throwable.class)
	public void delete(String ids) {

        if(StringUtils.isNotBlank(ids)){
            List<String> idList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(ids);
            if(CollectionUtils.isNotEmpty(idList)){
                idList.forEach(id ->{
                    ShoppingCart cart = this.getById(id);
                    if(cart != null){
                    	ShoppingCartVo shoppingCartVo = new ShoppingCartVo();
                    	BeanUtils.copyProperties(cart, shoppingCartVo);
                    	shoppingCartItemService.deleteItemByShoppingCartId(shoppingCartVo );
                    }
                });
                this.removeByIds(idList);
            }
        }
	}

	@Transactional(rollbackFor = Throwable.class)
	public void submit(UserVO user, SubmitRequest submitRequest) throws Exception {
		
        logger.debug("submitRequest -> {}",submitRequest);
        //????????????????????????
        ApplyInfo baseInfo = submitRequest.convertToApplicationInfo();
        logger.debug("baseInfo -> {}",baseInfo);
        List<ShoppingCart> shoppingCartItems;
        //???????????????????????????
        if(StringUtils.equals("all",submitRequest.getShoppingCartIds())){
            shoppingCartItems = this.list(new QueryWrapper<ShoppingCart>().lambda()
                                            .eq(ShoppingCart::getCreatorIdCard,user.getIdcard())
                                            .eq(ShoppingCart::getStatus,ShoppingCartStatus.WAIT_SUBMIT.getCode()));
        }else {
            shoppingCartItems = getShoppingCartItems(user.getIdcard(),submitRequest.getShoppingCartIds());
        }
        if(StringUtils.isBlank(submitRequest.getShoppingCartIds())){
            throw new CustomException(CommonCode.ITEM_NULL);
        }
        List<String> idList;
        if(StringUtils.equals("all",submitRequest.getShoppingCartIds())){
            idList = shoppingCartItems.stream().map(ShoppingCart::getId).distinct().collect(Collectors.toList());
        }else {
            idList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(submitRequest.getShoppingCartIds());
        }

        List<ApplyInfo> infoList = Lists.newArrayList();

        //?????????????????????DaaS??????
        merge(user,shoppingCartItems,baseInfo,infoList);

        logger.debug("after merge DS -> {}",infoList.size());
        logger.debug("after merge baseInfo -> {}",baseInfo);
        int dsNum = infoList.size();

        List<ShoppingCart> shoppingCartListWithoutDS = shoppingCartItems.stream().filter(item->
               /*! StringUtils.equals(FormNum.SAAS_SERVICE.toString(),item.getFormNum()) && */!StringUtils.equals(FormNum.DAAS.toString(),item.getFormNum())).distinct().collect(Collectors.toList());

        List<String> withoutDsIdList = shoppingCartListWithoutDS.stream().map(ShoppingCart::getId).distinct().collect(Collectors.toList());


        logger.debug("shoppingCartListWithoutDS -> {}",shoppingCartListWithoutDS.size());
        int withoutDsNum = shoppingCartListWithoutDS.size();

        //??????DS????????????????????????????????????
        List<FilesVo> fileList  = submitRequest.getFileList();
        if(CollectionUtils.isNotEmpty(fileList)){
            List<FilesVo> splitFileList = Lists.newArrayList();
            for(String id:withoutDsIdList){
                for(FilesVo f:fileList){
                	FilesVo nf =  new FilesVo();
                    BeanUtils.copyProperties(f,nf);
                    nf.setId(null);
                    nf.setRefId(id);
                    logger.debug("File nf -> {}",nf);
                    splitFileList.add(nf);
                }
            }
            FilesParam param = new FilesParam();
            param.setFiles(splitFileList);
			filesApi.saveBatch(param);
            logger.debug("id size -> {} fileList size ->{} splitFileList -> {}",withoutDsIdList.size(),fileList.size(),splitFileList.size());
            //???????????????
            List<String> originFileId = fileList.stream().map(FilesVo::getId).distinct().collect(Collectors.toList());
            param.setFilesIdList(originFileId);
            filesApi.deleteBatch(param);
        }
        //IaaS ??? PaaS???SaaS????????? ???????????????IaaS PaaS SaaS????????????
        for (ShoppingCart shoppingCart:shoppingCartListWithoutDS){
        	
        	ApplyInfo info = configAndSaveBaseInfo(user,shoppingCart,baseInfo);
        	infoList.add(info);
        	updateFiles(info.getId(),shoppingCart.getId());
            ShoppingCartVo shoppingCartVo = new ShoppingCartVo();
        	BeanUtils.copyProperties(shoppingCart, shoppingCartVo);
        	shoppingCartVo.setAppInfoId(info.getId());
        	shoppingCartItemService.refAppInfoFromShoppingCart(shoppingCartVo);
            
            //??????????????????????????????
           workflowApi.launchInstanceOfWorkflow(user.getIdcard(),info.getWorkFlowId(),info.getId());
           
        }

        logger.debug("after deal without DS -> {}",infoList.size());
        //????????????

        if(infoList.size() != dsNum + withoutDsNum){
            throw new CustomException(CommonCode.ORDER_SPLIT_ERROR);
        }
        Date now = new Date();
        //????????????
        for(ApplyInfo info:infoList){
            if ("1".equals(info.getDraft())) {
            	throw new CustomException(CommonCode.DRAFT_ERROR);
            }
            sysLogApi.saveLog(user.getIdcard(),"???????????????"+info.getServiceTypeName()+";???????????????"+info.getOrderNumber(),"????????????", IpUtil.getIp());

            WorkflowInstanceVO instance = workflowApi.getWorkflowInstanceByBusinessId(info.getId());
            if (null==instance){
                throw new CustomException(CommonCode.FLOW_INSTANCE_NULL_ERROR);
            }
            
            ActivityParam param = new ActivityParam();
            param.setActivitystatus(0);
            param.setIsstart(0);
            param.setInstanceId(instance.getId());
            
            info.setCreateTime(now);
            info.setModifiedTime(now);
            if ("kx".equals(submitRequest.getType())) {
                //???????????????
                info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
            }else {
                //??????????????????
                info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
            }
            Map<String,String> map = new HashMap<>();
            map.put("name",info.getServiceTypeName());
            map.put("order",info.getOrderNumber());
            map.put("depApproveUserIds",submitRequest.getUserIds());//??????????????????????????????
            WorkflowActivityVO firstActivity = workflowApi.getOneWorkflowActivityByParams(WorkflowActivityStatus.WAITING.getCode(),instance.getId());
            if (firstActivity==null) {
                logger.info("???????????????????????????????????????");
                throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
            }
            Map resultMap = workflowApi.advanceActivity(firstActivity.getId(),map);
            if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
                throw new CustomException(OrderCode.FEIGN_METHOD_ERROR);
            }
        }
        applyInfoService.updateBatchById(infoList);
        //???????????????
        this.removeByIds(idList);
	}
	
	/**
     * ????????????????????????
     * @param appInfoId
     * @param shoppingCartId
     */
    private  void updateFiles(String appInfoId,String shoppingCartId){
        FilesParam param = new FilesParam();
        param.setRefId(shoppingCartId);
        param.setNewRefId(appInfoId);
		filesApi.updateFileRef(param );
    
    }
	
	/**
     * ?????????????????????DaaS???????????????:????????????(DaaS)???????????????(DaaS)??????????????????
     * @param allItems ???????????????
	 * @throws Exception 
     */
    private void merge(UserVO user,List<ShoppingCart> allItems,
    		ApplyInfo baseInfo,List<ApplyInfo> infoList) throws Exception{

        //??????????????????(DaaS)?????????
        List<ShoppingCart> daaSResource = getDaaSResource(allItems);
        if(CollectionUtils.isNotEmpty(daaSResource)){
        	ApplyInfo info = dealMerge(user,daaSResource,baseInfo);
            infoList.add(info);
            workflowApi.launchInstanceOfWorkflow(user.getIdcard(),info.getWorkFlowId(),info.getId());
        }
        //??????????????????(DaaS)?????????
        List<ShoppingCart> daaSService = getDaaSService(allItems);
        if(CollectionUtils.isNotEmpty(daaSService)){
            //????????????(DaaS)??????????????????????????????????????????????????????IP
            baseInfo.setClusterAccount(null);
            baseInfo.setVmIp(null);
            ApplyInfo info = dealMerge(user,daaSService,baseInfo);
            infoList.add(info);
            workflowApi.launchInstanceOfWorkflow(user.getIdcard(),info.getWorkFlowId(),info.getId());
        }
    }
	
    /**
     * ??????DaaS???????????????
     * @param allItems ???????????????
     * @return DaaS???????????????
     */
    private List<ShoppingCart> getDaaSService(List<ShoppingCart> allItems){

        List<ShoppingCart> daaSService = allItems.stream().filter(item -> StringUtils.equals(FormNum.DAAS.toString(),item.getFormNum()) && StringUtils.equals("????????????(DaaS)",item.getServiceTypeName())).collect(Collectors.toList());
        return daaSService;
    }
    
    /**
     * ????????????
     * @param shoppingCartList DAAS???SAAS???????????????
     * @param baseInfo ??????????????????
     * @throws Exception 
     */
    private ApplyInfo dealMerge(UserVO user,List<ShoppingCart> shoppingCartList,
    		ApplyInfo baseInfo) throws Exception{
        if(CollectionUtils.isNotEmpty(shoppingCartList)){
            //????????????DaaS/SaaS?????????
            ShoppingCart shoppingCart = shoppingCartList.get(0);
            //????????????DaaS/SaaS?????????????????????
            ApplyInfo info = configAndSaveBaseInfo(user,shoppingCart,baseInfo);
            logger.debug("DS info -> {}",info);
            //applicationInfoService.save(info);
            //??????DaaS/SaaS??????????????????ID
            List<String> idList = shoppingCartList.stream().map(ShoppingCart::getId).distinct().collect(Collectors.toList());
            //????????????????????????
            dealRef(info,idList);
            return info;
        }
        return null;
    }
    
    /**
     * ????????????????????????
     * @param info
     * @param shoppingCartIdList
     */
    private void dealRef(ApplyInfo info,List<String> shoppingCartIdList){
        //??????
        refFiles(info.getFileList(), info.getId());
        if(StringUtils.equals(FormNum.DAAS.toString(),info.getFormNum())){
        	daasApplicationApi.submitMerge(info.getId(),shoppingCartIdList);
        }else if(StringUtils.equals(FormNum.SAAS_SERVICE.toString(),info.getFormNum())){
        	saasApplicationApi.submitMerge(info.getId(),shoppingCartIdList);
        }
    }
    
    /**
     * ?????????????????????
     * @param shoppingCart
     * @param baseInfo
     * @throws Exception 
     */
    private ApplyInfo configAndSaveBaseInfo(UserVO user,
    		ShoppingCart shoppingCart,ApplyInfo baseInfo) throws Exception{
    	
        HandlerWrapper hw = FormNum.getHandlerWrapperByName(shoppingCart.getFormNum());
        ApplyInfo info = new ApplyInfo();
        BeanUtils.copyProperties(baseInfo,info);
        info.setId(UUIDUtil.getUUID());
        logger.debug("ID -> {}",info.getId());
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        info.setStatus(ApplicationInfoStatus.SHOPPING_CART.getCode());

        //iaas???paas???saas???????????????????????????????????????daas??????????????????????????????
        //1???iaas???2???daas???3???paas???5???saas
        if (shoppingCart.getResourceType() == 2) {
            info.setExplanation(baseInfo.getExplanation());
        }else {
            info.setExplanation(shoppingCart.getExplanation());
        }

//        info.setFlowStepId(null);
//        info.setFlowStepIdBak(null);
        info.setServiceTypeId(shoppingCart.getServiceTypeId());
        info.setServiceTypeName(shoppingCart.getServiceTypeName());
        ResourceType resourceType = hw.getFormNum().getResourceType();
        //??????????????????????????????????????????
        WorkflowVO workflow = workflowApi.chooseWorkFlow(resourceType.getCode(), info.getServiceTypeId(),
        		info.getAreaName(), info.getPoliceCategory(), info.getNationalSpecialProject());
        if(workflow == null){
            logger.error("?????????ID:{} ????????????:{} ??????:{} ??????: {} ??????ID:{} ????????????:{}",shoppingCart.getId(),resourceType.toString(),info.getAreaName(),info.getPoliceCategory(),info.getServiceTypeId(),info.getNationalSpecialProject());
            throw  new Exception("????????????: "+resourceType.toString()+ "??????: "+ info.getAreaName()+"??????: "
            		+info.getPoliceCategory()+ "??????ID: "+info.getServiceTypeId()+"????????????: "
            		+info.getNationalSpecialProject()+"???????????????");
        }
        info.setWorkFlowId(workflow.getId());
        info.setResourceType(resourceType.getCode());
        info.setFormNum(shoppingCart.getFormNum());
        info.setOrderNumber(genOrderNum());
        info.setHwPoliceCategory(AreaPoliceCategoryUtils.getPoliceCategory(baseInfo.getAppName()));
        applyInfoService.save(info);
        return info;
    }
    
    private String genOrderNum() {
        // ????????????
        String yyyyMMdd = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String redisKey = RedisKey.KEY_ORDER_NUM_PREFIX + yyyyMMdd;
        Long increment = stringRedisTemplate.opsForValue().increment(redisKey, 1L);
        if (increment == null) {
            throw new CustomException(CommonCode.ORDER_NUMBER_ERROR);
        }
        // ????????????1???
        stringRedisTemplate.expire(redisKey, 1L, TimeUnit.DAYS);
        return String.format("%s%04d", yyyyMMdd, increment);
    }
    
    /**
     * ??????DaaS???????????????
     * @param allItems ???????????????
     * @return DaaS???????????????
     */
    private List<ShoppingCart> getDaaSResource(List<ShoppingCart> allItems){

        List<ShoppingCart> daaSResource = allItems.stream().filter(item -> StringUtils.equals(FormNum.DAAS.toString(),item.getFormNum()) && StringUtils.equals("????????????(DaaS)",item.getServiceTypeName())).collect(Collectors.toList());
        return daaSResource;
    }

	/**
     * ????????????????????????Item????????????????????????Item???
     * @param idCard ????????????????????????
     * @param ids ?????????Items???Id?????????
     * @return ?????????Item??????List
     */
    private List<ShoppingCart> getShoppingCartItems(String idCard,String ids){
        if(StringUtils.isBlank(ids)){
            logger.debug("shoppingCartIds -> {}",ids);
            throw new CustomException(CommonCode.SHOPPINGCAR_ID_NULL);
        }
        List<String> idList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(ids);

       return this.list(new QueryWrapper<ShoppingCart>().lambda()
                                                    .eq(ShoppingCart::getCreatorIdCard,idCard)
                                                    .eq(ShoppingCart::getStatus,ShoppingCartStatus.WAIT_SUBMIT.getCode())
                                                    .in(ShoppingCart::getId,idList));


    }
	
	/**
     * ???????????????????????????
     *
     * @return map
     */
	public Map<String, Integer> getNumGroupByType(String idCard) {
        int iaas = this.count(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getResourceType,ResourceType.IAAS.getCode()).eq(ShoppingCart::getCreatorIdCard,idCard));

        int paas = this.count(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getResourceType,ResourceType.PAAS.getCode()).eq(ShoppingCart::getCreatorIdCard,idCard));
        int saas = this.count(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getResourceType,ResourceType.SAAS_SERVICE.getCode()).eq(ShoppingCart::getCreatorIdCard,idCard));

        int daasService = this.count(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getResourceType,ResourceType.DAAS.getCode()).eq(ShoppingCart::getCreatorIdCard,idCard).eq(ShoppingCart::getServiceTypeName,"????????????(DaaS)"));
        int daasResource = this.count(new QueryWrapper<ShoppingCart>().lambda().eq(ShoppingCart::getResourceType,ResourceType.DAAS.getCode()).eq(ShoppingCart::getCreatorIdCard,idCard).eq(ShoppingCart::getServiceTypeName,"????????????(DaaS)"));

        Map<String,Integer> result = Maps.newHashMapWithExpectedSize(7);
        result.put("iaas",iaas);
        result.put("paas",paas);
        result.put("daasService",daasService);
        result.put("daasResource",daasResource);
        result.put("saas",saas);

        return result;
    }

	@Override
	public Integer count(String idCard) {
		return this.count(new QueryWrapper<ShoppingCart>()
        		.lambda().eq(ShoppingCart::getCreatorIdCard,idCard));
	}

}
