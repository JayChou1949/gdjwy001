package com.upd.hwcloud.controller.backend.application;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.Dict;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.ThreePartyInterface;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.DateUtil;
import com.upd.hwcloud.service.IDictService;
import com.upd.hwcloud.service.ISaasApplicationService;
import com.upd.hwcloud.service.ISaasService;
import com.upd.hwcloud.service.application.SassAppUseStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>
 * 应用访问统计表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-06-29
 */
@Api(description = "应用运营统计")
@RestController
@RequestMapping("/sassAppUseStatistics")
public class SassAppUseStatisticsController {

    @Autowired
    private ISaasApplicationService saasApplicationService;

    @Autowired
    private SassAppUseStatisticsService sassAppUseStatisticsService;

    @Autowired
    private IDictService dictService;

    @Autowired
    private ISaasService saasService;

    @ApiOperation("应用运营统计总览数量")//SaaS应用人员使用状况统计表
    @RequestMapping(value = "/appOperateCount", method = RequestMethod.GET)
    public R appOperateCount(@LoginUser User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取应用总数
//        HashMap<String,Long> appCount = saasApplicationService.getWorkbenchOverview(user);//{num:1}
        Dict dict = dictService.getOne(new QueryWrapper<Dict>().lambda()
                .eq(Dict::getLvl, 1)
                .eq(Dict::getpValue, "saasType")
                .eq(Dict::getName, "通用应用"));
        System.out.println(dict.getId());
        if(dict==null){
            return R.error("没有字典信息");
        }
        int appCount=saasService.count(new QueryWrapper<Saas>().lambda()
                .eq(Saas::getSubType,dict.getId())
                .eq(Saas::getStatus,2)//已上线
        );
        //获取权限用户数，应用申请单内
        Map authMap=new HashMap();
        authMap.put("subType",dict.getId());
        Integer authCount=sassAppUseStatisticsService.countByAppAuth(authMap);
        Long allCount=sassAppUseStatisticsService.allCount(authMap);

        HashMap<String,Long> userCount=new HashMap<>();
//        userCount.put("USERCOUNT",10L);
        //获取应用使用量，由安审平台提供接口，传身份证信息获取用户使用量，
        //确定所需字段
        HashMap<String,Long> appUseCount=new HashMap<>();
//        appUseCount.put("APPUSECOUNT",999999L);
        Map map=new HashMap();
        map.put("appCount",appCount);
        map.put("userCount",authCount);
        map.put("appUseCount",allCount);
        return R.ok(map);

    }
    @ApiOperation("应用运营统计报表详细")
    @RequestMapping(value = "/appOperateDetail", method = RequestMethod.GET)
    public R appOperateDetail(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
                                     String submitStartDate, String submitEndDate) throws Exception {

        if (StringUtils.isEmpty(submitStartDate)) {
            submitStartDate= DateUtil.dateAdd(new Date(),-30,"yyyy-MM-dd");
        }
        if (StringUtils.isEmpty(submitEndDate)) {
            submitEndDate = DateUtil.formateDate(new Date(), "yyyy-MM-dd");
        }
        Dict dict = dictService.getOne(new QueryWrapper<Dict>().lambda()
                .eq(Dict::getLvl, 1)
                .eq(Dict::getpValue, "saasType")
                .eq(Dict::getName, "通用应用"));
        System.out.println(dict.getId());
        if(dict==null){
            return R.error("没有字典信息");
        }
        Map paramMap = new HashMap();
        paramMap.put("subType", dict.getId());
        paramMap.put("userId", user.getIdcard());
        paramMap.put("startDate",submitStartDate);
        paramMap.put("endDate", submitEndDate);
        //应用访问次数趋势图
        List<Map> queryUseCount=sassAppUseStatisticsService.queryUseCount(paramMap);
        //拆分x\y轴数据
        LinkedList queryUseCountX=new LinkedList();
        LinkedList queryUseCountY=new LinkedList();
        queryUseCount.forEach(item->{
            queryUseCountX.add(item.get("ADDTIME"));
            queryUseCountY.add(item.get("SCOUNT"));
        });

        //地市用户使用总量
        List<Map> queryAreaUseCount=sassAppUseStatisticsService.queryAreaUseCount(paramMap);
        //警种使用总量
        List<Map> queryPoliceUseCount=sassAppUseStatisticsService.queryPoliceUseCount(paramMap);
        //应用使用量
        List<Map> queryAppUseCount=sassAppUseStatisticsService.queryAppUseCount(paramMap);
        //拆分x\y轴数据
        LinkedList queryAppUseCountX=new LinkedList();
        LinkedList queryAppUseCountY=new LinkedList();
        queryAppUseCount.forEach(item->{
            queryAppUseCountX.add(item.get("APPNAME"));
            queryAppUseCountY.add(item.get("SCOUNT"));
        });
        //地市用户数量
        List<Map> queryAreaUserCount=sassAppUseStatisticsService.queryAreaUserCount(paramMap);
        List<Map> queryAreaUserAvgCount=sassAppUseStatisticsService.queryAreaUserAvgCount(paramMap);
        //拆分x\y轴数据
        LinkedList queryAreaUserCountX=new LinkedList();
        LinkedList queryAreaUserCountY=new LinkedList();
        LinkedList queryAreaAVGCountX=new LinkedList();
        LinkedList queryAreaAVGCountY=new LinkedList();
        queryAreaUserCount.forEach(item->{
            queryAreaUserCountX.add(item.get("AREAS"));
            queryAreaUserCountY.add(item.get("SCOUNT"));
//            queryAreaAVGCountX.add(item.get("AREAS"));
//            queryAreaAVGCountY.add(item.get("AVGCOUNT"));
        });
        queryAreaUserAvgCount.forEach(item->{
//            queryAreaUserCountX.add(item.get("AREAS"));
//            queryAreaUserCountY.add(item.get("SCOUNT"));
            queryAreaAVGCountX.add(item.get("AREAS"));
            queryAreaAVGCountY.add(item.get("AVGCOUNT"));
        });
        //警种用户数量
        List<Map> queryPoliceUserCount=sassAppUseStatisticsService.queryPoliceUserCount(paramMap);
        List<Map> queryPoliceUserAvgCount=sassAppUseStatisticsService.queryPoliceUserAvgCount(paramMap);
        //拆分x\y轴数据
        LinkedList queryPoliceUserCountX=new LinkedList();
        LinkedList queryPoliceUserCountY=new LinkedList();
        LinkedList queryPoliceAVGCountX=new LinkedList();
        LinkedList queryPoliceAVGCountY=new LinkedList();
        queryPoliceUserCount.forEach(item->{
            queryPoliceUserCountX.add(item.get("POLICE_CATEGORY"));
            queryPoliceUserCountY.add(item.get("SCOUNT"));
//            queryPoliceAVGCountX.add(item.get("POLICE_CATEGORY"));
//            queryPoliceAVGCountY.add(item.get("AVGCOUNT"));
        });
        queryPoliceUserAvgCount.forEach(item->{
//            queryPoliceUserCountX.add(item.get("POLICE_CATEGORY"));
//            queryPoliceUserCountY.add(item.get("SCOUNT"));
            queryPoliceAVGCountX.add(item.get("POLICE_CATEGORY"));
            queryPoliceAVGCountY.add(item.get("AVGCOUNT"));
        });
        //地市人均使用量
//        List<Map> queryAreaUserCount2=sassAppUseStatisticsService.queryAreaUserCount(user.getIdcard());
        //警种人均使用量
//        List<Map> queryPoliceUserCount2=sassAppUseStatisticsService.queryPoliceUserCount(user.getIdcard());
//        list.forEach(System.out::println);
        Map map=new HashMap();
        map.put("queryUseCount",queryUseCount);
        map.put("queryUseCountX",queryUseCountX);//
        map.put("queryUseCountY",queryUseCountY);//
        map.put("queryAreaUseCount",queryAreaUseCount);
        map.put("queryPoliceUseCount",queryPoliceUseCount);
        map.put("queryAppUseCount",queryAppUseCount);
        map.put("queryAppUseCountX",queryAppUseCountX);//
        map.put("queryAppUseCountY",queryAppUseCountY);//
        map.put("queryAreaUserCount",queryAreaUserCount);
        map.put("queryAreaUserCountX",queryAreaUserCountX);//
        map.put("queryAreaUserCountY",queryAreaUserCountY);//
        map.put("queryPoliceUserCount",queryPoliceUserCount);
        map.put("queryPoliceUserCountX",queryPoliceUserCountX);//
        map.put("queryPoliceUserCountY",queryPoliceUserCountY);//
        map.put("queryAreaAVGCountX",queryAreaAVGCountX);//
        map.put("queryAreaAVGCountY",queryAreaAVGCountY);//
        map.put("queryPoliceAVGCountX",queryPoliceAVGCountX);//
        map.put("queryPoliceAVGCountY",queryPoliceAVGCountY);//
        return R.ok(map);
    }
    @ApiOperation("导入应用使用数据")
    @RequestMapping(value = "/import",method = RequestMethod.POST)
    public R importData(@LoginUser User user, @RequestParam(value = "file") MultipartFile file){
        sassAppUseStatisticsService.importData(file,user.getIdcard());
        return R.ok();
    }
}

