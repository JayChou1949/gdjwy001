package com.hirisun.cloud.order.service.recover;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.system.ResourceRecoverResponseVo;
import com.hirisun.cloud.order.bean.recover.ResourceRecover;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 资源回收列表 服务类
 */
public interface IResourceRecoverService extends IService<ResourceRecover> {

	
	void delete(String applicant, String phone, String importTime);
	
    void importData(MultipartFile file,String idCard);

    IPage<ResourceRecoverResponseVo> getGroupPage(IPage<ResourceRecoverResponseVo> page,@Param("p") Map<String,String> params);

}
