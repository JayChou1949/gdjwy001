package com.upd.hwcloud.service.workbench;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.workbench.PublicStatistics;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;
import com.upd.hwcloud.bean.vo.workbench.ResourceOverviewVO;
import com.upd.hwcloud.bean.vo.workbench.SaasLvThree;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 工作台統計
 */
public interface Workbench<I> {

    /**
     * 工作台资源总览统计
     * @param user
     * @param queryVO
     * @return
     */
    List<GeneralDTO> getUseResource(User user, QueryVO queryVO);


    /**
     * 服务发布统计
     * @param user
     * @param queryVO
     * @return
     */
    PublicStatistics doPublicStatistics(User user,QueryVO queryVO);


    List<GeneralDTO> getServiceOfSaas(String appName);

    /**
     * SaaS三级页面除了应用应用信息的各层数据
     * @param name
     * @return
     */
    R serviceOfSaas (String name);

    List<I> getServiceOfSaasStatistics(String name);

    /**
     * SaaS三级页面各层数据到导出
     * @param name
     * @param request
     * @param response
     * @throws Exception
     */
    void lvThreePageExport(String name, HttpServletRequest request, HttpServletResponse response) throws  Exception;


    /**
     * 资源概览分页
     * @param pageNum
     * @param pageSize
     * @param user
     * @param serviceName
     * @param appName
     * @return
     */
    IPage<ResourceOverviewVO> resourcePage(Long pageNum,Long pageSize,User user,String serviceName,String appName);

    HashMap<String,Long> resourceOverview(User user);


    /**
     * 我的申请单分页
     * @param pageNum
     * @param pageSize
     * @param queryVO
     * @param status
     * @param user
     * @return
     */
    IPage<I> applyPage(Integer pageNum,Integer pageSize,QueryVO queryVO,String status,User user);




}
