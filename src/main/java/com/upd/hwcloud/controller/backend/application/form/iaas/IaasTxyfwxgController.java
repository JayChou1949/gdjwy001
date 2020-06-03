package com.upd.hwcloud.controller.backend.application.form.iaas;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;

import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.IaasTxyfwxg;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.service.application.IIaasTxyfwxgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhangwy
 * @since 2019-09-11
 */
@Api(description = "弹性云服务（扩容/缩容）")
@Controller
@RequestMapping("/iaasTxyfwxg")
public class IaasTxyfwxgController {
    @Autowired
    private IIaasTxyfwxgService iaasTxyfwxgService;

    @ApiOperation("导出excel")
    @RequestMapping("/exportExcel/{id}")
    public void exportExcel(@LoginUser User user, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<IaasTxyfwxg> datas = iaasTxyfwxgService.getByAppInfoId(id);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"服务申请信息"), IaasTxyfwxg.class, datas);
        ExcelUtil.export(request, response, "弹性云服务（扩容/缩容）申请信息表", workbook);
    }
}

