package com.upd.hwcloud.controller.backend.common;

import com.alibaba.fastjson.JSONObject;
import com.upd.hwcloud.bean.entity.application.IaasTxyfwImpl;
import com.upd.hwcloud.bean.entity.application.IaasYzmyzyUser;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.utils.ExcelUtil;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author wb
 * @date 2019/10/16
 */
@Api(description = "导入")
@Controller
@RequestMapping("/import")
public class ImportController {

    @ApiOperation("弹性云业务办理导入excel")
    @RequestMapping(value = "/excel/txyfw",method = RequestMethod.POST)
    @ResponseBody
    public R importEcs(@RequestParam(value = "file") MultipartFile file) throws Exception{
        //String filename = file.getOriginalFilename();
        //String suffix = filename.substring(filename.indexOf("."));
       /* List<IaasTxyfwImpl> txyfwImpl = new ArrayList<>();
        if(file !=null){
            //创建sheet表单
            Sheet sheet = ExcelUtil.getSheet(file);
            for(int i=1;i<sheet.getLastRowNum()+1;i++){
                //遍历每一行，获取每一个单元格的数据
                try{
                    IaasTxyfwImpl obj = new IaasTxyfwImpl();
                    Row row = sheet.getRow(i);
                    obj.setComponent(ExcelUtil.getStringCellValue(row,0));//应用组件
                    obj.setAppType(ExcelUtil.getStringCellValue(row,1));//应用类型
                    obj.setSpecification(ExcelUtil.getStringCellValue(row,2));//规格名称
                    obj.setStorage(ExcelUtil.getStringCellValue(row,3));//数据盘存储
                    obj.setOs(ExcelUtil.getStringCellValue(row,4));//系统
                    obj.setNet(ExcelUtil.getStringCellValue(row,5));//网络
                    obj.setServerId(ExcelUtil.getStringCellValue(row,6));//实例id
                    obj.setAccessIp(ExcelUtil.getStringCellValue(row,7));//公安网访问ip
                    obj.setServerPort(ExcelUtil.getStringCellValue(row,8));//开放端口
                    obj.setUserName(ExcelUtil.getStringCellValue(row,9));//用户名
                    obj.setPassword(ExcelUtil.getStringCellValue(row,10));//初始密码
                    obj.setServerIp1(ExcelUtil.getStringCellValue(row,11));//ip
                    String isFf =  ExcelUtil.getStringCellValue(row,12);
                    obj.setIsFf("是".equals(isFf)?"1":"0");//是否发放 0 否 1 是
                    txyfwImpl.add(obj);
                }catch (Exception e){
                    e.printStackTrace();
                    return R.error(e.getMessage());
                }

            }
            return R.ok(txyfwImpl);
        }else{
            return R.error("文件异常，请重新上传");
        }*/
        ImportParams params = new ImportParams();
        List<IaasTxyfwImpl> implList  = ExcelImportUtil.importExcel(file.getInputStream(),IaasTxyfwImpl.class,params);
        return R.ok(implList);
    }


    /**
     *
     * @param file 文件
     * @return
     */
    @ApiOperation("云桌面业务办理导入excel")
    @RequestMapping("/excel/yzmfw")
    @ResponseBody
    public R importYzmdata(@RequestParam(value = "file") MultipartFile file){
        List<IaasYzmyzyUser> yzmUsers = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        IaasYzmyzyUser obj = null;
        Row row = null;
        if(file !=null){
            Sheet sheet = ExcelUtil.getSheet(file);
            for(int i=1;i<sheet.getLastRowNum()+1;i++){
                try {
                    //遍历每一行，获取每一个单元格的数据
                     obj = new IaasYzmyzyUser();
                     row = sheet.getRow(i);
                     //obj.setUserStatus(row.getCell(0).getStringCellValue());//状态
                     obj.setName(ExcelUtil.getStringCellValue(row,0));//姓名
                     obj.setIdcard(ExcelUtil.getStringCellValue(row,1));//身份证号
                     obj.setPhone(ExcelUtil.getStringCellValue(row,2));//联系电话
                     obj.setCompany(ExcelUtil.getStringCellValue(row,3));//归属单位
                     String userType = ExcelUtil.getStringCellValue(row,4);
                     obj.setUserType("民警".equals(userType)?"1":"2");//身份  1 民警 2 开发人员
                     String sex = ExcelUtil.getStringCellValue(row,5);
                     obj.setSex("男".equals(sex)?"1":"2");//性别 1男2女
                     obj.setYzyIp(ExcelUtil.getStringCellValue(row,6));//云桌面ip
                     obj.setAccount(ExcelUtil.getStringCellValue(row,7));//账户名称
                     obj.setPassword(ExcelUtil.getStringCellValue(row,8));//账户密码
                    yzmUsers.add(obj);
                }catch (Exception e){
                    e.printStackTrace();
                    return R.error(e.getMessage());
                }
            }
            return R.ok(yzmUsers);
        }else{
            return R.error("文件异常，请重新上传");
        }
    }
}
