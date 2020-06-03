package com.upd.hwcloud.controller.backend.application.form.paas;

import com.upd.hwcloud.bean.entity.application.PaasDistributedDbInfo;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.application.IPaasDistributedDbInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author junglefisher
 * @date 2020/5/9 10:46
 */
@Api(value = "Libra-分布式并行数据库")
@Controller
@RequestMapping("/distributedDbInfo")
public class PaasDistributedDbInfoController {

    @Autowired
    private IPaasDistributedDbInfoService paasDistributedDbInfoService;

    @RequestMapping(value = "/updateById",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("根据订单ID修改Libra-分布式并行数据库信息")
    public R updateByInfoId(@RequestBody PaasDistributedDbInfo paasDistributedDbInfo){
        try {
            paasDistributedDbInfoService.updateById(paasDistributedDbInfo);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }
}
