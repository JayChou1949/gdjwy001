package com.upd.hwcloud.controller.trash;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.contains.UserType;
import com.upd.hwcloud.bean.entity.Iaas;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.common.utils.StringBooleanCheck;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.common.utils.easypoi.ExportMoreView;
import com.upd.hwcloud.common.utils.easypoi.ExportView;
import com.upd.hwcloud.service.IIaasService;
import com.upd.hwcloud.service.IOperateRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * IaaS 表 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
@Api(description = "IaaS服务管理")
@Controller
@RequestMapping("/iaas")
public class IaasController {

    @Autowired
    private IIaasService iaasService;
    @Autowired
    private IOperateRecordService operateRecordService;


    @ApiOperation("新增服务")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public R create(@LoginUser User user, @RequestBody Iaas iaas) {
        iaas.setId(UUIDUtil.getUUID());
        iaas.setCreator(user.getIdcard());
        iaas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        verifyParams(iaas);
        iaas.insert();
        return R.ok();
    }

    @ApiOperation("上/下线")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "服务id", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "result", value = "操作结果 1:上线,其它:下线", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "操作描述", paramType = "query", dataType = "String"),
    })
    @RequestMapping(value = "/publish/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R publish(@LoginUser User user, @PathVariable String id, Integer result,
                     @RequestParam(required = false) String remark) {
        if (result == null) {
            return R.error("请选择操作结果");
        }
        iaasService.publish(user, id, result, remark);
        return R.ok();
    }

    @RequestMapping(value = "/sort/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R sort(@LoginUser User user, @PathVariable String id, String ope) {
        iaasService.serviceSort(id, ope);
        return R.ok();
    }

    /**
     * 分页
     *
     * @param status {@link ReviewStatus}
     */
    @ApiOperation("分页查询服务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "一页的数据量", defaultValue = "20", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "服务名", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "subType", value = "子类", paramType = "form", dataType = "String")
    })
    @RequestMapping(value = "/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R page(@LoginUser User user, @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize, Integer status,
                  @RequestParam(required = false) String name, @RequestParam(required = false) String subType) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        IPage<Iaas> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = iaasService.getPage(page, user, status, name, subType);
        return R.ok(page);
    }

    @ApiOperation("删除服务")
    @ApiImplicitParam(name = "id", value = "服务id", required = true, paramType = "path", dataType = "String")
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R delete(@LoginUser User user, @PathVariable String id) {
        Iaas iaas = iaasService.getById(id);
        iaas.setStatus(ReviewStatus.DELETE.getCode());
        iaas.updateById();
        operateRecordService.insertRecord(id, user.getIdcard(), "删除", "删除", null);
        return R.ok();
    }

    @ApiOperation("修改服务信息")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public R edit(@LoginUser User user, @RequestBody Iaas iaas) {
        verifyParams(iaas);
        if (StringUtils.isEmpty(iaas.getCreator())) {
            iaas.setCreator(null);
        }
        iaas.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        iaas.updateById();
        return R.ok();
    }

    @ApiOperation("服务信息详情")
    @ApiImplicitParam(name = "id", value = "服务id", required = true, paramType = "path", dataType = "String")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R detail(@LoginUser User user, @PathVariable String id) {
        Iaas iaas = iaasService.getDetail(user, id);
        return R.ok(iaas);
    }

    @ApiOperation("流程配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "服务id", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "flowId", value = "流程id", required = true, paramType = "path", dataType = "String")})
    @RequestMapping(value = "set/{id}/{flowId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R setflow(@LoginUser User user, @PathVariable String id, @PathVariable String flowId) {
        Iaas iaas = new Iaas();
        iaas.setId(id);
        iaas.setWorkFlowId(flowId);
        iaasService.updateById(iaas);
        return R.ok(iaas);
    }

    /**
     * 校验参数
     */
    private void verifyParams(Iaas iaas) {
        if (!StringBooleanCheck.check(iaas.getHome())) {
            throw new BaseException("参数错误");
        }
        if (!StringBooleanCheck.check(iaas.getCanApplication())) {
            throw new BaseException("参数错误");
        }
        if (!StringBooleanCheck.check(iaas.getHasDoc())) {
            throw new BaseException("参数错误");
        }
    }


    @ApiOperation("IAAS业务办理工单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="areas", value="地区", paramType="query", dataType="String"),
            @ApiImplicitParam(name="policeCategory", value="警种", paramType="query", dataType="String"),
            @ApiImplicitParam(name="submitStartDate", value="提交申请开始日期", required = true, paramType="query", dataType="String"),
            @ApiImplicitParam(name="submitEndDate", value="提交申请结束日期", required = true, paramType="query", dataType="String"),
            @ApiImplicitParam(name="bigdataStartDate", value="大数据办审核开始日期", required = true, paramType="query", dataType="String"),
            @ApiImplicitParam(name="bigdataEndDate", value="大数据办审核结束日期", required = true, paramType="query", dataType="String"),
            @ApiImplicitParam(name="carryStartDate", value="业务办理开始日期", required = true, paramType="query", dataType="String"),
            @ApiImplicitParam(name="carryEndDate", value="业务办理结束日期", required = true, paramType="query", dataType="String")
    })
    @RequestMapping(value = "/export/iaasOrderTotal", method = RequestMethod.GET)
    public void iaasOrderTotal(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(required = false) String areas, @RequestParam(required = false) String policeCategory,
                               String submitStartDate, String submitEndDate, String bigdataStartDate, String bigdataEndDate,
                               String carryStartDate, String carryEndDate) throws Exception {
        //组装参数
        if (UserType.TENANT_MANAGER.getCode().equals(user.getType())) {
            areas = user.getTenantArea();
            policeCategory = user.getTenantPoliceCategory();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("areas", areas);
        params.put("policeCategory", policeCategory);
        params.put("submitStartDate", submitStartDate);
        params.put("submitEndDate", submitEndDate);
        params.put("bigdataStartDate", bigdataStartDate);
        params.put("bigdataEndDate", bigdataEndDate);
        params.put("carryStartDate", carryStartDate);
        params.put("carryEndDate", carryEndDate);
        params.put("status",3);//使用中
       //多sheet导出
        List<ExportView> moreViewList = Lists.newArrayList();
        moreViewList.add(iaasService.getTanxingyunData(params));//弹性云
        moreViewList.add(iaasService.getBiangengData(params));//变更
        moreViewList.add(iaasService.getDashujuData(params));//大数据组件
        moreViewList.add(iaasService.getZhuomianyunData(params));//桌面云
        moreViewList.add(iaasService.getDuixiangCCData(params));//对象存储
        moreViewList.add(iaasService.getWenjianCCData(params));//文件存储
        moreViewList.add(iaasService.getTXFuzaijunhengData(params));//弹性负载均衡
        ExportMoreView moreView = new ExportMoreView();
        moreView.setMoreViewList(moreViewList);
        ExcelUtil.exportMoreView(request, response, "IAAS业务办理工单统计表", moreView);
    }
}



