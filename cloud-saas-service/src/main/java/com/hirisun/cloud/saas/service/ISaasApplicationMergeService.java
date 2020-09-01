package com.hirisun.cloud.saas.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.saas.vo.SaasApplicationMergeVO;
import com.hirisun.cloud.model.saas.vo.SaasOrderTotalVo;
import com.hirisun.cloud.model.saas.vo.SaasTotalVo;
import com.hirisun.cloud.model.saas.vo.SaasUseTotalVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.saas.bean.SaasApplicationMerge;

/**
 * SaaS资源申请合并信息表 服务类
 */
public interface ISaasApplicationMergeService extends IService<SaasApplicationMerge> {

    /**
     * 合并单子
     * @param ids 待合并单子的id,多个使用,分隔
     */
    SaasApplicationMerge merge(String userId, String ids);

    void update(String userId, SaasApplicationMerge info);

    SaasApplicationMerge getDetails(String id);

    void deleteById(String userId, String id);

    Page<SaasApplicationMergeVO> getFlowPage(String userId, Map<String, Object> param);

    Page<SaasApplicationMergeVO> getFlowPageWithServiceName(String userId, Map<String, Object> param);

    List<SaasTotalVo> saasTotal(String areas, String policeCategory, Map<String, String> params);

    List<SaasOrderTotalVo> saasOrderTotal(String areas, String policeCategory, Map<String, String> params);

    List<SaasOrderTotalVo> saasOrderQuery(String areas, String policeCategory, Map<String, String> params);

    List<SaasUseTotalVo> saasUseTotal(String areas, String policeCategory, Map<String, String> params);

    void downloadFile(HttpServletResponse response) throws IOException;
}
