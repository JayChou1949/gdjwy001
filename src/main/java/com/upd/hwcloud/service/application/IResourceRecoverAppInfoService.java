package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.dto.ImplRequest;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ResourceRecoverAppInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.wfm.FallBackVO;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.resourceRecover.ResourceRecoverSubmit;
import com.upd.hwcloud.common.config.LoginUser;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * <p>
 * 资源回收申请信息 服务类
 * </p>
 *
 * @author yyc
 * @since 2020-05-14
 */
public interface IResourceRecoverAppInfoService extends IService<ResourceRecoverAppInfo> {

    /**
     * 申请提交
     * @param user 当前用户
     * @param submit 提交请求体
     * @return R
     */
    R submit(User user, ResourceRecoverSubmit submit);

    /**
     * 初始化流程
     * @return R
     */
    R initWorkFlow();

    /**
     * 详情
     * @param id 申请单ID
     * @return map
     */
    Map<String,Object> detail(String id,User user);

    /**
     * 审批
     * @param user 当前用户
     * @param vo 流转VO
     * @return R
     */
    R approve(User user, FallBackVO vo);


    /**
     * 分页
     * @param page 分页对象
     * @param name 被回收人姓名
     * @param orderNum 订单号 1 待审核 2 待实施 3使用中
     * @param status 状态
     * @return
     */
    IPage<ResourceRecoverAppInfo> getPage(IPage<ResourceRecoverAppInfo> page,User user,String name, String orderNum,String status);


    R impl(User user, String id,  String modelId, String activityId, @RequestBody ImplRequest implRequest) throws Exception;

    /**
     * 删除
     * @param id 订单ID
     * @return
     */
    R delete(String id);




}
