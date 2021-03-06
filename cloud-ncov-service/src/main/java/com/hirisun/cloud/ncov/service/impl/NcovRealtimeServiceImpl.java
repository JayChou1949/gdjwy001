package com.hirisun.cloud.ncov.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hirisun.cloud.redis.service.RedisService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.hirisun.cloud.api.file.FileApi;
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
    private RedisService redisService;
	@Autowired
	private FileApi fileApi;
	@Autowired
	private NcovFileUploadService ncovFileUploadService;
	
	@Value("${file.access.path}")
	private String fileAccessPath;
	
	/**
	 * ???????????? ??????????????????,??????????????????????????????????????????
	 */
	public HomePageNcovRealtimeVo getHomePageNcovRealtimeVo() {
		
		String resString = redisService.get(NcovKey.HOME_PAGE_NCOV_REALTIME);
		if(StringUtils.isNotBlank(resString))return JSON.parseObject(resString,HomePageNcovRealtimeVo.class);
		
		HomePageNcovRealtimeVo vo = setHomePageNcovRealtimeCache();
		return vo;
	}
	
	/**
	 * ??????????????????????????????
	 * @return
	 */
	private HomePageNcovRealtimeVo getHomePageNcovRealtime() {
		
		List<NcovRealtimeVo> allList = ncovRealtimeMapper.findAll();
		//??????????????????
		List<NcovRealtimeVo> provinceList = new ArrayList<NcovRealtimeVo>();
		//??????????????????
		NcovRealtimeVo counturyTotal = new NcovRealtimeVo();
		//????????????????????????
		List<NcovRealtimeVo> cityList = new ArrayList<NcovRealtimeVo>();
		//??????????????????????????????
		NcovRealtimeVo cityTotal = new NcovRealtimeVo();
		
		if(CollectionUtils.isNotEmpty(allList)) {
			
			allList.forEach(vo->{
				
				Integer regionType = vo.getRegionType();
				//??????
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
		//???????????????????????????????????????????????????,?????????????????????
		vo.setCountryTotal(counturyTotal);
		//??????????????????????????????,?????????????????????
		vo.setCityTotal(cityTotal);
		//???????????????????????????????????????????????????????????????
		vo.setProvinceList(provinceList);
		//??????????????????
		vo.setCityList(cityList);
		return vo;
	}

	/**
	 * ????????????,???????????????,??????????????????
	 */
	@Transactional
	public ResponseResult importNcovRealtimeData(List<NcovRealtimeVo> importList)throws CustomException {
		
		ResponseResult result = new ResponseResult(CommonCode.SUCCESS.code(),CommonCode.SUCCESS.msg());
		
		if(CollectionUtils.isNotEmpty(importList)) {
			
			importList.forEach(ncovRealtime->{
				saveOrUpdateNcovRealtime(ncovRealtime);
			});
			//????????????
			setHomePageNcovRealtimeCache();
		}else {
			result.setCode(CommonCode.IMPORT_FAIL.code());
			result.setMsg(CommonCode.IMPORT_FAIL.msg());
		}
		return result;
		
	}

	/**
	 * ??????id????????????
	 */
	public NcovRealtime getNcovRealtimeById(String id) {
		NcovRealtime ncovRealtime = ncovRealtimeMapper.selectById(id);
		return ncovRealtime;
	}

	/**
	 * ?????????????????????
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
	 * ????????????????????????
	 */
	@Transactional
	public ResponseResult editNcovRealtime(NcovRealtimeVo vo) {
		
		ResponseResult result = new ResponseResult(CommonCode.SUCCESS.code(),CommonCode.SUCCESS.msg());
		saveOrUpdateNcovRealtime(vo);
		//????????????
		setHomePageNcovRealtimeCache();
		return result;
	}

	/**
	 *???????????? 
	 */
	public HomePageNcovRealtimeVo setHomePageNcovRealtimeCache() {
		HomePageNcovRealtimeVo vo = getHomePageNcovRealtime();
		redisService.setForPerpetual(NcovKey.HOME_PAGE_NCOV_REALTIME, JSON.toJSONString(vo));
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
				//????????????????????????????????????
				FileUpload fileUpload = ncovFileUploadService.getNcovFileUploadByType(serviceType, dataType);
				String fileId = fileUpload.getFilePath();
				fileApi.delete(fileId);
				
				//???????????????????????????excel??????
				ExportParams exportParams = new ExportParams(null,regionType==1?"??????????????????":"??????????????????");
		        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, NcovRealtimeVo.class, list);
		        
		        //???????????????????????????
		        String filePath = uploadFile(workbook);
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
	
	private String uploadFile(Workbook workbook) throws IOException {
		
		String fileName = "???????????????????????????-"+workbook.getSheetName(0)+".xls";
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        
        FileItem fileItem = new DiskFileItemFactory().createItem("file",
        		"application/vnd.ms-excel", true, fileName);
        
        OutputStream outputStream = fileItem.getOutputStream();
        InputStream inputStream = new ByteArrayInputStream(bos.toByteArray());
        IOUtils.copy(inputStream, outputStream);
        
		MultipartFile multi = new CommonsMultipartFile(fileItem );
		String filePath = fileApi.upload(multi, NcovFileupload.NCOV_FILE_TYPE, fileName);
		return filePath;
	}
	
}
