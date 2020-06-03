package com.upd.hwcloud.controller.backend.serviceManagement.paas.subpage;

import com.upd.hwcloud.bean.entity.PaasSubpage;
import com.upd.hwcloud.controller.backend.serviceManagement.BaseSubpageController;
import com.upd.hwcloud.service.IPaasSubpageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * iaas二级页面配置 前端控制器
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
@RestController
@RequestMapping("/paasSubpage")
public class PaasSubpageController extends BaseSubpageController<IPaasSubpageService, PaasSubpage> {

}

