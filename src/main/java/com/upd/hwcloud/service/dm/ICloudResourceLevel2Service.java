package com.upd.hwcloud.service.dm;

import com.upd.hwcloud.bean.entity.dm.CloudResourceLevel2;
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
public interface ICloudResourceLevel2Service extends IService<CloudResourceLevel2> {
    R overviewLevel2(String name);

    List<String> resourceLevelNames();

    R originOverview(String name);

    List<CloudResourceLevel2> getAll();
}
