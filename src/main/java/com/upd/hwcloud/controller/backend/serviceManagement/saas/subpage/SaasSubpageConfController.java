package com.upd.hwcloud.controller.backend.serviceManagement.saas.subpage;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.SaasSubpageConf;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.ISaasSubpageConfService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * SAAS服务资源配置 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2019-08-06
 */
@RestController
@RequestMapping("/saasSubpageConf")
public class SaasSubpageConfController {

    @Autowired
    private ISaasSubpageConfService saasSubpageConfService;

    @ApiImplicitParam(name="saasId", value="服务id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/save/{saasId}", method = RequestMethod.POST)
    public R save(@PathVariable String saasId, @RequestBody SaasSubpageConf conf) {
        saasSubpageConfService.save(saasId, conf);
        return R.ok();
    }

    @ApiImplicitParam(name="saasId", value="服务id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{saasId}", method = RequestMethod.GET)
    public R detail(@PathVariable String saasId) {
        SaasSubpageConf conf = saasSubpageConfService.getOne(new QueryWrapper<SaasSubpageConf>().lambda()
                .eq(SaasSubpageConf::getMasterId, saasId));
        return R.ok(conf);
    }

}

