package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.dto.cov.*;
import com.upd.hwcloud.bean.entity.Org;
import com.upd.hwcloud.dao.OrgMapper;
import com.upd.hwcloud.dao.application.ApplicationInfoMapper;
import com.upd.hwcloud.service.IaasCloudDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author junglefisher
 * @date 2020/3/2 17:57
 */
@Service
public class IaasCloudDeskServiceImpl implements IaasCloudDeskService {

    @Autowired
    private ApplicationInfoMapper applicationInfoMapper;
    @Autowired
    private OrgMapper orgMapper;
    @Value("${ncov.iaas.cloud_desk.start_time}")
    private String startTime;

    @Override
    public CloudDeskHomePage cloudDeskHomePage() {
        CloudDeskHomePage cloudDeskHomePage=new CloudDeskHomePage();
        Integer policeNum=applicationInfoMapper.policeNum(startTime);
        Integer areaNum=applicationInfoMapper.areaNum(startTime);
        List<CloudIdAndUseType> clouds = applicationInfoMapper.allCloudId(startTime);
        BigDecimal cpu =new BigDecimal(0);
        BigDecimal memory =new BigDecimal(0);
        BigDecimal disk =new BigDecimal("0.0");
        for (CloudIdAndUseType cloudIdAndUseType : clouds) {
            String useType = cloudIdAndUseType.getUseType();
            if ("开发".equals(useType)){
                cpu=cpu.add(new BigDecimal(4));
                memory=memory.add(new BigDecimal(8));
                disk=disk.add(new BigDecimal(0.15));
            }else if ("民警访问".equals(useType)){
                cpu=cpu.add(new BigDecimal(2));
                memory=memory.add(new BigDecimal(3));
                disk=disk.add(new BigDecimal("0.03"));
            }
        }
        cloudDeskHomePage.setAreaNum(areaNum);
        cloudDeskHomePage.setPoliceNum(policeNum);
        cloudDeskHomePage.setTotal(clouds.size());
        cloudDeskHomePage.setCpu(cpu.intValue());
        cloudDeskHomePage.setMemory(memory.intValue());
        cloudDeskHomePage.setDisk(disk.doubleValue());
        return cloudDeskHomePage;
    }

    @Override
    public List<CloudDeskNumByAreaOrPolice> cloudDeskByArea() {
        return applicationInfoMapper.cloudDeskByArea(startTime);
    }

    @Override
    public List<CloudDeskDetail> detailByUnit() {
        Integer num=1;
        //  各单位桌面云统计总量集合
        List<CloudDeskDetail> cloudDeskDetails=new ArrayList<>();
        //  表单id及申请人单位名称
        List<CloudDeskIdAndUnit> cloudDeskIdAndUnits= applicationInfoMapper.idAndUnit(startTime);
        //  所有一级单位
        List<CloudDeskIdAndUnit> allOrgByLevel1 = orgMapper.allOrgByLevel1();
        //  各单位对应的桌面云申请类型集合的map
        Map<String, List<String>> unitAndIdsMap = getUnitIdAndUserTypesMap(allOrgByLevel1, cloudDeskIdAndUnits);
        for (CloudDeskIdAndUnit idAndUnit : allOrgByLevel1) {
            //  获取指定单位对应桌面云申请类型集合
            List<String> list = unitAndIdsMap.get(idAndUnit.getId());
            if (list==null||list.size()==0){
                continue;
            }else {
                CloudDeskDetail cloudDeskDetail=new CloudDeskDetail();
                cloudDeskDetail.setUnit(idAndUnit.getUnit());
                cloudDeskDetail.setNum(num++);
                cloudDeskDetail.setTotal(list.size());
                BigDecimal cpu =new BigDecimal(0);
                BigDecimal memory =new BigDecimal(0);
                BigDecimal disk =new BigDecimal("0.0");
                for (String useType : list) {
                    if ("开发".equals(useType)){
                        cpu=cpu.add(new BigDecimal(4));
                        memory=memory.add(new BigDecimal(8));
                        disk=disk.add(new BigDecimal(0.15));
                    }else if ("民警访问".equals(useType)){
                        cpu=cpu.add(new BigDecimal(2));
                        memory=memory.add(new BigDecimal(3));
                        disk=disk.add(new BigDecimal("0.03"));
                    }
                }
                cloudDeskDetail.setCpu(cpu.intValue());
                cloudDeskDetail.setMemory(memory.intValue());
                cloudDeskDetail.setDisk(disk.doubleValue());
                cloudDeskDetails.add(cloudDeskDetail);
            }
        }
        return cloudDeskDetails;
    }

    @Override
    public List<CloudDeskNumByAreaOrPolice> cloudDeskByPolice() {
        return applicationInfoMapper.cloudDeskByPolice(startTime);
    }

    private Map<String,List<String>> getUnitIdAndUserTypesMap(List<CloudDeskIdAndUnit> allOrgByLevel1,List<CloudDeskIdAndUnit> cloudDeskIdAndUnits){
        //  各单位对应表单id集合的map
        Map<String,List<String>> idsMap = new HashMap<>();
        //  所有一级单位
        Map<String, String> map = allOrgByLevel1.stream().collect(Collectors.toMap(CloudDeskIdAndUnit::getId, CloudDeskIdAndUnit::getUnit));
        cloudDeskIdAndUnits.forEach(data ->{
            Org org = orgMapper.selectOne(new QueryWrapper<Org>().lambda().eq(Org::getFullName, data.getUnit()));
            if (org!=null){
                String id = org.getId();
                while (map.get(id) == null){
                    Org org1 = orgMapper.selectOne(new QueryWrapper<Org>().lambda().eq(Org::getId, id));
                    id=org1.getUpGovId();
                }
                if (idsMap.get(id)==null){
                    List<String> list=new ArrayList<>();
                    list.add(data.getUseType());
                    idsMap.put(id,list);
                }else {
                    List<String> list = idsMap.get(id);
                    list.add(data.getUseType());
                    idsMap.put(id,list);
                }
            }
        });
        return idsMap;
    }
}
