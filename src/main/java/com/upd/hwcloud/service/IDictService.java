package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.dto.RedPoint;
import com.upd.hwcloud.bean.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-10-18
 */
public interface IDictService extends IService<Dict> {

    Dict add(Dict dict);
    Dict edit(Dict dict);

    List<Dict> getChildByValue(String value);

    RedPoint getRedPoint();

    List<Dict> getChildByName(String name);

    List<Dict> getTree();

    boolean isBasePaaSService(String id);


    Map<String,String> selectDeepMap(String dictId);
}
