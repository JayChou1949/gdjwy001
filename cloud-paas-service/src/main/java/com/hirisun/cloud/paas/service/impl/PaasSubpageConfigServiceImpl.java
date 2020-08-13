package com.hirisun.cloud.paas.service.impl;

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
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.app.vo.AppSceneVo;
import com.hirisun.cloud.model.app.vo.FunChaVo;
import com.hirisun.cloud.model.app.vo.FunDetailVo;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.paas.bean.PaasSubpageConfig;
import com.hirisun.cloud.paas.mapper.PaasSubpageConfigMapper;
import com.hirisun.cloud.paas.service.PaasSubpageConfigService;

@Service
public class PaasSubpageConfigServiceImpl implements PaasSubpageConfigService {

	@Autowired
	private PaasSubpageConfigMapper paasSubpageConfigMapper;
	@Autowired
	private FilesApi filesApi;
	@Autowired
	private AppSceneApi appSceneApi;
	@Autowired
	private FunChaApi funChaApi;
	@Autowired
	private FunDetailApi funDetailApi;
	
	@Transactional(rollbackFor = Exception.class)
	public void savePaasPage(UserVO user, PaasSubpageConfig paas) {
		
		//页面设置一个master 就是iaas id
//		iaas.setMasterId(iaas.geti);
		
		String masterId = paas.getMasterId();
		SubpageParam param = new SubpageParam();
    	param.setMasterId(masterId);
		
        paas.setId(UUIDUtil.getUUID());
        paasSubpageConfigMapper.insert(paas);
        List<FilesVo> files = paas.getFilesList();
        List<AppSceneVo> scenes = paas.getAppScenes();
        if (scenes != null) {
        	
        	param.setAppSceneList(scenes);
        	appSceneApi.save(param);
        }
        List<FunChaVo> funCha = paas.getFunChas();
        param.setFunCha(funCha);
        funChaApi.save(param);
        List<FunDetailVo> funDetails = paas.getFunDetails();
        param.setFunDetails(funDetails);
        funDetailApi.save(param);
        
        param.setFiles(files);
        param.setRefId(paas.getId());
        filesApi.refFiles(param);
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateIaasPage(UserVO user, PaasSubpageConfig iaas) {
		deleteSubpage(iaas.getMasterId());
		savePaasPage(user, iaas);
	}

	private void deleteSubpage(String masterId) {
		
		SubpageParam param = new SubpageParam();
    	param.setMasterId(masterId);
		
		PaasSubpageConfig page = paasSubpageConfigMapper.selectOne(new QueryWrapper<PaasSubpageConfig>()
				.lambda().eq(PaasSubpageConfig::getMasterId,masterId));
		appSceneApi.remove(param);
		funChaApi.remove(param);
		funDetailApi.remove(param);
        if (null!=page){
        	
        	param.setSubpageId(page.getId());
        	filesApi.remove(param);
        }
        paasSubpageConfigMapper.delete(new QueryWrapper<PaasSubpageConfig>()
        		.lambda().eq(PaasSubpageConfig::getMasterId,masterId));
    }

	@Override
	public PaasSubpageConfig getDetail(String paasId) {

		SubpageParam param = new SubpageParam();
    	param.setMasterId(paasId);
		
		PaasSubpageConfig subpage =  paasSubpageConfigMapper.selectOne(new QueryWrapper<PaasSubpageConfig>()
				.lambda().eq(PaasSubpageConfig::getMasterId,paasId));
        if (subpage != null) {
        	
        	param.setSubpageId(subpage.getId());
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
