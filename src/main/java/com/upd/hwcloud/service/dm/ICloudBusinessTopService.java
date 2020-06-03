package com.upd.hwcloud.service.dm;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.dm.CloudBusinessTop;
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
public interface ICloudBusinessTopService extends IService<CloudBusinessTop> {
    /**
     * 各警种应用数
     * @return
     */
    R policeApplication();

    /**
     * 警种应用数统计
     */
    R applicationAccount();

    /**
     * 警种应用使用率
     * @param name
     * @return
     */


    R policeApplicationUsage(String name);


    R policeApplicationCount();

    R areaApplicationCount();

    R policeHasAppName();

    R areaHasAppName();

    R areaApplication();

    List<String> getAppListByName(String name);

    Page<CloudBusinessTop> getPage(Page<CloudBusinessTop> page,String name,String type,String orderType,String cloud);


}
