package com.hirisun.cloud.order.service.register.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.system.SystemApi;
import com.hirisun.cloud.api.user.OrgApi;
import com.hirisun.cloud.api.user.UserApi;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.consts.RedisKey;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.common.WorkOrder;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.system.SysDictVO;
import com.hirisun.cloud.model.user.OrgVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.bean.register.Register;
import com.hirisun.cloud.order.bean.register.SaasRegister;
import com.hirisun.cloud.order.bean.servicePublish.ServicePublish;
import com.hirisun.cloud.order.mapper.register.RegisterMapper;
import com.hirisun.cloud.order.mapper.register.SaasRegisterMapper;
import com.hirisun.cloud.order.mapper.servicePublish.ServicePublishMapper;
import com.hirisun.cloud.order.service.apply.ApplyReviewRecordService;
import com.hirisun.cloud.order.service.register.IRegisterService;
import com.hirisun.cloud.order.util.WorkOrderUtils;
import com.hirisun.cloud.redis.service.RedisService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务注册表 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-05-27
 */
public class RegisterServiceImpl<M extends RegisterMapper<T>, T extends Register> extends ServiceImpl<M, T> implements IRegisterService<T> {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ApplyReviewRecordService applyReviewRecordService;
    @Autowired
    private UserApi userApi;
    @Autowired
    private FilesApi filesApi;
    @Autowired
    private OrgApi orgApi;

    @Autowired
    private SystemApi systemApi;

    @Autowired
    private ServicePublishMapper servicePublishMapper;
    @Autowired
    private SaasRegisterMapper saasRegisterMapper;

    @Autowired
    private WorkflowApi workflowApi;

    @Autowired
    private RedisService redisService;
//    @Autowired
//    private IDictService dictService;

    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);
   
    @Override
    @Transactional
    public void save(UserVO user, T info) {
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        info.setStatus(ApplyInfoStatus.INNER_REVIEW.getCode());

        String orderNum = redisService.genOrderNum(RedisKey.KEY_SAAS_PREFIX);
        info.setOrderNumber(orderNum);
        String serviceCode = genServiceCode(info);
        info.setServiceCode(serviceCode);
        this.save(info);
        // 关联文件信息
        SubpageParam fileParam = new SubpageParam();
        fileParam.setFiles(info.getFileList());
        fileParam.setRefId(info.getId());
        filesApi.refFiles(fileParam);
    }

    private String genServiceCode(T info) {
        String govUnit = null;
        StringBuilder sb = new StringBuilder();
        String codePrefix = null;
        String codeMiddle = null;
        SysDictVO dict = null;
        if (StringUtils.isNotEmpty(info.getSubType())) {
            dict = systemApi.feignGetById(info.getSubType());
        }
        if (!StringUtils.equals(info.getArea(),"政府机构")) {
            OrgVO orgVO = new OrgVO();
            orgVO.setFullName(info.getJsUnit());
            List<OrgVO> orgList = orgApi.getOrgByParams(orgVO);
            if (CollectionUtils.isNotEmpty(orgList)) {
                codePrefix = orgList.get(0).getCode();
            }
        }else {
            try {
                Method getGovUnit = info.getClass().getMethod("getGovUnit", info.getClass().getDeclaredClasses());
                Method govOrgCode = info.getClass().getMethod("getGovOrgCode", info.getClass().getDeclaredClasses());
                govUnit = (String) getGovUnit.invoke(info);
                codePrefix = (String) govOrgCode.invoke(info);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (dict != null) {
            if (StringUtils.equals("政府机构应用",dict.getName())) {
                codeMiddle = "0501";
            }else if (StringUtils.equals("专项应用",dict.getName())) {
                codeMiddle = "0502";
            }else if (StringUtils.equals("疫情专区",dict.getName())) {
                codeMiddle = "0503";
            }else if (StringUtils.equals("通用应用",dict.getName())) {
                codeMiddle = "0504";
            }else if (StringUtils.equals("警种应用",dict.getName())) {
                codeMiddle = "0505";
            }else if (StringUtils.equals("地市应用",dict.getName())) {
                codeMiddle = "0506";
            }
        }
        //todo 查询在iaas、服务发布和应用注册中该申请人所在的机构已创建的申请数量，用于生成5位流水号
//        int iCount = iaasService.count(new QueryWrapper<Iaas>().lambda().eq(Iaas::getResOrg, info.getJsUnit()).isNotNull(Iaas::getServiceCode));
        int iCount=0;
        int pdsCount = servicePublishMapper.selectCount(new QueryWrapper<ServicePublish>().lambda().eq(ServicePublish::getJsUnit, info.getJsUnit()).isNotNull(ServicePublish::getServiceCode));
        int appCount = 0;
        if (StringUtils.equals(info.getArea(),"政府机构")) {
            appCount = saasRegisterMapper.selectCount(new QueryWrapper<SaasRegister>().lambda().eq(SaasRegister::getGovUnit, govUnit).isNotNull(SaasRegister::getServiceCode));
        }else {
            appCount = saasRegisterMapper.selectCount(new QueryWrapper<SaasRegister>().lambda().eq(SaasRegister::getJsUnit, info.getJsUnit()).isNotNull(SaasRegister::getServiceCode));
        }
        int total = iCount+pdsCount+appCount;
        String codeSuffix = String.format("%05d", total+1);

        if (codePrefix != null && codeMiddle != null && codeSuffix != null) {
            sb.append(codePrefix).append(codeMiddle).append(codeSuffix);
        }
        if (sb != null) {
            return sb.toString();
        }
        return null;
    }


    @Override
    public T getDetails(String id) {

        T info = baseMapper.getDetails(id);
        if (null ==info) return null;

        info.setUser(userApi.getUserByIdCard(info.getCreator()));
        SubpageParam param = new SubpageParam();
        param.setRefId(id);
        List<FilesVo> files = filesApi.find(param);
        info.setFileList(files);
        List<ApplyReviewRecord> allReviewInfo = applyReviewRecordService.getAllReviewInfoByAppInfoId(id);
        // 审核信息
        info.setReviewList(allReviewInfo);
        return info;
    }

    /**
     * Paas注册未使用
     * @param user
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<T> getPage(UserVO user, IPage<T> page, Map<String, Object> param ) {

        page = baseMapper.getPage(page, param);
        List<T> records = page.getRecords();
        logger.debug("records -> {}",records);
        if (records != null && !records.isEmpty()) {
            curHandlerPerson(records,user);
        }
        return page;
    }

    @Override
    public IPage<T> getResponsePage(IPage<T> page, UserVO user, String appName) {
        return baseMapper.getResponsePage(page,user.getName(),user.getOrgName(),user.getIdcard(),appName,user.getMobileWork());
    }

    @Transactional
    @Override
    public void update(UserVO user, T info) {
        this.updateById(info);
        // 关联文件信息
        SubpageParam fileParam = new SubpageParam();
        fileParam.setFiles(info.getFileList());
        fileParam.setRefId(info.getId());
        filesApi.refFiles(fileParam);
    }


    @Override
    public void saveService(T register) {

    }

    @Override
    public int getTodoCount(UserVO user) {
        return baseMapper.getTodoCount(user);
    }

    @Override
    public int getRegisterTodo(String idCard) {
        return 0;
    }

    /**
     *
     * @param infoList
     * @param user
     */
    public void curHandlerPerson(List<T> infoList, UserVO user){
        List<String> instanceId = infoList.stream().map(Register::getInstanceId).distinct().collect(Collectors.toList());
        //实例Id到处理人身份证的集合(单表查询)
        Map<String,String> instance2IdCardsMap = workflowApi.instanceToHandleIdCards(instanceId);
        List<String> handlePeronIdsList = Lists.newArrayList();
        instance2IdCardsMap.forEach((k,v)->{
            if(StringUtils.isNotBlank(v)){
                handlePeronIdsList.add(v);
            }
        });
        //获取身份证号到名字的Map
        Map<String,String> idCard2NameMap = idCardsNameMap(handlePeronIdsList);
        //获取实例-待办处理人身份证号Map
        for(Register record:infoList){
            if(instance2IdCardsMap.size()!=0){
                if(instance2IdCardsMap.containsKey(record.getInstanceId()) && instance2IdCardsMap.get(record.getInstanceId()) != null){
                    record.setProcessingPerson(instance2IdCardsMap.get(record.getInstanceId()));
                }
            }
        }
        for (Register record : infoList) {
            if(idCard2NameMap != null){
                //身份证号集合字符串替换为名字集合字符串
                convertIdCardToName(idCard2NameMap,record);
            }
            ApplyInfoStatus applyInfoStatus = ApplyInfoStatus.codeOf(record.getStatus());
            // 判断是否能删除
            if (applyInfoStatus != ApplyInfoStatus.DELETE
                    && Objects.equals(user.getIdcard(), record.getCreator())) {
                record.setCanDelete(true);
            }
        }
    }
    /**
     * 获取身份证号与名字关联的Map
     * @param idCardsList 身份证号集合 {"5110022522,545451515","45454551515,454554"}
     * @return  {"5110022522":"jack"}
     */
    public Map<String,String> idCardsNameMap(List<String> idCardsList){
        List<String> idCardElementList = Lists.newArrayList();
        idCardsList.forEach(idCards->{
            if(StringUtils.isNotEmpty(idCards)){
                List<String> idCardList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(idCards);
                if(CollectionUtils.isNotEmpty(idCardList)){
                    idCardElementList.addAll(idCardList);
                }
            }
        });
        if(CollectionUtils.isNotEmpty(idCardElementList)){
            List<UserVO> userList = userApi.getUserByIdCardList(idCardElementList);
            if (CollectionUtils.isEmpty(userList)) {
                throw new CustomException(CommonCode.SERVER_ERROR);
            }
            return userList.stream().collect(Collectors.toMap(UserVO::getIdcard,UserVO::getName));
        }
        return null;
    }

    /**
     * 身份证号转名字
     * @param idCardToNameMap
     * @param
     */
    public void convertIdCardToName(Map<String,String> idCardToNameMap, Register<T> workOrder){
        if(idCardToNameMap != null){
            if(StringUtils.isNotBlank(workOrder.getProcessingPerson())){
                List<String> nameList = Lists.newArrayList();
                List<String> idCardList = Splitter.on(",")
                        .trimResults().omitEmptyStrings().splitToList(workOrder.getProcessingPerson());

                idCardList.forEach(idCard->{
                    if(idCardToNameMap.containsKey(idCard)){
                        nameList.add(idCardToNameMap.get(idCard));
                    }
                });

                String names = Joiner.on(",").skipNulls().join(nameList);
                workOrder.setProcessingPerson(names);
            }
        }
    }
}
