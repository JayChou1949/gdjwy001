package com.hirisun.cloud.platform.document.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.platform.document.bean.DevDocClass;
import com.hirisun.cloud.platform.document.mapper.DevDocClassMapper;
import com.hirisun.cloud.platform.document.service.DevDocClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 文档分类表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
@Service
public class DevDocClassServiceImpl extends ServiceImpl<DevDocClassMapper, DevDocClass> implements DevDocClassService {

    @Autowired
    private DevDocClassMapper devDocClassMapper;

    @Override
    public List<DevDocClass> listWithTree() {
//        List<DevDocClass> redisList= CacheUtils.getList("listWithTree:1",DevDocClass.class);
//        if(redisList!=null&&redisList.size()>0){//有缓存，返回
//            return redisList;
//        }
        //一次性查出所有数据
        List<DevDocClass> list=baseMapper.selectList(null);
        List<DevDocClass> level1Menus= list.stream().filter(categoryEntity ->
                categoryEntity.getParentId()==null
        ).map((menu)->{
            menu.setChildren(getChildrens(menu,list));
            return menu;
        }).sorted((menu1,menu2)->{
            return (int)((menu1.getSortNum()==null?0:menu1.getSortNum())-(menu2.getSortNum()==null?0:menu2.getSortNum()));
        }).collect(Collectors.toList());

//        CacheUtils.saveList("listWithTree:1",level1Menus);
        return level1Menus;
    }


    private List<DevDocClass> getChildrens(DevDocClass root,List<DevDocClass> all){
        List<DevDocClass> level1Menus= all.stream().filter(categoryEntity ->
                categoryEntity.getParentId()!=null&&categoryEntity.getParentId().equals(root.getId())
        ).map((menu)->{
            menu.setChildren(getChildrens(menu,all));
            return menu;
        }).sorted((menu1,menu2)->{
            return (int)((menu1.getSortNum()==null?0:menu1.getSortNum())-(menu2.getSortNum()==null?0:menu2.getSortNum()));
        }).collect(Collectors.toList());

        return level1Menus;
    }
}
