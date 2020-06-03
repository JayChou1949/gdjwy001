package com.upd.hwcloud.service.webManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Case;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 典型案例 服务类
 * </p>
 *
 * @author huru
 * @since 2018-10-25
 */
public interface ICaseService extends IService<Case> {

    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    void publish(User user, String id, Integer result, String remark);

    Page<Case> getPage(Page<Case> page,User user,Integer status,String name);

    Page<Case> getCaseByPage(Page<Case> page);

}
