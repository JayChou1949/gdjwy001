package com.upd.hwcloud.controller.backend.serviceManagement.iaas.subpage;


import com.upd.hwcloud.bean.entity.IaasSubpage;
import com.upd.hwcloud.controller.backend.serviceManagement.BaseSubpageController;
import com.upd.hwcloud.service.IIaasSubpageService;

import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * iaas二级页面配置 前端控制器
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
@RestController
@RequestMapping("/iaasSubpage")
public class IaasSubpageController extends BaseSubpageController<IIaasSubpageService,IaasSubpage> {

}

