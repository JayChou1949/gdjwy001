package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.application.InnerReviewUser;

import java.util.List;

/**
 * <p>
 * 部门内审核关联表 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-12-19
 */
public interface IInnerReviewUserService extends IService<InnerReviewUser> {

    /**
     * 插入审核人(先删除,后插入)
     * @param id 申请信息 id
     * @param userIds 审核人
     */
    void infoUser(String id, List<String> userIds);

    /**
     * 通过申请信息 id,删除审核人
     * @param id
     */
    void removeByAppInfoId(String id);

    /**
     * 获取列表
     * @param appInfoId 申请信息 id
     * @param userId 身份证号
     * @return
     */
    List<InnerReviewUser> getList(String appInfoId, String userId);

    /**
     * 清除加办/转发记录数据
     * @param resourceType 服务类型
     */
    void deleteAddAndForwardByResourceType(Long resourceType);

    /**
     * 除加办/转发记录数据
     * @param serviceId 服务 id
     */
    void deleteAddAndForwardByServiceId(String serviceId);

}
