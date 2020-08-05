package com.hirisun.cloud.ncov.service;

import com.hirisun.cloud.model.ncov.vo.daas.DataAccessVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataModelingVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataServiceVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataSharingVo;
import com.hirisun.cloud.model.ncov.vo.daas.HomePageDataVo;

public interface NcovDaasService {

	public HomePageDataVo getHomePageBigData() throws Exception;
	
	public DataAccessVo getDataAccessVo()throws Exception;
	
	public DataGovernanceVo getDataGovernance();
	
	public DataServiceVo getDataService();
	
	public DataModelingVo getDataModeling();
	
	public DataSharingVo getDataSharing();
	
}
