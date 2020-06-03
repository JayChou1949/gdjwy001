package com.upd.hwcloud.controller.backend.css;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.PoliceClass;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.IOperateRecordService;
import com.upd.hwcloud.service.webManage.IPoliceClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 警种上云 前端控制器
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
@Api(description = "警种上云管理接口")
@Controller
@RequestMapping("/policeClass")
public class PoliceClassController {

    @Autowired
    IPoliceClassService iPoliceClassService;
    @Autowired
    IOperateRecordService iOperateRecordService;

    @ApiOperation("新建警种上云")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public R create(@LoginUser User user, @RequestBody PoliceClass policeClass) {
        policeClass.setId(UUIDUtil.getUUID());
        policeClass.setCreator(user.getIdcard());
        policeClass.setStatus(ReviewStatus.REVIEWING.getCode());
        policeClass.insert();
        return R.ok();
    }

    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    @ApiOperation("警种上云上/下线")
    @RequestMapping(value = "/publish/{id}")
    @ResponseBody
    public R publish(@LoginUser User user, @PathVariable String id, Integer result,
                     @RequestParam(required = false) String remark) {
        if (result == null) {
            return R.error();
        }
        iPoliceClassService.publish(user, id, result, remark);
        return R.ok();
    }

    /**
     * 审核
     * @param result 审核结果 1:通过,其它:驳回
     * @param remark 审核描述
     */
    @ApiOperation("审核警种上云")
    @RequestMapping(value = "/review/{id}")
    @ResponseBody
    public R review(@LoginUser User user, @PathVariable String id, Integer result, @RequestParam(required = false) String remark) {
        if (result == null) {
            return R.error("请选择审核结果");
        }
        PoliceClass policeClass = iPoliceClassService.getById(id);
        if (policeClass == null) {
            return R.error("该服务不存在");
        }
        if (result.equals(1)) {
            policeClass.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            policeClass.updateById();
            iOperateRecordService.insertRecord(id, user.getIdcard(), "审核", "通过", remark);
        } else {
            policeClass.setStatus(ReviewStatus.REJECT.getCode());
            policeClass.updateById();
            iOperateRecordService.insertRecord(id, user.getIdcard(), "审核", "驳回", remark);
        }
        return R.ok();
    }

    /**
     * 分页
     * @param status {@link ReviewStatus}
     */
    @ApiOperation("分页查询警种上云")
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
        Page<PoliceClass> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = iPoliceClassService.getPage(page,user,status,name);
        return R.ok(page);
    }

    @ApiOperation("删除警种上云")
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public R delete(@LoginUser User user, @PathVariable String id) {
        PoliceClass policeClass = iPoliceClassService.getById(id);
        policeClass.setStatus(ReviewStatus.DELETE.getCode());
        iPoliceClassService.updateById(policeClass);
        iOperateRecordService.insertRecord(id, user.getIdcard(), "删除", "删除", null);
        return R.ok();
    }

    @ApiOperation("警种上云详情")
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public R detail(@LoginUser User user, @PathVariable String id) {
        PoliceClass policeClass = iPoliceClassService.getById(id);
        return R.ok(policeClass);
    }

    @ApiOperation("编辑警种上云")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public R edit(@LoginUser User user, @RequestBody PoliceClass policeClass) {
        policeClass.setStatus(ReviewStatus.REVIEWING.getCode());
        policeClass.updateById();
        return R.ok();
    }
}

