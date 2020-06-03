package com.upd.hwcloud.controller.portal.iaasConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.dto.Mdv;
import com.upd.hwcloud.bean.dto.PhyAndVm;
import com.upd.hwcloud.bean.dto.Tl5;
import com.upd.hwcloud.bean.entity.ThreePartyInterface;
import com.upd.hwcloud.bean.entity.iaasConfig.*;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IThreePartyInterfaceService;
import com.upd.hwcloud.service.iaasConfig.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(description = "iaas后台配置接口")
@Controller(value = "iaasBigDataController")
@RequestMapping("/api/iaas")
public class IaasController {

    @Autowired
    IVmDeviceService iVmDeviceService;
    @Autowired
    IPhyDeviceService iPhyDeviceService;
    @Autowired
    IPoliceCloudService iPoliceCloudService;
    @Autowired
    IMoveDataVideoService iMoveDataVideoService;
    @Autowired
    IResourceService iResourceService;
    @Autowired
    ITop5Low5Service iTop5Low5Service;
    @Autowired
    IThreePartyInterfaceService threePartyInterfaceService;
    @Autowired
    IBigdataResourceService bigdataResourceService;

    @ApiOperation("IAAS大数据工程资源-详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orderBy", value="排序规则 升序(acs),降序(desc)", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/bigdataResource/detail",method = RequestMethod.GET)
    @ResponseBody
    public R bigdataResourceDetail(@RequestParam(required = false) String orderBy) {
        ThreePartyInterface gkStr = threePartyInterfaceService.getOne(
                new QueryWrapper<ThreePartyInterface>().eq("label", "IAAS大数据工程资源-详情"));
        Map<String, Object> result = Maps.newHashMap();
        if (StringUtils.isNotEmpty(gkStr.getData())) {
            JSONObject gk = JSON.parseObject(gkStr.getData());
            result.put("gk", gk);
        }
        boolean orderAble = "asc".equals(orderBy) || "desc".equals(orderBy);
        List<BigdataResource> list = bigdataResourceService.list(new QueryWrapper<BigdataResource>().lambda()
                .orderBy(orderAble, "asc".equals(orderBy), BigdataResource::getNum)
                .orderByAsc(BigdataResource::getSort));
        result.put("list", list);
        return R.ok(result);
    }


    /**
     * 查物理设备 虚拟设备 警务云工程资源
     * @param type
     * @param area
     * @return
     */
    @ApiOperation("查物理设备 虚拟设备 警务云工程资源")
    @RequestMapping(value = "phyAndVm",method = RequestMethod.GET)
    @ResponseBody
    public R phyAndVm(@RequestParam(required = false) String type, @RequestParam(required = false) String area) {

        PhyDevice all = new PhyDevice();
        Integer server = 0;
        Integer router = 0;
        Integer storage = 0;
        Integer change = 0;
        Integer firewall = 0;
        Integer bigdata = 0;
        VmDevice vall = new VmDevice();
        Integer cloudServer = 0;
        Integer metalServer = 0;
        Integer image = 0;
        Integer cloudDisk = 0;
        Integer priCloud = 0;
        Integer elaIp = 0;
        Integer elaLoad = 0;
        Integer vmFirewell = 0;
        PoliceCloud pcall = new PoliceCloud();
        Integer cpuTotal = 0;
        Integer cpuAllotment = 0;
        Integer cpuAllotmentPro = 0;
        Integer gpuTotal = 0;
        Integer gpuAllotment = 0;
        Integer gpuAllotmentPro = 0;
        Integer emsTotal = 0;
        Integer emsAllotment = 0;
        Integer emsAllotmentPro = 0;
        Integer storTotal = 0;
        Integer storAllotment = 0;
        Integer storAllotmentPro = 0;

        //类下面的所有地区总和
        if(type != null && area == null) {
            List<PhyDevice> phyDevices = iPhyDeviceService.list(new QueryWrapper<PhyDevice>().eq("TYPE",type));
            List<VmDevice> vmDevices = iVmDeviceService.list(new QueryWrapper<VmDevice>().eq("TYPE",type));
            List<PoliceCloud> policeClouds = iPoliceCloudService.list(new QueryWrapper<PoliceCloud>().eq("TYPE",type));

            for(PhyDevice phyDevice : phyDevices) {
                server += Integer.valueOf(phyDevice.getServer());
                router += Integer.valueOf(phyDevice.getRouter());
                storage += Integer.valueOf(phyDevice.getStorage());
                change += Integer.valueOf(phyDevice.getChange());
                firewall += Integer.valueOf(phyDevice.getFirewall());
                bigdata += Integer.valueOf(phyDevice.getBigdata());
            }

            for(VmDevice vmDevice : vmDevices) {
                cloudServer += Integer.valueOf(vmDevice.getCloudServer());
                metalServer += Integer.valueOf(vmDevice.getMetalServer());
                image += Integer.valueOf(vmDevice.getImage());
                cloudDisk += Integer.valueOf(vmDevice.getCloudDisk());
                priCloud += Integer.valueOf(vmDevice.getPriCloud());
                elaIp += Integer.valueOf(vmDevice.getElaIp());
                elaLoad += Integer.valueOf(vmDevice.getElaLoad());
                vmFirewell += Integer.valueOf(vmDevice.getVmFirewell());
            }

            for(PoliceCloud policeCloud : policeClouds) {
                cpuTotal += Integer.valueOf(policeCloud.getCpuTotal());
                cpuAllotment += Integer.valueOf(policeCloud.getCpuAllotment());
                cpuAllotmentPro += Integer.valueOf(policeCloud.getCpuAllotmentPro());
                gpuTotal += Integer.valueOf(policeCloud.getGpuTotal());
                gpuAllotment += Integer.valueOf(policeCloud.getGpuAllotment());
                gpuAllotmentPro += Integer.valueOf(policeCloud.getGpuAllotmentPro());
                emsTotal += Integer.valueOf(policeCloud.getEmsTotal());
                emsAllotment += Integer.valueOf(policeCloud.getEmsAllotment());
                emsAllotmentPro += Integer.valueOf(policeCloud.getEmsAllotmentPro());
                storTotal += Integer.valueOf(policeCloud.getStorTotal());
                storAllotment += Integer.valueOf(policeCloud.getStorAllotment());
                storAllotmentPro += Integer.valueOf(policeCloud.getStorAllotmentPro());
            }

            all.setServer(String.valueOf(server));
            all.setRouter(String.valueOf(router));
            all.setStorage(String.valueOf(storage));
            all.setChange(String.valueOf(change));
            all.setFirewall(String.valueOf(firewall));
            all.setBigdata(String.valueOf(bigdata));

            vall.setCloudServer(String.valueOf(cloudServer));
            vall.setMetalServer(String.valueOf(metalServer));
            vall.setImage(String.valueOf(image));
            vall.setCloudDisk(String.valueOf(cloudDisk));
            vall.setPriCloud(String.valueOf(priCloud));
            vall.setElaIp(String.valueOf(elaIp));
            vall.setElaLoad(String.valueOf(elaLoad));
            vall.setVmFirewell(String.valueOf(vmFirewell));

            pcall.setCpuTotal(String.valueOf(cpuTotal));
            pcall.setCpuAllotment(String.valueOf(cpuAllotment));
            pcall.setCpuAllotmentPro(String.valueOf(cpuAllotmentPro));
            pcall.setGpuTotal(String.valueOf(gpuTotal));
            pcall.setGpuAllotment(String.valueOf(gpuAllotment));
            pcall.setGpuAllotmentPro(String.valueOf(gpuAllotmentPro));
            pcall.setEmsTotal(String.valueOf(emsTotal));
            pcall.setEmsAllotment(String.valueOf(emsAllotment));
            pcall.setEmsAllotmentPro(String.valueOf(emsAllotmentPro));
            pcall.setStorTotal(String.valueOf(storTotal));
            pcall.setStorAllotment(String.valueOf(storAllotment));
            pcall.setStorAllotmentPro(String.valueOf(storAllotmentPro));

            PhyAndVm phyAndVm = new PhyAndVm();
            phyAndVm.setPhyDevice(all);
            phyAndVm.setVmDevice(vall);
            phyAndVm.setPoliceCloud(pcall);
            return R.ok(phyAndVm);
        }

        //全省
        if(type == null && area == null) {
            List<PhyDevice> phyDevices = iPhyDeviceService.list(new QueryWrapper<>());
            List<VmDevice> vmDevices = iVmDeviceService.list(new QueryWrapper<>());
            List<PoliceCloud> policeClouds = iPoliceCloudService.list(new QueryWrapper<>());

            for(PhyDevice phyDevice : phyDevices) {
                server += Integer.valueOf(phyDevice.getServer());
                router += Integer.valueOf(phyDevice.getRouter());
                storage += Integer.valueOf(phyDevice.getStorage());
                change += Integer.valueOf(phyDevice.getChange());
                firewall += Integer.valueOf(phyDevice.getFirewall());
                bigdata += Integer.valueOf(phyDevice.getBigdata());
            }

            for(VmDevice vmDevice : vmDevices) {
                cloudServer += Integer.valueOf(vmDevice.getCloudServer());
                metalServer += Integer.valueOf(vmDevice.getMetalServer());
                image += Integer.valueOf(vmDevice.getImage());
                cloudDisk += Integer.valueOf(vmDevice.getCloudDisk());
                priCloud += Integer.valueOf(vmDevice.getPriCloud());
                elaIp += Integer.valueOf(vmDevice.getElaIp());
                elaLoad += Integer.valueOf(vmDevice.getElaLoad());
                vmFirewell += Integer.valueOf(vmDevice.getVmFirewell());
            }

            for(PoliceCloud policeCloud : policeClouds) {
                cpuTotal += Integer.valueOf(policeCloud.getCpuTotal());
                cpuAllotment += Integer.valueOf(policeCloud.getCpuAllotment());
                cpuAllotmentPro += Integer.valueOf(policeCloud.getCpuAllotmentPro());
                gpuTotal += Integer.valueOf(policeCloud.getGpuTotal());
                gpuAllotment += Integer.valueOf(policeCloud.getGpuAllotment());
                gpuAllotmentPro += Integer.valueOf(policeCloud.getGpuAllotmentPro());
                emsTotal += Integer.valueOf(policeCloud.getEmsTotal());
                emsAllotment += Integer.valueOf(policeCloud.getEmsAllotment());
                emsAllotmentPro += Integer.valueOf(policeCloud.getEmsAllotmentPro());
                storTotal += Integer.valueOf(policeCloud.getStorTotal());
                storAllotment += Integer.valueOf(policeCloud.getStorAllotment());
                storAllotmentPro += Integer.valueOf(policeCloud.getStorAllotmentPro());
            }

            all.setServer(String.valueOf(server));
            all.setRouter(String.valueOf(router));
            all.setStorage(String.valueOf(storage));
            all.setChange(String.valueOf(change));
            all.setFirewall(String.valueOf(firewall));
            all.setBigdata(String.valueOf(bigdata));

            vall.setCloudServer(String.valueOf(cloudServer));
            vall.setMetalServer(String.valueOf(metalServer));
            vall.setImage(String.valueOf(image));
            vall.setCloudDisk(String.valueOf(cloudDisk));
            vall.setPriCloud(String.valueOf(priCloud));
            vall.setElaIp(String.valueOf(elaIp));
            vall.setElaLoad(String.valueOf(elaLoad));
            vall.setVmFirewell(String.valueOf(vmFirewell));

            pcall.setCpuTotal(String.valueOf(cpuTotal));
            pcall.setCpuAllotment(String.valueOf(cpuAllotment));
            pcall.setCpuAllotmentPro(String.valueOf(cpuAllotmentPro));
            pcall.setGpuTotal(String.valueOf(gpuTotal));
            pcall.setGpuAllotment(String.valueOf(gpuAllotment));
            pcall.setGpuAllotmentPro(String.valueOf(gpuAllotmentPro));
            pcall.setEmsTotal(String.valueOf(emsTotal));
            pcall.setEmsAllotment(String.valueOf(emsAllotment));
            pcall.setEmsAllotmentPro(String.valueOf(emsAllotmentPro));
            pcall.setStorTotal(String.valueOf(storTotal));
            pcall.setStorAllotment(String.valueOf(storAllotment));
            pcall.setStorAllotmentPro(String.valueOf(storAllotmentPro));

            PhyAndVm phyAndVm = new PhyAndVm();
            phyAndVm.setPhyDevice(all);
            phyAndVm.setVmDevice(vall);
            phyAndVm.setPoliceCloud(pcall);
            return R.ok(phyAndVm);

        }

        //地区+全省
        if("4".equals(type) && area != null) {
            List<PhyDevice> phyDevices = iPhyDeviceService.list(new QueryWrapper<PhyDevice>().eq("AREA",area));
            List<VmDevice> vmDevices = iVmDeviceService.list(new QueryWrapper<VmDevice>().eq("AREA",area));
            List<PoliceCloud> policeClouds = iPoliceCloudService.list(new QueryWrapper<PoliceCloud>().eq("AREA",area));

            for(PhyDevice phyDevice : phyDevices) {
                server += Integer.valueOf(phyDevice.getServer());
                router += Integer.valueOf(phyDevice.getRouter());
                storage += Integer.valueOf(phyDevice.getStorage());
                change += Integer.valueOf(phyDevice.getChange());
                firewall += Integer.valueOf(phyDevice.getFirewall());
                bigdata += Integer.valueOf(phyDevice.getBigdata());
            }

            for(VmDevice vmDevice : vmDevices) {
                cloudServer += Integer.valueOf(vmDevice.getCloudServer());
                metalServer += Integer.valueOf(vmDevice.getMetalServer());
                image += Integer.valueOf(vmDevice.getImage());
                cloudDisk += Integer.valueOf(vmDevice.getCloudDisk());
                priCloud += Integer.valueOf(vmDevice.getPriCloud());
                elaIp += Integer.valueOf(vmDevice.getElaIp());
                elaLoad += Integer.valueOf(vmDevice.getElaLoad());
                vmFirewell += Integer.valueOf(vmDevice.getVmFirewell());
            }

            for(PoliceCloud policeCloud : policeClouds) {
                cpuTotal += Integer.valueOf(policeCloud.getCpuTotal());
                cpuAllotment += Integer.valueOf(policeCloud.getCpuAllotment());
                cpuAllotmentPro += Integer.valueOf(policeCloud.getCpuAllotmentPro());
                gpuTotal += Integer.valueOf(policeCloud.getGpuTotal());
                gpuAllotment += Integer.valueOf(policeCloud.getGpuAllotment());
                gpuAllotmentPro += Integer.valueOf(policeCloud.getGpuAllotmentPro());
                emsTotal += Integer.valueOf(policeCloud.getEmsTotal());
                emsAllotment += Integer.valueOf(policeCloud.getEmsAllotment());
                emsAllotmentPro += Integer.valueOf(policeCloud.getEmsAllotmentPro());
                storTotal += Integer.valueOf(policeCloud.getStorTotal());
                storAllotment += Integer.valueOf(policeCloud.getStorAllotment());
                storAllotmentPro += Integer.valueOf(policeCloud.getStorAllotmentPro());
            }

            all.setArea(area);
            all.setServer(String.valueOf(server));
            all.setRouter(String.valueOf(router));
            all.setStorage(String.valueOf(storage));
            all.setChange(String.valueOf(change));
            all.setFirewall(String.valueOf(firewall));
            all.setBigdata(String.valueOf(bigdata));

            vall.setCloudServer(String.valueOf(cloudServer));
            vall.setMetalServer(String.valueOf(metalServer));
            vall.setArea(area);
            vall.setImage(String.valueOf(image));
            vall.setCloudDisk(String.valueOf(cloudDisk));
            vall.setPriCloud(String.valueOf(priCloud));
            vall.setElaIp(String.valueOf(elaIp));
            vall.setElaLoad(String.valueOf(elaLoad));
            vall.setVmFirewell(String.valueOf(vmFirewell));

            pcall.setArea(area);
            pcall.setCpuTotal(String.valueOf(cpuTotal));
            pcall.setCpuAllotment(String.valueOf(cpuAllotment));
            pcall.setCpuAllotmentPro(String.valueOf(cpuAllotmentPro));
            pcall.setGpuTotal(String.valueOf(gpuTotal));
            pcall.setGpuAllotment(String.valueOf(gpuAllotment));
            pcall.setGpuAllotmentPro(String.valueOf(gpuAllotmentPro));
            pcall.setEmsTotal(String.valueOf(emsTotal));
            pcall.setEmsAllotment(String.valueOf(emsAllotment));
            pcall.setEmsAllotmentPro(String.valueOf(emsAllotmentPro));
            pcall.setStorTotal(String.valueOf(storTotal));
            pcall.setStorAllotment(String.valueOf(storAllotment));
            pcall.setStorAllotmentPro(String.valueOf(storAllotmentPro));

            PhyAndVm phyAndVm = new PhyAndVm();
            phyAndVm.setPhyDevice(all);
            phyAndVm.setVmDevice(vall);
            phyAndVm.setPoliceCloud(pcall);
            return R.ok(phyAndVm);
        }

        QueryWrapper<PhyDevice> phyQuery = new QueryWrapper<>();
        QueryWrapper<VmDevice> vmQuery = new QueryWrapper<>();
        QueryWrapper<PoliceCloud> pcQuery = new QueryWrapper<>();
        if(type != null && !"".equals(type)) {
            phyQuery.eq("TYPE",type);
            vmQuery.eq("TYPE",type);
            pcQuery.eq("TYPE",type);
        }
        if(area != null && !"".equals(area)) {
            phyQuery.eq("AREA",area);
            vmQuery.eq("AREA",area);
            pcQuery.eq("AREA",area);
        }

        PhyDevice phyDevice = iPhyDeviceService.getOne(phyQuery);
        VmDevice vmDevice = iVmDeviceService.getOne(vmQuery);
        PoliceCloud policeCloud = iPoliceCloudService.getOne(pcQuery);
        PhyAndVm phyAndVm = new PhyAndVm();
        phyAndVm.setPhyDevice(phyDevice);
        phyAndVm.setVmDevice(vmDevice);
        phyAndVm.setPoliceCloud(policeCloud);
        return R.ok(phyAndVm);
    }

    /**
     * 移动网，大数据，视频工程资源
     * @return
     */
    @ApiOperation("查询移动网，大数据，视频工程资源")
    @RequestMapping(value = "moveDataVideo",method = RequestMethod.GET)
    @ResponseBody
    public R moveDataVideo() {
        MoveDataVideo move = iMoveDataVideoService.getOne(new QueryWrapper<MoveDataVideo>().eq("STATUS", "1"));
        MoveDataVideo data = iMoveDataVideoService.getOne(new QueryWrapper<MoveDataVideo>().eq("STATUS", "2"));
        MoveDataVideo video = iMoveDataVideoService.getOne(new QueryWrapper<MoveDataVideo>().eq("STATUS", "3"));
        Mdv mdv = new Mdv();
        mdv.setData(data);
        mdv.setMove(move);
        mdv.setVideo(video);
        return R.ok(mdv);
    }

    /**
     * 资源分配情况
     * @param type
     * @return
     */
    @ApiOperation("查询资源分配情况")
    @RequestMapping(value = "getResource",method = RequestMethod.GET)
    @ResponseBody
    public R getResource(@RequestParam(required = false) String type) {

        //全省
        if(type == null) {
            List<Resource> resources1 = iResourceService.list(
                    new QueryWrapper<Resource>().eq("BIG_TYPE","2").orderByAsc("TYPE").orderByAsc("SORT"));
            List<Resource> resources2 = iResourceService.list(
                    new QueryWrapper<Resource>().eq("BIG_TYPE","3").orderByAsc("TYPE").orderByAsc("SORT"));

            List<Resource> all = new ArrayList<>();
            boolean r1Empty =  resources1 == null || resources1.isEmpty();
            boolean r2Empty =  resources2 == null || resources2.isEmpty();
            if (r1Empty && r2Empty) {
                return R.ok(all);
            }
            if (r1Empty) {
                return R.ok(resources2);
            }
            if (r2Empty) {
                return R.ok(resources1);
            }
            for(Resource resource1 : resources1) {
                boolean addR2 = false;
                for (Resource resource2 : resources2) {
                    if(resource1.getName().equals(resource2.getName())) {
                        Integer vmNumall = Integer.valueOf(resource1.getVmNum()) + Integer.valueOf(resource2.getVmNum());
                        resource2.setVmNum(String.valueOf(vmNumall));
                        if(!"0".equals(resource2.getVmNum())) {
                            all.add(resource2);
                            addR2 = true;
                        }
                    }
                }
                if(!addR2) {
                    if(!"0".equals(resource1.getVmNum())) {
                        all.add(resource1);
                    }
                }
            }

//            for(Resource resource1 : resources1) {
//                boolean flag = false;
//                for (Resource resource2 : resources2) {
//                    if(resource1.getName().equals(resource2.getName())) {
//                        Integer vmNumall = Integer.valueOf(resource1.getVmNum())+Integer.valueOf(resource2.getVmNum());
//                        resource2.setVmNum(String.valueOf(vmNumall));
//                        resources2.add(resource2);
//                        flag = true;
//                    }
//                }
//                if(flag == false) {
//                    all.add(resource1);
//                }
//            }
//            all.addAll(resources2);
//            for(int i=0;i<all.size();) {
//                if("0".equals(all.get(i).getVmNum())) {
//                    all.remove(i);
//                }else{
//                    i++;
//                }
//            }
            return R.ok(all);
        } else {
            QueryWrapper<Resource> queryWrapper = new QueryWrapper();
            queryWrapper.eq("BIG_TYPE",type);
            queryWrapper.orderByAsc("SORT");
            List<Resource> resources = iResourceService.list(queryWrapper);
            return R.ok(resources);
        }
    }

    @RequestMapping("top5Low5")
    @ResponseBody
    public R getTop5Low5() {
//        List<Top5Low5> qwCpuTop5 = iTop5Low5Service.list(new QueryWrapper<Top5Low5>().eq("TYPE", "1").orderByDesc("CPU"));
//        List<Top5Low5> jzCpuTop5 = iTop5Low5Service.list(new QueryWrapper<Top5Low5>().eq("TYPE", "2").orderByDesc("CPU"));
//        List<Top5Low5> dqCpuTop5 = iTop5Low5Service.list(new QueryWrapper<Top5Low5>().eq("TYPE", "3").orderByDesc("CPU"));
//
//        List<Top5Low5> qwEmsTop5 = iTop5Low5Service.list(new QueryWrapper<Top5Low5>().eq("TYPE", "1").orderByDesc("EMS"));
//        List<Top5Low5> jzEmsTop5 = iTop5Low5Service.list(new QueryWrapper<Top5Low5>().eq("TYPE", "2").orderByDesc("EMS"));
//        List<Top5Low5> dqEmsTop5 = iTop5Low5Service.list(new QueryWrapper<Top5Low5>().eq("TYPE", "3").orderByDesc("EMS"));
//
//        List<Top5Low5> qwStorTop5 = iTop5Low5Service.list(new QueryWrapper<Top5Low5>().eq("TYPE", "1").orderByDesc("stor"));
//        List<Top5Low5> jzStorTop5 = iTop5Low5Service.list(new QueryWrapper<Top5Low5>().eq("TYPE", "2").orderByDesc("stor"));
//        List<Top5Low5> dqStorTop5 = iTop5Low5Service.list(new QueryWrapper<Top5Low5>().eq("TYPE", "3").orderByDesc("stor"));

        List<Top5Low5> qwCpuTop5 = iTop5Low5Service.getByType("1","CPU");
        List<Top5Low5> jzCpuTop5 = iTop5Low5Service.getByType("2","CPU");
        List<Top5Low5> dqCpuTop5 = iTop5Low5Service.getByType("3","CPU");

        List<Top5Low5> qwEmsTop5 = iTop5Low5Service.getByType("1","EMS");
        List<Top5Low5> jzEmsTop5 = iTop5Low5Service.getByType("2","EMS");
        List<Top5Low5> dqEmsTop5 = iTop5Low5Service.getByType("3","EMS");

        List<Top5Low5> qwStorTop5 = iTop5Low5Service.getByType("1","STOR");
        List<Top5Low5> jzStorTop5 = iTop5Low5Service.getByType("2","STOR");
        List<Top5Low5> dqStorTop5 = iTop5Low5Service.getByType("3","STOR");


        Tl5 tl5 = new Tl5();

        tl5.setQwCpuLow5(desc(qwCpuTop5));
        tl5.setQwCpuTop5(asc(qwCpuTop5));
        tl5.setDqCpuLow5(desc(dqCpuTop5));
        tl5.setDqCpuTop5(asc(dqCpuTop5));
        tl5.setJzCpuLow5(desc(jzCpuTop5));
        tl5.setJzCpuTop5(asc(jzCpuTop5));
        tl5.setQwEmsLow5(desc(qwEmsTop5));
        tl5.setQwEmsTop5(asc(qwEmsTop5));
        tl5.setJzEmsLow5(desc(jzEmsTop5));
        tl5.setJzEmsTop5(asc(jzEmsTop5));
        tl5.setDqEmsLow5(desc(dqEmsTop5));
        tl5.setDqEmsTop5(asc(dqEmsTop5));
        tl5.setQwStorTop5(asc(qwStorTop5));
        tl5.setQwStorLow5(desc(qwStorTop5));
        tl5.setJzStorTop5(asc(jzStorTop5));
        tl5.setJzStorLow5(desc(jzStorTop5));
        tl5.setDqStorLow5(desc(dqStorTop5));
        tl5.setDqStorTop5(asc(dqStorTop5));
        return R.ok(tl5);
    }


    public List asc(List list) {
        if(list==null || list.size()<5){
            return list;
        }
        return list.subList(0,5);
    }

    public List desc(List list) {
        if(list==null || list.size()<5){
            return list;
        }
        return list.subList(list.size()-5,list.size());
    }
}
