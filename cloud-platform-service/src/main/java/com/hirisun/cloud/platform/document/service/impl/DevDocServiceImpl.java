package com.hirisun.cloud.platform.document.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.api.file.FileApi;
import com.hirisun.cloud.api.system.SystemApi;
import com.hirisun.cloud.common.contains.ReviewStatus;
import com.hirisun.cloud.common.util.IpUtil;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.model.file.FileSystemVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.document.bean.DevDoc;
import com.hirisun.cloud.platform.document.bean.DevDocClass;
import com.hirisun.cloud.platform.document.bean.DevDocFile;
import com.hirisun.cloud.platform.document.mapper.DevDocMapper;
import com.hirisun.cloud.platform.document.service.DevDocFileService;
import com.hirisun.cloud.platform.document.service.DevDocService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 文档 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
@Slf4j
@Service
public class DevDocServiceImpl extends ServiceImpl<DevDocMapper, DevDoc> implements DevDocService {

    @Autowired
    private FileApi fileApi;

    @Autowired
    private DevDocMapper devDocMapper;

    @Autowired
    private DevDocFileService devDocFileService;

    @Autowired
    private SystemApi systemApi;

    @Override
    public Page<DevDoc> getPage(Page<DevDoc> page,UserVO userVO, Map paramMap) {
        return devDocMapper.getPage(page,userVO,paramMap);
    }

    @Transactional
    @Override
    public void saveOrUpdateDevDoc(UserVO user, DevDoc devDoc) {
        if (StringUtils.isEmpty(devDoc.getId())) {
            String id = UUIDUtil.getUUID();
            devDoc.setId(id);
            devDoc.setCreator(user.getIdcard());
            devDoc.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            if (!StringUtils.isEmpty(devDoc.getFileIds())) {
                List<DevDocFile> fileList = new ArrayList<>();
                String[] ids = devDoc.getFileIds().split(",");
                for(String str:ids){
                    DevDocFile file = new DevDocFile();
                    file.setDocId(id);
                    file.setFileId(str);
                    fileList.add(file);
                }
                devDocFileService.saveBatch(fileList);
            }
            this.save(devDoc);
        }else{
            devDoc.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            //检查附件是否有修改,查询数据库，对比
            String fileIds = devDoc.getFileIds();
            String [] fileArray=fileIds.split(",");
            List<DevDocFile> fileList = devDocFileService.list(new QueryWrapper<DevDocFile>().lambda().eq(DevDocFile::getDocId, devDoc.getId()));
            if("".equals(fileIds)){
                if(CollectionUtils.isNotEmpty(fileList)){
                    List<String> idList = fileList.stream().map(DevDocFile::getId).distinct().collect(Collectors.toList());
                    devDocFileService.removeByIds(idList);
                    //TODO 物理删除文件
                }
            }else if(!StringUtils.isEmpty(fileIds)){
                List<String> idList = fileList.stream().map(DevDocFile::getId).distinct().collect(Collectors.toList());
                devDocFileService.removeByIds(idList);
                fileList = new ArrayList<>();
                for(String str:fileArray){
                    DevDocFile file = new DevDocFile();
                    file.setDocId(devDoc.getId());
                    file.setFileId(str);
                    fileList.add(file);
                }
                devDocFileService.saveBatch(fileList);
            }
            this.updateById(devDoc);
        }
    }

    @Override
    public Map getDetailById(String id) {
        DevDoc devDoc = this.getById(id);
        JSONObject imageFile=null;
        JSONObject docFile=null;
        if (!StringUtils.isEmpty(devDoc.getImage())) {
            String imageJsonStr=fileApi.getFileSystemInfo(devDoc.getImage());
            if (!StringUtils.isEmpty(imageJsonStr)) {
                imageFile = JSON.parseObject(imageJsonStr);
            }
        }
        List<DevDocFile> list = devDocFileService.list(new QueryWrapper<DevDocFile>().lambda().eq(DevDocFile::getDocId, devDoc.getId()));
        List<String> fileIds = list.stream().map(DevDocFile::getFileId).distinct().collect(Collectors.toList());
        List<FileSystemVO> fileList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(fileIds)){
            String fileStr = fileApi.getFileByIds(fileIds);
            if (!org.apache.commons.lang.StringUtils.isEmpty(fileStr)) {
                fileList = JSON.parseArray(fileStr, FileSystemVO.class);
            }
        }
        Map map = new HashMap();
        map.put("devDoc", devDoc);
        map.put("imageFile", imageFile);
        map.put("docFileList", fileList);
        return map;
    }

    /**
     * 删除开发文档
     * 逻辑删除文档，物理删除图片，附件
     * @param id
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void deleteById(UserVO user,String id) {
        DevDoc document = this.getById(id);
        document.setStatus(ReviewStatus.DELETE.getCode());
        this.updateById(document);
        //TODO 物理删除文件
        log.info("删除图片");
        fileApi.delete(document.getImage());

        List<DevDocFile> list = devDocFileService.list(new QueryWrapper<DevDocFile>().lambda().eq(DevDocFile::getDocId, document.getId()));
        list.forEach(item->{
            log.info("删除附件");
            fileApi.delete(item.getFileId());
        });
    }

}
