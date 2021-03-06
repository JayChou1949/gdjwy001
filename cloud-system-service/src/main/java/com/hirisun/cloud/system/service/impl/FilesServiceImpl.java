package com.hirisun.cloud.system.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.JsonUtils;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.param.FilesParam;
import com.hirisun.cloud.system.bean.Files;
import com.hirisun.cloud.system.mapper.FilesMapper;
import com.hirisun.cloud.system.service.FilesService;


@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements FilesService {

    @Autowired
    FilesMapper filesMapper;
    private static String filePath;

    @Value("${file.path}")
    public void setRootPath(String rootPath) {
        FilesServiceImpl.filePath = rootPath;
    }

    private final static  String sourceFileName = "ncovSource.xlsx";

    private final static String clusterFileName = "ncovCluster.xlsx";

    private final static  String ecsFileName = "ncovEcsSource.xlsx";

    //????????????????????????????????????
    private static final Map<String,String> NCOV_BIG_DATA_FILE = ImmutableMap.<String,String>builder()
            .put("dataSharing","??????????????????V1.xlsx").put("dataModeling","??????????????????V1.xlsx")
            .put("dataGovernance","????????????????????????.xls").put("dataAccess","??????????????????.xlsx")
            .put("dataService","????????????????????????.xlsx").build();


    private static final Map<String,String> NCOV_BIG_DATA_TITLE = ImmutableMap.<String,String>builder()
            .put("dataSharing","???????????????-????????????").put("dataModeling","???????????????-????????????")
            .put("dataGovernance","???????????????-????????????").put("dataAccess","???????????????-????????????")
            .put("dataService","???????????????-????????????").build();


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
            files  = saveNcovDataFile(file,filePath,sourceFileName,"?????????????????????");
            files.insert();
        }else {
            files  = updateNcovDataFile(file,old.getId(),filePath,sourceFileName,"?????????????????????");
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
            files  = saveNcovDataFile(file,filePath,clusterFileName,"?????????????????????");
            files.insert();
        }else {
            files  = updateNcovDataFile(file,old.getId(),filePath,clusterFileName,"?????????????????????");
            files.updateById();
        }
        return files;
    }

    @Override
    public Files uploadEcsDataFile(MultipartFile file) throws IOException {
        Files files;
        Files old  = this.getOne(new QueryWrapper<Files>().lambda().eq(Files::getName,ecsFileName));
        if(old == null){
            files  = saveNcovDataFile(file,filePath,ecsFileName,"????????????????????????");
            files.insert();
        }else {
            files  = updateNcovDataFile(file,old.getId(),filePath,ecsFileName,"????????????????????????");
            files.updateById();
        }
        return files;
    }


    @Override
    public Files ncovDataAreaUpload(MultipartFile file,String module) throws  IOException{
        Files files;
        String filesName = NCOV_BIG_DATA_FILE.get(module);
        if(StringUtils.isBlank(filesName)){
            throw new CustomException(CommonCode.INVALID_PARAM);
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
            response.setContentType("application/force-download");// ???????????????????????????
            String originaName;
            try {
                originaName = URLEncoder.encode(save.getOriginaName(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                originaName = save.getOriginaName();
            }
            response.addHeader("Content-Disposition","attachment;fileName=" + originaName);// ???????????????

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
        files.setName(uuid.toString());//???????????????
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
    
    @Transactional(rollbackFor = Exception.class)
    public void refFiles(SubpageParam param) {
    	
    	String refId = param.getRefId();
    	
        if (StringUtils.isEmpty(refId)) {
            return;
        }
        
        List<FilesVo> files = param.getFiles();
        
        this.remove(new QueryWrapper<Files>().lambda().eq(Files::getRefId, refId));
        if (files != null && !files.isEmpty()) {
            for (FilesVo fileVo : files) {
            	
            	Files file = JsonUtils.voToBean(fileVo, Files.class);
            	file.setId(null);
            	file.setRefId(refId);
            	this.save(file);
            }
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
        files.setName(uuid.toString());//???????????????
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
        files.setRealURL("/"+folder+"/"+time+"/"+newFileName);
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
        files.setName(uuid.toString());//???????????????
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
            //???????????? ??????????????????????????????
            if(StringUtils.isNotBlank(files.getName()) && StringUtils.isNotBlank(files.getPath())
                    && StringUtils.isNotBlank(files.getSuffix())){
                String path = files.getPath();
                if(path.startsWith(filePath)){
                    Files updateFile = new Files();
                    //?????????????????? ??????????????????
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

    @Transactional(rollbackFor = Exception.class)
	public void remove(String saasSubpageId) {
		this.remove(new QueryWrapper<Files>().lambda().eq(Files::getRefId,saasSubpageId));
		
	}

	@Override
	public List<FilesVo> findByRefId(String subpageId) {
		
		List<Files> list = this.list(new QueryWrapper<Files>()
				.lambda().eq(Files::getRefId, subpageId));
		
		if(CollectionUtils.isNotEmpty(list)) {
			return JSON.parseObject(JSONObject.toJSONString(list),
					new TypeReference<List<FilesVo>>(){});
		}
		
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	public void saveBatch(FilesParam filesParam) {
		
		List<FilesVo> filesVoList = filesParam.getFiles();
		
		if(CollectionUtils.isNotEmpty(filesVoList)) {
			
			List<Files> filesList = new ArrayList<Files>(); 
			
			for(FilesVo vo : filesVoList) {
				Files files = new Files();
				BeanUtils.copyProperties(vo, files);
				filesList.add(files);
			}
			this.saveBatch(filesList);
		}
		
		
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(FilesParam param) {
		
		List<String> filesIdList = param.getFilesIdList();
		if(CollectionUtils.isNotEmpty(filesIdList)) {
			filesMapper.deleteBatchIds(filesIdList);
		}
		
		
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateFileRef(FilesParam param) {
		filesMapper.update(new Files(),new UpdateWrapper<Files>().lambda()
				.eq(Files::getRefId,param.getRefId()).set(Files::getRefId,param.getNewRefId()));
	}
}
