package com.upd.hwcloud.service.dm.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.entity.dm.CloudBusinessTop;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.utils.AreaPoliceCategoryUtils;
import com.upd.hwcloud.dao.dm.CloudBusinessTopMapper;
import com.upd.hwcloud.service.dm.ICloudBusinessTopService;
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
public class CloudBusinessTopServiceImpl extends ServiceImpl<CloudBusinessTopMapper, CloudBusinessTop> implements ICloudBusinessTopService {

    @Autowired
    private CloudBusinessTopMapper cloudBusinessTopMapper;


    /**
     * 应用统计
     * @return
     */
    @Override
    public R applicationAccount() {
        List<GeneralDTO> appCount = cloudBusinessTopMapper.applicationCount();
        appCount.forEach(app->{
            if("海关缉私".equals(app.getName())){
                app.setName("打私");
            }
        });

        List<GeneralDTO> policeCount = appCount.stream().filter(app->!AreaPoliceCategoryUtils.isContainAreaName(app.getName())).collect(Collectors.toList());

        List<GeneralDTO> areaCount =  appCount.stream().filter(app->AreaPoliceCategoryUtils.isContainAreaName(app.getName())).collect(Collectors.toList());


        Map<String,List<GeneralDTO>> result = Maps.newHashMap();

        result.put("police",policeCount); //警种统计
        result.put("area",areaCount); //地市统计

        return R.ok(result);
    }


    /**
     * 警种应用统计（通过应用表）
     * @return
     */
    @Override
    public R  policeApplicationCount(){
        List<GeneralDTO> appCount = cloudBusinessTopMapper.applicationCount();
        appCount.forEach(app->{
            if("海关缉私".equals(app.getName())){
                app.setName("打私");
            }
        });

        List<GeneralDTO> policeCount = appCount.stream().filter(app->!AreaPoliceCategoryUtils.isContainAreaName(app.getName())).collect(Collectors.toList());

        return R.ok(policeCount);
    }

    @Override
    public R areaApplicationCount(){
        List<GeneralDTO> appCount = cloudBusinessTopMapper.applicationCount();
        List<GeneralDTO> areaCount =  appCount.stream().filter(app->AreaPoliceCategoryUtils.isContainAreaName(app.getName())).collect(Collectors.toList());
        return R.ok(areaCount);

    }

    @Override
    public R policeHasAppName(){
        List<GeneralDTO> appCount = cloudBusinessTopMapper.applicationCount();
        appCount.forEach(app->{
            if("海关缉私".equals(app.getName())){
                app.setName("打私");
            }
        });
        List<String > policeName =  appCount.stream().filter(app->!AreaPoliceCategoryUtils.isContainAreaName(app.getName())).map(GeneralDTO::getName).collect(Collectors.toList());
        return R.ok(policeName);
    }

    @Override
    public R areaHasAppName(){
        List<GeneralDTO> appCount = cloudBusinessTopMapper.applicationCount();
        List<String > areaName =  appCount.stream().filter(app->AreaPoliceCategoryUtils.isContainAreaName(app.getName())).map(GeneralDTO::getName).collect(Collectors.toList());
        return R.ok(areaName);

    }


    @Override
    public R policeApplication() {
        try{
            List<GeneralDTO> appCount = cloudBusinessTopMapper.policeApplication();
            appCount.forEach(app->{
                if("海关缉私".equals(app.getName())){
                    app.setName("打私");
                }
            });
            return R.ok(appCount);
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.error("获取警种应用上云失败");
    }

    @Override
    public R areaApplication() {
        try{
            List<GeneralDTO> appCount = cloudBusinessTopMapper.policeApplication();
            return R.ok(appCount);
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.error("获取地市应用上云失败");
    }

    @Override
    public R policeApplicationUsage(String name) {
        List<CloudBusinessTop> cpuUsagePreList = cloudBusinessTopMapper.selectList(new QueryWrapper<CloudBusinessTop>()
                .eq("BUSINESS_UNIT1",name).isNotNull("vcpu_usage").orderByDesc("vcpu_usage"));


        List<CloudBusinessTop>  cpuUsageList = cpuUsagePreList.stream().limit(5).collect(Collectors.toList());

        List<CloudBusinessTop> memUsagePreList = cloudBusinessTopMapper.selectList(new QueryWrapper<CloudBusinessTop>()
                .eq("BUSINESS_UNIT1",name).isNotNull("memeory_usage").orderByDesc("memeory_usage"));

        List<CloudBusinessTop>  memUsageList = memUsagePreList.stream().limit(5).collect(Collectors.toList());

        List<CloudBusinessTop> diskUsagePreList = cloudBusinessTopMapper.selectList(new QueryWrapper<CloudBusinessTop>()
                .eq("BUSINESS_UNIT1",name).isNotNull("evs_disk_usage").orderByDesc("evs_disk_usage"));

        List<CloudBusinessTop> diskUsageList = diskUsagePreList.stream().limit(5).collect(Collectors.toList());

        Map<String,List> res = new HashMap<>();

        res.put("cpu",cpuUsageList);
        res.put("mem",memUsageList);
        res.put("disk",diskUsageList);
        return R.ok(res);
    }

    @Override
    public Page<CloudBusinessTop> getPage(Page<CloudBusinessTop> page, String name, String type, String orderType, String cloud) {
        return cloudBusinessTopMapper.page(page, name, type, orderType, cloud);
    }

    @Override
    public List<String> getAppListByName(String name) {
        List<CloudBusinessTop>   businessTopList = cloudBusinessTopMapper.selectList(new QueryWrapper<CloudBusinessTop>().eq("BUSINESS_UNIT1",name));
        List<String> appName = businessTopList.stream().map(CloudBusinessTop::getName).collect(Collectors.toList());
        return appName;
    }

}
