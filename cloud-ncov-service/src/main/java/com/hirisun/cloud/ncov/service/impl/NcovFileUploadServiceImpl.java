package com.hirisun.cloud.ncov.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hirisun.cloud.redis.service.RedisService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hirisun.cloud.api.file.FileApi;
import com.hirisun.cloud.common.exception.ExceptionCast;
import com.hirisun.cloud.common.util.JsonUtils;
import com.hirisun.cloud.common.util.LocalDateUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.model.ncov.contains.NcovFileupload;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.vo.daas.DataAccessVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataModelingVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataSharingVo;
import com.hirisun.cloud.model.ncov.vo.file.FileUploadVo;
import com.hirisun.cloud.model.ncov.vo.iaas.NcovHomePageIaasVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterApp;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterOverviewVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterResourceVo;
import com.hirisun.cloud.model.ncov.vo.realtime.NcovRealtimeVo;
import com.hirisun.cloud.ncov.bean.FileUpload;
import com.hirisun.cloud.ncov.bean.NcovHomePageData;
import com.hirisun.cloud.ncov.mapper.FileUploadMapper;
import com.hirisun.cloud.ncov.service.NcovFileUploadService;
import com.hirisun.cloud.ncov.service.NcovHomePageDataService;
import com.hirisun.cloud.ncov.service.NcovRealtimeService;
import com.hirisun.cloud.ncov.util.NcovEcsImportUtil;


@Service
@RefreshScope
public class NcovFileUploadServiceImpl implements NcovFileUploadService {

	@Autowired
	private RedisService redisService;
	@Autowired
	private FileApi fileApi;
	@Autowired
	private FileUploadMapper fileUploadMapper;
	@Autowired
	private NcovHomePageDataService ncovHomePageDataService;
	@Autowired
	private NcovRealtimeService ncovRealtimeService;
	
	@Value("${file.access.path}")
	private String fileAccessPath;
	
	/**
	 * ????????????
	 * 1??????????????????????????????
	 * 2??????????????????????????????
	 * 3???????????????
	 */
	@Transactional(rollbackFor = Exception.class) 
	public FileUploadVo fileUpload(String serviceType,String dataType,MultipartFile multipartFile) throws Exception {
		
		String fileName = multipartFile.getOriginalFilename();
		
		//?????????
		String filePath = fileApi.upload(multipartFile, NcovFileupload.NCOV_FILE_TYPE, "????????????????????????.");
		if(StringUtils.isNotBlank(filePath)) {
			//?????????????????????
			String fileOldId = saveFileUpload(serviceType,dataType ,fileName, filePath);
			//????????????
			setCache(dataType,multipartFile);
			
			//???????????????????????????,?????????????????????excel??????(????????????100%??????,????????????????????????),????????????????????????
			if(StringUtils.isNotBlank(fileOldId)) {
				fileApi.delete(fileOldId);
			}
			FileUploadVo fileUploadVo = new FileUploadVo();
			fileUploadVo.setDataType(dataType);
			fileUploadVo.setFileName(fileName);
			fileUploadVo.setFilePath(fileAccessPath+filePath);
			return fileUploadVo;
			
		}
		return null;
	}

	/**
	 * ??????????????????????????????
	 * @param serviceType
	 * @param dataType
	 * @param fileName
	 * @param fileId
	 * @return
	 */
	private String saveFileUpload(String serviceType,String dataType, String fileName, String fileId) {
		Map<String, Object> columnMap  = new HashMap<String, Object>();
		columnMap.put("DATA_TYPE", dataType);
		List<FileUpload> fileList = fileUploadMapper.selectByMap(columnMap);
		
		FileUpload fileUpload = null;
		String fileOldPath = null;
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		if(CollectionUtils.isNotEmpty(fileList)) {
			
			fileUpload = fileList.get(0);
			
			//??????????????????fileId???????????????
			fileOldPath = fileUpload.getFilePath();
			
			fileUpload.setUpdateDate(LocalDateUtil.getCurrentDateTime());
			fileUpload.setFileName(fileName);
			fileUpload.setFilePath(fileId);
			fileUpload.setServiceType(serviceType);
			fileUpload.setDataType(dataType);
			fileUpload.setFileType(fileType);
			fileUploadMapper.updateById(fileUpload);
		}else {
			fileUpload = new FileUpload();
			fileUpload.setCreateDate(LocalDateUtil.getCurrentDateTime());
			fileUpload.setFileName(fileName);
			fileUpload.setFilePath(fileId);
			fileUpload.setServiceType(serviceType);
			fileUpload.setDataType(dataType);
			fileUpload.setFileType(fileType);
			fileUploadMapper.insert(fileUpload);
		}
		
		return fileOldPath;
	}

	/**
	 * ????????????????????????????????????????????????????????????
	 * @param dataType
	 * @param multipartFile
	 * @throws IOException
	 * @throws Exception
	 */
	private void setCache(String dataType,MultipartFile multipartFile) throws IOException, Exception {
		if(NcovFileupload.DAAS_DATA_SHARING.equals(dataType)) {
			
			DataSharingVo dataSharingVo = NcovEcsImportUtil.getShardingExcelData(multipartFile.getInputStream());
			if(dataSharingVo != null) {
				String json = JsonUtils.objectToJson(dataSharingVo);
				saveOrUpdate(NcovFileupload.DAAS_DATA_SHARING,
						NcovKey.HOME_PAGE_DAAS_DATA_SHARING,json);
			}
			
		}else if(NcovFileupload.DAAS_DATA_MODELING.equals(dataType)) {
			
			DataModelingVo dataModelingVo = NcovEcsImportUtil.getMoelingExcelData(multipartFile.getInputStream());
			if(dataModelingVo != null) {
				String json = JsonUtils.objectToJson(dataModelingVo);
				saveOrUpdate(NcovFileupload.DAAS_DATA_MODELING,
						NcovKey.HOME_PAGE_DAAS_DATA_MODELING,json);
			}
			
		}else if(NcovFileupload.DAAS_DATA_GOVERNANCE.equals(dataType)) {
			
			DataGovernanceVo dataGovernanceVo = NcovEcsImportUtil.getDataGovernance(multipartFile.getInputStream());
			if(dataGovernanceVo != null) {
				String json = JsonUtils.objectToJson(dataGovernanceVo);
				saveOrUpdate(NcovFileupload.DAAS_DATA_GOVERNANCE,
						NcovKey.HOME_PAGE_DAAS_DATA_GOVERNANCE,json);
			}
			
		}else if(NcovFileupload.DAAS_DATA_ACCESS.equals(dataType)) {
			
			DataAccessVo dataAccess = NcovEcsImportUtil.getDataAccess(multipartFile.getInputStream());
			
			List<List<Object>> police = NcovEcsImportUtil.getDataAccessLevel3(multipartFile.getInputStream(),"??????",1,2);
			List<List<Object>> other = NcovEcsImportUtil.getDataAccessLevel3(multipartFile.getInputStream(),"",1,1);
			
			if(dataAccess != null) {
				
				dataAccess.setLevel3Police(police);
				dataAccess.setLevel3Other(other);
				
	            //?????????????????????
	            String json = JsonUtils.objectToJson(dataAccess);
				saveOrUpdate(NcovFileupload.DAAS_DATA_ACCESS,
						NcovKey.HOME_PAGE_DAAS_DATA_ACCESS,json);
			}
			
			
			
		}else if(NcovFileupload.DAAS_DATA_SERVICE.equals(dataType)) {
			
			Map<String, Map<String, Long>> map = NcovEcsImportUtil.getNcovDataServiceCount(multipartFile.getInputStream());
			if(MapUtils.isNotEmpty(map)) {
				String json = JsonUtils.objectToJson(map);
				saveOrUpdate(NcovFileupload.DAAS_DATA_SERVICE,
						NcovKey.HOME_PAGE_DAAS_DATA_SERVICE_COUNT,json);
			}
			
		}else if(NcovFileupload.IAAS_VM.equals(dataType)) {
			
			NcovHomePageIaasVo vmIaasVo = NcovEcsImportUtil.getOverviewData(multipartFile.getInputStream());
			if(vmIaasVo != null) {
				String json = JsonUtils.objectToJson(vmIaasVo);
				saveOrUpdate(NcovFileupload.IAAS_VM,
						NcovKey.HOME_PAGE_IAAS_NCOV_OVERVIEW,json);
			}
			
		}else if(NcovFileupload.IAAS_DESKTOP.equals(dataType)) {
			
			NcovHomePageIaasVo desktopNumIaasVo = NcovEcsImportUtil.epidemicExcl(multipartFile.getInputStream());
			if(desktopNumIaasVo != null) {
				String json = JsonUtils.objectToJson(desktopNumIaasVo);
				saveOrUpdate(NcovFileupload.IAAS_DESKTOP,
						NcovKey.HOME_PAGE_IAAS_NCOV_DESKTOP,json);
			}
			
		}else if(NcovFileupload.PAAS_DATA.equals(dataType)) {
			
			NcovClusterOverviewVo overview = NcovEcsImportUtil.getOverview(multipartFile.getInputStream(),0);
			if(overview != null) {
				String json = JsonUtils.objectToJson(overview);
				saveOrUpdate(NcovFileupload.PAAS_OVERVIEW,
						NcovKey.HOME_PAGE_PAAS_OVERVIEW,json);
			}
			
			List<NcovClusterResourceVo> resourceList = NcovEcsImportUtil.getResourceList(multipartFile.getInputStream(),1);
			if(CollectionUtils.isNotEmpty(resourceList)) {
				String json = JsonUtils.objectToJson(resourceList);
				saveOrUpdate(NcovFileupload.PAAS_RESOURCE,
						NcovKey.HOME_PAGE_PAAS_RESOURCE,json);
			}
			
			List<NcovClusterApp> appList = NcovEcsImportUtil.getAppDetailList(multipartFile.getInputStream(),2);
			if(CollectionUtils.isNotEmpty(appList)) {
				String json = JsonUtils.objectToJson(appList);
				saveOrUpdate(NcovFileupload.PAAS_APPDETAIL,
						NcovKey.HOME_PAGE_PAAS_APPDETAIL,json);
			}
			
		}else if(NcovFileupload.REALTIME_PROVICE.equals(dataType)) {
			
			List<NcovRealtimeVo> provideList = NcovEcsImportUtil.getNcovRealtimeByExcel(multipartFile.getInputStream(),1);
			ncovRealtimeService.importNcovRealtimeData(provideList);
			
			
		}else if(NcovFileupload.REALTIME_CITY.equals(dataType)) {
			
			List<NcovRealtimeVo> cityList = NcovEcsImportUtil.getNcovRealtimeByExcel(multipartFile.getInputStream(),2);
			ncovRealtimeService.importNcovRealtimeData(cityList);
			
			
		}
	}

	private void saveOrUpdate(String dataType,String redisKey,String json) {
		NcovHomePageData ncovHomePageData = ncovHomePageDataService.getNcovHomePageDataByType(dataType);
		
		int success = 0;
		
		if(ncovHomePageData != null) {
			ncovHomePageData.setUpdateDate(LocalDateUtil.getCurrentDateTime());
			ncovHomePageData.setData(json);
			success = ncovHomePageDataService.updateNcovHomePageData(ncovHomePageData);
		}else {
			ncovHomePageData = new NcovHomePageData();
			ncovHomePageData.setData(json);
			ncovHomePageData.setDataType(dataType);
			ncovHomePageData.setCreateDate(LocalDateUtil.getCurrentDateTime());
			success = ncovHomePageDataService.saveNcovHomePageData(ncovHomePageData);
		}
		//????????????????????????????????????
		if(success >0 ) {
			redisService.setForPerpetual(redisKey, json);
		}
	}
	
	/**
	 * ????????????????????????????????????(Ncov)
	 */
	public Map<String, FileUploadVo> getFileUrlByServiceType(String serviceType,String dataType) {
		
		//serviceType ????????????
		if(StringUtils.isBlank(serviceType)) {
			ExceptionCast.cast(CommonCode.INVALID_PARAM);
		}
		Map<String, Object> columnMap  = new HashMap<String, Object>();
		columnMap.put("SERVICE_TYPE", serviceType);
		if(StringUtils.isNotBlank(dataType)) {
			columnMap.put("DATA_TYPE", dataType);
		}
		List<FileUpload> fileList = fileUploadMapper.selectByMap(columnMap);
		
		Map<String, FileUploadVo> map = NcovFileupload.initUrlData();
		if(CollectionUtils.isNotEmpty(fileList)) {
			fileList.forEach(fileUpload->{
				FileUploadVo vo = map.get(fileUpload.getDataType());
				vo.setFileName(fileUpload.getFileName());
				vo.setFilePath(fileAccessPath+fileUpload.getFilePath());
			});
		}
		return map;
	}

	@Override
	public FileUpload getNcovFileUploadByType(String serviceType, String dataType) {
		
		Map<String, Object> columnMap  = new HashMap<String, Object>();
		columnMap.put("SERVICE_TYPE", serviceType);
		columnMap.put("DATA_TYPE", dataType);
		
		List<FileUpload> fileList = fileUploadMapper.selectByMap(columnMap);
		if(CollectionUtils.isNotEmpty(fileList))return fileList.get(0);
		return null;
	}

	@Transactional(rollbackFor = Exception.class) 
	public void updateFileUpload(FileUpload fileUpload) {
		fileUploadMapper.updateById(fileUpload);
	}

}
