package com.upd.hwcloud.service.webManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Apply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.webManage.Case;

/**
 * <p>
 * 试点应用 服务类
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
public interface IApplyService extends IService<Apply> {

    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    void publish(User user, String id, Integer result, String remark);

    Page<Apply> getPage(Page<Apply> page, User user, Integer status, String name);

    Page<Apply> getApplyByPage(Page<Apply> page);
}
