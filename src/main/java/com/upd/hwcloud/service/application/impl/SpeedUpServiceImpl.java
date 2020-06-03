package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.entity.EpidemicApplication;
import com.upd.hwcloud.bean.entity.IaasZysb;
import com.upd.hwcloud.bean.entity.Register;
import com.upd.hwcloud.bean.entity.SaasApplicationMerge;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.ResourceRecoverAppInfo;
import com.upd.hwcloud.bean.entity.wfm.Activity;
import com.upd.hwcloud.dao.wfm.ActivityMapper;
import com.upd.hwcloud.service.IUserService;
import com.upd.hwcloud.service.application.ISpeedUpService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yyc
 * @date 2020/4/10
 */
@Service
public class SpeedUpServiceImpl implements ISpeedUpService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private IUserService userService;


    /**
     * 传入实例ID集合获取    实例ID-待办人身份证号集合映射关系
     * @param instanceIdList 实例ID集合
     * @return map
     */
    @Override
    public Map<String,String> instanceToHandleIdCards(List<String> instanceIdList){
        //获取实例对应流转信息，在SQL层不使用 null,放到内存过滤，避免索引失效
        List<Activity> activities = activityMapper.selectList(new QueryWrapper<Activity>()
                .lambda()
                .in(Activity::getInstanceid,instanceIdList));

        //获取待办流转信息
        List<Activity> toDoActivities = activities.parallelStream().filter(activity ->
                StringUtils.equals(activity.getActivitystatus(),"待办")
                        && activity.getActivitytype()==null).collect(Collectors.toList());

        // instanceId-IdCards
        Map<String,String> instanceTodoIdCardsMap = toDoActivities.parallelStream().collect(Collectors.groupingBy(Activity::getInstanceid,
                Collectors.mapping(Activity::getHandlepersonids,Collectors.joining(","))));

        return instanceTodoIdCardsMap;
    }

    /**
     * 身份证号转名字
     * @param idCardToNameMap
     * @param record
     */
    public void convertIdCardToName(Map<String,String> idCardToNameMap,ApplicationInfo record){
        if(idCardToNameMap != null){
            if(StringUtils.isNotBlank(record.getProcessingPerson())){
                List<String> nameList = Lists.newArrayList();
                List<String> idCardList = Splitter.on(",")
                        .trimResults().omitEmptyStrings().splitToList(record.getProcessingPerson());

                idCardList.forEach(idCard->{
                    if(idCardToNameMap.containsKey(idCard)){
                        nameList.add(idCardToNameMap.get(idCard));
                    }
                });

                String names = Joiner.on(",").skipNulls().join(nameList);
                record.setProcessingPerson(names);
            }
        }
    }

    public void convertIdCardToNameEpidemic(Map<String,String> idCardToNameMap,EpidemicApplication record){
        if(idCardToNameMap != null){
            if(StringUtils.isNotBlank(record.getProcessingPerson())){
                List<String> nameList = Lists.newArrayList();
                List<String> idCardList = Splitter.on(",")
                        .trimResults().omitEmptyStrings().splitToList(record.getProcessingPerson());

                idCardList.forEach(idCard->{
                    if(idCardToNameMap.containsKey(idCard)){
                        nameList.add(idCardToNameMap.get(idCard));
                    }
                });

                String names = Joiner.on(",").skipNulls().join(nameList);
                record.setProcessingPerson(names);
            }
        }
    }


    public <T extends  Register> void convertIdCardToNameRegister(Map<String,String> idCardToNameMap,T record){
        if(idCardToNameMap != null){
            if(StringUtils.isNotBlank(record.getProcessingPerson())){
                List<String> nameList = Lists.newArrayList();
                List<String> idCardList = Splitter.on(",")
                        .trimResults().omitEmptyStrings().splitToList(record.getProcessingPerson());

                idCardList.forEach(idCard->{
                    if(idCardToNameMap.containsKey(idCard)){
                        nameList.add(idCardToNameMap.get(idCard));
                    }
                });

                String names = Joiner.on(",").skipNulls().join(nameList);
                record.setProcessingPerson(names);
            }
        }
    }

    public void convertIdCardToNameApplication(Map<String,String> idCardToNameMap,SaasApplicationMerge record){
        if(idCardToNameMap != null){
            if(StringUtils.isNotBlank(record.getProcessingPerson())){
                List<String> nameList = Lists.newArrayList();
                List<String> idCardList = Splitter.on(",")
                        .trimResults().omitEmptyStrings().splitToList(record.getProcessingPerson());

                idCardList.forEach(idCard->{
                    if(idCardToNameMap.containsKey(idCard)){
                        nameList.add(idCardToNameMap.get(idCard));
                    }
                });

                String names = Joiner.on(",").skipNulls().join(nameList);
                record.setProcessingPerson(names);
            }
        }
    }

    public void convertIdCardToNamePublish(Map<String,String> idCardToNameMap,ServicePublish record){
        if(idCardToNameMap != null){
            if(StringUtils.isNotBlank(record.getProcessingPerson())){
                List<String> nameList = Lists.newArrayList();
                List<String> idCardList = Splitter.on(",")
                        .trimResults().omitEmptyStrings().splitToList(record.getProcessingPerson());

                idCardList.forEach(idCard->{
                    if(idCardToNameMap.containsKey(idCard)){
                        nameList.add(idCardToNameMap.get(idCard));
                    }
                });

                String names = Joiner.on(",").skipNulls().join(nameList);
                record.setProcessingPerson(names);
            }
        }
    }

    public void convertIdCardToNameZysb(Map<String,String> idCardToNameMap,IaasZysb record){
        if(idCardToNameMap != null){
            if(StringUtils.isNotBlank(record.getProcessingPerson())){
                List<String> nameList = Lists.newArrayList();
                List<String> idCardList = Splitter.on(",")
                        .trimResults().omitEmptyStrings().splitToList(record.getProcessingPerson());

                idCardList.forEach(idCard->{
                    if(idCardToNameMap.containsKey(idCard)){
                        nameList.add(idCardToNameMap.get(idCard));
                    }
                });

                String names = Joiner.on(",").skipNulls().join(nameList);
                record.setProcessingPerson(names);
            }
        }
    }

    public void convertIdCardToNameResourceRecovered(Map<String,String> idCardToNameMap,ResourceRecoverAppInfo record){
        if(idCardToNameMap != null){
            if(StringUtils.isNotBlank(record.getProcessingPerson())){
                List<String> nameList = Lists.newArrayList();
                List<String> idCardList = Splitter.on(",")
                        .trimResults().omitEmptyStrings().splitToList(record.getProcessingPerson());

                idCardList.forEach(idCard->{
                    if(idCardToNameMap.containsKey(idCard)){
                        nameList.add(idCardToNameMap.get(idCard));
                    }
                });

                String names = Joiner.on(",").skipNulls().join(nameList);
                record.setProcessingPerson(names);
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
            return userService.getIdCardMapToNameByList(idCardElementList);
        }
        return null;
    }


    public  void dealProcessingPerson(List<ApplicationInfo> records, User user){
        List<String> instanceId = records.stream().map(ApplicationInfo::getInstanceId).distinct().collect(Collectors.toList());
        //实例Id到处理人身份证的集合(单表查询)
        Map<String,String> instance2IdCardsMap = instanceToHandleIdCards(instanceId);

        List<String> handlePeronIdsList = Lists.newArrayList();
        instance2IdCardsMap.forEach((k,v)->{
            if(StringUtils.isNotBlank(v)){
                handlePeronIdsList.add(v);
            }
        });


        //获取身份证号到名字的Map
        Map<String,String> idCard2NameMap = idCardsNameMap(handlePeronIdsList);
        //获取实例-待办处理人身份证号Map
        for(ApplicationInfo record:records){
            if(instance2IdCardsMap.size()!=0){
                if(instance2IdCardsMap.containsKey(record.getInstanceId()) && instance2IdCardsMap.get(record.getInstanceId()) != null){
                    record.setProcessingPerson(instance2IdCardsMap.get(record.getInstanceId()));
                }
            }
        }
        for (ApplicationInfo record : records) {

            if(idCard2NameMap != null){
                //身份证号集合字符串替换为名字集合字符串
                convertIdCardToName(idCard2NameMap,record);
            }
            ApplicationInfoStatus ais = ApplicationInfoStatus.codeOf(record.getStatus());
            // 判断是否能删除
            if (ais != ApplicationInfoStatus.DELETE
                    && Objects.equals(user.getIdcard(), record.getCreator())) {
                record.setCanDelete(true);
            }
        }
    }

    public  void dealProcessingPersonEpidemic(List<EpidemicApplication> records, User user){
        List<String> instanceId = records.stream().map(EpidemicApplication::getInstanceId).distinct().collect(Collectors.toList());
        //实例Id到处理人身份证的集合(单表查询)
        Map<String,String> instance2IdCardsMap = instanceToHandleIdCards(instanceId);

        List<String> handlePeronIdsList = Lists.newArrayList();
        instance2IdCardsMap.forEach((k,v)->{
            if(StringUtils.isNotBlank(v)){
                handlePeronIdsList.add(v);
            }
        });


        //获取身份证号到名字的Map
        Map<String,String> idCard2NameMap = idCardsNameMap(handlePeronIdsList);
        //获取实例-待办处理人身份证号Map
        for(EpidemicApplication record:records){
            if(instance2IdCardsMap.size()!=0){
                if(instance2IdCardsMap.containsKey(record.getInstanceId()) && instance2IdCardsMap.get(record.getInstanceId()) != null){
                    record.setProcessingPerson(instance2IdCardsMap.get(record.getInstanceId()));
                }
            }
        }
        for (EpidemicApplication record : records) {

            if(idCard2NameMap != null){
                //身份证号集合字符串替换为名字集合字符串
                convertIdCardToNameEpidemic(idCard2NameMap,record);
            }
            ApplicationInfoStatus ais = ApplicationInfoStatus.codeOf(record.getStatus());
            // 判断是否能删除
            if (ais != ApplicationInfoStatus.DELETE
                    && Objects.equals(user.getIdcard(), record.getCreator())) {
                record.setCanDelete(true);
            }
        }
    }

    public <T extends Register> void dealProcessingPersonRegister(List<T> records, User user){
        List<String> instanceId = records.stream().map(T::getInstanceId).distinct().collect(Collectors.toList());
        //实例Id到处理人身份证的集合(单表查询)
        Map<String,String> instance2IdCardsMap = instanceToHandleIdCards(instanceId);

        List<String> handlePeronIdsList = Lists.newArrayList();
        instance2IdCardsMap.forEach((k,v)->{
            if(StringUtils.isNotBlank(v)){
                handlePeronIdsList.add(v);
            }
        });


        //获取身份证号到名字的Map
        Map<String,String> idCard2NameMap = idCardsNameMap(handlePeronIdsList);
        //获取实例-待办处理人身份证号Map
        for(T record:records){
            if(instance2IdCardsMap.size()!=0){
                if(instance2IdCardsMap.containsKey(record.getInstanceId()) && instance2IdCardsMap.get(record.getInstanceId()) != null){
                    record.setProcessingPerson(instance2IdCardsMap.get(record.getInstanceId()));
                }
            }
        }
        for (T record : records) {

            if(idCard2NameMap != null){
                //身份证号集合字符串替换为名字集合字符串
                convertIdCardToNameRegister(idCard2NameMap,record);
            }
            ApplicationInfoStatus ais = ApplicationInfoStatus.codeOf(record.getStatus());
            // 判断是否能删除
            if (ais != ApplicationInfoStatus.DELETE
                    && Objects.equals(user.getIdcard(), record.getCreator())) {
                record.setCanDelete(true);
            }
        }
    }

    public  void dealProcessingPersonApplication(List<SaasApplicationMerge> records, User user){
        List<String> instanceId = records.stream().map(SaasApplicationMerge::getInstanceId).distinct().collect(Collectors.toList());
        //实例Id到处理人身份证的集合(单表查询)
        Map<String,String> instance2IdCardsMap = instanceToHandleIdCards(instanceId);

        List<String> handlePeronIdsList = Lists.newArrayList();
        instance2IdCardsMap.forEach((k,v)->{
            if(StringUtils.isNotBlank(v)){
                handlePeronIdsList.add(v);
            }
        });


        //获取身份证号到名字的Map
        Map<String,String> idCard2NameMap = idCardsNameMap(handlePeronIdsList);
        //获取实例-待办处理人身份证号Map
        for(SaasApplicationMerge record:records){
            if(instance2IdCardsMap.size()!=0){
                if(instance2IdCardsMap.containsKey(record.getInstanceId()) && instance2IdCardsMap.get(record.getInstanceId()) != null){
                    record.setProcessingPerson(instance2IdCardsMap.get(record.getInstanceId()));
                }
            }
        }


        for (SaasApplicationMerge record : records) {

            if(idCard2NameMap != null){
                //身份证号集合字符串替换为名字集合字符串
                convertIdCardToNameApplication(idCard2NameMap,record);
            }
            boolean stat = ApplicationInfoStatus.INNER_REVIEW.getCode().equals(record.getStatus())
                    || ApplicationInfoStatus.REVIEW_REJECT.getCode().equals(record.getStatus());
            if (stat && Objects.equals(user.getIdcard(), record.getCreator())) {
                record.setCanUnmerge(true);
            }
        }
    }

    public  void dealProcessingPersonPublish(List<ServicePublish> records, User user){
        List<String> instanceId = records.stream().map(ServicePublish::getInstanceId).distinct().collect(Collectors.toList());
        //实例Id到处理人身份证的集合(单表查询)
        Map<String,String> instance2IdCardsMap = instanceToHandleIdCards(instanceId);

        List<String> handlePeronIdsList = Lists.newArrayList();
        instance2IdCardsMap.forEach((k,v)->{
            if(StringUtils.isNotBlank(v)){
                handlePeronIdsList.add(v);
            }
        });


        //获取身份证号到名字的Map
        Map<String,String> idCard2NameMap = idCardsNameMap(handlePeronIdsList);
        //获取实例-待办处理人身份证号Map
        for(ServicePublish record:records){
            if(instance2IdCardsMap.size()!=0){
                if(instance2IdCardsMap.containsKey(record.getInstanceId()) && instance2IdCardsMap.get(record.getInstanceId()) != null){
                    record.setProcessingPerson(instance2IdCardsMap.get(record.getInstanceId()));
                }
            }
        }
        for (ServicePublish record : records) {

            if(idCard2NameMap != null){
                //身份证号集合字符串替换为名字集合字符串
                convertIdCardToNamePublish(idCard2NameMap,record);
            }
            ApplicationInfoStatus ais = ApplicationInfoStatus.codeOf(record.getStatus());
            // 判断是否能删除
            if (ais != ApplicationInfoStatus.DELETE
                    && Objects.equals(user.getIdcard(), record.getCreator())) {
                record.setCanDelete(true);
            }
        }
    }

    @Override
    public void dealProcessingPersonZysb(List<IaasZysb> records, User user) {
        List<String> instanceId = records.stream().map(IaasZysb::getInstanceId).distinct().collect(Collectors.toList());
        //实例Id到处理人身份证的集合(单表查询)
        Map<String,String> instance2IdCardsMap = instanceToHandleIdCards(instanceId);

        List<String> handlePeronIdsList = Lists.newArrayList();
        instance2IdCardsMap.forEach((k,v)->{
            if(StringUtils.isNotBlank(v)){
                handlePeronIdsList.add(v);
            }
        });


        //获取身份证号到名字的Map
        Map<String,String> idCard2NameMap = idCardsNameMap(handlePeronIdsList);
        //获取实例-待办处理人身份证号Map
        for(IaasZysb record:records){
            if(instance2IdCardsMap.size()!=0){
                if(instance2IdCardsMap.containsKey(record.getInstanceId()) && instance2IdCardsMap.get(record.getInstanceId()) != null){
                    record.setProcessingPerson(instance2IdCardsMap.get(record.getInstanceId()));
                }
            }
        }
        for (IaasZysb record : records) {

            if(idCard2NameMap != null){
                //身份证号集合字符串替换为名字集合字符串
                convertIdCardToNameZysb(idCard2NameMap,record);
            }
            ApplicationInfoStatus ais = ApplicationInfoStatus.codeOf(record.getStatus());
            // 判断是否能删除
            if (ais != ApplicationInfoStatus.DELETE
                    && Objects.equals(user.getIdcard(), record.getCreator())) {
                record.setCanDelete(true);
            }
        }
    }

    @Override
    public void dealProcessingPersonResourceRecovered(List<ResourceRecoverAppInfo> records, User user) {
        List<String> instanceId = records.stream().map(ResourceRecoverAppInfo::getInstanceId).distinct().collect(Collectors.toList());
        //实例Id到处理人身份证的集合(单表查询)
        Map<String,String> instance2IdCardsMap = instanceToHandleIdCards(instanceId);

        List<String> handlePeronIdsList = Lists.newArrayList();
        instance2IdCardsMap.forEach((k,v)->{
            if(StringUtils.isNotBlank(v)){
                handlePeronIdsList.add(v);
            }
        });


        //获取身份证号到名字的Map
        Map<String,String> idCard2NameMap = idCardsNameMap(handlePeronIdsList);
        //获取实例-待办处理人身份证号Map
        for(ResourceRecoverAppInfo record:records){
            if(instance2IdCardsMap.size()!=0){
                if(instance2IdCardsMap.containsKey(record.getInstanceId()) && instance2IdCardsMap.get(record.getInstanceId()) != null){
                    record.setProcessingPerson(instance2IdCardsMap.get(record.getInstanceId()));
                }
            }
        }
        for (ResourceRecoverAppInfo record : records) {

            if(idCard2NameMap != null){
                //身份证号集合字符串替换为名字集合字符串
                convertIdCardToNameResourceRecovered(idCard2NameMap,record);
            }
            ApplicationInfoStatus ais = ApplicationInfoStatus.codeOf(record.getStatus());
            // 判断是否能删除
            if (ais != ApplicationInfoStatus.DELETE && ais != ApplicationInfoStatus.USE
                    && Objects.equals(user.getIdcard(), record.getCreatorIdCard())) {
                record.setCanDelete(true);
            }
        }
    }
}
