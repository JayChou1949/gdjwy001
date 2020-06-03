package com.upd.hwcloud.controller.backend.css;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.Log;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.UserDoc;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.IpUtil;
import com.upd.hwcloud.service.IUserDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户操作文档管理 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2019-09-25
 */
@Api(description = "用户操作手册管理")
@RestController
@RequestMapping("/userDoc")
public class UserDocController {

    @Autowired
    private IUserDocService userDocService;

    @ApiOperation("新增文档")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public R create(@LoginUser User user, @RequestBody UserDoc userDoc) {
        userDocService.saveUserDoc(user, userDoc);
        return R.ok(userDoc.getId());
    }

    @ApiOperation("上/下线")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="服务id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="result", value="操作结果 1:上线,其它:下线", required = true, paramType="query", dataType="String")
    })
    @RequestMapping(value = "/publish/{id}", method = RequestMethod.GET)
    public R publish(@LoginUser User user, @PathVariable String id, Integer result) {
        if (result == null) {
            return R.error("请选择操作结果");
        }
        UserDoc userDoc = new UserDoc();
        userDoc.setId(id);
        if (result.equals(1)) {
            userDoc.setStatus(ReviewStatus.ONLINE.getCode());
        } else {
            userDoc.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        }
        userDocService.updateById(userDoc);
        return R.ok();
    }

    /**
     * 分页
     * @param status {@link ReviewStatus}
     */
    @ApiOperation("分页查询文档列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="form", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="form", dataType="String"),
            @ApiImplicitParam(name="status", value="状态,  1: 待上线 2: 上线 ", required = true, paramType="form", dataType="String"),
            @ApiImplicitParam(name="name", value="文件名", paramType="form", dataType="String"),
            @ApiImplicitParam(name = "domain",value = "适用业务",paramType = "form", dataType = "String")
    })
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public R page(@LoginUser User user, @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize, Integer status,
                  @RequestParam(required = false) String name, @RequestParam(required = false) String domain) {
        IPage<UserDoc> page = new Page<>(pageNum, pageSize);
        page = userDocService.getPage(page, user, status, name, domain);
        return R.ok(page);
    }

    @ApiOperation("删除文档")
    @ApiImplicitParam(name="id", value="文档id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public R delete(@LoginUser User user, @PathVariable String id) {
        if (userDocService.removeById(id)) {
            new Log(user.getIdcard(),"删除文档","删除文档", IpUtil.getIp()).insert();
            return R.ok();
        } else {
            return R.error("删除失败");
        }
    }

    @ApiOperation("修改文档信息")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public R edit(@LoginUser User user, @RequestBody UserDoc userDoc) {
        userDocService.updateUserDoc(userDoc);
        return R.ok();
    }

    @ApiOperation("文档信息详情")
    @ApiImplicitParam(name="id", value="文档id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R detail(@LoginUser User user, @PathVariable String id) {
        UserDoc userDoc = userDocService.getDetail(user, id);
        return R.ok(userDoc);
    }

}

