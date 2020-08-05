package com.hirisun.cloud.ncov.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.hirisun.cloud.api.file.FileApi;
import com.hirisun.cloud.api.redis.RedisApi;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.ncov.contains.NcovFileupload;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.vo.file.FileVo;
import com.hirisun.cloud.model.ncov.vo.realtime.HomePageNcovRealtimeVo;
import com.hirisun.cloud.model.ncov.vo.realtime.NcovRealtimeVo;
import com.hirisun.cloud.ncov.bean.FileUpload;
import com.hirisun.cloud.ncov.bean.NcovRealtime;
import com.hirisun.cloud.ncov.mapper.NcovRealtimeMapper;
import com.hirisun.cloud.ncov.service.NcovFileUploadService;
import com.hirisun.cloud.ncov.service.NcovRealtimeService;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;

@Service
public class NcovRealtimeServiceImpl implements NcovRealtimeService {

	@Autowired
	private NcovRealtimeMapper ncovRealtimeMapper;
	@Autowired
    private RedisApi redisApi;
	@Autowired
	private FileApi fileApi;
	@Autowired
	private NcovFileUploadService ncovFileUploadService;
	
	@Value("${file.access.path}")
	private String fileAccessPath;
	
	/**
	 * 获取首页 疫情实时数据,先读取缓存，没有则读取数据库
	 */
	public HomePageNcovRealtimeVo getHomePageNcovRealtimeVo() {
		
		String resString = redisApi.getStrValue(NcovKey.HOME_PAGE_NCOV_REALTIME);
		if(StringUtils.isNotBlank(resString))return JSON.parseObject(resString,HomePageNcovRealtimeVo.class);
		
		HomePageNcovRealtimeVo vo = setHomePageNcovRealtimeCache();
		return vo;
	}
	
	/**
	 * 获取首页疫情实时数据
	 * @return
	 */
	private HomePageNcovRealtimeVo getHomePageNcovRealtime() {
		
		List<NcovRealtimeVo> allList = ncovRealtimeMapper.findAll();
		//全国数据省份
		List<NcovRealtimeVo> provinceList = new ArrayList<NcovRealtimeVo>();
		//全国统计数据
		NcovRealtimeVo counturyTotal = new NcovRealtimeVo();
		//所有城市列表数据
		List<NcovRealtimeVo> cityList = new ArrayList<NcovRealtimeVo>();
		//统计所有城市列表数据
		NcovRealtimeVo cityTotal = new NcovRealtimeVo();
		
		if(CollectionUtils.isNotEmpty(allList)) {
			
			allList.forEach(vo->{
				
				Integer regionType = vo.getRegionType();
				//省份
				if(regionType == 1) {
					
					provinceList.add(vo);
					
					counturyTotal.setCure(counturyTotal.getCure()+vo.getCure());
					counturyTotal.setDeath(counturyTotal.getDeath()+vo.getDeath());
					counturyTotal.setDiagnosis(counturyTotal.getDiagnosis()+vo.getDiagnosis());
					counturyTotal.setSuspected(counturyTotal.getSuspected()+vo.getSuspected());
					counturyTotal.setYesterdayCure(counturyTotal.getYesterdayCure()+vo.getYesterdayCure());
					counturyTotal.setYesterdayDeath(counturyTotal.getYesterdayDeath()+vo.getYesterdayDeath());
					counturyTotal.setYesterdayDiagnosis(counturyTotal.getYesterdayDiagnosis()+vo.getYesterdayDiagnosis());
					counturyTotal.setYesterdaySuspected(counturyTotal.getYesterdaySuspected()+vo.getYesterdaySuspected());
					
				}else {
					cityList.add(vo);
					
					cityTotal.setCure(cityTotal.getCure()+vo.getCure());
					cityTotal.setDeath(cityTotal.getDeath()+vo.getDeath());
					cityTotal.setDiagnosis(cityTotal.getDiagnosis()+vo.getDiagnosis());
					cityTotal.setSuspected(cityTotal.getSuspected()+vo.getSuspected());
					cityTotal.setYesterdayCure(cityTotal.getYesterdayCure()+vo.getYesterdayCure());
					cityTotal.setYesterdayDeath(cityTotal.getYesterdayDeath()+vo.getYesterdayDeath());
					cityTotal.setYesterdayDiagnosis(cityTotal.getYesterdayDiagnosis()+vo.getYesterdayDiagnosis());
					cityTotal.setYesterdaySuspected(cityTotal.getYesterdaySuspected()+vo.getYesterdaySuspected());
				}
				
			});
			
		}
		
		HomePageNcovRealtimeVo vo = new HomePageNcovRealtimeVo();
		//当天全国各省确诊、疑似、治愈、死亡,较昨日增长总计
		vo.setCountryTotal(counturyTotal);
		//广东确诊、治愈、死亡,较昨日增长总计
		vo.setCityTotal(cityTotal);
		//当天全国各省确诊、疑似、治愈、死亡地图数据
		vo.setProvinceList(provinceList);
		//广东列表数据
		vo.setCityList(cityList);
		return vo;
	}

	/**
	 * 导入数据,存在则刷新,不存在则新增
	 */
	@Transactional
	public ResponseResult importNcovRealtimeData(List<NcovRealtimeVo> importList)throws CustomException {
		
		ResponseResult result = new ResponseResult(CommonCode.SUCCESS.code(),CommonCode.SUCCESS.msg());
		
		if(CollectionUtils.isNotEmpty(importList)) {
			
			importList.forEach(ncovRealtime->{
				saveOrUpdateNcovRealtime(ncovRealtime);
			});
			//刷新缓存
			setHomePageNcovRealtimeCache();
		}else {
			result.setCode(CommonCode.IMPORT_FAIL.code());
			result.setMsg(CommonCode.IMPORT_FAIL.msg());
		}
		return result;
		
	}

	/**
	 * 根据id获取数据
	 */
	public NcovRealtime getNcovRealtimeById(String id) {
		NcovRealtime ncovRealtime = ncovRealtimeMapper.selectById(id);
		return ncovRealtime;
	}

	/**
	 * 新增或更新数据
	 * @param vo
	 */
	private void saveOrUpdateNcovRealtime(NcovRealtimeVo vo) {
		String regionName = vo.getRegionName();
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("REGION_NAME", regionName);
		List<NcovRealtime> list = ncovRealtimeMapper.selectByMap(columnMap);
		
		if(CollectionUtils.isNotEmpty(list)) {
			list.forEach(entity->{
				vo.setId(entity.getId());
				BeanUtils.copyProperties(vo, entity);
				ncovRealtimeMapper.updateById(entity);
			});
		}else {
			NcovRealtime entity = new NcovRealtime();
			BeanUtils.copyProperties(vo, entity);
			ncovRealtimeMapper.insert(entity);
		}
	}
	
	/**
	 * 后台系统编辑数据
	 */
	@Transactional
	public ResponseResult editNcovRealtime(NcovRealtimeVo vo) {
		
		ResponseResult result = new ResponseResult(CommonCode.SUCCESS.code(),CommonCode.SUCCESS.msg());
		saveOrUpdateNcovRealtime(vo);
		//刷新缓存
		setHomePageNcovRealtimeCache();
		return result;
	}

	/**
	 *刷新缓存 
	 */
	public HomePageNcovRealtimeVo setHomePageNcovRealtimeCache() {
		HomePageNcovRealtimeVo vo = getHomePageNcovRealtime();
		redisApi.setForPerpetual(NcovKey.HOME_PAGE_NCOV_REALTIME, JSON.toJSONString(vo));
		return vo;
	}

	@Override
	public List<NcovRealtimeVo> findNcovRealtimeByRegionType(int regionType) {
		List<NcovRealtimeVo> list = ncovRealtimeMapper.findNcovRealtimeList(regionType);
		return list;
	}

	@Override
	public NcovRealtimeVo countNcovRealTime(Integer regionType) {
		return ncovRealtimeMapper.countNcovRealTime(regionType);
	}

	@Override
	public String exportNcovRealtimeByRegionType(String serviceType,String dataType,int regionType) {
		
		List<NcovRealtimeVo> list = ncovRealtimeMapper.findNcovRealtimeList(regionType);
		if(CollectionUtils.isNotEmpty(list)) {
			try {
				//先删除原来可能存在的文件
				FileUpload fileUpload = ncovFileUploadService.getNcovFileUploadByType(serviceType, dataType);
				String fileId = fileUpload.getFilePath();
				fileApi.delete(fileId);
				
				//再根据类型数据构造excel文件
				ExportParams exportParams = new ExportParams(null,regionType==1?"全国省份数据":"全省各市数据");
		        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, NcovRealtimeVo.class, list);
		        
		        ByteArrayOutputStream bos = new ByteArrayOutputStream();
		        workbook.write(bos);
		        byte[] data = bos.toByteArray();
		        
		        FileVo fileVo = new FileVo();
		        fileVo.setFileName("警务云首页实时数据-"+workbook.getSheetName(0)+".xls");
		        fileVo.setFileByte(data);
		        fileVo.setBusinessKey(NcovFileupload.NCOV_FILE_TYPE);
		        fileVo.setBusinessTag("警务云首页实时数据-"+workbook.getSheetName(0)+".xls");
		        fileVo.setFileSize(Long.valueOf(data.length));
		        fileVo.setFileType("application/vnd.ms-excel");
		        
		        //再上传到文件服务器
		        String filePath = fileApi.uploadByte(fileVo);
		        fileUpload.setFilePath(filePath);
		        ncovFileUploadService.updateFileUpload(fileUpload);
	            return fileAccessPath+filePath;
	        } catch (IOException e) {
	        	throw new CustomException(CommonCode.EXPORT_FAIL);
	        }
		}else {
			throw new CustomException(CommonCode.EXPORT_FAIL);
		}
	}
}
