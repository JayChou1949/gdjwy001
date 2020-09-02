package com.hirisun.cloud.order.util;



import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hirisun.cloud.api.user.UserApi;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.model.common.Tree;
import com.hirisun.cloud.model.common.WorkOrder;
import com.hirisun.cloud.model.saas.vo.SaasApplicationMergeVO;
import com.hirisun.cloud.model.user.UserVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wuxiaoxing 2020-09-01
 *
 * 工单通用方法处理
 */
@Component
public class WorkOrderUtils {

    @Autowired
    private WorkflowApi workflowApi;

    @Autowired
    private UserApi userApi;

    /**
     *
     * @param infoList
     * @param user
     */
    public void curHandlerPerson(List<? extends WorkOrder> infoList, UserVO user){
        List<String> instanceId = infoList.stream().map(WorkOrder::getInstanceId).distinct().collect(Collectors.toList());
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
        for(WorkOrder record:infoList){
            if(instance2IdCardsMap.size()!=0){
                if(instance2IdCardsMap.containsKey(record.getInstanceId()) && instance2IdCardsMap.get(record.getInstanceId()) != null){
                    record.setProcessingPerson(instance2IdCardsMap.get(record.getInstanceId()));
                }
            }
        }
        for (WorkOrder record : infoList) {
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
    public void convertIdCardToName(Map<String,String> idCardToNameMap, WorkOrder<?> workOrder){
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
