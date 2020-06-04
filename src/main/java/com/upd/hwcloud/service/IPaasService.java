package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.dto.OnLineServiceDto;
import com.upd.hwcloud.bean.dto.apig.ServiceReturnBean;
import com.upd.hwcloud.bean.entity.Paas;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.upd.hwcloud.bean.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * PaaS 表 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
public interface IPaasService extends IService<Paas>,SortService  {

    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    void publish(User user, String id, Integer result, String remark);

    Map<String, Object> getFrontData();

    IPage<Paas> getPage(IPage<Paas> page, User user, Integer status, String name, String subType);

    Paas getDetail(User user, String id);

    Set<String> getLabel(String typeId);

    IPage<Paas> getMorePage(IPage<Paas> page, String typeId, String keyword);

    List<OnLineServiceDto> getOnlineService();

    List<Map<String,Object>> getLabelWithCount(String typeId);

    List<Paas> getOneClickPage(String typeId, String label, String keyword);

    void savePaas(Paas paas);

    void updatePaas(Paas paas);

    List<Paas> search(String keyword);
    boolean servicePublish2PaaS(ServicePublish servicePublish,ServiceReturnBean returnBean);

    void hotfix();
}
