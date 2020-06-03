package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.entity.IaasFunCha;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * IAAS功能特点 服务类
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
public interface IIaasFunChaService extends IService<IaasFunCha> {

    void save(List<IaasFunCha> funChara, String masterId);
}
