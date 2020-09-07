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
import com.hirisun.cloud.model.app.vo.FunDetailExpVo;
import com.hirisun.cloud.model.app.vo.FunDetailVo;
import com.hirisun.cloud.system.bean.FunDetail;
import com.hirisun.cloud.system.bean.FunDetailExp;
import com.hirisun.cloud.system.mapper.FunDetailMapper;
import com.hirisun.cloud.system.service.FunDetailExpService;
import com.hirisun.cloud.system.service.FunDetailService;

@Service
public class FunDetailServiceImpl implements FunDetailService {

	@Autowired
	private FunDetailMapper funDetailMapper;
	
	@Autowired
	private FunDetailExpService detailExpService;
    
	/**
	 * 保存功能详情
	 */
	@Transactional(rollbackFor = Exception.class)
    public void save(SubpageParam param) {
		
		List<FunDetailVo> funDetails = param.getFunDetails();
		String masterId = param.getMasterId();
		if(CollectionUtils.isNotEmpty(funDetails)) {
			int i=1;
	        for (FunDetailVo funDetailVo : funDetails){
	        	
	        	FunDetail funDetail = JsonUtils.voToBean(funDetailVo, FunDetail.class);
	        	
	        	funDetail.setId(null);
	        	funDetail.setIaasId(masterId);
	        	funDetail.setRemark(i+"");
	            i++;
	            funDetailMapper.insert(funDetail);
	            String appId = funDetailVo.getId();
	            List<FunDetailExpVo> funDetailExpVoList = funDetailVo.getDetailExps();
	            if(CollectionUtils.isNotEmpty(funDetailExpVoList)) {
	            	int j=1;
		            for (FunDetailExpVo funDetailExpVo:funDetailExpVoList){
		            	
		            	FunDetailExp funDetailExp = JsonUtils.voToBean(funDetailExpVo, FunDetailExp.class);
		            	
		            	funDetailExp.setId(null);
		            	funDetailExp.setRemark(j+"");
		                j++;
		                funDetailExp.setIaasId(masterId);
		                funDetailExp.setAppId(appId);
		                detailExpService.save(funDetailExp);
		            }
	            }
	        }
		}
        
    }

	/**
	 * 根据 master id 获取功能详情
	 */
    public List<FunDetailVo> getByMasterId(SubpageParam param) {
    	
    	List<FunDetail> list = funDetailMapper.getByMasterId(param.getMasterId());
    	
    	if(CollectionUtils.isNotEmpty(list)) {
			return JSON.parseObject(JSONObject.toJSONString(list),
					new TypeReference<List<FunDetailVo>>(){});
		}
    	
        return null;
    }

    /**
	 * 根据 master id 删除功能详情
	 */
    @Transactional(rollbackFor = Exception.class)
	public void remove(SubpageParam param) {
    	funDetailMapper.delete(new QueryWrapper<FunDetail>()
    			.lambda().eq(FunDetail::getIaasId,param.getMasterId()));
	}
}
