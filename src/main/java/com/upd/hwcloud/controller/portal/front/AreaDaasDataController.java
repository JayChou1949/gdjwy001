package com.upd.hwcloud.controller.portal.front;

import com.upd.hwcloud.bean.dto.AreaDaasData;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.AreaDaasDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author junglefisher
 * @date 2020/6/14 11:22
 */
@RestController
@RequestMapping("/areaDaasData")
public class AreaDaasDataController {

    @Autowired
    private AreaDaasDataService areaDaasDataService;

    /**
     * 插入地市Daas服务数据
     * @param areaDaasData
     * @return
     */
    @RequestMapping(value = "/importDaasData",method = RequestMethod.POST)
    public R importDaasData(@RequestBody AreaDaasData areaDaasData){
        try {
            areaDaasDataService.importDaasData(areaDaasData);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.error();
    }

    /**
     * 获取地市数据服务数据
     * @return
     */
    @RequestMapping(value = "/getDaasData/{name}",method = RequestMethod.GET)
    public R getDaasData(@PathVariable("name") String name){
        try {
            AreaDaasData areaDaasData = areaDaasDataService.getDaasData(name);
            return R.ok(areaDaasData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.error();
    }
}
