package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.entity.application.ServiceLimit;
import com.upd.hwcloud.bean.entity.report.ReportIaas;
import com.upd.hwcloud.bean.entity.report.ReportPaas;
import com.upd.hwcloud.dao.PaasMapper;
import com.upd.hwcloud.dao.application.ServiceLimitMapper;
import com.upd.hwcloud.service.application.IServiceLimitService;
import com.upd.hwcloud.service.report.IReportIaasService;
import com.upd.hwcloud.service.report.IReportPaasService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuc
 * @since 2020-03-03
 */
@Service
public class ServiceLimitServiceImpl extends ServiceImpl<ServiceLimitMapper, ServiceLimit> implements IServiceLimitService {

    @Autowired
    private IReportIaasService reportIaasService;

    @Autowired
    private IReportPaasService reportPaasService;


    @Override
    public ServiceLimit getQuota(String formNum, String area, String policeCategory, String nationalSpecialProject,String clusterName) {

        ServiceLimit quota = null;
        if(StringUtils.equals(formNum,"IAAS_TXYFWXG")){
            formNum = "IAAS_TXYFW";
        }
        if (StringUtils.isNotEmpty(nationalSpecialProject)){
            if(StringUtils.equals("PAAS",formNum)){
                quota = this.getOne(new QueryWrapper<ServiceLimit>().select("sum(CPU) cpu,sum(MEMORY) memory,sum(STORAGE) storage").eq("RESOURCE_TYPE", ResourceType.PAAS.getCode())
                        .eq("NATIONAL_SPECIAL_PROJECT",nationalSpecialProject));
            }else {
                quota = this.getOne(new QueryWrapper<ServiceLimit>().select("sum(CPU) cpu,sum(MEMORY) memory,sum(STORAGE) storage").eq("FORM_NUM",formNum)
                        .eq("NATIONAL_SPECIAL_PROJECT",nationalSpecialProject));
            }
        }else {
            if(!StringUtils.equals(area,"省厅")){
                if(StringUtils.equals("PAAS",formNum)){
                    if (StringUtils.equals("ElasticSearch",clusterName)||StringUtils.equals("Redis",clusterName)||StringUtils.equals("Libra",clusterName)) {
                        quota = this.getOne(new QueryWrapper<ServiceLimit>().select("sum(CPU) cpu,sum(MEMORY) memory,sum(STORAGE) storage").eq("RESOURCE_TYPE", ResourceType.PAAS.getCode())
                                .eq("AREA",area).eq("DESCRIPTION",clusterName));
                    }else {
                        quota = this.getOne(new QueryWrapper<ServiceLimit>().select("sum(CPU) cpu,sum(MEMORY) memory,sum(STORAGE) storage").eq("RESOURCE_TYPE", ResourceType.PAAS.getCode())
                                .eq("AREA",area).eq("DESCRIPTION","YARN"));
                    }
                }else {
                    quota = this.getOne(new QueryWrapper<ServiceLimit>().select("sum(CPU) cpu,sum(MEMORY) memory,sum(STORAGE) storage").eq("FORM_NUM",formNum)
                            .eq("AREA",area));
                }
            }else {
                if(StringUtils.equals("PAAS",formNum)){
                    quota = this.getOne(new QueryWrapper<ServiceLimit>().lambda().eq(ServiceLimit::getArea,"省厅")
                            .eq(ServiceLimit::getPoliceCategory,policeCategory)
                            .eq(ServiceLimit::getResourceType,ResourceType.PAAS.getCode()));
                }else {
                    quota = this.getOne(new QueryWrapper<ServiceLimit>().lambda().eq(ServiceLimit::getArea,"省厅")
                            .eq(ServiceLimit::getPoliceCategory,policeCategory)
                            .eq(ServiceLimit::getFormNum,formNum));
                }
            }
        }
        return quota;
    }

    /**
     * 增加额度
     *
     * @param appInfoId      上报订单号
     * @param area           地市
     * @param policeCategory 警种
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void increaseQuota(String appInfoId, String area, String policeCategory) {
        List<ReportIaas> iaasList =  reportIaasService.list(new QueryWrapper<ReportIaas>().lambda().eq(ReportIaas::getAppInfoId,appInfoId));

        if(CollectionUtils.isNotEmpty(iaasList)){
            //暂时只处理弹性云服务器
            List<ReportIaas> ecsList = iaasList.parallelStream().filter(iaas->StringUtils.equals(iaas.getResourceName(),"弹性云服务器")).collect(Collectors.toList());
           if(CollectionUtils.isNotEmpty(ecsList)){
               dealEcs(ecsList,area,policeCategory);
           }
        }

        List<ReportPaas> paasList = reportPaasService.list(new QueryWrapper<ReportPaas>().lambda().eq(ReportPaas::getAppInfoId,appInfoId));
        if(CollectionUtils.isNotEmpty(paasList)){
            dealPaas(paasList,area,policeCategory);
        }
    }

    /**
     * 处理PaaS限额
     * @param paasList
     * @param area
     * @param policeCategory
     */
    private void dealPaas(List<ReportPaas> paasList,String area,String policeCategory){
        double totalCpu = paasList.parallelStream().filter(item->item.getCpu() != null).map(ReportPaas::getCpu).reduce(Double::sum).get();
        double totalMemory = paasList.parallelStream().filter(item->item.getMemory() != null).map(ReportPaas::getMemory).reduce(Double::sum).get();
        double totalStorage = paasList.parallelStream().filter(item->item.getDisk() != null).map(ReportPaas::getDisk).reduce(Double::sum).get();
        ServiceLimit serviceLimit = this.getOne(new QueryWrapper<ServiceLimit>().lambda().eq(ServiceLimit::getArea,area)
                                                .eq(ServiceLimit::getPoliceCategory,policeCategory)
                                                .eq(ServiceLimit::getResourceType,ResourceType.PAAS.getCode()));
        if(serviceLimit == null){
            ServiceLimit newVo = new ServiceLimit();
            newVo.setArea(area);
            newVo.setCpu(totalCpu);
            newVo.setMemory(totalMemory);
            newVo.setStorage(totalStorage);
            newVo.setPoliceCategory(policeCategory);
            newVo.setResourceType(ResourceType.PAAS.getCode());
            newVo.insert();
        }else {
            double cpu = serviceLimit.getCpu() + totalCpu;
            double memory = serviceLimit.getMemory() + totalMemory;
            double storage = serviceLimit.getStorage() + totalStorage;
            this.update(new ServiceLimit(),new UpdateWrapper<ServiceLimit>().lambda().eq(ServiceLimit::getId,serviceLimit.getId())
                    .set(ServiceLimit::getCpu,cpu)
                    .set(ServiceLimit::getMemory,memory)
                    .set(ServiceLimit::getStorage,storage));
        }
    }

    /**
     * 处理ECS限额
     * @param ecsList
     * @param area
     * @param policeCategory
     */
    private void dealEcs(List<ReportIaas> ecsList,String area,String policeCategory){
        double totalCpu = ecsList.parallelStream().map(ReportIaas::getCpu).reduce(Double::sum).get();
        double totalMemory = ecsList.parallelStream().map(ReportIaas::getMemory).reduce(Double::sum).get();
        double totalStorage = ecsList.parallelStream().map(ReportIaas::getDisk).reduce(Double::sum).get();
        ServiceLimit serviceLimit = this.getOne(new QueryWrapper<ServiceLimit>().lambda().eq(ServiceLimit::getArea,area).eq(ServiceLimit::getPoliceCategory,policeCategory)
                .eq(ServiceLimit::getServiceName,"弹性云服务器")
                .eq(ServiceLimit::getResourceType,ResourceType.IAAS.getCode()));
        if(serviceLimit == null){
            ServiceLimit newVo = new ServiceLimit();
            newVo.setArea(area);
            newVo.setCpu(totalCpu);
            newVo.setMemory(totalMemory);
            newVo.setStorage(totalStorage);
            newVo.setResourceType(ResourceType.IAAS.getCode());
            newVo.setServiceName("弹性云服务器");
            newVo.setFormNum("IAAS_TXYFW");
            newVo.insert();
        }else {
            double cpu = serviceLimit.getCpu() + totalCpu;
            double memory = serviceLimit.getMemory() + totalMemory;
            double storage = serviceLimit.getStorage() + totalStorage;
            this.update(new ServiceLimit(),new UpdateWrapper<ServiceLimit>().lambda().eq(ServiceLimit::getId,serviceLimit.getId())
                    .set(ServiceLimit::getCpu,cpu)
                    .set(ServiceLimit::getMemory,memory)
                    .set(ServiceLimit::getStorage,storage));
        }
    }
}
