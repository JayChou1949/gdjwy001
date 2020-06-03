package com.upd.hwcloud.service.dm.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.dto.OverviewDTO;
import com.upd.hwcloud.bean.entity.dm.CloudBusiness;
import com.upd.hwcloud.bean.entity.dm.CloudBusinessTop;
import com.upd.hwcloud.bean.entity.dm.CloudResourceLevel1;
import com.upd.hwcloud.bean.entity.dm.CloudResourceLevel2;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.dao.dm.CloudBusinessMapper;
import com.upd.hwcloud.dao.dm.CloudBusinessTopMapper;
import com.upd.hwcloud.dao.dm.CloudResourceLevel1Mapper;
import com.upd.hwcloud.dao.dm.CloudResourceLevel2Mapper;
import com.upd.hwcloud.service.dm.ICloudResourceLevel2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class CloudResourceLevel2ServiceImpl extends ServiceImpl<CloudResourceLevel2Mapper, CloudResourceLevel2> implements ICloudResourceLevel2Service {
    @Autowired
    private CloudResourceLevel2Mapper resourceLevel2Mapper;

    @Autowired
    private CloudBusinessTopMapper businessTopMapper;

    @Autowired
    private CloudResourceLevel1Mapper resourceLevel1Mapper;

    @Autowired
    private CloudBusinessMapper cloudBusinessMapper;


    /**
     * 省厅地市名
     * @return
     */
    @Override
    public List<String> resourceLevelNames(){
        try{
            List<CloudResourceLevel2> resourceLevel2List = resourceLevel2Mapper.selectList(new QueryWrapper<CloudResourceLevel2>().lambda()
            .eq(CloudResourceLevel2::getDisplay,0));

            List<String> names = resourceLevel2List.stream().map(item -> item.getName()).collect(Collectors.toList());
            return names;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    
    @Override
    public R overviewLevel2(String name) {

        String originName = name;
        if("省厅".equals(name) || "深圳市".equals(name) || "中山市".equals(name) || "数据中台".equals(name)){
            if("省厅".equals(name)){
                name = name + "警务云";
            }else if("数据中台".equals(name)) {
                name = name;
            }else if("深圳市".equals(name) || "中山市".equals(name)){
                String[] strings = name.split("市");
                name = strings[0]+"警务云";
            }
            try{
                CloudResourceLevel2 level2 = resourceLevel2Mapper.selectOne(new QueryWrapper<CloudResourceLevel2>().lambda()
                        .eq(CloudResourceLevel2::getName,name));

                if(level2!=null){
                   /* OverviewDTO overviewDTO = composeOverviewDTO(level2);

                    int appNum = businessTopMapper.selectCount(new QueryWrapper<CloudBusinessTop>().lambda().eq(CloudBusinessTop::getCloud,name));

                    overviewDTO.setAppNum(appNum);*/
                    return R.ok(level2);
                }else {
                    return R.error("该地市尚未对接");
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }else {
            if("佛山市".equals(name) || "汕头市".equals(name)){
                name = name + "公安局";
            }else {
                String[] strings = name.split("市");
                name = strings[0];
            }
            CloudBusiness cloudBusiness = cloudBusinessMapper.selectOne(new QueryWrapper<CloudBusiness>().lambda().eq(CloudBusiness::getName,name));
            CloudResourceLevel2 resourceLevel2 = business2Level2(cloudBusiness,originName,name);
            return R.ok(resourceLevel2);
        }


        return R.error("获取第二级资源概览失败");
    }

    private CloudResourceLevel2 business2Level2(CloudBusiness cloudBusiness,String originName,String name){
        CloudResourceLevel2 resourceLevel2 = new CloudResourceLevel2();
        resourceLevel2.setName(originName);
        resourceLevel2.setVmNumber(cloudBusiness.getVmNumber());
        resourceLevel2.setMemoryTotal(cloudBusiness.getMemeoryTotal());
        resourceLevel2.setVcpuTotal(cloudBusiness.getVcpuTotal());
        resourceLevel2.setStorageTotal(cloudBusiness.getDiskTotal());
        List<CloudBusinessTop> cloudBusinessTopList = businessTopMapper.selectList(new QueryWrapper<CloudBusinessTop>().lambda().eq(CloudBusinessTop::getBusinessUnit1,name));
        if(cloudBusinessTopList!=null){
            resourceLevel2.setAppNum(cloudBusinessTopList.size());
        }
        return resourceLevel2;

    }
    
    
    @Override
    public R originOverview(String name) {

        if("一片云".equals(name)){
            CloudResourceLevel1 oneCloud = resourceLevel1Mapper.selectOne(new QueryWrapper<CloudResourceLevel1>().lambda()
                    .eq(CloudResourceLevel1::getName,"一片云"));
            if(oneCloud != null){
                return R.ok(oneCloud);
            }
            return R.ok("一片云数据不存在");
        }

        CloudResourceLevel2 level2 = resourceLevel2Mapper.selectOne(new QueryWrapper<CloudResourceLevel2>().lambda()
                .eq(CloudResourceLevel2::getName,name));
        if(level2 != null){
            return R.ok(level2);
        }

        return R.ok("该地市未对接");


    }

    
    @Override
    public List<CloudResourceLevel2> getAll() {
        return resourceLevel2Mapper.selectList(new QueryWrapper<>());
    }

    private OverviewDTO composeOverviewDTO(CloudResourceLevel2 resourceLevel2){
        OverviewDTO overviewDTO = new OverviewDTO();
        overviewDTO.setName(resourceLevel2.getName());
        overviewDTO.setMemoryTotal(resourceLevel2.getMemoryTotal());
        overviewDTO.setPhysicalhostNumber(resourceLevel2.getPhysicalhostNumber());
        overviewDTO.setStoragepoolNumber(resourceLevel2.getStoragepoolNumber());
        overviewDTO.setVmNumber(resourceLevel2.getVmNumber());
        overviewDTO.setStorageTotal(resourceLevel2.getStorageTotal());
        return overviewDTO;
    }

}
