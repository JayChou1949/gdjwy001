package com.hirisun.cloud.user.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.model.param.WorkflowNodeParam;
import com.hirisun.cloud.model.user.OrgVO;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.user.bean.Org;
import com.hirisun.cloud.user.service.OrgService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 * 机构表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@Controller
@RequestMapping("/org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    /**
     * 根据参数获取机构信息
     */
    @ApiIgnore
    @ApiOperation("根据参数获取机构信息")
    @PostMapping("/feign/getOrgByParams")
    public List<OrgVO> getOrgByParams(@RequestBody OrgVO vo) {
        LambdaQueryWrapper<Org> wrapper = new QueryWrapper<Org>().lambda();
        if (vo.getId() != null) {
            wrapper.eq(Org::getId, vo.getId());
        }
        if (vo.getFullName() != null) {
            wrapper.eq(Org::getFullName, vo.getFullName());
        }
        List<Org> orgList = orgService.list(wrapper);
        if(CollectionUtils.isNotEmpty(orgList)) {
            List<OrgVO> list = JSON.parseObject(JSON.toJSON(orgList).toString(),
                    new TypeReference<List<OrgVO>>(){});
            return list;
        }
        return null;
    }
}

