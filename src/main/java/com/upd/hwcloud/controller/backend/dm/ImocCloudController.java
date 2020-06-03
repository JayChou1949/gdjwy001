package com.upd.hwcloud.controller.backend.dm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.entity.Dict;
import com.upd.hwcloud.bean.entity.dm.*;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IDictService;
import com.upd.hwcloud.service.dm.*;
import com.upd.hwcloud.service.dm.impl.CloudVmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: Iaas二三级页面
 * @author: yyc
 * @date: 2019-08-13 14:23
 **/
@RestController
@RequestMapping("/iMoc/cloud/")
public class ImocCloudController {
    @Autowired
    private ICloudBusinessService cloudBusinessService;

    @Autowired
    private ICloudBusinessTopService cloudBusinessTopService;

    @Autowired
    private ICloudVmService cloudVmService;

    @Autowired
    private ICloudResourceLevel1Service cloudResourceLevel1Service;

    @Autowired
    private ICloudResourceLevel2Service cloudResourceLevel2Service;

    @Autowired
    private IDictService dictService;

    @Autowired
    private ICloudCategoryService categoryService;

    @Autowired
    private ICloudVmAddSubService cloudVmAddSubService;

    private static final ImmutableMap<String, String> inverseAreaMapping =
            ImmutableMap.copyOf(HashBiMap.create(CloudVmServiceImpl.areaMapping).inverse());


    private static final ImmutableMap<String, String> appOrderMapping =
            ImmutableMap.<String, String>builder()
                    .put("cpu", "VCPU_USAGE")
                    .put("mem", "MEMEORY_USAGE")
                    .put("disk","EVS_DISK_USAGE")
                    .build();


    private static final ImmutableMap<String, String> vmOrderMapping =
            ImmutableMap.<String, String>builder()
                    .put("cpu", "CPU_USAGE")
                    .put("mem", "MEMORY_USAGE")
                    .put("disk","EVS_DISK_USAGE")
                    .build();



    @RequestMapping("appCount")
    public R appCount(){
        return cloudBusinessTopService.applicationAccount();
    }

    /**
     * 警种应用数统计
     * @return
     */
    @RequestMapping("policeAppCount")
    public R policeAppCount(){
        return cloudBusinessTopService.policeApplicationCount();
    }



    /**
     * 地市应用数(目前只统计了非发达地市)
     * @return
     */
    @RequestMapping("areaAppCount")
    public R areaAppCount(){

        return cloudBusinessTopService.areaApplicationCount();
    }



    /**
     * 警种、地市使用率
     * @param name
     * @return
     */
    @RequestMapping("businessUsage")
    public R businessUsage(String name){
        if("打私".equals(name)){
            name = "海关缉私";
        }
        return cloudBusinessService.usage(name);
    }

    //todo:还没做
    @RequestMapping("business/page")
    public R businessPage(Page<CloudBusiness> page, String name, String type, String orderType, String cloud){
        page = cloudBusinessService.getPage(page,name,type,orderType,cloud);
        return R.ok(page);
    }


    /**
     * 警种应用使用率
     * @param name
     * @return
     */
    @RequestMapping("appUsage")
    public R policeAppUsage(String name){
        if("打私".equals(name)){
            name = "海关缉私";
        }
        return cloudBusinessTopService.policeApplicationUsage(name);
    }

    /**
     * 应用分页列表
     *
     */
    @RequestMapping("app/page")
    public R appPage(Page<CloudBusinessTop> page, String name, String type, String orderType, String cloud){

        if(type!=null && appOrderMapping.containsKey(type)){
            type = appOrderMapping.get(type);
        }
        page = cloudBusinessTopService.getPage(page,name,type,orderType,cloud);

        return R.ok(page);
    }


    /**
     * 警察列表
     * @return
     */
    @RequestMapping("policeNames")
    public R policeNames(){
        return cloudBusinessTopService.policeHasAppName();
    }


    /**
     *  虚拟机分页
     * @param page
     * @param name
     * @param type
     * @param orderType
     * @return
     */
    @RequestMapping("vm/page")
    public R vmPage(Page<CloudVm> page, String name, String type, String orderType, String cloud){


        if(type!=null && appOrderMapping.containsKey(type)){
            type = vmOrderMapping.get(type);
        }


       page =  cloudVmService.getPage(page,name,type,orderType,cloud);

        return R.ok(page);
    }


    @RequestMapping("addOrSub/page")
    public R addOrSubPage(Page<CloudVmAddSub> page,String name, Integer dataType){
        //科信改大数据办
        if ("大数据办".equals(name)) {
            page = (Page<CloudVmAddSub>) cloudVmAddSubService.page(page, new QueryWrapper<CloudVmAddSub>().eq("DATA_TYPE", dataType).eq("BUSINESS_UNIT1", "科信"));
        } else {
            page = (Page<CloudVmAddSub>) cloudVmAddSubService.page(page,new QueryWrapper<CloudVmAddSub>().eq("DATA_TYPE",dataType).eq("BUSINESS_UNIT1",name));
        }
        return R.ok(page);

    }

    /**
     *
     * @param page:current size
     * @param name:应用名
     * @param type：0（全部） 1（扩容） 2（缩容）
     * DATA_TYPE:   0:CPU扩容 1:CPU缩容 2:内存扩容 3:内存缩容
     * @return
     */
    @RequestMapping("addOrSub/pageList")
    public R addOrSubPageList(Page<CloudVmAddSub> page,String name,Integer type){
        if (1==type){
            page = (Page<CloudVmAddSub>) cloudVmAddSubService.page(page,new QueryWrapper<CloudVmAddSub>().in("DATA_TYPE",0,2).like("NAME",name));
        }else if (2==type){
            page = (Page<CloudVmAddSub>) cloudVmAddSubService.page(page,new QueryWrapper<CloudVmAddSub>().in("DATA_TYPE",1,3).like("NAME",name));
        }else{
            page = (Page<CloudVmAddSub>) cloudVmAddSubService.page(page,new QueryWrapper<CloudVmAddSub>().like("NAME",name));
        }
        return R.ok(page);
    }

    /**
     * 虚拟机使用详情
     * @param name
     * @return
     */
    @RequestMapping("vmDetail")
    public R vmDetail(String name){
        if("打私".equals(name)){
            name = "海关缉私";
        }
        return cloudVmService.vmDetail(name);}


    /**
     * 地市列表
     * @return
     */
    @RequestMapping("areaNames")
    public R areaNames(){
        return cloudBusinessTopService.areaHasAppName();
    }



    @RequestMapping("appList")
    public R appListByName(String name){

        if("打私".equals(name)){
            name = "海关缉私";
        }
       /* if("梅州".equals(name) || "潮州".equals(name) || "肇庆".equals(name) || "揭阳".equals(name)){
            List<String> appList = cloudVmService.poorAreaApp(name);
            return R.ok(appList);
        }*/
        List<String> appList = cloudBusinessTopService.getAppListByName(name);
        return R.ok(appList);
    }


    /**
     * 虚拟机TOP/BOT 5
     * @Param name
     * @param type cpu_usage CPU 利用率 memory_usage 内存利用率 evs_disk_usage 磁盘利用率
     * @param orderType desc 降序 asc 升序
     * @return
     */
    @RequestMapping("vmOrder")
    public R vmorder(String name,String type,String orderType){
        if("打私".equals(name)){
            name = "海关缉私";
        }
        List<CloudVm> vmList = cloudVmService.vmTop5(name,type,orderType);
        return R.ok(vmList);
    }

    /**
     * 一片云资源总览
     * @return
     */
    @RequestMapping("oneCloudOverview")
    public R oneCloudOverview(){
        return cloudResourceLevel1Service.overview();
    }


    /**
     * 二级云名字
     * @return
     */
    @RequestMapping("getLevel2Name")
    public R level2Name(){

        List<String> name = cloudResourceLevel2Service.resourceLevelNames();
        name.add(0,"一片云");
        return R.ok(name);

    }


    /**
     * 二级资源概览
     * @param name
     * @return
     */
    @RequestMapping("level2Overview")
    public R level2Overview(String name){
        return cloudResourceLevel2Service.overviewLevel2(name);
    }



    @RequestMapping("originOverview")
    public R originOverview(String name){
        return cloudResourceLevel2Service.originOverview(name);
    }

    /**
     * ods库云服务种类个数
     * @return
     */
    @RequestMapping("odsDataOverview")
    public R odsDataOverview(@RequestParam(value = "cloud",defaultValue = "一片云") String cloud,@RequestParam(value = "display",defaultValue = "0") Integer display){


        List<CloudCategory> cloudCategoryList = categoryService.list(new QueryWrapper<CloudCategory>().lambda().eq(CloudCategory::getCloud,cloud).eq(CloudCategory::getDisplay,display));

        return R.ok(cloudCategoryList);
    }


    /**
     * 虚拟机扩/缩标准字典
     * @return
     */
    @RequestMapping("vmStandard")
    public R vmStandard(){
        Dict dict = dictService.getOne(new QueryWrapper<Dict>().lambda().eq(Dict::getValue,"vmksbz"));

        Map<String,String> dictMap = dictService.selectDeepMap(dict.getId());

        return R.ok(dictMap);
    }


    /**
     * 存储扩缩统计
     * @param name
     * @return
     */
    @RequestMapping("storageNeedAddAndSub")
    public R storageNeedAddAndSub(String name){


        Long start = System.currentTimeMillis();
        /*List<CloudVm> cloudVmList = cloudVmService.list(new QueryWrapper<CloudVm>().lambda()
        .eq(CloudVm::getBusinessUnit1,name).isNotNull(CloudVm::getEvsDiskUsage));*/

        List<CloudVm> cloudVmList = cloudVmService.getList(name);
        System.out.println("QueryListSpend:"+ (System.currentTimeMillis()-start)+"ms");

        Long dictStart = System.currentTimeMillis();
       // Dict dict = dictService.getOne(new QueryWrapper<Dict>().lambda().eq(Dict::getValue,"vmksbz"));
        //Dict sub = dictService.getOne(new QueryWrapper<Dict>().lambda().eq(Dict::getName,"存储缩容利用率"));
        //Dict add = dictService.getOne(new QueryWrapper<Dict>().lambda().eq(Dict::getName,"存储扩容利用率"));

        List<Dict> dictList = dictService.list(new QueryWrapper<Dict>().lambda().
                eq(Dict::getName,"存储缩容利用率").or().eq(Dict::getName,"存储扩容利用率"));

        //Map<String,String> dictMap = dictService.selectDeepMap(dict.getId());

        System.out.println("dictSpend:"+ (System.currentTimeMillis()-dictStart)+"ms");

        Map<String,String> dictMap = dictList.stream().collect(Collectors.toMap((key->key.getName()),(value->value.getValue())));


        Double subLine = Double.valueOf(dictMap.get("存储缩容利用率"));
        Double addLine = Double.valueOf(dictMap.get("存储扩容利用率"));



       long filterStart = System.currentTimeMillis();
        List<CloudVm> needAdd = cloudVmList.stream().filter(cloudVm -> cloudVm.getEvsDiskUsage()>addLine).collect(Collectors.toList());
       // System.out.println("FilterSpend:"+ (System.currentTimeMillis()-filterStart)+"ms");
        List<CloudVm> needSub = cloudVmList.stream().filter(cloudVm -> cloudVm.getEvsDiskUsage()<subLine).collect(Collectors.toList());
        System.out.println("Filter2Spend:"+ (System.currentTimeMillis()-filterStart)+"ms");
        Map<String,List<CloudVm>> resultMap = Maps.newHashMap();
        resultMap.put("needAdd",needAdd);
        resultMap.put("needSub",needSub);
        return R.ok(resultMap);







    }



}
