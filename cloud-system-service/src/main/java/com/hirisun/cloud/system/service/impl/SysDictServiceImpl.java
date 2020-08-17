package com.hirisun.cloud.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.common.constant.RedisKey;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.redis.service.RedisService;
import com.hirisun.cloud.system.bean.SysDict;
import com.hirisun.cloud.system.mapper.SysDictMapper;
import com.hirisun.cloud.system.service.SysDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private RedisService redisService;


    /**
     * 获取数据字典
     * @return
     */
    @Override
    public List<SysDict> getSysDictList() {
        List<Object> list =  redisService.range(RedisKey.REDIS_SYS_DICT,0,-1);
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
            redisService.leftPush(RedisKey.REDIS_SYS_DICT, JSON.toJSONString(sysDict) );
        } else {
            /**
             * 1.查出所有字典
             * 2.查询目标字典下标
             * 3.替换目标字典内容
             */
            List<Object> list =  redisService.range(RedisKey.REDIS_SYS_DICT,0,-1);
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
                redisService.setForList(RedisKey.REDIS_SYS_DICT,targetIndex,JSON.toJSONString(sysDict));
            }
        }
    }

    /**
     * 删除字典
     * @param
     */
    @Override
    public void removeDict(String id) {
        List<Object> list =  redisService.range("REDIS_SYS_DICT",0,-1);
        list.forEach(item->{
            SysDict dict = JSON.parseObject(item.toString(), SysDict.class);
            if (dict.getId().equals(id)) {
                redisService.removeValueForList(RedisKey.REDIS_SYS_DICT, 0,item);
            }
        });
    }
}
