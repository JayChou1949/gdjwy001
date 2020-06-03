package com.upd.hwcloud.service.dm;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.dto.VmGeneralExport;
import com.upd.hwcloud.bean.entity.dm.CloudVm;
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
public interface ICloudVmService extends IService<CloudVm> {

    R vmDetail(String name);

    Page<CloudVm> getPage(Page<CloudVm> page,String name,String type,String orderType,String cloud);

    List<CloudVm> getList(String name);


    List<CloudVm> vmTop5(String name,String type, String orderType);

    List<String> poorAreaApp(String name);

    List<String> businessUnit1List();

    List<VmGeneralExport> exportByAppName(String appName);

    R areaAppCount();


    R policeNames();


    R AreaNames();

}
