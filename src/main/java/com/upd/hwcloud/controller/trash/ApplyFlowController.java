package com.upd.hwcloud.controller.trash;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.dto.EditStepDTO;
import com.upd.hwcloud.bean.entity.ApplyFlow;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.IApplyFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 资源申请流程配置 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2018-11-26
 */
@Api(description = "资源申请流程配置")
@RestController
@RequestMapping("/applyFlow")
public class ApplyFlowController {

    @Autowired
    private IApplyFlowService applyFlowService;

    @ApiOperation("更新资源申请流程配置")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@LoginUser User user, @RequestBody ApplyFlow flow) {
        flow = applyFlowService.update(user, flow);
        return R.ok(flow);
    }

    @ApiOperation("详情")
    @ApiImplicitParam(name="id", value="流程id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R detail(@PathVariable String id) {
        ApplyFlow applyFlow = applyFlowService.getDetail(id);
        return R.ok(applyFlow);
    }

    @ApiOperation("分页查询流程配置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="form", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="form", dataType="String"),
            @ApiImplicitParam(name="name", value="流程配置名", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public R page(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false) String name) {
        IPage<ApplyFlow> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = applyFlowService.getPage(page, name);
        return R.ok(page);
    }

    @ApiOperation("修改服务审核环节")
    @RequestMapping(value = "/service/editStep", method = RequestMethod.POST)
    @ResponseBody
    public R editStep(@LoginUser User user, @RequestBody EditStepDTO params) {
        applyFlowService.editServiceStep(user, params);
        return R.ok();
    }

}

