package com.upd.hwcloud.controller.backend.serviceManagement.saas;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.StringBooleanCheck;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.IOperateRecordService;
import com.upd.hwcloud.service.ISaasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * SaaS 表 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
@Api(description = "SaaS服务管理")
@RestController
@RequestMapping("/saas")
public class SaasController {

    @Autowired
    private ISaasService saasService;
    @Autowired
    private IOperateRecordService operateRecordService;

    @ApiOperation("新增服务")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public R create(@LoginUser User user, @RequestBody Saas saas) {
        saas.setId(UUIDUtil.getUUID());
        saas.setCreator(user.getIdcard());
        saas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        verifyParams(saas);
        saas.insert();
        return R.ok(saas.getId());
    }
    @RequestMapping(value = "/sort/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public R sort(@LoginUser User user, @PathVariable String id,String ope) {
        saasService.serviceSort(id,ope);
        return R.ok();
    }

    @ApiOperation("上/下线")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="服务id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="result", value="操作结果 1:上线,其它:下线", required = true, paramType="query", dataType="String"),
            @ApiImplicitParam(name="remark", value="操作描述", paramType="query", dataType="String"),
    })
    @RequestMapping(value = "/publish/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public R publish(@LoginUser User user, @PathVariable String id, Integer result,
                     @RequestParam(required = false) String remark) {
        if (result == null) {
            return R.error("请选择操作结果");
        }
        saasService.publish(user, id, result, remark);
        return R.ok();
    }

    /**
     * 分页
     * @param status {@link ReviewStatus}
     */
    @ApiOperation("分页查询服务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="form", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="form", dataType="String"),
            @ApiImplicitParam(name="status", value="状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除", required = true, paramType="form", dataType="String"),
            @ApiImplicitParam(name="name", value="服务名", paramType="form", dataType="String"),
            @ApiImplicitParam(name = "subType",value = "子类",paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "subType",value = "子类",paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/page", method = {RequestMethod.GET, RequestMethod.POST})
    public R page(@LoginUser User user, @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize, Integer status,
                  @RequestParam(required = false) String name,@RequestParam(required = false) String subType,@RequestParam(required = false,defaultValue = "0") Integer serviceFlag,
                 @RequestParam(required = false) Integer pilotApp) {
        IPage<Saas> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = saasService.getPage(page, user, status, name, subType,serviceFlag,pilotApp);
        return R.ok(page);
    }

    @ApiOperation("删除服务")
    @ApiImplicitParam(name="id", value="服务id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public R delete(@LoginUser User user, @PathVariable String id) {
        Saas saas = saasService.getById(id);
        saas.setStatus(ReviewStatus.DELETE.getCode());
        saas.updateById();
        operateRecordService.insertRecord(id, user.getIdcard(), "删除", "删除", null);
        return R.ok();
    }

    @ApiOperation("修改服务信息")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public R edit(@LoginUser User user, @RequestBody Saas saas) {
        verifyParams(saas);
        if (StringUtils.isEmpty(saas.getCreator())) {
            saas.setCreator(null);
        }
        saas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        saas.updateById();
        return R.ok();
    }

    @ApiOperation("服务信息详情")
    @ApiImplicitParam(name="id", value="服务id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public R detail(@LoginUser User user, @PathVariable String id) {
        Saas saas = saasService.getDetail(user, id);
        return R.ok(saas);
    }
    @ApiOperation("流程配置")
    @ApiImplicitParams({ @ApiImplicitParam(name="id", value="服务id", required = true, paramType="path", dataType="String"),
    @ApiImplicitParam(name="flowId", value="流程id", required = true, paramType="path", dataType="String")})
    @RequestMapping(value = "set/{id}/{flowId}", method = {RequestMethod.GET, RequestMethod.POST})
    public R setflow(@LoginUser User user, @PathVariable String id,@PathVariable String flowId) {
        Saas saas = new Saas();
        saas.setId(id);
        saas.setWorkFlowId(flowId);
        saasService.updateById(saas);
        return R.ok(saas);
    }

    @ApiOperation("通过名字搜索SaaS服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name", value="服务名", paramType="path", dataType="String")
    })
    @RequestMapping(value = "/list/{name}", method = RequestMethod.GET)
    public R list(@PathVariable String name) {
        List<Saas> result = saasService.list(new QueryWrapper<Saas>().lambda()
                .eq(Saas::getName, name)
                .or()
                .eq(Saas::getShortName, name));
        return R.ok(result);
    }

    /**
     * 校验参数
     */
    private void verifyParams(Saas saas) {
        if (!StringBooleanCheck.check(saas.getHome())) {
            throw new BaseException("参数错误");
        }
        if (!StringBooleanCheck.check(saas.getCanApplication())) {
            throw new BaseException("参数错误");
        }
        if (!StringBooleanCheck.check(saas.getHasDoc())) {
            throw new BaseException("参数错误");
        }
        if (!StringBooleanCheck.check(saas.getSecret())) {
            throw new BaseException("参数错误");
        }
    }

}

