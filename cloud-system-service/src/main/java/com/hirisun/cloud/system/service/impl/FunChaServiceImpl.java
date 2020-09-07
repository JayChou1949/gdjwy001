package com.hirisun.cloud.system.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.util.JsonUtils;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.app.vo.FunChaVo;
import com.hirisun.cloud.system.bean.FunCha;
import com.hirisun.cloud.system.mapper.FunChaMapper;
import com.hirisun.cloud.system.service.FunChaService;

@Service
public class FunChaServiceImpl implements FunChaService {

	@Autowired
	private FunChaMapper funChaMapper;
	
	/**
	 * 保存功能特点
	 */
	@Transactional(rollbackFor = Exception.class)
    public void save(SubpageParam param) {
		
		List<FunChaVo> funChaList = param.getFunCha();
		
		String masterId = param.getMasterId();
		if(CollectionUtils.isNotEmpty(funChaList)) {
			int i=1;
	        for (FunChaVo funChaVo:funChaList){
	            FunCha funCha = JsonUtils.voToBean(funChaVo, FunCha.class);
	            funCha.setId(null);
	            funCha.setRemark(i+"");
	            i++;
	            funCha.setIaasId(masterId);
	            funChaMapper.insert(funCha);
	        }
		}
    }

	/**
	 * 根据master id 删除功能特点
	 */
	@Transactional(rollbackFor = Exception.class)
	public void remove(SubpageParam param) {
		funChaMapper.delete(new QueryWrapper<FunCha>()
				.lambda().eq(FunCha::getIaasId,param.getMasterId()));
	}

	/**
	 * 根据master id 获取功能特点
	 */
	public List<FunChaVo> find(SubpageParam param) {
		
		List<FunCha> selectList = funChaMapper.selectList(new QueryWrapper<FunCha>()
				.lambda().eq(FunCha::getIaasId, param.getMasterId())
                    .orderByAsc(FunCha::getRemark));
		
		if(CollectionUtils.isNotEmpty(selectList)) {
			return JSON.parseObject(JSONObject.toJSONString(selectList),
					new TypeReference<List<FunChaVo>>(){});
		}
		
		return null;
	}

}
