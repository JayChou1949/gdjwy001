package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.IaasTxyfwImpl;
import com.upd.hwcloud.bean.entity.application.IaasYzmImpl;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 弹性云服务实施信息表 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-12-04
 */
public interface IIaasTxyfwImplService extends IService<IaasTxyfwImpl>, IImplHandler<IaasTxyfwImpl> {

    Map<String,Object> ncovOverview(String startTime);


}
