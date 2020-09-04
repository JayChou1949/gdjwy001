package com.hirisun.cloud.system.service;

import com.hirisun.cloud.model.system.SysDictVO;
import com.hirisun.cloud.system.bean.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-29
 */
public interface SysDictService extends IService<SysDict> {

    List<SysDict> getSysDictList();

    void saveOrUpdateDict(SysDict sysDict);

    void removeDict(String id);

    void syncSysDictData();

    boolean isBasePaaSService(String id);
    
    SysDictVO feignGetById(String id);
    
    List<SysDictVO> findDictByValue(String value);
}
