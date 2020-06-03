package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.application.ResourceRecover;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.vo.resourceRecover.ResourceRecoverResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资源回收列表 服务类
 * </p>
 *
 * @author yyc
 * @since 2020-05-14
 */
public interface IResourceRecoverService extends IService<ResourceRecover> {

    void importData(MultipartFile file,String idCard);

    IPage<ResourceRecoverResponse> getGroupPage(IPage<ResourceRecoverResponse> page,@Param("p") Map<String,String> params);

}
