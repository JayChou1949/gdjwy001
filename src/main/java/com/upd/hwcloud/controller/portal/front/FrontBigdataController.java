package com.upd.hwcloud.controller.portal.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.entity.Bigdata;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.IBigdataService;
import com.upd.hwcloud.service.application.IShoppingCartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(description = "门户网站大数据目录接口")
@RestController
@RequestMapping("/api/bigdata")
public class FrontBigdataController {

    @Autowired
    private IBigdataService bigdataService;

    @Autowired
    private IShoppingCartService shoppingCartService;

    @ApiOperation("分页查询大数据目录")
    @RequestMapping("/page")
    public R page(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false) String name,
                  @RequestParam(required = false) String dataFrom,
                  @RequestParam(required = false) String collectionUnit,
                  @RequestParam(required = false) String category) {
        Page<Bigdata> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page = bigdataService.getPage(page,name,dataFrom,collectionUnit,category);
        return R.ok(page);
    }

    @ApiOperation("分页查询大数据目录")
    @RequestMapping(value = "/v2/page",method = {RequestMethod.GET,RequestMethod.POST})
    public R pageV2(@LoginUser User user,@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                    @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                    @RequestParam(required = false) String name,
                    @RequestParam(required = false) String dataFrom,
                    @RequestParam(required = false) String collectionUnit,
                    @RequestParam(required = false) String category) {
        Page<Bigdata> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page = bigdataService.getPage(page,name,dataFrom,collectionUnit,category);
        List<Bigdata> records = page.getRecords();
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(new QueryWrapper<ShoppingCart>().lambda()
                                                                        .eq(ShoppingCart::getCreatorIdCard,user.getIdcard())
                                                                        .eq(ShoppingCart::getResourceType, ResourceType.DAAS.getCode())
                                                                        .isNotNull(ShoppingCart::getDsId));

        //一个用户购物车下不能有多个同类DaaS服务
        if(CollectionUtils.isNotEmpty(shoppingCartList)){
            List<String> apiGuidList = shoppingCartList.stream().map(ShoppingCart::getDsId).distinct().collect(Collectors.toList());
            for(Bigdata bigdata:records){
                if(apiGuidList.contains(bigdata.getApigGuid())){
                    //如果购物车类存在，标记不能添加到购物车中
                    bigdata.setCanAddShoppingCart(false);
                }
            }
        }
        return R.ok(page);
    }

    @ApiOperation("获取一键申请页面表格列配置")
    @RequestMapping(value = "/get/columnConfig", method = RequestMethod.GET)
    public R getColumnConfig() {
        List config = bigdataService.getColumnConfig();
        return R.ok(config);
    }

}
