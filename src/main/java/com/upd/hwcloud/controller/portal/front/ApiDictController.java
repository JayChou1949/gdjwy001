package com.upd.hwcloud.controller.portal.front;

import com.upd.hwcloud.bean.entity.Dict;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "门户网站字典接口")
@RestController
@RequestMapping("/api/dict")
public class ApiDictController {

    @Autowired
    private IDictService dictService;


    @ApiOperation("通过字典值查询子集")
    @RequestMapping(value = "/getChildByValue/{value}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R getChildByValue(@PathVariable String value) {
        List<Dict> list = dictService.getChildByValue(value);
        return R.ok(list);
    }

    @ApiOperation("是否是基础服务类别")
    @RequestMapping(value = "/checkBaseService/{id}",method = RequestMethod.GET)
    public R checkBaseService(@PathVariable String id){
        return R.ok(dictService.isBasePaaSService(id));
    }


}
