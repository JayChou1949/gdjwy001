package com.upd.hwcloud.controller.backend.common;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upd.hwcloud.bean.entity.Draft;
import com.upd.hwcloud.bean.entity.IaasStatistic.Data;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IDraftService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuc
 * @since 2020-01-03
 */
@Api(description = "草稿接口")
@RestController
@RequestMapping("/draft")
public class DraftController {

    @Autowired
    private IDraftService draftService;

    @ApiOperation("保存服务发布草稿")
    @RequestMapping(value = "/public",method = RequestMethod.POST)
    public R save(@RequestBody Draft draft){
        if(StringUtils.isBlank(draft.getIdcard())){
            return R.error("参数错误");
        }
        int count = draftService.count(new QueryWrapper<Draft>().lambda().eq(Draft::getIdcard,draft.getIdcard()).eq(Draft::getType,draft.getType()));
        boolean result = false;
        if(count==0){
            draft.setCreateTime(new Date());
            draft.setModifiedTime(new Date());
            result =draft.insert();
        }else{
            draft.setModifiedTime(new Date());
            result = draftService.update(new Draft(),new UpdateWrapper<Draft>().lambda().set(Draft::getData,draft.getData())
                                .eq(Draft::getIdcard,draft.getIdcard()).eq(Draft::getType,draft.getType()));
        }
        return result(result);
    }


    /**
     * 结果状态
     * @param result
     * @return
     */
    private R result(boolean result){
        if(result){
            return R.ok("操作成功");
        }else {
            return R.error("操作失败");
        }
    }
    @ApiOperation("获取服务发布草稿")
    @RequestMapping(value = "/public/{idcard}",method = RequestMethod.GET)
    public R getPublicDraft(@PathVariable("idcard") String idcard){
        Draft draft = draftService.getOne(new QueryWrapper<Draft>().lambda().eq(Draft::getIdcard,idcard).eq(Draft::getType,0));
        return R.ok(draft);
    }

    @ApiOperation("删除服务发布草稿")
    @RequestMapping(value = "/public/delete/{idcard}",method = RequestMethod.GET)
    public R deletePublicDraft(@PathVariable("idcard") String idcard){
        boolean res = draftService.remove(new QueryWrapper<Draft>().lambda().eq(Draft::getIdcard,idcard).eq(Draft::getType,0));
        return R.ok("删除 "+idcard+" 发布草稿: "+res);
    }

}

