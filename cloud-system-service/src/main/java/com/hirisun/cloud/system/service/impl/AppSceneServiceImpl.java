package com.hirisun.cloud.system.service.impl;

import java.util.List;
import java.util.Map;

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
import com.hirisun.cloud.model.app.vo.AppSceneVo;
import com.hirisun.cloud.model.app.vo.AppSupreVo;
import com.hirisun.cloud.system.bean.AppScene;
import com.hirisun.cloud.system.bean.AppSupre;
import com.hirisun.cloud.system.mapper.AppSceneMapper;
import com.hirisun.cloud.system.mapper.AppSupreMapper;
import com.hirisun.cloud.system.service.AppSceneService;

@Service
public class AppSceneServiceImpl implements AppSceneService {

	@Autowired
	private AppSceneMapper appSceneMapper;
	@Autowired
	private AppSupreMapper appSupreMapper;
	
	/**
	 * 保存应用场景及应用场景优势
	 */
	@Transactional(rollbackFor = Exception.class)
	public void save(SubpageParam param) {

		String masterId = param.getMasterId();
		
		List<AppSceneVo> appSceneList = param.getAppSceneList();
		
        int i=1;
        for (AppSceneVo appSceneVo:appSceneList){
        	
        	AppScene scene = JsonUtils.voToBean(appSceneVo, AppScene.class);
        	
            scene.setId(null);
            scene.setRemark(i+"");
            scene.setIaasId(masterId);
            i++;
            appSceneMapper.insert(scene);
            String appId = scene.getId();
            List<AppSupreVo> supres = appSceneVo.getSupres();
            int j = 1;
            for (AppSupreVo appSupreVo:supres){
            	
            	AppSupre supre = JsonUtils.voToBean(appSupreVo, AppSupre.class);
                supre.setId(null);
                supre.setRemark(j+"");
                supre.setIaasId(masterId);
                supre.setAppId(appId);
                j++;
                appSupreMapper.insert(supre);
            }
        }
    
	}

	/**
	 * 根据master id 获取应用场景集合
	 */
	public List<AppSceneVo> getByMasterId(String masterId) {
		
		List<AppScene> list = appSceneMapper.getByMasterId(masterId);
		
		if(CollectionUtils.isNotEmpty(list)) {
			return JSON.parseObject(JSONObject.toJSONString(list),
					new TypeReference<List<AppSceneVo>>(){});
		}
    	
		return null;
	}

	/**
	 * 根据master id 删除应用场景集合
	 */
	@Transactional(rollbackFor = Exception.class)
	public void remove(String masterId) {
		
		appSceneMapper.delete(new QueryWrapper<AppScene>()
				.lambda().eq(AppScene::getIaasId,masterId));
		
		
		
	}

}
