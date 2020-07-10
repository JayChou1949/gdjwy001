package com.hirisun.cloud.model.ncov.contains;

import java.util.HashMap;
import java.util.Map;

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
	public final static String REALTIME = "REALTIME";
	
	public static  Map<String, String> initUrlData(){
		
		Map<String, String> map = new HashMap<String, String>();
		map.put(NcovFileupload.DAAS_DATA_ACCESS, "");
		map.put(NcovFileupload.DAAS_DATA_GOVERNANCE, "");
		map.put(NcovFileupload.DAAS_DATA_MODELING, "");
		map.put(NcovFileupload.DAAS_DATA_SERVICE, "");
		map.put(NcovFileupload.DAAS_DATA_SHARING, "");
		map.put(NcovFileupload.IAAS_DESKTOP, "");
		map.put(NcovFileupload.IAAS_VM, "");
		map.put(NcovFileupload.PAAS_DATA, "");
		map.put(NcovFileupload.REALTIME, "");
		return map;
	}
}
