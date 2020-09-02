package com.hirisun.cloud.iaas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.api.system.AppSceneApi;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.system.FunChaApi;
import com.hirisun.cloud.api.system.FunDetailApi;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.iaas.bean.IaasSubpage;
import com.hirisun.cloud.iaas.mapper.IaasSubpageMapper;
import com.hirisun.cloud.iaas.service.IaasSubpageService;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.app.vo.AppSceneVo;
import com.hirisun.cloud.model.app.vo.FunChaVo;
import com.hirisun.cloud.model.app.vo.FunDetailVo;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.user.UserVO;

@Service
public class IaasSubpageServiceImpl implements IaasSubpageService {

	@Autowired
	private IaasSubpageMapper iaasSubpageConfigMapper;
	@Autowired
	private FilesApi filesApi;
	@Autowired
	private AppSceneApi appSceneApi;
	@Autowired
	private FunChaApi funChaApi;
	@Autowired
	private FunDetailApi funDetailApi;
	
	/**
	 * 保存iaas 二级页面
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveIaasPage(UserVO user, IaasSubpage iaas) {
		
		//页面设置一个master 就是iaas id
//		iaas.setMasterId(iaas.geti);
		
		String masterId = iaas.getMasterId();
		SubpageParam param = new SubpageParam();
    	param.setMasterId(masterId);
		
        iaas.setId(UUIDUtil.getUUID());
        iaasSubpageConfigMapper.insert(iaas);
        List<FilesVo> files = iaas.getFilesList();
        List<AppSceneVo> scenes = iaas.getAppScenes();
        if (scenes != null) {
        	
        	param.setAppSceneList(scenes);
        	appSceneApi.save(param);
        	
        }
        List<FunChaVo> funChara = iaas.getFunChas();
        param.setFunCha(funChara);
        funChaApi.save(param);
        List<FunDetailVo> funDetails = iaas.getFunDetails();
        param.setFunDetails(funDetails);
        funDetailApi.save(param);
        
        param.setFiles(files);
        param.setRefId(iaas.getId());
        filesApi.refFiles(param);
		
	}

	/**
	 * 更新iaas二级页面详情信息
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateIaasPage(UserVO user, IaasSubpage iaas) {
		deleteSubpage(iaas.getMasterId());
		saveIaasPage(user, iaas);
	}

	/**
	 * 删除iaas二级页面详情信息
	 */
	private void deleteSubpage(String iaasId) {
		
		SubpageParam param = new SubpageParam();
    	param.setMasterId(iaasId);
		
		IaasSubpage page = iaasSubpageConfigMapper.selectOne(new QueryWrapper<IaasSubpage>().lambda().eq(IaasSubpage::getMasterId,iaasId));
		appSceneApi.remove(param);
		funChaApi.remove(param);
		funDetailApi.remove(param);
        if (null!=page){
        	
        	param.setSubpageId(page.getId());
        	filesApi.remove(param);
        }
        iaasSubpageConfigMapper.delete(new QueryWrapper<IaasSubpage>()
        		.lambda().eq(IaasSubpage::getMasterId,iaasId));
    }

	/**
	 * 获取iaas二级页面详情信息
	 */
	public IaasSubpage getDetail(String iaasId) {

		SubpageParam param = new SubpageParam();
    	param.setMasterId(iaasId);
		
		
		IaasSubpage subpage =  iaasSubpageConfigMapper.selectOne(new QueryWrapper<IaasSubpage>()
				.lambda().eq(IaasSubpage::getMasterId,iaasId));
        if (subpage != null) {
        	
        	param.setRefId(subpage.getId());
        	List<FilesVo> filesList = filesApi.find(param);
            subpage.setFilesList(filesList);
            
            // 功能特点
            List<FunChaVo> iaasFunChas = funChaApi.find(param);
            subpage.setFunChas(iaasFunChas);
            // 应用场景
            List<AppSceneVo> appScenes = appSceneApi.getByMasterId(param);
            subpage.setAppScenes(appScenes);
            // 功能详情
            List<FunDetailVo> details = funDetailApi.find(param);
            subpage.setFunDetails(details);
        }
        return subpage;
    
	}

}
