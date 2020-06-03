package com.upd.hwcloud.service.dm;

import com.upd.hwcloud.bean.entity.dm.CloudResourceLevel1;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.response.R;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yyc
 * @since 2019-08-13
 */
public interface ICloudResourceLevel1Service extends IService<CloudResourceLevel1> {

    R overview();

    List<CloudResourceLevel1> getAll();
}
