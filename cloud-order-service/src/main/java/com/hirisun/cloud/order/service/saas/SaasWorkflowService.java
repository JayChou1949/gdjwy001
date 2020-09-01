package com.hirisun.cloud.order.service.saas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.saas.vo.SaasApplicationVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.vo.ApproveVO;
import com.hirisun.cloud.order.vo.ImplRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * SaaS资源申请原始信息表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-31
 */
public interface SaasWorkflowService {
    public QueryResponseResult<SaasApplicationVO> detail(String id);

    public QueryResponseResult adviser(UserVO user, FallBackVO vo);

    public QueryResponseResult approve(UserVO user, FallBackVO vo);

    public QueryResponseResult<ImplRequest> saveImpl(UserVO user, String id, String nodeId, String activityId, ImplRequest implRequest) throws Exception;

    public QueryResponseResult add(UserVO user, ApproveVO approveVO);

    public void userExport(String id, HttpServletRequest request, HttpServletResponse response);

}
