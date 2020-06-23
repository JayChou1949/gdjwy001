package com.upd.hwcloud.controller.backend.open;

import com.upd.hwcloud.bean.entity.InstanceOrderMessage;
import com.upd.hwcloud.bean.entity.ServiceInstance;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.InstanceOrderMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 警员信息查询
 * @author junglefisher
 * @date 2019/11/12 18:41
 */
@Api("查询警员信息(美亚用)")
@RestController
@RequestMapping("/api/InstanceOrderMessage")
public class InstanceOrderMessageController {
    @Autowired
    private InstanceOrderMessageService instanceOrderMessageService;

    @ApiOperation("根据实例ID查询警员信息")
    @RequestMapping(value = "/getMessageByGuid/{guid}",method = RequestMethod.GET)
    @ResponseBody
    public R getMessageByGuid(@PathVariable("guid") String guid)throws Exception{
        System.out.println("---------------------------GUID:"+guid);
        List<InstanceOrderMessage> instanceOrderMessageList;
        try {
            instanceOrderMessageList=instanceOrderMessageService.getMessageByGuid(guid);
            instanceOrderMessageList=instanceOrderMessageService.addKeyAndSecret(instanceOrderMessageList);
        }catch (Exception e){
            return R.error("查询失败!");
        }
        return R.ok(instanceOrderMessageList);
    }

    @RequestMapping(value = "/getServiceInstanceByProid/{projectId}")
    @ResponseBody
    public R getServiceInstanceByProid(@PathVariable("projectId") String projectId){
        List<ServiceInstance> serviceInstances;
        try {
            serviceInstances = instanceOrderMessageService.getServiceInstanceByProid(projectId);
        }catch (Exception e){
            return R.error("查询失败!");
        }
        return R.ok(serviceInstances);
    }

}
