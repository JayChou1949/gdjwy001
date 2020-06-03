package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.IaasNew;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * IaaS 表 服务类
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
public interface IIaasNewService extends IService<IaasNew> ,SortService{
    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    void publish(User user, String id, Integer result, String remark);

    IPage<IaasNew> getPage(IPage<IaasNew> page, User user, Integer status, String name, String subType);
    IaasNew getDetail(User user, String id);
    List<Map<String,Object>> getLabelWithCount();
    List<IaasNew> getOneClickPage(String label,String keyword);
    public List<IaasNew> getFrontPage();
}
