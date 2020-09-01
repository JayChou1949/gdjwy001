package com.hirisun.cloud.system.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.system.ResourceRecoverResponseVo;
import com.hirisun.cloud.system.bean.ResourceRecover;

/**
 * 资源回收列表 服务类
 */
public interface IResourceRecoverService extends IService<ResourceRecover> {

	
	void delete(String applicant, String phone, String importTime);
	
    void importData(MultipartFile file,String idCard);

    IPage<ResourceRecoverResponseVo> getGroupPage(IPage<ResourceRecoverResponseVo> page,@Param("p") Map<String,String> params);

}
