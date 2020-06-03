package com.upd.hwcloud.controller.portal.front;

import com.upd.hwcloud.bean.entity.Dict;
import com.upd.hwcloud.bean.entity.UserDoc;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.IDictService;
import com.upd.hwcloud.service.IUserDocService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/userDoc")
public class ApiUserDocController {

    @Autowired
    private IUserDocService userDocService;
    @Autowired
    private IDictService dictService;

    @ApiOperation("根据不同业务获取相关的操作文档")
    @RequestMapping(value = "/getDocs/{domainValue}", method = RequestMethod.GET)
    public R getDocsByDomain(@PathVariable("domainValue") String domainValue) {
        List<Dict> child = dictService.getChildByValue("userDocDomain");
        Dict dict = child.stream().filter(it -> Objects.equals(domainValue, it.getValue())).findFirst().orElse(null);
        List<UserDoc> userDocList = userDocService.getDocsByDomain(dict.getName());
        return R.ok(userDocList);
    }

}
