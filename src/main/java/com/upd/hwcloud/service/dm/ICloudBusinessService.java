package com.upd.hwcloud.service.dm;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.dm.CloudBusiness;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.response.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yyc
 * @since 2019-08-13
 */
public interface ICloudBusinessService extends IService<CloudBusiness> {

    R usage(String name);

    Page<CloudBusiness> getPage(Page<CloudBusiness> page,String name,String type,String orderType,String cloud);

}
