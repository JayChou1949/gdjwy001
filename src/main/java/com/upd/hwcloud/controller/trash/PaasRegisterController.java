package com.upd.hwcloud.controller.trash;


import com.upd.hwcloud.bean.contains.BusinessName;
import com.upd.hwcloud.bean.entity.PaasRegister;
import com.upd.hwcloud.controller.backend.application.RegisterController;
import com.upd.hwcloud.service.IPaasRegisterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * PAAS服务注册表 前端控制器
 * </p>
 *
 * @author zwb
 * @since 2019-06-12
 */
@RestController
@RequestMapping("/paasRegister")
public class PaasRegisterController extends RegisterController<IPaasRegisterService, PaasRegister> {
     String FLOW_CODE = "ZCPAAS";

    @Override
    public String getFlowcode() {
        return FLOW_CODE;
    }

    @Override
    public String getBusinessName() {
        return BusinessName.PAAS_REGIST;
    }

}

