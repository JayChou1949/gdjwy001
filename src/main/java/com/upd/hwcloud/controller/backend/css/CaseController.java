package com.upd.hwcloud.controller.backend.css;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Case;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.IOperateRecordService;
import com.upd.hwcloud.service.webManage.ICaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 典型案例 前端控制器
 * </p>
 *
 * @author huru
 * @since 2018-10-25
 */
@Api(description = "典型案例管理接口")
@RestController
@RequestMapping("/case")
public class CaseController {

    @Autowired
    ICaseService iCaseService;
    @Autowired
    IOperateRecordService iOperateRecordService;

    @ApiOperation("新建典型案例")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public R create(@LoginUser User user, @RequestBody Case c) {
        c.setId(UUIDUtil.getUUID());
        c.setCreator(user.getIdcard());
        c.setStatus(ReviewStatus.REVIEWING.getCode());
        c.insert();
        return R.ok();
    }

    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    @ApiOperation("典型案例上/下线")
    @RequestMapping(value = "/publish/{id}")
    @ResponseBody
    public R publish(@LoginUser User user, @PathVariable String id, Integer result,
                     @RequestParam(required = false) String remark) {
        if (result == null) {
            return R.error();
        }
        iCaseService.publish(user, id, result, remark);
        return R.ok();
    }

    /**
     * 审核
     * @param result 审核结果 1:通过,其它:驳回
     * @param remark 审核描述
     */
    @ApiOperation("审核典型案例")
    @RequestMapping(value = "/review/{id}")
    @ResponseBody
    public R review(@LoginUser User user, @PathVariable String id, Integer result, @RequestParam(required = false) String remark) {
        if (result == null) {
            return R.error("请选择审核结果");
        }
        Case c = iCaseService.getById(id);
        if (c == null) {
            return R.error("该服务不存在");
        }
        if (result.equals(1)) {
            c.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            iCaseService.updateById(c);
            iOperateRecordService.insertRecord(id, user.getIdcard(), "审核", "通过", remark);
        } else {
            c.setStatus(ReviewStatus.REJECT.getCode());
            iCaseService.updateById(c);
            iOperateRecordService.insertRecord(id, user.getIdcard(), "审核", "驳回", remark);
        }
        return R.ok();
    }

    /**
     * 分页
     * @param status {@link com.upd.hwcloud.bean.contains.ReviewStatus}
     */
    @ApiOperation("分页查询典型案例")
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
        Page<Case> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = iCaseService.getPage(page, user, status, name);
        return R.ok(page);
    }

    @ApiOperation("删除典型案例")
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public R delete(@LoginUser User user, @PathVariable String id) {
        Case c = iCaseService.getById(id);
        c.setStatus(ReviewStatus.DELETE.getCode());
        iCaseService.updateById(c);
        iOperateRecordService.insertRecord(id, user.getIdcard(), "删除", "删除", null);
        return R.ok();
    }

    @ApiOperation("典型案例详情")
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public R detail(@LoginUser User user, @PathVariable String id) {
        Case aCase = iCaseService.getById(id);
        return R.ok(aCase);
    }

    @ApiOperation("修改典型案例")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public R edit(@LoginUser User user, @RequestBody Case c) {
        c.setStatus(ReviewStatus.REVIEWING.getCode());
        iCaseService.updateById(c);
        return R.ok();
    }
}

