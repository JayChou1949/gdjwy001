package com.upd.hwcloud.controller.backend.css;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Apply;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.IOperateRecordService;
import com.upd.hwcloud.service.webManage.IApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 试点应用 前端控制器
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
@Api(description = "试点应用管理接口")
@Controller
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    IApplyService iApplyService;
    @Autowired
    IOperateRecordService iOperateRecordService;

    @ApiOperation("新建试点应用")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public R create(@LoginUser User user, @RequestBody Apply apply) {
        apply.setId(UUIDUtil.getUUID());
        apply.setCreator(user.getIdcard());
        apply.setStatus(ReviewStatus.REVIEWING.getCode());
        apply.insert();
        return R.ok();
    }

    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    @ApiOperation("试点应用上/下线")
    @RequestMapping(value = "/publish/{id}")
    @ResponseBody
    public R publish(@LoginUser User user, @PathVariable String id, Integer result,
                     @RequestParam(required = false) String remark) {
        if (result == null) {
            return R.error("请选择操作结果");
        }
        iApplyService.publish(user, id, result, remark);
        return R.ok();
    }

    /**
     * 审核
     * @param result 审核结果 1:通过,其它:驳回
     * @param remark 审核描述
     */
    @ApiOperation("试点应用审核")
    @RequestMapping(value = "/review/{id}")
    @ResponseBody
    public R review(@LoginUser User user, @PathVariable String id, Integer result, @RequestParam(required = false) String remark) {
        if (result == null) {
            return R.error("请选择审核结果");
        }
        Apply apply = iApplyService.getById(id);
        if (apply == null) {
            return R.error("该应用不存在");
        }
        if (result.equals(1)) {
            apply.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            apply.updateById();
            iOperateRecordService.insertRecord(id, user.getIdcard(), "审核", "通过", remark);
        } else {
            apply.setStatus(ReviewStatus.REJECT.getCode());
            apply.updateById();
            iOperateRecordService.insertRecord(id, user.getIdcard(), "审核", "驳回", remark);
        }
        return R.ok();
    }

    /**
     * 分页
     * @param status {@link com.upd.hwcloud.bean.contains.ReviewStatus}
     */
    @ApiOperation("分页查询试点应用")
    @RequestMapping(value = "/page")
    @ResponseBody
    public R page(@LoginUser User user, Integer pageNum, Integer pageSize, Integer status,
                  @RequestParam(required = false) String name) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        Page<Apply> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = iApplyService.getPage(page,user,status,name);
        return R.ok(page);
    }

    @ApiOperation("删除试点应用")
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public R delete(@LoginUser User user, @PathVariable String id) {
        Apply apply = iApplyService.getById(id);
        apply.setStatus(ReviewStatus.DELETE.getCode());
        iApplyService.updateById(apply);
        iOperateRecordService.insertRecord(id, user.getIdcard(), "删除", "删除", null);
        return R.ok();
    }

    @ApiOperation("试点应用详情")
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public R detail(@LoginUser User user, @PathVariable String id) {
        Apply apply = iApplyService.getById(id);
        return R.ok(apply);
    }

    @ApiOperation("修改试点应用")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public R edit(@LoginUser User user, @RequestBody Apply apply) {
        apply.setStatus(ReviewStatus.REVIEWING.getCode());
        apply.updateById();
        return R.ok();
    }
}

