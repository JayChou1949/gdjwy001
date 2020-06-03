package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.dto.IaasZysbExport;
import com.upd.hwcloud.bean.entity.IaasZysb;
import com.upd.hwcloud.bean.entity.IaasZysbXmxx;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.vo.resourceReport.export.ReportExport;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 申请信息表 服务类
 * </p>
 *
 * @author zwb
 * @since 2019-05-22
 */
public interface IIaasZysbService extends IService<IaasZysb> {

    void save(User user, IaasZysb info);

    IaasZysb getDetails(String id);

    IaasZysb getBizData(String id);

    IPage<IaasZysb> getPage(User user, IPage<IaasZysb> page, Map<String, Object> param);

    void update(User user, IaasZysb info);

    void moveOldData();

    List<IaasZysbExport> countExport(Map<String, Object> param);

    List<ReportExport> reportExport(Map<String,Object> param);

}
