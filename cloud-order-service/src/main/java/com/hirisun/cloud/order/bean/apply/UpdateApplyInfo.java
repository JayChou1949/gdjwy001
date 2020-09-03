package com.hirisun.cloud.order.bean.apply;

import com.hirisun.cloud.model.apply.ApplyInfoVO;
import lombok.Data;

import java.util.List;

/**
 * 更新申请信息
 * @param <S> 用户申请的服务器信息
 */
@Data
public class UpdateApplyInfo<S> {

    /**
     * 审核类型  inner:部门内审核 kx:科信审核
     */
    private String type;
    /**
     * 审核人 id
     */
    private List<String> userIds;
    private ApplyInfo info;

}
