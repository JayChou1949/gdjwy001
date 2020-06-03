package com.upd.hwcloud.controller.backend.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.OperateRecord;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IOperateRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 操作记录 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
@Api(description = "操作记录接口")
@Controller
@RequestMapping("/operateRecord")
public class OperateRecordController {

    @Autowired
    private IOperateRecordService operateRecordService;

    @ApiOperation("分页查询操作记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="form", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="form", dataType="String"),
            @ApiImplicitParam(name="targetId", value="关联的操作对象id", required = true, paramType="form", dataType="String")
    })
    @RequestMapping(value = "/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R page(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  String targetId) {
        IPage<OperateRecord> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = operateRecordService.getPage(page, targetId);
        return R.ok(page);
    }

}

