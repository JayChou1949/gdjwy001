package com.hirisun.cloud.order.service.apply;

import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.saas.vo.SaasApplicationVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.vo.ApproveVO;
import com.hirisun.cloud.order.vo.ImplRequest;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-31
 */
public interface ApplyService<T> {

    public QueryResponseResult adviser(UserVO user, FallBackVO vo);

    public QueryResponseResult add(UserVO user, ApproveVO approveVO);

    public QueryResponseResult forward(UserVO user, String activityId, String userIds);

    public QueryResponseResult submit(T order, UserVO user, String id, String type, String userIds);

    public QueryResponseResult reject(T info, UserVO user, FallBackVO vo);

    public QueryResponseResult deleteById(T info, UserVO user);

    /**
     * 流程开始执行,从申请到下一环节
     *
     * @param info
     * @param user
     */
    public void workflowStart(T info, UserVO user);

    /**
     * 获取工单详情
     *
     * @param info
     * @return
     */
    public QueryResponseResult detail(T info);

    /**
     * 保存实施审批记录,更新工单状态
     *
     * @param info
     * @param user
     * @param implRequest
     */
    public void saveImpl(T info, UserVO user, ImplRequest implRequest);

    public QueryResponseResult approve(T info, UserVO user, FallBackVO vo);

    /**
     * 终止流程,更改工单状态
     *
     * @param info
     * @param user
     * @return
     */
    public QueryResponseResult termination(T info, UserVO user);


}
