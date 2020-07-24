package com.hirisun.cloud.model.ncov.contains;

import java.util.HashMap;
import java.util.Map;

import com.hirisun.cloud.model.ncov.vo.file.FileUploadVo;

public class NcovFileupload {

	public final static String NCOV_FILE_TYPE = "NCOV";
	
	public final static String DAAS_DATA_SHARING = "DATASHARING";
	public final static String DAAS_DATA_MODELING = "DATAMODELING";
	public final static String DAAS_DATA_GOVERNANCE = "DATAGOVERNANCE";
	public final static String DAAS_DATA_ACCESS = "DATAACCESS";
	public final static String DAAS_DATA_SERVICE = "DATASERVICE";
	public final static String IAAS_VM = "IAASVM";
	public final static String IAAS_DESKTOP = "IAASDESKTOP";
	public final static String PAAS_DATA = "PAASDATA";
	public final static String PAAS_OVERVIEW = "PAASOVERVIEW";
	public final static String PAAS_RESOURCE = "PAASRESOURCE";
	public final static String PAAS_APPDETAIL = "PAASAPPDETAIL";
	public final static String REALTIME_PROVICE = "REALTIMEPROVICE";
	public final static String REALTIME_CITY = "REALTIMECITY";
	
	public static  Map<String, FileUploadVo> initUrlData(){
		
		Map<String, FileUploadVo> map = new HashMap<String, FileUploadVo>();
		FileUploadVo dataAccess = new FileUploadVo();
		dataAccess.setDataType(NcovFileupload.DAAS_DATA_ACCESS);
		map.put(NcovFileupload.DAAS_DATA_ACCESS, dataAccess);
		
		FileUploadVo dataGovernance = new FileUploadVo();
		dataGovernance.setDataType(NcovFileupload.DAAS_DATA_GOVERNANCE);
		map.put(NcovFileupload.DAAS_DATA_GOVERNANCE, dataGovernance);
		
		FileUploadVo dataModeling = new FileUploadVo();
		dataModeling.setDataType(NcovFileupload.DAAS_DATA_MODELING);
		map.put(NcovFileupload.DAAS_DATA_MODELING, dataModeling);
		
		FileUploadVo dataService = new FileUploadVo();
		dataService.setDataType(NcovFileupload.DAAS_DATA_SERVICE);
		map.put(NcovFileupload.DAAS_DATA_SERVICE, dataService);
		
		FileUploadVo dataSharing = new FileUploadVo();
		dataSharing.setDataType(NcovFileupload.DAAS_DATA_SHARING);
		map.put(NcovFileupload.DAAS_DATA_SHARING, dataSharing);
		
		FileUploadVo iaasDesktop = new FileUploadVo();
		iaasDesktop.setDataType(NcovFileupload.IAAS_DESKTOP);
		map.put(NcovFileupload.IAAS_DESKTOP, iaasDesktop);
		
		FileUploadVo iaasVm = new FileUploadVo();
		iaasVm.setDataType(NcovFileupload.IAAS_VM);
		map.put(NcovFileupload.IAAS_VM, iaasVm);
		
		FileUploadVo paasData = new FileUploadVo();
		paasData.setDataType(NcovFileupload.PAAS_DATA);
		map.put(NcovFileupload.PAAS_DATA, paasData);
		
		FileUploadVo realtimeProvice = new FileUploadVo();
		realtimeProvice.setDataType(NcovFileupload.REALTIME_PROVICE);
		map.put(NcovFileupload.REALTIME_PROVICE, realtimeProvice);
		
		FileUploadVo realtimeCity = new FileUploadVo();
		realtimeCity.setDataType(NcovFileupload.REALTIME_CITY);
		map.put(NcovFileupload.REALTIME_CITY, realtimeCity);
		
		return map;
	}
	
	
}
