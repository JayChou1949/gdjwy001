package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.SaasRecoverInfo;
import com.upd.hwcloud.bean.entity.User;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangbao
 * @since 2019-10-24
 */
public interface ISaasRecoverInfoService extends IService<SaasRecoverInfo> {

    /**
     * 表单中用户当前在用服务
     * @param id 回收申请单 id
     */
    List<SaasRecoverInfo> getUserUseService(String id);


    /**
     * 待办事项
     * @param user
     * @return
     */
    int getTodoCount(User user);

    int getApplicationRecoverTodo(String idCard);

}
