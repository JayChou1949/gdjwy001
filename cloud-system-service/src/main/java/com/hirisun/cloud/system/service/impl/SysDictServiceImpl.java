package com.hirisun.cloud.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.common.constant.RedisKey;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.model.system.SysDictVO;
import com.hirisun.cloud.redis.service.RedisService;
import com.hirisun.cloud.system.bean.SysDict;
import com.hirisun.cloud.system.mapper.SysDictMapper;
import com.hirisun.cloud.system.service.SysDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
     * 把数据字典数据表数据插入到redis中
     * @return
     */
    @Override
    public void syncSysDictData() {
        List<SysDict> list = this.list(new QueryWrapper<>());
        list.forEach(sysDict->{
            redisService.leftPush(RedisKey.REDIS_SYS_DICT, JSON.toJSONString(sysDict) );
        });
    }

    @Override
    public boolean isBasePaaSService(String id) {
        List<SysDict> list = this.list(new QueryWrapper<>());
        for (SysDict item : list) {
            //TODO feign通过paasId查询paas信息
            if (StringUtils.isNotEmpty(item.getValue())&&item.getValue().equals("基础服务")) {
                return true;
            }
        }
        return false;
    }

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

	@Override
	public SysDictVO feignGetById(String id) {
		
		SysDictVO vo = null;
		
		List<Object> list =  redisService.range("REDIS_SYS_DICT",0,-1);
		for(Object item : list) {
			SysDict dict = JSON.parseObject(item.toString(), SysDict.class);
			if (dict.getId().equals(id)) {
            	vo = new SysDictVO();
            	BeanUtils.copyProperties(dict, vo);
            	break;
            }
		}
		
		return vo;
	}
}
