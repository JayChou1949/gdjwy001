package com.upd.hwcloud.service.dm.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.dto.VmGeneralExport;
import com.upd.hwcloud.bean.entity.dm.CloudVm;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.utils.BigDecimalUtil;
import com.upd.hwcloud.dao.dm.CloudVmMapper;
import com.upd.hwcloud.service.dm.ICloudVmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yyc
 * @since 2019-08-13
 */
@Service
public class CloudVmServiceImpl extends ServiceImpl<CloudVmMapper, CloudVm> implements ICloudVmService {


    @Autowired
    private CloudVmMapper cloudVmMapper;


    @Override
    public List<CloudVm> getList(String name) {
        return cloudVmMapper.getList(name);
    }


    @Override
    public Page<CloudVm> getPage(Page<CloudVm> page, String name, String type, String orderType,String cloud) {


        return cloudVmMapper.page(page,name,type,orderType,cloud);
    }

    @Override
    public R vmDetail(String name) {
        List<CloudVm> cpuVmPreList =  cloudVmMapper.selectList(new QueryWrapper<CloudVm>().lambda().eq(CloudVm::getBusinessUnit1,name).isNotNull(CloudVm::getCpuUsage).orderByDesc(CloudVm::getCpuUsage));

        List<CloudVm> cpuVmList = cpuVmPreList.stream().limit(5).collect(Collectors.toList());

        List<CloudVm> memVmPreList =  cloudVmMapper.selectList(new QueryWrapper<CloudVm>().lambda().eq(CloudVm::getBusinessUnit1,name).isNotNull(CloudVm::getMemoryUsage).orderByDesc(CloudVm::getMemoryUsage));

        List<CloudVm> memVmList = memVmPreList.stream().limit(5).collect(Collectors.toList());

        List<CloudVm> diskVmPreList =  cloudVmMapper.selectList(new QueryWrapper<CloudVm>().lambda().eq(CloudVm::getBusinessUnit1,name).isNotNull(CloudVm::getEvsDiskUsage).orderByDesc(CloudVm::getEvsDiskUsage));

        List<CloudVm> diskVmList = diskVmPreList.stream().limit(5).collect(Collectors.toList());

        Map<String,List> res = new HashMap<>();

        res.put("cpu",cpuVmList);

        res.put("mem",memVmList);

        res.put("disk",diskVmList);

        return R.ok(res);
    }


    @Override
    public List<CloudVm> vmTop5(String name, String type, String orderType) {
        StringBuilder typeBuilder = new StringBuilder().append(type);
        List<CloudVm> vmList = cloudVmMapper.vmListOrder(name,typeBuilder.toString(),orderType);
        return vmList;
    }

    @Override
    public List<String> poorAreaApp(String name) {
         List<String> appName = cloudVmMapper.poorAreaAppListByName(name);
        return appName;
    }

    @Override
    public List<String> businessUnit1List() {
        List<String> list = cloudVmMapper.businessUnit1();

        return list;
    }


    @Override
    public List<VmGeneralExport> exportByAppName(String appName) {
        List<VmGeneralExport> exportList = cloudVmMapper.exportVmByAppName(appName);
        exportList.forEach(item->{
            if(item.getRamSize()!=null){
                item.setRamSize(BigDecimalUtil.div(item.getRamSize(),1024d).doubleValue());
            }
            if(item.getDiskSize()!=null){
                item.setDiskSize(BigDecimalUtil.div(item.getDiskSize(),1024d).doubleValue());
            }
            if("VM".equals(item.getType())){
                item.setType("虚拟机");
            }

        });
            return exportList;
    }

    @Override
    public R areaAppCount() {
        try {
            List<GeneralDTO> areaAppCount = cloudVmMapper.areaAppCount();
            areaAppCount.forEach(area->{
                String areaName = areaMapping.get(area.getName());
                if (areaName != null) {
                    area.setName(areaName);
                }
            });
            return R.ok(areaAppCount);
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.error("获取地市应用数失败");
    }

    @Override
    public R policeNames() {
        List<CloudVm> areaVm = cloudVmMapper.selectList(new QueryWrapper<CloudVm>().lambda()
                .like(CloudVm::getName,"广东%"));
        try{
            List<String> areaName = areaVm.stream().map(CloudVm::getBusinessUnit1).distinct().collect(Collectors.toList());
            return R.ok(areaName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.error("获取地市失败");
    }

    @Override
    public R AreaNames() {
        List<CloudVm> areaVm = cloudVmMapper.selectList(new QueryWrapper<CloudVm>().lambda()
                .notLike(CloudVm::getName,"广东%"));
        /*areaVm.forEach(area->{
            String areaName = areaMapping.get(area.getBusinessUnit1());
            if (areaName != null) {
                area.setBusinessUnit1(areaName);
            }
        });*/
        try{
            List<String> areaName = areaVm.stream().map(CloudVm::getBusinessUnit1).distinct().collect(Collectors.toList());
            return R.ok(areaName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.error("获取地市失败");
    }

    public static final ImmutableMap<String, String> areaMapping =
            ImmutableMap.<String, String>builder()
                    .put("汕头市公安局", "汕头")
                    .put("佛山市公安局", "佛山")
                    .build();

}
