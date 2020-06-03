package com.upd.hwcloud.controller.backend.application.form.iaas;

import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.IaasTxyfw;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.service.application.IIaasTxyfwService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * Created by zwb on 2019/4/23.
 */
@Api(description = "弹性云服务")
@RestController
@RequestMapping("/iaasTxyfw")
public class IaasTxyfwController {
    @Autowired
    private IIaasTxyfwService iaasTxyfwService;

    @ApiOperation("导出excel")
    @RequestMapping("/exportExcel/{id}")
    public void exportExcel(@LoginUser User user, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<IaasTxyfw> datas = iaasTxyfwService.getByAppInfoId(id);
        HSSFWorkbook excle = ExcelUtil.createExcel(datas, IaasTxyfw.class, "服务申请信息");
      ExcelUtil.export(request,response,"弹性云服务申请信息表",excle);

    }
}
