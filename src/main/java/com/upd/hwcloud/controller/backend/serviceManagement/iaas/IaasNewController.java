package com.upd.hwcloud.controller.backend.serviceManagement.iaas;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.IaasNew;
import com.upd.hwcloud.bean.entity.Log;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.IpUtil;
import com.upd.hwcloud.common.utils.StringBooleanCheck;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IIaasNewService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * IaaS 表 前端控制器
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
@RestController
@RequestMapping("/iaasNew")
public class IaasNewController {

    @Autowired
    private IIaasNewService iaasNewService;
    @Autowired
    private IFilesService filesService;

    @ApiOperation("新增服务")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public R create(@LoginUser User user, @RequestBody IaasNew iaas) {
        iaas.setCreator(user.getIdcard());
        iaas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        verifyParams(iaas);
        iaas.insert();
        filesService.refFiles(iaas.getFileList(), iaas.getId());
        return R.ok(iaas.getId());
    }

    @ApiOperation("上/下线")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="服务id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="result", value="操作结果 1:上线,其它:下线", required = true, paramType="query", dataType="String"),
            @ApiImplicitParam(name="remark", value="操作描述", paramType="query", dataType="String"),
    })
    @RequestMapping(value = "/publish/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R publish(@LoginUser User user, @PathVariable String id, Integer result,
                     @RequestParam(required = false) String remark) {
        if (result == null) {
            return R.error("请选择操作结果");
        }
        iaasNewService.publish(user, id, result, remark);
        return R.ok();
    }
    @RequestMapping(value = "/sort/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R sort(@LoginUser User user, @PathVariable String id,String ope) {
        iaasNewService.serviceSort(id,ope);
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
            @ApiImplicitParam(name = "subType",value = "子类",paramType = "form", dataType = "String")
    })
    @RequestMapping(value = "/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R page(@LoginUser User user, @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false, defaultValue = "1") Integer status,
                  @RequestParam(required = false) String name, @RequestParam(required = false) String subType) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        IPage<IaasNew> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = iaasNewService.getPage(page, user, status, name, subType);
        return R.ok(page);
    }

    @ApiOperation("删除服务")
    @ApiImplicitParam(name="id", value="服务id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R delete(@LoginUser User user, @PathVariable String id) {
        IaasNew iaas = iaasNewService.getById(id);
        iaas.setStatus(ReviewStatus.DELETE.getCode());
        iaas.updateById();
        new Log(user.getIdcard(),"IaaS服务id："+id,"删除服务", IpUtil.getIp()).insert();
        return R.ok();
    }

    @ApiOperation("修改服务信息")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public R edit(@LoginUser User user, @RequestBody IaasNew iaas) {
        verifyParams(iaas);
        if (StringUtils.isEmpty(iaas.getCreator())) {
            iaas.setCreator(null);
        }
        iaas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        iaas.updateById();
        filesService.refFiles(iaas.getFileList(), iaas.getId());
        return R.ok(iaas.getId());
    }

    @ApiOperation("服务信息详情")
    @ApiImplicitParam(name="id", value="服务id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R detail(@LoginUser User user, @PathVariable String id) {
        IaasNew iaas = iaasNewService.getDetail(user, id);
        return R.ok(iaas);
    }
    @ApiOperation("流程配置")
    @ApiImplicitParams({ @ApiImplicitParam(name="id", value="服务id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="flowId", value="流程id", required = true, paramType="path", dataType="String")})
    @RequestMapping(value = "set/{id}/{flowId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R setflow(@LoginUser User user, @PathVariable String id,@PathVariable String flowId) {
        IaasNew iaas = new IaasNew();
        iaas.setId(id);
        iaas.setWorkFlowId(flowId);
        iaasNewService.updateById(iaas);
        return R.ok(iaas);
    }
    /**
     * 校验参数
     */
    private void verifyParams(IaasNew iaas) {

        if (!StringBooleanCheck.check(iaas.getCanApplication())) {
            throw new BaseException("参数错误");
        }
    }
}

