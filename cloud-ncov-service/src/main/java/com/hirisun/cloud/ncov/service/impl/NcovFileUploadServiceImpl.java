package com.hirisun.cloud.ncov.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hirisun.cloud.api.file.FileUploadApi;
import com.hirisun.cloud.api.redis.RedisApi;
import com.hirisun.cloud.common.util.JsonUtils;
import com.hirisun.cloud.common.util.LocalDateUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.ncov.contains.NcovFileupload;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceLevel2Vo;
import com.hirisun.cloud.model.ncov.vo.daas.NcovDataOverviewVo;
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
	private RedisApi redisApi;
	@Autowired
	private FileUploadApi fileUploadApi;
	@Autowired
	private FileUploadMapper fileUploadMapper;
	@Autowired
	private NcovHomePageDataService ncovHomePageDataService;
	@Autowired
	private NcovRealtimeService ncovRealtimeService;
	
	@Value("${file.access.path}")
	private String fileAccessPath;
	
	/**
	 * 文件上传
	 * 1、先上传到文件服务器
	 * 2、更新或新增文件数据
	 * 3、刷新缓存
	 */
	@Transactional(rollbackFor = Exception.class) 
	public void fileUpload(String serviceType,String dataType, MultipartFile multipartFile) throws Exception {
		
		String fileName = multipartFile.getOriginalFilename();
		
		//先上传
		QueryResponseResult upload = fileUploadApi.upload(multipartFile);
		Integer code = upload.getCode();
		if(code == 0) {
			//保存或更新数据
			String fileOldId = saveFileUpload(serviceType,dataType ,fileName, upload.getData().toString());
			//刷新缓存
			setCache(dataType, multipartFile);
			
			//最后如果是更新数据,则尝试删除旧的excel文件(无法保证100%删除,可能存在网络原因),避免过多垃圾文件
			if(StringUtils.isNotBlank(fileOldId)) {
				QueryResponseResult delete = fileUploadApi.deleteFileByFileId(fileOldId);
				System.out.println(delete.getData());
			}
		}
	}

	/**
	 * 保存或更新上传的文件
	 * @param serviceType
	 * @param dataType
	 * @param fileName
	 * @param fileId
	 * @return
	 */
	private String saveFileUpload(String serviceType,String dataType, String fileName, String fileId) {
		System.out.println("访问路径: "+fileId);
		Map<String, Object> columnMap  = new HashMap<String, Object>();
		columnMap.put("DATA_TYPE", dataType);
		List<FileUpload> fileList = fileUploadMapper.selectByMap(columnMap);
		
		FileUpload fileUpload = null;
		String fileOldPath = null;
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		if(CollectionUtils.isNotEmpty(fileList)) {
			
			fileUpload = fileList.get(0);
			
			//返回旧文件的fileId删除旧文件
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
	 * 根据数据类型刷新对应上传文档的数据到缓存
	 * @param dataType
	 * @param multipartFile
	 * @throws IOException
	 * @throws Exception
	 */
	private void setCache(String dataType, MultipartFile multipartFile) throws IOException, Exception {
		if(NcovFileupload.DAAS_DATA_SHARING.equals(dataType)) {
			
			List<NcovDataOverviewVo> dataSharingVos = NcovEcsImportUtil.getShardingMoelingExcelData(multipartFile.getInputStream(), 1, 0);
			if(CollectionUtils.isNotEmpty(dataSharingVos)) {
				String json = JsonUtils.objectToJson(dataSharingVos);
				saveOrUpdate(NcovFileupload.DAAS_DATA_SHARING,
						NcovKey.HOME_PAGE_DAAS_DATA_SHARING,json);
			}
			
		}else if(NcovFileupload.DAAS_DATA_MODELING.equals(dataType)) {
			
			List<NcovDataOverviewVo> dataModelingVos = NcovEcsImportUtil.getShardingMoelingExcelData(multipartFile.getInputStream(), 1, 0);
			if(CollectionUtils.isNotEmpty(dataModelingVos)) {
				String json = JsonUtils.objectToJson(dataModelingVos);
				saveOrUpdate(NcovFileupload.DAAS_DATA_MODELING,
						NcovKey.HOME_PAGE_DAAS_DATA_MODELING,json);
			}
			
		}else if(NcovFileupload.DAAS_DATA_GOVERNANCE.equals(dataType)) {
			
			Map<String, List<DataGovernanceLevel2Vo>> dataGovernanceMap = NcovEcsImportUtil.getDataGovernanceMap(multipartFile.getInputStream());
			if(MapUtils.isNotEmpty(dataGovernanceMap)) {
				String json = JsonUtils.objectToJson(dataGovernanceMap);
				saveOrUpdate(NcovFileupload.DAAS_DATA_GOVERNANCE,
						NcovKey.HOME_PAGE_DAAS_DATA_GOVERNANCE,json);
			}
			
		}else if(NcovFileupload.DAAS_DATA_ACCESS.equals(dataType)) {
			
			Map<String, Long> dataAccess = NcovEcsImportUtil.getDataAccess(multipartFile.getInputStream());
			if(MapUtils.isNotEmpty(dataAccess)) {
				
				String json = JsonUtils.objectToJson(dataAccess);
				saveOrUpdate(NcovFileupload.DAAS_DATA_ACCESS,
						NcovKey.HOME_PAGE_DAAS_DATA_ACCESS,json);
				
			}
			
		}else if(NcovFileupload.DAAS_DATA_SERVICE.equals(dataType)) {
			
			int ncovDataServiceCount = NcovEcsImportUtil.getNcovDataServiceCount(multipartFile.getInputStream());
			
			saveOrUpdate(NcovFileupload.DAAS_DATA_SERVICE,
					NcovKey.HOME_PAGE_DAAS_DATA_SERVICE_COUNT,String.valueOf(ncovDataServiceCount));
			
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
			
		}else if(NcovFileupload.REALTIME.equals(dataType)) {
			
			List<NcovRealtimeVo> provideList = NcovEcsImportUtil.getNcovRealtimeByExcel(multipartFile.getInputStream(), 0,1);
			List<NcovRealtimeVo> cityList = NcovEcsImportUtil.getNcovRealtimeByExcel(multipartFile.getInputStream(), 1,1);
			ncovRealtimeService.importNcovRealtimeData(provideList);
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
		//新增或更新成功才刷新内存
		if(success >0 ) {
			redisApi.setForPerpetual(redisKey, json);
		}
	}
	
	/**
	 * 根据服务类型获取所有文件(Ncov)
	 */
	public Map<String, String> getFileUrlByServiceType(String serviceType) {
		
		Map<String, Object> columnMap  = new HashMap<String, Object>();
		columnMap.put("SERVICE_TYPE", serviceType);
		List<FileUpload> fileList = fileUploadMapper.selectByMap(columnMap);
		
		Map<String, String> map = NcovFileupload.initUrlData();
		if(CollectionUtils.isNotEmpty(fileList)) {
			fileList.forEach(fileUpload->{map.put(fileUpload.getDataType(), fileAccessPath+fileUpload.getFilePath());});
		}
		return map;
	}

	@Override
	public Map<String, String> getFileUriByServiceType(String serviceType) {
		
		Map<String, Object> columnMap  = new HashMap<String, Object>();
		columnMap.put("SERVICE_TYPE", serviceType);
		List<FileUpload> fileList = fileUploadMapper.selectByMap(columnMap);

		Map<String, String> map = NcovFileupload.initUrlData();
		if(CollectionUtils.isNotEmpty(fileList)) {
			fileList.forEach(fileUpload->{map.put(fileUpload.getDataType(), fileUpload.getFilePath());});
		}
		return map;
	}
	
}
