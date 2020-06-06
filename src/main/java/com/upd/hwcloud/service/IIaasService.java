package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.Iaas;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.common.utils.easypoi.ExportView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * IaaS 表 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
public interface IIaasService extends IService<Iaas> ,SortService {

    void hotfix();

    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    void publish(User user, String id, Integer result, String remark);

    IPage<Iaas> getPage(IPage<Iaas> page, User user, Integer status, String name, String subType);

    Iaas getDetail(User user, String id);

    List<Map<String,Object>> getLabelWithCount();

    List<Iaas> getOneClickPage(String label, String keyword);

    /***********************************IAAS业务办理工单 start****************************************************/

    /**
     * 获取弹性云数据
     * @return
     */
    ExportView getTanxingyunData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException;

    /**
     * 获取变更数据
     * @return
     */
    ExportView getBiangengData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException;

    /**
     * 获取大数据组件数据
     * @return
     */
    ExportView getDashujuData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException;

    /**
     * 获取桌面云数据
     * @return
     */
    ExportView getZhuomianyunData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException;

    /**
     * 获取对象存储数据
     * @return
     */
    ExportView getDuixiangCCData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException;

    /**
     * 获取文件存储数据
     * @return
     */
    ExportView getWenjianCCData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException;

    /**
     * 获取弹性负载均衡数据
     * @return
     */
    ExportView getTXFuzaijunhengData(Map<String, Object> params) throws InvocationTargetException, IllegalAccessException;

    /***********************************IAAS业务办理工单 end****************************************************/

}
