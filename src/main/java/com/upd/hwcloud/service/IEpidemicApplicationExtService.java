package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.entity.EpidemicApplicationExt;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 疫情应用申请信息扩展表 服务类
 * </p>
 *
 * @author wuc
 * @since 2020-02-27
 */
public interface IEpidemicApplicationExtService extends IService<EpidemicApplicationExt> {

    List<EpidemicApplicationExt> getListByMasterId(String id);

}
