package com.hirisun.cloud.api.saas;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.model.saas.vo.SaasApplicationMergeVO;
import com.hirisun.cloud.model.saas.vo.SaasApplicationVO;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@FeignClient("cloud-saas-service")
public interface SaasApplicationApi {

	@ApiIgnore
	@ApiOperation("合并提交saas服务")
    @PostMapping(value = "/saas/application/submit/merge")
    public void submitMerge(@RequestParam String appInfoId, @RequestParam List<String> shoppingCartIdList);

    @ApiIgnore
    @ApiOperation("更新状态")
    @GetMapping(value = "/saas/application/updateStatus")
    public void updateStatus(@RequestParam String mergeId, String status);

    @ApiIgnore
    @ApiOperation("同步合并后单子的id查询合并的申请原始单")
    @GetMapping(value = "/saas/application/getListByMergeId")
    public List<SaasApplicationVO> getListByMergeId(@RequestParam String id);

    @ApiIgnore
    @ApiOperation("getFlowPage")
    @PostMapping(value = "/saas/saasApplicationMerge/getFlowPage")
    public Page<SaasApplicationMergeVO> getFlowPage(@RequestParam String userId, @RequestParam Map<String, Object> param);

    @ApiIgnore
    @ApiOperation("getFlowPageWithServiceName")
    @PostMapping(value = "/saas/saasApplicationMerge/getFlowPageWithServiceName")
    public Page<SaasApplicationMergeVO> getFlowPageWithServiceName(@RequestParam String userId, @RequestParam Map<String, Object> param);

    @ApiIgnore
    @ApiOperation("merge")
    @GetMapping(value = "/saas/saasApplicationMerge/merge")
    public SaasApplicationMergeVO merge(@RequestParam String userId, @RequestParam String ids);

    @ApiIgnore
    @ApiOperation("deleteById")
    @GetMapping(value = "/saas/saasApplicationMerge/deleteById")
    public void deleteById(@RequestParam String userId, @RequestParam String id);

    @ApiIgnore
    @ApiOperation("update")
    @PutMapping(value = "/saas/saasApplicationMerge/update")
    public void update(@RequestParam String userId, @RequestBody SaasApplicationMergeVO vo);

    @ApiIgnore
    @ApiOperation("getById")
    @GetMapping(value = "/saas/saasApplicationMerge/getById")
    public SaasApplicationMergeVO getById(@RequestParam String id);

    @ApiIgnore
    @ApiOperation("getDetails")
    @GetMapping(value = "/saas/saasApplicationMerge/getDetails")
    public SaasApplicationMergeVO getDetails(@RequestParam String id);

    @ApiIgnore
    @ApiOperation("updateById")
    @GetMapping(value = "/saas/saasApplicationMerge/updateById")
    public void updateById(@RequestBody SaasApplicationMergeVO vo);

    @ApiIgnore
    @ApiOperation("更新业务办理时间")
    @GetMapping(value = "/saas/saasApplicationMerge/updateCarryTime")
    public void updateCarryTime(@RequestParam String id);

    @ApiIgnore
    @ApiOperation("根据参数更新")
    @PutMapping(value = "/saas/saasApplicationMerge/updateByParams")
    public void updateByParams(@RequestBody SaasApplicationMergeVO vo);
}
