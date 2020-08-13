package com.hirisun.cloud.api.iaas;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hirisun.cloud.model.iaas.vo.IaasObjStoreVo;

import io.swagger.annotations.ApiOperation;

@FeignClient("cloud-iaas-service")
public interface IaasObjStoreApi {

	@ApiOperation(value = "根据字段匹配数据并排序")
    @PostMapping("/iaas/obj/store/list")
	public List<IaasObjStoreVo> list(@RequestBody IaasObjStoreVo iaasObjStoreVo);
}
