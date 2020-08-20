package com.hirisun.cloud.order.service.apply;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.apply.ApplyInfo;
import com.hirisun.cloud.order.continer.IImplHandler;

import java.util.Map;

/**
 * <p>
 * 申请信息表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-08
 */
public interface ApplyInfoService extends IService<ApplyInfo> {

    /**
     * 获取申请工单记录
     */
    Page<ApplyInfo> getPage(Page<ApplyInfo> page, UserVO user, Map map);

    /**
     * 逻辑删除申请审批工单
     * @param user
     * @param id 工单id
     */
    void deleteById(UserVO user, String id);

    /**
     * 工单审批批准流转
     * @param user
     * @param vo
     * @return
     */
    QueryResponseResult approve(UserVO user, FallBackVO vo);

    /**
     * 工单审批业务实施
     * @param user
     * @param param
     * @param implHandler
     * @param modelId
     * @param <I>
     * @throws Exception
     */
    public <I> void saveImpl(UserVO user, Map<String, Object> param, IImplHandler<I> implHandler, String modelId) throws Exception;

}
