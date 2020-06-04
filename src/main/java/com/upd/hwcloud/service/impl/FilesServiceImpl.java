package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.application.PaasTyyh;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.dao.FilesMapper;
import com.upd.hwcloud.service.IFilesService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  文件 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-10-25
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

    @Autowired
    FilesMapper filesMapper;
    @Value("${file.path}")
    private String filePath;

    private final static  String sourceFileName = "ncovSource.xlsx";

    private final static String clusterFileName = "ncovCluster.xlsx";

    private final static  String ecsFileName = "ncovEcsSource.xlsx";

    //防疫大数据模块文件名映射
    private static final Map<String,String> NCOV_BIG_DATA_FILE = ImmutableMap.<String,String>builder()
            .put("dataSharing","数据共享情况V1.xlsx").put("dataModeling","数据建模情况V1.xlsx")
            .put("dataGovernance","质量分析情况清单.xls").put("dataAccess","数据接入情况.xlsx")
            .put("dataService","地市疫情防控数据.xlsx").build();


    private static final Map<String,String> NCOV_BIG_DATA_TITLE = ImmutableMap.<String,String>builder()
            .put("dataSharing","防疫大数据-服务共享").put("dataModeling","防疫大数据-数据建模")
            .put("dataGovernance","防疫大数据-数据治理").put("dataAccess","防疫大数据-数据接入")
            .put("dataService","防疫大数据-数据服务").build();


    @Override
    public Files upload(MultipartFile file,String folder) throws IOException {
        Files files = saveFile(file, folder);
        files.insert();
        return files;
    }

    @Override
    public Files uploadNcovDataFile(MultipartFile file) throws IOException{
        Files files;
        Files old  = this.getOne(new QueryWrapper<Files>().lambda().eq(Files::getName,sourceFileName));
        if(old == null){
            files  = saveNcovDataFile(file,filePath,sourceFileName,"疫情接入数据源");
            files.insert();
        }else {
            files  = updateNcovDataFile(file,old.getId(),filePath,sourceFileName,"疫情接入数据源");
            files.updateById();
        }
        return files;
    }


    public Files uploadNcovReport(MultipartFile file,String title) throws  IOException{
        Files files;
        Files old = this.getOne(new QueryWrapper<Files>().lambda().eq(Files::getTitle,title));
        if(old == null){
            files = saveNcovReport(file,title);
            files.insert();
        }else {
            files = updateNcovReport(file,old.getId(),title,old.getName());
            files.updateById();
        }
        return files;
    }

    @Override
    public Files uploadClusterDataFile(MultipartFile file) throws IOException {
        Files files;
        Files old  = this.getOne(new QueryWrapper<Files>().lambda().eq(Files::getName,clusterFileName));
        if(old == null){
            files  = saveNcovDataFile(file,filePath,clusterFileName,"疫情集群数据源");
            files.insert();
        }else {
            files  = updateNcovDataFile(file,old.getId(),filePath,clusterFileName,"疫情集群数据源");
            files.updateById();
        }
        return files;
    }

    @Override
    public Files uploadEcsDataFile(MultipartFile file) throws IOException {
        Files files;
        Files old  = this.getOne(new QueryWrapper<Files>().lambda().eq(Files::getName,ecsFileName));
        if(old == null){
            files  = saveNcovDataFile(file,filePath,ecsFileName,"疫情虚拟机数据源");
            files.insert();
        }else {
            files  = updateNcovDataFile(file,old.getId(),filePath,ecsFileName,"疫情虚拟机数据源");
            files.updateById();
        }
        return files;
    }


    @Override
    public Files ncovDataAreaUpload(MultipartFile file,String module) throws  IOException{
        Files files;
        String filesName = NCOV_BIG_DATA_FILE.get(module);
        if(StringUtils.isBlank(filesName)){
            throw new BaseException("无效路径参数");
        }
        String titile = NCOV_BIG_DATA_TITLE.get(module);
        Files old = this.getOne(new QueryWrapper<Files>().lambda().eq(Files::getName,filesName));
        if(old == null){
            files = saveNcovDataFile(file,filePath+"//ncovArea",filesName,titile);
            files.insert();
        }else {
            files = updateNcovDataFile(file,old.getId(),filePath+"//ncovArea",filesName,titile);
            files.updateById();
        }
        return files;
    }

    @Override
    public void download(String fileId, HttpServletResponse response) throws IOException {
        List<Files> list = filesMapper.selectList(new QueryWrapper<Files>().eq("NAME",fileId));
        if (list == null || list.isEmpty()) {
            return;
        }
        Files save = list.get(0);
        File file = new File(save.getPath()+"/"+save.getName()+save.getSuffix());
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            String originaName;
            try {
                originaName = URLEncoder.encode(save.getOriginaName(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                originaName = save.getOriginaName();
            }
            response.addHeader("Content-Disposition","attachment;fileName=" + originaName);// 设置文件名

            FileUtils.copyFile(file, response.getOutputStream());
        }
    }

    @Override
    public Files update(MultipartFile file,String id,String folder) throws IOException {
        Files files = new Files();
        String fileName = file.getOriginalFilename();
        files.setOriginaName(fileName);
        String strs[] = fileName.split("[.]");
        String suffix = strs[strs.length - 1];
        files.setSuffix("."+suffix);
        UUID uuid = UUID.randomUUID();
        files.setName(uuid.toString());//设置图片名
        String newFileName = uuid + "."+ suffix;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        String path = filePath+"/"+folder+"/"+time;
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        files.setPath(path);
        File targetFile = new File(path, newFileName);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(targetFile));
        out.write(file.getBytes());
        out.flush();
        out.close();
        files.setUrl("/files/download/"+newFileName);
        files.setId(id);
        files.updateById();
        return files;
    }
    @Override
    public void refFiles(List<Files> files, String refId) {
        if (StringUtils.isEmpty(refId)) {
            return;
        }
        this.remove(new QueryWrapper<Files>().lambda().eq(Files::getRefId, refId));
        if (files != null && !files.isEmpty()) {
            for (Files f : files) {
                f.setId(null);
                f.setRefId(refId);
            }
            this.saveBatch(files);
        }
    }

    @Override
    public List<Files> getFilesByRefId(String refId) {
        if (StringUtils.isEmpty(refId)) {
            return null;
        }
        List<Files> filesList = this.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, refId));
        return filesList;
    }

    private Files saveFile(MultipartFile file, String folder) throws IOException {
        Files files = new Files();
        String fileName = file.getOriginalFilename();
        files.setOriginaName(fileName);
        String strs[] = fileName.split("[.]");
        String suffix = strs[strs.length - 1];
        files.setSuffix("."+suffix);
        UUID uuid = UUID.randomUUID();
        files.setName(uuid.toString());//设置图片名
        String newFileName = uuid + "."+ suffix;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        String path = filePath+"/"+folder+"/"+time;
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        files.setPath(path);
        File targetFile = new File(path, newFileName);
        FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
        files.setUrl("/files/download/"+newFileName);
        files.setRealURL("/"+folder+"/"+time+"/"+newFileName+"."+suffix);
        return files;
    }

    private Files saveNcovDataFile(MultipartFile file,String path,String name,String title) throws IOException{
        Files files = new Files();
        String fileName = file.getOriginalFilename();
        files.setOriginaName(fileName);
        String strs[] = fileName.split("[.]");
        String suffix = strs[strs.length - 1];
        files.setSuffix("."+suffix);
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        files.setPath(path);
        files.setTitle(title);
        files.setName(name);
        File targetFile = new File(path,name);
        FileUtils.copyInputStreamToFile(file.getInputStream(),targetFile);
        files.setUrl("/files/download/"+name);
        //files.setRealURL("/"+folder+"/"+time+"/"+newFileName+"."+suffix);
        return files;
    }
    private Files updateNcovDataFile(MultipartFile file,String id,String path,String name,String title) throws IOException{
        Files files = new Files();
        files.setId(id);
        String fileName = file.getOriginalFilename();
        files.setOriginaName(fileName);
        String strs[] = fileName.split("[.]");
        String suffix = strs[strs.length - 1];
        files.setSuffix("."+suffix);
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        files.setPath(path);
        files.setName(name);
        files.setTitle(title);
        File targetFile = new File(path,name);
        FileUtils.copyInputStreamToFile(file.getInputStream(),targetFile);
        files.setUrl("/files/download/"+name);
        return files;
    }

    private Files saveNcovReport(MultipartFile file,String title) throws IOException{
        Files files = new Files();
        String fileName = file.getOriginalFilename();
        files.setOriginaName(fileName);
        String strs[] = fileName.split("[.]");
        String suffix = strs[strs.length - 1];
        files.setSuffix("."+suffix);
        String path = filePath+"/"+"ncov-report";
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        files.setPath(path);
        files.setTitle(title);
        UUID uuid = UUID.randomUUID();
        files.setName(uuid.toString());//设置图片名
        String newFileName = uuid + "."+ suffix;
        File targetFile = new File(path,newFileName);
        FileUtils.copyInputStreamToFile(file.getInputStream(),targetFile);
        files.setUrl("/files/download/"+newFileName);
        return files;
    }

    private Files updateNcovReport(MultipartFile file,String id,String title,String name) throws IOException{
        Files files = new Files();
        files.setId(id);
        files.setName(name);
        files.setTitle(title);
        String fileName = file.getOriginalFilename();
        files.setOriginaName(fileName);
        String strs[] = fileName.split("[.]");
        String suffix = strs[strs.length - 1];
        files.setSuffix("."+suffix);
        String path = filePath+"/"+"ncov-report" ;
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        files.setPath(path);
        File targetFile = new File(path,name);
        FileUtils.copyInputStreamToFile(file.getInputStream(),targetFile);
        files.setUrl("/files/download/"+name);
        return files;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void hotfix() {
        List<Files> allFiles = this.list(new QueryWrapper<>());
        List<Files> updateFiles = Lists.newArrayList();
        for(Files files:allFiles){
            //文件记录 名、路劲、后缀不为空
            if(StringUtils.isNotBlank(files.getName()) && StringUtils.isNotBlank(files.getPath())
                    && StringUtils.isNotBlank(files.getSuffix())){
                String path = files.getPath();
                if(path.startsWith(filePath)){
                    Files updateFile = new Files();
                    //去掉路劲前缀 拼接名和后缀
                    List<String> pList = Splitter.on("hwyFiles").splitToList(path);
                    String sufPath = pList.get(1);
                    StringBuilder sb = new StringBuilder(sufPath);
                    sb.append("/").append(files.getName()).append(files.getSuffix());
                    files.setRealURL(sb.toString());
                    BeanUtils.copyProperties(files,updateFile);
                    if(updateFile != null){
                        updateFiles.add(updateFile);
                    }else {
                        System.out.println("origin file "+files);
                    }
                }
            }
        }
        this.updateBatchById(updateFiles,3000);
    }
}
