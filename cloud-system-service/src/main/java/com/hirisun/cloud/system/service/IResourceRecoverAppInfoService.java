package com.hirisun.cloud.system.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.impl.vo.ImplRequestVo;
import com.hirisun.cloud.model.system.ResourceRecoverSubmitVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.FallBackVO;
import com.hirisun.cloud.system.bean.ResourceRecoverAppInfo;

/**
 * 资源回收申请信息 服务类
 */
public interface IResourceRecoverAppInfoService extends IService<ResourceRecoverAppInfo> {

    /**
     * 申请提交
     * @param user 当前用户
     * @param submit 提交请求体
     * @return R
     */
    ResponseResult submit(UserVO user, ResourceRecoverSubmitVo submit);

    /**
     * 初始化流程
     * @return R
     */
    ResponseResult initWorkFlow();

    /**
     * 详情
     * @param id 申请单ID
     * @return map
     */
    Map<String,Object> detail(String id,UserVO user);

    /**
     * 审批
     * @param user 当前用户
     * @param vo 流转VO
     * @return R
     */
    ResponseResult approve(UserVO user, FallBackVO vo);


    /**
     * 分页
     * @param page 分页对象
     * @param name 被回收人姓名
     * @param orderNum 订单号 1 待审核 2 待实施 3使用中
     * @param status 状态
     * @return
     */
    IPage<ResourceRecoverAppInfo> getPage(IPage<ResourceRecoverAppInfo> page,UserVO user,String name, String orderNum,String status);


    ResponseResult impl(UserVO user, String id,  String modelId, String activityId, @RequestBody ImplRequestVo implRequest) throws Exception;

    /**
     * 删除
     * @param id 订单ID
     * @return
     */
    ResponseResult delete(String id);

    void queryUntreatedRecover();




}
