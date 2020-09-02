package com.hirisun.cloud.api.user;

import com.hirisun.cloud.model.user.OrgVO;
import com.hirisun.cloud.model.user.UserVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * @author wuxiaoxing
 * @date 2020-08-07
 * @description
 */
@FeignClient("cloud-user-service")
public interface OrgApi {

    /**
     * 根据参数获取机构信息
     */
    @ApiIgnore
    @ApiOperation("根据参数获取机构信息")
    @PostMapping("/org/feign/getOrgByParams")
    public List<OrgVO> getOrgByParams(@RequestBody OrgVO vo);
}
