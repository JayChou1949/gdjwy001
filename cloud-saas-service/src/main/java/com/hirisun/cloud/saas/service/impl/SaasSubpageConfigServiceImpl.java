package com.hirisun.cloud.saas.service.impl;

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
import com.hirisun.cloud.model.daas.vo.DaasServiceOverview;
import com.hirisun.cloud.model.daas.vo.InnerServiceOverview;
import com.hirisun.cloud.model.daas.vo.ServiceOverview;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.saas.bean.SaasSubpageConfig;
import com.hirisun.cloud.saas.mapper.SaasSubpageConfigMapper;
import com.hirisun.cloud.saas.service.SaasSubpageConfigService;

@Service
public class SaasSubpageConfigServiceImpl implements SaasSubpageConfigService {

	@Autowired
	private SaasSubpageConfigMapper saasSubpageConfigMapper;
	@Autowired
	private FilesApi filesApi;
	@Autowired
	private AppSceneApi appSceneApi;
	@Autowired
	private FunChaApi funChaApi;
	@Autowired
	private FunDetailApi funDetailApi;
	
	@Transactional(rollbackFor = Exception.class)
	public void saveSaasPage(UserVO user, SaasSubpageConfig saas) {
		
		//页面设置一个master 就是iaas id
//		iaas.setMasterId(iaas.geti);
		
		String masterId = saas.getMasterId();
		
		SubpageParam param = new SubpageParam();
    	param.setMasterId(masterId);
    	
		saas.setId(UUIDUtil.getUUID());
        saasSubpageConfigMapper.insert(saas);
        List<FilesVo> files = saas.getFilesList();
        List<AppSceneVo> scenes = saas.getAppScenes();
        if (scenes != null) {
        	param.setAppSceneList(scenes);
        	appSceneApi.save(param);
        }
        List<FunChaVo> funCha = saas.getFunChas();
        param.setFunCha(funCha);
        funChaApi.save(param);
        List<FunDetailVo> funDetails = saas.getFunDetails();
        param.setFunDetails(funDetails);
        funDetailApi.save(param);
        param.setFiles(files);
        param.setRefId(saas.getId());
        filesApi.refFiles(param);
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateIaasPage(UserVO user, SaasSubpageConfig iaas) {
		deleteSubpage(iaas.getMasterId());
		saveSaasPage(user, iaas);
	}

	private void deleteSubpage(String masterId) {
		
		SubpageParam param = new SubpageParam();
    	param.setMasterId(masterId);
		
		SaasSubpageConfig page = saasSubpageConfigMapper.selectOne(new QueryWrapper<SaasSubpageConfig>()
				.lambda().eq(SaasSubpageConfig::getMasterId,masterId));
		appSceneApi.remove(param);
		funChaApi.remove(param);
		funDetailApi.remove(param);
        if (null!=page){
        	
        	param.setSubpageId(page.getId());
        	filesApi.remove(param);
        }
        saasSubpageConfigMapper.delete(new QueryWrapper<SaasSubpageConfig>()
        		.lambda().eq(SaasSubpageConfig::getMasterId,masterId));
    }

	@Override
	public SaasSubpageConfig getDetail(String masterId) {

		SubpageParam param = new SubpageParam();
    	param.setMasterId(masterId);
		
		SaasSubpageConfig subpage =  saasSubpageConfigMapper.selectOne(new QueryWrapper<SaasSubpageConfig>()
				.lambda().eq(SaasSubpageConfig::getMasterId,masterId));
		
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

	@Override
	public List<DaasServiceOverview> daas(String name) {
		return saasSubpageConfigMapper.daas(name);
	}

	@Override
	public List<ServiceOverview> paas(String name) {
		return saasSubpageConfigMapper.paas(name);
	}

	@Override
	public List<InnerServiceOverview> paasOther(String name) {
		return saasSubpageConfigMapper.paasOther(name);
	}
	
	


}
