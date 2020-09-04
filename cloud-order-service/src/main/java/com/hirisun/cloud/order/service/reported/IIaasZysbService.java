package com.hirisun.cloud.order.service.reported;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.epidemic.EpidemicApplication;
import com.hirisun.cloud.order.bean.reported.IaasZysb;
import com.hirisun.cloud.model.export.vo.ReportExportVo;
import com.hirisun.cloud.model.iaas.vo.IaasZysbExportVo;
import com.hirisun.cloud.model.user.UserVO;

import java.util.List;
import java.util.Map;

/**
 * 申请信息表 服务类
 */
public interface IIaasZysbService extends IService<IaasZysb> {

    void save(UserVO user, IaasZysb info);

    IaasZysb getDetails(String id);

    IaasZysb getBizData(String id);

    IPage<IaasZysb> getPage(UserVO user, IPage<IaasZysb> page, Map<String, Object> param);

    void update(UserVO user, IaasZysb info);

//    void moveOldData();
//
//    List<IaasZysbExportVo> countExport(Map<String, Object> param);
//
//    List<ReportExportVo> reportExport(Map<String,Object> param);


    void create(UserVO user, IaasZysb info);

}
