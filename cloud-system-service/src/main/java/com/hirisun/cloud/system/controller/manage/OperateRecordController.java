package com.hirisun.cloud.system.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.model.system.OperateRecordVo;
import com.hirisun.cloud.system.service.OperateRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "系统操作记录")
@RestController
@RequestMapping("/system/operate")
public class OperateRecordController {

	@Autowired
	private OperateRecordService operateRecordService;
	
	@ApiIgnore
    @ApiOperation("根据配置id验证是否存在上线的记录")
    @PostMapping("/exits")
    public boolean isNotEmpty(String configId) {
		return operateRecordService.validateExits(configId);
	}
	
	@ApiIgnore
    @ApiOperation("新增操作记录")
    @PostMapping("/save")
    public void save(@RequestBody OperateRecordVo vo) {
		operateRecordService.insertRecord(vo);
	}
	
	@ApiIgnore
    @ApiOperation("新增最新操作记录")
    @PostMapping("/save/latest")
    public void saveLatest(@RequestBody OperateRecordVo vo) {
		operateRecordService.insertLatestOnline(vo);
	}
}
