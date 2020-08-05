package com.hirisun.cloud.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.api.redis.RedisApi;
import com.hirisun.cloud.common.util.TreeUtils;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.system.bean.SysDict;
import com.hirisun.cloud.system.mapper.SysDictMapper;
import com.hirisun.cloud.system.service.SysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-29
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Autowired
    private RedisApi redisApi;

    public static String REDIS_SYS_DICT="REDIS_SYS_DICT";


    /**
     * 获取数据字典
     * @return
     */
    @Override
    public List<SysDict> getSysDictList() {
        List<Object> list =  redisApi.range("REDIS_SYS_DICT",0,-1);
        List<SysDict> dictList = new ArrayList();
        for (int x = 0; x < list.size(); x++) {
            SysDict dict = JSON.parseObject(list.get(x).toString(), SysDict.class);
            dictList.add(dict);
        }
        return dictList;
    }

    /**
     * 新增或更新数据字典，保存数据字典到redis
     * @param sysDict
     */
    @Override
    public void saveOrUpdateDict(SysDict sysDict) {
        if (StringUtils.isEmpty(sysDict.getId())) {
            String id = UUIDUtil.getUUID();
            sysDict.setId(id);
            redisApi.leftPush(REDIS_SYS_DICT, JSON.toJSONString(sysDict) );
        } else {
            /**
             * 1.查出所有字典
             * 2.查询目标字典下标
             * 3.替换目标字典内容
             */
            List<Object> list =  redisApi.range(REDIS_SYS_DICT,0,-1);
            if(list==null||list.size()==0){
                return;
            }
            int targetIndex=0;
            boolean dictMatch=false;
            for (int i=0;i<list.size();i++){
                SysDict target = JSON.parseObject(list.get(i).toString(), SysDict.class);
                if (target.getId().equals(sysDict.getId())) {
                    targetIndex=i;
                    dictMatch = true;
                    break;
                }
            }
            if (dictMatch) {
                redisApi.setForList(REDIS_SYS_DICT,targetIndex,JSON.toJSONString(sysDict));
            }
        }
    }

    /**
     * 删除字典
     * @param
     */
    @Override
    public void removeDict(String id) {
        List<Object> list =  redisApi.range("REDIS_SYS_DICT",0,-1);
        list.forEach(item->{
            SysDict dict = JSON.parseObject(item.toString(), SysDict.class);
            if (dict.getId().equals(id)) {
                redisApi.removeValueForList(REDIS_SYS_DICT, 0,item);
            }
        });
    }
}
