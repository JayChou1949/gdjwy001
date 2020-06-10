package com.upd.hwcloud.controller.backend.system.wfm;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ServiceWorkFlow;
import com.upd.hwcloud.bean.entity.wfm.Workflow;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.wfm.WorkFlowVo;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.log.OperationLog;
import com.upd.hwcloud.service.IUserService;
import com.upd.hwcloud.service.application.IServiceWorkFlowService;
import com.upd.hwcloud.service.wfm.IWorkflowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwb
 * @since 2019-04-10
 */
@Api(description = "流程配置")
@RestController
@RequestMapping("/workflow")
public class WorkflowController {
    @Autowired
    IWorkflowService workflowService;
    @Autowired
    private IServiceWorkFlowService serviceWorkFlowService;
    @Autowired
    private IUserService userService;

    //todo:二级门户改造-workflow新增国家专项，对应更新修改 (省厅，警种，地市，国家专项。同一类资源只能有一个默认流程)，目前修改使用的/beta/edit

    @ApiOperation("新增流程配置")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public R add(@LoginUser User user, @RequestBody Workflow flow) {
        //权限校验
        if(!userService.isFlowPermission(user,flow.getArea())){
            return R.error("无权限操作该流程");
        }
        flow.setVersion(0);
        flow = workflowService.saveFlow(user, flow);
        return R.ok(flow);
    }
    @ApiOperation("修改流程配置")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public R update(@LoginUser User user, @RequestBody Workflow flow) {
        //权限校验
        if(!userService.isFlowPermission(user,flow.getArea())){
            return R.error("无权限操作该流程");
        }
        flow = workflowService.updateFlow(user, flow);
        return R.ok(flow);
    }

    @ApiOperation("修改流程配置-beta")
    @RequestMapping(value = "/beta/edit", method = RequestMethod.POST)
    public R updateBeta(@LoginUser User user, @RequestBody Workflow flow) {
        //权限校验
        if(!userService.isFlowPermission(user,flow.getArea())){
            return R.error("无权限操作该流程");
        }
        boolean res  = workflowService.updateFlowBeta(flow);
        if(res){
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation("详情")
    @ApiImplicitParam(name="id", value="流程id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R detail(@PathVariable String id,Integer version) {
        Workflow flow = workflowService.getDetail(id,version);
        return R.ok(flow);
    }


    @ApiOperation("详情-beta")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="流程id", paramType="path", dataType="String"),
            @ApiImplicitParam(name="version", value="版本号",paramType="form", dataType="String"),
    })
    @RequestMapping(value = "/beta/{id}",method = RequestMethod.GET)
    public R detailBeta(@PathVariable String id,Integer version){
        WorkFlowVo vo = workflowService.getFlowVo(id,version);
        return R.ok(vo);
    }

    @ApiOperation("删除")
    @ApiImplicitParam(name="id", value="id", required = true, paramType="path", dataType="String")
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(operation = "删除流程")
    @RequestMapping(value = "delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R delete(@LoginUser User user,@PathVariable String id) {

        Workflow workflow = workflowService.getById(id);
        if(workflow == null){
            return R.error("该流程不存在");
        }
        if(!userService.isFlowPermission(user,workflow.getArea())){
            return R.error("无权删除该流程");
        }
        workflowService.deleteFlow(id);
        return R.ok(id);
    }
    @ApiOperation("分页查询流程配置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="form", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="form", dataType="String"),
            @ApiImplicitParam(name="name", value="流程配置名", paramType="form", dataType="String"),
            @ApiImplicitParam(name="area", value="地市名", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public R page(@LoginUser User user,@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false) String name,
                  @RequestParam(required = false) String area,
                  @RequestParam(required = false) String policeCategory) {
        IPage<Workflow> page = new Page<>(pageNum,pageSize);
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        QueryWrapper<Workflow> wrapper = new QueryWrapper<Workflow>();

        //区域不为空,警种为空 （省厅和 警种）
        if(StringUtils.isNotBlank(area) && StringUtils.isBlank(policeCategory)){
            if(!userService.isFlowPermission(user,area)){
                return R.error("无权查看该区域数据");
            }
            wrapper.eq("area",area.trim()).isNull("POLICE_CATEGORY");
        }else if(StringUtils.isNotBlank(policeCategory) && StringUtils.equals(area,"省厅")){ //警种
            wrapper.eq("POLICE_CATEGORY",policeCategory).eq("AREA","省厅");
        }else {//区域为空=>全部区域（只有管理员能看）
            if(!userService.isManager(user.getType())){
                return R.error("无权查看全部区域数据");
            }
        }
        wrapper.eq("flow_status",0).like("workflowname",name==null?"":name.trim()).orderByDesc("create_time");
        page = workflowService.page(page,wrapper);
        return R.ok(page);
    }

    @ApiOperation("流程配置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name", value="流程配置名", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(String name) {

        QueryWrapper<Workflow> wrapper = new QueryWrapper<Workflow>();
        wrapper.eq("flow_status",0).orderByDesc("create_time");
        return R.ok(workflowService.list(wrapper));
    }


    @ApiOperation("新-服务流程配置列表")
    @RequestMapping(value = "/service/{serviceId}/list", method = RequestMethod.GET)
    public R serviceFlowList(@PathVariable String serviceId,String name,String area,String policeCategory) {

        QueryWrapper<Workflow> wrapper = new QueryWrapper<Workflow>();
        wrapper.eq("flow_status",0).orderByDesc("create_time");
        if(StringUtils.isNotBlank(name)){
            wrapper.like("workflowname",name.trim());
        }
        if(StringUtils.isNotBlank(area)){
            wrapper.eq("area",area.trim());
        }
        if(StringUtils.isNotBlank(policeCategory)){
            wrapper.eq("POLICE_CATEGORY",policeCategory);
        }
        //获取所有IPDS流程
        List<Workflow> workflowList = workflowService.list(wrapper);

        //获取该服务的流程
        List<ServiceWorkFlow> serviceWorkFlowList = serviceWorkFlowService.list(new QueryWrapper<ServiceWorkFlow>().lambda().eq(ServiceWorkFlow::getServiceId,serviceId));
        if(CollectionUtils.isNotEmpty(serviceWorkFlowList)){
            workflowList.forEach(workflow -> {
                serviceWorkFlowList.forEach(item->{
                    //服务的流程,设置serviceFlowCheck为True.
                    if(StringUtils.equals(item.getWorkFlowId(),workflow.getId())){
                        workflow.setServiceFlowCheck(true);
                    }
                });
            });
        }

        return R.ok(workflowList);
    }

    @ApiOperation("注册上报流程配置列表")
    @RequestMapping(value = "/zcsb", method = RequestMethod.GET)
    public R zcsb() {
        List<Workflow> list = new ArrayList<>();
        Workflow zcsaas = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS", "ZCSAAS"));
        list.add(zcsaas);
        List<Workflow> publishList = workflowService.list(new QueryWrapper<Workflow>().likeRight("DEFAULT_PROCESS", "PUBLISH"));
        list.addAll(publishList);
        Workflow zysb = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS", "ZYSB"));
        list.add(zysb);
        return R.ok(list);
    }

    @ApiOperation("saas资源申请流程配置")
    @RequestMapping(value = "/saas", method = RequestMethod.GET)
    public R saas() {
        List<Workflow> list = new ArrayList<>(3);
        Workflow saas = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS", "SAAS"));
        list.add(saas);
        Workflow saasRecover = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS", "SAAS_POWER_RECOVER"));
        list.add(saasRecover);
        Workflow epidemic = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS", "EPIDEMIC"));
        list.add(epidemic);
        return R.ok(list);
    }

    @ApiOperation("资源回收申请流程配置")
    @RequestMapping(value = "/resource-recover")
    public R resourceRecover(){
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","RECOVER"));
        return R.ok(workflow);
    }

    @ApiOperation("用户是否在IaaS上报和SaaS回收流程")
    @RequestMapping(value = "/zysbAndRecover/status",method = RequestMethod.GET)
    public R zysbAndRecoverStatus(@LoginUser User user){
        if(user==null){
            return R.error("请登录");
        }
        return R.ok(workflowService.checkZysbAndRecover(user.getIdcard()));
    }


}

