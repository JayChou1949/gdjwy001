package com.upd.hwcloud.service.workbench.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.WorkbenchApplyStatus;
import com.upd.hwcloud.bean.entity.Application;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class CommonHandler {

    /**
     * 判斷是否租戶管理員
     * @param user
     * @return
     */
     public  static boolean isTenantManager(User user){
         return user.getType() == 20;
     }

    /**
     * 判斷是否租戶
     * @param user
     * @return
     */
    public  static boolean isPolice(User user){
        return "省厅".equals(user.getTenantArea());
    }


    /**
     * 地市名去掉市
     * @param areaName
     * @return
     */
    public static  String splitArea(String areaName){
        if(StringUtils.isNotEmpty(areaName)){
            String[] strings = areaName.split("市");
            return strings[0];
        }
        return null;


    }

    /**
     * % name %
     * @param name
     * @return
     */
    public static String dealNameforQuery(String name){
        if(StringUtils.isNotBlank(name)){
            name = name.trim();
            StringBuilder sb = new StringBuilder().append("%").append(name).append("%");
            return sb.toString();
        }
        return null;
    }

    /**
     * 等值查询=>trim
     * @param name
     * @return
     */
    public static  String dealNameForEqualQuery(String name){
        if(StringUtils.isNotBlank(name)){
            return name.trim();
        }
        return null;
    }


    /**
     * 构造查询参数(IaaS,PaaS,DaaS)
     * @param vo
     * @param user
     * @param status
     * @return
     */
    public static Map<String,Object> handlerOfQueryVO(QueryVO vo, User user, String status, Long resourceType){
        Map<String,Object> param = Maps.newHashMap();
        //申请人名或订单号
        //vo.setCreatorName(CommonHandler.dealNameforQuery(vo.getCreatorName()));
        if(vo.getKeyWord() != null){
            vo.setKeyWord(vo.getKeyWord().trim());
        }
        //资源类型转为对应编码
        vo.setType(resourceType);
        vo.setStatus(status);
        vo.setHandler(user.getIdcard());
        /*if (ApplicationInfoStatus.SHOPPING_CART.getCode().equals(status)
                || ApplicationInfoStatus.SHOPPING_CART_DEL.getCode().equals(status)) {
            vo.setStatus(null); // 不能查购物车的数据
        }*/
        if (!WorkbenchApplyStatus.REVIEW_STATUS.getCode().equals(status)
                && !WorkbenchApplyStatus.IMPL_STATUS.getCode().equals(status)) {
            vo.setProcessType(null);
        }
        if(CommonHandler.isTenantManager(user)){
            vo.setPoliceCategory(user.getTenantPoliceCategory());
            vo.setArea(user.getTenantArea());
        }else{
            //此处为处理人
            vo.setCreator(user.getIdcard());
        }
        param.put("queryVO",vo);
        return  param;
    }

    /**
     * 构造查询参数(服务发布,应用注册)
     * @param vo
     * @param user
     * @return
     */
    public static Map<String,Object> handlerOfQueryVO(QueryVO vo, User user){
        Map<String,Object> param = Maps.newHashMap();
        //申请人名或订单号
        //vo.setCreatorName(CommonHandler.dealNameforQuery(vo.getCreatorName()));
        if(vo.getKeyWord() != null){
            vo.setKeyWord(vo.getKeyWord().trim());
        }
        /*if (ApplicationInfoStatus.SHOPPING_CART.getCode().equals(status)
                || ApplicationInfoStatus.SHOPPING_CART_DEL.getCode().equals(status)) {
            vo.setStatus(null); // 不能查购物车的数据
        }*/
        if (!WorkbenchApplyStatus.REVIEW_STATUS.getCode().equals(vo.getStatus())
                && !WorkbenchApplyStatus.IMPL_STATUS.getCode().equals(vo.getStatus())) {
            vo.setProcessType(null);
        }

        vo.setHandler(user.getIdcard());

        if(CommonHandler.isTenantManager(user)){
            vo.setPoliceCategory(user.getTenantPoliceCategory());
            vo.setArea(user.getTenantArea());
        }else{
            //此处为处理人
            vo.setCreator(user.getIdcard());
        }
        param.put("queryVO",vo);
        return  param;
    }


    /**
     * 带Keyword的处理
     * @param vo
     * @param user
     * @return
     */
    public static Map<String,Object> handlerOfKeyWord(QueryVO vo,User user){
        Map<String ,Object> param = Maps.newHashMap();

        if(StringUtils.isNotBlank(vo.getKeyWord())){
            vo.setKeyWord(dealNameforQuery(vo.getKeyWord()));
        }

        if(StringUtils.isNotBlank(vo.getCreatorName())){
            vo.setCreatorName(vo.getCreatorName());
        }

        if(CommonHandler.isTenantManager(user)){
            vo.setPoliceCategory(user.getTenantPoliceCategory());
            vo.setArea(user.getTenantArea());
        }else{
            //此处为处理人
            vo.setCreator(user.getIdcard());
        }
        param.put("queryVO",vo);
        return  param;

    }


    /**
     * 基础处理查询参数
     * @param user
     * @param
     * @return
     */
    public static Map<String,Object> handlerNewPageParam(User user,String serviceName,String appName){
        Map<String,Object> param = Maps.newHashMap();
        QueryVO vo = new QueryVO();
        if(StringUtils.isNotEmpty(serviceName)){
            vo.setServiceName(CommonHandler.dealNameforQuery(serviceName));
        }
        if(StringUtils.isNotEmpty(appName)){
            vo.setKeyWord(CommonHandler.dealNameforQuery(appName));
        }
        if(CommonHandler.isTenantManager(user)){
            vo.setPoliceCategory(user.getTenantPoliceCategory());
            vo.setArea(user.getTenantArea());
        }else{
            //此处为处理人
            vo.setCreator(user.getIdcard());
        }
        param.put("queryVO",vo);
        return  param;
    }

    public static Map<String,Object> handlerUserParam(User user){
        Map<String,Object> param = Maps.newHashMap();
        QueryVO vo = new QueryVO();
        if(CommonHandler.isTenantManager(user)){
            vo.setPoliceCategory(user.getTenantPoliceCategory());
            vo.setArea(user.getTenantArea());
        }else{
            //此处为处理人
            vo.setCreator(user.getIdcard());
        }
        param.put("queryVO",vo);
        return  param;
    }

    public static Map<String,Long> handlerOfOverview(Map<String,Long> res){
        if(res == null){
            res.put("NUM",0L);
            res.put("SUMCOUNT",0L);
            res.put("APPNUM",0L);
        }else{
            if(!res.containsKey("SUMCOUNT")){
                res.put("SUMCOUNT",0L);
            }
            if(!res.containsKey("NUM")){
                res.put("NUM",0L);
            }
            if(!res.containsKey("APPNUM")){
                res.put("APPNUM",0L);
            }
        }

        return res;
    }


    /**
     * 检查是否有删除权限
     * @param records
     * @param user
     */
    public static void checkDeleteEnable(List<ApplicationInfo> records,User user){
        if (records != null && !records.isEmpty()) {
            for (ApplicationInfo record : records) {
                ApplicationInfoStatus ais = ApplicationInfoStatus.codeOf(record.getStatus());
                // 判断是否能删除
                if (ais != ApplicationInfoStatus.DELETE
                        && Objects.equals(user.getIdcard(), record.getCreator())) {
                    record.setCanDelete(true);
                }
            }
        }
    }
}
